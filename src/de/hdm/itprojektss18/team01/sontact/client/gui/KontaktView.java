package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;



public class KontaktView extends VerticalPanel{
	
	private EditorServiceAsync editorService = ClientsideSettings.getEditorVerwaltung();
	private CellList<Kontakt> kTable;
	private Button loeschen;
	private Button updaten;
	final SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>(KontaktWrapper.getKeyProvider());
	final ListDataProvider<Kontakt> kontaktDataProvider = new ListDataProvider<Kontakt>();
	HorizontalPanel hp = new HorizontalPanel();
	
	//Konstruktor
	public KontaktView(){
		onLoad();
		
		hp.add(loeschen);
		hp.add(updaten);
		
	}

public void onLoad() {
	

	// Create a cell to render each value.
    KontaktCell kcell = new KontaktCell();
    kTable = new CellList<Kontakt>(kcell);
    
    // Create a CellList that uses the cell.
    kTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
    

    /** Prüfung der Session bei Aufruf dieses Panels. Ist Nutzer noch eingeloggt? Wenn ja wird die Sperrliste des eingeloggten Profils
	 * ausgelesen und in einer Tabelle <code>dataTable</code> ausgegeben.
	 */		
    
    Kontakt k = new Kontakt();
    k.setId(1);
    k.setOwnerId(1);

	editorService.getKontaktById(k.getId(), new AsyncCallback<Vector<Kontakt>>() {

		@Override
		public void onFailure(Throwable err) {
			err.getMessage().toString();
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kTable.setRowData(0, result);
			
			if(result.size() == 0){
				kTable.setVisible(false);
		 		hp.setVisible(false);
		 		loeschen.setVisible(false);
		 		
			
		} else {
			hp.setVisible(true);
			kTable.setVisible(true);
		}
    	
    	
		}});
}}
