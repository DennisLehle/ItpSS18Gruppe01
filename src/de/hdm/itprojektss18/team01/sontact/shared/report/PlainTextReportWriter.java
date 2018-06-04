package de.hdm.itprojektss18.team01.sontact.shared.report;

import java.util.Vector;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels Plain Text formatiert. Das
 * im Zielformat vorliegende Ergebnis wird in der Variable
 * <code>reportText</code> abgelegt und kann nach Aufruf der entsprechenden
 * Prozessierungsmethode mit <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 */
public class PlainTextReportWriter extends ReportWriter {

  /**
   * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
   * <code>process...</code>-Methoden) belegt. Format: Text
   */
  private String reportText = "";

  /**
   * Zurücksetzen der Variable <code>reportText</code>.
   */
  public void resetReportText() {
    this.reportText = "";
  }

  /**
   * Header-Text produzieren.
   * 
   * @return Text
   */
  public String getHeader() {
    // Wir benötigen für Demozwecke keinen Header.
    return "";
  }

  /**
   * Trailer-Text produzieren.
   * 
   * @return Text
   */
  public String getTrailer() {
    // Wir verwenden eine einfache Trennlinie, um das Report-Ende zu markieren.
    return "___________________________________________";
  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
   * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
   * 
   * @param r der zu prozessierende Report
   */
//  @Override
//public void process(AllAccountsOfCustomerReport r) {
//
//    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
//    this.resetReportText();
//
//    /*
//     * In diesen Buffer schreiben wir während der Prozessierung sukzessive
//     * unsere Ergebnisse.
//     */
//    StringBuffer result = new StringBuffer();
//
//    /*
//     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports
//     * ausgelesen und in Text-Form übersetzt.
//     */
//    result.append("*** " + r.getTitle() + " ***\n\n");
//    result.append(r.getHeaderData() + "\n");
//    result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");
//    Vector<Row> rows = r.getRows();
//
//    for (Row row : rows) {
//      for (int k = 0; k < row.getNumColumns(); k++) {
//        result.append(row.getColumnAt(k) + "\t ; \t");
//      }
//
//      result.append("\n");
//    }
//
//    result.append("\n");
//    result.append(r.getImprint() + "\n");
//
//    /*
//     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
//     * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
//     * Ergebnis mittels getReportText() auszulesen.
//     */
//    this.reportText = result.toString();
//  }
//
//  /**
//   * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
//   * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
//   * 
//   * @param r der zu prozessierende Report
//   */
//  @Override
//public void process(AllAccountsOfAllCustomersReport r) {
//
//    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
//    this.resetReportText();
//
//    /*
//     * In diesen Buffer schreiben wir während der Prozessierung sukzessive
//     * unsere Ergebnisse.
//     */
//    StringBuffer result = new StringBuffer();
//
//    /*
//     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports
//     * ausgelesen und in Text-Form übersetzt.
//     */
//    result.append("*** " + r.getTitle() + " ***\n\n");
//
//    if (r.getHeaderData() != null)
//      result.append(r.getHeaderData() + "\n");
//
//    result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");
//
//    /*
//     * Da AllAccountsOfAllCustomersReport ein CompositeReport ist, enthält r
//     * eine Menge von Teil-Reports des Typs AllAccountsOfCustomerReport. Für
//     * jeden dieser Teil-Reports rufen wir processAllAccountsOfCustomerReport
//     * auf. Das Ergebnis des jew. Aufrufs fügen wir dem Buffer hinzu.
//     */
//    for (int i = 0; i < r.getNumSubReports(); i++) {
//      /*
//       * AllAccountsOfCustomerReport wird als Typ der SubReports vorausgesetzt.
//       * Sollte dies in einer erweiterten Form des Projekts nicht mehr gelten,
//       * so müsste hier eine detailliertere Implementierung erfolgen.
//       */
//      AllAccountsOfCustomerReport subReport = (AllAccountsOfCustomerReport) r
//          .getSubReportAt(i);
//
//      this.process(subReport);
//
//      // Ein Form Feed wäre hier statt der 5 Leerzeilen auch denkbar...
//      result.append(this.reportText + "\n\n\n\n\n");
//
//      /*
//       * Nach jeder Übersetzung eines Teilreports und anschließendem Auslesen
//       * sollte die Ergebnisvariable zurückgesetzt werden.
//       */
//      this.resetReportText();
//    }
//
//    /*
//     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
//     * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
//     * Ergebnis mittels getReportText() auszulesen.
//     */
//    this.reportText = result.toString();
//  }
//
//  /**
//   * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
//   * 
//   * @return ein String bestehend aus einfachem Text
//   */
//  public String getReportText() {
//    return this.getHeader() + this.reportText + this.getTrailer();
//  }

@Override
public void process(AllKontakteByNutzerReport r) {
	// TODO Auto-generated method stub
	
}

@Override
public void process(AllKontakteReport r) {
	// TODO Auto-generated method stub
	
}
}