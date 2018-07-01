package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Berechtigung auf ein geteiltes Objekt.
 * Berechtigungs-Objekte könne von Nutzern für Nutzer für folgende Objekte
 * erteilt werden: <code>Kontakt</code>-, <code>Kontaktliste</code>- und
 * <code>Auspraegung</code>-Objekte.
 * 
 * Ein Berechtigungs-Objekt referenziert jeweils zwei Nutzer, den Eigentümer
 * (ownerId) und den Empfänger (reveiverId) der Berechtigung, sowie eine
 * ObjektId (objectId), welche in verbindung mit dem Objekt-Type (type)
 * eindeutig ist.
 * 
 * @see <code>BusinessObject</code>
 * @see <code>Kontaktliste</code>
 * @see <code>Kontakt</code>
 * @see <code>Auspraegung</code>
 * 
 */
public class Berechtigung extends BusinessObject {
	
	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Berechtigung
	 */
	private int ownerId;
	private int receiverId;
	private int objectId;
	private char type;	

	
	/**
	 * Auslesen des Nutzers/Eigentuemers
	 * @return ownerId - Eigentuemer der Berechtigung
	 */
	public int getOwnerId() {
		return ownerId;
	}
	
	/**
	 * Setzten des Nutzers/Eigentuemers
	 * @param ownerId - Eigentuemer der Berechtigung
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Auslesen des Nutzers/Empfaenger
	 * @return receiverId - Empfaenger der Berechtigung
	 */
	public int getReceiverId() {
		return receiverId;
	}

	/**
	 * Setzten des Nutzers/Empfaengers
	 * @param receiverId - Empfaenger der Berechtigung
	 */
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * Auslesen der ObjectId
	 * @return objectId - Eindeutige Id des geteilten Objekts
	 */
	public int getObjectId() {
		return objectId;
	}

	/**
	 * Setzten der ObjectId
	 * @param objectId - Eindeutige Id des geteilten Objekts 
	 */
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	/**
	 * Auslesen des Types des geteilten Objekts
	 * @return tyoe - Eindeutiger Type des geteilten Objekts
	 */
	public char getType() {
		return type;
	}

	/**
	 * Setzten des Types des geteilten Objekts
	 * @param type - Eindeutiger Type des geteilten Objekts 
	 */
	public void setType(char type) {
		this.type = type;
	}


}
