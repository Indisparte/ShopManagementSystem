package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Collezione;

/**
 * Questa classe comunica con la relazione "Collezione" e prevede delle query utili all'applicativo
 * 
 * @author Antonio Di Nuzzo
 *
 */
public class CollezioneDAO extends AbstractDAO<Collezione> {
	

	/**                                           
	 * Aggiunge una nuova collezione
	 * @param nome - Il nome della collezione
	 * @param sesso - Il sesso della collezione
	 */
	public void aggiungiCollezione(Collezione col) {
		String query = "insert into \"Negoziodiabbigliamento\".\"Collezione\"(\"IDCollezione\",\"Nome\",\"Sesso\") values(?,?,?)";
		parameters = new ArrayList<Object>();
		parameters.add(col.getIdCollezione());
		parameters.add(col.getNome());
		parameters.add(col.getNome());
		//Controllo se la collezione è già presente, in caso contrario viene aggiunta.
		if(!(collezioneEsistente (col))) {                                              
				try {
					CRUD(query, parameters);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Metodo che controlla se una collezione esiste già
	 * @param col - la collezione da confrontare
	 * @return Vero se la collezione già esiste</br>
	 * Falso se non esiste
	 */
	private boolean collezioneEsistente(Collezione col) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Collezione\" where \"IDCollezione\"=?";
		return exists(query, col.getIdCollezione());
				
	}

	

	/**
	 * Rimuove una collezione specifica per il sesso
	 * @param sesso
	 * @param nome
	 */
	public void rimuoviCollezioneById(String ID) {
		String query = "delete from \"Negoziodiabbigliamento\".\"Collezione\" where \"IDCollezione\" =?";
		try {
			CRUD(query, ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calcola l'id utilizzando le prime lettere del nome e del sesso
	 * 
	 * @param nome Il nome della collezione
	 * @param sesso Il sesso della collezione
	 * @return L'id della collezione
	 */
	public String calcolaIdCollez(String nome, String sesso) {
		if((nome!=null) || (sesso !=null)) {
		String p1 = nome.substring(0, 1);
		String p2 = sesso.substring(0, 1);
		String id = (p1.concat(p2)).toUpperCase();
		return id;
		}
		else {
			return null;
		}
		
	}

	/**
	 * Trova la collezione che rispetta i parametri forniti
	 * 
	 * @param name Il nome della collezione
	 * @param sex Il sesso della collezione
	 * @return Il risultato della query
	 */
	public ResultSet collezioneEsistente(String name, String sex) {
		ResultSet result = null;
		String query = "select * from \"Negoziodiabbigliamento\".\"Collezione\" where \"Nome\"=? and \"Sesso\"=?";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, name);
			pst.setString(2, sex);
			result = pst.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
		
	}
	/**
	 * Cerca una collezione verificandone l'esistenza
	 * 
	 * @param name Il nome della collezione
	 * @param sex Il sesso della collezione
	 * @return
	 * <b>true</b> La collezione esiste
	 * <b>false</b> La collezione non esiste
	 */
	public boolean esisteCollezione(String name, String sex) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Collezione\" where \"Nome\"=? and \"Sesso\"=?";
		
		parameters = new ArrayList<Object>();
		parameters.add(name);
		parameters.add(sex);
		
		return exists(query, parameters);
		
		
	}
	
	/**
	 * Trova la collezione che rispetta i parametri forniti
	 * 
	 * @param name Il nome della collezione
	 * 
	 * @return Il risultato della query
	 */
	public ResultSet collezioneEsistente(String name) {
		ResultSet result = null;
		String query = "select * from \"Negoziodiabbigliamento\".\"Collezione\" where \"Nome\"=? ";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, name);
			result = pst.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
		
	}
	
	/**
	 * Cerca l'id fornito restituendo l'esito della ricerca
	 * @param Id Identificativo della collezione
	 * @return
	 * <b>true</b> L'Id esiste
	 * <b>false</b> L'Id non esiste
	 */
	public boolean idEsistente(String Id) {
		String query = "select \"IDCollezione\" from \"Negoziodiabbigliamento\".\"Collezione\" where \"IDCollezione\"=?";
		return exists(query, Id);
		
	}
	
	/**
	 * Aggiunge una collezione
	 * 
	 * @param name il nome della collezione
	 * @param sex il sesso della collezione
	 * @param ID l'identificativo della collezione
	 */
	public void addCollection(String name, String sex, String ID) {
		String query = "insert into \"Negoziodiabbigliamento\".\"Collezione\"(\"IDCollezione\",\"Nome\",\"Sesso\") values(?,?,?)";
		parameters = new ArrayList<Object>();
		parameters.add(ID);
		parameters.add(name);
		parameters.add(sex);
		
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		}
	
	/**
	 * Mostra l'id della collezione specificata 
	 * @param name il nome della collezione
	 * @param sex il sesso della collezione
	 * @return Id della collezione, <b>null</b> altrimenti
	 */
	public String showCollectionID(String name, String sex)  {
		ResultSet result;
		String ID = null;
		String query = "select \"IDCollezione\" from \"Negoziodiabbigliamento\".\"Collezione\" where \"Nome\"=? and \"Sesso\"=?";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, name);
			pst.setString(2, sex);
			result = pst.executeQuery();
			if(result.next()) {
				ID = result.getString("IDCollezione");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
		
	}
	
	/**
	 * Permette di selezionare tutte le tuple della relazione e ne restituisce l'interrogazione
	 * @return Risultato della query
	 * 
	 */
	public ResultSet mostraCollezioni() {
		ResultSet rs= null;
		try {
			String query = "select * from \"Negoziodiabbigliamento\".\"Collezione\" ";
			
			PreparedStatement pst =prepareStatement(getConnection(), query);
			rs= pst.executeQuery();
			
		} catch (Exception m) {
			m.printStackTrace();
		
        }
		return rs;
    }
	
	public ArrayList<Collezione> listaCollezioni(){
		String query = "select * from \"Negoziodiabbigliamento\".\"Collezione\" ";
		return getAll(query);
	}

	@Override
	protected Collezione makeBean(ResultSet rs) throws SQLException {

		Collezione collezione = new Collezione(rs.getString("Sesso"), rs.getString("Nome"),rs.getString("IDCollezione"));
				return collezione;
	}
	
	
	}

