package model;



public class Impiegato extends Scontrino {
	private String nome;
	private String cognome;
	private String dataNascita;
	private String dataAssunzione;
	private double stipendio;
	private String cf;
	private String idOperatore;
	private String email;
	private String cell;
	private String sesso;
	private String psw;
	private String privilegio;
	private byte[] img ;
	
	public Impiegato() {
		
	}
	

	public Impiegato(Impiegato i) {
		this.nome = i.getNome();
		this.cognome = i.getCognome();
		this.dataNascita = i.getDataNascita();
		this.dataAssunzione = i.getDataAssunzione();
		this.stipendio = i.getStipendio();
		this.cf = i.getCf();
		this.idOperatore = i.getIdOperatore();
		this.email = i.getEmail();
		this.cell = i.getCell();
		this.sesso = i.getSesso();
		this.psw = i.getPsw();
		this.img = i.getImg();
		this.privilegio = i.getPrivilegio();
	}
	/**
	 * @param nome
	 * @param cognome
	 * @param dataNascita
	 * @param dataAssunzione
	 * @param stipendio
	 * @param cf
	 * @param idOperatore
	 * @param email
	 * @param cell
	 * @param sesso
	 * @param psw
	 */
	public Impiegato(String nome, String cognome, String dataNascita, String dataAssunzione, double stipendio,
			String cf, String idOperatore, String email, String cell, String sesso, String psw, byte[] img, String privilegio) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.stipendio = stipendio;
		this.cf = cf;
		this.idOperatore = idOperatore;
		this.email = email;
		this.cell = cell;
		this.sesso = sesso;
		this.psw = psw;
		this.img= img;
		this.privilegio = privilegio;
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
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the dataNascita
	 */
	public String getDataNascita() {
		return dataNascita;
	}
	/**
	 * @param dataNascita the dataNascita to set
	 */
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	/**
	 * @return the dataAssunzione
	 */
	public String getDataAssunzione() {
		return dataAssunzione;
	}
	/**
	 * @param dataAssunzione the dataAssunzione to set
	 */
	public void setDataAssunzione(String dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}
	/**
	 * @return the stipendio
	 */
	public double getStipendio() {
		return stipendio;
	}
	/**
	 * @param stipendio the stipendio to set
	 */
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
	/**
	 * @return the cf
	 */
	public String getCf() {
		return cf;
	}
	/**
	 * @param cf the cf to set
	 */
	public void setCf(String cf) {
		this.cf = cf;
	}
	/**
	 * @return the idOperatore
	 */
	public String getIdOperatore() {
		return idOperatore;
	}
	/**
	 * @param idOperatore the idOperatore to set
	 */
	public void setIdOperatore(String idOperatore) {
		this.idOperatore = idOperatore;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the cell
	 */
	public String getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(String cell) {
		this.cell = cell;
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
	 * @return the psw
	 */
	public String getPsw() {
		return psw;
	}
	/**
	 * @param psw the psw to set
	 */
	public void setPsw(String psw) {
		this.psw = psw;
	}
	
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}
	
	public void creaScontrino(double totale, String idScontrino, String dataEmissione) {
		Scontrino sc = new Scontrino(totale, idScontrino, idOperatore, dataEmissione);
	}
}
	
	

