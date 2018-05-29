package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
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
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse zeigt die Kontakte einer Spezifischen Kontaktliste in einer CellTabel da.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class ShowKontaktliste extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private CellTable<Kontaktliste> kontaktListenTable2;
	final SingleSelectionModel<Kontaktliste> selectionModel = new SingleSelectionModel<Kontaktliste>();
	final ListDataProvider<Kontaktliste> dataProvider = new ListDataProvider<Kontaktliste>();


	private Button addKontaktToKontaktliste;

	
	HorizontalPanel hp3 = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();
	

	/**
	 * Konstruktor wird ausgelöst wenn ein Kontakt bereits existiert.
	 */
	public ShowKontaktliste(final Nutzer n, Kontakt k) {
		RootPanel.get("content").clear();
		head.add(new HTML("<h2>Welcher Kontaktliste soll der Kontakt hinzugefügt werden?</h2>"));
		RootPanel.get("content").add(head);
		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n, k);

		hp3.add(addKontaktToKontaktliste);

		

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
		kontaktListenTable2 = new CellTable<Kontaktliste>();
	
		//Auslesen aller Kontaktlisten die der Nutzer aktuell besitzt.
		ev.getKontaktlistenByOwner(n, new AsyncCallback<Vector<Kontaktliste>>() {
			
			@Override
			public void onFailure(Throwable err) {
				Window.alert("Fehler beim RPC Call" + err.toString());

			}

			/**
			 * Aufruf aller Kontaktlisten die der Nutzer erstellt hat und bei denen der als
			 * Owner hinterlegt ist.
			 */
			public void onSuccess(Vector<Kontaktliste> result) {
				if (result.size() == 0) {
					kontaktListenTable2.setVisible(false);
					hp3.setVisible(false);
					Window.alert("Leider existieren noch keine Kontakte, füge doch gleich welche hinzu :)");
					
				} else {
					kontaktListenTable2.setVisible(true);
					sp.setVisible(true);
					hp3.setVisible(true);

				}
				kontaktListenTable2.setRowCount(result.size(), true);
				kontaktListenTable2.setVisibleRange(0, 10);
				kontaktListenTable2.setRowData(result);

			}
		});


		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt Informationen.
		 */
		TextColumn<Kontaktliste> NameDerKLColumn = new TextColumn<Kontaktliste>() {

			@Override
			public String getValue(Kontaktliste kl) {

				return (String) kl.getTitel();
			}
		};

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere Kontakten.
		 */
		Column<Kontaktliste, Boolean> prüfeColumn = new Column<Kontaktliste, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontaktliste object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufügen der Columns für die Darstellung der Kontaktlisten.
		 */
		kontaktListenTable2.addColumn(NameDerKLColumn, "Kontaktlisten: ");
		NameDerKLColumn.setSortable(true);

		kontaktListenTable2.setColumnWidth(prüfeColumn, 40, Unit.PX);
		kontaktListenTable2.addColumn(prüfeColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktListenTable2.setWidth("80%", true);
		kontaktListenTable2.setColumnWidth(NameDerKLColumn, "100px");
		kontaktListenTable2.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontaktliste>createCheckboxManager());

		ListDataProvider<Kontaktliste> dataProvider = new ListDataProvider<Kontaktliste>();

		ListHandler<Kontaktliste> sort = new ListHandler<Kontaktliste>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktListenTable2);
		kontaktListenTable2.addColumnSortHandler(sort);
		
		this.add(kontaktListenTable2);
		
		this.addKontaktToKontaktliste = new Button("Kontaktliste hinzufügen");

		//Größe des ScrollPanels bestimmen plus in das ScrollPanel die CellTable hinzufügen.
		sp.setSize("900px", "400px");
		sp.add(kontaktListenTable2);
		this.add(sp);
		this.add(hp3);
		//RootPanel.get("content").add(hp3);
		//RootPanel.get("content").add(this);

		/**
		 * Button ClickHandler zum hinzufügen eines Kontaktes einer speziellen Kontaktliste.
		 */
		addKontaktToKontaktliste.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				//Selektierte Kontaktliste
				Kontaktliste kl = selectionModel.getSelectedObject();
				//Sicherheitsabfrage ob selektierter Kontakt null ist.
				if(kl == null) {
					Window.alert("Bitte wähle eine Kontaktliste aus.");
				} else {
				//Es wird die selektierte Kontaktliste übergeben und der Kontakt der zuvor ausgewählt wurde. (Kostruktor übergabe)
				ev.addKontaktToKontaktliste(kl, k, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Hoppala" + caught.toString());
					}

					@Override
					public void onSuccess(Void result) {
							Window.Location.reload();
						
					}
				});
				}
			}

		});

	}

}

