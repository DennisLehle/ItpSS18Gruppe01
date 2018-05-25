package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;

/**
 * Stellt die Zelle einer Kontaktliste dar.
 * WIrd für das TreeViewModel benötigt.
 * 
 * @author dennislehle
 *
 */
public class KontaktlisteCell extends AbstractCell<Kontaktliste> {


	@Override
	public void render(Context context, Kontaktliste kl, SafeHtmlBuilder sb) {
		if(kl == null) {
			return;
		}

	  sb.appendHtmlConstant("<div>");
	  sb.appendHtmlConstant("<p><span class='glyphicon glyphicon-list-alt'></span> &nbsp; ");
      sb.appendEscaped(kl.getTitel());
      sb.appendHtmlConstant("</div>");

	}
}
