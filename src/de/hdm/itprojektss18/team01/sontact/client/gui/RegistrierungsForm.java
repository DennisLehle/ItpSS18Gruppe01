package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Wird aufgerufen wenn Nutzer noch kein eigenes Profil im System besitzt.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class RegistrierungsForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();

	Kontakt k = new Kontakt();
	Nutzer n = new Nutzer();
	

	FlexTable kontaktFlex = new FlexTable();

	ScrollPanel sp = new ScrollPanel();

	public RegistrierungsForm(Nutzer nutzer) {
		k.setId(2);

		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Kontakt erstellen</h2>"));

		// Groesse des ScrollPanels anpassen
		sp.setSize("900px", "400px");

		HorizontalPanel BtnPanel = new HorizontalPanel();

		gmailTb.setText(nutzer.getEmailAddress());
		gmailTb.setEnabled(false);

		// Button addEigenschaftBtn = new Button();


		// Button f�r den Abbruch der Erstellung.

		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		Button weiterBtn = new Button("Weiter");
		weiterBtn.addClickHandler(new kontaktErstellenClickHandler());

		BtnPanel.add(weiterBtn);
		BtnPanel.add(quitBtn);

		this.add(headerPanel);

		this.add(new Label("Name des Kontakts:"));

		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

		this.add(gmailTb);
		this.add(vornameTxtBox);
		this.add(nachnameTxtBox);

		sp.add(kontaktFlex);
		this.add(sp);

		this.setSpacing(20);
		BtnPanel.setSpacing(20);

		this.add(BtnPanel);

	}

	/**
	 * ClickHandler zum Speichern der Kontakteigenschaften
	 */

	private class EigenschaftenSpeichern implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			int count = kontaktFlex.getRowCount();
			
			while(count != 0) {
				
				TextBox eigenschaftTb = new TextBox();
				TextBox auspraegungTb = new TextBox();
				
				eigenschaftTb = (TextBox) kontaktFlex.getWidget(count, 0);
				auspraegungTb = (TextBox) kontaktFlex.getWidget(count, 1);
				count--;
				
				ev.createAuspraegungForNewEigenschaft(eigenschaftTb.getText(), auspraegungTb.getText(), k,
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Void result) {
								Window.alert("passt");

							}
						});
				
			}
			


			RootPanel.get("content").add(new KontaktForm());

		}

	}

	/**
	 * ClickHandler zum Erzeugen von neuen Eigenschafts-Angaben
	 */
	private class eigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TextBox txtBoxEigenschaft = new TextBox();
			txtBoxEigenschaft.getElement().setPropertyString("placeholder", "Eigenschaft bennenen...");
			TextBox txtBoxWert = new TextBox();
			txtBoxWert.getElement().setPropertyString("placeholder", "Wert eingeben...");

			int count = kontaktFlex.getRowCount();
			kontaktFlex.setWidget(count, 0, txtBoxEigenschaft);
			kontaktFlex.setWidget(count, 1, txtBoxWert);
			int count2 = kontaktFlex.getRowCount();
			count = count2 + 1;
			
			VerticalPanel vp = new VerticalPanel();
			
			vp.add(txtBoxEigenschaft);
			vp.add(txtBoxWert);
			
			RootPanel.get("content").add(vp);

		}

	}

	/**
	 * ClickHandler zum Speichern eines neu angelegten Kontakts
	 */
	public class kontaktErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Cookies des Nutzers holen.
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
			
			if (vornameTxtBox.getText().isEmpty() && nachnameTxtBox.getText().isEmpty()) {
				MessageBox.alertWidget("Benachrichtigung", "Bitte mindestens Vor- und Nachname angeben");
			} else {
				ev.createKontaktRegistrierung(vornameTxtBox.getText(), nachnameTxtBox.getText(), n,
						new AsyncCallback<Kontakt>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(Kontakt result) {
								k = result;
								RootPanel.get("content").clear();
								HorizontalPanel headerPanel = new HorizontalPanel();
								headerPanel.add(new HTML("<h2>Kontakteigenschaften angeben</h2>"));

								Button createEigenschaftBtn = new Button("Eigenschaft erstellen");
								HorizontalPanel BtnPanel = new HorizontalPanel();
								createEigenschaftBtn.addClickHandler(new eigenschaftClickHandler());
								BtnPanel.add(createEigenschaftBtn);
								Button speichernBtn = new Button("speichern");
								speichernBtn.addClickHandler(new EigenschaftenSpeichern());
								BtnPanel.add(speichernBtn);
								
								RootPanel.get("content").add(headerPanel);
								RootPanel.get("content").add(BtnPanel);


							}

						});

			}

			

		}
	}

}
