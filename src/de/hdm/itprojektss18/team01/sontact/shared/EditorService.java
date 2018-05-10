package de.hdm.itprojektss18.team01.sontact.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;





@RemoteServiceRelativePath("editorservice")
public interface EditorService extends RemoteService{
	
	
	public void init() throws IllegalArgumentException;

}
