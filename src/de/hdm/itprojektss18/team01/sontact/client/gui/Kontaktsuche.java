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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

public class Kontaktsuche extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private CellTable<Kontakt> searchTable;
	final SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	final ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

	ListBox lb = new ListBox();
	TextBox tb = new TextBox();

	private Button search;
	private Button showKontakt;

	HorizontalPanel hp3 = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	HorizontalPanel searchbar = new HorizontalPanel();
	HorizontalPanel showKontaktButtonPanel = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();

	/**
	 * Konstruktor wird ausgelöst man einen Kontakt des Nutzers übergibt um die
	 * Eigenschaften mit ihren Ausprägung anzeigen zu lassen.
	 */
	public Kontaktsuche(final Nutzer n, String auswahl, String eingabe) {

		RootPanel.get("content").clear();

		head.add(new HTML("<h4>Ergebnis der Suche: </h4>"));
		RootPanel.get("content").add(head);
		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n, auswahl, eingabe);

	}

	/**
	 * Diese Methode wird vom Konstruktor der Klasse geladen wenn die Klasse
	 * instanziiert wird. Hier wird die suche für Kontakten realisiert und in eine
	 * Tabellenstrukur dargestellt.
	 * 
	 * @param n
	 *            der akteulle Nutzer des Systems.
	 * @param auswahl
	 *            aus der ListBox gewählter Parameter
	 * @param eingabe
	 *            aus der TexBox eingegebener Wert
	 */
	protected void onLoad(final Nutzer n, String auswahl, String eingabe) {

		/**
		 * Initialisierung des Labels und eines CellTabels für die Kontakte
		 */
		searchTable = new CellTable<Kontakt>();

		/**
		 * Die if-Abfrage untersucht was in der Listbox ausgewähöt wurde und sucht
		 * anhand der TextBox eingabe nach Kontakten des Nutzers. Es werden eigene und
		 * auch geteilte/ mit dem Nutzer geteilte Kontakte herausgefiltert und
		 * angezeigt.
		 */
		if(eingabe == "") {
			Window.alert("Upps, es wurde keine Eingabe registriert.");
		}
		else if (auswahl == "Name") {
			ev.getKontakteByName(eingabe, n, new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					if (result.size() == 0) {
						showKontaktButtonPanel.setVisible(false);
						Window.alert("Für den gesuchten Kontakt " + eingabe + " gabe es keine treffer");
						
					} else {
						searchTable.setVisible(true);
						sp.setVisible(true);
						hp3.setVisible(true);
					}
					searchTable.setRowCount(result.size(), true);
					searchTable.setVisibleRange(0, 10);
					searchTable.setRowData(result);

				}

			});
			// Kontakte anhand der Ausprägungen suchen.
		} else if (auswahl == "Auspraegung") {
			ev.getKontakteByAuspraegung(eingabe, n, new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					if (result.size() == 0) {
						showKontaktButtonPanel.setVisible(false);
						Window.alert("Für die gesuchte Ausprägung " + eingabe + " gabe es keine treffer");
					
					} else {
						searchTable.setVisible(true);
						sp.setVisible(true);
						hp3.setVisible(true);
					}
					searchTable.setRowCount(result.size(), true);
					searchTable.setVisibleRange(0, 10);
					searchTable.setRowData(result);

				}

			});
			// Kontakte anhand der Eigenschaften suchen.
		} else if (auswahl == "Eigenschaft") {
			ev.getKontakteByEigenschaft(eingabe, n, new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					if (result.size() == 0) {
						showKontaktButtonPanel.setVisible(false);
						Window.alert("Für die gesuchte Eigenschaft " + eingabe + " gabe es keine treffer");
					} else {
						searchTable.setVisible(true);
						sp.setVisible(true);
						hp3.setVisible(true);
					}
					searchTable.setRowCount(result.size(), true);
					searchTable.setVisibleRange(0, 10);
					searchTable.setRowData(result);

				}

			});

		} else {
			Window.alert("Tut uns leid, Sie haben keinen Kontakt mit dieser " + eingabe + " Angabe.");
		}

		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt Informationen.
		 */

		TextColumn<Kontakt> vornameColumn = new TextColumn<Kontakt>() {

			@Override
			public String getValue(Kontakt vorname) {
				// TODO Auto-generated method stub
				return (String) vorname.getVorname();
			}
		};

		TextColumn<Kontakt> nachnameColumn = new TextColumn<Kontakt>() {

			@Override
			public String getValue(Kontakt nachname) {
				// TODO Auto-generated method stub
				return (String) nachname.getNachname();
			}
		};

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere Kontakten
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
		searchTable.addColumn(vornameColumn, "Vorname: ");
		vornameColumn.setSortable(true);

		searchTable.addColumn(nachnameColumn, "Nachname: ");
		nachnameColumn.setSortable(true);
		
		

		searchTable.setColumnWidth(checkColumn, 40, Unit.PX);
		searchTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		searchTable.setWidth("80%", true);
		searchTable.setColumnWidth(vornameColumn, "100px");
		searchTable.setColumnWidth(nachnameColumn, "100px");

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(searchTable);
		searchTable.addColumnSortHandler(sort);
		searchTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createDefaultManager());
		
		this.search = new Button("<image src='/images/search.png' width='15px' height='15px' align='center' />  Start");
		this.showKontakt = new Button("<image src='/images/user.png' width='20px' height='20px' align='center' />  anzeigen");
		
		// Größe der TextBox/ListBox/Button bestimmen.
		lb.setPixelSize(130, 35);
		tb.setPixelSize(125, 25);
		search.setPixelSize(125, 25);

		// ListBox mit Auswahlen befüllen.
		lb.addItem("Name");
		lb.addItem("Auspraegung");
		lb.addItem("Eigenschaft");
		tb.getElement().setPropertyString("placeholder", "Name/Ausprägung/Eigenschaft");

		// Der SearchBar den Button, die ListBox und die TextBox hinzufügen.
		searchbar.add(search);
		searchbar.add(lb);
		searchbar.add(tb);
		showKontaktButtonPanel.add(showKontakt);

		// Tabelle dem Vertical Panel hinzufügen
		this.add(searchbar);
		// Tabelle dem Vertical Panel hinzufügen
		this.add(searchTable);
		
		// Größe des ScrollPanels bestimmen plus in das ScrollPanel die CellTable
		// hinzufügen.
		sp.setSize("900px", "400px");
		sp.add(searchTable);
		
		this.add(sp);
		this.add(hp3);
		this.add(showKontaktButtonPanel);
		
		
	

		// ClickHandler für die persönliche suche von anderen Kontakten.
		search.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				RootPanel.get("content").add(new Kontaktsuche(n, lb.getSelectedItemText(), tb.getText()));
			}
		});
		
		
		// ClickHandler für die persönliche suche von anderen Kontakten.
		showKontakt.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if (selectionModel.getSelectedObject() == null) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie einen Kontakt aus.");
				} else {
					RootPanel.get("content").clear();
					ev.getKontaktById(selectionModel.getSelectedObject().getId(), new AsyncCallback<Kontakt>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Der ausgewählte Kontakt konnte nicht angezeigt werden.");

						}

						@Override
						public void onSuccess(Kontakt result) {

						}
					});
					clear();
					add(new KontaktForm(selectionModel.getSelectedObject()));

				}
			}
		});
		

	}

	/**
	 * KeyProvider für Kontakt Objekte zum identifizieren.
	 */
	public static final ProvidesKey<Kontakt> KEY_PROVIDER = new ProvidesKey<Kontakt>() {

		@Override
		public Object getKey(Kontakt item) {
			return item == null ? null : item.getId();
		}
	};

	/**
	 * Abfrage ob der KeyProvider null ist oder nicht.
	 * 
	 * @return
	 */
	public static ProvidesKey<Kontakt> getKeyProvider() {
		if (KEY_PROVIDER != null) {
			return KEY_PROVIDER;
		} else {
			return null;
		}
	}

}
