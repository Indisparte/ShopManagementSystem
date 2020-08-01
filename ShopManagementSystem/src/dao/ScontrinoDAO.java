package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Scontrino;

public class ScontrinoDAO extends AbstractDAO<Scontrino>{
	
	

	
	public void creaScontrino(int numeroScontrino, double tot, String IDOperatore) {
		 if(scontrinoEsiste(numeroScontrino)) {
			 if(JOptionPane.showConfirmDialog(null,"E' Presente uno scontrino ancora attivo\n eliminarlo?","Warning",JOptionPane.YES_OPTION) 
	     				== JOptionPane.YES_OPTION) {
	     			eliminaScontrino(numeroScontrino);
	     			creaScontrino(numeroscontrino(), tot, IDOperatore);
	     		} 
		 }else {
     	   String query = "INSERT INTO \"Negoziodiabbigliamento\".\"Scontrino\"(\"IDScontrino\", \"IDOperatore\", \"Totale\")VALUES (?, ?, ?)";
     	  parameters.add(numeroScontrino);
     	  parameters.add(IDOperatore);
     	  parameters.add(tot);
     	  try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 } 
	 
	}
	
	private boolean scontrinoEsiste (int numeroScontrino) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Scontrino\" where \"IDScontrino\"=?";
		return exists(query, numeroScontrino);
	}
	
	public int getScontrinoAttivo() {
		String query = "SELECT \"IDScontrino\" FROM \"Negoziodiabbigliamento\".\"Scontrino\" WHERE \"Stato\"='Attivo'";
		PreparedStatement pst;
		int nScont = 0;
		try {
			pst = prepareStatement(getConnection(), query);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				  nScont = rs.getInt("IDScontrino");
			}
			return nScont;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nScont;
	}
	
	/**
	 * Controlla che l'id operatore fornito abbia uno scontrino attivo,
	 * restituendo l'esito della query
	 * @param Idop identificativo operatore
	 * @return
	 * <b>true</b>L'operatore ha uno scontrino attivo</br>
	 * <b>false</b>l'operatore non ha uno scontrino attivo
	 */
	public boolean opHaScontrinoAttivo(String Idop) {
		String query = "SELECT \"IDScontrino\" FROM \"Negoziodiabbigliamento\".\"Scontrino\" WHERE \"Stato\"='Attivo' and \"IDOperatore\"=?";
		return exists(query, Idop);
	}
	
	public String getScontrinoAttivoData() {
		String query = "SELECT \"DataEmissione\" FROM \"Negoziodiabbigliamento\".\"Scontrino\" WHERE \"Stato\"='Attivo'";
		PreparedStatement pst;
		String data = null;
		try {
			pst = prepareStatement(getConnection(), query);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				  data = rs.getString("DataEmissione");
			}
			
			return data;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	public void emettiScontrino(int IDScontrino, double tot ) {
		String query = "UPDATE \"Negoziodiabbigliamento\".\"Scontrino\"\r\n" + 
				"	SET \"Stato\"='Emesso', \"Totale\"=?\r\n" + 
				"	WHERE \"IDScontrino\"=?;";
		parameters = new ArrayList<Object>();
		parameters.add(tot);
		parameters.add(IDScontrino);
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminaScontrino(int IDScontrino ) {
		String query = "DELETE FROM \"Negoziodiabbigliamento\".\"Scontrino\"\r\n" + 
				"	WHERE \"IDScontrino\"=?";
		try {
			CRUD(query, IDScontrino);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Calcola il numero dello scontrino da emettere
	 * @return Il numero dello scontrino
	 */
	public int numeroscontrino(){
		int max = 0;
		try {
			String query = "select MAX (\"IDScontrino\") as \"minimo\" from \"Negoziodiabbigliamento\".\"Scontrino\" where \"Stato\"='Emesso'";
			PreparedStatement pst = prepareStatement(getConnection(), query);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
		     max=rs.getInt("minimo");
		     return (max+1);
		    
		}

		} catch (Exception m) {
			m.printStackTrace();
        }
		
		
		return max;
	}
	/**
	 * Rimuove un capo dai dettagli
	 * @param ean Il capo
	 * @param numeroScontrino il numero dello scontrino
	 */
	
	public void rimuoviDettagli(String ean, int numeroScontrino) {
		String query = "delete from  \"Negoziodiabbigliamento\".\"Dettagli\" where \"EAN\"=? and \"IDScontrino\"=?";
		
		parameters = new ArrayList<Object>();
		parameters.add(ean);
		parameters.add(numeroScontrino);
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Restituisce true/false se il capo è presente nei dettagli di uno scontrino
	 * @param ean L'ean del capo
	 * @param numsc Il numero dello scontrino
	 * @return
	 * <b>true</b> Il capo è presente
	 	<b>false</b> Il capo non è presente
	 */
	public boolean capoGiaPresenteInDettagli(String ean, int numsc) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Dettagli\" where \"EAN\"=? and \"IDScontrino\"=?";
		parameters = new ArrayList<Object>();
		parameters.add(ean);
		parameters.add(numsc);
		return exists(query, parameters);
	}
	
	/**
	 * Inserisce un nuovo capo in dettagli 
	 * @param ean Il codice ean del capo
	 * @param numeroScontrino Il numero dello scontrino
	 */
	public void inserisciDettagli(String ean, int numeroScontrino) {
		parameters = new ArrayList<Object>();
		parameters.add(ean);
		parameters.add(numeroScontrino);
		if(!capoGiaPresenteInDettagli(ean, numeroScontrino)) {
			String query = "INSERT INTO \"Negoziodiabbigliamento\".\"Dettagli\"(\"EAN\",\"IDScontrino\", \"Quantità\") VALUES (?,?,1)";
			try {
				CRUD(query, parameters);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			String query = "update \"Negoziodiabbigliamento\".\"Dettagli\" set \"Quantità\"= \"Quantità\"+1 where \"EAN\"=? and \"IDScontrino\"=?";
			try {
				CRUD(query, parameters);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	protected Scontrino makeBean(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
