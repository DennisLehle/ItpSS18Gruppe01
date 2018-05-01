package de.hdm.itprojektss18.team01.sontact.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.LoginService;
import de.hdm.itprojektss18.team01.sontact.shared.LoginServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SontactReport implements EntryPoint {
	
	Nutzer loginInfo = new Nutzer();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte Melden Sie sich mit Ihren Google Account, um einen Zugriff auf den ReportGenerator zu bekommen.");
	private Anchor signInLink = new Anchor("Login");
	public Anchor signOutLink = new Anchor("Logout");
	
	
	LoginServiceAsync loginService = GWT.create(LoginService.class);

	/**
	 * Buttons der Navigation. 
	 * final, damit Callbacks sie verändern können.
	 */
	final Button showAllKontakteReport = new Button("Alle Kontakte Anzeigen");
	final Button showAllKontakteNachBestimmtenAusp = new Button("Kontakte nach Ausprägungen");
	final Button showReportNotVisitedButton = new Button("Alle geteilten Kontakte anzeigen");
	final Button backToEditorService = new Button("Zurück zu Sontact");
	
	/**
	 * Definition der Panels.
	 */
	private VerticalPanel navigationPanel;
	private VerticalPanel detailPanel;
	
	/**
	 * ReportService und EditorService werden auf null gesetzt.
	 * Diese werden neu geladen, dies Dient zur Sicherheit.
	 */
	ReportGeneratorServiceAsync reportGeneratorService = null;
	EditorServiceAsync editorService = null;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL() + "SontactReport.html", new AsyncCallback<Nutzer>() {
	      public void onFailure(Throwable error) {
	      }

		@Override
		public void onSuccess(Nutzer result) {
			loginInfo = result;
	        if(loginInfo.isLoggedIn()) {
	        	RootPanel.get("Details").clear(); 
	        } else {
	          loadLogin();
	        }
			
		}
	    });
	  }

	  private void loadLogin() {
	    // Assemble login panel.
	    signInLink.setHref(loginInfo.getLoginUrl());
	    loginPanel.add(loginLabel);
	    loginPanel.add(signInLink);
	    RootPanel.get("Details").add(loginPanel);
	  }
	}
