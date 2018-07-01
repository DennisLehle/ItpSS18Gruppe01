package de.hdm.itprojektss18.team01.sontact.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung eines exemplarischen Nutzers. Ein Nutzer-Objekt wird hierbei im
 * System mit seiner Email-Adresse, mit welcher er sich registriert, versehen.
 * 
 * @see <code>BusinessObject</code>
 * 
 */
public class Nutzer extends BusinessObject implements IsSerializable {

	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariable der Klasse Nutzer
	 */
	private String emailAddress = "";

	
	/**
	 * Auslesen der Email-Adresse des Nutzers
	 * @return emailAddress - Email-Adresse des Nutzers
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzen der Email
	 * @param emailAddress des Nutzers
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
