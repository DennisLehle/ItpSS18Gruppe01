package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Wird aufgerufen wenn Nutzer noch kein eigenes Profil im System besitzt.
 * 
 * @see onModuleLoad() Login 
 * @author dennislehle
 *
 */
public class RegistrierungsFormular extends VerticalPanel {
	
	private Label titel = new Label("Eigenen Kontakt anlegen");
	
	private Label vorname = new Label("Vorname");
	private TextBox vornameTb = new TextBox();
	HorizontalPanel eigenschaftKontaktLabel = new HorizontalPanel();
	
	private Label nachname = new Label("Nachname");
	private TextBox nachnameTb = new TextBox();
	HorizontalPanel eigenschaftKontaktTb = new HorizontalPanel();

	/**
	 * TextBox und Label für Eigenschaft und Ausprägung 
	 * Diese multiplizieren sich je nach dem wie viele Eigenschaften/Ausprägungen ein Kontakt aufweist.
	 * Feste Eigenschaften auswählen
	 */
	private Button festeEerstellen = new Button("Feste Eigenschaften +");
	
	//ListBox und TextBox nebeneinander in ein HorizontalPanel
	private ListBox eigenschaft = new ListBox();
	private TextBox auspraegung = new TextBox();
	HorizontalPanel festeEigenschaftPanel = new HorizontalPanel();
	
	/**
	 * TextBox und Label für Eigenschaft und Ausprägung 
	 * Diese multiplizieren sich je nach dem wie viele Eigenschaften/Ausprägungen ein Kontakt aufweist.
	 * Eigene Eigenschaften auswählen...
	 */
	private Button eigeneEerstellen = new Button("Eigene Eigenschaft+");
	
	//ListBox und TextBox nebeneinander in ein HorizontalPanel
	private TextBox eigeneAuspraegung = new TextBox();
	private TextBox eigeneEigenschaft = new TextBox();
	HorizontalPanel eigeneEigenschaftPanel = new HorizontalPanel();
	
	/**
	 * Button zum abschließen des Anmeldevorgangs.
	 */
	private Button speichern = new Button("Speichern");
	
	
	public RegistrierungsFormular() {
		
		
		
		
		
	}
}
