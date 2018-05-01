package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Herzstück der Präsentationsschicht, welche die Hauptansicht darstellen soll. 
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class Hauptansicht extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel footer = new VerticalPanel();

	Button kbtn = new Button("Meine Kontakte");
	Button klbtn = new Button("Meine Kontaktlisten");
	Button sbtn = new Button(" Suche");
	Button rpbtn = new Button("ReportGenerator");

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
		root.addItem(sbtn);
		root.addItem(rpbtn);
		
		/**
		 * Styling der Buttons
		 */
		kbtn.setStyleName("ButtonStyle");
		klbtn.setStyleName("ButtonStyle");
		sbtn.setStyleName("ButtonStyle");
		rpbtn.setStyleName("ButtonStyle");
		kbtn.setPixelSize(200, 40);
		klbtn.setPixelSize(200, 40);
		sbtn.setPixelSize(200, 40);
		rpbtn.setPixelSize(200, 40);
		
		/**
		 * Bauminhalt wird dem Baum hinzugefügt und dem VerticalPanel hinzugefuegt.
		 */
		t.addItem(root);
		vPanel.add(t);

	}

}
