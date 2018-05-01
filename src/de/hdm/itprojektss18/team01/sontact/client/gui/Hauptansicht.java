package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Herzst�ck der Pr�sentationsschicht, welche die Hauptansicht darstellen soll. 
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Hauptansicht extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel footer = new VerticalPanel();

	Button kbtn = new Button("<image src='group-512.png' width='20px' height='20px' />Meine Kontakte");
	Button klbtn = new Button("<image src='Kontakt.png' width='20px' height='20px' /> Meine Kontaktliste");
	Button rpbtn = new Button("<image src='reportGenerator.png' width='15px' height='15px' />Report Generator");

	Tree t = new Tree();
	TreeItem root = new TreeItem();

	public Hauptansicht() {
		initWidget(vPanel);
		

		/** 
		 * Baum zur Navigation erstellen
		 */
		root.setText("Max Mustermann");
		root.addItem(kbtn);
		root.addItem(klbtn);
		root.addItem(rpbtn);
		
		/**
		 * Styling der Buttons
		 */
		kbtn.setStyleName("ButtonStyle");
		klbtn.setStyleName("ButtonStyle");
		rpbtn.setStyleName("ButtonStyle");
		kbtn.setPixelSize(200, 40);
		klbtn.setPixelSize(200, 40);
		rpbtn.setPixelSize(200, 40);
		
		/**
		 * Bauminhalt wird dem Baum hinzugef�gt und dem VerticalPanel hinzugefuegt.
		 */
		t.addItem(root);
		vPanel.add(t);
		
		/**
		 * ClickHandler für den "Meine Kontaktliste" Button.
		 */
		klbtn.addClickHandler(new ClickHandler(){
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert 
			 * die on click methode, die auf einen klick wartet und dann ausgeführt
			 * wird wenn der Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
				showKontaktliste skl = new showKontaktliste();
				skl.onLoad();

			}
		});
		
		/**
		 * ClickHandler für den "Meine Kontakte" Button.
		 */
		kbtn.addClickHandler(new ClickHandler(){
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert 
			 * die on click methode, die auf einen klick wartet und dann ausgeführt
			 * wird wenn der Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
	

			}
		});
		
		/**
		 * ClickHandler für den ReportGenerator Button.
		 */
		rpbtn.addClickHandler(new ClickHandler(){
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert 
			 * die on click methode, die auf einen klick wartet und dann ausgeführt
			 * wird wenn der Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
	

			}
		});
	}
}

