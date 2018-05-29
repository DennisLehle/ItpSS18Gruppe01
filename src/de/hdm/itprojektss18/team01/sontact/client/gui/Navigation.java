package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Navigation der GUI f�r einezlnen Sektionen Kontakt, Kontaktliste etc.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Navigation extends VerticalPanel {
	
	Label kontaktlisteLbl = new Label("Meine Kontaktlisten:");
	Button neueKontaktlisteBtn = new Button(
			"<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> Kontaktliste");
	
	Button neuerKontaktBtn = new Button("<image src='/images/add-contacts.png' width='20px' height='20px' align='center' /> Kontaktliste");
	/**
	 * Konstruktor der Navigations-Klasse.
	 */
	public Navigation(final Nutzer nutzer) {

		/**
		 * ScrollPanel für den Baum.
		 */
		ScrollPanel sc = new ScrollPanel();
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);
		
		
		/**
		 * Anlegen des Baumes für die Navigation in Kontaktlisten.
		 */
		SontactTreeViewModel navTreeModel = new SontactTreeViewModel(nutzer);
		CellTree navTree = new CellTree(navTreeModel, null);

		neueKontaktlisteBtn.addClickHandler(new ClickHandler() {


			@Override
			public void onClick(ClickEvent event) {
				//Wird instanziiert wenn eine neue Kontaktliste erstellt werden soll.
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new KontaktlisteForm(nutzer));
			}

		});
		

		neueKontaktlisteBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new KontaktForm(nutzer));
				
				
			}
		});
		sc.add(navTree);

		this.add(kontaktlisteLbl);
		this.add(neueKontaktlisteBtn);
		this.add(neuerKontaktBtn);
		this.add(sc);

	}

}
