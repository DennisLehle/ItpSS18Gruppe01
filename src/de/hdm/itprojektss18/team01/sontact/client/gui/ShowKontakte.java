package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Set;
import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Diese Klasse zeigt <code>Kontakt</code> Objekte eines Nutzers an. Diese
 * werden in einem CellTable dargestellt und koennen von dort aus geloescht oder
 * angezeigt werden.
 * 
 * @author Dennis Lehle, Kevin Batista, Ugur Bayrak
 *
 */
public class ShowKontakte extends VerticalPanel {

	// Zugriff auf das asynchrone Interface
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	private CellTable.Resources tableRes = GWT.create(TableResources.class);

	Kontaktliste kontl = new Kontaktliste();

	// Tabelle eines Kontaktformulars
	private CellTable<Kontakt> kontaktTable;

	// Tabelle eines Kontaktformulars
	private CellTable<Kontakt> kontaktListenTable;

	// Auswahl von mehreren Zellen innerhalb eines Formulars
	final MultiSelectionModel<Kontakt> selectionModel = new MultiSelectionModel<Kontakt>();
	final SingleSelectionModel<Kontakt> selectionModel1 = new SingleSelectionModel<Kontakt>();
	final ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

	// Erstellung / Deklarierung von Objekten
	Vector<Kontakt> ko = new Vector<Kontakt>();
	Nutzer nutzer = new Nutzer();
	ListBox auswahl = new ListBox();
	TextBox eingabe = new TextBox();
	Boolean x = new Boolean(false);

	/**
	 * Buttons der Klasse deklarieren.
	 */
	private Button deleteKontakt;
	private Button addKontaktToKontaktliste;
	private Button addKontaktToKontaktlisteStart;
	private Button deleteKontaktFromKontaktliste;
	private Button showKontaktFromKontaktliste;
	private Button search;
	private Button abbrechen;
	private Button shareBtn;

	/**
	 * Label fuer den Titel deklarieren.
	 */
	private Label titel = new Label();
	Label erleuterung = new Label();

	/**
	 * Panels die genutzt werden deklarieren.
	 */
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	HorizontalPanel headSearch = new HorizontalPanel();
	HorizontalPanel searchbar = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();

	/**
	 * Konstruktor der Klasse fuer das Anzeigen von Kontakten einer Kontaktliste,
	 * wenn eine Kontaktliste ausgewaehlt wird
	 * 
	 * @param kl
	 *            Kontaktliste die ausgewaehlt wurde
	 */
	public ShowKontakte(Kontaktliste kl) {
		kontl = kl;

		// Leeren der Div-Container
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();

		// Div-Container wird mit dem neuen HTML-Fragment befuellt
		RootPanel.get("contentHeader").add(new HTML("<h2>Kontakt/e auswählen für<h2> " + kl.getTitel()));

		// Methode die beim Start dieser Klasse aufgerufen wird.
		showAllKontakte(kl);

		// Kontakt/e einer Kontaktliste hinzufuegen Button.
		hp.add(addKontaktToKontaktlisteStart);
		hp.add(abbrechen);

	}

	/**
	 * Konstruktor der Klasse, um meine Kontakte der Default-Kontaktliste "Meine
	 * Kontakte" beim Aufruf des Programms anzuzeigen.
	 * 
	 * @param n
	 *            aktuell eingeloggter Nutzer
	 */
	public ShowKontakte(final Nutzer n) {
		hp.clear();
		hp.add(new HTML("<h9>Übersicht aller Kontakte</h9>"));

		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n);

		// Buttons die initialisiert werden beim Aufruf dieser Klasse
		hp.add(addKontaktToKontaktliste);

	}

	/**
	 * Konstruktor der Klasse fuer das Anzeigen von Kontakten einer Kontaktliste,
	 * wenn eine Kontaktliste ausgewaehlt wird
	 * 
	 * @param n
	 *            Nutzer der uebergeben wird
	 * @param kl
	 *            Kontaktliste die ausgewaehlt wurde
	 * 
	 */
	public ShowKontakte(final Nutzer n, Kontaktliste kl) {

		// Methode die beim Start dieser Klasse aufgerufen wird.
		showKontakteOfKontaktliste(n, kl);

		hp.add(deleteKontaktFromKontaktliste);
		hp.add(shareBtn);

	}

	/**
	 * Wird vom Konstruktor aufgerufen der beim Aufruf des Programms gestartet wird.
	 * 
	 * @param n
	 *            Nutzer der uebergeben wird.
	 */
	protected void onLoad(final Nutzer n) {

		// Zuweisung der Groe�e der Kontakttabelle
		kontaktTable = new CellTable<Kontakt>(10, tableRes);

		// Der eingeloggte Nutzer wird durch Cookies markiert
		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		// Button wird mit einem Bild fuer den Suchaufruf hinterlegt
		this.search = new Button("<image src='/images/search.png' width='15px' height='15px' align='center' /> ");

		// Titel der Suche
		search.setTitle("Suchen Sie nach Kontakten anhand von Namen, Eigenschaften oder Ausprägungen");
		search.setStylePrimaryName("searchBtn");

		// ListBox mit einem Namen, einer Auspraegung und einer Eigenschaft als Auswahl
		// befuellen.
		auswahl.addItem("Name");
		auswahl.addItem("Auspraegung");
		auswahl.addItem("Eigenschaft");
		auswahl.setStylePrimaryName("search");
		eingabe.getElement().setPropertyString("placeholder", "Name/Eigenschaft/Ausprägung");
		eingabe.setStylePrimaryName("searchTb");

		// Der SearchBar den Button, die ListBox und die TextBox hinzufuegen.
		searchbar.add(search);
		searchbar.add(eingabe);
		searchbar.add(auswahl);

		allKontakte();

		/**
		 * Tabelle befuellen mit den aus der DB abzurufenden Kontaktinformationen.
		 */
		TextColumn<Kontakt> vornameCol = new TextColumn<Kontakt>() {

			// Ueberschreiben der Inhalte mit dem aktuellen Wert
			@Override
			public String getValue(Kontakt vorname) {

				return (String) vorname.getVorname();
			}
		};

		TextColumn<Kontakt> nachnameCol = new TextColumn<Kontakt>() {

			// Ueberschreiben der Inhalte mit dem aktuellen Wert
			@Override
			public String getValue(Kontakt nachname) {

				return (String) nachname.getNachname();
			}
		};

		// Stellt die Verbindung zu dem Interface her
		Resources resources = GWT.create(Resources.class);

		Column<Kontakt, ImageResource> imageColumn = new Column<Kontakt, ImageResource>(new ImageResourceCell()) {

			// Ueberschreiben der Inhalte mit dem aktuellen Wert
			@Override
			public ImageResource getValue(Kontakt object) {
				if (object.getOwnerId() != n.getId()) {
					return resources.getImageResource1();
				} else {
					return null;
				}

			}
		};

		/**
		 * Implementierung der Checkbox fuer die Auswahl, von einer oder mehrerer
		 * Kontakte.
		 */
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {

			// Ueberschreiben der Inhalte mit dem aktuellen Wert
			@Override
			public Boolean getValue(Kontakt object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufuegen der Columns fuer die Darstellung der Kontakte.
		 */
		kontaktTable.addColumn(vornameCol, "Vorname");
		vornameCol.setSortable(true);

		kontaktTable.addColumn(nachnameCol, "Nachname");
		nachnameCol.setSortable(true);

		kontaktTable.addColumn(imageColumn, "Teilungsstatus");
		nachnameCol.setSortable(true);

		// Die Groesse einer Spalte innerhalb der Kontakttabelle definieren
		kontaktTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktTable.setWidth("97%", true);
		kontaktTable.setColumnWidth(vornameCol, "150px");
		kontaktTable.setColumnWidth(nachnameCol, "150px");
		kontaktTable.setColumnWidth(imageColumn, "55px");

		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createDefaultManager());

		// Alle Daten vom Server werden hier gespeichert
		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);
		this.add(hp);

		// Kontakt Tabelle dem Container hinzufuegen.
		this.add(searchbar);
		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> fuer Interaktionen
		 * mit den Kontakten.
		 */
		this.deleteKontakt = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> löschennnn");

		// Button der fuer das Hinzufuegen eines Kontakts einer Kontaktliste zustaendig
		// ist
		this.addKontaktToKontaktliste = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' />"
						+ "<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> hinzufügen");
		addKontaktToKontaktliste.setStylePrimaryName("khunzufuegenKl");
		addKontaktToKontaktliste.setTitle("Kontakt/e einer Kontaktliste hinzufügen");

		// Groesse des Buttons
		sp.setSize("800px", "500px");
		sp.add(kontaktTable);
		this.add(sp);

		// Laesst Kontakte mit einem doppelten Klick anzeigen.
		kontaktTable.addDomHandler(new DoubleClickHandler() {

			// Das Ereignis nach Aufruf des Klicks
			@Override
			public void onDoubleClick(DoubleClickEvent event) {

				if (selectionModel.getSelectedSet().size() > 1) {
					MessageBox.alertWidget("Hinweis", "Bitte nur ein Kontakt auswählen.");
				} else {
					Set<Kontakt> set = selectionModel.getSelectedSet();
					final Vector<Kontakt> kontakte = new Vector<Kontakt>(set);

					ev.getKontaktById(kontakte.get(0).getId(), new AsyncCallback<Kontakt>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Der ausgewählte Kontakt konnte nicht angezeigt werden.");

						}

						@Override
						public void onSuccess(Kontakt result) {
							RootPanel.get("content").add(new KontaktForm(result));
						}
					});

				}
			}

		}, DoubleClickEvent.getType());

		/**
		 * Button-ClickHandler, um eine Teilhaberschaft zu entfernen und Kontakte zu
		 * loeschen. Hier wird unterschieden zwischen dem Nutzer der als Owner oder
		 * Receiver hintergelegt. Ist man Owner kann der Kontakt permanent geloescht
		 * werden. Als Receiver wird der Kontakt nur aus der Kontaktliste entfernt.
		 */
		deleteKontakt.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// Vector leeren.
				ko.removeAllElements();

				// gewaehlte Kontakte dem Vector hinzufuegen.
				ko.addAll(selectionModel.getSelectedSet());

				// Cookies des eingeloggten Nutzers setzen.
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				if (ko == null) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie mindestens einen Kontakt zum löschen aus.");
				}

				boolean x = Window
						.confirm("Wollen Sie die " + ko.capacity() + " gewählten Kontakte wirklich löschen?");
				if (x == true) {
					for (int i = 0; i < ko.size(); i++) {

						// Wenn man nicht der Owner ist wird erst die Berechtigung entfernt.
						if (nutzer.getId() != ko.elementAt(i).getOwnerId()) {
							/*
							 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
							 * wenn es eine Uebereinstimmung gibt wird die Berechtigung entfernt.
							 */
							ev.deleteBerechtigungReceiver(ko, n, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();

								}

								@Override
								public void onSuccess(Void result) {

								}

							});

							// Ist der Nutzer der Owner des Kontaktes kann er geloescht werden.
						} else

						// Zusaetzliche Pruefung ob es sich, um den eigenen Kontakt handelt.
						if (ko.elementAt(i).getOwnerId() == nutzer.getId() && ko.elementAt(i).getIdentifier() == 'r') {
							Window.alert("Tut uns leid, sie können Ihren Kontakt: " + ko.elementAt(i).getVorname()
									+ " " + ko.elementAt(i).getNachname() + " nicht löschen.");

						}

					}
				}

			}

		});

		// Einen Kontakt einer Kontaktliste hinzufuegen
		// Auswahl eines Kontaktes dann auf Kontakt Kontaktliste hinzufuegen klicken
		// ShowKontaktlisten wird geoeffnet und nun kann der Kontakt einer Kontaktliste
		// hinzugefuegt werden.
		addKontaktToKontaktliste.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// Leeren Vector erstellen
				Vector<Kontakt> kontakte = new Vector<Kontakt>();

				// Ausgewaehlte Kontakte dem Vector hinzufuegen
				kontakte.addAll(selectionModel.getSelectedSet());

				if (kontakte.capacity() == 0) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie mindestens einen Kontakt aus.");
				} else if (kontakte.capacity() >= 1) {
					RootPanel.get("contentHeader").clear();
					RootPanel.get("content").clear();
					RootPanel.get("contentHeader")
							.add(new HTML("<h2>Welcher Kontaktliste soll der Kontakt hinzugefügt werden?</h2>"));
					RootPanel.get("content").add(new ShowKontaktliste(n, null, kontakte));

				}

			}
		});

		// ClickHandler fuer die persoenliche Suche von allen Kontakten.
		search.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (eingabe.getText() == "") {
					MessageBox.alertWidget("Hinweis", "Upps, es wurde keine Eingabe registriert.");

				} else {
					hp.clear();
					searchbar.clear();
					kontaktTable.removeFromParent();

					RootPanel.get("content").add(new Kontaktsuche(n, auswahl.getSelectedItemText(), eingabe.getText()));
				}
			}
		});

		// KeyDownHandler fuer die Suche von Kontakten, Button aktivieren ueber die
		// Enter-Taste
		eingabe.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					search.click();

				}
			}

		});

	}

	// Wird aufgerufen wenn ein Kontakt einer speziellen Kontaktliste angezeigt
	// lassen will.
	protected void showKontakteOfKontaktliste(final Nutzer n, Kontaktliste kl) {
		kontl = kl;

		// Initialisierung des Labels und eines CellTabels fuer die Kontakte
		this.add(titel);

		kontaktListenTable = new CellTable<Kontakt>(10, tableRes);

		// Asynchroner Aufruf, um alle Kontakte einer Kontaktliste zu enthalten
		ev.getKontakteByKontaktliste(kl.getId(), new AsyncCallback<Vector<Kontakt>>() {

			@Override
			public void onFailure(Throwable err) {
				Window.alert("Fehler beim RPC Call" + err.toString());

			}

			/**
			 * Aufruf aller Kontaktlisten die der Nutzer erstellt hat und bei denen der als
			 * Owner hinterlegt ist.
			 */
			public void onSuccess(Vector<Kontakt> result) {

				if (result.size() == 0) {
					kontaktListenTable.setVisible(false);
					deleteKontaktFromKontaktliste.setVisible(false);
					showKontaktFromKontaktliste.setVisible(false);

				} else {
					kontaktListenTable.setVisible(true);

				}

				kontaktListenTable.setRowCount(result.size(), true);
				kontaktListenTable.setVisibleRange(0, 10);
				kontaktListenTable.setRowData(result);

			}
		});

		/**
		 * Tabelle befuellen mit den aus der DB abgerufenen Kontaktinformationen.
		 */
		TextColumn<Kontakt> vornameColumn = new TextColumn<Kontakt>() {

			// Uberschreiben des Inhalts, mit dem aktuellen Wert
			@Override
			public String getValue(Kontakt vorname) {

				return (String) vorname.getVorname();
			}
		};
		/**
		 * Tabelle befuellen mit den aus der DB abgerufenen Kontaktinformationen.
		 */
		TextColumn<Kontakt> nachnameColumn = new TextColumn<Kontakt>() {

			// Uberschreiben des Inhalts, mit dem aktuellen Wert
			@Override
			public String getValue(Kontakt nachname) {

				return (String) nachname.getNachname();
			}
		};

		Resources resources = GWT.create(Resources.class);

		Column<Kontakt, ImageResource> imageColumn = new Column<Kontakt, ImageResource>(new ImageResourceCell()) {

			@Override
			public ImageResource getValue(Kontakt object) {
				if (object.getOwnerId() != n.getId()) {
					return resources.getImageResource1();
				} else {
					return null;
				}

			}
		};

		/**
		 * Implementierung der Checkbox fuer die Auswahl von einem oder mehrere
		 * Kontakten.
		 */
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontakt object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufuegen der Columns fuer die Darstellung der Kontakte.
		 */
		kontaktListenTable.addColumn(vornameColumn, "Vorname ");
		vornameColumn.setSortable(true);

		/**
		 * Hinzufuegen der Columns fuer die Darstellung der Kontakte.
		 */
		kontaktListenTable.addColumn(nachnameColumn, "Nachname ");
		nachnameColumn.setSortable(true);

		/**
		 * Hinzufuegen der Columns fuer die Darstellung des Teilungsstatus.
		 */
		kontaktListenTable.addColumn(imageColumn, "Teilungsstatus ");
		nachnameColumn.setSortable(true);

		/**
		 * Festlegung des Tabellen-Formats
		 */
		kontaktListenTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktListenTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktListenTable.setWidth("97%", true);
		kontaktListenTable.setColumnWidth(vornameColumn, "100px");
		kontaktListenTable.setColumnWidth(nachnameColumn, "100px");
		kontaktListenTable.setColumnWidth(imageColumn, "55px");

		kontaktListenTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktListenTable);
		kontaktListenTable.addColumnSortHandler(sort);

		// Die Cell-Tabelle dem ScrollPanel hinzufuegen.
		sp.add(kontaktListenTable);

		// Groesse des ScrollPanels bestimmen.
		sp.setSize("790px", "400px");

		this.add(sp);
		this.add(hp);

		// Button fuer das Loeschen eines Kontakts einer Kontaktliste
		this.deleteKontaktFromKontaktliste = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> löschen");
		deleteKontaktFromKontaktliste.setStylePrimaryName("deleteKontaktButton");
		deleteKontaktFromKontaktliste.setTitle("EInen Kontakt aus der Kontaktliste löschen");

		// ClickHandler zum Teilen von Kontaktlisten.
		this.shareBtn = new Button(
				"<image src='/images/share.png' width='20px' height='20px' align='center' /> teilen");

		// Styling des Buttons
		shareBtn.setStylePrimaryName("teilunsKlButton");
		shareBtn.setTitle("Kontaktliste mit anderen Nutzern teilen");

		/**
		 * Button ClickHandler, um eine Teilhaberschaft zu entfernen und Kontakte zu
		 * loeschen. Hier wird unterschieden zwischen Owner und Receiver. Ist der Nutzer
		 * als Owner hinterlegt, wird der Kontakt permanent geloescht. Ist der Nutzer
		 * als Receiver eingetragen, wird der Kontakt nur aus der Kontaktliste entfernt.
		 * Ausgenommen von der Loeschung ist der eigene Kontakt und der
		 * Default-Kontaktlisten.
		 */
		deleteKontaktFromKontaktliste.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ko.removeAllElements();
				ko.addAll(selectionModel.getSelectedSet());

				// Nutzer Cookies holen und den Nutzer setzen.
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				// Pruefung ob der Vector 0 Kontakte enthaelt.
				if (ko.size() == 0) {
					MessageBox.alertWidget("Hinweis",
							"Wählen sie einen Kontakt aus um mit dem löschen Fortfahren zu können.");
				} else {
					// Wenn Vector nicht null, dann wird gefragt mit Anzahl ob EIgenschaften
					// geloescht werden sollen.
					x = Window.confirm("Sind sie sicher " + ko.capacity() + " Kontakt/e löschen zu wollen?");
				}
				if (x == true) {
					for (int i = 0; i < ko.size(); i++) {

						/*
						 * Pruefung, ob ein Nutzer als Owner der Kontaktliste hinterlegt ist und ob es
						 * sich um die Kontaktliste "Mit mir geteilte Kontakte" handelt um die
						 * Berechtigung zu loeschen. Berechtigung kann nur aus der mit mir geteilte
						 * Kontakteliste entfernt werden, handelt es sich nicht um diese Liste wird nur
						 * der Kontakt aus der Kontaktliste entfernt.
						 * 
						 * Dies dient dazu, weil man Kontakte auch nur aus einer Kontaktliste entfernen
						 * will ohne die Intension der Berechtigung zu entfernen.
						 */
						if (nutzer.getId() != ko.elementAt(i).getOwnerId()
								&& kl.getTitel() == "Mit mir geteilte Kontakte") {

							// Nutzer Cookies setzen und dann ueber den Nutzer holen.

							/*
							 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
							 * wenn es eine Uebereinstimmung gibt, wird die Berechtigung entfernt.
							 */
							ev.deleteBerechtigungReceiver(ko, n, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();

								}

								@Override
								public void onSuccess(Void result) {

								}

							});

							/*
							 * Ist der Nutzer der Owner des Kontakts und befindet sich der Kontakt in der
							 * Liste "Meine Kontakte" wird er permanent geloescht.
							 */
						} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() == nutzer.getId()
								&& ko.elementAt(i).getIdentifier() != 'r') {
							ev.deleteKontakt(ko.elementAt(i), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();

								}

								@Override
								public void onSuccess(Void result) {
									MessageBox.alertWidget("Hinweis",
											ko.capacity() + " Kontakt/e wurde/n erfolgreich gelöscht.");

								}

							});
							/*
							 * Case 1: Ist der Nutzer nicht der Owner und befindet sich in der Kontaktliste
							 * "Meine Kontakte" wird der Kontakt nur aus der Kontaktliste entfernt.
							 * 
							 * Case 2: Befindet der Nutzer sich nicht in der Kontaktliste
							 * "Mit mir geteilte Kontakte" oder ist der Owner des Kontakts wird der Kontakt
							 * nur aus der Kontaktliste entfernt.
							 */
						} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() != nutzer.getId()
								|| kl.getTitel() != "Mit mir geteilte Kontakte"
										&& ko.elementAt(i).getOwnerId() != nutzer.getId()) {

							// Asynchroner Aufruf, der einen Kontakt einer Kontaktliste entfernt
							ev.removeKontaktFromKontaktliste(kl, ko.elementAt(i), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();
								}

								@Override
								public void onSuccess(Void result) {
									MessageBox.alertWidget("Hinweis",
											"Es wurden " + ko.capacity() + " Kontakte erfolgreich aus der Kontaktliste "
													+ kl.getTitel() + " gelöscht");

								}

							});
							/*
							 * Ist der Nutzer als Owner und will einen Kontakt aus einer NICHT Standard
							 * Kontaktliste loeschen.
							 */
						} else if (kl.getTitel() != "Meine Kontakte"
								&& ko.elementAt(i).getOwnerId() == nutzer.getId()) {

							// Asynchroner Aufruf, der einen Kontakt einer Kontaktliste entfernt
							ev.removeKontaktFromKontaktliste(kl, ko.elementAt(i), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();
								}

								@Override
								public void onSuccess(Void result) {
									MessageBox.alertWidget("Hinweis",
											"Es wurden " + ko.capacity() + " Kontakte erfolgreich aus der Kontaktliste "
													+ kl.getTitel() + " gelöscht");

								}

							});

							// Pruefung, ob der Owner der uebergebene Nutzer ist
						} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() == nutzer.getId()
								&& ko.elementAt(i).getIdentifier() == 'r') {
							MessageBox.alertWidget("Hinweis", "Sie können Ihren eigenen Kontakt nicht löschen.");
						}

						// Loeschen von Kontakten aus der Kontaktliste als Teilhaber, wenn der Titel der
						// Liste nicht ""Mit mir geteilte Kontakte" heisst.
						else if (nutzer.getId() != ko.elementAt(i).getOwnerId()
								&& kl.getTitel() != "Mit mir geteilte Kontakte") {

							// Asynchroner Aufruf, der einen Kontakt einer Kontaktliste entfernt
							ev.removeKontaktFromKontaktliste(kl, ko.elementAt(i), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();
								}

								@Override
								public void onSuccess(Void result) {
									MessageBox.alertWidget("Hinweis",
											"Es wurden " + ko.capacity() + " Kontakte erfolgreich aus der Kontaktliste "
													+ kl.getTitel() + " gelöscht");

								}

							});

						}

					}

					// Div's leeren und Kontaktliste aktualisieren.
					RootPanel.get("content").clear();
					RootPanel.get("contentHeader").clear();
					RootPanel.get("navigator").clear();
					RootPanel.get("navigator").add(new Navigation(nutzer));
					RootPanel.get("content").add(new KontaktlisteForm(kl));

				}

			}

		});

		// Dem Teilungs-Button einen Clickhandler zuweisen.
		shareBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MessageBox.shareAlert("Hinweis", "Geben Sie die Email des Empfängers an", kl);
			}
		});
	}

	/**
	 * Methode zum hinzufuegen eines Kontaktes zu einer Kontaktliste. Es wird eine
	 * CellTable erstellt mit allen bis zu diesem Zeitpunkt vorhandenen Kontakten,
	 * um einen auszuwaehlen der der Kontaktliste kl hinzuefuegt werden soll. Es
	 * wird hierbei die Kontaktliste "Meine Kontakte" des Nutzers aus der DB
	 * ausgelesen. In dieser sind auch die Kontakte vorhanden die mit einem Nutzer
	 * geteilt wurden. Diese Kontakte werden hier auch angezeigt.
	 * 
	 * @param kl
	 *            Kontakliste in der ein Kontakt hinzugefuegt werden soll.
	 */
	void showAllKontakte(Kontaktliste kl) {

		// Hinter dem eingeloggten Nutzer werden Cookies gesetzt
		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		// Kontakttabelle wird das Format zugewiesen
		kontaktTable = new CellTable<Kontakt>(10, tableRes);

		/**
		 * Alle Kontakte der Kontaktliste werden ausgelesen. Hierunter fallen die selbst
		 * angelegten Kontakt, sowie die mit einem geteilten Kontakte
		 * 
		 */
		allKontakte();

		// Die Tabelle mit den aus der DB abgerufenen Kontaktinformationen wird befuellt

		TextColumn<Kontakt> vornameCol = new TextColumn<Kontakt>() {

			// Inhalte werden mit dem aktuellen Wert befuellt
			@Override
			public String getValue(Kontakt vorname) {

				return (String) vorname.getVorname();
			}
		};

		TextColumn<Kontakt> nachnameCol = new TextColumn<Kontakt>() {

			// Inhalte werden mit dem aktuellen Wert befuellt
			@Override
			public String getValue(Kontakt nachname) {

				return (String) nachname.getNachname();
			}
		};

		Resources resources = GWT.create(Resources.class);

		Column<Kontakt, ImageResource> imageColumn = new Column<Kontakt, ImageResource>(new ImageResourceCell()) {

			@Override
			public ImageResource getValue(Kontakt object) {
				if (object.getOwnerId() != nutzer.getId()) {
					return resources.getImageResource1();
				} else {
					return null;
				}
			}
		};

		/**
		 * Implementierung der Checkbox fuer die Auswahl von einem oder mehreren
		 * Kontakten.
		 */
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {

			// Inhalte werden mit dem aktuellen Wert befuellt
			@Override
			public Boolean getValue(Kontakt object) {
				return selectionModel.isSelected(object);
			}
		};

		// Hinzufuegen der Columns fuer die Darstellung der Kontakte.
		kontaktTable.addColumn(vornameCol, "Vorname");
		vornameCol.setSortable(true);

		kontaktTable.addColumn(nachnameCol, "Nachname");
		nachnameCol.setSortable(true);

		kontaktTable.addColumn(imageColumn, "Teilungsstatus");
		imageColumn.setSortable(true);

		// Der Tabellen-Darstellung wird das Format zugewiesen.
		kontaktTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktTable.setWidth("97%", true);
		kontaktTable.setColumnWidth(vornameCol, "100px");
		kontaktTable.setColumnWidth(nachnameCol, "100px");
		kontaktTable.setColumnWidth(imageColumn, "55px");
		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);

		// Button der einen Kontakt einer Kontaktliste hinzufuegt
		this.addKontaktToKontaktlisteStart = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> hinzufügen");
		addKontaktToKontaktlisteStart.setStylePrimaryName("khunzufuegenKl");
		addKontaktToKontaktlisteStart.setTitle("Kontakt/e einer Kontaktliste hinzufügen");

		// Button fuer das Abbrechen
		this.abbrechen = new Button(
				"<image src='/images/abbrechen.png' width='20px' height='20px' align='center' /> Abbrechen");
		abbrechen.setStylePrimaryName("khunzufuegenKlAbbbruch");
		abbrechen.setTitle("Hinzufügen Abbrechen");

		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> fuer die
		 * Interaktionen mit den Kontakten.
		 */
		sp.setSize("900px", "400px");
		sp.add(kontaktTable);

		// ScrollPanel und HorizontalPanel hinzufuegen
		this.add(sp);
		this.add(hp);

		// Einen Kontakt einer Kontaktliste hinzufuegen
		// Auswahl eines Kontaktes dann auf Kontakt Kontaktliste hinzufuegen klicken
		// ShowKontaktlisten wird geoeffnet und nun kann der Kontakt einer Kontaktliste
		// hinzugefuegt werden.
		addKontaktToKontaktlisteStart.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Vector erst leeren zur Sicherheit
				ko.removeAllElements();
				// Ausgeaehlte Kontakte dem Vector hinzufuegen
				ko.addAll(selectionModel.getSelectedSet());

				// Abfrage ob Kapazitaet des Vectors gleich 0
				if (ko.capacity() == 0) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie mindestens einen Kontakt aus.");
				} else if (ko.capacity() >= 1) {

					// Asynchroner Aufruf der die Kontakte einer Kontaktliste aufruft
					ev.getKontakteByKontaktliste(kl.getId(), new AsyncCallback<Vector<Kontakt>>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Vector<Kontakt> result) {

							for (int i = 0; i < ko.size(); i++) {

								if (result.contains(ko.elementAt(i))) {
									MessageBox.alertWidget("Hinweis",
											"Der Kontakt " + ko.elementAt(i).getVorname() + ", "
													+ ko.elementAt(i).getNachname()
													+ " existiert bereits in dieser Kontaktliste");
								} else {
									// Es wird die selektierte Kontaktliste uebergeben und der Kontakt der zuvor
									// ausgewaehlt wurde. (Konstruktoruebergabe)
									ev.addKontaktToKontaktliste(kl, ko.elementAt(i), new AsyncCallback<Void>() {

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

							// Div's leeren und Kontaktliste anzeigen bei der die Kontakte hinzugefuegt
							// wurden.
							RootPanel.get("content").clear();
							RootPanel.get("contentHeader").clear();
							RootPanel.get("content").add(new KontaktlisteForm(kl));
							MessageBox.alertWidget("Glückwunsch", "Sie haben " + ko.capacity()
									+ " Kontakte zu Ihrer Kontaktliste " + kl.getTitel() + " hinzugefügt");
						}
					});
				}
			}
		});

		// Button fuer das Abbrechen der Hinzufuegen-Aktion.
		abbrechen.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// Div's leeren und Kontaktliste anzeigen bei der die Kontakte hinzugefuegt
				// wurden.
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				RootPanel.get("content").add(new KontaktlisteForm(kl));
			}
		});
	}

	/**
	 * Diese Methode fuehrt alle Kontakte eines Nutzers zusammen. Hier werden alle
	 * Kontakte hinterlegt die der Nutzer selbst angelegt hat, ebenso werden alle
	 * Kontakte eingefuegt, hinter denen der Nutzer als Receive hinterlegt ist.
	 * Diese Kontakte werden in der aufzurufenden Kontakttabelle integriert.
	 * 
	 */
	public void allKontakte() {

		// Hinter dem uebergeben Nutzer werden Cookies gesetzt.
		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		/**
		 * Diese aufeinander folgenden Methoden rufen fuer den Nutzer die eigenen
		 * Kontakte und die mit IHM geteilten Kontakte auf und fuehrt sie in einer
		 * gemeinsamen Liste zusammen.
		 */
		ev.getAllKontakteByOwner(nutzer, new AsyncCallback<Vector<Kontakt>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getMessage().toString();

			}

			// Meine Kontakte des Nutzers bei dem er der Owner ist werden der Table
			// hinzugefuegt.
			@Override
			public void onSuccess(Vector<Kontakt> ownKontakte) {

				// Ruft Meine Kontakte auf, die mit dem Nutzer geteilt wurden.
				ev.getAllSharedKontakteByReceiver(nutzer.getId(), new AsyncCallback<Vector<Kontakt>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					// Alle geteilten Kontakte werden der Tabel hinzugefuegt.
					@Override
					public void onSuccess(Vector<Kontakt> sharedKontakte) {

						// Leerer Vector fuer Zusammenfuehrung erzeugen.
						Vector<Kontakt> zsm = new Vector<Kontakt>();

						// Zusammenfuehrung von geteilten und eigenen Kontakten des Nutzers in einer
						// Tabelle.
						zsm.addAll(ownKontakte);
						zsm.addAll(sharedKontakte);

						// Formatierung der Kontakttabelle
						kontaktTable.setRowCount(zsm.size(), true);
						kontaktTable.setVisibleRange(0, 10);
						kontaktTable.setRowData(zsm);
					}
				});
			}
		});
	}
}
