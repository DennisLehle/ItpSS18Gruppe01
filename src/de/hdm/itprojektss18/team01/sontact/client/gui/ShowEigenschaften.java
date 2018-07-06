package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
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
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Diese Klasse repräsentiert die Eigenschaften eines Kontaktes.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */
public class ShowEigenschaften extends VerticalPanel {
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	private CellTable.Resources tableRes = GWT.create(TableResources.class);

	private CellTable<Relatable> eigenschaftAuspraegungTable;
	final MultiSelectionModel<Relatable> selectionModel = new MultiSelectionModel<Relatable>(getKeyProvider());
	final ListDataProvider<Relatable> dataProvider = new ListDataProvider<Relatable>();
	Vector<Nutzer> allUser = new Vector<Nutzer>();
	Vector<Relatable> gewaehlteAuspraegung = new Vector<Relatable>();
	Vector<Relatable> statusObjects = new Vector<Relatable>();
	Vector<Relatable> auspraegungen = new Vector<Relatable>();
	boolean sharedStatus = false;
	Boolean x = new Boolean(false);


	HorizontalPanel hp3 = new HorizontalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel head = new HorizontalPanel();
	VerticalPanel status = new VerticalPanel();
	VerticalPanel vp = new VerticalPanel();
	ScrollPanel sp = new ScrollPanel();
	Label ownerlb = new Label();
	

	/**
	 * Konstruktor wird ausgelöst man einen Kontakt des Nutzers übergibt um die
	 * Eigenschaften mit ihren Ausprägung anzeigen zu lassen.
	 * 
	 * @param n aktuell eingeloggter Nutzer
	 * @param k Uebergebener Kontakt der ausgewaehlt wurde
	 */
	public ShowEigenschaften(final Nutzer n, Kontakt k) {
	
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		
		/*
		 * Prüfungs Mehtode ob der Nutzer der Owner ist
		 * fuer die Label Setzung.
		 */
		ownerPruefung(k,n);
		
		RootPanel.get("content").add(hp);
		head.add(new HTML("<h5>Kontaktinformationen </h5>"));
		RootPanel.get("content").add(head);
		// Methode die beim Start dieser Klasse aufgerufen wird.
		onLoad(n, k);

	}

	/**
	 * Diese Methode wird durch den Konstruktor der Klasse aufgerufen.
	 * 
	 * @param n
	 *            der Nutzer der übergeben wird
	 * @param k
	 *            der Kontakt der übergeben wird
	 */
	protected void onLoad(final Nutzer n, Kontakt k) {
		
		
		
		RootPanel.get("contentHeader").add(new HTML(k.getVorname() + " " + k.getNachname()));
		/**
		 * Initialisierung des Labels und eines CellTabels für die Kontakte
		 */
		eigenschaftAuspraegungTable = new CellTable<Relatable>(10, tableRes);
	
		/**
		 * Alle Nutzer holen für den Abgleich wer Eigentümer/Ersteller einer Eigenschaft ist.
		 */
		ev.findAllNutzer(new AsyncCallback<Vector<Nutzer>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getMessage().toString();
				
			}

			@Override
			public void onSuccess(Vector<Nutzer> result) {
				//Alle gefundenen Nutzer in dem Vector speichern.
				allUser.addAll(result);
				
			}
			
		});

		// Auslesen aller Ausprägungen eines Kontakts. Prüfung ob Nutzer der Owner ist.
		if (n.getId() == k.getOwnerId()) {
			ev.getAllAuspraegungenByKontaktRelatable(k.getId(), new AsyncCallback<Vector<Relatable>>() {
				
				@Override
				public void onFailure(Throwable err) {
					Window.alert("Fehler beim RPC Call" + err.toString());

				}

				/**
				 * Aufruf aller Ausprägungen die der Nutzer erstellt hat und bei denen der als
				 * Owner hinterlegt ist.
				 */
				
				public void onSuccess(Vector<Relatable> result) {
					statusObjects.addAll(result);
					
				
					if (result.size() == 0) {

						eigenschaftAuspraegungTable.setVisible(false);

					} else {
						eigenschaftAuspraegungTable.setVisible(true);
						sp.setVisible(true);
						hp3.setVisible(true);

					}
				
					eigenschaftAuspraegungTable.setRowCount(statusObjects.size(), true);
					eigenschaftAuspraegungTable.setVisibleRange(0, 10);
					eigenschaftAuspraegungTable.setRowData(statusObjects);

				}
			
			});
		} else {

			// Wenn der Nutzer nicht der Eigentümer des Kontaktes ist werden die geteilten
			// Ausprägungen abgefragt.
			ev.getAllSharedAuspraegungenByKontaktAndNutzer(k, n, new AsyncCallback<Vector<Relatable>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Relatable> result) {
					statusObjects.addAll(result);
					
					if (result.size() == 0) {
						eigenschaftAuspraegungTable.setVisible(false);
					
					} else {
						eigenschaftAuspraegungTable.setVisible(true);
						sp.setVisible(true);
						hp3.setVisible(true);

					}
					
					for (int i = 0; i < statusObjects.size(); i++) {
						
	
						if(statusObjects.elementAt(i).getOwnerId() == n.getId() || statusObjects.elementAt(i).getStatus() == true) {
							if(auspraegungen.contains(statusObjects.elementAt(i))) {
								statusObjects.remove(i);
							} else {
								auspraegungen.add(statusObjects.elementAt(i));
							}
						}
					}
	
					eigenschaftAuspraegungTable.setRowCount(auspraegungen.size(), true);
					eigenschaftAuspraegungTable.setVisibleRange(0, 10);
					eigenschaftAuspraegungTable.setRowData(auspraegungen);
				}

			});
		}

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
		
		TextColumn<Relatable> erstellerColumn = new TextColumn<Relatable>() {

			@Override
			public String getValue(Relatable owner) {
				// TODO Auto-generated method stub
				Nutzer nn = new Nutzer();
				
					for (int i = 0; i < allUser.size(); i++) {
						if(owner.getOwnerId() == allUser.elementAt(i).getId()) {
							nn.setEmailAddress(allUser.elementAt(i).getEmailAddress());
						}
					}			
				return nn.getEmailAddress();
			}
		};

		Resources resources = GWT.create(Resources.class);

		Column<Relatable, ImageResource> imageColumn = new Column<Relatable, ImageResource>(new ImageResourceCell()) {

			@Override
			public ImageResource getValue(Relatable object) {
				if (object.getStatus() == true) {
					return resources.getImageResource();
				} else {
					return null;
				}

			}
		};
			

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere
		 * Eigenschafen mit Ausprägungen.
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
		eigenschaftAuspraegungTable.addColumn(eigenschaftColumn, "Eigenschaft ");
		eigenschaftColumn.setSortable(true);

		eigenschaftAuspraegungTable.addColumn(auspraegnungColumn, "Ausprägung ");
		auspraegnungColumn.setSortable(true);

		eigenschaftAuspraegungTable.addColumn(imageColumn, "Teilungsstatus ");
		imageColumn.setSortable(true);
		
		eigenschaftAuspraegungTable.addColumn(erstellerColumn, "Ersteller ");
		imageColumn.setSortable(true);

		eigenschaftAuspraegungTable.setColumnWidth(checkColumn, 40, Unit.PX);
		eigenschaftAuspraegungTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		eigenschaftAuspraegungTable.setWidth("97%", true);
		eigenschaftAuspraegungTable.setColumnWidth(eigenschaftColumn, "100px");
		eigenschaftAuspraegungTable.setColumnWidth(auspraegnungColumn, "100px");
		eigenschaftAuspraegungTable.setColumnWidth(imageColumn, "45px");
		eigenschaftAuspraegungTable.setColumnWidth(erstellerColumn, "65px");
		ListDataProvider<Relatable> dataProvider = new ListDataProvider<Relatable>();

		ListHandler<Relatable> sort = new ListHandler<Relatable>(dataProvider.getList());
		dataProvider.addDataDisplay(eigenschaftAuspraegungTable);
		eigenschaftAuspraegungTable.addColumnSortHandler(sort);
		eigenschaftAuspraegungTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Relatable>createCheckboxManager());
		this.add(eigenschaftAuspraegungTable);

		// ClickHandler zum teilen von Kontakten mit ausgewählten Ausprägungen.
		Button shareKontakt = new Button(
				"<image src='/images/share.png' width='20px' height='20px' align='center' /> teilen");
		shareKontakt.setStylePrimaryName("teilunsButtons");
		shareKontakt.setTitle("Kontakt teilen");

		// Zum löschen einer Auspraegung aus dem Kontakt.
		Button deleteAuspraegung = new Button(
				"<image src='/images/user.png' width='20px' height='20px' align='center' />"
						+ "<image src='/images/info.png' width='10px' height='10px' align='center' /> löschen");
		deleteAuspraegung.setStyleName("infoloeschenButton");
		deleteAuspraegung.setTitle("Löschen einer Eigenschaft mit seiner Ausprägung");

		// Größe des ScrollPanels bestimmen plus in das ScrollPanel die CellTable
		// hinzufügen.
		
		sp.setSize("900px", "400px");
		sp.add(eigenschaftAuspraegungTable);
		hp3.add(shareKontakt);
		hp3.add(deleteAuspraegung);

		RootPanel.get("content").add(this.status);

		this.add(sp);
		this.add(hp3);

		// ClickHandler für die persönliche suche von anderen Kontakten.
		shareKontakt.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Vector alle selektierten Eigenschaften/Ausprägungen mitgeben aber davor erst
				// leeren.
				gewaehlteAuspraegung.removeAll(gewaehlteAuspraegung);
				gewaehlteAuspraegung.addAll(selectionModel.getSelectedSet());

				MessageBox.shareAlertKontakt("Hinweis", "Geben Sie die Email des Empfängers an.", k,
						gewaehlteAuspraegung);
			}
		});

		// ClickHandler zum löschen einer Auspraegung.
		deleteAuspraegung.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Vector alle selektierten Eigenschaften/Ausprägungen mitgeben aber davor erst leeren.
				gewaehlteAuspraegung.removeAll(gewaehlteAuspraegung);
				gewaehlteAuspraegung.addAll(selectionModel.getSelectedSet());
				
				//Prüfung auf null Wert des Vectors
				if(gewaehlteAuspraegung.size() == 0) {
					MessageBox.alertWidget("Hinweis", "Wählen sie eine Eigenschaft aus um mit dem löschen Fortfahren zu können.");
				} else {
					//Wenn Vector nicht null, dann wird gefragt mit Anzahl ob EIgenschaften geloescht werden sollen.
						x = Window.confirm("Sind sie sicher " +gewaehlteAuspraegung.capacity() + " Eigenschaften löschen zu wollen?");
				}
				//Prüfung ob Nutzer Ja angewählt hat damit die Löschoperation durchgeführt werden kann.
				if(x == true) {
				 if (n.getId() == k.getOwnerId()) {
					 
					// Geht die ausgewählten Auspraegungen durch und übergibt jede einzelne für die
					// Löschung davon.
					for (int i = 0; i <= gewaehlteAuspraegung.size(); i++) {
						
						//Löschung der Berechtigung und dann wird die AUspraegung entfernt.
						ev.deleteAllBerechtigungenByOwner(n, gewaehlteAuspraegung.elementAt(i).getId(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();
								
							}

							@Override
							public void onSuccess(Void result) {
							//Hier passiert nichts weiter....
								
							}
							
						});
									
						//Loeschnung der ausgewaehlten Auspraegung.
						ev.deleteAuspraegungById(gewaehlteAuspraegung.elementAt(i).getId(), k.getId(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(Void result) {

								RootPanel.get("content").clear();
								RootPanel.get("contentHeader").clear();
								RootPanel.get("content").add(new KontaktForm(k));
						
							}
							

						});		
					
						//Prüfung ob das letzte Element erreicht wurde damit die MessageBox mit der Anzahl von gelöschten Eigenschaften erzeugt wird.
						if(gewaehlteAuspraegung.elementAt(i) == gewaehlteAuspraegung.lastElement()) {
						MessageBox.alertWidget("Hinweis", "Sie haben "+ gewaehlteAuspraegung.capacity()+ " Eigenschaften mit den dazugehörigen Ausprägungen erfolgreich gelöscht. ");
						}
					}

					} else if(k.getOwnerId() != n.getId()){
					// Wenn man nicht der Owner ist springt man in die else Anweisung hinein.
					for (int i = 0; i <= gewaehlteAuspraegung.size(); i++) {
						
					//Berechtigung an einer Eigenschaft aufloesen als Teilhaber der Eigenschaft.
					 ev.deleteAllBerechtigungenByReceiver(n, gewaehlteAuspraegung.elementAt(i).getId(), new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();
							
						}

						@Override
						public void onSuccess(Void result) {
							RootPanel.get("content").clear();
							RootPanel.get("contentHeader").clear();
							RootPanel.get("content").add(new KontaktForm(k));
							
						}
						 
					 });

					}
						
					
				}
				
				

			}
			}
			
			
		});

	}
	
	/**
	 * Methode zum pruefen wer der Eigentuemer ist fuer die
	 * Setzung des Labels des Eiegentuemers und des
	 * Teilungsstatuses.
	 * 
	 * @param k uebergebener aktueller Kontakt
	 * @param n aktuell eingeloggter Nutzer
	 */
	public void ownerPruefung(Kontakt k, Nutzer n) {
		// Abfrage wer der Owner der Liste ist.
		if (k.getOwnerId() != n.getId()) {
			ev.getNutzerById(k.getOwnerId(), new AsyncCallback<Nutzer>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Nutzer result) {
					ownerlb.setText("Eigentümer: " + result.getEmailAddress());
					ownerlb.setStylePrimaryName("labelD");
					hp.add(new HTML("<image src='/images/info3.png' width='22px' height='22px' />"));
					hp.add(ownerlb);
					hp.setTitle("Information des Eigentums");
				}

			});
		} else {
			
			ownerlb.setText("Sie sind der Eigentümer dieses Kontakts");
			ownerlb.setStylePrimaryName("labelD");
			hp.add(new HTML("<image src='/images/info3.png' width='22px' height='22px' />"));
			hp.add(ownerlb);
			hp.setTitle("Information des Eigentums");
	
		}
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
	 * 
	 * @return KEY_PROVIDER mit der Anzahl der Relatable Objekte
	 * @return null nichts kommt zurueck
	 */
	public static ProvidesKey<Relatable> getKeyProvider() {
		if (KEY_PROVIDER != null) {
			return KEY_PROVIDER;
		} else {
			return null;
		}
	}

}
