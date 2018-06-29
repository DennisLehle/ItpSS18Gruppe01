package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Dieses Interface stellt eine Resourcequelle für das Sharesymbol der 
 * geteilten Eigenschaften/Auspraegungen der <code>ShowEigenschaft</code>-Klasse dar.
 * Für mehr Infomationen nach CellTable.Resources (GWT Javadoc) googlen.
 * 
 * @author Dennis Lehle
 *
 */
public interface Resources extends ClientBundle {
	  @Source("shareAus.png")
	  ImageResource getImageResource();
	 
	}
