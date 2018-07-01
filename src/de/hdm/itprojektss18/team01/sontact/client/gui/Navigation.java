package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse Navigation stellt das Herzstueck der GUI dar. Hier wird die
 * <code>SontactTreeViewModel</code>-Klasse eingebunden und die Buttons fuer die
 * Erstellung von neuen <code>Kontakt</code> oder
 * <code>Kontaktliste</code>-Objekten realsiert.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */

public class Navigation extends VerticalPanel {

	HorizontalPanel hp = new HorizontalPanel();
	Button neueKontaktlisteBtn = new Button(
			"<image src='/images/kontaktliste.png' width='25px' height='25px' align='center'/>"
					+ "<image src='/images/plus.png' width=22px' height='22px' align='center' />");
	Button neuerKontaktBtn = new Button("<image src='/images/user.png' width='25px' height='25px' align='center' />"
			+ "<image src='/images/plus.png' width=22px' height='22px' align='center' />");

	/**
	 * Konstruktor fuer die Navigations-Klasse.
	 */
	public Navigation(final Nutzer nutzer) {

		// Erstellung des ScrollPanel´s fuer den Baum
		ScrollPanel sc = new ScrollPanel();
		// Groeße des ScrollPanel´s wird angepasst.
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		// Anlegen des Baumes navTreeModel fuer die Navigation in den Kontaktlisten.
		SontactTreeViewModel navTreeModel = new SontactTreeViewModel(nutzer);

		// Anlegung des Baumes mit dem zuvor definierten TreeViewModel.
		CellTree navTree = new CellTree(navTreeModel, null);

		// ClickHandler fuer das Erstellen von neuen Kontaktlisten.
		neueKontaktlisteBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Wird instanziiert wenn eine neue Kontaktliste erstellt werden soll.
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new KontaktlisteForm(nutzer));
			}
		});

		// ClickHandler fuer das Erstellen von neuen Kontakten.
		neuerKontaktBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new RegistrierungsForm(nutzer));

			}
		});

		// Hier wird das TreeViewModel dem ScrollPanel hinzugefuegt.
		sc.add(navTree);

		// Hier wird der Button fuer die Erstellung von neuen Kontaktlisten gestylt.
		neueKontaktlisteBtn.setPixelSize(100, 60);
		neueKontaktlisteBtn.setStyleName("button1");
		neueKontaktlisteBtn.setTitle("Neue Kontaktliste erstellen");

		// Hier wird der Button fuer die Erstellung von neuen Kontakten gestylt.
		neuerKontaktBtn.setPixelSize(100, 60);
		neuerKontaktBtn.setStyleName("button1");
		neuerKontaktBtn.setTitle("Neuen Kontakt erstellen");
		
		// Buttons werden dem HorizontalPanel hinzugefuegt.
		hp.add(neueKontaktlisteBtn);
		hp.add(neuerKontaktBtn);

		// Das HorizontalPanel wird dem VerticalPanel hinzugefuegt.
		this.add(hp);

		// Hier wird der Header fuer die Kontaktlisten gesetzt.
		this.add(new HTML("<center><h5>Meine Kontaktlisten</h5></center>"));

		// Das ScrollPanel wird dem VerticalPanel hinzugefuegt.
		this.add(sc);

	}
}
