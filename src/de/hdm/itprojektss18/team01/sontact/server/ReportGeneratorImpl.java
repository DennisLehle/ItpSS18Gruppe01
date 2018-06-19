package de.hdm.itprojektss18.team01.sontact.server;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18.team01.sontact.shared.EditorService;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.Column;
import de.hdm.itprojektss18.team01.sontact.shared.report.CompositeParagraph;
import de.hdm.itprojektss18.team01.sontact.shared.report.Paragraph;
import de.hdm.itprojektss18.team01.sontact.shared.report.Row;
import de.hdm.itprojektss18.team01.sontact.shared.report.Report;
import de.hdm.itprojektss18.team01.sontact.shared.report.SimpleParagraph;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.bo.*;
import de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGenerator;

/**
 * Implementierung des serverseitigen RPC-Services f�r den Report.
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EditorService editorService = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	/*
	 * *************************************************************************
	 * ABSCHNITT BEGINN: INITIALISIERUNG
	 * *************************************************************************
	 */
	public void init() throws IllegalArgumentException {

		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.editorService = impl;
	}

	protected EditorService getEditorService() {
		return this.editorService;
	}
	/*
	 * *************************************************************************
	 * ABSCHNITT ENDE: INITIALISIERUNG
	 * *************************************************************************
	 */

	/**
	 * Abruf des Reports der alle Kontakte des eingeloggten Nutzers generiert.
	 * 
	 * @param Nutzer
	 *            n
	 * @return report
	 */
	public AlleKontakteReport createAlleKontakteReport(Nutzer n) throws IllegalArgumentException {

		if (this.getEditorService() == null) {
			return null;
		}

		// Die Erstellung einer Instanz dieses Reports
		AlleKontakteReport report = new AlleKontakteReport();

		// Festlegung des Titels und des Generierungsdatums dieses Reports
		report.setTitle("Alle Kontakte");
		report.setCreated(new Date());

		// Kopfzeile der Reporttabelle; mit den Ueberschriften der einzelnen Spalten
		Row head = new Row();
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Erstellungszeitpunkt"));
		head.addColumn(new Column("Modifikationszeitpunkt"));

		// Spalte zur Darstellung des Eigentuemer eines Kontaktes
		head.addColumn(new Column("Kontakteigentuemer"));

		// Kopfzeile dem Report hinzufuegen
		report.addRow(head);

		// Angeforderte Kontaktdaten in den Vektor laden und dem Report hinzufuegen
		Vector<Kontakt> kontakt = new Vector<Kontakt>();

		// Kontakte der folgenden Vektoren aufrufen und dem ersten Vector hinzufuegen
		kontakt.addAll(this.getEditorService().getAllKontakteByOwner(n));
		kontakt.addAll(this.getEditorService().getAllSharedKontakteByReceiver(n.getId()));

		// Die Kontakte des gespeicherten Vectors, pro Zeile der Reporttabelle
		// hinzufuegen
		for (int i = 0; i < kontakt.size(); i++) {

			Row kon = new Row();
			kon.addColumn(new Column(kontakt.elementAt(i).getVorname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getNachname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getErstellDat().toString()));
			kon.addColumn(new Column(kontakt.elementAt(i).getModDat().toString()));

			if (kontakt != null) {
				kon.addColumn(new Column(
						getEditorService().getNutzerById(kontakt.elementAt(i).getOwnerId()).getEmailAddress()));

			}
			// Einzelne Zeile dem Report hinzufuegen
			report.addRow(kon);
		}
		// Rueckgabe der Reportausgabe
		return report;
	}

	/**
	 * Report der alle Kontakte nach Eigenschaften mit ihrer Auspraegung ausgibt.
	 * Die uebergebenen Eigenschaften oder Auspraegungen, werden vom Nutzer mithilfe
	 * der Suchleiste uebergeben. Die daraufhin den Report mit dem entsprechenden
	 * Filter zurueckgeben.
	 * 
	 * @param String auspraegung, String eigenschaft, Nutzer n
	 * @return report
	 */
	public AlleKontakteNachEigenschaftenReport createAuspraegungReport(String auspraegung, String eigenschaft, Nutzer n)
			throws IllegalArgumentException {

		if (this.getEditorService() == null) {
			return null;
		}

		// Die Erstellung einer Instanz dieses Reports
		AlleKontakteNachEigenschaftenReport report = new AlleKontakteNachEigenschaftenReport();

		// Festlegung des Titels und des Generierungsdatums dieses Reports
		if (auspraegung != null) {
			report.setTitle("Alle Kontakte nach" + " " + auspraegung + " ");
		} else if (eigenschaft != null) {
			report.setTitle("Alle Kontakte nach" + " " + eigenschaft + " ");
		}
		report.setCreated(new Date());

		// Kopfzeile der Reporttabelle; mit den Ueberschriften der einzelnen Spalten
		Row head = new Row();
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));

		// Spalte zur Darstellung des Eigentuemer eines Kontaktes
		head.addColumn(new Column("Kontakteigentuemer"));

		// Uebergebener Inhalt
		// head.addColumn(new Column("Eigenschaft"));
		// head.addColumn(new Column("Auspraegung"));

		// Kopfzeile dem Report hinzufuegen
		report.addRow(head);

		// Angeforderte Kontaktdaten in den Vektor laden und dem Report hinzufuegen
		Vector<Kontakt> kontakt = new Vector<Kontakt>();

		// Pruefung des uebergebenen Parameters, dieses durch die Suche den Report
		// ausgibt
		if (eigenschaft != null) {
			kontakt.addAll(this.getEditorService().getKontakteByEigenschaft(eigenschaft, n));

		} else if (auspraegung != null) {
			kontakt.addAll(this.getEditorService().getKontakteByAuspraegung(auspraegung, n));

		}
		// Die Kontakte des gespeicherten Vectors, pro Zeile der Reporttabelle
		// hinzufuegen

		// Vector<Relatable> relatable = new Vector<Relatable>();
		
		for (int i = 0; i < kontakt.size(); i++) {
			Vector<Relatable> auspraegungen = getEditorService()
					.getAllAuspraegungenByKontaktRelatable(kontakt.elementAt(i).getId());
			// relatable.addAll(auspraegungen);

			Row kon = new Row();

			kon.addColumn(new Column(kontakt.elementAt(i).getVorname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getNachname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getErstellDat().toString()));
			kon.addColumn(new Column(kontakt.elementAt(i).getModDat().toString()));
			kon.addColumn(
					new Column(getEditorService().getNutzerById(kontakt.elementAt(i).getOwnerId()).getEmailAddress()));

			 head.addColumn(new Column("Eigenschaft"));
			 head.addColumn(new Column("Auspraegung"));

			for (int j = 0; j < auspraegungen.size(); j++) {
//			head.addColumn(new Column("Eigenschaft"));
//			head.addColumn(new Column("Auspraegung"));
			
			kon.addColumn(new Column(auspraegungen.elementAt(j).getBezeichnung()));
			kon.addColumn(new Column(auspraegungen.elementAt(j).getWert()));
				
				}
			// Einzelne Zeile dem Report hinzufuegen
		report.addRow(kon);
	}

		// Rueckgabe der Reportausgabe
		return report;

	}


	/**
	 * Report der alle Kontakte nach Eigenschaften mit ihrer Auspraegung ausgibt.
	 * Die uebergebenen Eigenschaften oder Auspraegungen, werden vom Nutzer mithilfe
	 * der Suchleiste uebergeben. Die daraufhin den Report mit dem entsprechenden
	 * Filter zurueckgeben.
	 * 
	 * @param String auspraegung, String eigenschaft, Nutzer n
	 * @return report
	 */
	public AlleGeteiltenKontakteReport createAlleGeteilteReport(String email, Nutzer n)
			throws IllegalArgumentException {

		if (this.getEditorService() == null) {
			return null;
		}

		// Die Erstellung einer Instanz dieses Reports
		AlleGeteiltenKontakteReport report = new AlleGeteiltenKontakteReport();

		// Festlegung des Titels und des Generierungsdatums dieses Reports
		report.setTitle("Alle Kontakte nach bestimmten Teilhaberschaften");
		report.setCreated(new Date());

		// Kopfzeile der Reporttabelle; mit den Ueberschriften der einzelnen Spalten
		Row head = new Row();
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Kontaktteilhaber"));

		// Spalte zur Darstellung des Eigentuemer eines Kontaktes
		report.addRow(head);

		// Angeforderte Kontaktdaten in den entsprechenden Vektor laden und dem Report hinzufuegen
		Vector<Kontakt> kontakt = new Vector<Kontakt>();
		Vector<Kontakt> receiv = new Vector<Kontakt>();
		Vector<Berechtigung> b = new Vector<Berechtigung>();
		
		// Der zu ubergebene Teilhaber in Form eines Nutzer wird instantitiert
		Nutzer receiver = new Nutzer();
		
		// Dem Nutzer wird die entsprechende Emailadresse zugewiesen
		receiver = this.editorService.getUserByGMail(email);

		// Alle geteilten Kontakte des eingeloggten Nutzers aufrufen
		kontakt.addAll(this.getEditorService().getAllSharedKontakteByOwner(n.getId()));
		b.addAll(this.getEditorService().getAllBerechtigungenByOwner(n.getId()));

		// Die geteilten Kontakte des Vectors werden pro Kontakt und Berechtigung jeweils durchlaufen 	
		for (int i = 0; i < kontakt.size(); i++) {
			
			for (int j = 0; j < b.size(); j++) {

				// Pruefung und Vergleich des Kontakt- Objekts mit dem zugehörigen Berechtigung-Objekt 
				// die sich auf den Receiver beziehen, der als Nutzer eingetragen ist  
				if (kontakt.elementAt(i).getId() == b.elementAt(j).getObjectId()
						&& n.getId() == b.elementAt(j).getOwnerId()
						&& receiver.getId() == b.elementAt(j).getReceiverId()) {

					Kontakt k = new Kontakt();
					k = this.editorService.getKontaktById(b.elementAt(j).getObjectId());
					receiv.add(k);
				}
			}

		}

		// Die Berechtigungen des gespeicherten Vectors, werden pro Zeile der Reporttabelle
		// hinzugefuegt
		for (int i = 0; i < receiv.size(); i++) {

			Row kon = new Row();
			kon.addColumn(new Column(receiv.elementAt(i).getVorname()));
			kon.addColumn(new Column(receiv.elementAt(i).getNachname()));
			kon.addColumn(new Column(receiv.elementAt(i).getErstellDat().toString()));
			kon.addColumn(new Column(receiv.elementAt(i).getModDat().toString()));
			kon.addColumn(new Column(receiver.getEmailAddress()));

			// Einzelne Zeile dem Report hinzufuegen
			report.addRow(kon);
		}

		// Rueckgabe der Reportausgabe
		return report;
	}

	/*
	 * *************************************************************************
	 * ABSCHNITT - Anfang: Hilfsmethoden
	 * *************************************************************************
	 */

	/**
	 * Den Nutzer anhand der Registrierungsmail auslesen und die identifzierende
	 * Mail als Zeichkette anzeigen lassen.
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	private String getNutzerByGMail(Nutzer n) throws IllegalArgumentException {

		String name = "";
		Nutzer nutzer = editorService.getUserByGMail(n.getEmailAddress());
		name = nutzer.getEmailAddress();

		return name;
	}

	/**
	 * Zur korrekten Ausgabe der Kopfdaten, wird diese Hilfsmethode einheitlich f�r
	 * alle Berichtsausgaben verwendet.
	 * 
	 * @param n
	 * @param report
	 * @return
	 */
	private CompositeParagraph createHeaderData(Nutzer n) {
		// Generierung der Kopfdaten des Reports
		CompositeParagraph headerData = new CompositeParagraph();
		try {
			headerData.addSubParagraph(new SimpleParagraph("Nutzer: " + this.getNutzerByGMail(n)));

		} catch (NullPointerException e) {
			headerData.addSubParagraph(new SimpleParagraph("Nutzer: " + "Unbekannter Nutzer"));
			e.printStackTrace();
		}
		headerData
				.addSubParagraph(new SimpleParagraph("Erstellungsdatum: " + new Timestamp(System.currentTimeMillis())));

		return headerData;
	}
}
