package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import org.eclipse.jetty.security.jaspi.modules.UserInfo;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Klasse welche Formulare f�r Kontaktd darstellt, diese erlauben
 * Interaktionsm�glichkeiten um Kontakte Anzuzeigen, zu Bearbeiten, zu L�schen
 * oder Neuanzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontakt selectedKontakt = null;

	SontactTreeViewModel sontactTree = null;

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();

	Label erstellungsdatum = new Label();
	Label modifikationsdatum = new Label();

	FlexTable KontaktProfilFelx = new FlexTable();

	public KontaktForm() {
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt bereits vorhanden ist.
	 * 
	 * @param Kontakt
	 */
	public KontaktForm(Kontakt k) {

		this.selectedKontakt = k;
		
		//Nutzer Cookies holen.
		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
		
		RootPanel.get("content").clear();

		ev.getKontaktById(k.getId(), new AsyncCallback<Kontakt>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Hopala");
			}

			@Override
			public void onSuccess(Kontakt result) {
				selectedKontakt = result;

				HorizontalPanel headerPanel = new HorizontalPanel();
				headerPanel.setBorderWidth(10);
				headerPanel.setWidth("70%");
				HorizontalPanel BtnPanel = new HorizontalPanel();
				BtnPanel.setBorderWidth(10);
				BtnPanel.getElement().getStyle().setDisplay(Display.BLOCK);
				VerticalPanel vp = new VerticalPanel();
				Label ownerLb = new Label();

				headerPanel.add(new HTML("<h2>Kontakt: <em>" + selectedKontakt.getVorname() + " "
						+ selectedKontakt.getNachname() + "</em></h2>"));

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktBtn = new Button(
						"<image src='/images/user.png' width='20px' height='20px' align='center' /> bearbeiten");

				// ClickHandler f�r das Updaten eines Kontakts
				editKontaktBtn.addClickHandler(new updateKontaktClickHandler());

				BtnPanel.add(editKontaktBtn);
				
				//ClickHandler zum teilen von Kontakten
				Button shareBtn = new Button(
						"<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");

				shareBtn.addClickHandler(new shareKontaktlisteClickHandler());
				BtnPanel.add(shareBtn);
				
				
				//Abfrage wer der Owner des Kontaktes ist.
				if(k.getOwnerId() != n.getId()) {
				ev.getNutzerById(k.getOwnerId(), new AsyncCallback<Nutzer>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();
						
					}

					@Override
					public void onSuccess(Nutzer result) {
						ownerLb.setText("Eigentümer: "+result.getEmailAddress());
						
						
					}
					
				});
				}
				

				// Panel fuer das Erstellungs- und Modifikationsdatum
				VerticalPanel datePanel = new VerticalPanel();
				datePanel.setSpacing(20);

				DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
				erstellungsdatum.setText("Erstellungsdatum : " + dateFormat.format(selectedKontakt.getErstellDat()));
				modifikationsdatum.setText("Modifikationsdatum : " + dateFormat.format(selectedKontakt.getModDat()));
				
				datePanel.add(erstellungsdatum);
				datePanel.add(modifikationsdatum);
				
		
				//Überprüft Status eines Objekts ob es geteilt wurde.
				ev.getStatusForObject(k.getId(),k.getType(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();
						
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result ==true) {
							HTML shared = new HTML("<image src='/images/share.png' width='15px' height='15px' align='center' />");
							headerPanel.add(shared);
						}
					}
					
				});
				
				//Überprüfung ob Kontakt den Nutzer repräsentiert für Löschung aus dem System.
				if(k.getOwnerId() == n.getId() && k.getIdentifier() == 'r') {

					//Button für die Löschung erstellen und ClickHandler zuweisen.
					Button deleteNutzer = new Button("<image src='/images/trash.png' width='15px' height='15px' align='center' />");
					deleteNutzer.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							//Abfrage ob Nutzer sich wirklich löschen will.
							Window.confirm("Wollen Sie sich wirklich unwiderruflich von uns verabschieden?");
							
							ev.deleteNutzer(n, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();
									
								}

								@Override
								public void onSuccess(Void result) {
									//Nutzer wurde gelöscht und wird auf die Startseite verwiesen.
									Window.Location.replace("Sontact.html");
									
								}
								
							});
	
						}
					});
					deleteNutzer.setStyleName("deleteNutzer");
					RootPanel.get("content").add(deleteNutzer);
					
				}
				
				
				vp.add(headerPanel);
				vp.add(ownerLb);
	
				RootPanel.get("content").add(vp);
				RootPanel.get("content").add(new ShowEigenschaften(n, selectedKontakt));
				RootPanel.get("content").add(BtnPanel);
				
				RootPanel.get("content").add(datePanel);
				
			}
		});
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt neu erstellt wird
	 */
	public KontaktForm(final Nutzer n) {
		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Neuen Kontakt erstellen</h2>"));

		HorizontalPanel BtnPanel = new HorizontalPanel();

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		BtnPanel.add(quitBtn);

		Button saveBtn = new Button("erstellen");
		saveBtn.addClickHandler(new speichernKontaktClickHandler());

		BtnPanel.add(saveBtn);

		VerticalPanel vp = new VerticalPanel();
		

		vp.add(headerPanel);
		vp.add(new Label("Name des Kontakts:"));
		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

		vp.add(vornameTxtBox);
		vp.add(nachnameTxtBox);
		vp.add(BtnPanel);

		vp.setSpacing(20);
		BtnPanel.setSpacing(20);
		
		RootPanel.get("content").add(vp);

	}

	// ClickHandler
	
	/**
	 * ClickHandler zum Speichern eines neu angelegten Kontakts
	 */
	public class speichernKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer n = new Nutzer();

			// Cookies des Nutzers holen.

			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			if (vornameTxtBox.getText() == "" || nachnameTxtBox.getText() == "") {
				MessageBox.alertWidget("Bitte geben Sie alle Felder an",
						"Vorname: " + vornameTxtBox.getText() + "Nachname: " + nachnameTxtBox.getText());
			} else {

				ev.createKontakt(vornameTxtBox.getText(), nachnameTxtBox.getText(), n, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Void result) {
						RootPanel.get("content").add(new KontaktForm());

						// Refresh der Seite für die Aktualisierug des Baumes.
						Window.Location.reload();

					}

				});
			}
		}

	}
	
	/**
	 * ClickHandler zum teilen von Kontakten.
	 * 
	 * @author Dennis Lehle
	 *
	 */
	private class shareKontaktlisteClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			Kontakt k = selectedKontakt;
			
			
		

		}

	}

	/**
	 * ClickHandler f�r das Updaten eines Kontakts
	 * 
	 * @author Batista
	 *
	 */
	private class updateKontaktClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("content").clear();

			HorizontalPanel BtnPanel = new HorizontalPanel();
			HorizontalPanel headerPanel = new HorizontalPanel();
			HorizontalPanel InfoPanel = new HorizontalPanel();
			headerPanel.add(new HTML("<h2>Kontakt:  <em>" + selectedKontakt.getVorname() + " "
					+ selectedKontakt.getNachname() + "</em> bearbeiten</h2>"));
			InfoPanel.add(new HTML("<h4>Kontaktinformationen:</h4>"));

			
			Button cancelBtn = new Button("abbrechen");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new KontaktForm(selectedKontakt));
				}
			});

			BtnPanel.add(cancelBtn);

			// Instanziierung Button zum Speichern der �nderungen an des selektierten
			// Kontakts
			Button saveBtn = new Button("speichern");
			// ClickHandler f�r das Speichern
			saveBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					selectedKontakt.setVorname(vornameTxtBox.getText());
					selectedKontakt.setNachname(nachnameTxtBox.getText());

					ev.saveKontakt(selectedKontakt, new AsyncCallback<Kontakt>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Kontakt result) {
							
							
//							RootPanel.get("content").add(new KontaktForm(selectedKontakt));
//							Window.Location.reload();

						}

					});

				}
			});

			BtnPanel.add(saveBtn);

			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hpVorname = new HorizontalPanel();
			HorizontalPanel hpNachname = new HorizontalPanel();
			VerticalPanel vpName = new VerticalPanel();

			vp.add(headerPanel);
			vp.add(InfoPanel);
			hpVorname.add(new Label("Vorname: "));
			hpVorname.add(vornameTxtBox);

			hpNachname.add(new Label("Nachname: "));
			hpNachname.add(nachnameTxtBox);
			
			
			ev.getAllAuspraegungenByKontakt(selectedKontakt.getId(), new AsyncCallback<Vector<Auspraegung>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Auspraegung> result) {
					Vector<Auspraegung> auspraegungen = new Vector<>();
					auspraegungen = result;

					for (int i = 0; i < auspraegungen.size(); i++) {

						TextBox auspraegung = new TextBox();
						auspraegung.setText(auspraegungen.elementAt(i).getWert());

						ev.getEigenschaftForAuspraegung(auspraegungen.elementAt(i).getEigenschaftId(),
								new AsyncCallback<Eigenschaft>() {

									@Override
									public void onFailure(Throwable caught) {
										caught.getMessage().toString();

									}

									@Override
									public void onSuccess(Eigenschaft result) {

										TextBox eigenschafttb = new TextBox();
										eigenschafttb.setText(result.getBezeichnung());
										int count = KontaktProfilFelx.getRowCount();
										KontaktProfilFelx.setWidget(count, 0, eigenschafttb);
										KontaktProfilFelx.setWidget(count, 1, auspraegung);
										int count2 = KontaktProfilFelx.getRowCount();
										count = count2 + 1;

									}
								});

					}

				}

			});
			
			
			vpName.add(hpVorname);
			vpName.add(hpNachname);
			
			vp.add(KontaktProfilFelx);
			vp.add(BtnPanel);

			RootPanel.get("content").add(vpName);
			RootPanel.get("content").add(vpName);
			RootPanel.get("content").add(vp);
			
			selectedKontakt.setVorname(vornameTxtBox.getText());
			selectedKontakt.setNachname(nachnameTxtBox.getText());

			BtnPanel.setSpacing(20);
			vp.setSpacing(20);

		}

	}

	public void setSelectedKontakt(Kontakt k) {
		if (k != null) {
			selectedKontakt = k;
			vornameTxtBox.setText(selectedKontakt.getVorname());
			nachnameTxtBox.setText(selectedKontakt.getNachname());

		} else {

			vornameTxtBox.setText("");
			nachnameTxtBox.setText("");

		}

	}

	public void setSontactTreeViewModel(SontactTreeViewModel sontactTreeViewModel) {
		sontactTree = sontactTreeViewModel;
	}
}
