package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

public class MessageBox {
	
	static EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	/**
	 * Normale MessageBox die beim Aufruf eine Information ausgibt.
	 * @param header die Überschrift des Box
	 * @param content die Information die Übermittelt wird
	 * @return
	 */
	public static DialogBox alertWidget(final String header, final String content) {
        final DialogBox box = new DialogBox();
        final VerticalPanel panel = new VerticalPanel();
        box.setText(header);
        box.setGlassEnabled(true);
        panel.add(new HTML(content));
        final Button buttonClose = new Button("<image src='/images/ok.png' width='30px' height='30px' align='center' />");
        		
        buttonClose.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				box.hide();

			}
		});
        
        
        final Label emptyLabel = new Label("");
        emptyLabel.setSize("auto","25px");
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
	 * @param header Überschrift der Box
	 * @param content die Information zur Email eingabe.
	 * @param kl die Kontaktliste die geteilt wird.
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
        final Button buttonClose = new Button("<image src='/images/abbrechen.png' width='30px' height='30px' align='center' /> abbrechen");
        final Button buttonShare = new Button("<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");
        
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
							ev.shareObject(n.getId(), result.getId(), kl.getId(), kl.getType(),
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
        emptyLabel.setSize("auto","25px");
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
	 * Diese MessageBox wird beim teilen eines Kontaktes angezeigt.
	 * Es wird eine Email verlangt für wenn der Kontakt freigegeben werden soll.
	 * 
	 * @param header Überschrift der Box
	 * @param content die Information zur Email eingabe.
	 * @param k der Kontakt der geteilt wird.
	 * @return
	 */
	public static DialogBox shareAlertKontakt(final String header, final String content, Kontakt k) {
        final DialogBox box = new DialogBox();
        TextBox tb = new TextBox();
        final VerticalPanel panel = new VerticalPanel();
        final HorizontalPanel hp = new HorizontalPanel();
        box.setText(header);
        box.setGlassEnabled(true);
        panel.add(new HTML(content));
        final Button buttonClose = new Button("<image src='/images/abbrechen.png' width='30px' height='30px' align='center' /> abbrechen");
        final Button buttonShare = new Button("<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");
        
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
							ev.shareObject(n.getId(), result.getId(), k.getId(), k.getType(),
									new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Void result) {
											Window.alert("Sie haben den Kontakt: " + k.getVorname() + " "+ k.getNachname()
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
        emptyLabel.setSize("auto","25px");
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
   
//        panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
//        panel.setCellHorizontalAlignment(buttonShare, HasAlignment.ALIGN_LEFT);
       panel.add(hp);
        box.add(panel);
      
        box.center();
        box.show();
        return box;
    }
	
	
}
