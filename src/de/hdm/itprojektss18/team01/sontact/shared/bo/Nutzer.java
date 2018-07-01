package de.hdm.itprojektss18.team01.sontact.shared.bo;

import de.hdm.itprojektss18.team01.sontact.shared.bo.BusinessObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Nutzer extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Email-Adresse des Nutzers
	 */
	private String emailAddress = "";

	/**
	 * Auslesen der Email
	 * @return emailAddress des Nutzers
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzen der Email
	 * 
	 * @param emailAddress des Nutzers
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
