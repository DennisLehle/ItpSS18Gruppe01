package de.hdm.itprojektss18.team01.sontact.client;

import java.util.logging.Logger;
import de.hdm.itprojektss18.team01.sontact.shared.LoginService;
import de.hdm.itprojektss18.team01.sontact.shared.LoginServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.CommonSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorService;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGenerator;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse ClientsideSettings beinhaltet Eigenschaften und Dienste,
 * die für alle Client-seitigen Klassen relevant sind.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */

public class ClientsideSettings extends CommonSettings {
	
	/**
	 * Anlegen eines leeren Nutzers zur Speicherung des aktuellen Nutzers.
	 */
	public Nutzer nutzer = null;
	
	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>EditorService</code>.
	 */
	private static EditorServiceAsync editorVerwaltung = null;
	
	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>LoginService</code>.
	 */
	private static LoginServiceAsync loginService = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>ReportGenerator</code>.
	 */
	private static ReportGeneratorAsync reportGeneratorVerwaltung = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "Sontact Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ClientSideSettings.getLogger();
	 * </pre>
	 * 
	 * und dann Nachrichten schreiben etwa mittels
	 * 
	 * <pre>
	 * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	 * </pre>
	 * 
	 * oder
	 * 
	 * <pre>
	 * logger.info(&quot;Lege neuen Kunden an.&quot;);
	 * </pre>
	 * 
	 * <p>
	 * Bitte auf <em>angemessene Log Levels</em> achten! Severe und info sind
	 * nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfügen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen künftig
	 * sämtliches Logging im Code und können ohne abermaliges Kompilieren den
	 * Log Level "von außen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in Ihrem <code>war/WEB-INF</code>-Ordner.
	 * Der dort standardmäßig vorgegebene Log Level ist <code>WARN</code>.
	 * Dies würde bedeuten, dass Sie keine <code>INFO</code>-Meldungen wohl
	 * aber <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn
	 * Sie also auch Log des Levels <code>INFO</code> wollten, müssten Sie in
	 * dieser Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen Verwaltung. Diese
	 * Methode erstellt die Verwaltung, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>EditorSerivceAsync editorSerivce = ClientSideSettings.getEditorService()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>EditorServiceAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static EditorServiceAsync getEditorVerwaltung() {
		/**
		 * Gab es bislang noch keine Verwaltung-Instanz, wird einer erstellt.
		 */
		if (editorVerwaltung == null) {
			/**
			 * Instantiieren des EditorService's.
			 */
			editorVerwaltung = GWT.create(EditorService.class);
		}
		/**
		 * Rückgabe des EditorService's.
		 */
		return editorVerwaltung;
	}

	/**
	 * Anlegen und Auslesen der applikationsweit eindeutigen Verwaltung. Diese
	 * Methode erstellt die Verwaltung, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>LoginSerivceAsync loginSerivce = ClientSideSettings.getLoginService()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>LoginServiceAsync</code>
	 */

	public static LoginServiceAsync getLoginService() {
		/**
		 * Gab es bislang noch keine Verwaltung-Instanz, wird einer erstellt.
		 */
		if (loginService == null) {
			/**
			 * Instantiieren des EditorService's.
			 */
			loginService = GWT.create(LoginService.class);
		}
		/**
		 * R�ckgabe des EditorService's.
		 */
		return loginService;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	 * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>ReportGeneratorServiceAsync reportGeneratorSerivce = ClientSideSettings.getReportGeneratorService()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs
	 *         <code>ReportGeneratorServiceAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static ReportGeneratorAsync getReportGeneratorService() {
		/**
		 * Gab es bislang noch keine ReportGenerator-Instanz, wird eine neue
		 * erstellt. Auch ein sogenannter <Singleton>
		 */
		if (reportGeneratorVerwaltung == null) {

			reportGeneratorVerwaltung = GWT.create(ReportGenerator.class);
			final AsyncCallback<Void> initReportGeneratorServiceCallback = new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};
			reportGeneratorVerwaltung.init(initReportGeneratorServiceCallback);
		}
		/**
		 * Rückgabe des ReportGeneratorService's.
		 */
		return reportGeneratorVerwaltung;
	}
	
	/**
	 * Setzen eines Nutzers für die Bearbeitung in der Sontact Verwaltung
	 */
	public final void setCurrentNutzer(Nutzer n) {
		this.nutzer = n;
	}
	
	/**
	 * Auslesen eines Nutzers für die Bearbeitung in der Sontact Verwaltung
	 */
	public Nutzer getCurrentNutzer() {
		return nutzer;
	}
}
