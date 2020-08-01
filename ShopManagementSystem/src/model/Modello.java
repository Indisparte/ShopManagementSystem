package model;

import model.Collezione;

public class Modello extends Collezione{
	private String nome, sesso;
	private String ean;
	private String idCollezione;
	private double prezzo;
	private String idFornitore;
	private String descrizione;
	private String IDModello;
	private byte[] img ;
	private int sconto;
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getSesso() {
		return sesso;
	}



	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getIdCollezione() {
		return idCollezione;
	}



	public void setIdCollezione(String idCollezione) {
		this.idCollezione = idCollezione;
	}


	
	public Modello() {}
	
	

	public Modello(String nome, String sesso, String ean, String idCollezione, double prezzo, String idFornitore, String descrizione, String iDModello, byte[] img, int sconto) {
		super();
		this.nome= nome;
		this.sesso = sesso;
		this.ean = ean;
		this.idCollezione = idCollezione;
		this.prezzo = prezzo;
		this.idFornitore = idFornitore;
		this.descrizione = descrizione;
		this.IDModello = iDModello;
		this.img = img;
		this.sconto = sconto;
	}

	/**
	 * @return the ean
	 */
	public String getEan() {
		return ean;
	}
	/**
	 * @param ean the ean to set
	 */
	public void setEan(String ean) {
		this.ean = ean;
	}
	/**
	 * @return the prezzo
	 */
	public double getPrezzo() {
		return prezzo;
	}
	/**
	 * @param prezzo the prezzo to set
	 */
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	/**
	 * @return the idFornitore
	 */
	public String getIdFornitore() {
		return idFornitore;
	}
	/**
	 * @param idFornitore the idFornitore to set
	 */
	public void setIdFornitore(String idFornitore) {
		this.idFornitore = idFornitore;
	}
	
	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


public byte[] getImg() {
	return img;
}

public void setImg(byte[] img) {
	this.img = img;
}

public String getIDModello() {
	return IDModello;
}

public void setIDModello(String iDModello) {
	IDModello = iDModello;
}



public int getSconto() {
	return sconto;
}



public void setSconto(int sconto) {
	this.sconto = sconto;
}
}
