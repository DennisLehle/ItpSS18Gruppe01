package de.hdm.itprojektss18.team01.sontact.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{
	public Nutzer login(String requestUri);
}
