package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.sql.Timestamp;


/**
 * Realisierung eines exemplarischen Kontakts. Ein Kontakt-Objekt referenziert einen Nutzer als 
 * Eigentuemer und besitzt einen Vor- und Nachnamen, ein Erstellungs-
 * und Modifikationsdatum sowie einen Objekt-Type.
 * 
 * @see <code>BusinessObject</code>
 * 
 */
public class Kontakt extends BusinessObject {

	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Kontakt
	 */
	private String vorname;
	private String nachname;
	private Timestamp erstellDat;
	private Timestamp modDat;
	private int ownerId;
	private char identifier;
	private char type = 'k';
	private int KontaktlisteId;

	
	/**
	 * Auslesen des Vornamens
	 * @return vorname des Kontakts
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setzten des Vornamens
	 * @param vorname des Kontakts
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Auslesen des Nachnamens
	 * @return nachname des Kontakts 
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Setzten des Nachnamens
	 * @param nachname des Kontakts
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Auslesen des Erstellungsdatums
	 * @return erstellDat - Erstellungsdatum des Kontakts
	 */
	public Timestamp getErstellDat() {
		return erstellDat;
	}
	
	/**
	 * Setzten des Erstellungsdatums
	 * @param i erstellDat - Erstellungsdatum des Kontakts
	 */
	public void setErstellDat(Timestamp i) {
		this.erstellDat = i;
	}

	/**
	 * Auslesen des Modifikationsdatums
	 * @return modDat - Modifikationsdatum des Kontakts 
	 */
	public Timestamp getModDat() {
		return modDat;
	}

	/**
	 * Setzten des Modifikationsdatums
	 * @param modDat - Modifikationsdatum des Kontakts
	 */
	public void setModDat(Timestamp modDat) {
		this.modDat = modDat;
	}

	/**
	 * Auslesen des Eigentuemers
	 * @return ownerId - Eigentuermer des Kontakts
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * Setzten des Eigentuermers 
	 * @param ownerId - Eigentuermer des Kontakts
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
	 * @return k - Type 'k' fuer Kontakt 
	 */
	public char getType() {
		return type;
	}


	/**
	 * Auslesen des identifiers, welcher den Marker für den eigenen Kontakt des Nutzres darstellt.
	 * @return identifier - Marker fuer den eigenen Kontakt des Nutzers
	 */
	public char getIdentifier() {
		return identifier;
	}

	/**
	 * Setzten des identifiers, welcher den Marker fuer den eigenen Kontakt des Nutzers darstellt.
	 * @param identifier - Marker fuer den eigenen Kontakt des Nutzers
	 */
	public void setIdentifier(char identifier) {
		this.identifier = identifier;
	}

	
	
	// ------
	
	public int getKontaktlisteId() {
		return KontaktlisteId;
	}

	public void setKontaktlisteId(int kontaktlisteId) {
		KontaktlisteId = kontaktlisteId;
	}
	
	
	
	
}
