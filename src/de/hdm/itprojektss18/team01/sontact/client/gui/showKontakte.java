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

public class showKontakte {

	private Button createKontaktBtn = new Button("Kontakt erstellen");
	private Button removeKontaktBtn = new Button("Kontakt l\u00f6schen");
	Button searchBtn = new Button("Suche");
	TextBox searchTextBox = new TextBox();

	HorizontalPanel searchPanel = new HorizontalPanel();
	VerticalPanel KontaktPanel = new VerticalPanel();

	void onLoad() {
		searchPanel.add(searchBtn);
		searchPanel.add(searchTextBox);
		this.KontaktPanel.add(searchPanel);
		
		this.KontaktPanel.add(createKontaktBtn);
		this.KontaktPanel.add(removeKontaktBtn);

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

		/**
		 * Panels dem RootPanel hinzufÃ¼gen und in dem div-Bereich "content"
		 * ablegen.
		 */
		RootPanel.get("content").add(KontaktPanel);
		
		

	}

}
