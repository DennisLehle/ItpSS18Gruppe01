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
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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

	/**
	 * Buttons der Klasse deklarieren.
	 */
	private Button deleteKontakt;
	private Button showKontakt;
	private Button addKontaktToKontaktliste;
	private Button deleteKontaktFromKontaktliste;
	private Button showKontaktFromKontaktliste;
	private Button addKontakt;
	/**
	 * Label für den Titel deklarieren.
	 */
	private Label titel = new Label();

	/**
	 * Panels die genutzt werden deklarieren.
	 */
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();

	
	/**
	 * Konstruktor der Klasse für's Anzeigen von Kontakten einer Kontaktliste, wenn
	 * man auf eine Kontaktliste klickt
	 * 
	 * @param n Nutzer der übergeben wird
	 * @param kl Kontaktliste die ausgewählt wurde
	 *           
	 */
	public ShowKontakte(Kontaktliste kl) {

		RootPanel.get("content").clear();
		head.add(new HTML("<h2>Kontakt auswählen<h2>"));
		RootPanel.get("content").add(head);
		// Methode die beim Start dieser Klasse aufgerufen wird.
		showAllKontakte(kl);

		hp.add(addKontakt);
		

	}

	/**
	 * Konstruktor der Klasse um alle Kontakte der Kontaktliste "Alle Kontakte" beim
	 * start des Programms anzuzeigen.
	 */
	public ShowKontakte(final Nutzer n) {

		head.add(new HTML("<h2>Übersicht Ihrer Kontakte</h2>"));
		RootPanel.get("content").add(head);
		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n);
		// Buttons die inizialisiert werden bei Start dieser Klasse
		hp.add(showKontakt);
		hp.add(deleteKontakt);
		hp.add(addKontaktToKontaktliste);
	}

	/**
	 * Konstruktor der Klasse für's Anzeigen von Kontakten einer Kontaktliste, wenn
	 * man auf eine Kontaktliste klickt
	 * 
	 * @param n Nutzer der übergeben wird
	 * @param kl Kontaktliste die ausgewählt wurde
	 *           
	 */
	public ShowKontakte(final Nutzer n, Kontaktliste kl) {

		// Methode die beim Start dieser Klasse aufgerufen wird.
		showKontakteOfKontaktliste(n, kl);

		hp.add(deleteKontaktFromKontaktliste);
		hp.add(showKontaktFromKontaktliste);

	}

	/**
	 * Wird vom Konstruktor aufgrufen der beim start des Programms greift.
	 * 
	 * @param n
	 *            Nutzer der übergeben wird.
	 */
	protected void onLoad(final Nutzer n) {

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
		this.deleteKontakt = new Button("<image src='/images/trash.png' width='20px' height='20px' align='center' /> löschen");
		this.showKontakt = new Button("<image src='/images/user.png' width='20px' height='20px' align='center' /> anzeigen");
		this.addKontaktToKontaktliste = new Button("<image src='/images/kontaktliste.png' width='20px' height='20px' align='center' /> hinzufügen");

		sp.setSize("900px", "400px");
		sp.add(kontaktTable);
		this.add(sp);
		this.add(hp);

		/**
		 * Button ClickHandler fürs anzeigen eines Kontaktes aus der Kontaktliste. Die
		 * KontaktFormular Klasse wird instanzieiert.
		 */
		showKontakt.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Kontakt k = selectionModel.getSelectedObject();

				if (k == null) {
					MessageBox.alertWidget("Hinweis", "Bitte wählen Sie einen Kontakt aus.");
				} else {
					RootPanel.get("content").clear();
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
					add(new KontaktForm(k));

				}
			}
		});

		/**
		 * Button ClickHandler fürs löschen eines Kontaktes aus der Kontaktliste.
		 */
		deleteKontakt.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Window.confirm("Das löschen hier löscht den Kontakt aus allen Kontaktlisten. Sind Sie sicher?");

				Kontakt k = selectionModel.getSelectedObject();
				if (k == null) {
					Window.alert("Es wurde kein Kontakt selektiert.");
				} else {
					ev.removeKontakt(k, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Hoppala" + caught.toString());
						}

						@Override
						public void onSuccess(Void result) {
							if (selectionModel.getSelectedSet().size() > 0) {
								Window.Location.reload();
								onLoad(n);
							}
						}
					});

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
					Window.alert("Leider existieren noch keine Kontakte, füge doch gleich welche hinzu :)");

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
				DefaultSelectionEventManager.<Kontakt>createCheckboxManager());

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

		this.deleteKontaktFromKontaktliste = new Button("<image src='/images/user.png' width='20px' height='20px' align='center' /> löschen");
		this.showKontaktFromKontaktliste = new Button("<image src='/images/user.png' width='20px' height='20px' align='center' /> anzeigen");

		/**
		 * Button ClickHandler fürs anzeigen eines Kontaktes aus der Kontaktliste. Die
		 * KontaktFormular Klasse wird instanzieiert.
		 */
		showKontaktFromKontaktliste.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Kontakt k = selectionModel.getSelectedObject();

				if (k != null) {
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
					add(new KontaktForm(k));
				} else {
					Window.alert("Bitte wähle einen Kontakt an um ihn anzeigen zu lassen.");
				}
			}
		});

		/**
		 * Button ClickHandler fürs löschen eines Kontaktes aus der Kontaktliste.
		 */
		deleteKontaktFromKontaktliste.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				Kontakt k = selectionModel.getSelectedObject();
				if (k == null) {
					Window.alert("Bitte wähle einen Kontakt an der gelöscht werden soll.");
				} else {
					ev.removeKontaktFromKontaktliste(kl, k, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Hoppala" + caught.toString());
						}

						@Override
						public void onSuccess(Void result) {
							if (selectionModel.getSelectedSet().size() > 0) {

								Window.Location.reload();
							}
						}
					});

				}
			}
		});
	
	}
	/**
	 * Methode zum hinzufügen eines Kontaktes zu einer Kontaktliste.
	 * Es wird eine CellTable erstellt mit allen bis dato vorhandenen Kontaken
	 * um einen auszuwählen der der Kontaktliste kl hinzuefügt werden soll.
	 * 
	 * @param kl Kontakliste in der ein Kontakt hizugefügt werden soll.
	 */
	void showAllKontakte(Kontaktliste kl) {
		
		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
		
		kontaktTable = new CellTable<Kontakt>();

		//Alle Kontakte des Nutzers herauslesen.
		ev.getAllKontakteByOwner(n, new AsyncCallback<Vector<Kontakt>>() {

			@Override
			public void onFailure(Throwable err) {
				Window.alert("Fehler beim RPC Call" + err.toString());

			}

			// Aufruf aller Kontakt die der Nutzer erstellt hat und er als Owner hinterlegt ist.
			public void onSuccess(Vector<Kontakt> result) {
			
				if (result.size() == 0) {
					kontaktTable.setVisible(false);
					hp.setVisible(false);

				} else {
					Window.alert("DUDU");
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
		this.addKontakt = new Button("Kontakt hinzufügen");

		sp.setSize("900px", "400px");
		sp.add(kontaktTable);
		this.add(sp);
		this.add(hp);
		
		
		/**
		 * Button ClickHandler zum hinzufügen der bereits ausgewählten Kontaktliste.
		 */
		addKontakt.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				//Selektierte Kontaktliste
				Kontakt k = selectionModel.getSelectedObject();
				//Sicherheitsabfrage ob selektierter Kontakt null ist.
				if(k == null) {
					Window.alert("Bitte wähle einen Kontakt aus.");
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
