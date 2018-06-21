 package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
 * Interaktionsmöglichkeiten um Kontaktlisten Anzuzeigen, zu Bearbeiten, zu Löschen
 * oder neu anzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktlisteForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontaktliste selectedKontaktliste = null;

	SontactTreeViewModel sontactTree = null;

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

		ev.getKontaktlisteById(kl.getId(), new AsyncCallback<Kontaktliste>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Hopala");
			}

			@Override
			public void onSuccess(Kontaktliste result) {
				
				Nutzer n = new Nutzer();
				n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
				selectedKontaktliste = result;

				HorizontalPanel headerPanel = new HorizontalPanel();
				HorizontalPanel BtnPanel = new HorizontalPanel();
				VerticalPanel vp = new VerticalPanel();
				Label ownerLb = new Label();
				
				
				headerPanel.add(new HTML(selectedKontaktliste.getTitel()));
				
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
				BtnPanel.add(deleteKlBtn);
				// ClickHandler f�r das L�schen einer Kontaktliste
				deleteKlBtn.addClickHandler(new deleteClickHandler());
				deleteKlBtn.setStylePrimaryName("deleteKlButton");	
				deleteKlBtn.setTitle("Löschen der Kontaktliste");
				BtnPanel.add(deleteKlBtn);

				Button addKontaktBtn = new Button(
						"<image src='/images/user.png' width='20px' height='20px' align='center' /> hinzufügen");
				addKontaktBtn.setStylePrimaryName("addKontaktToKlButton");
				addKontaktBtn.addClickHandler(new addKontaktClickHandler());
				addKontaktBtn.setTitle("Hinzufügen eines Kontakts zu Kontaktliste");
				BtnPanel.add(addKontaktBtn);
				
				//ClickHandler zum teilen von Kontaktlisten.
				Button shareBtn = new Button(
						"<image src='/images/share.png' width='20px' height='20px' align='center' /> teilen");
				shareBtn.setStylePrimaryName("teilunsKlButton");
				shareBtn.addClickHandler(new shareKontaktlisteClickHandler());
				shareBtn.setTitle("Kontaktliste mit anderen Nutzern teilen");
				BtnPanel.add(shareBtn);
				
				infolb.setText("Kontaktlisten Interaktion");
				infolb.setStylePrimaryName("infoLabel");
				vp.add(infolb);
				
				//ClickHandler zum Löschen von Kontaktlisten-Teilhaberschaften.
				Button deleteTeilhaber = new Button("<image src='/images/share.png' width='20px' height='20px' align='center' /> löschen");
				deleteTeilhaber.setStylePrimaryName("teilunsDeleteKlButton");
				deleteTeilhaber.addClickHandler(new deleteTeilhaberClickHandler());
				deleteTeilhaber.setTitle("Löschen einer Teilhaberschaft an der Kontaktliste");
				BtnPanel.add(deleteTeilhaber);
							
				//Abfrage wer der Owner der Liste ist.
				if(kl.getOwner() != n.getId()) {
				ev.getNutzerById(kl.getOwnerId(), new AsyncCallback<Nutzer>() {

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
				
				//Überprüft den Status eines Objektes ob es geteilt wurde.
				ev.getStatusForObject(kl.getId(),kl.getType(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();
						
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result ==true) {
							HTML shared = new HTML("<image src='/images/share.png' width='15px' height='15px' />");
							shared.setTitle("Geteilte Kontaktliste");
							RootPanel.get("contentHeader").add(shared);
//							
						}
					}
					
				});
				
				
				vp.add(ownerLb);
				vp.add(BtnPanel);
				RootPanel.get("contentHeader").add(headerPanel);
				RootPanel.get("content").add(vp);
				RootPanel.get("content").add(new ShowKontakte(n, result));

			}

		});
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste neu erstellt wird.
	 */
	public KontaktlisteForm(final Nutzer n) {
		
		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Neue Kontaktliste erstellen</h2>"));

		HorizontalPanel BtnPanel = new HorizontalPanel();

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen.
				Window.Location.reload();

			}
		});

		BtnPanel.add(quitBtn);

		Button saveBtn = new Button("erstellen");
		saveBtn.addClickHandler(new speichernKontaktlisteClickHandler());

		BtnPanel.add(saveBtn);

		VerticalPanel vp = new VerticalPanel();

		vp.add(headerPanel);
		vp.add(new Label("Name der Kontaktliste:"));
		txtBox.getElement().setPropertyString("placeholder", "Titel der Kontaktliste...");
		vp.add(txtBox);
		vp.add(BtnPanel);

		RootPanel.get("content").add(vp);

	}

	// Beginn der ClickHandler

	/**
	 * ClickHandler fuer das Loeschen einer Kontaktliste.
	 * 
	 * @author Batista
	 *
	 */
	private class deleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			Window.confirm("Sind Sie sicher die Kontaktliste " + selectedKontaktliste.getTitel() + " löschen zu wollen?");
			
			// Nutzer Cookies setzen und dann per Nutzer holen.
			Nutzer n = new Nutzer();
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			// Wenn man nicht der Owner ist wird erst die Berechtigung entfernt.
			if (n.getId() != selectedKontaktliste.getOwnerId()) {

				/*
				 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und 
				 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
				 */
				ev.getAllBerechtigungenByReceiver(n.getId(), new AsyncCallback<Vector<Berechtigung>>() {

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
								b.setReceiverId(n.getId());
								b.setType(selectedKontaktliste.getType());

								ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										caught.getMessage().toString();

									}

									@Override
									public void onSuccess(Void result) {
										
										Window.alert("Die Teilhaberschaft wurde aufgelöst.");
										
										// Nutzer Cookies holen.
										Nutzer n = new Nutzer();
										n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
										n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

										RootPanel.get("navigator").clear();
										Navigation save = new Navigation(n);  
										RootPanel.get("navigator").add(save);
										RootPanel.get("content").clear();
										RootPanel.get("content").add(new ShowKontakte(n));

										
									}
								});

							}
						}

					}

				});

			// Ist man Owner der Kontaktliste wird die Kontaktliste direkt gelöscht.
			} else {
				
				//Zusätzliche Prüfung ob es sich um eines der default Kontaktlisten handelt.
				if(selectedKontaktliste.getTitel() == "Meine Kontakte" && selectedKontaktliste.getOwnerId() == n.getId()|| 
						selectedKontaktliste.getTitel() == "Mit mir geteilte Kontakte" && selectedKontaktliste.getOwnerId() == n.getId()) {
					
					Window.alert("Tut uns leid, die Standard Listen können hier nicht gelöscht werden.");
					
				} else {
					
				//Wenn es sich nicht um eine Standard Liste handelt kann sie gelöscht werden.
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
						Navigation save = new Navigation(n);  
						RootPanel.get("navigator").add(save);
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new ShowKontakte(n));
					}
				});
				}
			}
		}
	}

	/**
	 * ClickHandler zum Speichern einer neu angelegten Kontaktliste.
	 */
	public class speichernKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			// Cookies des Nutzers holen.
			Nutzer n = new Nutzer();
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			ev.createKontaktliste(txtBox.getText(), n, new AsyncCallback<Kontaktliste>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Kontaktliste result) {

					// Nutzer Cookies holen.
					Nutzer n = new Nutzer();
					n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
					n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
					
					// Refresh der Seite für die Aktualisierug des Baumes.
					RootPanel.get("navigator").clear();
					Navigation save = new Navigation(n);  
					RootPanel.get("navigator").add(save);
					RootPanel.get("content").add(new KontaktlisteForm(result));

				}
			});

		}

	}
	
	private class shareKontaktlisteClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			
			MessageBox.shareAlert("Geben Sie die Email des Empfängers an", "Email: ", selectedKontaktliste);

		}

	}

	private class addKontaktClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			RootPanel.get("content").add(new ShowKontakte(selectedKontaktliste));

		}

	}
	
	private class deleteTeilhaberClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			MessageBox.deleteTeilhaber("Teilhaberschaft entfernen", "Wählen sie für die Löschung einer Teilhaberschaft eine EMail Adresse aus.", selectedKontaktliste, null);

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

			HorizontalPanel BtnPanel = new HorizontalPanel();
			HorizontalPanel headerPanel = new HorizontalPanel();
			headerPanel.add(
					new HTML(selectedKontaktliste.getTitel() + " bearbeiten....</h2>"));

			Button cancelBtn = new Button("abbrechen");
			cancelBtn.setTitle("Abbrechen der Bearbeitung");
			
			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("contentHeader").clear();
					RootPanel.get("contentHeader").add(new KontaktlisteForm(selectedKontaktliste));
				}
			});

			BtnPanel.add(cancelBtn);

			// Instanziierung Button zum Speichern der Aenderungen an der selektierten Kontaktliste
			Button saveBtn = new Button("speichern");
			saveBtn.setTitle("Speichern der Änderung");
			
			// ClickHandler fuer das Speichern
			saveBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					selectedKontaktliste.setTitel(txtBox.getText());

					ev.saveKontaktliste(selectedKontaktliste, new AsyncCallback<Void>() {

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
							Navigation save = new Navigation(n);  
							RootPanel.get("navigator").add(save);
							RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));


						}

					});

				}
			});

			BtnPanel.add(saveBtn);

			VerticalPanel vp = new VerticalPanel();
			vp.add(headerPanel);
			txtBox.getElement().setPropertyString("placeholder", "Neuer Titel...");
			vp.add(txtBox);
			vp.add(BtnPanel);
			RootPanel.get("content").add(vp);
			selectedKontaktliste.setTitel(txtBox.getText());

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