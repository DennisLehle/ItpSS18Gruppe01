package de.hdm.itprojektss18.team01.sontact.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.gui.Hauptansicht;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.LoginService;
import de.hdm.itprojektss18.team01.sontact.shared.LoginServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */
public class Sontact implements EntryPoint {

	private Nutzer loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the Sontact application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");


	
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	private static EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Sontact.html", new AsyncCallback<Nutzer>() {

			@Override
			public void onFailure(Throwable error) {
				//Window.alert("Fehler Login: " + error.toString());
				start();

			}



			@Override
			public void onSuccess(Nutzer result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					start();
				} else {
					loadLogin();

				}

				//

			}
		});
		
	}
	
	private void start() {
		Hauptansicht hp = new Hauptansicht();
		RootPanel.get("navigator").add(hp);

		
	}

	void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("content").add(loginPanel);
	}

}
