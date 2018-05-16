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

public class showKontakte extends VerticalPanel {

	private Button createKontaktBtn = new Button("Kontakt erstellen");
	private Button removeKontaktBtn = new Button("Kontakt l\u00f6schen");
	Button searchBtn = new Button("Suche");
	TextBox searchTextBox = new TextBox();

	HorizontalPanel searchPanel = new HorizontalPanel();

	public showKontakte() {

		searchPanel.add(searchBtn);
		searchPanel.add(searchTextBox);

		this.add(searchPanel);
		this.add(createKontaktBtn);
		this.add(removeKontaktBtn);

		/**
		 * Styling der Buttons
		 */
		createKontaktBtn.setStyleName("ButtonStyleKontakte");
		createKontaktBtn.setPixelSize(200, 40);
		removeKontaktBtn.setStyleName("ButtonStyleKontakte");
		removeKontaktBtn.setPixelSize(200, 40);
		searchBtn.setStyleName("ButtonStyleSuche");

		// TextBox stylen...
		searchTextBox.setStyleName("ButtonStyleSuche");

	}

}
