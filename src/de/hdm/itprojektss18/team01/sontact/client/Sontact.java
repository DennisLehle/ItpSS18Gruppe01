package de.hdm.itprojektss18.team01.sontact.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.gui.MessageBox;
import de.hdm.itprojektss18.team01.sontact.client.gui.Navigation;
import de.hdm.itprojektss18.team01.sontact.client.gui.RegistrierungsFormular;
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
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the Sontact application.");
	private Anchor signInLink = new Anchor("Sign In");

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
					editorVerwaltung.findNutzerByEmail(loginInfo.getEmailAddress(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable error) {
							Window.alert("Es ist ein Fehler beim Login aufgetreten: ");

						}

						@Override
						public void onSuccess(final Nutzer nutzer) {
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
										editorVerwaltung.findNutzerByEmail(n.getEmailAddress(),
												new AsyncCallback<Nutzer>() {

													@Override
													public void onFailure(Throwable error) {
														Window.alert("Es ist ein Fehler beim Login aufgetreten: ");

													}

													@Override
													public void onSuccess(final Nutzer nutzer) {
														clientSettings.setCurrentNutzer(nutzer);
														RootPanel.get("content")
																.add(new RegistrierungsFormular(nutzer));

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

		RootPanel.get("navigator").add(new Navigation(nutzer));
		RootPanel.get("nutzermenu")
				.add(new HTML("<p><span class='fa fa-user-circle-o'></span> &nbsp; " + nutzer.getEmailAddress()));
		HTML signOutLink = new HTML(
				"<p><a href='" + loginInfo.getLogoutUrl() + "'><span class='fas fa-sign-out-alt'></span></a></p>");
		RootPanel.get("nutzermenu").add(signOutLink);

		clientSettings.setCurrentNutzer(nutzer);
		RootPanel.get("navigator").add(new Navigation(clientSettings.getCurrentNutzer()));
		
		//Identifizierung des Registrierungs Kontakts des Nutzers für Namens Setzung in der Gui.
		editorVerwaltung.getOwnKontakt(nutzer, new AsyncCallback <Kontakt>() {

			@Override
			public void onFailure(Throwable err) {
				err.getMessage().toString();
				
			}

			@Override
			public void onSuccess(Kontakt result) {
				RootPanel.get("nutzermenu").add(new HTML("<p><span class='fa fa-user-circle-o'></span> &nbsp; " + result.getVorname() +" "+ result.getNachname()));
				HTML signOutLink = new HTML("<p><a href='" 
						+ loginInfo.getLogoutUrl() 
						+ "'><span class='fas fa-sign-out-alt'></span></a></p>");
				RootPanel.get("nutzermenu").add(signOutLink);
			}
			
			
			
		});
		

		RootPanel.get("content").add(new ShowKontakte(nutzer));
		HorizontalPanel footer = new HorizontalPanel();
		Anchor startseite = new Anchor("Startseite", "Sontact.html");
		HTML copyrightText1 = new HTML(" | ");
		HTML copyrightText2 = new HTML(" | © 2018 Sontact, IT-Projekt Gruppe01, Hochschule der\n" + 
				"					Medien Stuttgart | ");
		Anchor reportGeneratorLink = new Anchor (" ReportGenerator", "SontactReport.html");
		Anchor impressumLink = new Anchor("Impressum");
		footer.add(startseite);
		footer.add(copyrightText1);
		footer.add(reportGeneratorLink);
		footer.add(copyrightText2);

		RootPanel.get("footer").add(footer);

	}

	void loadLogin() {
		// Assemble login panel.
		VerticalPanel loginPanel = new VerticalPanel();
	//	HTML loginHeader = new HTML("<h1>Sontact Login</h1>");
		Label loginLabel = new Label("Nur ein Schritt trennt Sie noch von der Kontaktverwaltung. Melden Sie sich jetzt mit einem Google-Konto an, um Sontact nutzen zu können.");
		Anchor signInLink = new Anchor("Mit Google anmelden");
		
		signInLink.setHref(loginInfo.getLoginUrl());
	//	loginPanel.add(loginHeader);
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		loginPanel.addStyleName("signInLink");
		
		RootPanel.get("content").add(loginPanel);			

	}

}
