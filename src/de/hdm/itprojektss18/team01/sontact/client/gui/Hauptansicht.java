package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Herzst�ck der Pr�sentationsschicht, welche die Hauptansicht darstellen soll. 
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Hauptansicht extends VerticalPanel{
	Navigation nav;
	
	
	public Hauptansicht(final Nutzer nutzer) {
		/**
		 * Zuweisung der Navigation der Hauptansicht. 
		 */
		Navigation nav = new Navigation(nutzer);
		this.clear(); 
		this.add(nav);
		RootPanel.get("navigator").add(this);
		RootPanel.get("content").add(new showKontakte());

	}
}




