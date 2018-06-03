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
import de.hdm.itprojektss18.team01.sontact.client.gui.MessageBox;
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
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */
public class Sontact implements EntryPoint {

	private LoginInfo loginInfo = null;
	private Kontakt ownProfil = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Nur ein Schritt trennt Sie noch von der Kontaktverwaltung. Melden Sie sich jetzt mit einem Google-Konto an, um Sontact nutzen zu können.");
	private Anchor signInLink = new Anchor("Mit Google anmelden");

	LoginServiceAsync loginService = GWT.create(LoginService.class);
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings clientSettings = new ClientsideSettings();
	
	/**
	 * This is the entry point method.
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

					editorVerwaltung.getUserByGMail(loginInfo.getEmailAddress(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable error) {
							Window.alert("Es ist ein Fehler beim Login aufgetreten: ");
					
						}

						@Override
						public void onSuccess(Nutzer nutzer) {
							if (nutzer != null) {
								RootPanel.get("content").clear();
								start(nutzer);
							} else {
								RootPanel.get("content").clear();
								RootPanel.get("navigator").clear();
								MessageBox.alertWidget("Kontakt",
										"Sie haben noch kein Kontakt angelegt, bitte legen Sie Ihren eigenen Kontakt an");
								editorVerwaltung.createNutzer(loginInfo.getEmailAddress(), new AsyncCallback<Nutzer>() {

									@Override
									public void onFailure(Throwable error) {
										error.getMessage();
									}

									@Override
									public void onSuccess(Nutzer result) {
										Nutzer n = result;
										editorVerwaltung.getUserByGMail(n.getEmailAddress(),
												new AsyncCallback<Nutzer>() {

													@Override
													public void onFailure(Throwable error) {
														Window.alert("Es ist ein Fehler beim Login aufgetreten: ");

													}

													@Override
													public void onSuccess(final Nutzer nutzer) {
													RootPanel.get("content").add(new RegistrierungsForm(nutzer));
													
													}
												});

									}

								});
							}
						}
					});

				} else {
					loadLogin();
				}
			}
		});
	}

	/**
	 * Ist die Initialisierungmethode fuer den Start des Kontaktverwaltungssystems
	 * 
	 * @param nutzer
	 */
	private void start(final Nutzer nutzer) {
		//Profil Label wird erstellt.
		Label profilLb = new Label();

		//Logout wird hier schon gesetzt
		HTML signOutLink = new HTML("<p><a href='" 
				+ loginInfo.getLogoutUrl() 
				+ "'><span class='glyphicon glyphicon-log-out'></span></a></p>");
		RootPanel.get("nutzermenu").add(signOutLink);
		
		
		//Setzen von Cookies für spätere Identifizierung eines Nutzers.
		Cookies.setCookie("nutzerGMail", nutzer.getEmailAddress()); 	
		
		//Wenn ein nutzer vorhanden wird die nutzerId noch gesetzt.
		Cookies.setCookie("nutzerID", String.valueOf(nutzer.getId()));
	
		RootPanel.get("navigator").add(new Navigation(nutzer));
		
		//Identifizierung des Registrierungs Kontakts des Nutzers für Namens Setzung in der Gui.
		editorVerwaltung.getOwnKontakt(nutzer, new AsyncCallback <Kontakt>() {

			@Override
			public void onFailure(Throwable err) {
				err.getMessage().toString();
				
			}

			@Override
			public void onSuccess(Kontakt result) {
				//Eigenes Profil wird gesetzt um außerhalb Zugriff zu erhalten.
				ownProfil = result;
				//Label wird mit dem Vor-, und Nachnamen des Nutzers befült der sich Eingeloggt hat.
				profilLb.setText(result.getVorname() +" "+ result.getNachname());
				//Label wird mit ClickHandler versehen um aufs eigene Profil zu gelangen.
				profilLb.addClickHandler(new ownProfilClickHandler());
				profilLb.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
				
				
				//Erstmal den Container leeren.
				RootPanel.get("nutzermenu").clear();
				
				//Profil logo wird erstellt.
			//	RootPanel.get("nutzermenu").add(new HTML("<p><span class='glyphicon glyphicon-user'></span> &nbsp; "));
				
				//Logout wird hier schon gesetzt
				HTML signOutLink = new HTML("<p><a href='" 
						+ loginInfo.getLogoutUrl() 
						+ "'><span class='glyphicon glyphicon-log-out'></span></a></p>");
				
				//Label und Logout werden dem NutzerMenu <div> Container hinzugefügt.
				RootPanel.get("nutzermenu").add(profilLb);
				RootPanel.get("nutzermenu").add(new HTML("<p><span class='glyphicon glyphicon-user'></span> &nbsp;"));
				RootPanel.get("nutzermenu").add(signOutLink);
				
				
			}
			
			
		});
		

		RootPanel.get("content").add(new ShowKontakte(nutzer));
		HorizontalPanel footer = new HorizontalPanel();
		Anchor startseite = new Anchor("Startseite", "Sontact.html");
		HTML copyrightText1 = new HTML(" | ");
		HTML copyrightText2 = new HTML(" | © 2018 Sontact, IT-Projekt Gruppe01, Hochschule der\n" + "Medien Stuttgart | ");
		Anchor reportGeneratorLink = new Anchor (" ReportGenerator", "SontactReport.html");
	//	Anchor impressumLink = new Anchor("Impressum");
		footer.add(startseite);
		footer.add(copyrightText1);
		footer.add(reportGeneratorLink);
		footer.add(copyrightText2);

		RootPanel.get("footer").add(footer);

	}

	void loadLogin() {		
		signInLink.setHref(loginInfo.getLoginUrl());
	//	loginPanel.add(loginHeader);
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		loginPanel.addStyleName("login");
		
		RootPanel.get("content").add(loginPanel);			

	}

	/**
	 * ClickHandler fürs Label um auf das eigene Profil zugelangen.
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
}
