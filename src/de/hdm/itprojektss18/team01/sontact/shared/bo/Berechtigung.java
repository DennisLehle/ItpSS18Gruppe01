package de.hdm.itprojektss18.team01.sontact.shared.bo;


/**
 * Realisierung einer exemplarischen Berechtigungsklasse. 
 */
public class Berechtigung extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzvariablen der Klasse Berechtigung
	 */
	private int senderId;
	private int userId;
	private int objectId;
	private char objectType;
	private Berechtigungsstufe berechtigungsstufe;
	
	/**
	 * Konstruktoren
	 */
    public Berechtigung(int id, Berechtigungsstufe b) {
        this.id = id;
        this.berechtigungsstufe = b;
    }
    
    public Berechtigung(Berechtigungsstufe b) {
        this.berechtigungsstufe = b;
    }

    public Berechtigung() {
        this.berechtigungsstufe = Berechtigungsstufe.NONE;
    }

    public boolean isUserAllowedTo(Berechtigungsstufe action) {

        return this.berechtigungsstufe.wert >= action.wert;
    }
    
    /**
     * Enumerator
     */
    public enum Berechtigungsstufe {

        NONE(0), READ(10), EDIT(20), DELETE(30);

        private int wert;

        Berechtigungsstufe(int b) {
            wert = b;
        }
    }
	
	/**
	 * Getter- und Setter-Methoden zum Setzen und Auslesen der Werte
	 */
    public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	
	public char getObjectTyoe() {
		return objectType;
	}
	public void setObjectType(char objectType) {
		this.objectType = objectType;
	}
	
	public Berechtigungsstufe getBerechtigungsstufe() {
		return berechtigungsstufe;
	}
	public void setBerechtigungsstufe(Berechtigungsstufe berechtigungsstufe) {
		this.berechtigungsstufe = berechtigungsstufe;
	}
	
    @Override
    public String toString() {
        return "Berechtigung [id=" + id + ", senderId=" + senderId + ", userId=" + userId + 
        		", objectId=" + objectId + ", type= " + objectType + "berechtigungsstufe= " + berechtigungsstufe + "]";
    }
  
}
