package model;

public class Dettagli extends Scontrino{

	private String ean;
	private String idScontrino;
	
	public Dettagli() {
		
	}
	
	public Dettagli(String idScontrino, String ean) {
		setEan(ean);
		setIdScontrino(idScontrino);
	}
	
	public String getIdScontrino() {
		return idScontrino;
	}
	public void setIdScontrino(String idScontrino) {
		this.idScontrino = idScontrino;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	
}
