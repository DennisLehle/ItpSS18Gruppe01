package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Diese Klasse repräsentiert die Eigenschaften eines Kontaktes.
 * 
 *  @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */
public class ShowEigenschaften  extends VerticalPanel{
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private CellTable<Relatable> eigenschaftAuspraegungTable;
	final MultiSelectionModel<Relatable> selectionModel = new MultiSelectionModel<Relatable>(getKeyProvider());
	final ListDataProvider<Relatable> dataProvider = new ListDataProvider<Relatable>();
	Vector<Relatable> gewählteAuspraegung = new Vector<Relatable>();

	private Button shareKontakt;

	HorizontalPanel hp3 = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();
	

	/**
	 * Konstruktor wird ausgelöst man einen Kontakt des Nutzers übergibt um die 
	 * Eigenschaften mit ihren Ausprägung anzeigen zu lassen.
	 */
	public ShowEigenschaften(final Nutzer n, Kontakt k) {
		
		head.add(new HTML("<h4>Kontaktinformationen: </h4>"));
		RootPanel.get("content").add(head);
		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n, k);
		
	
		

		

	}
	/**
	 * Diese Methode wird durch den Konstruktor der Klasse aufgerufen.
	 * 
	 * @param n der Nutzer der übergeben wird
	 * @param k der Kontakt der übergeben wird
	 */
	protected void onLoad(final Nutzer n, Kontakt k) {
			
		/**
		 * Initialisierung des Labels und eines CellTabels für die Kontakte
		 */
		eigenschaftAuspraegungTable = new CellTable<Relatable>();
		
	
		//Auslesen aller Ausprägungen eines Kontakts.
		ev.getAllAuspraegungenByKontaktRelatable(k.getId(), new AsyncCallback <Vector<Relatable>>() {
		
			
			@Override
			public void onFailure(Throwable err) {
				Window.alert("Fehler beim RPC Call" + err.toString());

			}

			/**
			 * Aufruf aller Kontaktlisten die der Nutzer erstellt hat und bei denen der als
			 * Owner hinterlegt ist.
			 */
			public void onSuccess(Vector<Relatable> result) {
				if (result.size() == 0) {
					
					eigenschaftAuspraegungTable.setVisible(false);
					
					hp3.add(new HTML("<img src= images/sad.png />"));
					
					
				} else {
					eigenschaftAuspraegungTable.setVisible(true);
					sp.setVisible(true);
					hp3.setVisible(true);

				}
				eigenschaftAuspraegungTable.setRowCount(result.size(), true);
				eigenschaftAuspraegungTable.setVisibleRange(0, 10);
				eigenschaftAuspraegungTable.setRowData(result);

			}
		});
	
		

		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt Informationen.
		 */
		TextColumn<Relatable> auspraegnungColumn = new TextColumn<Relatable>() {

			@Override
			public String getValue(Relatable auspraegung) {
				// TODO Auto-generated method stub
				return (String) auspraegung.getWert();
			}
		};
		
		TextColumn<Relatable> eigenschaftColumn = new TextColumn<Relatable>() {

			@Override
			public String getValue(Relatable eigenschaft) {
				// TODO Auto-generated method stub
				return (String) eigenschaft.getBezeichnung();
			}
		};
		

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere Eigenschafen mit Ausprägungen.
		 */
		Column<Relatable, Boolean> checkColumn = new Column<Relatable, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Relatable object) {
				return selectionModel.isSelected(object);
			}
		};


		/**
		 * Hinzufügen der Columns für die Darstellung der Kontaktlisten.
		 */
		eigenschaftAuspraegungTable.addColumn(eigenschaftColumn, "Eigenschaft: ");
		eigenschaftColumn.setSortable(true);
		
		eigenschaftAuspraegungTable.addColumn(auspraegnungColumn, "Ausprägung: ");
		auspraegnungColumn.setSortable(true);
		
		eigenschaftAuspraegungTable.setColumnWidth(checkColumn, 40, Unit.PX);
		eigenschaftAuspraegungTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		eigenschaftAuspraegungTable.setWidth("80%", true);
		eigenschaftAuspraegungTable.setColumnWidth(eigenschaftColumn, "100px");
		eigenschaftAuspraegungTable.setColumnWidth(auspraegnungColumn, "100px");

		
		ListDataProvider<Relatable> dataProvider = new ListDataProvider<Relatable>();

		ListHandler<Relatable> sort = new ListHandler<Relatable>(dataProvider.getList());
		dataProvider.addDataDisplay(eigenschaftAuspraegungTable);
		eigenschaftAuspraegungTable.addColumnSortHandler(sort);
		eigenschaftAuspraegungTable.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Relatable> createCheckboxManager());
	
		
		this.add(eigenschaftAuspraegungTable);
		
		//ClickHandler zum teilen von Kontakten mit ausgewählten Ausprägungen.
		Button shareKontakt = new Button(
				"<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");
		
//		this.addKontaktToKontaktliste = new Button("<image src='/images/user.png' width='20px' height='20px' align='center' />" + "<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> hinzufügen");

		//Größe des ScrollPanels bestimmen plus in das ScrollPanel die CellTable hinzufügen.
		sp.setSize("900px", "400px");
		sp.add(eigenschaftAuspraegungTable);
		
		hp3.add(shareKontakt);
		this.add(sp);
		this.add(hp3);
		
		//ClickHandler für die persönliche suche von anderen Kontakten.
		shareKontakt.addClickHandler(new ClickHandler() {

			
			@Override
			public void onClick(ClickEvent event) {
				

				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));
				
				//Vector alle selektierten Eigenschaften/Ausprägungen mitgeben.
				gewählteAuspraegung.addAll(selectionModel.getSelectedSet());
				
				MessageBox.shareAlertKontakt("Geben Sie die Email des Empfängers an", "Email: ", k, gewählteAuspraegung);


			
			}
		});



	}
	
	
	
	
	/**
	 * KeyProvider für Relatable Objekte zum identifizieren.
	 */
	public static final ProvidesKey<Relatable> KEY_PROVIDER = new ProvidesKey<Relatable>() {
    
	@Override
	public Object getKey(Relatable item) {
		return item == null ? null : item.getId();
	}
	};

	/**
	 * Abfrage ob der KeyProvider null ist oder nicht.
	 * @return1
	 */
	public static ProvidesKey<Relatable> getKeyProvider(){
		if (KEY_PROVIDER != null){
			return KEY_PROVIDER;
		}
		else{
			return null;
		}
	}

}
