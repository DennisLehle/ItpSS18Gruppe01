package de.hdm.itprojektss18.team01.sontact.client.gui;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class showKontaktliste {
	
	Button klErstellen = new Button("Kontaktliste +");
	Button klLöschen = new Button("Kontaktliste -");
	Button search = new Button("Suche");
	TextBox tb = new TextBox();
	
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel klePanelerstellen = new HorizontalPanel();
	HorizontalPanel klePanellöschen = new HorizontalPanel();
	
	/**
	 * Aufbau der Kontaktliste Seite.
	 */
	void onLoad(){
		
		/**
		 * Ausrichtung der Suche Horizontal.
		 */
		hp.add(search);
		hp.add(tb);
		/**
		 * Einbettung der Buttons mit Horizontaler Ausrichtung.
		 */
		klePanelerstellen.add(klErstellen);
		klePanellöschen.add(klLöschen);
		
		/**
		 * Styling der Buttons.
		 */
		
		klErstellen.setStyleName("ButtonStyleKontaktliste");
		klErstellen.setPixelSize(200, 40);
		klLöschen.setStyleName("ButtonStyleKontaktliste");
		klLöschen.setPixelSize(200, 40);
		
		tb.setStyleName("ButtonStyleSuche");
		search.setStyleName("ButtonStyleSuche");
	
		/**
		 * Panels dem RootPanel hinzufügen und in dem div-Bereich "content" ablegen.
		 */
		RootPanel.get("content").add(hp);
		RootPanel.get("content").add(klePanelerstellen);
		RootPanel.get("content").add(klePanellöschen);
		
		
		
	}

}
