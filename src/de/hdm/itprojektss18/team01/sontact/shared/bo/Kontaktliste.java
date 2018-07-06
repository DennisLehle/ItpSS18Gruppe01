package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Kontaktliste. Ein Kontaktlisten-Objekt
 * zeichnet sich durch einen Titel, einem Eigentuemer, welcher durch einen
 * Nutzer referenziert ist, und seinem Objekt-Type aus.
 * 
 * @see <code>BusinessObject</code>
 * 
 */
public class Kontaktliste extends BusinessObject {
	
	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Kontaktliste
	 */
	private String titel;
	private int ownerId;
	private char type = 'l';
	private boolean status;
	

	/**
	 * Auslesen des Titels
	 * @return titel - Titel der Kontaktliste 
	 */
	public String getTitel() {
		return titel;
	}
	
	/**
	 * Setzten des Titels
	 * @param titel - Titel der Kontaktliste 
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	/**
	 * Auslesen des Eigentuemers
	 * @return ownerId - Eigentuermer der Kontaktliste 
	 */
	public int getOwnerId() {
		return ownerId;
	}
	
	/**
	 * Setzten des Eigentuermers 
	 * @param ownerId - Eigentuermer der Kontaktliste
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Auslesen der BusinessObject-Id
	 * @return id - Eindeutige id des Kontakts
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 * Auslesen des Objekt-Types. Objekte, welche von Nutzern mit Nutzern geteilt werden koennen - 
	 * srich Kontakte, Kontaktlisten und Auspraegungen, besitzen einen Type,
	 * um in Verbindung mit der BusinessObject-Id die Eindeutigkeit zu gewaehrleisten.
	 * (siehe <code>Berechtigung</code>)
	 * @return l - Type 'l' fuer Kontaktliste 
	 */
	public char getType() {
		return type;
	}

	/**
	 * Auslesen des Statuses
	 * @return status - Teilung des Objekts
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Setzten des Statuses 
	 * @param status - Teilung des Objekts setzen
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}


}