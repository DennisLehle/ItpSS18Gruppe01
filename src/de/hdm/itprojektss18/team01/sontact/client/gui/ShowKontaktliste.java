package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
 * Die Klasse ShowKontaktliste zeigt die Kontakte einer spezifischen
 * Kontaktliste in einer CellTabel an.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */
public class ShowKontaktliste extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	private CellTable.Resources tableRes = GWT.create(TableResources.class);

	private CellTable<Kontaktliste> kontaktListenTable2;
	final SingleSelectionModel<Kontaktliste> selectionModel = new SingleSelectionModel<Kontaktliste>();
	final ListDataProvider<Kontaktliste> dataProvider = new ListDataProvider<Kontaktliste>();

	HorizontalPanel hp3 = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();

	/**
	 * Konstruktor  der Klasse ShwoKontaktliste
	 * Wird aufgerufen, wenn der Nutzer einen Kontakt
	 * zu einer Kontaktliste hinzufuegen will.
	 * 
	 * @param n aktuell eingeloggter Nutzer
	 * @param k ausgewaehlter Kontakt
	 * @param ko Vector von Kontakt Objekten 
	 */
	public ShowKontaktliste(final Nutzer n, Kontakt k, Vector<Kontakt> ko) {
		
//		RootPanel.get("contentHeader").clear();
//		RootPanel.get("content").clear();
//		RootPanel.get("contentHeader")
//				.add(new HTML("<h2>Welcher Kontaktliste soll der Kontakt "+ k.getVorname() + " "+k.getNachname()+ "hinzugefügt werden?</h2>"));

		
		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n, k, ko);

	}

	/**
	 * Die Methode onLoac wird durch den Konstruktor der Klasse ShowKontaktliste
	 * aufgerufen.
	 * 
	 * @param n
	 *            der Nutzer der uebergeben wird
	 * @param k
	 *            der Kontakt der uebergeben wird
	 */
	protected void onLoad(final Nutzer n, Kontakt k, Vector<Kontakt> ko) {

		
		 // Initialisierung des Labels und eines CellTabels für die Kontakte
		kontaktListenTable2 = new CellTable<Kontaktliste>(10, tableRes);

		// Auslesen aller Kontaktlisten die der Nutzer aktuell besitzt.
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
		 * Tabelle befuellen mit den aus der DB abgerufenen Kontakt Informationen.
		 */
		TextColumn<Kontaktliste> NameDerKLColumn = new TextColumn<Kontaktliste>() {

			@Override
			public String getValue(Kontaktliste kl) {

				return (String) kl.getTitel();
			}
		};
		
		Resources resources = GWT.create(Resources.class);

		Column<Kontaktliste, ImageResource> imageColumn = new Column<Kontaktliste, ImageResource>(new ImageResourceCell()) {

			@Override
			public ImageResource getValue(Kontaktliste object) {
				if (object.getOwnerId() != n.getId()) {
					return resources.getImageResource2();
				} else {
					return null;
				}

			}
		};
		

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere Kontakten.
		 */
		Column<Kontaktliste, Boolean> checkColumn = new Column<Kontaktliste, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontaktliste object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufuegen der Columns für die Darstellung der Kontaktlisten.
		 */
		kontaktListenTable2.addColumn(NameDerKLColumn, "Kontaktlisten ");
		NameDerKLColumn.setSortable(true);

		kontaktListenTable2.addColumn(imageColumn, "Teilungsstatus ");
		NameDerKLColumn.setSortable(true);
		
		kontaktListenTable2.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktListenTable2.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktListenTable2.setWidth("97%", true);
		kontaktListenTable2.setColumnWidth(NameDerKLColumn, "200px");
		kontaktListenTable2.setColumnWidth(imageColumn, "55px");
		kontaktListenTable2.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Kontaktliste>createDefaultManager());

		ListDataProvider<Kontaktliste> dataProvider = new ListDataProvider<Kontaktliste>();

		ListHandler<Kontaktliste> sort = new ListHandler<Kontaktliste>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktListenTable2);
		kontaktListenTable2.addColumnSortHandler(sort);

		this.add(kontaktListenTable2);

		// Groeße des ScrollPanels bestimmen, außerdem wird das ScrollPanel in die CellTable
		// hinzugefuegt.
		sp.setSize("900px", "400px");
		sp.add(kontaktListenTable2);
		
		this.add(sp);
		this.add(hp3);

		// Mit doppel Klick wird der Kontakt einer Kontaktliste hinzugefuegt.
		kontaktListenTable2.addDomHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {

				// Auslesen der Kontakt aus einer Kontaktliste
				ev.getKontakteByKontaktliste(selectionModel.getSelectedObject().getId(),
						new AsyncCallback<Vector<Kontakt>>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage();
							}

							@Override
							public void onSuccess(Vector<Kontakt> result) {

								for (int i = 0; i < ko.size(); i++) {

									if (result.contains(ko.elementAt(i))) {
										MessageBox.alertWidget("Hinweis",
												"Der Kontakt " + ko.elementAt(i).getVorname()
														+ ko.elementAt(i).getNachname()
														+ " existiert bereits in dieser Kontaktliste");
									} else {
										
										// Es wird die selektierte Kontaktliste uebergeben und der Kontakt der zuvor
										// ausgewaehlt wurde. (Kostruktor uebergabe)
										ev.addKontaktToKontaktliste(selectionModel.getSelectedObject(), ko.elementAt(i),
												new AsyncCallback<Void>() {

													@Override
													public void onFailure(Throwable caught) {
														Window.alert("Hoppala" + caught.toString());
													}

													@Override
													public void onSuccess(Void result) {

													}
												});
									}
								}

							}

						});
				
				// Div's leeren und Kontaktliste anzeigen bei der die Kontakte hinzugefuegt wurden.
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				RootPanel.get("content").add(new KontaktlisteForm(selectionModel.getSelectedObject()));

			}

		}, DoubleClickEvent.getType());

	}
}
