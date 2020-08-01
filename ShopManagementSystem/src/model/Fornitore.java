package model;

public class Fornitore extends Modello {
private String piva;
private String nome;
private String indirizzo;
private String cap;
private String telefono;
private String sitoWeb;
public Fornitore() {
	
}
/**
 * Costruttore della classe
 * @param piva
 * @param nome
 * @param indirizzo
 * @param cap
 * @param telefono
 * @param sitoWeb
 */
public Fornitore(String piva, String nome, String indirizzo, String cap, String telefono, String sitoWeb) {
	
	this.piva = piva;
	this.nome = nome;
	this.indirizzo = indirizzo;
	this.cap = cap;
	this.telefono = telefono;
	this.sitoWeb = sitoWeb;
}
/**
 * @return the piva
 */
public String getPiva() {
	return piva;
}
/**
 * @param piva the piva to set
 */
public void setPiva(String piva) {
	this.piva = piva;
}
/**
 * @return the nome
 */
public String getNome() {
	return nome;
}
/**
 * @param nome the nome to set
 */
public void setNome(String nome) {
	this.nome = nome;
}
/**
 * @return the indirizzo
 */
public String getIndirizzo() {
	return indirizzo;
}
/**
 * @param indirizzo the indirizzo to set
 */
public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}
/**
 * @return the cap
 */
public String getCap() {
	return cap;
}
/**
 * @param cap the cap to set
 */
public void setCap(String cap) {
	this.cap = cap;
}
/**
 * @return the telefono
 */
public String getTelefono() {
	return telefono;
}
/**
 * @param telefono the telefono to set
 */
public void setTelefono(String telefono) {
	this.telefono = telefono;
}
/**
 * @return the sitoWeb
 */
public String getSitoWeb() {
	return sitoWeb;
}
/**
 * @param sitoWeb the sitoWeb to set
 */
public void setSitoWeb(String sitoWeb) {
	this.sitoWeb = sitoWeb;
}
@Override
public String toString() {
	return "Fornitore [piva=" + piva + ", nome=" + nome + ", indirizzo=" + indirizzo + ", cap=" + cap + ", telefono="
			+ telefono + ", sitoWeb=" + sitoWeb + "]";
}
}
