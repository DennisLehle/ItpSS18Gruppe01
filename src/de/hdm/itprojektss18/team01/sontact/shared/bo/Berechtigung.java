package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Berechtigungsklasse.
 */
public class Berechtigung extends BusinessObject {
	
	/*
	 * Testkommentar f¸r nicht getrackte Klasse. 
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Berechtigung
	 */
	private int ownerId;
	private int receiverId;
	private int objectId;
	private char type;
	private int berechtigungsstufe;	
	private int kontaktId;
	private int kontaktlisteId;
	private int auspraegungId;

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
	 * Getter- und Setter-Methoden zum Setzen und Auslesen der Werte
	 */
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
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
		return "Berechtigung [id=" + id + ", senderId=" + ownerId + ", userId=" + receiverId + ", objectId=" + objectId
				+ ", type= " + type + "berechtigungsstufe= " + berechtigungsstufe + "]";
	}

// Getter und Setter gesetzt f√ºr Test...
	public int getKontaktId() {
		return kontaktId;
	}

	public void setKontaktid(int kontaktId) {
		this.kontaktId = kontaktId;
	}

	public int getKontaktlisteId() {
		return kontaktlisteId;
	}

	public void setKontaktlisteId(int kontaktlisteId) {
		this.kontaktlisteId = kontaktlisteId;
	}

	public int getAuspraegungId() {
		return auspraegungId;
	}

	public void setAuspraegungId(int auspraegungId) {
		this.auspraegungId = auspraegungId;
	}

}
