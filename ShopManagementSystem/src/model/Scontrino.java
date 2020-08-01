package model;

public class Scontrino {

	
	private double totale;
	private String idScontrino;
	private String idOperatore;
	private String dataEmissione;
	
	

	public Scontrino() {
	}
	
	public Scontrino(double totale, String idScontrino, String idOperatore, String dataEmissione) {
		super();
		this.totale = totale;
		this.idScontrino = idScontrino;
		this.idOperatore = idOperatore;
		this.dataEmissione = dataEmissione;
	}
	
	
	/**
	 * @return the totale
	 */
	public double getTotale() {
		return totale;
	}
	/**
	 * @param totale the totale to set
	 */
	public void setTotale(double totale) {
		this.totale = totale;
	}
	
	
	public String getIdOperatore() {
		return idOperatore;
	}
	public void setIdOperatore(String idOperatore) {
		this.idOperatore = idOperatore;
	}
	public String getIdScontrino() {
		return idScontrino;
	}
	public void setIdScontrino(String idScontrino) {
		this.idScontrino = idScontrino;
	}
	
	public String getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(String dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	
	
	
	
}
