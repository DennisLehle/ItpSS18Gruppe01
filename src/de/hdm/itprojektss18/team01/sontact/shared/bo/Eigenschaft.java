package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Eigenschaft, welcher einem Kontakt
 * zugewiesen wird. Hierbei ist eine Eigenschaft immer mit einer Auspraegung
 * versehen, welche den tatsaechlichen Wert (Auspraegung) der Eigenschaft
 * beinhaltet.
 * 
 * @see <code>BusinessObject</code>
 * @see<code>Relatable</code>
 *
 */
public class Eigenschaft extends BusinessObject implements Relatable  {

	/**
	 * Eindeutige Identifikation der Version einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Eigenschaft
	 */
	private String bezeichnung;
	private Auspraegung a;
	
	
	/**
	 * Auslesen der Eigenschaft
	 * @return bezeichnung - Bezeichnung der Eigenschaft
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * Setzten der Eigenschaft
	 * @param bezeichnung - Bezeichnung der Eigenschaft 
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	/**
	 * Auslesen der Auspraegung
	 * @return a - Auspraegung der Eigenschaft
	 */
	public Auspraegung getAuspraegung() {
		return a;
	}
	
	/**
	 * Setzten der Auspraegung
	 * @param a - Auspraegung der Eigenschaft 
	 */
	public void setAuspraegung(Auspraegung a) {
		this.a = a;
	}

	public String getWert() {
		return null;
	}

	public boolean getStatus() {
		return false;
	}

	@Override
	public int getOwnerId() {
		return 0;
	}

	@Override
	public char getType() {
		return 'a';
	}

}
