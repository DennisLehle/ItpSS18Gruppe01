package de.hdm.itprojektss18.team01.sontact.server;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGenerator;


public class ReportGeneratorServiceImpl extends RemoteServiceServlet implements ReportGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EditorService service = null;
	
	public ReportGeneratorServiceImpl() throws IllegalArgumentException {

}
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Beginn: Initialisierung
	 * *************************************************************************
	 */
	public void init() throws IllegalArgumentException {
		
		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.service = impl;
	}
	
	protected EditorService getEditorService() {
		return this.service;
	}
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Ende: Initialisierung
	 * *************************************************************************
	 */
	
	/**
	 * Report der alle Kontakte eines Nutzers ausgibt.
	 */
	@Override
	public AlleKontakteReport createAlleKontakteReport() throws IllegalArgumentException {
		
		//Erstellung einer Instanz des Reports
		AlleKontakteReport report = new AlleKontakteReport(); 
		
		//Setzen des Report Titels und dem Generierungsdatums
		report.setTitle("Alle Kontakte");
		report.setCreated(new Date());
		
		//Hinzufuegen der Kopfdaten
		report.setHeaderData(createHeaderData(n, report));
		
		Row head = new Row();
		
		//Kopfzeile mit den Ueberschriften mit den einzelnen Spalten im Report erstellen
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Status"));
		
		//Kopfzeile dem Report hinzufuegen
		report.addRow(head);
		
		//Relevante Daten in den Vektor laden und Zeile fuer Zeile dem Report hinzufuegen
		Vector <Kontakt> kontakt = this.getEditorService().getAllKontakteByOwner(n);
		
		for (Kontakt k : kontakt) {
			Row kon = new Row();
			kon.addColumn(new Column (k.getVorname()));
			kon.addColumn(new Column (k.getNachname()));
			kon.addColumn(new Column (k.getErstellDat().toString()));
			kon.addColumn(new Column (k.getModDat().toString()));
			
		//@TODO Status!!!
			
			
			
		// Noch nicht vollständig!!!!
	
		}

		return null;
	}
	

	
	
	/**
	 * Report der alle geteilten Kontakte eines Nutzers ausgibt.
	 */
	@Override
	public AlleGeteiltenKontakteReport createAlleGeteilteReport() throws IllegalArgumentException {
		

		return null;
	}
	

	/**
	 * Report der alle Kontakte nach Eigenschaften mit ihrer Auspraegung ausgibt.
	 */
	@Override
	public AlleKontakteNachEigenschaftenReport createAuspraegungReport() throws IllegalArgumentException {
		
		
		return null;
	}
	
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Anfang: Hilfsmethoden
	 * *************************************************************************
	 */
	
	
	
	
	private String getTimeForReport(Report report) {
		// TODO Auto-generated method stub
		return null;
	}
	private String getDateForReport(Report report) {
		// TODO Auto-generated method stub
		return null;
	}
	private String getNameForNutzer(Nutzer n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CompositeParagraph createHeaderData(Nutzer n, Report report) {
			
			//Generierung der Kopfdaten des Reports
			CompositeParagraph headerData = new CompositeParagraph();
			
			try {
				
				headerData.addSubParagraph(new SimpleParagraph("Nutzer: " + getNameForNutzer(n)));
			} 
			
			catch (NullPointerException e) {
				headerData.addSubParagraph(new SimpleParagraph("Nutzer: Unbekannter Nutzer"));
				e.printStackTrace();
			}
			
			headerData.addSubParagraph(new SimpleParagraph ("Datum: " + getDateForReport(report)));
			headerData.addSubParagraph(new SimpleParagraph ("Uhrzeit: " + getTimeForReport(report)));
			
			return headerData;
		}
	
}
