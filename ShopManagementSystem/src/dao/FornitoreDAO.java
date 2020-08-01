package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Fornitore;

/**
 * Permette query sulla relazione Fornitore
 * @author Antonio Di Nuzzo
 * @author Eleonora Di Santi
 *
 */
public class FornitoreDAO extends AbstractDAO<Fornitore> {
	
	/**
	 * Aggiunge un fornitore
	 * 
	 */
	public void AggiungiFornitore(String piva, String nome, String indirizzo, String cap, String telefono, String sitoWeb){
			String query = "INSERT INTO \"Negoziodiabbigliamento\".\"Fornitore\" "
					+ "(\"PIva\", \"Nome\", \"Indirizzo\", \"CAP\", \"Telefono\", \"SitoWeb\") VALUES (?,?,?,?,?,?)";
			parameters = new ArrayList<Object>();
			parameters.add(piva);
			parameters.add(nome);
			parameters.add(indirizzo);
			parameters.add(cap);
			parameters.add(telefono);
			parameters.add(sitoWeb);
			try {
				CRUD(query, parameters);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
	
	/**
	 * Rimuove un fornitore
	 * @param piva identificativo del fornitore
	 */
	public void RimuoviFornitore(String piva) {
		String query = "delete from \"Negoziodiabbigliamento\".\"Fornitore\" where \"PIva\" = ?";

		try {
			CRUD(query, piva);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifica un fornitore
	 * @param piva
	 * @param nome
	 * @param indirizzo
	 * @param cap
	 * @param telefono
	 * @param sitoWeb
	 */
	public void ModificaFornitore (String piva, String nome, String indirizzo, String cap, String telefono, String sitoWeb) {
				String query = "UPDATE \"Negoziodiabbigliamento\".\"Fornitore\" "
						+ "SET \"Nome\"=? , \"Indirizzo\"=?, \"CAP\"=?, \"Telefono\"=?, \"SitoWeb\"=? WHERE \"PIva\"=?";

				parameters = new ArrayList<Object>();
				parameters.add(nome);
				parameters.add(indirizzo);
				parameters.add(cap);
				parameters.add(telefono);
				parameters.add(sitoWeb);
				parameters.add(piva);
				try {
					CRUD(query, parameters);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
	}
	
	/**
	 *Restituisce il nome di un fornitore specificato dalla pIva
	 * @param piva
	 * @return Nome del fornitore
	 */
	public String getNomeFornitorebyPiva(String piva) {
		ResultSet result;
		String nome = null;
		String query = "select \"Nome\" from \"Negoziodiabbigliamento\".\"Fornitore\" where \"PIva\"=?";
		try {
			PreparedStatement pst =prepareStatement(getConnection(), query);
			pst.setString(1,piva);
			result = pst.executeQuery();
			
			if(result.next()) {
			nome = result.getString("Nome");
			}
			
		} catch (SQLException e) {e.printStackTrace(); return null;}
		return nome;
	}
	
	/**
	 * Trova la pIva grazie al nome del fornitore
	 * @param nome il nome del fornitore
	 * @return
	 * La pIva del fornitore
	 */
	public String getPIvaByNome(String nome) {
		ResultSet result;
		String piva = null;
		String query = "select \"PIva\" from \"Negoziodiabbigliamento\".\"Fornitore\" where \"Nome\"=?";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1,nome);
			result = pst.executeQuery();
			
			if(result.next()) {
			piva = result.getString("PIva");
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		return piva;
	}
	
	/**
	 * Trova la pIva grazie al modello venduto dal fornitore
	 * @param nome il nome del fornitore
	 * @return
	 * La pIva del fornitore
	 */
	public String getPIvaByModello(String idModello) {
		ResultSet result;
		String piva = null;
		String query = "select \"IDFornitore\" from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1,idModello);
			result = pst.executeQuery();
			
			if(result.next()) {
			piva = result.getString("IDFornitore");
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		return piva;
	}
	
	/**
	 * Controlla se un fornitore esiste
	 * @param piva identificativo del fornitore
	 * @return
	 * <b>true</b>Il fornitore esiste</br>
	 * <b>false</b> Il fornitore non esiste
	 */
	public boolean esisteFornitore (String piva) {
		String query = "select \"PIva\" from \"Negoziodiabbigliamento\".\"Fornitore\" 	where \"PIva\"=?" ;
		return exists(query, piva);
	}
	
	public ArrayList<Fornitore> listaFornitori(){
		String query = "select * from \"Negoziodiabbigliamento\".\"Fornitore\"";
		
			return getAll(query);
		
	}
	
	public Fornitore getFornitore(String piva) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Fornitore\" 	where \"PIva\"=?" ;

		return findOne(query, piva);
	}

	@Override
	protected Fornitore makeBean(ResultSet rs) throws SQLException {
		Fornitore fornitore = new Fornitore(rs.getString("Piva"),rs.getString("Nome"),rs.getString("Indirizzo"),
				rs.getString("Cap"),rs.getString("Telefono"),rs.getString("SitoWeb"));
		return fornitore;
	}
	
}
