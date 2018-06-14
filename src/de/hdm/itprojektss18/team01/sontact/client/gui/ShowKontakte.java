package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
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

	private CellTable<Kontakt> kontaktTable;
	private CellTable<Kontakt> kontaktListenTable;
	final SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();
	final ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();
	ListBox auswahl = new ListBox();
	TextBox eingabe = new TextBox();
	//TextBox auswahl = new TextBox();

	/**
	 * Buttons der Klasse deklarieren.
	 */
	private Button deleteKontakt;
	private Button addKontaktToKontaktliste;
	private Button deleteKontaktFromKontaktliste;
	private Button showKontaktFromKontaktliste;
	private Button search;

	/**
	 * Label für den Titel deklarieren.
	 */
	private Label titel = new Label();

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
	 * @param n
	 *            Nutzer der übergeben wird
	 * @param kl
	 *            Kontaktliste die ausgewählt wurde
	 */
	public ShowKontakte(Kontaktliste kl) {

		RootPanel.get("content").clear();
		head.add(new HTML("<h2>Kontakt auswählen<h2>"));

		RootPanel.get("content").add(head);

		// Methode die beim Start dieser Klasse aufgerufen wird.
		showAllKontakte(kl);

	

	}

	/**
	 * Konstruktor der Klasse um Meine Kontakte der Kontaktliste "Meine Kontakte"
	 * beim start des Programms anzuzeigen.
	 */
	public ShowKontakte(final Nutzer n) {

		head.add(new HTML("<h2>Übersicht aller Kontakte</h2>"));
		RootPanel.get("content").add(head);

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
	

	}

	/**
	 * Wird vom Konstruktor aufgrufen der beim start des Programms greift.
	 * 
	 * @param n
	 *            Nutzer der übergeben wird.
	 */
	protected void onLoad(final Nutzer n) {
		kontaktTable = new CellTable<Kontakt>();

		this.search = new Button(
				"<image src='/images/search.png' width='15px' height='15px' align='center' />  Start");

		
		auswahl.setPixelSize(130, 35);
		eingabe.setPixelSize(125, 25);
		search.setPixelSize(125, 25);
		
		// ListBox mit Auswahlen befüllen.
		auswahl.addItem("Name");
		auswahl.addItem("Auspraegung");
		auswahl.addItem("Eigenschaft");
		eingabe.getElement().setPropertyString("placeholder", "Name/Ausprägung");


		// Der SearchBar den Button, die ListBox und die TextBox hinzufügen.
		searchbar.add(search);
		searchbar.add(auswahl);
		searchbar.add(eingabe);
		this.add(searchbar);
		
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
		kontaktTable.setWidth("80%", true);
		kontaktTable.setColumnWidth(vornameCol, "100px");
		kontaktTable.setColumnWidth(nachnameCol, "200px");
		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createDefaultManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);

		
		// Kontakt Tabelle dem Container hinzufügen.
		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> für Interaktionen mit
		 * den Kontakten.
		 */
		this.deleteKontakt = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> löschen");
		
		this.addKontaktToKontaktliste = new Button(
				"<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> hinzufügen");

		sp.setSize("900px", "400px");
		sp.add(kontaktTable);
		this.add(sp);
		this.add(hp);

		//Lässt Kontakte mit einem Doppel Klick anzeigen.
		kontaktTable.addDomHandler(new DoubleClickHandler() {

					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						//RootPanel.get("content").clear();
						ev.getKontaktById(selectionModel.getSelectedObject().getId(), new AsyncCallback<Kontakt>() {
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
						
					
			    }, DoubleClickEvent.getType());

		/**
		 * Button ClickHandler um eine Teilungen zu entfernen und Kontakt zu löschen
		 * Hier wird unterschieden zwischen Owner und Receiver. Ist man Owner kann man
		 * den Kontakt permanent löschen wenn man der Receiver ist wird der Kontakt nur
		 * aus der Kontaktliste entfernt.
		 */
		deleteKontakt.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Kontakt k = selectionModel.getSelectedObject();

				if (k == null) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie einen Kontakt zum löschen aus.");
				}
				Window.alert("Sind Sie sicher die Kontaktliste " + k.getVorname() + " " + k.getNachname()
						+ " löschen zu wollen?");

				Nutzer n = new Nutzer();
				n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				// Wenn man nicht der Owner ist wird erst die Berechtigung entfernt.
				if (n.getId() != k.getOwnerId()) {
					// Nutzer Cookies setzen und dann per Nutzer holen.

					/*
					 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
					 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
					 */
					ev.getAllBerechtigungenByReceiver(n.getId(), new AsyncCallback<Vector<Berechtigung>>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();
						}

						@Override
						public void onSuccess(Vector<Berechtigung> result) {
							Vector<Berechtigung> berecht = result;

							for (int i = 0; i < berecht.size(); i++) {

								if (berecht.elementAt(i).getObjectId() == k.getId()) {
									Berechtigung b = new Berechtigung();
									b.setObjectId(k.getId());
									b.setOwnerId(k.getOwnerId());
									b.setReceiverId(n.getId());
									b.setType(k.getType());

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
				} else {
					// Zusätzliche Prüfung ob es sich um den eigenen Kontakt Handelt.
					if (k.getOwnerId() == n.getId() && k.getIdentifier() == 'r') {
						Window.alert("Tut uns leid, sie können Ihren Kontakt: " + k.getVorname() + " " + k.getNachname()
								+ " nicht löschen.");
					} else {
						ev.deleteKontakt(k, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();
							}

							@Override
							public void onSuccess(Void result) {
								Window.Location.reload();
							}
						});
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

				Kontakt k = selectionModel.getSelectedObject();

				if (k == null) {
					MessageBox.alertWidget("Hinweis",
							"Bitte wählen Sie einen Kontakt aus der einer Kontaktliste hinzugefügt werden soll.");
				} else {

					ev.getKontaktById(k.getId(), new AsyncCallback<Kontakt>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Der ausgewählte Kontakt konnte nicht angezeigt werden.");

						}

						@Override
						public void onSuccess(Kontakt result) {
							RootPanel.get("content").clear();
							RootPanel.get("content").add(new ShowKontaktliste(n, k));
						}
					});
				}
			}
		});

		//ClickHandler für die persönliche suche von anderen Kontakten.
		search.addClickHandler(new ClickHandler() {

			
			@Override
			public void onClick(ClickEvent event) {
				

				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));
				
				if(eingabe.getText() == "") {
					Window.alert("Upps, es wurde keine Eingabe registriert.");
				} else {
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

		kontaktListenTable = new CellTable<Kontakt>();

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
		kontaktListenTable.setWidth("80%", true);
		kontaktListenTable.setColumnWidth(vornameColumn, "100px");
		kontaktListenTable.setColumnWidth(nachnameColumn, "200px");

		kontaktListenTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Kontakt>createDefaultManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktListenTable);
		kontaktListenTable.addColumnSortHandler(sort);

		// Die CellTabelle dem ScrollPanel hinzufügen.
		sp.add(kontaktListenTable);

		// Größse des ScrollPanels bestimmen.
		sp.setSize("900px", "400px");

		this.add(sp);
		this.add(hp);

		this.deleteKontaktFromKontaktliste = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' /> löschen");

		/**
		 * Button ClickHandler um eine Teilungen zu entfernen und Kontakt zu löschen
		 * Hier wird unterschieden zwischen Owner und Receiver. Ist man Owner kann man
		 * den Kontakt permanent löschen wenn man der Receiver ist wird der Kontakt nur
		 * aus der Kontaktliste entfernt. Ausgenommen von der Löschung ist der eigene
		 * Kontakt und default Kontaktlisten.
		 */
		deleteKontaktFromKontaktliste.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Kontakt k = selectionModel.getSelectedObject();
				
				
				if(k != null) {
				
				Nutzer n = new Nutzer();
				n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				/*
				 * Prüfung ob man der Owner der Kontaktliste ist und ob es sich um die 
				 * Kontaktliste "Mit mir geteilte Kontakte" handelt um die Berechtigung zu löschen.
				 * Berechtigung kann nur aus der mit mir geteilte Kontakteliste entfernt werden, 
				 * handelt es sich nicht um diese Liste wird nur der Kontakt aus der Kontaktliste entfernt.
				 * 
				 * Dies dient dazu, weil man Kontakte auch nur aus einer Kontaktliste entfernen will 
				 * ohne die Intension die Berechtigung zu entfernen.
				 */
				if (n.getId() != k.getOwnerId() && kl.getTitel() == "Mit mir geteilte Kontakte") {
					// Nutzer Cookies setzen und dann per Nutzer holen.

					/*
					 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
					 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
					 */
					ev.getAllBerechtigungenByReceiver(n.getId(), new AsyncCallback<Vector<Berechtigung>>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();
						}

						@Override
						public void onSuccess(Vector<Berechtigung> result) {
							Vector<Berechtigung> berecht = result;

							for (int i = 0; i < berecht.size(); i++) {

								if (berecht.elementAt(i).getObjectId() == k.getId()) {
									Berechtigung b = new Berechtigung();
									b.setObjectId(k.getId());
									b.setOwnerId(k.getOwnerId());
									b.setReceiverId(n.getId());
									b.setType(k.getType());

									ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {
											Window.alert("Die Teilhaberschaft wurde aufgelöst.");
											Window.Location.reload();
										}
									});

								}
							}

						}

					});

					/*
					 *  Ist man Owner des Kontakts und befindet sich der Kontakt 
					 *  in der Liste "Meine Kontakte" wird er permanent gelöscht.
					 */
				} else if (kl.getTitel() == "Meine Kontakte" && k.getOwnerId() == n.getId() && k.getIdentifier() != 'r'){
					ev.deleteKontakt(k, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();
							
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Der Kontakt wurde erfolgreich gelöscht.");
							
						}
						
					});
					/*
					 * Case 1:
					 * Ist man nicht der Owner und befindet sich in der Kontaktliste "Meine Kontakte"
					 * wird der Kontakt nur aus der Kontaktliste entfernt.
					 * 
					 * Case 2:
					 * Befindet man sich nicht in der Kontaktliste "Mit mir geteilte Kontakte" oder ist der 
					 * Owner des Kontakts wird der Kontakt nur aus der Kontaktliste entfernt.
					 */
				} else if (kl.getTitel() == "Meine Kontakte" && k.getOwnerId() != n.getId() ||
						kl.getTitel() != "Mit mir geteilte Kontakte" && k.getOwnerId() != n.getId()) {
					 ev.removeKontaktFromKontaktliste(kl, k, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();							
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Der Kontakt " + k.getVorname() + k.getNachname() + 
									" wurde erfolgreich aus der Kontaktliste " + kl.getTitel());
							
						}
						 
						 
					 });
					 /*
					  * Ist man Owner und will einen Kontakt aus einer NICHT Standard Kontaktliste löschen.
					  */
				} else if(kl.getTitel() != "Meine Kontakte" && k.getOwnerId() == n.getId()) {
					 ev.removeKontaktFromKontaktliste(kl, k, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();							
							}

							@Override
							public void onSuccess(Void result) {
								Window.alert("Der Kontakt " + k.getVorname() + k.getNachname() + 
										" wurde erfolgreich aus der Kontaktliste " + kl.getTitel());
								
							}
							 
							 
						 });
					
				} else {
					Window.alert("Sie können Ihren eigenen Kontakt nicht löschen.");
				}
			} else {
				Window.alert("Bitte wählen sie einen Kontakt aus der gelöscht werden soll.");
			}
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

		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		kontaktTable = new CellTable<Kontakt>();
		
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
		kontaktTable.setWidth("80%", true);
		kontaktTable.setColumnWidth(vornameCol, "100px");
		kontaktTable.setColumnWidth(nachnameCol, "200px");
		kontaktTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Kontakt>createDefaultManager());

		ListDataProvider<Kontakt> dataProvider = new ListDataProvider<Kontakt>();

		ListHandler<Kontakt> sort = new ListHandler<Kontakt>(dataProvider.getList());
		dataProvider.addDataDisplay(kontaktTable);
		kontaktTable.addColumnSortHandler(sort);
	
		this.add(kontaktTable);

		/**
		 * Erstellung von Buttons mit <code>ClickHandlern()</code> für Interaktionen mit
		 * den Kontakten.
		 */

		sp.setSize("900px", "400px");
		sp.add(kontaktTable);
		this.add(sp);
		this.add(hp);

		//Lässt Kontakte mit einem Doppel Klick anzeigen.
		kontaktTable.addDomHandler(new DoubleClickHandler() {

					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						//RootPanel.get("content").clear();
						ev.getKontaktById(selectionModel.getSelectedObject().getId(), new AsyncCallback<Kontakt>() {
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
						
					
			    }, DoubleClickEvent.getType());
		


	}
	
	public void allKontakte() {
		//Setzen der Cookies für den Nutzer um mit ihm arbeiten zu können.
		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
		
		/**
		 * Diese aufeinander folgenden Methoden rufen für den Nutzer die eigenen
		 * Kontakte und die mit IHM geteilten Kontakte auf und führt sie in einer
		 * gemeinsamen Liste zusammen.
		 */
		ev.getAllKontakteByOwner(n, new AsyncCallback<Vector<Kontakt>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getMessage().toString();

			}

			// Meine Kontakte des Nutzers bei dem er der Owner ist werden der Table
			// hinzugefügt.
			@Override
			public void onSuccess(Vector<Kontakt> ownKontakte) {

				// Holt Meine Kontakte die mit dem Nutzer geteilt wurden.
				ev.getAllSharedKontakteByReceiver(n.getId(), new AsyncCallback<Vector<Kontakt>>() {

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
