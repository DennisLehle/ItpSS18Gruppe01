package de.hdm.itprojektss18.team01.sontact.shared.report;

/**
 * <p>
 * Diese Klasse wird benötigt, um auf dem Client die ihm vom Server zur
 * Verfügung gestellten <code>Report</code>-Objekte in ein menschenlesbares
 * Format zu überführen.
 * </p>
 * <p>
 * Das Zielformat kann prinzipiell beliebig sein. Methoden zum Auslesen der in
 * das Zielformat überführten Information wird den Subklassen überlassen. In
 * dieser Klasse werden die Signaturen der Methoden deklariert, die für die
 * Prozessierung der Quellinformation zuständig sind.
 * </p>
 * 
 * @author Thies
 */
public abstract class ReportWriter {

	/**
	 * Uebersetzen eines <code>AlleKontakteNachEigenschaftReport</code> in das
	 * Zielformat.
	 * 
	 * @param r der zu uebersetzende Report
	 */
  public abstract void process(AlleKontakteNachEigenschaftenReport r);

  
  
  	/**
  	 * Uebersetzen eines <code>AlleKontakteReport</code> in das
  	 * Zielformat.
  	 * 
  	 * @param r der zu uebersetzende Report
  	 */
  public abstract void process(AlleKontakteReport r);
  
  
  
  	/**
  	 * Uebersetzen eines <code>AlleGeteiltenKontakteReport</code> in das
  	 * Zielformat.
  	 * 
  	 * @param r der zu uebersetzende Report
  	 */
  public abstract void process(AlleGeteiltenKontakteReport r);


}
