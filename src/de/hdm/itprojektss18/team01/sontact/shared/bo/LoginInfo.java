package de.hdm.itprojektss18.team01.sontact.shared.bo;



import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginInfo implements IsSerializable {

	private boolean loggedIn = false;
	private String loginUrl = "";
	private String logoutUrl = " ";
	private String emailAddress = "";

	/**
	 * Gibt TRUE zurueck, wenn ein Nutzer angemeldet ist andernfalls wird 
	 * FALSE zurueckgegeben.
	 */

	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Gibt den Login-Status zurueck.
	 * 
	 * @return loggedIn
	 */

	public boolean getLoginIn() {
		return loggedIn;
	}

	/**
	 * Setzt den Login-Status
	 */
	public void setLoginIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Gibt die LoginUrl zurueck.
	 * 
	 * @return loginUrl
	 */

	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzt die LoginUrl
	 * 
	 * @param loginUrl die gesetzt werden soll
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Gibt die Logout zurueck.
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
	 * Gibt die Email zurueck.
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

}
