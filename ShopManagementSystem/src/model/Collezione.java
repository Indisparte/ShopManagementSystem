package model;

public class Collezione {
private  String idCollezione;
private String sesso;
private String nome;

public Collezione() {
	
}
/**
 * Costruttore della classe
 * @param sesso
 * @param nome
 */
public Collezione(String sesso, String nome, String idCollezione) {
	this.nome = nome;
	this.sesso = sesso;
	this.idCollezione = idCollezione;
}
/**
 * @return the idCollezione
 */
public  String getIdCollezione() {
	return idCollezione;
}
public void setIdCollezione(String idCollezione) {
	this.idCollezione = idCollezione;
}

/**
 * @return the sesso
 */
public String getSesso() {
	return sesso;
}
/**
 * @param sesso the sesso to set
 */
public void setSesso(String sesso) {
	this.sesso = sesso;
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
@Override
public String toString() {
	return "Collezione [idCollezione=" + idCollezione + ", nome=" + nome + ", sesso=" + sesso + "]\n";
}

@Override
public boolean equals(Object obj) {
	Collezione other = (Collezione) obj;
	return(this.nome.equalsIgnoreCase(other.nome)
		&& (this.idCollezione.equalsIgnoreCase(other.idCollezione))
		&& (this.sesso.equalsIgnoreCase(other.sesso)));
}


}
