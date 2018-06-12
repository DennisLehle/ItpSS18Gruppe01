package de.hdm.itprojektss18.team01.sontact.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.itprojektss18.team01.sontact.shared.bo.LoginInfo;

/**
 * Asynchrones Gegenstueck des Interfaces <code>LoginService</code>
 *
 */
public interface LoginServiceAsync{

	public void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
