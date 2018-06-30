package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.io.Serializable;


/**
 * Dieses Interface führt die Eigenschaft und Ausprägungsklassen als 
 * einen Datentyp zusammen um später in der CellTabel dagestellt werden zu können.
 * 
 * @author Dennis Lehle
 *
 */
public interface Relatable extends Serializable {
	
	//Id des Objektes (Eindeutig)
	int getId();
	//Gibt die Bezeichnung einer Eigenschaft zurueck.
	String getBezeichnung();
	//Gibt die Auspraegung einer Eigenschaft zurueck.
	String getWert();
	//Gibt den Status der Teilung einer Auspraegung zurueck.
	boolean getStatus();
	//Gibt den EIgentümer der AUspraegung/EIgenschaft zurück.
	int getOwnerId();
	//Gibt den Typ des Objekts zurück.
	char getType();
	
	
	

}
