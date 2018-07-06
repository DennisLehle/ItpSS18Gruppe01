package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Dieses Interface stellt eine Resourcequelle für das Sharesymbol von geteilten
 * Eigenschaften/Auspraegungen, Kontakten oder Kontaktlisten dar.
 * 
 * Für mehr Infomationen nach CellTable.Resources (GWT Javadoc) googlen.
 * 
 * @author Dennis Lehle
 *
 */
public interface Resources extends ClientBundle {
	  //Dieses Image symbolisieet geteilte Eigenschaften/Auspraegungen
	  @Source("shareAus.png")
	  ImageResource getImageResource();
	  
	  //Dieses Image symbolisiert geteilte Kontakte
	  @Source("sharecontact.png")
	  ImageResource getImageResource1();
	  
	  //Dieses Image symbolisiert geteilte Kontaktlisten
	  @Source("sharedd.png")
	  ImageResource getImageResource2();
	 
	 
	}
