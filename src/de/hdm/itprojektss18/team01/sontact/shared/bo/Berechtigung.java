package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Berechtigungsklasse.
 */
public class Berechtigung extends BusinessObject {
	
	/*
	 * Testkommentar fuer nicht getrackte Klasse. 
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Berechtigung
	 */
	private int ownerId;
	private int receiverId;
	private int objectId;
	private char type;	

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


}
