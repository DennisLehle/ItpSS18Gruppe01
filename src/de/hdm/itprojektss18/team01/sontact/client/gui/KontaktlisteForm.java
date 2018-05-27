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

/**
 * Klasse welche Formulare für Kontaktlisten darstellt,* diese erlauben
 * Interaktionsmöglichkeiten um Kontaktlisten Anzuzeigen, zu Bearbeiten, Löschen oder
 * Neuanzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktlisteForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontaktliste selectedKontaktliste = null;

	SontactTreeViewModel sontactTree = null;

	TextBox txtBox = new TextBox();

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

				HorizontalPanel headerPanel = new HorizontalPanel();
				HorizontalPanel BtnPanel = new HorizontalPanel();
				VerticalPanel vp = new VerticalPanel();

				headerPanel.add(new HTML("<h2>Kontaktliste: <em>" + selectedKontaktliste.getTitel() + "</em></h2>"));

				// L�sch-Button instanziieren und dem Panel zuweisen
				Button deleteKlBtn = new Button("Kontaktliste löschen");
				BtnPanel.add(deleteKlBtn);

				// ClickHandler f�r das L�schen einer Kontaktliste
				deleteKlBtn.addClickHandler(new deleteClickHandler());
				BtnPanel.add(deleteKlBtn);

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktlisteBtn = new Button("Kontaktliste bearbeiten");

				// ClickHandler f�r das Updaten einer Kontaktliste
				editKontaktlisteBtn.addClickHandler(new updateKontaktlisteClickHandler());
				BtnPanel.add(editKontaktlisteBtn);

				vp.add(headerPanel);
				vp.add(BtnPanel);
				RootPanel.get("content").add(vp);

			}

		});
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn eine Kontaktliste neu erstellt wird
	 */
	public KontaktlisteForm(final Nutzer n) {
		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Neue Kontaktliste erstellen</h2>"));

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
		saveBtn.addClickHandler(new speichernKontaktlisteClickHandler());

		BtnPanel.add(saveBtn);

		VerticalPanel vp = new VerticalPanel();

		vp.add(headerPanel);
		vp.add(new Label("Name der Kontaktliste:"));
		txtBox.getElement().setPropertyString("placeholder", "Titel der Kontaktliste...");
		vp.add(txtBox);
		vp.add(BtnPanel);

		vp.setSpacing(20);
		BtnPanel.setSpacing(20);

		RootPanel.get("content").add(vp);

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
						Window.alert("Die Kontaktliste " + selectedKontaktliste.getTitel() + " enthält " + result.size()
								+ " Kontakt(e). Bitte zuerst alle Kontakte aus der Liste entfernen.");
					} else {
						if (Window.confirm(
								"Kontaktliste: " + selectedKontaktliste.getTitel() + "unwiderruflich löschen?")) {
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
							Window.alert("Kontaktliste wurde gelöscht");
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

			// Cookies des Nutzers holen.

			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			ev.createKontaktliste(txtBox.getText(), n, new AsyncCallback<Kontaktliste>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Kontaktliste result) {
					RootPanel.get("content").add(new KontaktlisteForm(result));

					// Refresh der Seite für die Aktualisierug des Baumes.
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

			HorizontalPanel BtnPanel = new HorizontalPanel();
			HorizontalPanel headerPanel = new HorizontalPanel();
			headerPanel.add(
					new HTML("<h2>Kontaktliste:  <em>" + selectedKontaktliste.getTitel() + "</em> bearbeiten</h2>"));

			Button cancelBtn = new Button("abbrechen");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new KontaktlisteForm(selectedKontaktliste));
				}
			});

			BtnPanel.add(cancelBtn);

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

			BtnPanel.add(saveBtn);

			VerticalPanel vp = new VerticalPanel();
			vp.add(headerPanel);
			txtBox.getElement().setPropertyString("placeholder", "Neuer Titel...");
			vp.add(txtBox);
			vp.add(BtnPanel);
			RootPanel.get("content").add(vp);
			selectedKontaktliste.setTitel(txtBox.getText());

			BtnPanel.setSpacing(20);
			vp.setSpacing(20);

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