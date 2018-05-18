package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Darstellung eines Kontakt Formulars, dieses wird für anzeigen von Kontakten benötigt.
 * Grundaufbau der Darstellung von Kontakt Informationen.
 * 
 * Es werden hier Informationen vom <code>Kontakt</code> hinterlegt.
 * Dieser Kontakt enthält folgene Informationen:
 * 
 * <code>Eigenschaft</code>
 * <code>Auspraegung</code>
 * 
 * @author Dennis Lehle
 *
 */
public class KontaktFormular extends VerticalPanel {
	
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	
	/**
	 * TextBoxen für das KontaktFormular
	 */
	TextBox vornameTb = new TextBox();
	TextBox nachnameTb = new TextBox();
	
	Kontakt k = null;
	
	
	public KontaktFormular() {
		Grid kontaktGrid = new Grid(3, 2);
		this.add(kontaktGrid);

		Label vorname = new Label("Vorname");
		kontaktGrid.setWidget(1, 0, vorname);
		kontaktGrid.setWidget(1, 1, vornameTb);

		Label nachname = new Label("Nachname");
		kontaktGrid.setWidget(2, 0, nachname);
		kontaktGrid.setWidget(2, 1, nachnameTb);

		HorizontalPanel kontaktButtonPanel = new HorizontalPanel();
		this.add(kontaktButtonPanel);
	}
//		Button saveButton = new Button("Speichern");
//		saveButton.addClickHandler(new SaveClickHandler());
//		kontaktButtonPanel.add(saveButton);
//
//		Button deleteButton = new Button("Löschen");
//		deleteButton.addClickHandler(new DeleteClickHandler());
//		kontaktButtonPanel.add(deleteButton);
//
//	}
//	/**
//	 * Klick Handler fürs Speichern.
//	 */
//	private class SaveClickHandler implements ClickHandler {
//		@Override
//		public void onClick(ClickEvent event) {
//			if (vornameTb != null && nachnameTb != null) {
//				Window.alert("Bitte Feld ausfüllen :)");
//				
//				editorVerwaltung.createKontakt(vornameTb, nachnameTb, n, callback);
//			} else {
//				Window.alert("kein Kunde ausgewählt");
//			}
//		}
//	}
//	
//	
//	

}
