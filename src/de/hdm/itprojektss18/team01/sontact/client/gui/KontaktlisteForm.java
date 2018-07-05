package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Klasse welche Formulare für Kontaktlisten darstellt, diese erlauben
 * Interaktionsmöglichkeiten um Kontaktlisten Anzuzeigen, zu Bearbeiten, zu
 * Löschen oder neu anzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktlisteForm extends VerticalPanel {

	// Setzen des Zugriffs auf das Asyne Service Interface
	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	// Erstellung / Deklarierung von Objekten
	Kontaktliste kl = new Kontaktliste();
	Kontaktliste selectedKontaktliste = null;
	Nutzer nutzer = new Nutzer();
	SontactTreeViewModel sontactTree = null;

	// Panels anlegen
	HorizontalPanel BtnPanel = new HorizontalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	VerticalPanel vp = new VerticalPanel();

	// Label anlegen
	Label ownerLb = new Label();
	Label infolb = new Label();

	// TextBox anlegen
	TextBox txtBox = new TextBox();

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste bereits vorhanden
	 * ist.
	 * 
	 * @param kl
	 *            Kontktliste die ausgewaehlt wurde
	 */
	public KontaktlisteForm(Kontaktliste kl) {
		this.kl = kl;

		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new HTML(kl.getTitel()));

		// Setten des Nutzers durch Cookies.
		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		/*
		 * Erstllung der Buttons und dem zuweisen zu einem CSS-Style.
		 */
		// Update-Button intanziieren und dem Panel zuweisen
		Button editKontaktlisteBtn = new Button(
				"<image src='/images/edit.png' width='20px' height='20px' align='center' /> bearbeiten");

		editKontaktlisteBtn.setStylePrimaryName("bearbeitenKlButton");
		editKontaktlisteBtn.setTitle("Bearbeitung der Kontaktliste");

		// ClickHandler f�r das Updaten einer Kontaktliste
		editKontaktlisteBtn.addClickHandler(new updateKontaktlisteClickHandler());
		BtnPanel.add(editKontaktlisteBtn);

		// L�sch-Button instanziieren und dem Panel zuweisen
		Button deleteKlBtn = new Button(
				"<image src='/images/trash.png' width='20px' height='20px' align='center' />  löschen");

		deleteKlBtn.setStylePrimaryName("deleteKlButton");
		deleteKlBtn.setTitle("Löschen der Kontaktliste");

		// ClickHandler f�r das L�schen einer Kontaktliste
		deleteKlBtn.addClickHandler(new deleteClickHandler());
		BtnPanel.add(deleteKlBtn);

		Button addKontaktBtn = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> hinzufügen");

		addKontaktBtn.setStylePrimaryName("addKontaktToKlButton");
		addKontaktBtn.setTitle("Hinzufügen eines Kontakts zu Kontaktliste");

		addKontaktBtn.addClickHandler(new addKontaktClickHandler());
		BtnPanel.add(addKontaktBtn);

		infolb.setText("Kontaktlisten Interaktion");
		infolb.setStylePrimaryName("infoLabel");
		vp.add(infolb);

		// ClickHandler zum Löschen von Kontaktlisten-Teilhaberschaften.
		Button deleteTeilhaber = new Button(
				"<image src='/images/share.png' width='20px' height='20px' align='center' /> löschen");

		deleteTeilhaber.setStylePrimaryName("teilunsDeleteKlButton");
		deleteTeilhaber.setTitle("Löschen einer Teilhaberschaft an der Kontaktliste");

		deleteTeilhaber.addClickHandler(new deleteTeilhaberClickHandler());
		BtnPanel.add(deleteTeilhaber);

		/**
		 * Prüfungs Methode ob der Nutzer der Owner
		 * des Kontaktes ist um dementsprechen das Label
		 * und die Images der Teilung zu setzen.
		 */
		ownerPruefung();

		vp.add(hp);
		vp.add(BtnPanel);
		RootPanel.get("content").add(vp);
		RootPanel.get("content").add(new ShowKontakte(nutzer, kl));

	}

	/**
	 * Methode die Prüft ob der Nutzer der Owner des Kontaktes ist
	 * oder nicht um dementsprechen das Label und Image für die Teilung zu setzen.
	 */
	public void ownerPruefung() {
		// Abfrage wer der Owner der Liste ist.
		if (kl.getOwnerId() != nutzer.getId()) {
			ev.getNutzerById(kl.getOwnerId(), new AsyncCallback<Nutzer>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Nutzer result) {
					ownerLb.setText("Eigentümer: " + result.getEmailAddress());
					ownerLb.setStylePrimaryName("labelD");
					hp.add(new HTML("<image src='/images/info3.png' width='22px' height='22px' />"));
					hp.add(ownerLb);

				}

			});
		} else {
			ownerLb.setText("Sie sind der Eigentümer dieser Kontaktliste");
			ownerLb.setStylePrimaryName("labelD");
			hp.add(new HTML("<image src='/images/info3.png' width='22px' height='22px' />"));
			hp.add(ownerLb);
		}

		// Überprüft den Status eines Objektes ob es geteilt wurde.
		ev.getStatusForObject(kl.getId(), kl.getType(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getMessage().toString();

			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == true) {
					HTML shared = new HTML("<image src='/images/share.png' width='15px' height='15px' />");
					shared.setTitle("Geteilte Kontaktliste");
					RootPanel.get("contentHeader").add(shared);

				}
			}

		});

	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste neu erstellt wird.
	 * 
	 * @param n
	 *            Nutzer der eingeloggt ist
	 */
	public KontaktlisteForm(final Nutzer n) {

		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new HTML("<h2>Kontaktliste anlegen</h2>"));

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button(
				"<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Abbrechen");
		quitBtn.setTitle("Erstellung einer neuen Kontaktliste abbrechen");
		quitBtn.setStylePrimaryName("cancelKontaktlButton");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				RootPanel.get("content").add(new ShowKontakte(n));

			}
		});

		Button saveBtn = new Button(
				"<image src='/images/save.png' width='20px' height='20px' align='center'/> Speichern");
		saveBtn.setTitle("Neue Kontaktliste speichern");
		saveBtn.setStylePrimaryName("bearbeitungKSave");
		saveBtn.addClickHandler(new SpeichernKontaktlisteClickHandler());

		BtnPanel.add(quitBtn);
		BtnPanel.add(saveBtn);

		txtBox.getElement().setPropertyString("placeholder", "Titel der Kontaktliste...");
		vp.add(txtBox);
		vp.add(BtnPanel);

		RootPanel.get("content").add(vp);

		// Der TextBox einen KexDownHandler hinzufügen für Userfreundlicheren Umgang
		txtBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					saveBtn.click();

				}
			}

		});

	}

	/**
	 * ClickHandler fuer das Loeschen einer Kontaktliste.
	 */
	private class deleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			boolean x = Window.confirm("Sind Sie sicher die Kontaktliste " + kl.getTitel() + " löschen zu wollen?");

			if (x == true) {
				// Nutzer Cookies setzen und dann per Nutzer holen.
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				// Wenn man nicht der Owner ist wird erst die Berechtigung entfernt.
				if (nutzer.getId() != kl.getOwnerId()) {

					/*
					 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
					 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
					 */
					ev.getAllBerechtigungenByReceiver(nutzer.getId(), new AsyncCallback<Vector<Berechtigung>>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();
						}

						@Override
						public void onSuccess(Vector<Berechtigung> result) {
							Vector<Berechtigung> berecht = result;

							for (int i = 0; i < berecht.size(); i++) {

								if (berecht.elementAt(i).getObjectId() == kl.getId()) {
									Berechtigung b = new Berechtigung();
									b.setObjectId(kl.getId());
									b.setOwnerId(kl.getOwnerId());
									b.setReceiverId(nutzer.getId());
									b.setType(kl.getType());

									ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {

											MessageBox.alertWidget("Hinweis",
													"Die Teilhaberschaft wurde erfolgreich aufgelöst.");

											// Nutzer Cookies holen.
											Nutzer n = new Nutzer();
											n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
											n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

											RootPanel.get("navigator").clear();
											RootPanel.get("navigator").add(new Navigation(n));
											RootPanel.get("content").clear();
											RootPanel.get("contentHeader").clear();
											RootPanel.get("content").add(new ShowKontakte(n));

										}
									});

								}
							}

						}

					});

					// Ist man Owner der Kontaktliste wird die Kontaktliste direkt gelöscht.
				} else {

					// Zusätzliche Prüfung ob es sich um eines der default Kontaktlisten handelt.
					if (kl.getTitel() == "Meine Kontakte" && kl.getOwnerId() == nutzer.getId()
							|| kl.getTitel() == "Mit mir geteilte Kontakte" && kl.getOwnerId() == nutzer.getId()) {

						MessageBox.alertWidget("Hinweis",
								"Tut uns leid, die Standard-Kontaktliste kann nicht gelöscht werden.");

					} else {

						// Wenn es sich nicht um eine Standard Liste handelt kann sie gelöscht werden.
						ev.deleteKontaktliste(kl, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();
							}

							@Override
							public void onSuccess(Void result) {

								// Nutzer Cookies holen.
								Nutzer n = new Nutzer();
								n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
								n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

								RootPanel.get("navigator").clear();
								RootPanel.get("navigator").add(new Navigation(n));
								RootPanel.get("content").clear();
								RootPanel.get("contentHeader").clear();
								RootPanel.get("content").add(new ShowKontakte(n));
							}
						});
					}
				}

			}

		}
	}

	/**
	 * ClickHandler zum Speichern einer neu angelegten Kontaktliste.
	 */
	public class SpeichernKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			// Cookies des Nutzers holen.
			nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			if (txtBox.getText() == "") {
				MessageBox.alertWidget("Hinweis", "Bitte geben Sie der Kontaktliste einen Namen");
				txtBox.getElement().getStyle().setBorderColor("red");
			} else {
				ev.createKontaktliste(txtBox.getText(), nutzer, new AsyncCallback<Kontaktliste>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Kontaktliste result) {
						// Refresh der Seite für die Aktualisierug des Baumes.
						RootPanel.get("navigator").clear();
						RootPanel.get("content").clear();
						RootPanel.get("contentHeader").clear();
						RootPanel.get("navigator").add(new Navigation(nutzer));
						RootPanel.get("content").add(new KontaktlisteForm(result));
						MessageBox.alertWidget("Glückwunsch",
								"Sie haben die Kontaktliste " + result.getTitel() + " erfolgreich erstellt.");

					}
				});
			}

		}

	}

	/**
	 * ClickHandler um Kontakte einer Kontaktliste hinzuzufügen.
	 */
	private class addKontaktClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			RootPanel.get("content").add(new ShowKontakte(kl));

		}

	}

	/**
	 * ClickHandler zum löschen von Teilhaberschaften
	 */
	private class deleteTeilhaberClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			MessageBox.deleteTeilhaber("Teilhaberschaft entfernen",
					"Wählen sie für die Löschung einer Teilhaberschaft eine EMail Adresse aus.", kl, null);

		}

	}

	/**
	 * ClickHandler fuer das Updaten einer Kontaktliste.
	 * 
	 * @author Batista
	 */
	private class updateKontaktlisteClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("content").clear();
			RootPanel.get("contentHeader").clear();
			BtnPanel.clear();
			vp.clear();

			RootPanel.get("contentHeader").add(new HTML(kl.getTitel()
					+ " bearbeiten <image src='/images/edit.png' width='30px' height='30px' align='center'/></h2>"));

			Button cancelBtn = new Button(
					"<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Abbrechen");
			cancelBtn.setTitle("Abbrechen der Bearbeitung");
			cancelBtn.setStylePrimaryName("cancelKontaktlButton");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					BtnPanel.clear();
					RootPanel.get("content").add(new KontaktlisteForm(kl));

				}
			});
			if (kl.getTitel() == "Meine Kontakte" && kl.getOwnerId() == nutzer.getId()
					|| kl.getTitel() == "Mit mir geteilte Kontakte" && kl.getOwnerId() == nutzer.getId()) {
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				BtnPanel.clear();
				RootPanel.get("content").add(new KontaktlisteForm(kl));
				MessageBox.alertWidget("Hinweis",
						"Sie dürfen die Standard Kontaktliste " + kl.getTitel() + " nicht umbenennen.");

			} else {
				// Instanziierung Button zum Speichern der Aenderungen an der selektierten
				// Kontaktliste
				Button saveBtn = new Button(
						"<image src='/images/save.png' width='20px' height='20px' align='center'/> Speichern");
				saveBtn.setTitle("Speichern der Änderung");
				saveBtn.setStylePrimaryName("bearbeitungKSave");

				// ClickHandler fuer das Speichern
				saveBtn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						if (txtBox.getText() == "") {
							MessageBox.alertWidget("Hinweis", "Sie dürfen keine Kontaktliste ohne Namen erstellen");
							txtBox.getElement().getStyle().setBorderColor("red");
						} else {
							kl.setTitel(txtBox.getText());

							ev.saveKontaktliste(kl, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();

								}

								@Override
								public void onSuccess(Void result) {
									RootPanel.get("navigator").clear();
									RootPanel.get("navigator").add(new Navigation(nutzer));
									RootPanel.get("content").add(new KontaktlisteForm(kl));
									MessageBox.alertWidget("Hinweis",
											"Ihre Kontaktliste wurde erfolgreich aktualisiert.");
									sontactTree.updateKontaktliste(kl);
									sontactTree.setSelectedKontaktliste(kl);

								}

							});
						}

					}

				});
				BtnPanel.add(saveBtn);
				BtnPanel.add(cancelBtn);
				txtBox.getElement().setPropertyString("placeholder", "Neuer Titel...");
				vp.add(txtBox);
				vp.add(BtnPanel);
				RootPanel.get("content").add(vp);
				selectedKontaktliste.setTitel(txtBox.getText());
			}

		}

	}

	public void setSelectedKontaktliste(Kontaktliste kl) {
		if (kl != null) {
			selectedKontaktliste = kl;
			txtBox.setText(selectedKontaktliste.getTitel());
		} else {

			txtBox.setText("");

		}

	}

	public void setSontactTreeViewModel(SontactTreeViewModel sontactTreeViewModel) {
		sontactTree = sontactTreeViewModel;
	}

}