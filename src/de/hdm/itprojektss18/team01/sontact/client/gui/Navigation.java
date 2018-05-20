package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Navigation der GUI f�r einezlnen Sektionen Kontakt, Kontaktliste etc.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Navigation extends VerticalPanel {

	Button ownerbtn = new Button(
			"<image src='/images/user.png' width='20px' height='20px' align='center' /> Eigener Kontakt");
	Button kbtn = new Button("<image src='/images/kontakte.png' width='20px' height='20px' align='center' /> Kontakte");
	Button klbtn = new Button(
			"<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> Kontaktliste");
	Button logOutButton = new Button(
			"<image src='/images/logout.png' width='20px' height='20px' align='center' /> Abmelden");
	Button rpbtn = new Button(
			"<image src='/images/report.png' width='20px' height='20px' align='center' /> Report Generator");

	public Navigation(final Nutzer nutzer) {

		/**
		 * Styling der Buttons
		 */
		kbtn.setStyleName("ButtonStyle");
		klbtn.setStyleName("ButtonStyle");
		rpbtn.setStyleName("ButtonStyle");
		ownerbtn.setStyleName("ButtonStyle");
		logOutButton.setStyleName("ButtonStyle");
		kbtn.setPixelSize(150, 40);
		klbtn.setPixelSize(150, 40);
		rpbtn.setPixelSize(150, 40);
		logOutButton.setPixelSize(150, 40);
		ownerbtn.setPixelSize(150, 40);

		/**
		 * Button zur Anzeige der Navigation anheften
		 */
		this.add(ownerbtn);
		this.add(kbtn);
		this.add(klbtn);
		this.add(logOutButton);
		this.add(rpbtn);

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

		/**
		 * ClickHandler für den ReportGenerator Button.
		 */
		rpbtn.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();

			}
		});

	}
}