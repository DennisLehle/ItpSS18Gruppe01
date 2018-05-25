package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Wird aufgerufen wenn Nutzer noch kein eigenes Profil im System besitzt.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class RegistrierungsFormular extends Composite {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private Label titel = new Label("Nutzerkontakt anlegen");

	private Label vorname = new Label("Vorname:");
	private TextBox vornameTb = new TextBox();
	private Label nachname = new Label("Nachname:");
	private TextBox nachnameTb = new TextBox();
	private Label gmail = new Label("Gmail-Adresse:");
	private TextBox gmailTb = new TextBox();
	private Button speichernBtn = new Button("Speichern");

	private Button auswahlEigenschaftBtn = new Button("Auswahleigenschaft");
	private Button eigeneEigenschaftBtn = new Button("Eigene Eigenschaft hinzufuegen");

	private VerticalPanel hauptPanel = new VerticalPanel();
	private HorizontalPanel ButtonsPanel = new HorizontalPanel();
	
	private HorizontalPanel EigenschaftsMenuPanel = new HorizontalPanel();
	
	
	FlexTable mindestEigenschaftsTable = new FlexTable();
	FlexTable eigeneEigenschaftFlex = new FlexTable();
	FlexTable auswahlEigenschaftFlex = new FlexTable();
	

	public RegistrierungsFormular(Nutzer nutzer) {
		initWidget(hauptPanel);
		ButtonsPanel.add(auswahlEigenschaftBtn);
		ButtonsPanel.add(eigeneEigenschaftBtn);
		ButtonsPanel.add(speichernBtn);
		ButtonsPanel.setSpacing(20);
		hauptPanel.add(ButtonsPanel);
		
		EigenschaftsMenuPanel.setSpacing(15);
		EigenschaftsMenuPanel.setBorderWidth(5);
		hauptPanel.add(EigenschaftsMenuPanel);

		

		// Methode welche die Pflichtangaben lädt
		ladePflichtEigenschaften(mindestEigenschaftsTable, EigenschaftsMenuPanel);
		
	
		gmailTb.setText(nutzer.getEmailAddress());
		gmailTb.setEnabled(false);

		vornameTb.setFocus(true);
		nachnameTb.setFocus(true);

		speichernBtn.setPixelSize(160, 40);
		auswahlEigenschaftBtn.setPixelSize(160, 40);
		eigeneEigenschaftBtn.setPixelSize(160, 40);

		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(titel);

		/**
		 * ClickHandler zum Erzeugen von Auswwahleigenschaften welche aus einer ListBox
		 * selektiert werden können.
		 */
		auswahlEigenschaftBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TextBox txtBox = new TextBox();
				final ListBox listBox = new ListBox();
				listBox.getElement().setPropertyString("placeholder", "Eigenschaft auswählen");
				ev.getEigenschaftAuswahl(new AsyncCallback<Vector<Eigenschaft>>() {

					@Override
					public void onFailure(Throwable error) {
						error.getMessage().toString();

					}

					@Override
					public void onSuccess(Vector<Eigenschaft> result) {
						if (result != null) {
							for (int i = 0; i < result.size(); i++) {
								listBox.addItem(result.elementAt(i).getBezeichnung());
							}
						}

					}

				});
				
				platziereAuswahlEigenschaften(listBox, txtBox, auswahlEigenschaftFlex, EigenschaftsMenuPanel);
							
			}
			
		});

		/**
		 * ClickHandler welches es ermöglicht eigene Eigenschaften hinzuzufügen.
		 */
		eigeneEigenschaftBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TextBox txtBoxEigenschaft = new TextBox();
				TextBox txtBoxWert = new TextBox();

				platziereEigeneEigenschaften(txtBoxEigenschaft, txtBoxWert, eigeneEigenschaftFlex, EigenschaftsMenuPanel);
				

			}
			
		});

		/**
		 * ClickHandler zum Speichern des neu angelegten Nutzer-Kontakts.
		 */
		speichernBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String vorname = vornameTb.getText();
				String nachname = nachnameTb.getText();
				if (!vornameTb.getText().isEmpty() && !nachnameTb.getText().isEmpty()) {
					ev.createKontaktRegistrierung(vorname, nachname, nutzer, new AsyncCallback<Kontakt>() {

						@Override
						public void onFailure(Throwable error) {
							error.getMessage().toString();
						}

						@Override
						public void onSuccess(Kontakt k) {
							MessageBox.alertWidget("Benachrichtigung: ",
									"Sie haben den Kontakt " + vorname + " " + nachname + " erfolgreich angelegt");
						}
					});

				} else {
					MessageBox.alertWidget("Benachrichtigung: ", "Vor- und Nachname sind Pflichtfelder");
				}

			}

		});

	}

	public void platziereAuswahlEigenschaften(ListBox listBox, TextBox txtBox, FlexTable zusatzEigenschaftFlex, HorizontalPanel EigenschaftsMenuPanel) {

		listBox.getElement().setPropertyString("placeholder", "Eigenschaft auswählen");
		txtBox.getElement().setPropertyString("placeholder", "Wert der Eigenschaft");
		int count = zusatzEigenschaftFlex.getRowCount();
		zusatzEigenschaftFlex.setWidget(count + 1, 0, listBox);
		zusatzEigenschaftFlex.setWidget(count + 2, 0, txtBox);
		//zusatzEigenschaftFlex.setBorderWidth(2);
		EigenschaftsMenuPanel.add(zusatzEigenschaftFlex);

	}

	public void platziereEigeneEigenschaften(TextBox txtBoxEigenschaft, TextBox txtBoxWert,
			FlexTable eigeneEigenschaftFlex, HorizontalPanel EigenschaftsMenuPanel ) {

		txtBoxEigenschaft.getElement().setPropertyString("placeholder", "Name der Eigenschaft");
		txtBoxWert.getElement().setPropertyString("placeholder", "Wert der Eigenschaft");
		txtBoxEigenschaft.setFocus(true);
		txtBoxWert.setFocus(true);

		int count = eigeneEigenschaftFlex.getRowCount();
		eigeneEigenschaftFlex.setWidget(count + 1, 0, txtBoxEigenschaft);
		eigeneEigenschaftFlex.setWidget(count + 2, 0, txtBoxWert);
		//eigeneEigenschaftFlex.setBorderWidth(2);
		EigenschaftsMenuPanel.add(eigeneEigenschaftFlex);

	}

	public void ladePflichtEigenschaften(FlexTable mindestEigenschaftsTable, HorizontalPanel EigenschaftsMenuPanel) {
		int count = mindestEigenschaftsTable.getRowCount();
		mindestEigenschaftsTable.setWidget(count + 1, 0, vorname);
		mindestEigenschaftsTable.setWidget(count + 2, 0, vornameTb);
		mindestEigenschaftsTable.setWidget(count + 3, 0, nachname);
		mindestEigenschaftsTable.setWidget(count + 4, 0, nachnameTb);
		mindestEigenschaftsTable.setWidget(count + 5, 0, gmail);
		mindestEigenschaftsTable.setWidget(count + 6, 0, gmailTb);
		//mindestEigenschaftsTable.setBorderWidth(2);
		EigenschaftsMenuPanel.add(mindestEigenschaftsTable);
		
		

	}

}