package de.hdm.itprojektss18.team01.sontact.shared.bo;

import de.hdm.itprojektss18.team01.sontact.shared.bo.BusinessObject;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Nutzer extends BusinessObject implements IsSerializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Email-Adresse des Nutzers
	 */
	private String emailAddress = "";

	/**
	 * Auslesen der Email
	 * 
	 * @param emailAddress
	 */

	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzen der Email
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
