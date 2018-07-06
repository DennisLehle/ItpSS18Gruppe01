package de.hdm.itprojektss18.team01.sontact.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.gui.KontaktForm;
import de.hdm.itprojektss18.team01.sontact.client.gui.Navigation;
import de.hdm.itprojektss18.team01.sontact.client.gui.RegistrierungsForm;
import de.hdm.itprojektss18.team01.sontact.client.gui.ShowKontakte;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.LoginService;
import de.hdm.itprojektss18.team01.sontact.shared.LoginServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.LoginInfo;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse EntryPoint beinhaltet die Methode <code>onModuleLoad()</code>
 * welche beim Starten der Applikation aufgerufen wird.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */
public class Sontact implements EntryPoint {
	
	private LoginInfo loginInfo = null;
	private Kontakt ownProfil = null;
	private Label searchlb = new Label();
	private VerticalPanel loginPanel = new VerticalPanel();
	private 	Label profilLb = new Label();
	private Label loginLabel = new Label("Herzlich Wilkommen auf Sontact. Um die Kontaktverwaltung nutzen zu können melden Sie sich bitte mit einem Google-Konto an, um fortfahren zu können.");
	private HTML loginHTML = new HTML("<h7></h7>");
	private	HTML sontactHTML = new HTML("<h8>SONTACT</h8>");
	private Anchor signInLink = new Anchor("Mit Google anmelden");

   /**
	* Erzeugung des <code>EditorService</code>-Objekts ist noetig, um eine zentrale
	* Applikations-Verwaltung zu initialisieren, um die Aktivitaeten der
	* Applikation zu steuern.
	*/
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings clientSettings = new ClientsideSettings();
	
	/**
	 * Das ist die EntryPoint Methode <code>onModuleLoad()</code>. Sie ist die
	 * Einstiegspunktmethode, die automatisch aufgerufen wird, indem ein Modul
	 * geladen wird, das eine implementierende Klasse als Einstiegspunkt deklariert.
	 */
	public void onModuleLoad() {

		loginService.login(GWT.getHostPageBaseURL() + "Sontact.html", new AsyncCallback<LoginInfo>() {

			@Override
			public void onFailure(Throwable error) {
				Window.alert("Fehler Login: " + error.toString());

			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					//Der Nutzer wird anhand seiner EMail aus der Db gesucht.
					editorVerwaltung.getUserByGMail(loginInfo.getEmailAddress(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable error) {
							Window.alert("Es ist ein Fehler beim Login aufgetreten: ");
					
						}

						@Override
						public void onSuccess(Nutzer nutzer) {
							//Ist der Nutzer noch registriert wird er zur Startseite weitergeleitet.
							if (nutzer != null) {
								
								RootPanel.get("content").clear();
								// Die start Methode wird geladen
								start(nutzer);
									
							//Ist er noch kein Mitglied bei Sontact wird er als Nutzer angelegt und zum Registrierungs-Formular geleitet.
							} else {
								//Div's alle leeren.
								RootPanel.get("content").clear();
								RootPanel.get("navigator").clear();
								
								// Der Nutzer wird anhand der eingeloggten Email-Adresse createt
								editorVerwaltung.createNutzer(loginInfo.getEmailAddress(), new AsyncCallback<Nutzer>() {

									@Override
									public void onFailure(Throwable error) {
										error.getMessage();
									}

									@Override
									public void onSuccess(Nutzer result) {
										//Das Registrierungs Formular wird aufgerufen.
										RootPanel.get("content").add(new RegistrierungsForm(result));

									}

								});
							}
						}
					});

				} else {
					// Ladet den Login fuer die Applikation
					loadLogin();
				}
			}
		});
	}

	/**
	 * Ist die Initialisierungmethode fuer den Start des Kontaktverwaltungssystems.
	 * 
	 * @param nutzer
	 */
	private void start(final Nutzer nutzer) {

		// Logout wird hier schon gesetzt
		HTML signOutLink = new HTML("<p><a href='" + loginInfo.getLogoutUrl() 
				+ "'><span class='glyphicon glyphicon-log-out'></span></a></p>");
		RootPanel.get("nutzermenu").add(signOutLink);
		
		// Setzen von Cookies fuer die spaetere Identifizierung eines Nutzers.
		Cookies.setCookie("nutzerGMail", nutzer.getEmailAddress()); 	
		
		// Wenn ein Nutzer vorhanden ist wird zusaetzlich noch die nutzerId gesetzt.
		Cookies.setCookie("nutzerID", String.valueOf(nutzer.getId()));
		RootPanel.get("navigator").add(new Navigation(nutzer));
		
		// Identifizierung des Registrierungs-Kontakts des Nutzers fuer den Namen im Header in der GUI.
		editorVerwaltung.getOwnKontakt(nutzer, new AsyncCallback <Kontakt>() {

			@Override
			public void onFailure(Throwable err) {
				err.getMessage().toString();
				
			}

			@Override
			public void onSuccess(Kontakt result) {
				// Eigenes Profil wird gesetzt um außerhalb Zugriff zu erhalten.
				ownProfil = result;
				
				// Label wird mit dem Vor- und Nachnamen des Nutzers befuellt der sich eingeloggt hat.
				profilLb.setText(result.getVorname() +" "+ result.getNachname());
				profilLb.setTitle("Dein Kontakt");
				
				// Label wird mit dem ClickHandler versehen, um auf das eigene Profil zu gelangen.
				profilLb.addClickHandler(new ownProfilClickHandler());
				profilLb.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
					
				
				//searchlb.setText("Suche");
				searchlb.setText("Suche ");
				searchlb.setTitle("Aufruf der Suchseite für eine gezielte Kontaktsuche");
				searchlb.addClickHandler(new searchClickHandler());
				searchlb.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
				// Leeren des NutzerMenu <div> containers.
				RootPanel.get("nutzermenu").clear();

				// Hier wird der Logout schon gesetzt.
				HTML signOutLink = new HTML("<p><a href='" + loginInfo.getLogoutUrl() 
						+ "'><span class='glyphicon glyphicon-log-out'></span></a></p>");
				signOutLink.setTitle("Sontact Logout - Bis bald ");
				
				// Label und Logout werden dem NutzerMenu <div> Container hinzugefuegt.

				RootPanel.get("nutzermenu").add(searchlb);
				RootPanel.get("nutzermenu").add(new HTML("<p><span class='glyphicon glyphicon-search'></span> &nbsp;"));
				RootPanel.get("nutzermenu").add(new HTML("  "));
				RootPanel.get("nutzermenu").add(profilLb);
				RootPanel.get("nutzermenu").add(new HTML("<p><span class='glyphicon glyphicon-user'></span> &nbsp;"));
				RootPanel.get("nutzermenu").add(signOutLink);
				
				
			}
		
		});
		
		
		/*
		 * Hinzufuegen der Sontact.html und SontactReport.html in den Footer.
		 */
		HorizontalPanel footer = new HorizontalPanel();
		Anchor startseite = new Anchor("Startseite ", "Sontact.html");
		HTML text1 = new HTML(" | ");
		HTML text2 = new HTML(" | © 2018 Sontact, IT-Projekt Gruppe01, Hochschule der\n" + "Medien Stuttgart | ");
		Anchor reportGeneratorLink = new Anchor (" ReportGenerator ", "SontactReport.html");
		
		footer.add(startseite);
		footer.add(text1);
		footer.add(reportGeneratorLink);
		footer.add(text2);
		
		// Das footerPanel wird gestylt
		RootPanel.get("footer").setStylePrimaryName("footer");
		// Das footerPanel wird dem <div> footer hinzugefuegt
		RootPanel.get("footer").add(footer);
		// Hinzufuegen der Uebersicht fuer alle Kontakte
		RootPanel.get("content").add(new ShowKontakte(nutzer));

	}
	
	/*
	 *Die Methode loadLogin ladet den Login fuer die Applikation.
	 */
	void loadLogin() {	
		
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginHTML);
		loginPanel.add(sontactHTML);
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		loginPanel.addStyleName("login");
		
		// Footer wird geelert fuer den LoginContainer
		RootPanel.get("footer").clear();
		// Hinzufuegen des LoginPanels in den <div> Container "content"
		RootPanel.get("content").add(loginPanel);			
	}

	/**
	 * ClickHandler fuers das Label, um auf das eigene Profil zu gelangen.
	 * 
	 * @author Dennis Lehle
	 *
	 */
	class ownProfilClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("content").clear();
			new KontaktForm(ownProfil);
			
		}
		
	}
	
	class searchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));
			RootPanel.get("content").clear();
			RootPanel.get("contentHeader").clear();
			RootPanel.get("content").add(new ShowKontakte(nutzer));
			
		}
		
	}
}