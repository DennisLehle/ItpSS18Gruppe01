package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.List;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Diese Klasse zeigt <code>Kontakt</code> Objekte eines Nutzers an. 
 * Diese werden in einem CellTable dargestellt und können von dort aus gelöscht oder angezeigt werden.
 * 
 * @author Dennis Lehle, Kevin Batista, Ugur Bayrak
 *
 */
public class ShowKontakte extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private CellTable<Kontakt> kontaktTable;
	List<Kontakt> komplettesProfil;
	final SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	final ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

	private Button deleteKontakt;
	private Button showKontakt = new Button("Kontakt anzeigen");
	private Label titel = new Label();

	HorizontalPanel hp = new HorizontalPanel();

	/**
	 * Konstruktor der Klasse
	 */
	public ShowKontakte(final Nutzer n) {

		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n);
		// Buttons die inizialisiert werden bei Start dieser Klasse
		hp.add(showKontakt);
		hp.add(deleteKontakt);
	}

	protected void onLoad(final Nutzer n) {

		/**
		 * Initialisierung des Labels und eines CellTabels für die Kontakte
		 */
		this.add(titel);
		kontaktTable = new CellTable<Kontakt>();

		ev.getAllKontakteByOwner(n, new AsyncCallback<Vector<Kontakt>>() {

			@Override
			public void onFailure(Throwable err) {
				Window.alert("Fehler beim RPC Call" + err.toString());

			}

			/**
			 * Aufruf aller Kontakt die der Nutzer erstellt hat und er als Owner hinterlegt
			 * ist.
			 */
			public void onSuccess(Vector<Kontakt> result) {
				if (result.size() == 0) {
					kontaktTable.setVisible(false);
					hp.setVisible(false);

				} else {
					kontaktTable.setVisible(true);
					hp.setVisible(true);
				}

				kontaktTable.setRowCount(result.size(), true);
				kontaktTable.setVisibleRange(0, 10);
				kontaktTable.setRowData(result);

			}
		});

		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt Informationen.
		 */
		TextColumn<Kontakt> vornameCol = new TextColumn<Kontakt>() {

			@Override
			public String getValue(Kontakt vorname) {

				return (String) vorname.getVorname();
			}
		};

		TextColumn<Kontakt> nachnameCol = new TextColumn<Kontakt>() {

			@Override
			public String getValue(Kontakt nachname) {

				return (String) nachname.getNachname();
			}
		};

		TextColumn<Kontakt> erstellungsdatumCol = new TextColumn<Kontakt>() {

			public String getValue(Kontakt erstellungsdatum) {

				return erstellungsdatum.getErstellDat().toString();
			}
		};

		TextColumn<Kontakt> modifikationsdatumCol = new TextColumn<Kontakt>() {

			public String getValue(Kontakt modifikationsdatum) {

				return modifikationsdatum.getModDat().toString();
			}
		};

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere Kontakten.
		 */
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontakt object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufügen der Columns für die Darstellung der Kontakte.
		 */
		kontaktTable.addColumn(vornameCol, "Vorname");
		vornameCol.setSortable(true);

		kontaktTable.addColumn(nachnameCol, "Nachname");
		nachnameCol.setSortable(true);

		kontaktTable.addColumn(erstellungsdatumCol, "Erstellungsdatum");
		kontaktTable.addColumn(modifikationsdatumCol, "Modifikationsdatum");

		kontaktTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));

		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);

		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> für Interaktionen mit
		 * den Kontakten.
		 */
		this.deleteKontakt = new Button("Löschen");
		this.showKontakt = new Button("Kontakt anzeigen");
		titel.setText("Meine Kontakte");
		this.add(hp);

		/**
		 * Button ClickHandler fürs anzeigen eines Kontaktes aus der Kontaktliste. Die
		 * KontaktFormular Klasse wird instanzieiert.
		 */
		showKontakt.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Kontakt k = selectionModel.getSelectedObject();

				ev.getKontaktById(k.getId(), new AsyncCallback<Kontakt>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Der ausgewählte Kontakt konnte nicht angezeigt werden.");

					}

					@Override
					public void onSuccess(Kontakt result) {

					}
				});
				clear();
				add(new KontaktFormular());

			}
		});

		/**
		 * Button ClickHandler fürs löschen eines Kontaktes aus der Kontaktliste.
		 */
		deleteKontakt.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Kontakt k = selectionModel.getSelectedObject();

				ev.removeKontakt(k, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Hoppala" + caught.toString());
					}

					@Override
					public void onSuccess(Void result) {
						if (selectionModel.getSelectedSet().size() > 0) {
							clear();
							onLoad();
						}
					}
				});

			}
		});

	}
}
