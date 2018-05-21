package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse stellt Interaktionsm�glichkeiten um Kontaktlisten zu verwalten bereit
 * und soll vorhandene Kontaktlisten anzeigen.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class ShowKontaktliste extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private CellTable<Kontaktliste> kontaktListenTable;
	final SingleSelectionModel<Kontaktliste> selectionModel = new SingleSelectionModel<Kontaktliste>();
	final ListDataProvider<Kontaktliste> dataProvider = new ListDataProvider<Kontaktliste>();
	private Label titel = new Label();

	/**
	 * Konstruktor der Klasse
	 */
	public ShowKontaktliste(final Nutzer n) {

		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n);


	}

	protected void onLoad(final Nutzer n) {

		/**
		 * Initialisierung des Labels und eines CellTabels für die Kontakte
		 */
		this.add(titel);
		kontaktListenTable = new CellTable<Kontaktliste>();

		ev.getKontaktlistenByOwner(n, new AsyncCallback<Vector<Kontaktliste>>() {

			@Override
			public void onFailure(Throwable err) {
				Window.alert("Fehler beim RPC Call" + err.toString());

			}

			/**
			 * Aufruf aller Kontaktlisten die der Nutzer erstellt hat und bei denen der als Owner
			 * hinterlegt ist.
			 */
			public void onSuccess(Vector<Kontaktliste> result) {
				if (result.size() == 0) {
					kontaktListenTable.setVisible(false);
		
				} else {
					kontaktListenTable.setVisible(true);
	
				}

				kontaktListenTable.setRowCount(result.size(), true);
				kontaktListenTable.setVisibleRange(0, 10);
				kontaktListenTable.setRowData(result);

			}
		});

		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt
		 * Informationen.
		 */
		TextColumn<Kontaktliste> ListenbezColumn = new TextColumn<Kontaktliste>() {

			@Override
			public String getValue(Kontaktliste bez) {

				return (String) bez.getTitel();
			}
		};

		

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere
		 * Kontakten.
		 */
		Column<Kontaktliste, Boolean> checkColumn = new Column<Kontaktliste, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontaktliste object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufügen der Columns für die Darstellung der Kontakte.
		 */
		kontaktListenTable.addColumn(ListenbezColumn, "Titel der Kontaktliste");
		ListenbezColumn.setSortable(true);

		
		kontaktListenTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktListenTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));

		kontaktListenTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontaktliste>createCheckboxManager());

		ListDataProvider<Kontaktliste> dataProvider = new ListDataProvider<Kontaktliste>();

		ListHandler<Kontaktliste> sort = new ListHandler<Kontaktliste>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktListenTable);
		kontaktListenTable.addColumnSortHandler(sort);

		this.add(kontaktListenTable);
//
//		/**
//		 * Erstellung von Buttons mit <code>ClickHandlern()</code> für
//		 * Interaktionen mit den Kontakten.
//		 */
//		this.deleteKontakt = new Button("Löschen");
//		this.showKontakt = new Button("Kontakt anzeigen");
//		titel.setText("Meine Kontakte");
//		this.add(hp);
//
//		/**
//		 * Button ClickHandler fürs anzeigen eines Kontaktes aus der
//		 * Kontaktliste. Die KontaktFormular Klasse wird instanzieiert.
//		 */
//		showKontakt.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//
//				Kontakt k = selectionModel.getSelectedObject();
//
//				ev.getKontaktById(k.getId(), new AsyncCallback<Kontakt>() {
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Der ausgewählte Kontakt konnte nicht angezeigt werden.");
//
//					}
//
//					@Override
//					public void onSuccess(Kontakt result) {
//
//					}
//				});
//				clear();
//				add(new KontaktFormular());
//
//			}
//		});
//
//		/**
//		 * Button ClickHandler fürs löschen eines Kontaktes aus der
//		 * Kontaktliste.
//		 */
//		deleteKontakt.addClickHandler(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//
//				Kontakt k = selectionModel.getSelectedObject();
//
//				ev.removeKontakt(k, new AsyncCallback<Void>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Hoppala" + caught.toString());
//					}
//
//					@Override
//					public void onSuccess(Void result) {
//						if (selectionModel.getSelectedSet().size() > 0) {
//							clear();
//							onLoad();
//						}
//					}
//				});
//
//			}
//		});
//
//	}

}}
