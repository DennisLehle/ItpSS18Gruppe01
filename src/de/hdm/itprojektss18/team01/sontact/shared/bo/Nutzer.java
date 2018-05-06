package de.hdm.itprojektss18.team01.sontact.shared.bo;

import de.hdm.itprojektss18.team01.sontact.shared.bo.BusinessObject;

import java.io.Serializable;

public class Nutzer extends BusinessObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean loggedIn = false;
	private String loginUrl = "";
	private String logoutUrl =" ";
	private String emailAddress ="";
	private String vorname ="";
	private String nachname ="";

	/**
	 * Gibt TRUE zur�ck, wenn ein Nutzer angemeldet ist andernfalls wird FALSE
	 * zur�ckgegeben.
	 */

	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Gibt den Login-Status zurück.
	 * 
	 * @return loggedIn
	 */

	public boolean getLoginIn() {
		return loggedIn;
	}

	/**
	 * Setzt den Login-Status
	 * 
	 * @return loggedIn
	 */

	public void setLoginIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Gibt die LoginUrl zurück.
	 * 
	 * @return loginUrl
	 */

	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzt die LoginUrl
	 * 
	 * @param loggedIn
	 */

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Gibt die Logout zurück.
	 * 
	 * @return logoutUrl
	 */

	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Setzt die LogoutUrl
	 * 
	 * @param logoutUrl
	 */

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Gibt die Email zurück.
	 * 
	 * @return emailAdress
	 */

	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzt die Email
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * AUslesen des Vornamens.
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setzen des Vornamens.
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	/**
	 * Auslesen des Nachnamens
	 * @return
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Setzen des Nachnamens
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

}
