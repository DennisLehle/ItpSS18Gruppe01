package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Herzst�ck der Pr�sentationsschicht, welche die Hauptansicht darstellen soll. 
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Hauptansicht extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	Navigation nav;
	
	
	public Hauptansicht() {
		/**
		 * Zuweisung der Navigation der Hauptansicht. 
		 */
		Navigation nav = new Navigation();
		vPanel.clear(); 
		vPanel.add(nav);
		RootPanel.get("navigator").add(vPanel);
	}
}




