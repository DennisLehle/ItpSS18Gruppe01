package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.BusinessObject;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Darstellung der Navigation durch Kontaktlisten und deren Kontakte.
 * 
 * @author Dennis Lehle, Ugur Bayrak, Kevin Batista
 */

public class SontactTreeViewModel implements TreeViewModel {

	/**
	 * EditorService wird auf null gesetzt und stellt die Verbindung zum Async
	 * Interface dar.
	 */
	private EditorServiceAsync ev = null;

	/**
	 * Nutzer wird auf null gesetzt.
	 */
	private Nutzer n = null;

	/**
	 * ListDataProvider-Objekte verwalten das Datenmodell auf Client-Seite.
	 */
	private ListDataProvider<Kontaktliste> kontaktlisteDataProvider = null;

	/**
	 * Diese Map speichert die ListDataProviders fuer die Kontaktlisten.
	 */
	private Map<Kontaktliste, ListDataProvider<Kontakt>> kontaktDataProvider = null;

	/**
	 * Selektierung einer Kontaktliste / eines Kontakts moeglich.
	 */
	private Kontaktliste selectedKontaktliste = null;
	private Kontakt selectedKontakt = null;

	/**
	 * Formulare zur Anpassung von Kontaktlisten/Kontakte im TreeViewModel.
	 */
	private KontaktlisteForm kontaktlisteForm = null;
	private KontaktForm kontaktForm = null;

	/**
	 * Bildet BusinessObjects auf eindeutige Stringobjekte ab, die als Schluessel fuer
	 * Baumknoten dienen. Dadurch werden im Selektionsmodell alle Objekte mit
	 * derselben string selektiert, wenn eines davon selektiert wird.
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {

		@Override
		public String getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			} else if (bo instanceof Kontaktliste) {
				return "KL-" + bo.getId();
			} else if (bo instanceof Kontakt) {
				return "K-" + bo.getId();
			}
			return null;

		}
	}

	/**
	 * Die lokal definierte Klasse BusinessObjectKeyProvider wird nun als Variable
	 * der Klasse SontactTreeViewModel hinzugefuegt.
	 */
	private BusinessObjectKeyProvider boKeyProvider = null;

	/**
	 * Das <code>SingleSelectionObject</code> wird von GWT vordefiniert Diese
	 * beschreibt die Auswahl von Objekten im Baum.
	 */
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	/**
	 * Nested Class fuer die Reaktion auf Selektionsereignisse. Als Folge einer
	 * Baumknotenauswahl wird je nach Typ des Business-Objekts die
	 * "selectedKontaktliste" bzw. der "selectedKontakt" gesetzt.
	 * 
	 * @author Rathke
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

			// Abfrage des ausgewaehlten Objekts
			BusinessObject selection = selectionModel.getSelectedObject();

			if (selection instanceof Kontaktliste) {
					RootPanel.get("content").add(new KontaktlisteForm((Kontaktliste) selection));
			} else if (selection instanceof Kontakt) {
				RootPanel.get("content").add(new KontaktForm((Kontakt) selection));

			}
		}
	}

	/**
	 * Konstruktur der Klasse SontactTreeViewModel.
	 * 
	 * @param n der aktuell eingeloggte Nutzer
	 */
	public SontactTreeViewModel(final Nutzer n) {
		this.n = n;
		this.ev = ClientsideSettings.getEditorVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		kontaktDataProvider = new HashMap<Kontaktliste, ListDataProvider<Kontakt>>();
	}

	/**
	 * Formulare fuer die Setter.
	 * 
	 * @param kontaktlisteForm Kontaktliste die gesetzt wird
	 */
	public void setKontaktlisteForm(KontaktlisteForm kontaktlisteForm) {
		this.kontaktlisteForm = kontaktlisteForm;
	}

	public void setKontaktForm(KontaktForm kontaktForm) {
		this.kontaktForm = kontaktForm;
	}

	/**
	 * Methode zum Abfragen der aktuell selektierten Kontaktliste.
	 * 
	 * @return aktuell selektierte Kontaktliste
	 */
	public Kontaktliste getSelectedKontaktliste() {
		return selectedKontaktliste;
	}

	/**
	 * Setzt die selektierte Kontaktliste welches der Nutzer angeklickt hat. Die
	 * selektierte Kontaktliste wird der KontaktlisteForm/Kontaktform mitgeteilt.
	 * 
	 * @param selectedKontaktliste die ausgewahelt wurde
	 */
	public void setSelectedKontaktliste(Kontaktliste selectedKontaktliste) {

		this.selectedKontaktliste = selectedKontaktliste;
		this.kontaktlisteForm.setSelectedKontaktliste(selectedKontaktliste);
		this.kontaktForm.setSelectedKontakt(selectedKontakt);

		this.selectedKontakt = null;
		this.kontaktForm.setSelectedKontakt(null);

	}

	/**
	 * Methode zum Abfragen des aktuell selektierten Kontakts.
	 * 
	 * @return aktuell selektierter Kontakt
	 */
	public Kontakt getSelectedKontakt() {
		return selectedKontakt;
	}

	/**
	 * Diese Methode wird aufgerufen wenn der Nutzer einen Kontakt selektiert.
	 * 
	 * @param selectedKontakt der ausgewaehlt wurde
	 */
	public void setSelectedKontakt(Kontakt selectedKontakt) {

		// Aenderungen am Kontakt durch KontaktForm moeglich.
		this.selectedKontakt = selectedKontakt;
		this.kontaktForm.setSelectedKontakt(selectedKontakt);

		// Kontaktliste in der sich Kontakte befinden wird aktualsiert.
		if (selectedKontakt != null) {

			// Selektierte Kontaktliste anhand der ID in der Datenbank finden.
			this.ev.getKontaktlisteById(this.selectedKontakt.getKontaktlisteId(), new AsyncCallback<Kontaktliste>() {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Hopala");
				}

				@Override
				public void onSuccess(Kontaktliste result) {
					selectedKontaktliste = result;
					kontaktlisteForm.setSelectedKontaktliste(result);
				}
			});
		}
	}

	/**
	 * Fuegt eine neue Kontaktliste dem Baum hinzu.
	 * 
	 * @param kontaktliste die hinzugefuegt wird
	 */
	public void addKontaktliste(Kontaktliste kontaktliste) {
		this.kontaktlisteDataProvider.getList().add(kontaktliste);

		// Selektiert die Kontaktliste
		this.selectionModel.setSelected(kontaktliste, true);
	}

	/**
	 * Aktualsierung des Kontaktlisten-Objekts.
	 * 
	 * @param kontaktliste die aktualisiert wird
	 */
	public void updateKontaktliste(Kontaktliste kontaktliste) {
		List<Kontaktliste> kontaktlisteList = this.kontaktlisteDataProvider.getList();
		int i = 0;
		for (Kontaktliste aktuell : kontaktlisteList) {
			if (aktuell.getId() == kontaktliste.getId()) {
				kontaktlisteList.set(i, kontaktliste);
				break;
			} else {
				i++;
			}
		}
		this.kontaktlisteDataProvider.refresh();
	}

	/**
	 * Loeschung eines Kontaktlisten-Objekts.
	 * 
	 * @param kontaktliste die geloescht wird
	 */
	public void deleteKontaktliste(Kontaktliste kontaktliste) {

		this.kontaktDataProvider.remove(kontaktliste);

		this.kontaktlisteDataProvider.getList().remove(kontaktliste);
	}

	/**
	 * Hinzufuegen eines Kontakt-Objekts in eine Kontaktliste.
	 * 
	 * @param kontakt der hinzugefuegt wird
	 * @param kontaktliste in der der Kontakt hinzugefuegt wird
	 */
	public void addKontakt(Kontakt kontakt, Kontaktliste kontaktliste) {

		// Falls es noch keinen Kontakt Provider fuer diese Kontaktliste gibt,
		// wurde der Baumknoten noch nicht geoeffnet und wir brauchen nichts tun.
		if (!this.kontaktDataProvider.containsKey(kontaktliste)) {
			return;
		}
		
		// Erstellt einen ListDataProvider mit Kontakten der ausgewaehlten Kontaktliste.
		ListDataProvider<Kontakt> kontaktProvider = this.kontaktDataProvider.get(kontaktliste);
		if (!kontaktProvider.getList().contains(kontakt)) {

			kontaktProvider.getList().add(kontakt);
		}

		this.selectionModel.setSelected(kontakt, true);

	}

	/**
	 * Aktualsierung eines Kontakt-Objekts.
	 * 
	 * @param kontakt der aktualisiert wird
	 */
	public void updateKontakt(final Kontakt kontakt) {
		
		//Auslesen der Kontaktlisten per id
		this.ev.getKontaktlisteById(kontakt.getKontaktlisteId(), new AsyncCallback<Kontaktliste>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Hopala");
			}

			@Override
			public void onSuccess(Kontaktliste result) {
				List<Kontakt> kontaktList = kontaktDataProvider.get(result).getList();

				for (int i = 0; i < kontaktList.size(); i++) {
					if (kontakt.getId() == kontaktList.get(i).getId()) {
						kontaktList.set(i, kontakt);
						break;
					}
				}
			}
		});
	}

	/**
	 * Loeschung eines Kontakt-Objekts aus der Kontaktliste.
	 * 
	 * @param kontakt der geloescht wird
	 * @param kontaktliste aus der der Kontakt geloescht wird
	 */
	public void deleteKontakt(Kontakt kontakt, Kontaktliste kontaktliste) {

		// Wenn der Baumknoten noch nicht angelegt wurde, gibt es hier nichts zu tun
		if (!this.kontaktDataProvider.containsKey(kontaktliste)) {
			return;
		}

		kontaktDataProvider.get(kontaktliste).getList().remove(kontakt);
		selectionModel.setSelected(kontaktliste, true);
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		// Auflistung der Kontaktlisten
		if (value == null) {

			// Erzeugen eines neuen ListDataProviders fuer Kontaktlisten
			this.kontaktlisteDataProvider = new ListDataProvider<Kontaktliste>();

			// Abfrage aller Kontaktlisten des Nutzer wo er als Eigentuemer deklariert ist
			this.ev.getKontaktlistenByOwner(this.n, new AsyncCallback<Vector<Kontaktliste>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Vector<Kontaktliste> result) {
					
					// Alle gefundenen Kontaktlisten werden dem DataProvider hinzugefuegt
					for (Kontaktliste kontaktliste : result) {
						kontaktlisteDataProvider.getList().add(kontaktliste);
					}
					
					//Wenn die eigenen Kontakte in dem Baum eingepflegt wurden werden nun die von
					//anderen Nutzern geteilten Kontaktlisten eingepflegt.
					ev.getAllSharedKontaktlistenByReceiver(n.getId(), new AsyncCallback<Vector<Kontaktliste>>() {

						@Override
						public void onFailure(Throwable error) {
							error.getMessage().toString();
							
						}

						@Override
						public void onSuccess(Vector<Kontaktliste> result) {
							
							//Geteilten Kontaktlisten deren Namen jetzt bekannt sind hinzufügen.
							for (Kontaktliste kontaktliste : result) {
								kontaktlisteDataProvider.getList().add(kontaktliste);
						}
						}
					});
					}
			});
			
			return new DefaultNodeInfo<Kontaktliste>(kontaktlisteDataProvider, new KontaktlisteCell(), selectionModel, null);
		}
	

		
		 // Wenn Value eine Instanz von Kontaktliste ist werden die Kontakte aus der
		 // Datenbank rausgelesen und dem ListDataProvider hinzugef�gt.
		if (value instanceof Kontaktliste) {
			
			final ListDataProvider<Kontakt> kontaktLDP = new ListDataProvider<Kontakt>();
			this.kontaktDataProvider.put((Kontaktliste) value, kontaktLDP);
			int kontaktlisteId = ((Kontaktliste) value).getId();
			
			// Auslesen der Kontakte die in einer spezfischen Kontaktliste enthalten sind
			this.ev.getKontakteByKontaktliste(kontaktlisteId, new AsyncCallback<Vector<Kontakt>>() {

				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Hopla, Kontakte konnten nicht aufgerufen werden");
				}

				public void onSuccess(Vector<Kontakt> result) {
					for (Kontakt kontakt : result) {
						kontaktLDP.getList().add(kontakt);
					}
				}

			});

			return new DefaultNodeInfo<Kontakt>(kontaktLDP, new KontaktCell(), selectionModel, null);
	
		}
		
		return null;
	}
	
	/**
	 * Ueberpruefung ob das Objekt ein Blatt ist.
	 */
	public boolean isLeaf(Object value) {
		return (value instanceof Kontakt);
	}

}