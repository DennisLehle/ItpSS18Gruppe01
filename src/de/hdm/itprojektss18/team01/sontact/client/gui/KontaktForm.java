package de.hdm.itprojektss18.team01.sontact.client.gui;

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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Klasse welche Formulare für Kontaktd darstellt, diese erlauben
 * Interaktionsmöglichkeiten um Kontakte Anzuzeigen, zu Bearbeiten, zu Löschen
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


	public KontaktForm() {
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt bereits vorhanden ist.
	 * 
	 * @param Kontakt
	 */
	public KontaktForm(Kontakt k) {

		this.selectedKontakt = k;
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
				HorizontalPanel BtnPanel = new HorizontalPanel();
				VerticalPanel vp = new VerticalPanel();

				headerPanel.add(new HTML("<h2>Kontakt: <em>" + selectedKontakt.getVorname() + " "
						+ selectedKontakt.getNachname() + "</em></h2>"));

				// Lï¿½sch-Button instanziieren und dem Panel zuweisen
				Button deleteKontaktBtn = new Button("Kontakt lÃ¶schen");
				BtnPanel.add(deleteKontaktBtn);

				// ClickHandler fï¿½r das Lï¿½schen eines Kontakts
				deleteKontaktBtn.addClickHandler(new deleteClickHandler());
				BtnPanel.add(deleteKontaktBtn);

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktBtn = new Button("Kontakt bearbeiten");

				// ClickHandler fï¿½r das Updaten eines Kontakts
				editKontaktBtn.addClickHandler(new updateKontaktClickHandler());
				BtnPanel.add(editKontaktBtn);
				
				// 
				VerticalPanel datePanel = new VerticalPanel();
				
			
				String erstelldate = selectedKontakt.getErstellDat().toString();
				String moddate = selectedKontakt.getModDat().toString();

				datePanel.add(new HTML("<h2>Datum: <em>" + erstelldate + " "
						+ moddate + "</em></h2>"));

				vp.add(headerPanel);
				vp.add(BtnPanel);
				vp.add(datePanel);
				RootPanel.get("content").add(vp);

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

		// Button fÃ¼r den Abbruch der Erstellung.
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
	 * ClickHandler fï¿½r das Lï¿½schen eines Kontakts
	 * 
	 * @author Batista
	 *
	 */
	private class deleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Check, ob Kontakte in der Liste enthalten sind
			ev.getKontaktById(selectedKontakt.getId(), new AsyncCallback<Kontakt>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();
				}

				@Override
				public void onSuccess(Kontakt result) {
					// Wenn Kontakte vorhanden sind...
					Window.confirm("Kontakt: " + selectedKontakt.getVorname() + selectedKontakt.getNachname()
							+ "unwiderruflich lÃ¶schen?");
					{
						loescheKontakt();
					}
				}

				public void loescheKontakt() {
					ev.removeKontakt(selectedKontakt, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable error) {
							error.getMessage().toString();

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Kontakt wurde gelÃ¶scht");
							Window.Location.reload();

						}

					});

				}
			});

		}

	}

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

			ev.createKontakt(vornameTxtBox.getText(), nachnameTxtBox.getText(), n, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Void result) {
					RootPanel.get("content").add(new KontaktForm());

					// Refresh der Seite fÃ¼r die Aktualisierug des Baumes.
					Window.Location.reload();

				}
			});

		}

	}

	/**
	 * ClickHandler fï¿½r das Updaten eines Kontakts
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
			headerPanel.add(new HTML("<h2>Kontakt:  <em>" + selectedKontakt.getVorname() + " "
					+ selectedKontakt.getNachname() + "</em> bearbeiten</h2>"));

			Button cancelBtn = new Button("abbrechen");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new KontaktForm(selectedKontakt));
				}
			});

			BtnPanel.add(cancelBtn);

			// Instanziierung Button zum Speichern der ï¿½nderungen an des selektierten
			// Kontakts
			Button saveBtn = new Button("speichern");
			// ClickHandler fï¿½r das Speichern
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
							RootPanel.get("content").add(new KontaktForm(selectedKontakt));
							Window.Location.reload();

						}

					});

				}
			});

			BtnPanel.add(saveBtn);

			VerticalPanel vp = new VerticalPanel();
			vp.add(headerPanel);
			vp.add(vornameTxtBox);
			vp.add(nachnameTxtBox);
			vp.add(BtnPanel);
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
