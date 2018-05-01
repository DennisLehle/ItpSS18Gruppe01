package de.hdm.itprojektss18.team01.sontact.server;

import java.io.Serializable;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18.team01.sontact.shared.LoginService;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


public class LoginServiceImpl extends RemoteServiceServlet implements LoginService, Serializable{

	/**
	 * Von Eclipse automatisch generiert.
	 */
	private static final long serialVersionUID = 1L;
	

	  public Nutzer login(String requestUri)  throws IllegalArgumentException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    Nutzer loginInfo = new Nutzer();

	    if (user != null) {
	      loginInfo.setLoginIn(true);
	      loginInfo.setEmailAddress(user.getEmail());
	      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
	    } else {
	      loginInfo.setLoginIn(false);
	      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
	    }
	    return loginInfo;
	  }


}
