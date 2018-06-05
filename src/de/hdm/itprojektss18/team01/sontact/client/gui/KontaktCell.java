package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;


/**
 * Klasse zur Darstellung von Kontakt-Objekten.
 * 
 * 
 * 
 * @author dennislehle
 */
public class KontaktCell extends AbstractCell<Kontakt> {
	


	@Override
	public void render(Context context, Kontakt k, SafeHtmlBuilder sb) {
		if(k == null) {
			return;
		}

	
	  sb.appendHtmlConstant("<div>");
	  sb.appendHtmlConstant("<p><span class='glyphicon glyphicon-user'></span> &nbsp; ");
      sb.appendEscaped(k.getVorname());
      sb.appendHtmlConstant(", ");
      sb.appendEscaped(k.getNachname());
      sb.appendHtmlConstant("</div>");
      
      
      

	}
}
