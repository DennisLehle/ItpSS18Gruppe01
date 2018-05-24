package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Navigation der GUI f�r einezlnen Sektionen Kontakt, Kontaktliste etc.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Navigation extends VerticalPanel {

	Button neueKontaktliste = new Button(
			"<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> Kontaktliste");

	/**
	 * Konstruktor der Navigations-Klasse.
	 */
	public Navigation(final Nutzer nutzer) {

		/**
		 * Anlegen des Baumes für die Navigation in Kontaktlisten.
		 */
		SontactTreeViewModel navTreeModel = new SontactTreeViewModel(nutzer);
		CellTree navTree = new CellTree(navTreeModel, null);

		neueKontaktliste.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Hier kommt das Kontaktlisten Formular hinein...Welches aufgerufen wird wenn
				// man eine neue Kontaktliste erstellen möchte.
			}

		});

		this.add(neueKontaktliste);
		this.add(navTree);
	}

}
