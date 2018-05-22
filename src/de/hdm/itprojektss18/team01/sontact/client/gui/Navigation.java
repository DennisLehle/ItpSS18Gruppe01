package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Navigation der GUI f�r einezlnen Sektionen Kontakt, Kontaktliste etc.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Navigation extends VerticalPanel {
	

	Button kbtn = new Button("<image src='/images/kontakte.png' width='20px' height='20px' align='center' /> Kontakte");
	Button klbtn = new Button(
			"<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> Kontaktliste");

	public Navigation(final Nutzer nutzer) {

		/**
		 * Styling der Buttons
		 */
		kbtn.setStyleName("ButtonStyle");
		klbtn.setStyleName("ButtonStyle");
		
		kbtn.setPixelSize(150, 40);
		klbtn.setPixelSize(150, 40);
		
		/**
		 * Button zur Anzeige der Navigation anheften
		 */
		
		this.add(kbtn);
		this.add(klbtn);
	

		/**
		 * ClickHandler für den "Meine Kontaktliste" Button.
		 */
		klbtn.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
				RootPanel.get("content").add(new ShowKontaktliste(nutzer));


			}
		});

		/**
		 * ClickHandler für den "Meine Kontakte" Button.
		 * 
		 * @param anonymen
		 *            inneren Klasse (Instanz) welche das ClickHandler Interface
		 *            implementiert.
		 */
		kbtn.addClickHandler(new ClickHandler() {
			/**
			 * Abstrakte Methode des Interfaces wird implementiert! Durch den Klick auf den
			 * Button, sollen weitere Interaktionsm�glichkeiten f�r die Verwaltung von
			 * Kontakten angezeigt werden. Daf�r wird erst den Content geleert und
			 * anschlie�end geladen.
			 */
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new ShowKontakte(nutzer));

			}
		});


	}
}