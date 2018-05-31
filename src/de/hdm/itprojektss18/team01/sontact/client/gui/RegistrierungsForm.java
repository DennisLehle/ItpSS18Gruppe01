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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Wird aufgerufen wenn Nutzer noch kein eigenes Profil im System besitzt.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class RegistrierungsForm extends VerticalPanel {

	// private Label titel = new Label("Nutzerkontakt anlegen");
	//
	// private Label vorname = new Label("Vorname:");
	// private TextBox vornameTb = new TextBox();
	// private Label nachname = new Label("Nachname:");
	// private TextBox nachnameTb = new TextBox();
	// private TextBox gmailTb = new TextBox();
	//
	// private Label gmail = new Label("Gmail-Adresse:");
	//
	// private Button speichernBtn = new Button("Speichern");
	//
	// private Button auswahlEigenschaftBtn = new Button("Auswahleigenschaft");
	// private Button eigeneEigenschaftBtn = new Button("Eigene Eigenschaft
	// hinzufuegen");
	//
	// private VerticalPanel hauptPanel = new VerticalPanel();
	// private HorizontalPanel ButtonsPanel = new HorizontalPanel();
	//
	// private HorizontalPanel EigenschaftsMenuPanel = new HorizontalPanel();
	//
	// FlexTable mindestEigenschaftsTable = new FlexTable();
	// FlexTable eigeneEigenschaftFlex = new FlexTable();
	// FlexTable auswahlEigenschaftFlex = new FlexTable();

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();

	Grid kontaktGrid = new Grid();
	FlexTable kontaktFlex = new FlexTable();

	ScrollPanel sp = new ScrollPanel();

	public RegistrierungsForm(Nutzer nutzer) {

		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Kontakt erstellen</h2>"));

		// Groesse des ScrollPanels anpassen
		sp.setSize("900px", "400px");

		HorizontalPanel BtnPanel = new HorizontalPanel();

		gmailTb.setText(nutzer.getEmailAddress());
		gmailTb.setEnabled(false);

		//Button addEigenschaftBtn = new Button();
		
		Button createEigenschaftBtn = new Button("Eigenschaft erstellen");

		createEigenschaftBtn.addClickHandler(new eigenschaftClickHandler());

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		Button saveBtn = new Button("erstellen");
		saveBtn.addClickHandler(new kontaktErstellenClickHandler());

		BtnPanel.add(saveBtn);
		BtnPanel.add(quitBtn);
		BtnPanel.add(createEigenschaftBtn);

		this.add(headerPanel);

		this.add(new Label("Name des Kontakts:"));

		//vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		//nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

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

		}

	}

	/**
	 * ClickHandler zum Speichern eines neu angelegten Kontakts
	 */
	public class kontaktErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer n = new Nutzer();
			// Cookies des Nutzers holen.
			
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			if (!vornameTxtBox.getText().isEmpty() && !nachnameTxtBox.getText().isEmpty()) {
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
								Kontakt k = result;
								int count = kontaktFlex.getRowCount();

								while (count != 0) {
									TextBox eigenschaftTB = new TextBox();
									eigenschaftTB = (TextBox) kontaktFlex.getWidget(count, 1);

									ev.createEigenschaft(eigenschaftTB.getText(), new AsyncCallback<Eigenschaft>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Fail");

										}

										@Override
										public void onSuccess(Eigenschaft result) {
											TextBox auspraegungTB = new TextBox();
											auspraegungTB = (TextBox) kontaktFlex.getWidget(count, 2);
											ev.createAuspraegung(auspraegungTB.getText(), result.getId(), k.getId(), n,
													new AsyncCallback<Auspraegung>() {

														@Override
														public void onFailure(Throwable caught) {
															Window.alert("Fail II");

														}

														@Override
														public void onSuccess(Auspraegung result) {
															kontaktFlex.removeRow(count);
														}

													});

										}

									});

								}

								RootPanel.get("content").add(new KontaktForm());

							}

						});
			}
		}

	}

}
// initWidget(hauptPanel);
// ButtonsPanel.add(auswahlEigenschaftBtn);
// ButtonsPanel.add(eigeneEigenschaftBtn);
// ButtonsPanel.add(speichernBtn);
// ButtonsPanel.setSpacing(20);
// hauptPanel.add(ButtonsPanel);
//
// EigenschaftsMenuPanel.setSpacing(15);
// EigenschaftsMenuPanel.setBorderWidth(5);
// hauptPanel.add(EigenschaftsMenuPanel);
//
// // Methode welche die Pflichtangaben lädt
// ladePflichtEigenschaften(mindestEigenschaftsTable, EigenschaftsMenuPanel);
//
// gmailTb.setText(nutzer.getEmailAddress());
// gmailTb.setEnabled(false);
//
// vornameTb.setFocus(true);
// nachnameTb.setFocus(true);
//
// speichernBtn.setPixelSize(160, 40);
// auswahlEigenschaftBtn.setPixelSize(160, 40);
// eigeneEigenschaftBtn.setPixelSize(160, 40);
//
// RootPanel.get("contentHeader").clear();
// RootPanel.get("contentHeader").add(titel);
//
// /**
// * ClickHandler zum Erzeugen von Auswwahleigenschaften welche aus einer
// ListBox
// * selektiert werden können.
// */
// auswahlEigenschaftBtn.addClickHandler(new ClickHandler() {
//
// @Override
// public void onClick(ClickEvent event) {
// TextBox txtBox = new TextBox();
// final ListBox listBox = new ListBox();
// listBox.getElement().setPropertyString("placeholder", "Eigenschaft
// auswählen");
// ev.getEigenschaftAuswahl(new AsyncCallback<Vector<Eigenschaft>>() {
//
// @Override
// public void onFailure(Throwable error) {
// error.getMessage().toString();
//
// }
//
// @Override
// public void onSuccess(Vector<Eigenschaft> result) {
// if (result != null) {
// for (int i = 0; i < result.size(); i++) {
// listBox.addItem(result.elementAt(i).getBezeichnung());
// }
// }
//
// }
//
// });
//
// platziereAuswahlEigenschaften(listBox, txtBox, auswahlEigenschaftFlex,
// EigenschaftsMenuPanel);
//
// }
//
// });
//
// /**
// * ClickHandler welches es ermöglicht eigene Eigenschaften hinzuzufügen.
// */
// eigeneEigenschaftBtn.addClickHandler(new ClickHandler() {
//
// @Override
// public void onClick(ClickEvent event) {
// TextBox txtBoxEigenschaft = new TextBox();
// TextBox txtBoxWert = new TextBox();
//
// platziereEigeneEigenschaften(txtBoxEigenschaft, txtBoxWert,
// eigeneEigenschaftFlex,
// EigenschaftsMenuPanel);
//
// }
//
// });
//
// /**
// * ClickHandler zum Speichern des neu angelegten Nutzer-Kontakts.
// */
// speichernBtn.addClickHandler(new ClickHandler() {
//
// @Override
// public void onClick(ClickEvent event) {
// String vorname = vornameTb.getText();
// String nachname = nachnameTb.getText();
// if (!vornameTb.getText().isEmpty() && !nachnameTb.getText().isEmpty()) {
// ev.createKontaktRegistrierung(vorname, nachname, nutzer, new
// AsyncCallback<Kontakt>() {
//
// @Override
// public void onFailure(Throwable error) {
// error.getMessage().toString();
// }
//
// @Override
// public void onSuccess(Kontakt k) {
// Window.Location.reload();
// }
// });
//
// } else {
// MessageBox.alertWidget("Benachrichtigung: ", "Vor- und Nachname sind
// Pflichtfelder");
// }
//
// }
//
// });
//
// }
//
// public void platziereAuswahlEigenschaften(ListBox listBox, TextBox txtBox,
// FlexTable zusatzEigenschaftFlex,
// HorizontalPanel EigenschaftsMenuPanel) {
//
// listBox.getElement().setPropertyString("placeholder", "Eigenschaft
// auswählen");
// txtBox.getElement().setPropertyString("placeholder", "Wert der Eigenschaft");
// int count = zusatzEigenschaftFlex.getRowCount();
// zusatzEigenschaftFlex.setWidget(count + 1, 0, listBox);
// zusatzEigenschaftFlex.setWidget(count + 2, 0, txtBox);
// // zusatzEigenschaftFlex.setBorderWidth(2);
// EigenschaftsMenuPanel.add(zusatzEigenschaftFlex);
//
// }
//
// public void platziereEigeneEigenschaften(TextBox txtBoxEigenschaft, TextBox
// txtBoxWert,
// FlexTable eigeneEigenschaftFlex, HorizontalPanel EigenschaftsMenuPanel) {
//
// txtBoxEigenschaft.getElement().setPropertyString("placeholder", "Name der
// Eigenschaft");
// txtBoxWert.getElement().setPropertyString("placeholder", "Wert der
// Eigenschaft");
// txtBoxEigenschaft.setFocus(true);
// txtBoxWert.setFocus(true);
//
// int count = eigeneEigenschaftFlex.getRowCount();
// eigeneEigenschaftFlex.setWidget(count + 1, 0, txtBoxEigenschaft);
// eigeneEigenschaftFlex.setWidget(count + 2, 0, txtBoxWert);
// // eigeneEigenschaftFlex.setBorderWidth(2);
// EigenschaftsMenuPanel.add(eigeneEigenschaftFlex);
//
// }
//
// public void ladePflichtEigenschaften(FlexTable mindestEigenschaftsTable,
// HorizontalPanel EigenschaftsMenuPanel) {
// int count = mindestEigenschaftsTable.getRowCount();
// mindestEigenschaftsTable.setWidget(count + 1, 0, vorname);
// mindestEigenschaftsTable.setWidget(count + 2, 0, vornameTb);
// mindestEigenschaftsTable.setWidget(count + 3, 0, nachname);
// mindestEigenschaftsTable.setWidget(count + 4, 0, nachnameTb);
// mindestEigenschaftsTable.setWidget(count + 5, 0, gmail);
// mindestEigenschaftsTable.setWidget(count + 6, 0, gmailTb);
// // mindestEigenschaftsTable.setBorderWidth(2);
// EigenschaftsMenuPanel.add(mindestEigenschaftsTable);
//
// }
