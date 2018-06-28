package de.hdm.itprojektss18.team01.sontact.shared.report;

import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.report.CompositeReport;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 */
public class HTMLReportWriter extends ReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * <code>process...</code>-Methoden) belegt. Format: HTML-Text
	 */
	private String reportText = "";

	/**
	 * Zurücksetzen der Variable <code>reportText</code>.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Umwandeln eines <code>Paragraph</code>-Objekts in HTML.
	 * 
	 * @param p
	 *            der Paragraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraph2HTML((CompositeParagraph) p);
		} else {
			return this.paragraph2HTML((SimpleParagraph) p);
		}
	}

	/**
	 * Umwandeln eines <code>CompositeParagraph</code>-Objekts in HTML.
	 * 
	 * @param p
	 *            der CompositeParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * Umwandeln eines <code>SimpleParagraph</code>-Objekts in HTML.
	 * 
	 * @param p
	 *            der SimpleParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(SimpleParagraph p) {
		return "<p>" + p.toString() + "</p>";
	}

	/**
	 * HTML-Header-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}

	/**
	 * HTML-Trailer-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	/**
	 * Prozessieren des uebergebenen Reports und Ablage im Zielformat. Ein Auslesen
	 * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */
	@Override
	public void process(AlleKontakteReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
		this.resetReportText();

		/*
		 * In diesen Buffer schreiben wir waehrend der Prozessierung sukzessiv unsere
		 * Ergebnisse.
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in HTML-Form uebersetzt.
		 */
		result.append("<H3 style=\"font-family:sans-serif;\">" + r.getTitle() + "</H3>");
		result.append("<table style=\"width:500px;border:1px solid #e6e6e6; font-family: sans-serif;\"></br><tr>");
		result.append("<tr><td>" + " Erstellungsdatum des Reports | " + r.created().toString() + "</td></tr>");
		// result.append("<td valign=\"top\"><b>" + paragraph2HTML(r.getHeaderData())
		// + "</b></td></table>");

		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:100%\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("</td></tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");
				}

				else if (row.getNumColumns() == 2) {
					result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");
				}

				else if (row.getNumColumns() == 6) {
					result.append(
							"<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">" + "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
				}

				else {
					if (i > 1) {
						result.append("<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");

					}

					else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}

			}

			result.append("</tr>");

		}

		result.append("</table>");

		/*
		 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
		 * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
		 * Ergebnis mittels getReportText() auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
	 * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */
	@Override
	public void process(AlleKontakteNachEigenschaftenReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
		this.resetReportText();

		/*
		 * In diesen Buffer schreiben wir waehrend der Prozessierung sukzessiv unsere
		 * Ergebnisse.
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in HTML-Form uebersetzt.
		 */
		result.append("<H3 style=\"font-family:sans-serif;\">" + r.getTitle() + "</H3>");
		result.append("<table style=\"width:500px;border:1px solid #e6e6e6; font-family: sans-serif;\"><tr>");
		result.append("</tr><tr><td>" + " Erstellungsdatum des Reports|" + r.created().toString() + "</td></br></tr>");

		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:100%\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("</td></tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");
				}

				else if (row.getNumColumns() == 2) {
					result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif\">" + row.getColumnAt(k) + "</td>");
				}

				else if (row.getNumColumns() == 6) {
					result.append(
							"<td height=\"50\"; style=\"border-top: 3px solid #5669b1; font-family: sans-serif\">" + "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
				}

				else {
					if (i > 1) {
						result.append("<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif\">" + row.getColumnAt(k) + "</td>");

					}

					else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}

			}

			result.append("</tr>");

		}

		result.append("</table>");

		/*
		 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
		 * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
		 * Ergebnis mittels getReportText() auszulesen.
		 */
		this.reportText = result.toString();
	}

	@Override
	public void process(AlleKontakteNachTeilhabernReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
		this.resetReportText();

		/*
		 * In diesen Buffer schreiben wir waehrend der Prozessierung sukzessiv unsere
		 * Ergebnisse.
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in HTML-Form uebersetzt.
		 */
		result.append("<H3 style=\"font-family:sans-serif;\">" + r.getTitle() + "</H3>");
		result.append("<table style=\"width:500px;border:1px solid #e6e6e6; font-family: sans-serif;\"><tr>");
		result.append("</tr><tr><td>" + " Erstellungsdatum des Reports|" + r.created().toString() + "</td></br></tr>");

		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:100%\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("</td></tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");
				}

				else if (row.getNumColumns() == 2) {
					result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");
				}

				else if (row.getNumColumns() == 6) {
					result.append(
							"<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">" + "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
				}

				else {
					if (i > 1) {
						result.append("<td height=\"25\";style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");

					}

					else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}

			}

			result.append("</tr>");

		}

		result.append("</table>");

		/*
		 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
		 * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
		 * Ergebnis mittels getReportText() auszulesen.
		 */
		this.reportText = result.toString();
	}
	
@Override
	public void process(AlleGeteiltenKontakteReport r) {
	// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
			this.resetReportText();

			/*
			 * In diesen Buffer schreiben wir waehrend der Prozessierung sukzessiv unsere
			 * Ergebnisse.
			 */
			StringBuffer result = new StringBuffer();

			/*
			 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
			 * ausgelesen und in HTML-Form uebersetzt.
			 */
			result.append("<H3 style=\"font-family:sans-serif;\">" + r.getTitle() + "</H3>");
			result.append("<table style=\"width:500px;border:1px solid #e6e6e6; font-family: sans-serif;\"><tr>");
			result.append("</tr><tr><td>" + " Erstellungsdatum des Reports|" + r.created().toString() + "</td></br></tr>");

			Vector<Row> rows = r.getRows();
			result.append("<table style=\"width:100%\">");

			for (int i = 0; i < rows.size(); i++) {
				Row row = rows.elementAt(i);
				result.append("</td></tr>");
				for (int k = 0; k < row.getNumColumns(); k++) {
					if (i == 0) {
						result.append("<td style=\"background:lightsteelblue;font-weight:bold; font-size:larger; font-family:sans-serif;\">" + row.getColumnAt(k) + "</td>");
					}

					else if (row.getNumColumns() == 2) {
						result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");
					}

					else if (row.getNumColumns() == 6) {
						result.append(
								"<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">" + "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
					}

					else {
						if (i > 1) {
							result.append("<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">" + row.getColumnAt(k) + "</td>");

						}

						else {
							result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
						}
					}

				}

				result.append("</tr>");

			}

			result.append("</table>");

			/*
			 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
			 * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
			 * Ergebnis mittels getReportText() auszulesen.
			 */
			this.reportText = result.toString();
		}
		
	
	/**
	 * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	 * 
	 * @return ein String im HTML-Format
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}

	

}