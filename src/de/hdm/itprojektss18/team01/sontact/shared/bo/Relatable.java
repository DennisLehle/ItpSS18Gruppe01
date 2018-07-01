package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.io.Serializable;

/**
 * Das Interface <code>Relatable</code> wird von den Klassen
 * <code>Eigenschaft</code> und <code>Auspraegung</code> implementiert und dient
 * als Hilfestellung, um die Werte der jeweiligen Objekte - sprich die
 * Bezeichnung der Eigenschaft sowie den Wert der Auspraegung - in einem neuen
 * Datentyp <code>Relatable</code> zu repraesenteiren.
 * 
 * @see <code>Eigenschaft</code>
 * @see <code>Auspraegung</code>
 *
 */
public interface Relatable extends Serializable {
	

	/**
	 * Auslesen der Id
	 * @return id - Eindeutige id
	 */
	int getId();

	/**
	 * Auslesen der Bezeichnung der Eigenschaft
	 * @return bezeichnung - Bezeichnung der Eigenschaft
	 */
	String getBezeichnung();

	/**
	 * Auslesen des Werts der Auspraegung
	 * @return wert - Wert der Auspraegung
	 */
	String getWert();

	/**
	 * Auslesen des Statuses
	 * @return status - Status, ob jenes Objekt geteilt ist
	 */
	boolean getStatus();
	
	/**
	 * Auslesen des Eigentuemers/ Erstellers 
	 * @return ownerId - Eigentuemer/ Ersteller der Auspraegung/Eigenschaft
	 */
	int getOwnerId();
	
	/**
	 * Auslesen des Objekt-Types 
	 * @return type - Type des Objekts
	 */
	char getType();
	
	
	

}
