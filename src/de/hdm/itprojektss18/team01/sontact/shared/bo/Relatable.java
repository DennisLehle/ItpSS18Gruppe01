package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.io.Serializable;


/**
 * Dieses Interface führt die Eigenschaft und Ausprägungsklassen als 
 * einen Datentyp zusammen um später in der CellTabel dagestellt werden zu können.
 * @author dennislehle
 *
 */
public interface Relatable extends Serializable {
	
	//Id des Objektes (Eindeutig)
	int getId();
	//Gibt die Bezeichnung einer Eigenschaft zurück
	String getBezeichnung();
	//Gibt die Ausprägung einer Eigenschaft zurück.
	String getWert();
	
	
	

}
