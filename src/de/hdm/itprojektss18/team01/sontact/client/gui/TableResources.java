package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.cellview.client.CellTable;

/**
 * CellTabel Resources ist ein Interface zum Stylen der Celltable
 * der Sontact Kontaktverwaltung.
 * 
 * @author Dennis Lehle
 *
 */
public interface TableResources  extends CellTable.Resources {
	
	@Source({CellTable.Style.DEFAULT_CSS, "CellTable.css"})
	TableStyle cellTableStyle();
	 
	interface TableStyle extends CellTable.Style {}
	}