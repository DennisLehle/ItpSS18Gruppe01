package de.hdm.itprojektss18.team01.sontact.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.bo.LoginInfo;

@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService{
	public LoginInfo login(String requestUri);
}
