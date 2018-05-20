package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itprojektss18.team01.sontact.client.gui.MessageBox;

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

public class RegistrierungsFormular extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private Label titel = new Label("Eigenen Kontakt anlegen");

	private Label vorname = new Label("Vorname");
	private TextBox vornameTb = new TextBox();
	private Label nachname = new Label("Nachname");
	private TextBox nachnameTb = new TextBox();
	private Button speichern = new Button("Speichern");

	private Button auswahlEigenschaft = new Button("Auswahleigenschaft");
	private Button eigeneEigenschaft = new Button("Eigene Eigenschaft hinzufuegen");

	private HorizontalPanel hpvorname = new HorizontalPanel();
	private HorizontalPanel hpnachnahme = new HorizontalPanel();

	public RegistrierungsFormular(final Nutzer nutzer) {
		this.add(titel);
		hpvorname.add(vorname);
		hpvorname.add(vornameTb);
		hpnachnahme.add(nachname);
		hpnachnahme.add(nachnameTb);
		this.add(hpvorname);
		this.add(hpnachnahme);

		this.add(speichern);
		this.add(auswahlEigenschaft);
		this.add(eigeneEigenschaft);

		auswahlEigenschaft.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TextBox txtBox = new TextBox();
				final ListBox listBox = new ListBox();
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

				HorizontalPanel auswahlPanel = new HorizontalPanel();
				auswahlPanel.add(listBox);
				auswahlPanel.add(txtBox);
				RootPanel.get("content").add(auswahlPanel);
			}
		});

		eigeneEigenschaft.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TextBox txtBox = new TextBox();
				TextBox textBox = new TextBox();

				HorizontalPanel eigenesPanel = new HorizontalPanel();
				eigenesPanel.add(txtBox);
				eigenesPanel.add(textBox);
				RootPanel.get("content").add(eigenesPanel);
				// Schauen nach Regel für die Befüllung der Boxen (Box muss erst befüllt sein
				// damit die nächste Box erscheint)
			}

		});

		speichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RegistrierungsFormular rf = new RegistrierungsFormular(nutzer);
			//	String vorname = rf.vornameTb.getText();
				//String nachname = rf.nachnameTb.getText();
				ev.createKontakt(rf.vornameTb.getText(), rf.nachnameTb.getText(), nutzer, new AsyncCallback<Kontakt>() {

					@Override
					public void onFailure(Throwable error) {
						error.getMessage().toString();

					}

					@Override
					public void onSuccess(Kontakt k) {
						MessageBox.alertWidget("",
								"Sie haben Ihren Kontakt: " + vorname + " " + nachname + " erfolgreich angelegt");

					}
				});
			}

		});

	}

}