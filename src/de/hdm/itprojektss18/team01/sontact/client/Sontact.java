package de.hdm.itprojektss18.team01.sontact.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sontact implements EntryPoint {
	
	  private Nutzer loginInfo = null;
	  private VerticalPanel loginPanel = new VerticalPanel();
	  private Button bt = new Button();
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
				Window.alert("Fehler Login: " + error.toString());
				
			}

			@Override
			public void onSuccess(Nutzer result) {
				loginInfo = result;
		        if(loginInfo.isLoggedIn()) {
		        } else {
		          loadLogin();
				
			}
	    	
//	    
	    	
			}
		});
	}

        void loadLogin() {
	    // Assemble login panel.
	    signInLink.setHref(loginInfo.getLoginUrl());
	    loginPanel.add(loginLabel);
	    loginPanel.add(signInLink);
	    RootPanel.get("First").add(loginPanel);
	  }
        
	}
//	    }
////		
//		loginPanel.add(loginLabel);
//		loginPanel.add(bt);
//		RootPanel.get("Details").add(loginPanel);
//		
//		loginInfo = result;
//        if(loginInfo.isLoggedIn()) {
//        } else {
//          loadLogin();
	  
	  
	  // Window.alert("Fehler Login: " + error.toString());
////	  
	  
	 

