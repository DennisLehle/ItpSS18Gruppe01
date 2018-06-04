package de.hdm.itprojektss18.team01.sontact.server;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18.team01.sontact.shared.EditorService;
import de.hdm.itprojektss18.team01.sontact.shared.report.AllKontakteByNutzerReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.Column;
import de.hdm.itprojektss18.team01.sontact.shared.report.CompositeParagraph;
import de.hdm.itprojektss18.team01.sontact.shared.report.Paragraph;
import de.hdm.itprojektss18.team01.sontact.shared.report.Row;
import de.hdm.itprojektss18.team01.sontact.shared.report.Report;
import de.hdm.itprojektss18.team01.sontact.shared.report.SimpleParagraph;
import de.hdm.itprojektss18.team01.sontact.shared.report.AllKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.bo.*;
import de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGenerator;


public class ReportGeneratorServiceImpl extends RemoteServiceServlet{

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
	 * Statischer Aufruf aller Kontakte, eines Nutzers  
	 */
	public AllKontakteByNutzerReport createAllKontakteByReport(Nutzer n) 
			throws IllegalArgumentException { 
	
		//Erzeugung einer leeren Instanz des Reports 
		AllKontakteByNutzerReport report = new AllKontakteByNutzerReport();
		
		//Ueberschrift des Reports, sowie Erstellungsdatum der Ausgabe
		report.setTitle("Meine Kontakte");
		report.setCreated(new Date());
		
		//Hinzufuegen der Kopfzeile
		report.setHeaderData(createHeaderData(n, report));
		Row head = new Row();
		
		//Zuweisung der Kopfzeilendaten die im Report, jeder Spalte zugeordnet werden.
		head.addColumn(new Column("Vorname"));
		head.addColumn(new Column("Nachname"));
		head.addColumn(new Column("Eigenschaft"));
		head.addColumn(new Column("Auspraegung"));
	//	head.addColumn(new Column("Status"));

		//Kopfzeilendaten der Ausgabe hinzuf�gen 
		report.addRow(head);
		
		//Notwendige Daten abrufen und dem Report hinzufuegen
		Vector<Kontakt> kontakt = service.getAllKontakteByOwner(n);
	//	report.(kontakt.size());
		
		for (Kontakt k : kontakt) {
			Vector<Auspraegung> a = service.getAllAuspraegungenByKontakt(k.getId());
			Row row = new Row();
			row.addColumn(new Column(k.getVorname()));
			row.addColumn(new Column(k.getNachname()));
			
			for (Auspraegung auspraegung : a) {
				Eigenschaft e = service.getEigenschaftForAuspraegung(auspraegung.getEigenschaftId());
			
			row.addColumn(new Column(auspraegung.getWert()));
			row.addColumn(new Column(e.getBezeichnung()));
			}
		}
		
	return report;   	
}
	
	/**
	 * Statischer Aufruf aller Kontakte  
	 */
	public AllKontakteReport createAllKontakteReport(Kontakt k) 
			throws IllegalArgumentException {
		
	return null;
	}
	
	private Paragraph createHeaderData(Nutzer n, AllKontakteByNutzerReport report) {
		// TODO Auto-generated method stub
		return null;
	}



	
}
