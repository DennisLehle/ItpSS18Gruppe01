package de.hdm.itprojektss18.team01.sontact.client.gui;


import java.util.Set;
import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Diese Klasse zeigt <code>Kontakt</code> Objekte eines Nutzers an. Diese
 * werden in einem CellTable dargestellt und können von dort aus gelöscht oder
 * angezeigt werden.
 * 
 * @author Dennis Lehle, Kevin Batista, Ugur Bayrak
 *
 */
public class ShowKontakte extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	private CellTable.Resources tableRes = GWT.create(TableResources.class);

	private CellTable<Kontakt> kontaktTable;
	private CellTable<Kontakt> kontaktListenTable;
	final MultiSelectionModel<Kontakt> selectionModel = new MultiSelectionModel<Kontakt>();
	final SingleSelectionModel<Kontakt> selectionModel1 = new SingleSelectionModel<Kontakt>();
	final ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();
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
	 * Label für den Titel deklarieren.
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
	 * Konstruktor der Klasse für's Anzeigen von Kontakten einer Kontaktliste, wenn
	 * man auf eine Kontaktliste klickt
	 * 
	 * @param kl Kontaktliste die ausgewählt wurde
	 */
	public ShowKontakte(Kontaktliste kl) {

		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new HTML("<h2>Kontakt/e auswählen für<h2> " + kl.getTitel()));

		// Methode die beim Start dieser Klasse aufgerufen wird.
		showAllKontakte(kl);

		// Kontakt/e einer Kontaktliste hinzufügen Button.
		hp.add(addKontaktToKontaktlisteStart);
		hp.add(abbrechen);

	}

	/**
	 * Konstruktor der Klasse um Meine Kontakte der Kontaktliste "Meine Kontakte"
	 * beim start des Programms anzuzeigen.
	 */
	public ShowKontakte(final Nutzer n) {
		hp.clear();
		hp.add(new HTML("<h9>Übersicht aller Kontakte</h9>"));
		// RootPanel.get("content").clear();
		// RootPanel.get("contentHeader").clear();
		// RootPanel.get("contentHeader").add(new HTML("<h2>Übersicht aller
		// Kontakte</h2>"));

		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n);

		// Buttons die inizialisiert werden bei Start dieser Klasse
		hp.add(addKontaktToKontaktliste);

	}

	/**
	 * Konstruktor der Klasse für's Anzeigen von Kontakten einer Kontaktliste, wenn
	 * man auf eine Kontaktliste klickt
	 * 
	 * @param n
	 *            Nutzer der übergeben wird
	 * @param kl
	 *            Kontaktliste die ausgewählt wurde
	 * 
	 */
	public ShowKontakte(final Nutzer n, Kontaktliste kl) {

		// Methode die beim Start dieser Klasse aufgerufen wird.
		showKontakteOfKontaktliste(n, kl);

		hp.add(deleteKontaktFromKontaktliste);
		hp.add(shareBtn);

	}

	/**
	 * Wird vom Konstruktor aufgrufen der beim start des Programms greift.
	 * 
	 * @param n
	 *            Nutzer der übergeben wird.
	 */
	protected void onLoad(final Nutzer n) {

		kontaktTable = new CellTable<Kontakt>(10, tableRes);

		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		this.search = new Button("<image src='/images/search.png' width='15px' height='15px' align='center' /> ");

		search.setTitle("Suchen Sie nach Kontakten anhand von Namen, Eigenschaften oder Ausprägungen");
		search.setStylePrimaryName("searchBtn");
		// ListBox mit Auswahlen befüllen.

		auswahl.addItem("Name");
		auswahl.addItem("Auspraegung");
		auswahl.addItem("Eigenschaft");
		auswahl.setStylePrimaryName("search");
		eingabe.getElement().setPropertyString("placeholder", "Name/Eigenschaft/Ausprägung");
		eingabe.setStylePrimaryName("searchTb");

		// Der SearchBar den Button, die ListBox und die TextBox hinzufügen.
		searchbar.add(search);
		searchbar.add(eingabe);
		searchbar.add(auswahl);

		allKontakte();

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

		kontaktTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktTable.setWidth("100%", true);
		kontaktTable.setColumnWidth(vornameCol, "150px");
		kontaktTable.setColumnWidth(nachnameCol, "200px");
		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createCheckboxManager());
		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createDefaultManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);
		this.add(hp);

		// Kontakt Tabelle dem Container hinzufügen.
		this.add(searchbar);
		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> für Interaktionen mit
		 * den Kontakten.
		 */
		this.deleteKontakt = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> löschennnn");

		this.addKontaktToKontaktliste = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' />"
						+ "<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> hinzufügen");
		addKontaktToKontaktliste.setStylePrimaryName("khunzufuegenKl");
		addKontaktToKontaktliste.setTitle("Kontakt/e einer Kontaktliste hinzufügen");

		sp.setSize("800px", "500px");
		sp.add(kontaktTable);
		this.add(sp);

		// Lässt Kontakte mit einem Doppel Klick anzeigen.
		kontaktTable.addDomHandler(new DoubleClickHandler() {

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
		 * Button ClickHandler um eine Teilungen zu entfernen und Kontakt zu löschen
		 * Hier wird unterschieden zwischen Owner und Receiver. Ist man Owner kann man
		 * den Kontakt permanent löschen wenn man der Receiver ist wird der Kontakt nur
		 * aus der Kontaktliste entfernt.
		 */
		deleteKontakt.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// gewählte Kontakte dem Vector hinzufügen.
				ko.addAll(selectionModel.getSelectedSet());
				// Cookies des eingeloggten Nutzers setzen.
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				if (ko == null) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie mindestens einen Kontakt zum löschen aus.");
				}

				boolean x = Window.confirm("Wollen Sie die " + ko.capacity() + " gewählten Kontakte wirklich löschen?");
				if (x == true) {
					for (int i = 0; i < ko.size(); i++) {

						// Wenn man nicht der Owner ist wird erst die Berechtigung entfernt.
						if (nutzer.getId() != ko.elementAt(i).getOwnerId()) {
							// Nutzer Cookies setzen und dann per Nutzer holen.

							/*
							 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
							 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
							 */
							ev.getAllBerechtigungenByReceiver(nutzer.getId(),
									new AsyncCallback<Vector<Berechtigung>>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();
										}

										@Override
										public void onSuccess(Vector<Berechtigung> result) {
											Vector<Berechtigung> berecht = result;

											for (int i = 0; i < berecht.size(); i++) {

												if (berecht.elementAt(i).getObjectId() == ko.elementAt(i).getId()) {
													Berechtigung b = new Berechtigung();
													b.setObjectId(ko.elementAt(i).getId());
													b.setOwnerId(ko.elementAt(i).getOwnerId());
													b.setReceiverId(nutzer.getId());
													b.setType(ko.elementAt(i).getType());

													ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
														@Override
														public void onFailure(Throwable caught) {
															caught.getMessage().toString();

														}

														@Override
														public void onSuccess(Void result) {
															Window.alert("Die Teilhaberschaft wurde aufgelöst.");

														}
													});

												}
											}

										}

									});

							// Ist man Owner des Kontaktes kann er gelöscht werden.
						} else
						// Zusätzliche Prüfung ob es sich um den eigenen Kontakt Handelt.
						if (ko.elementAt(i).getOwnerId() == nutzer.getId() && ko.elementAt(i).getIdentifier() == 'r') {
							Window.alert("Tut uns leid, sie können Ihren Kontakt: " + ko.elementAt(i).getVorname() + " "
									+ ko.elementAt(i).getNachname() + " nicht löschen.");

						}

					}
				}

			}

		});

		// Einen Kontakt einer Kontaktliste hinzufügen
		// Auswahl eines Kontaktes dann auf Kontakt Kontaktliste hinzufügen klicken
		// ShowKontaktlisten wird geöffnet und nun kann der Kontakt einer Kontaktliste
		// hinzugefügt werden.
		addKontaktToKontaktliste.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Leeren Vector erstellen
				Vector<Kontakt> kontakte = new Vector<Kontakt>();
				// Ausgewählte Kontakte dem Vector hinzufügen
				kontakte.addAll(selectionModel.getSelectedSet());
				// Vectoren ShowKontaktliste den Vector weiterleiten
				if (kontakte.capacity() == 0) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie mindestens einen Kontakt aus.");
				} else if (kontakte.capacity() >= 1) {
					RootPanel.get("content").add(new ShowKontaktliste(n, null, kontakte));

				}

			}
		});

		// ClickHandler für die persönliche suche von anderen Kontakten.
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

	}

	// Wird aufgerufen wenn man Kontakte einer speziellen Kontaktliste anzeigen
	// lassen will.
	protected void showKontakteOfKontaktliste(final Nutzer n, Kontaktliste kl) {

		/**
		 * Initialisierung des Labels und eines CellTabels für die Kontakte
		 */
		this.add(titel);

		kontaktListenTable = new CellTable<Kontakt>(10, tableRes);

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
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt Informationen.
		 */
		TextColumn<Kontakt> vornameColumn = new TextColumn<Kontakt>() {

			@Override
			public String getValue(Kontakt vorname) {

				return (String) vorname.getVorname();
			}
		};
		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Kontakt Informationen.
		 */
		TextColumn<Kontakt> nachnameColumn = new TextColumn<Kontakt>() {

			@Override
			public String getValue(Kontakt nachname) {

				return (String) nachname.getNachname();
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
		kontaktListenTable.addColumn(vornameColumn, "Vorname: ");
		vornameColumn.setSortable(true);

		/**
		 * Hinzufügen der Columns für die Darstellung der Kontakte.
		 */
		kontaktListenTable.addColumn(nachnameColumn, "Nachname: ");
		nachnameColumn.setSortable(true);

		kontaktListenTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktListenTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktListenTable.setWidth("100%", true);
		kontaktListenTable.setColumnWidth(vornameColumn, "100px");
		kontaktListenTable.setColumnWidth(nachnameColumn, "200px");

		kontaktListenTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktListenTable);
		kontaktListenTable.addColumnSortHandler(sort);

		// Die CellTabelle dem ScrollPanel hinzufügen.
		sp.add(kontaktListenTable);

		// Größse des ScrollPanels bestimmen.
		sp.setSize("800px", "400px");

		this.add(sp);
		this.add(hp);

		this.deleteKontaktFromKontaktliste = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> löschen");
		deleteKontaktFromKontaktliste.setStylePrimaryName("deleteKontaktButton");
		deleteKontaktFromKontaktliste.setTitle("EInen Kontakt aus der Kontaktliste löschen");

		// ClickHandler zum teilen von Kontaktlisten.
		this.shareBtn = new Button(
				"<image src='/images/share.png' width='20px' height='20px' align='center' /> teilen");

		shareBtn.setStylePrimaryName("teilunsKlButton");
		shareBtn.setTitle("Kontaktliste mit anderen Nutzern teilen");

		/**
		 * Button ClickHandler um eine Teilungen zu entfernen und Kontakt zu löschen
		 * Hier wird unterschieden zwischen Owner und Receiver. Ist man Owner kann man
		 * den Kontakt permanent löschen wenn man der Receiver ist wird der Kontakt nur
		 * aus der Kontaktliste entfernt. Ausgenommen von der Löschung ist der eigene
		 * Kontakt und default Kontaktlisten.
		 */
		deleteKontaktFromKontaktliste.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// Vector erst leeren und dann ausgewählte Kontakte dem Vector hinzufügen.
				ko.removeAllElements();
				ko.addAll(selectionModel.getSelectedSet());
				
				// Nutzer Cookies holen und Nutzer setten.
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				//Prüfung ob der Vector 0 Kontakte enthält.
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
						 * Prüfung ob man der Owner der Kontaktliste ist und ob es sich um die
						 * Kontaktliste "Mit mir geteilte Kontakte" handelt um die Berechtigung zu
						 * löschen. Berechtigung kann nur aus der mit mir geteilte Kontakteliste
						 * entfernt werden, handelt es sich nicht um diese Liste wird nur der Kontakt
						 * aus der Kontaktliste entfernt.
						 * 
						 * Dies dient dazu, weil man Kontakte auch nur aus einer Kontaktliste entfernen
						 * will ohne die Intension die Berechtigung zu entfernen.
						 */
						if (nutzer.getId() != ko.elementAt(i).getOwnerId()
								&& kl.getTitel() == "Mit mir geteilte Kontakte") {
							// Nutzer Cookies setzen und dann per Nutzer holen.

							/*
							 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
							 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
							 */
							Window.alert("Teilhaber Löschung");
							ev.getAllBerechtigungenByReceiver(nutzer.getId(),
									new AsyncCallback<Vector<Berechtigung>>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();
										}

										@Override
										public void onSuccess(Vector<Berechtigung> result) {
											Vector<Berechtigung> berecht = result;

											for (int i = 0; i < berecht.size(); i++) {

												if (berecht.elementAt(i).getObjectId() == ko.elementAt(i).getId()) {
													Berechtigung b = new Berechtigung();
													b.setObjectId(ko.elementAt(i).getId());
													b.setOwnerId(ko.elementAt(i).getOwnerId());
													b.setReceiverId(nutzer.getId());
													b.setType(ko.elementAt(i).getType());

													ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
														@Override
														public void onFailure(Throwable caught) {
															caught.getMessage().toString();

														}

														@Override
														public void onSuccess(Void result) {
															MessageBox.alertWidget("Hinweis",
																	"Die Teilhaberschaft wurde aufgelöst.");

														}
													});

												}
											}

										}

									});

							/*
							 * Ist man Owner des Kontakts und befindet sich der Kontakt in der Liste
							 * "Meine Kontakte" wird er permanent gelöscht.
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
							 * Case 1: Ist man nicht der Owner und befindet sich in der Kontaktliste
							 * "Meine Kontakte" wird der Kontakt nur aus der Kontaktliste entfernt.
							 * 
							 * Case 2: Befindet man sich nicht in der Kontaktliste
							 * "Mit mir geteilte Kontakte" oder ist der Owner des Kontakts wird der Kontakt
							 * nur aus der Kontaktliste entfernt.
							 */
						} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() != nutzer.getId()
								|| kl.getTitel() != "Mit mir geteilte Kontakte"
										&& ko.elementAt(i).getOwnerId() != nutzer.getId()) {

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
							 * Ist man Owner und will einen Kontakt aus einer NICHT Standard Kontaktliste
							 * löschen.
							 */
						} else if (kl.getTitel() != "Meine Kontakte"
								&& ko.elementAt(i).getOwnerId() == nutzer.getId()) {

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

						} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() == nutzer.getId()
								&& ko.elementAt(i).getIdentifier() == 'r') {
							MessageBox.alertWidget("Hinweis", "Sie können Ihren eigenen Kontakt nicht löschen.");
						}
						// Löschen von Kontakten aus der Kontaktliste als Teilhaber, wenn der Titel der
						// Liste nicht ""Mit mir geteilte Kontakte" heißt.
						else if (nutzer.getId() != ko.elementAt(i).getOwnerId()
								&& kl.getTitel() != "Mit mir geteilte Kontakte") {

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

		shareBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MessageBox.shareAlert("Hinweis", "Geben Sie die Email des Empfängers an", kl);

			}

		});

	}

	/**
	 * Methode zum hinzufügen eines Kontaktes zu einer Kontaktliste. Es wird eine
	 * CellTable erstellt mit allen bis dato vorhandenen Kontaken um einen
	 * auszuwählen der der Kontaktliste kl hinzuefügt werden soll. Es wird hierbei
	 * die Kontaktliste "Meine Kontakte" des Nutzers aus der db gelesen. In dieser
	 * sind auch die Kontakte vorhanden die mit einem geteilt wurden. Diese Kontakte
	 * werden hier auch angezeigt.
	 * 
	 * @param kl
	 *            Kontakliste in der ein Kontakt hizugefügt werden soll.
	 */
	void showAllKontakte(Kontaktliste kl) {

		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		kontaktTable = new CellTable<Kontakt>(10, tableRes);

		allKontakte();

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

		kontaktTable.setColumnWidth(checkColumn, 40, Unit.PX);
		kontaktTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktTable.setWidth("100%", true);
		kontaktTable.setColumnWidth(vornameCol, "100px");
		kontaktTable.setColumnWidth(nachnameCol, "200px");
		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);

		this.addKontaktToKontaktlisteStart = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> hinzufügen");
		addKontaktToKontaktlisteStart.setStylePrimaryName("khunzufuegenKl");
		addKontaktToKontaktlisteStart.setTitle("Kontakt/e einer Kontaktliste hinzufügen");

		this.abbrechen = new Button(
				"<image src='/images/abbrechen.png' width='20px' height='20px' align='center' /> Abbrechen");
		abbrechen.setStylePrimaryName("khunzufuegenKlAbbbruch");
		abbrechen.setTitle("Hinzufügen Abbrechen");

		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> für Interaktionen mit
		 * den Kontakten.
		 */

		sp.setSize("900px", "400px");
		sp.add(kontaktTable);
		this.add(sp);
		this.add(hp);

		// Einen Kontakt einer Kontaktliste hinzufügen
		// Auswahl eines Kontaktes dann auf Kontakt Kontaktliste hinzufügen klicken
		// ShowKontaktlisten wird geöffnet und nun kann der Kontakt einer Kontaktliste
		// hinzugefügt werden.
		addKontaktToKontaktlisteStart.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ko.addAll(selectionModel.getSelectedSet());

				if (ko.capacity() == 0) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie mindestens einen Kontakt aus.");
				} else if (ko.capacity() >= 1) {

					ev.getKontakteByKontaktliste(kl.getId(), new AsyncCallback<Vector<Kontakt>>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

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
									// Es wird die selektierte Kontaktliste übergeben und der Kontakt der zuvor
									// ausgewählt wurde. (Kostruktor übergabe)
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
						}

					});

				}

			}
		});

		// Button fuer das Abbrechen der hinzufuegen aktion.
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

	public void allKontakte() {
		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		/**
		 * Diese aufeinander folgenden Methoden rufen für den Nutzer die eigenen
		 * Kontakte und die mit IHM geteilten Kontakte auf und führt sie in einer
		 * gemeinsamen Liste zusammen.
		 */
		ev.getAllKontakteByOwner(nutzer, new AsyncCallback<Vector<Kontakt>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getMessage().toString();

			}

			// Meine Kontakte des Nutzers bei dem er der Owner ist werden der Table
			// hinzugefügt.
			@Override
			public void onSuccess(Vector<Kontakt> ownKontakte) {

				// Holt Meine Kontakte die mit dem Nutzer geteilt wurden.
				ev.getAllSharedKontakteByReceiver(nutzer.getId(), new AsyncCallback<Vector<Kontakt>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					// Alle geteilten Kontakte werden der Tabel hinzugefügt.
					@Override
					public void onSuccess(Vector<Kontakt> sharedKontakte) {

						// Leerer Vector für Zusammenführung erzeugen.
						Vector<Kontakt> zsm = new Vector<Kontakt>();

						// Zusammenführung von geteilten und eigenen Kontakten des Nutzers in einer
						// Tabelle.
						zsm.addAll(ownKontakte);
						zsm.addAll(sharedKontakte);

						kontaktTable.setRowCount(zsm.size(), true);
						kontaktTable.setVisibleRange(0, 10);
						kontaktTable.setRowData(zsm);

					}

				});
			}
		});

	}
}
