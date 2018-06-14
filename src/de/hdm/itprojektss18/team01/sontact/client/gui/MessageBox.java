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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

public class MessageBox {

	static EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	/**
	 * Normale MessageBox die beim Aufruf eine Information ausgibt.
	 * 
	 * @param header
	 *            die Überschrift des Box
	 * @param content
	 *            die Information die Übermittelt wird
	 * @return
	 */
	public static DialogBox alertWidget(final String header, final String content) {
		final DialogBox box = new DialogBox();
		final VerticalPanel panel = new VerticalPanel();
		box.setText(header);
		box.setGlassEnabled(true);
		panel.add(new HTML(content));
		final Button buttonClose = new Button(
				"<image src='/images/ok.png' width='30px' height='30px' align='center' />");

		buttonClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				box.hide();

			}
		});

		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);
		buttonClose.setWidth("90px");
		panel.add(buttonClose);
		panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
		box.add(panel);
		box.center();
		box.show();
		return box;
	}

	/**
	 * Die Erweiterung fürs Teilen einer Kontaktliste.
	 * 
	 * @param header
	 *            Überschrift der Box
	 * @param content
	 *            die Information zur Email eingabe.
	 * @param kl
	 *            die Kontaktliste die geteilt wird.
	 * @return
	 */
	public static DialogBox shareAlert(final String header, final String content, Kontaktliste kl) {
		final DialogBox box = new DialogBox();
		TextBox tb = new TextBox();
		final VerticalPanel panel = new VerticalPanel();
		final HorizontalPanel hp = new HorizontalPanel();
		box.setText(header);
		box.setGlassEnabled(true);
		panel.add(new HTML(content));
		final Button buttonClose = new Button(
				"<image src='/images/abbrechen.png' width='30px' height='30px' align='center' /> abbrechen");
		final Button buttonShare = new Button(
				"<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");

		buttonClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				box.hide();

			}
		});

		buttonShare.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Nutzer n = new Nutzer();
				n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				// Erstmal die eingegebene Email des Nutzers aus der Db holen mit dem geteilt
				// werden soll.
				ev.getUserByGMail(tb.getText(), new AsyncCallback<Nutzer>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Nutzer result) {
						// Wenn es die eingegebene EMail vorhanden ist
						if (result != null) {
							/*
							 * Es wird vom Nutzer(Owner), vom gefundenen Nutzer(Receiver), die Kontaktlisten
							 * id der Kontatkliste und der Typ('l') übergeben.
							 */
							ev.shareObject(n.getId(), result.getId(), kl.getId(), kl.getType(), null,
									new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {
											Window.alert("Sie haben die Kontaktliste " + kl.getTitel()
													+ " erfolgreich geteilt.");
											box.hide();
										}

									});

						} else {
							Window.alert("Es wurde kein Nutzer mit dieser EMail gefunden.");
						}
					}
				});

			}
		});

		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);
		panel.add(tb);
		buttonClose.setWidth("80px");
		buttonClose.setHeight("60px");
		buttonShare.setWidth("80px");
		buttonShare.setHeight("60px");
		buttonShare.setStyleName("Button");

		hp.add(buttonShare);
		hp.add(buttonClose);

		panel.add(hp);
		box.add(panel);
		box.center();
		box.show();
		return box;
	}

	/**
	 * Diese MessageBox wird beim teilen eines Kontaktes angezeigt. Es wird eine
	 * Email verlangt für wenn der Kontakt freigegeben werden soll.
	 * 
	 * @param header
	 *            Überschrift der Box
	 * @param content
	 *            die Information zur Email eingabe.
	 * @param k
	 *            der Kontakt der geteilt wird.
	 * @return
	 */
	public static DialogBox shareAlertKontakt(final String header, final String content, Kontakt k,
			Vector<Relatable> avshare) {
		final DialogBox box = new DialogBox();
		TextBox tb = new TextBox();
		Nutzer n = new Nutzer();
		final VerticalPanel panel = new VerticalPanel();
		final HorizontalPanel hp = new HorizontalPanel();

		box.setText(header);
		box.setGlassEnabled(true);
		panel.add(new HTML(content));
		final Button buttonClose = new Button(
				"<image src='/images/abbrechen.png' width='30px' height='30px' align='center' /> abbrechen");
		final Button buttonShare = new Button(
				"<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");

		buttonClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				box.hide();

			}
		});

		buttonShare.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Nutzer n = new Nutzer();
				n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
				n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

				// Erstmal die eingegebene Email des Nutzers aus der Db holen mit dem geteilt
				// werden soll.
				ev.getUserByGMail(tb.getText(), new AsyncCallback<Nutzer>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Nutzer result) {
						// Wenn es die eingegebene EMail vorhanden ist

						if (result != null) {
							/*
							 * Es werden für das Teilen des Kontaktes der Kontakt und seine ausgewählten
							 * Ausprägungen übergeben.
							 */
							ev.shareObject(n.getId(), result.getId(), k.getId(), k.getType(), avshare,
									new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {
											Window.alert("Sie haben den Kontakt: " + k.getVorname() + " "
													+ k.getNachname() + " erfolgreich geteilt.");
											box.hide();
										}

									});

						} else {
							Window.alert("Es wurde kein Nutzer mit dieser EMail gefunden.");
						}

					}
				});

			}
		});

		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);
		panel.add(tb);

		buttonClose.setWidth("80px");
		buttonClose.setHeight("60px");
		buttonShare.setWidth("80px");
		buttonShare.setHeight("60px");
		buttonShare.setStyleName("Button");

		hp.add(buttonShare);
		hp.add(buttonClose);

		panel.add(hp);
		box.add(panel);

		box.center();
		box.show();
		return box;
	}

	/**
	 * Methode für die Löschung einer Teilhaberschaft einer Kontaktliste oder eines
	 * Kontaktes. Diese Teilhaberschaftsauflösung wird vom Nutzer (Owner) initiiert,
	 * wenn er einem oder mehreren Nutzern die Teilhaberschaft entziehen will.
	 * 
	 * @param header
	 *            Überschift des MessageBox.
	 * @param content
	 *            Erklärung für den Nutzer.
	 * @param kl
	 *            das Object Kontaktliste.
	 * @param k
	 *            das Object Kontakt.
	 * @return
	 */
	public static DialogBox deleteTeilhaber(final String header, final String content, Kontaktliste kl, Kontakt k) {
		final DialogBox box = new DialogBox();
		final VerticalPanel panel = new VerticalPanel();
		final HorizontalPanel hp = new HorizontalPanel();
		final ScrollPanel sp = new ScrollPanel();
		CellTable<Nutzer> nutzerTable;
		Vector<Nutzer> nutzer = new Vector<Nutzer>();
		final MultiSelectionModel<Nutzer> selectionModel = new MultiSelectionModel<Nutzer>(getKeyProvider());

		// Nutzer Cookies holen.
		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		nutzerTable = new CellTable<Nutzer>();
		nutzerTable.setWidth("10");
		box.setText(header);
		box.setGlassEnabled(true);
		panel.add(new HTML(content));
		final Button buttonClose = new Button(
				"<image src='/images/abbrechen.png' width='30px' height='30px' align='center' /> abbrechen");
		final Button buttonDeleteKl = new Button(
				"<image src='/images/share.png' width='30px' height='30px' align='center' />"
						+ "<image src='/images/trash.png' width='20px' height='20px' align='center' /> löschen");
		final Button buttonDeleteK = new Button(
				"<image src='/images/share.png' width='30px' height='30px' align='center' />"
						+ "<image src='/images/trash.png' width='20px' height='20px' align='center' /> löschen");

		// Button zum beenden fals nichts gemacht werden soll.
		buttonClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				box.hide();

			}
		});

		// Button zum löschen der Teilhaberschaft an einem Kontakt.
		buttonDeleteK.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
				 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
				 */
				ev.getAllBerechtigungenByOwner(n.getId(), new AsyncCallback<Vector<Berechtigung>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();
					}

					@Override
					public void onSuccess(Vector<Berechtigung> result) {

						for (int i = 0; i < selectionModel.getSelectedSet().size(); i++) {

							if (result.elementAt(i).getObjectId() == k.getId()
									&& result.elementAt(i).getType() == k.getType()) {
								Berechtigung b = new Berechtigung();
								b.setObjectId(k.getId());
								b.setOwnerId(n.getId());
								b.setReceiverId(result.elementAt(i).getReceiverId());
								b.setType(k.getType());

								ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										caught.getMessage().toString();

									}

									@Override
									public void onSuccess(Void result) {
										Window.alert("Die Teilhaberschaft an dem Kontakt " + k.getVorname()
												+ k.getNachname() + " wurde erfolgreich gelöscht.");

									}
								});

							}

						}

					}

				});
			}

		});

		// Button zum löschen der Teilhaberschaft an einer Kontaktliste
		buttonDeleteKl.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
				 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
				 */
				ev.getAllBerechtigungenByOwner(n.getId(), new AsyncCallback<Vector<Berechtigung>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();
					}

					@Override
					public void onSuccess(Vector<Berechtigung> result) {

						for (int i = 0; i <= selectionModel.getSelectedSet().size(); i++) {
							for (int j = 0; j <= nutzer.size(); j++) {

								if (result.elementAt(i).getOwnerId() == n.getId()) {
									Berechtigung b = new Berechtigung();
									b.setObjectId(kl.getId());
									b.setOwnerId(n.getId());
									b.setReceiverId(nutzer.elementAt(j).getId());
									b.setType(kl.getType());

									ev.deleteBerechtigung(b, new AsyncCallback<Void>() {
										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {
											Window.alert("Die Teilhaberschaft an der Kontaktliste " + kl.getTitel()
													+ " wurde erfolgreich gelöscht.");

										}
									});

								} else {
									Window.alert("Uppppppps");
								}
							}

						}

					}

				});

			}
		});

		////////////////// Table-von-Email-Adressen//////////////////////////

		/*
		 * Prüfen ob es sich um eine Kontaktliste handelt.
		 */
		if (kl != null) {

			ev.sharedWith(kl.getId(), kl.getType(), n, new AsyncCallback<Vector<Nutzer>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Nutzer> result) {
					if (result.size() == 0) {

						nutzerTable.setVisible(false);
						Window.alert("Die Kontaktliste " + kl.getTitel() + " hat noch keine Teilhaber.");
						box.hide();

					} else {
						nutzerTable.setVisible(true);
						sp.setVisible(true);

					}
					nutzerTable.setRowCount(result.size(), true);
					nutzerTable.setVisibleRange(0, 10);
					nutzerTable.setRowData(result);

					// Gefundene Nutzer alle dem Vector hinzufügen für spätere Bearbeitung
					nutzer.removeAllElements();
					nutzer.addAll(result);

				}

			});

			/*
			 * Prüfen ob es sich um einen Kontakt handelt.
			 */
		} else if (k != null) {
			ev.sharedWith(k.getId(), k.getType(), n, new AsyncCallback<Vector<Nutzer>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Nutzer> result) {
					if (result.size() == 0) {

						nutzerTable.setVisible(false);
						Window.alert(
								"Der Kontakt " + k.getVorname() + " " + k.getNachname() + " hat noch keine Teilhaber.");
						box.hide();
					} else {
						nutzerTable.setVisible(true);
						sp.setVisible(true);

					}
					nutzerTable.setRowCount(result.size(), true);
					nutzerTable.setVisibleRange(0, 10);
					nutzerTable.setRowData(result);

				}

			});
		}

		/**
		 * Tabelle Befüllen mit den aus der DB abgerufenen Nutzer Informationen.
		 */
		TextColumn<Nutzer> nutzerColumn = new TextColumn<Nutzer>() {

			@Override
			public String getValue(Nutzer nutzer) {
				// TODO Auto-generated method stub
				return (String) nutzer.getEmailAddress();
			}
		};

		/**
		 * Implementierung der Checkbox fürs auswählen von einem oder mehrere
		 * Eigenschafen mit Ausprägungen.
		 */
		Column<Nutzer, Boolean> checkColumn = new Column<Nutzer, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Nutzer object) {
				return selectionModel.isSelected(object);
			}
		};

		/**
		 * Hinzufügen der Columns für die Darstellung der Kontaktlisten.
		 */
		nutzerTable.addColumn(nutzerColumn, "EMail Adresse: ");
		nutzerColumn.setSortable(true);

		nutzerTable.setColumnWidth(checkColumn, 40, Unit.PX);
		nutzerTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		nutzerTable.setWidth("50%", true);
		nutzerTable.setColumnWidth(nutzerColumn, "100px");

		ListDataProvider<Nutzer> dataProvider = new ListDataProvider<Nutzer>();

		ListHandler<Nutzer> sort = new ListHandler<Nutzer>(dataProvider.getList());
		dataProvider.addDataDisplay(nutzerTable);
		nutzerTable.addColumnSortHandler(sort);
		nutzerTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Nutzer>createCheckboxManager());

		//////////////////////////////////////////

		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);

		buttonClose.setWidth("80px");
		buttonClose.setHeight("60px");
		sp.add(hp);
		hp.add(nutzerTable);
		/*
		 * Prüfung um welches Objekt es sich handelt. Jenachdem werden die lösch Buttons
		 * ausgeblendet.
		 */
		if (k == null) {
			buttonDeleteK.setVisible(false);
			buttonDeleteKl.setWidth("80px");
			buttonDeleteKl.setHeight("60px");
			buttonDeleteKl.setStyleName("Button");
			hp.add(buttonDeleteKl);
		} else {
			buttonDeleteKl.setVisible(false);
			buttonDeleteK.setWidth("80px");
			buttonDeleteK.setHeight("60px");
			buttonDeleteK.setStyleName("Button");
			hp.add(buttonDeleteK);
		}

		hp.add(buttonClose);
		panel.add(hp);
		box.add(panel);

		box.center();
		box.show();
		return box;
	}

	/**
	 * Überprüfung bei Teilungsstatusabfrage von geteilten Ausprägungen/
	 * Eigenschaften. Es werden die Sichten des Owners oder Receivers überprüft, da
	 * der Receiver geteilte Ausprägungen/ Eigenschaften ebenso weiter teilen kann.
	 * 
	 * @param header
	 *            Info für den Nutzer
	 * @param content
	 *            Aktionsbeschreibung für den Nutzer
	 * @param sharedAus
	 *            Vektor der geteilten Auspraegungen
	 * @param b
	 *            Vektor der Berechtigungen des aktuellen Nutzers.
	 * @param k
	 *            Kontakt in dem man den Status abgefragt hat
	 * @return
	 */
	public static DialogBox statusAuspraegungTeilung(final String header, final String content,
			Vector<Relatable> sharedAus, Vector<Berechtigung> b, Kontakt k) {
		final DialogBox box = new DialogBox();
		final VerticalPanel panel = new VerticalPanel();

		box.setText(header);
		box.setGlassEnabled(true);
		panel.add(new HTML(content));

		final Button buttonClose = new Button(
				"<image src='/images/ok.png' width='30px' height='30px' align='center' />");

		// Nutzer Cookies holen.
		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		buttonClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				box.hide();

			}
		});
		// Prüfung ob es sich um den Owner handelt.
		if (n.getId() == k.getOwnerId()) {
			for (int i = 0; i < sharedAus.size(); i++) {
				// Leere String Vektoren erstellen um geteilte Eigenschaften hinzuzufügen.
				Vector<String> bezeichnung = new Vector<String>();
				Vector<String> wert = new Vector<String>();

				// Hinzufügen der Eigenschaften zu den davor erstellten String Vektoren.
				bezeichnung.addElement(sharedAus.elementAt(i).getBezeichnung());
				wert.addElement(sharedAus.elementAt(i).getWert());

				/*
				 * Prüfen ob eine Teilung vorhanden ist. Bei true wird eine HTML erstellt und
				 * die Eigenschaften mitdem Teilungssymbol hinzugefügt.
				 */
				ev.getStatusForObject(sharedAus.elementAt(i).getId(), 'a', new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Boolean bo) {

						/*
						 * Prüfung ob die Serverantwort geteilte Eigenschaften enthält in Form eines
						 * booleans der dann "true" ergebit.
						 */
						if (bo == true) {

							HTML eigenschaft = new HTML(bezeichnung + " - " + wert
									+ " <image src='/images/share.png' width='15px' height='15px' align='center' />");
							panel.add(eigenschaft);
						}
					}

				});

			}
			// Wenn man nicht der Owner ist.
		} else {

			for (int j = 0; j < b.size(); j++) {
				for (int i = 0; i < sharedAus.size(); i++) {
					// Leere String Vektoren erstellen um geteilte Eigenschaften hinzuzufügen.
					Vector<String> bezeichnung = new Vector<String>();
					Vector<String> wert = new Vector<String>();

					// Hinzufügen der Eigenschaften zu den davor erstellten String Vektoren.
					if (b.elementAt(j).getObjectId() == sharedAus.elementAt(i).getId()
							&& b.elementAt(j).getOwnerId() == n.getId() && b.elementAt(j).getType() == 'a') {

						bezeichnung.addElement(sharedAus.elementAt(i).getBezeichnung());
						wert.addElement(sharedAus.elementAt(i).getWert());
						HTML eigenschaft = new HTML(bezeichnung + " - " + wert
								+ " <image src='/images/share.png' width='15px' height='15px' align='center' />");
						panel.add(eigenschaft);

					}
				}
			}

		}

		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);
		buttonClose.setWidth("90px");
		panel.add(buttonClose);
		panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
		box.add(panel);
		box.center();
		box.show();
		return box;
	}

	/**
	 * KeyProvider für Identifizierung eines Nutzer-Objekts.
	 */
	public static final ProvidesKey<Nutzer> KEY_PROVIDER = new ProvidesKey<Nutzer>() {

		@Override
		public Object getKey(Nutzer item) {
			return item == null ? null : item.getId();
		}
	};

	/**
	 * Abfrage ob der KeyProvider null ist oder nicht.
	 * 
	 * @return1
	 */
	public static ProvidesKey<Nutzer> getKeyProvider() {
		if (KEY_PROVIDER != null) {
			return KEY_PROVIDER;
		} else {
			return null;
		}
	}

}
