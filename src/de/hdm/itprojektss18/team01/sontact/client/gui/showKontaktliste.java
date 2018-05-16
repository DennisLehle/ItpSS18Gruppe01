package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Die Klasse stellt Interaktionsmöglichkeiten um Kontakte zu verwalten bereit
 * und soll vorhandene Kontakte anzeigen.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class showKontaktliste extends VerticalPanel {

	Button createListBtn = new Button("Kontaktliste erstellen");
	Button removeListBtn = new Button("Kontaktliste entfernen");
	Button searchBtn = new Button("Suche");
	TextBox tb = new TextBox();

	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel addListPanel = new HorizontalPanel();
	HorizontalPanel removeListPanel = new HorizontalPanel();

	/**
	 * Aufbau der Kontaktliste Seite.
	 */
	public showKontaktliste() {

		/**
		 * Ausrichtung der Suche Horizontal.
		 */
		hp.add(searchBtn);
		hp.add(tb);
		/**
		 * Einbettung der Buttons mit Horizontaler Ausrichtung.
		 */
		addListPanel.add(createListBtn);
		removeListPanel.add(removeListBtn);

		/**
		 * Styling der Buttons.
		 */

		createListBtn.setStyleName("ButtonStyleKontaktliste");
		createListBtn.setPixelSize(200, 40);
		removeListBtn.setStyleName("ButtonStyleKontaktliste");
		removeListBtn.setPixelSize(200, 40);

		tb.setStyleName("ButtonStyleSuche");
		searchBtn.setStyleName("ButtonStyleSuche");

		/**
		 * Panels dem RootPanel hinzufÃ¼gen und in dem div-Bereich "content" ablegen.
		 */
		RootPanel.get("content").add(hp);
		RootPanel.get("content").add(addListPanel);
		RootPanel.get("content").add(removeListPanel);

	}

}
