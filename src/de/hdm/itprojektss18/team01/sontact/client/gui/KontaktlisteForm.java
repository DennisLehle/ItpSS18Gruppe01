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

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontaktliste selectedKontaktliste = null;
	
	Nutzer nutzer = new Nutzer();

	SontactTreeViewModel sontactTree = null;
	
	HorizontalPanel BtnPanel = new HorizontalPanel();
	VerticalPanel vp = new VerticalPanel();
	Label ownerLb = new Label();

	TextBox txtBox = new TextBox();
	Label infolb = new Label();

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste bereits vorhanden
	 * ist.
	 * 
	 * @param Kontaktliste
	 */
	public KontaktlisteForm(Kontaktliste kl) {
		
				this.selectedKontaktliste = kl;
			
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				RootPanel.get("contentHeader").add(new HTML(selectedKontaktliste.getTitel()));

				//Setten des Nutzers durch Cookies.
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

//				// ClickHandler zum teilen von Kontaktlisten.
//				Button shareBtn = new Button(
//						"<image src='/images/share.png' width='20px' height='20px' align='center' /> teilen");
//
//				shareBtn.setStylePrimaryName("teilunsKlButton");
//				shareBtn.setTitle("Kontaktliste mit anderen Nutzern teilen");
//
//				shareBtn.addClickHandler(new shareKontaktlisteClickHandler());
//				BtnPanel.add(shareBtn);

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

				// Abfrage wer der Owner der Liste ist.
				if (selectedKontaktliste.getOwnerId() != nutzer.getId()) {
					ev.getNutzerById(selectedKontaktliste.getOwnerId(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Nutzer result) {
							ownerLb.setText("Eigentümer: " + result.getEmailAddress());

						}

					});
				}

				// Überprüft den Status eines Objektes ob es geteilt wurde.
				ev.getStatusForObject(selectedKontaktliste.getId(), selectedKontaktliste.getType(), new AsyncCallback<Boolean>() {

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
							//
						}
					}

				});

				vp.add(ownerLb);
				vp.add(BtnPanel);
				RootPanel.get("content").add(vp);
				RootPanel.get("content").add(new ShowKontakte(nutzer, selectedKontaktliste));

			}

	

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste neu erstellt wird.
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
		
		//Der TextBox einen KexDownHandler hinzufügen für Userfreundlicheren Umgang
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

			boolean x = Window.confirm(
					"Sind Sie sicher die Kontaktliste " + selectedKontaktliste.getTitel() + " löschen zu wollen?");

			if (x == true) {
				// Nutzer Cookies setzen und dann per Nutzer holen.
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				// Wenn man nicht der Owner ist wird erst die Berechtigung entfernt.
				if (nutzer.getId() != selectedKontaktliste.getOwnerId()) {

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

								if (berecht.elementAt(i).getObjectId() == selectedKontaktliste.getId()) {
									Berechtigung b = new Berechtigung();
									b.setObjectId(selectedKontaktliste.getId());
									b.setOwnerId(selectedKontaktliste.getOwnerId());
									b.setReceiverId(nutzer.getId());
									b.setType(selectedKontaktliste.getType());

									ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {

											MessageBox.alertWidget("Hinweis", "Die Teilhaberschaft wurde erfolgreich aufgelöst.");

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
					if (selectedKontaktliste.getTitel() == "Meine Kontakte"
							&& selectedKontaktliste.getOwnerId() == nutzer.getId()
							|| selectedKontaktliste.getTitel() == "Mit mir geteilte Kontakte"
									&& selectedKontaktliste.getOwnerId() == nutzer.getId()) {

						MessageBox.alertWidget("Hinweis", "Tut uns leid, die Standard Listen können hier nicht gelöscht werden.");

					} else {

						// Wenn es sich nicht um eine Standard Liste handelt kann sie gelöscht werden.
						ev.deleteKontaktliste(selectedKontaktliste, new AsyncCallback<Void>() {

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

			if(txtBox.getText() == "") {
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
					MessageBox.alertWidget("Glückwunsch", "Sie haben die Kontaktliste " + result.getTitel()+ " erfolgreich erstellt.");

				}
			});
			}

		}

	}
	
//	/**
//	 * ClickHandler zum teilen von Kontaktlisten.
//	 */
//	private class shareKontaktlisteClickHandler implements ClickHandler {
//		public void onClick(ClickEvent event) {
//			RootPanel.get("content").clear();
////			RootPanel.get("content").add(new ShowKontakte());
//			MessageBox.shareAlert("Geben Sie die Email des Empfängers an", " ", selectedKontaktliste);
//
//		}
//
//	}

	/**
	 * ClickHandler um Kontakte einer Kontaktliste hinzuzufügen.
	 */
	private class addKontaktClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			RootPanel.get("content").add(new ShowKontakte(selectedKontaktliste));

		}

	}

	/**
	 * ClickHandler zum löschen von Teilhaberschaften
	 */
	private class deleteTeilhaberClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			MessageBox.deleteTeilhaber("Teilhaberschaft entfernen",
					"Wählen sie für die Löschung einer Teilhaberschaft eine EMail Adresse aus.", selectedKontaktliste,
					null);

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

			RootPanel.get("contentHeader").add(new HTML(selectedKontaktliste.getTitel() + " bearbeiten <image src='/images/edit.png' width='30px' height='30px' align='center'/></h2>"));

			Button cancelBtn = new Button("<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Abbrechen");
			cancelBtn.setTitle("Abbrechen der Bearbeitung");
			cancelBtn.setStylePrimaryName("cancelKontaktlButton");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("contentHeader").clear();
					BtnPanel.clear();
					RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));
		
				}
			});
			if(selectedKontaktliste.getTitel() == "Meine Kontakte" && selectedKontaktliste.getOwnerId()== nutzer.getId() || 
					selectedKontaktliste.getTitel() == "Mit mir geteilte Kontakte" && selectedKontaktliste.getOwnerId()== nutzer.getId()){
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				BtnPanel.clear();
				RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));
				MessageBox.alertWidget("Hinweis", "Sie dürfen die Standard Kontaktliste " + selectedKontaktliste.getTitel() + " nicht umbenennen.");

			} else {
			// Instanziierung Button zum Speichern der Aenderungen an der selektierten Kontaktliste
			Button saveBtn = new Button("<image src='/images/save.png' width='20px' height='20px' align='center'/> Speichern");
			saveBtn.setTitle("Speichern der Änderung");
			saveBtn.setStylePrimaryName("bearbeitungKSave");
			
			// ClickHandler fuer das Speichern
			saveBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
								
					if(txtBox.getText() == "") {
						MessageBox.alertWidget("Hinweis", "Sie dürfen keine Kontaktliste ohne Namen erstellen");
						txtBox.getElement().getStyle().setBorderColor("red");
					} else {
					selectedKontaktliste.setTitel(txtBox.getText());
					
					ev.saveKontaktliste(selectedKontaktliste, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Void result) {
//							RootPanel.get("navigator").clear();
//							RootPanel.get("navigator").add(new Navigation(nutzer));
							RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));
							sontactTree.updateKontaktliste(selectedKontaktliste);
							sontactTree.setSelectedKontaktliste(selectedKontaktliste);
							

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