package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen <Code>KontaktlisteKontakt</code>-Klasse,
 * welche dafuer zustaendig ist, <code>Kontakt</code>- und
 * <code>Kontaktliste</code>-Objekte zusammen zu7 fuehren. Hierbei wird jede
 * neue Zuordnung eines Kontakts einer Kontaktliste durch ein
 * <Code>KontaktlisteKontakt</code>-Objekt verzeichnet.
 * 
 * @see <code>BusinessObject</code>
 * @see <code>Kontaktliste</code>
 * @see <code>Kontakt</code>
 *
 */
public class KontaktlisteKontakt extends BusinessObject {
	
	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse KontaktlisteKontakt
	 */
	private int kontaktlisteId = 0; 
	private int kontaktId = 0;
	
	
	/**
	 * Auslesen der KontaktlisteId
	 * @return kontaktlisteId -Eindeutige Id der Kontaktliste
	 */
	public int getKontaktlisteId() {
		return kontaktlisteId;
	}

	/**
	 * Setzten der KontaktlisteId
	 * @param kontaktlisteId - Eindeutige Id der Kontaktliste
	 */
	public void setKontaktlisteId(int kontaktlisteId) {
		this.kontaktlisteId = kontaktlisteId;
	}

	/**
	 * Auslesen der KontaktId
	 * @return kontaktId - Eindeutige Id des Kontakts
	 */
	public int getKontaktId() {
		return kontaktId;
	}
	
	/**
	 * Setzten der KontaktId
	 * @param kontaktId - Eindeutige Id des Kontakts
	 */
	public void setKontaktId(int kontaktId) {
		this.kontaktId = kontaktId;
	}


}
