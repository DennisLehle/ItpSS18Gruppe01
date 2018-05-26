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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

public class KontaktlisteForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontaktliste selectedKontaktliste = null;

	SontactTreeViewModel sontactTree = null;
	Label titel = new Label("Kontaktliste: ");
	TextBox txtBox = new TextBox();

	HorizontalPanel hpBtnPanel = new HorizontalPanel();

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste bereits vorhanden
	 * ist.
	 * 
	 * @param Kontaktliste
	 */
	public KontaktlisteForm(Kontaktliste kl) {
		
		this.selectedKontaktliste = kl;
		RootPanel.get("content").clear();

		ev.getKontaktlisteById(kl.getId(), new AsyncCallback<Kontaktliste>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Hopala");
			}

			@Override
			public void onSuccess(Kontaktliste result) {
				selectedKontaktliste = result;

				HorizontalPanel headPanel = new HorizontalPanel();
				headPanel.add(new HTML("<h2>Kontaktliste: <em>" + selectedKontaktliste.getTitel() + "</em></h2>"));
				
				if(selectedKontaktliste != null) {
					RootPanel.get("content").add(new HTML("<div class='info'><p><span class='fa fa-info-circle'></span>"
							+ " Das ist die Kontaktliste... </p></div>"));
				} else {
					//Wenn keine Kontaktliste ausgewählt wird, wird Window neu geladen.
					Window.alert("leider hast du keine Kontaktliste ausgewählt.");
					Window.Location.reload();
				}

				// L�sch-Button instanziieren und dem Panel zuweisen
				Button deleteKlBtn = new Button("Kontaktliste löschen");
				headPanel.add(deleteKlBtn);
				
				// ClickHandler f�r das L�schen einer Kontaktliste
				deleteKlBtn.addClickHandler(new deleteClickHandler());
				headPanel.add(deleteKlBtn);
			
				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktlisteBtn = new Button("Kontaktliste bearbeiten");
				
				// ClickHandler f�r das Updaten einer Kontaktliste
				editKontaktlisteBtn.addClickHandler(new updateKontaktlisteClickHandler());
				headPanel.add(editKontaktlisteBtn);
				RootPanel.get("content").add(headPanel);

			}

		});
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste neu erstellt wird
	 */
	public KontaktlisteForm(final Nutzer n) {
		//RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();
		
		HorizontalPanel headerPanel = new HorizontalPanel();	
		headerPanel.add(new Label("Neue Kontaktliste erstellen"));

		//Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		headerPanel.add(quitBtn);

		Button saveBtn = new Button("Neue Kontaktliste +");
		saveBtn.addClickHandler(new speichernKontaktlisteClickHandler());
		headerPanel.add(saveBtn);

		RootPanel.get("content").add(headerPanel);

		RootPanel.get("contentHeader").add(new HTML("<h2>Name der Kontaktliste</h2>"));

		RootPanel.get("content").add(txtBox);

	}

	// ClickHandler

	/**
	 * ClickHandler f�r das L�schen einer Kontaktliste
	 * 
	 * @author Batista
	 *
	 */
	private class deleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Check, ob Kontakte in der Liste enthalten sind
			ev.getKontakteByKontaktliste(selectedKontaktliste.getId(), new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();
				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					// Wenn Kontakte vorhanden sind...
					if (result.size() > 0) {
						Window.alert("Die Kontaktliste " + selectedKontaktliste.getTitel() + " enth�lt " + result.size()
								+ " Kontakt(e). Bitte zuerst alle Kontakte aus der Liste entfernen.");
					} else {
						if (Window.confirm(
								"M�chten Sie die Kontaktliste " + selectedKontaktliste.getTitel() + "l�schen?")) {
							loescheKontaktliste();
						}
					}
				}

				public void loescheKontaktliste() {
					ev.deleteKontaktliste(selectedKontaktliste, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable error) {
							error.getMessage().toString();

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Kontaktliste wurde gel�scht");
							Window.Location.reload();

						}

					});

				}
			});

		}

	}

	/**
	 * ClickHandler zum Sperichern einer neu angelegten Kontaktliste
	 */
	public class speichernKontaktlisteClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			Nutzer n = new Nutzer();
			
			//Cookies des Nutzers holen.
			
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
		
			ev.createKontaktliste(txtBox.getText(),n , new AsyncCallback<Kontaktliste>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Kontaktliste result) {
					RootPanel.get("content").add(new KontaktlisteForm(result));
					
					//Refresh der Seite für die Aktualisierug des Baumes.
					Window.Location.reload();

				}
			});
			
		}

	}

	/**
	 * ClickHandler f�r das Updaten einer Kontaktliste
	 * 
	 * @author Batista
	 *
	 */
	private class updateKontaktlisteClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			
			RootPanel.get("content").clear();
			
			HorizontalPanel headerPanel = new HorizontalPanel();
			headerPanel.add(new HTML("<h2>Kontaktliste:  <em>" + selectedKontaktliste.getTitel()+ "</em> bearbeiten</h2>"));

			Button cancelBtn = new Button("abbrechen");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));
				}
			});

			headerPanel.add(cancelBtn);

			// Instanziierung Button zum Speichern der �nderungen an der selektierten
			// Kontaktliste
			Button saveBtn = new Button("speichern");
			// ClickHandler f�r das Speichern
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
							RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));
							Window.Location.reload();
							
						}
					
					});
					
				}
			});
			
			headerPanel.add(saveBtn);
			RootPanel.get("content").add(headerPanel);
			
			txtBox.setText(selectedKontaktliste.getTitel());
			RootPanel.get("content").add(txtBox);
			selectedKontaktliste.setTitel(txtBox.getValue());
			
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
