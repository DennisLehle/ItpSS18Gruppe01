package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Wird aufgerufen wenn Nutzer noch kein eigenes Profil im System besitzt.
 * 
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

	private HorizontalPanel hpvorname = new HorizontalPanel();
	private HorizontalPanel hpnachnahme = new HorizontalPanel();

	public RegistrierungsFormular(final Nutzer n) {
		this.add(titel);
		hpvorname.add(vorname);
		hpvorname.add(vornameTb);
		hpnachnahme.add(nachname);
		hpnachnahme.add(nachnameTb);
		this.add(hpvorname);
		this.add(hpvorname);

	}

	public void ladeFesteEigenschaften() {
		//
	}

}
