package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.io.Serializable;

/**
 * Realisierug des Interfaces Participation.
 * Klassen, welches diese Interface implementieren, zeichnen ihre Instanzen für andere Nutzer 
 * als Teilbar aus.
 */

public interface Participation extends Serializable {

	// Gibt die eindeutige ID des Objekts zurueck. 
	int getId();
	
	// Gibt den Ursprünglichen Eigentuemer des Objekts zurueck.
	int getOwner();
	
	// Gibt den Typ des geteilten Obejkts zurück.
	char getType();
		
	// Gibt die Berechtigungsfreigabe zurück, welches die aktuellen Befugnisse eines Nutzers zu diesem Objekt darstellt.
	Berechtigung getBerechtigung();
	
}

