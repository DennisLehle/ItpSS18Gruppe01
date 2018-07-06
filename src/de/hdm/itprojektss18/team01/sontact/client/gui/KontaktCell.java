package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;

/**
 * Die Klasse KontaktCell dient zur Darstellung von Kontakt-Objekten. Diese
 * Klasse wird fuer das TreeViewModel benoetigt.
 * 
 * @author Dennis Lehle
 */

public class KontaktCell extends AbstractCell<Kontakt> {


	@Override
	public void render(Context context, Kontakt k, SafeHtmlBuilder sb) {
		if (k == null) {

			return;
		}
		
		 

		sb.appendHtmlConstant("<div>");
		sb.appendHtmlConstant("<p><span class='glyphicon glyphicon-user'></span> &nbsp; ");
		sb.appendEscaped(k.getVorname());
		sb.appendHtmlConstant(" ");
		sb.appendEscaped(k.getNachname());
		sb.appendEscaped(" ");
		if(k.getStatus()==true) {
		sb.appendHtmlConstant("<image src='/images/share.png' width='12px' height='12px' align='center'/>");
		}
		sb.appendHtmlConstant("</div>");

	}
	
}