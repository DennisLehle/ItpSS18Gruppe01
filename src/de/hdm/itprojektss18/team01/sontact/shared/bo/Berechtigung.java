package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Berechtigungsklasse.
 */
public class Berechtigung extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Berechtigung
	 */
	private int holderId;
	private int receiverId;
	private int objectId;
	private char type;
	private int berechtigungsstufe;
	
	private int kontaktid;
	private int kontaktlisteid;
	private int auspraegungid;

	/**
	 * Konstruktoren
	 */
	public Berechtigung(int id, int b) {
		this.id = id;
		this.berechtigungsstufe = b;
	}

	public Berechtigung(int b) {
		this.berechtigungsstufe = b;
	}

	public Berechtigung() {
		this.berechtigungsstufe = 0;
	}

	public boolean isUserAllowedTo(int action) {

		return this.berechtigungsstufe >= action;
	}

	public enum Berechtigungsstufe {
		NONE, READ, EDIT, DELETE;

		public static Berechtigungsstufe fromInteger(int berechtigungsstufe) {
			switch (berechtigungsstufe) {
			case 0:
				return NONE;
			case 1:
				return READ;
			case 2:
				return EDIT;
			case 3:
				return DELETE;
			}
			return null;
		}
	}

	/**
	 * Enumerator
	 *
	 * public enum Berechtigungsstufe {
	 * 
	 * NONE(0), READ(10), EDIT(20), DELETE(30);
	 * 
	 * private int wert;
	 * 
	 * Berechtigungsstufe(int b) { wert = b; } }
	 **/

	/**
	 * Getter- und Setter-Methoden zum Setzen und Auslesen der Werte
	 */
	public int getHolderId() {
		return holderId;
	}

	public void setHolderId(int holderId) {
		this.holderId = holderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	/**
	 * muss angepasst werden
	 * 
	 * @return
	 */
	public int getBerechtigungsstufe() {
		return this.berechtigungsstufe;
	}

	public void setBerechtigungsstufe(int berechtigungsstufe) {
		this.berechtigungsstufe = berechtigungsstufe;
	}

	@Override
	public String toString() {
		return "Berechtigung [id=" + id + ", senderId=" + holderId + ", userId=" + receiverId + ", objectId=" + objectId
				+ ", type= " + type + "berechtigungsstufe= " + berechtigungsstufe + "]";
	}

	
	
	
	
	
	
	
// Getter und Setter gesetzt für Test...
	public int getKontaktid() {
		return kontaktid;
	}

	public void setKontaktid(int kontaktid) {
		this.kontaktid = kontaktid;
	}

	public int getKontaktlisteid() {
		return kontaktlisteid;
	}

	public void setKontaktlisteid(int kontaktlisteid) {
		this.kontaktlisteid = kontaktlisteid;
	}

	public int getAuspraegungid() {
		return auspraegungid;
	}

	public void setAuspraegungid(int auspraegungid) {
		this.auspraegungid = auspraegungid;
	}

}
