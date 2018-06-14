package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;

/**
 * Stellt die Zelle einer Kontaktliste dar.
 * Wird für das TreeViewModel benötigt.
 * 
 * @author dennislehle
 *
 */
public class KontaktlisteCell extends AbstractCell<Kontaktliste> {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	boolean b = false;
	
	@Override
	public void render(Context context, Kontaktliste kl, SafeHtmlBuilder sb) {
		if(kl == null) {
			return;
		}

	  sb.appendHtmlConstant("<div>");
	  sb.appendHtmlConstant("<image src='/images/kontaktliste.png' width='15px' height='15px' align='center' /> ");
      sb.appendEscaped(kl.getTitel());
      sb.appendHtmlConstant("</div>");
      
      
	

}}
