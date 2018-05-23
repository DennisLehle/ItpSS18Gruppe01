package de.hdm.itprojektss18.team01.sontact.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.gui.Navigation;
import de.hdm.itprojektss18.team01.sontact.client.gui.NavigationReport;
import de.hdm.itprojektss18.team01.sontact.client.gui.ShowKontakte;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.LoginService;
import de.hdm.itprojektss18.team01.sontact.shared.LoginServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.LoginInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Diese Klasse ist der Anfang des ReportGenerators.
 * Der ReportGenerator soll nur statische HTML ausgaben tätigen.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 * 
 */
public class SontactReport implements EntryPoint {
	
	LoginInfo loginInfo = new LoginInfo();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte Melden Sie sich mit Ihren Google Account, um einen Zugriff auf den ReportGenerator zu bekommen.");
	private Anchor signInLink = new Anchor("Login");
	public Anchor signOutLink = new Anchor("Logout");
	ClientsideSettings clientSettings = new ClientsideSettings();
	
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();

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
	    loginService.login(GWT.getHostPageBaseURL() + "SontactReport.html", new AsyncCallback<LoginInfo>() {
	      public void onFailure(Throwable error) {
	      }

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
	        if(loginInfo.isLoggedIn()) {
	        	loadReport();
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
	    RootPanel.get("contentR").add(loginPanel);
	  }
	  
	  private void loadReport() {
		  NavigationReport nr = new NavigationReport();
		  RootPanel.get("navigatorR").add(nr);
			RootPanel.get("navigatorR").add(new Navigation(clientSettings.getCurrentNutzer()));
			
			//Identifizierung des Registrierungs Kontakts des Nutzers für Namens Setzung in der Gui.
			editorVerwaltung.getOwnKontakt(clientSettings.getCurrentNutzer(), new AsyncCallback <Kontakt>() {

				@Override
				public void onFailure(Throwable err) {
					err.getMessage().toString();
					
				}

				@Override
				public void onSuccess(Kontakt result) {
					RootPanel.get("nutzermenuR").add(new HTML("<p><span class='fa fa-user-circle-o'></span> &nbsp; " + result.getVorname() +" "+ result.getNachname()));
					HTML signOutLink = new HTML("<p><a href='" 
							+ loginInfo.getLogoutUrl() 
							+ "'><span class='fas fa-sign-out-alt'></span></a></p>");
					RootPanel.get("nutzermenuR").add(signOutLink);
				}
				
				
				
			});
			
			HorizontalPanel footer = new HorizontalPanel();
			Anchor startseite = new Anchor ("Startseite", "SontactReport.html");
			HTML copyrightText1 = new HTML(" | ");
			Anchor reportGeneratorLink = new Anchor ("Back 2 Sontact", "Sontact.html");
			HTML copyrightText2 = new HTML(" | 2018 Sontact | ");
			Anchor impressumLink = new Anchor("Impressum");
			footer.add(startseite);
			footer.add(copyrightText1);
			footer.add(reportGeneratorLink);
			footer.add(copyrightText2);
			
			RootPanel.get("footerR").add(footer);

		  
		  
		  
	  }
	}
