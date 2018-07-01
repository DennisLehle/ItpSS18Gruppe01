package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Auspraegung, welche in Verbindung mit einer
 * Eigenschaft einem Kontakt zugewiesen wird. Hierbei hat ein
 * Auspraegungs-Objekt einen Wert, welcher den tatsaechlichen Wert der
 * Eigenschaft darstellt, sowie die Verweise eigenschaftId für die dazugehoerige
 * Eigenschaftsbezeichnung, kontaktId fuer den dazugehoerigen Kontakt der
 * Eigenschaft, den status um zu prüfen, die Auspraegung geteilt ist, die
 * ownerId fuer den Eigentuemer/Ersteller der Auspraegung und den Objekt-type.
 * 
 * @see <code>BusinessObject</code>
 * @see<code>Relatable</code>
 * 
 */

public class Auspraegung extends BusinessObject implements Relatable {

	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzvariablen der Klasse Auspraegung
	 */
	private String wert;
	private int eigenschaftId;
	private int kontaktId;
	private boolean status;
	private Eigenschaft eigenschaft = new Eigenschaft();
	private int ownerId;
	private char type = 'a';

	/**
	 * Auslesen des Werts
	 * @return wert - Wert der Auspraegung
	 */
	public String getWert() {
		return wert;	
	}
	
	/**
	 * Setzten des Werts
	 * @param wert - Wert der Auspraegung
	 */
	public void setWert (String wert) {
		this.wert = wert;
	}
	
	/**
	 * Auslesen der EigenschaftId
	 * @return eigenschaftId - Eindeutige Id der Eigenschaft
	 */
	public int getEigenschaftId() {
		return eigenschaftId;
	}
	
	/**
	 * Setzten der EigenschaftId
	 * @param eigenschaftId - Eindeutige Id der Eigenschaft
	 */
	public void setEigenschaftId (int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}
	
	/**
	 * Auslesen des KontaktId
	 * @return kontaktId, Kontakt, welchem die Auspraegung zugeordnet ist
	 */
	public int getKontaktId () {
		return kontaktId;
	}
	
	/**
	 * Setzten der KontaktId
	 * @param kontaktId - Kontakt, welchem die Auspraegung zugeordnet ist
	 */
	public void setKontaktId (int kontaktId) {
		this.kontaktId = kontaktId;
	}

	/**
	 * Auslesen des Objekt-Types. Objekte, welche von Nutzern mit Nutzern geteilt werden koennen - 
	 * srich Kontakte, Kontaktlisten und Auspraegungen, besitzen einen Type,
	 * um in Verbindung mit der BusinessObject-Id die Eindeutigkeit zu gewaehrleisten.
	 * @see <code>Berechtigung</code>
	 * @return a - Type 'a' fuer Auspraegung 
	 */
	public char getType() {
		return type;
	}
	
	/**
	 * Setzten der Bezeichnung der Eigenschaft 
	 * @param bez - Bezeichnung der Eigenschaft
	 */
	public void setBezeichnung(String bez) {
		this.eigenschaft.setBezeichnung(bez);
	}
	
	/**
	 * Auslesen der Bezeichnung der Eigenschaft
	 * @return bezeichnung - Bezeichnung der Eigenschaft
	 */
	@Override
	public String getBezeichnung() {
		return this.eigenschaft.getBezeichnung();
	}

	/**
	 * Auslesen des Statuses der Auspraegung auf true oder false
	 * @return status - Status, ob jene Auspraegung geteilt ist
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Setzt des Statuses der Auspraegung auf true oder false
	 * @param status - Status, ob jene Auspraegung geteilt ist
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Auslesen des Eigentuemers/ Ersteller
	 * @return ownerId - Eigentuermer/ Ersteller der Auspraegung
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * Setzten des Eigentuermers 
	 * @param ownerId - Eigentuemer/ Ersteller der Auspraegung
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}


}
