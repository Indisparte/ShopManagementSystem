package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Impiegato;


/**
 * Fuzioni per la ricerca e la manipolazione dell'impiegato.
 * 
 */
public class ImpiegatoDAO extends AbstractDAO<Impiegato> {

	
	
	public Impiegato  MostraImpiegatobyId(String idOperatore) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Impiegato\" where \"IDOperatore\"=?";
		return findOne(query, idOperatore);
		
	}
	/**
	 * Metodo generico per la ricerca di un impiegato
	 * @param paramRicerca - Indica quale è il tipo di parametro di cui specifichiamo il valore<br/>
	 * Utilizzabili solo Nome, Cognome, Cf;
	 * @param valoreDiRicerca - Il valore del rispettivo parametro specificato
	 * @return
	 * 
	 */
	public ResultSet  MostraImpiegato(String paramRicerca, String valoreDiRicerca) {
		String query = "select \"Nome\", \"Cognome\", \"CF\" from \"Negoziodiabbigliamento\".\"Impiegato\" where \""+paramRicerca+"\"=?";
		ResultSet result = null;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, valoreDiRicerca);
			return result = pst.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public Impiegato  MostraImpiegatobyCf(String cf) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Impiegato\" where \"CF\"=?";
		
		return findOne(query, cf);
		
	}
	
	public boolean  EsisteImpiegato(String cf) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Impiegato\" where \"CF\"=?";
		return exists(query, cf);
		
	}
	
	public void AggiungiImpiegato(String nome, String cognome, String dataNascita,
								String cf, String idOperatore, String email, String cell, String sesso,
								String psw, byte[] photo, String privilegio){
		String query = "INSERT INTO \"Negoziodiabbigliamento\".\"Impiegato\"(\"Nome\", \"Cognome\", \"DataNascita\","
					+ "\"CF\", \"IDOperatore\", \"Email\", \"Cell\", \"Sesso\", \"Psw\", \"Image\", \"Privilegio\") "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?, ?)";
		parameters = new ArrayList<Object>();
		parameters.add(nome);
		parameters.add(cognome);
		parameters.add(dataNascita);
		parameters.add(cf);
		parameters.add(idOperatore);
		parameters.add(email);
		parameters.add(cell);
		parameters.add(sesso);
		parameters.add(psw);
		parameters.add(photo);
		parameters.add(privilegio);
		
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	public void RimuoviImpiegatoByCf(String cf) {
		String query = "delete from \"Negoziodiabbigliamento\".\"Impiegato\" where \"CF\"= ?";
		
		try {
			CRUD(query, cf);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void ModificaDatiPersonali( String idOperatore, String email, String cell, String psw, byte[] photo, String cf) {
		
		String query = "UPDATE \"Negoziodiabbigliamento\".\"Impiegato\" "
				+ "SET \"IDOperatore\"=?,\"Email\"=?,\"Cell\"=?, \"Psw\"=?, \"Image\"=? WHERE \"CF\"=?";
		parameters = new ArrayList<Object>();
		parameters.add(idOperatore);
		parameters.add(email);
		parameters.add(cell);
		parameters.add(psw);
		parameters.add(photo);
		parameters.add(cf);
		
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  Modifica i dati lavorativi dell'impiegato conoscendone il CF
	 * @param dataAssunzione
	 * @param stipendio
	 */
	public void ModificaDatiLavorativiByCf(String dataAssunzione, double stipendio, String cf, String privilegio) {

		String query = "UPDATE \"Negoziodiabbigliamento\".\"Impiegato\""
				+ " SET \"DataAssunzione\"=?, \"Stipendio\"=?, \"Privilegio\"=? WHERE \"CF\"=?";
		parameters = new ArrayList<Object>();
		parameters.add(dataAssunzione);
		parameters.add(stipendio);
		parameters.add(privilegio);
		parameters.add(cf);
		
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean usernameAlredyExists(String username) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Impiegato\" where \"IDOperatore\"=?";
		return exists(query, username);
		
	}
	
	public boolean emailAlredyExists(String email) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Impiegato\" where \"Email\"=?";
		return exists(query, email);
	}
	
	public boolean verificaCredenziali(String username, String password) {
		String query = "SELECT *" + 
				"	FROM \"Negoziodiabbigliamento\".\"Impiegato\"\r\n" + 
				"	where \"IDOperatore\"= ? and \"Psw\"=?";
		parameters = new ArrayList<Object>();
		parameters.add(username);
		parameters.add(password);
		return exists(query, parameters);
	}
	/**
	 * Restituisce il totale fatturato  dall'impiegato 
	 * @param I L'impiegato
	 * 
	 * @author Eleonora Di Santi
	 */
	
	public double totalefatturatoperimpiegato(String idOperatore) {
		
		try {
			String query = "SELECT SUM (\"Totale\") as SOMMA\r\n" + 
					"	FROM \"Negoziodiabbigliamento\".\"Scontrino\"\r\n" + 
					"	where \"IDOperatore\"='"+idOperatore+"';";
			PreparedStatement pst = prepareStatement(getConnection(), query);
			ResultSet rs= pst.executeQuery();
			
			
		    while(rs.next()) {
		    	double res=rs.getDouble("SOMMA");
		    	return res;
		    }
			pst.close();
		
			
		} catch (Exception m) {
			m.printStackTrace();
		
	    } 
	return 0;
	
	}
	
	/**
	 * Mi restituisci il numero di scontrini fatturati dall'impiegato 
	 * @param I L'impiegato
	 * @author Eleonora Di Santi
	 */
	
	public  int numOrdiniXImpiegato(String idOperatore) {
		try {
			String query = "SELECT count (\"IDScontrino\") as num\r\n" + 
					"	FROM \"Negoziodiabbigliamento\".\"Scontrino\"\r\n" + 
					"	where \"IDOperatore\"='"+idOperatore+"';";
			PreparedStatement pst = prepareStatement(getConnection(), query);
			ResultSet rs= pst.executeQuery();
		    while(rs.next()) {
		    	int res=rs.getInt("num");
		    	return res;
		    }
			pst.close();
		
			
		} catch (Exception m) {
			m.printStackTrace();
		
	    } 
	return 0;
	
     }
	@Override
	protected Impiegato makeBean(ResultSet rs) throws SQLException {
		Impiegato impiegato = new Impiegato(rs.getString("Nome"), rs.getString("Cognome"),rs.getString("DataNascita") , 
				rs.getString("DataAssunzione"), rs.getDouble("Stipendio"),rs.getString("CF"),
				rs.getString("IDOperatore"),rs.getString("Email"),rs.getString("Cell"),rs.getString("Sesso"),rs.getString("Psw"),
				rs.getBytes("Image"),rs.getString("Privilegio"));
		return impiegato;
	}
	
}

