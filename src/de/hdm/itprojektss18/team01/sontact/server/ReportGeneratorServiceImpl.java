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
	
	@Override
	public AlleKontakteNachEigenschaftenReport createAuspraegungReport() throws IllegalArgumentException {

		
		
		
		
		return null;
	}
	@Override
	public AlleKontakteReport createAlleKontakteReport() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AlleGeteiltenKontakteReport createAlleGeteilteReport() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
