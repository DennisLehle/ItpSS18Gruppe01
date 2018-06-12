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
	 * ABSCHNITT - Beginn: Initialisierung
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
	 * ABSCHNITT - Ende: Initialisierung
	 * *************************************************************************
	 */

	
	/**
	 * 
	 * KontaktReport: = simpleReport 
	 * 
	 * 
	 * -> K mit allen A`s
	 * 
	 */
	
	
	
	
	
	/**
	 * Report der alle Kontakte eines Nutzers ausgibt.
	 * 
	 * 
	 * AllKontakteReport: compositeReprort
	 * 
	 * iternativ RO KoktaktReport, soviele Kontakte es gibt. 
	 * 
	 * 
	 * 
	 */
	@Override
	public AlleKontakteReport createAlleKontakteReport(Nutzer n) throws IllegalArgumentException {

		if (this.getEditorService() == null) {
			return null;
		}

		// Erstellung einer Instanz des Reports
		AlleKontakteReport report = new AlleKontakteReport();

		// Setzen des Report Titels und dem Generierungsdatums
//		Row header = new Row();
		report.setTitle("Alle Kontakte");
		report.setCreated(new Date());

		// Erzeugung der Kopfdaten
		//report.setHeaderData(createHeaderData(n));

		// Kopfzeile mit den Ueberschriften mit den einzelnen Spalten im Report
		// erstellen
		Row head = new Row();
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Erstellungszeitpunkt"));
		head.addColumn(new Column("Modifikationszeitpunkt"));
		/* Eigentuemer des Kontaktes fuer die Fallunterscheidung
		 * welcher Kontakt mit dem 
		 */
		head.addColumn(new Column("Kontakteigent�mer"));

		// Kopfzeile dem Report hinzufuegen
		//report.addRow();
		report.addRow(head);


		// Relevante Kontaktdaten in den Vektor laden und Zeile fuer Zeile dem Report
		// hinzufuegen
		Vector<Kontakt> kontakt = new Vector<Kontakt>();
		
		
		kontakt.addAll(this.getEditorService().getAllKontakteByOwner(n));
		kontakt.addAll(this.getEditorService().getAllSharedKontakteByReceiver(n.getId()));

		for (int i = 0; i < kontakt.size(); i++) {
			
		
			Row kon = new Row();
			kon.addColumn(new Column(kontakt.elementAt(i).getVorname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getNachname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getErstellDat().toString()));
			kon.addColumn(new Column(kontakt.elementAt(i).getModDat().toString()));
			
			
				if (kontakt != null) {
//					boolean teilung = this.getEditorService().getStatusForObject(kontakt.elementAt(i).getId(), 'k');
//					int id = kontakt.elementAt(i).getOwnerId();
//					if (teilung == true) {

						kon.addColumn(new Column(getEditorService().getNutzerById(kontakt.elementAt(i).getOwnerId()).getEmailAddress()));

//					} else {
//						kon.addColumn(new Column(getEditorService().getNutzerById(id).getEmailAddress()));
//				}
				}
				
				report.addRow(kon);
		}
		
		return report;
	}

		
		
	
	/**
	 * Report der alle Kontakte nach Eigenschaften mit ihrer Auspraegung ausgibt. 
	 * Die bestimmten Eigenschaften k�nnen aus der Suchleiste ausgelesen 
	 * werden und werden mit der entsprechenden Auspraegung zur�ckgegeben.  
	 */
	@Override
	public AlleKontakteNachEigenschaftenReport createAuspraegungReport(String auspraegung,
			String eigenschaft, Nutzer n) throws IllegalArgumentException {
		
		if (this.getEditorService() == null) {
			return null;
		}
		
		AlleKontakteNachEigenschaftenReport report = new AlleKontakteNachEigenschaftenReport();
		
		// Setzen des Report Titels und dem Generierungsdatums
		report.setTitle("Alle Kontakte nach bestimmten Eigenschaften und Auspraegungen");
		report.setCreated(new Date());


		// Kopfzeile mit den Ueberschriften mit den einzelnen Spalten im Report
		// erstellen
		Row head = new Row();
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Eigenschaft"));
		head.addColumn(new Column("Auspraegung"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Kontakteigent�mer"));

		// Kopfzeile dem Report hinzufuegen
		//report.addRow();
		report.addRow(head);

		
		// Relevante Kontaktdaten in den Vektor laden und Zeile fuer Zeile dem Report
		// hinzufuegen
			Vector<Kontakt> kontakt = new Vector<Kontakt>();
			Vector<Relatable> aus = new Vector<Relatable>();
		
		if(eigenschaft != null) {
			kontakt.addAll(this.getEditorService().getKontakteByEigenschaft(eigenschaft, n));
			for (int i = 0; i < kontakt.size(); i++) {
				aus.addAll(this.getEditorService().getAllAuspraegungenByKontaktRelatable(kontakt.elementAt(i).getId()));
				
			}
		} else {
			kontakt.addAll(this.getEditorService().getKontakteByAuspraegung(auspraegung, n));
			
			for (int i = 0; i < kontakt.size(); i++) {
				aus.addAll(this.getEditorService().getAllAuspraegungenByKontaktRelatable(kontakt.elementAt(i).getId()));
				
			}
		}
	
		for (int i = 0; i < kontakt.size(); i++) {

			Row kon = new Row();
			kon.addColumn(new Column(kontakt.elementAt(i).getVorname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getNachname()));
			kon.addColumn(new Column(aus.elementAt(i).getBezeichnung()));
			kon.addColumn(new Column(aus.elementAt(i).getWert()));
			kon.addColumn(new Column(kontakt.elementAt(i).getModDat().toString()));
				
			if (kontakt != null) {
					kon.addColumn(new Column(getEditorService().getNutzerById(kontakt.elementAt(i).getOwnerId()).getEmailAddress()));
			}
				report.addRow(kon);	
		}
		
	
		return report;
		
	}
	
	/**
	 * Report der alle geteilten Kontakte eines Nutzers anzeigt, die als 
	 * Teilhaberschaften fuer andere Nutzer freigegeben wurden.
	 */
	@Override
	public AlleGeteiltenKontakteReport createAlleGeteilteReport(int receiverId, Nutzer n, String eigenschaft, String auspraegung) 
			throws IllegalArgumentException {
		
		if (this.getEditorService() == null) {
			return null;
		}

		// Erstellung einer Instanz des Reports
		AlleGeteiltenKontakteReport report = new AlleGeteiltenKontakteReport();
		

		// Setzen des Report Titels und dem Generierungsdatums
//		Row header = new Row();
		report.setTitle("Alle Kontakte nach bestimmten Teilhaberschaften");
		report.setCreated(new Date());

		// Erzeugung der Kopfdaten
		//report.setHeaderData(createHeaderData(n));

		// Kopfzeile mit den Ueberschriften mit den einzelnen Spalten im Report
		// erstellen
		Row head = new Row();
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Eigenschaft"));
		head.addColumn(new Column("Auspraegung"));
		head.addColumn(new Column("Kontaktteilhaber"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Kontakteigentümer"));


		// Kopfzeile dem Report hinzufuegen
		//report.addRow();
		report.addRow(head);


		// Relevante Kontaktdaten in den Vektor laden und Zeile fuer Zeile dem Report
		// hinzufuegen
		Vector<Kontakt> kontakt = new Vector<Kontakt>();
		Vector<Relatable> aus= new Vector<Relatable>();
	//	Vector<Kontakt> teilkontakt = new Vector <Kontakt>();
		Vector<Kontakt> receiv = new Vector<Kontakt>();
		Nutzer nutzer = new Nutzer();
		
		//Alle geteilten Kontakte des eingeloggten Nutzers herauslesen.
		kontakt.addAll(this.getEditorService().getAllSharedKontakteByOwner(n.getId()));
		for (int i = 0; i < kontakt.size(); i++) {
			if(kontakt.elementAt(i).getOwnerId() == receiverId) {
				receiv.add(kontakt.elementAt(i));
			}
			
		}
		

		for (int i = 0; i < kontakt.size(); i++) {
			//teilkontakt.addAll(this.editorService.getAllSharedKontakteByReceiver(kontakt.elementAt(i).getBerechtigung().getReceiverId()));
			aus.addAll(this.editorService.getAllAuspraegungenByKontaktRelatable(kontakt.elementAt(i).getId()));
			nutzer = this.editorService.getNutzerById(receiverId);

			Row kon = new Row();
			kon.addColumn(new Column(kontakt.elementAt(i).getVorname()));
			kon.addColumn(new Column(kontakt.elementAt(i).getNachname()));
			kon.addColumn(new Column(aus.elementAt(i).getBezeichnung()));
			kon.addColumn(new Column(aus.elementAt(i).getWert()));
			kon.addColumn(new Column(nutzer.getEmailAddress()));
			kon.addColumn(new Column(kontakt.elementAt(i).getModDat().toString()));
			kon.addColumn(new Column(getEditorService().getNutzerById(kontakt.elementAt(i).getOwnerId()).getEmailAddress()));
		
			report.addRow(kon);
		}
		
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
	 * Zur korrekten Ausgabe der Kopfdaten, wird diese Hilfsmethode einheitlich
	 * f�r alle Berichtsausgaben verwendet. 
	 * @param n
	 * @param report
	 * @return
	 */
	private CompositeParagraph createHeaderData(Nutzer n){
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
