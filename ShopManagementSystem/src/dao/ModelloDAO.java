package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import model.Modello;

public class ModelloDAO extends AbstractDAO<Modello>{
	
	CollezioneDAO CDAO = new CollezioneDAO();
	Modello M = new Modello();
	
	public void rimuoviModellobyIDM(String IDModello) {
		String query = "delete from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"= ?";
		try {
			CRUD(query, IDModello);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public boolean esisteModello(String IDModello) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		return exists(query, IDModello);
	}
	/**
	 * Restituisce il modello esistente
	 * @param IDModello
	 * @return
	 */
	public String modelloEsistente(String IDModello) {
		String query = "select \"IDModello\" from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, IDModello);
			result = pst.executeQuery();
			if(result.next()) {
				return result.toString();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public double getPrice(String IDModello) {
		double price = 0;
		String query = "select \"Prezzo\" from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(),query);
			pst.setString(1, IDModello);
			result = pst.executeQuery();
			if(result.next()) {
				price = result.getDouble("Prezzo");
				return price;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;

	}
	
	/**
	 * setta lo sconto di un modello
	 * @param IDModello
	 * @param sconto boolean, true se lo sconto deve esserci, false altrimenti
	 */
	public void setPrice(String IDModello, double prezzo) {
		String query = "update \"Negoziodiabbigliamento\".\"Modello\" set \"Prezzo\"=?";
		parameters = new ArrayList<Object>();
		parameters.add(IDModello);
		parameters.add(prezzo);
		
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public byte[] getPic(String IDModello) {
		byte[] foto = null;
		String query = "select \"Image\" from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, IDModello);
			result = pst.executeQuery();
			if(result.next()) {
				foto = result.getBytes("Image");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foto;

	}
	
	public String getDescrizione(String IDModello) {
		String descrizione = null;
		String query = "select \"Descrizione\" from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, IDModello);
			result = pst.executeQuery();
			if(result.next()) {
				descrizione = result.getString("Descrizione");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return descrizione;

	}
	
	/**
	 * Aggiunge un modello
	 * 
	 * @param IDModello
	 * @param IDCollection
	 * @param prezzo
	 * @param idFornitore
	 * @param descrizione
	 * @param img
	 */
	public void newModel(String IDModello, String IDCollection, double prezzo, String idFornitore, String descrizione, byte[] img) {
		String query = "insert into \"Negoziodiabbigliamento\".\"Modello\"(\"IDModello\",\"IDCollezione\",\"Prezzo\","
				+ "\"IDFornitore\",\"Descrizione\", \"Image\") values(?,?,?,?,?,?)";
		
		parameters = new ArrayList<Object>();
		parameters.add(IDModello);
		parameters.add(IDCollection);
		parameters.add(prezzo);
		parameters.add(idFornitore);
		parameters.add(descrizione);
		parameters.add(img);
		
		try {
			CRUD(query, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Trova l'ultimo Id modello creato e ne restituisce uno nuovo, il succesivo
	 * @return Il nuovo id modello
	 */
	public int newModelloID()  {
		ResultSet result;
		int ID = 0;
		String query = "select max(\"IDModello\") from \"Negoziodiabbigliamento\".\"Modello\" ";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			
			result = pst.executeQuery();
			if(result.next()) {
				ID = result.getInt(1)+1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
		
	}
	
	public int presenteSconto(String idModello) {
		String query = "select \"Sconto\" from \"Negoziodiabbigliamento\".\"Modello\" where \"IDModello\"=?";
		ResultSet result;
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, idModello);
			result = pst.executeQuery();
			if(result.next()) {
				int sconto = result.getInt("Sconto");
				return sconto;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * setta lo sconto di un modello
	 * @param IDModello
	 * @param sconto boolean, true se lo sconto deve esserci, false altrimenti
	 */
	public void setSconto(String IDModello, boolean sconto) {
		String query = "update \"Negoziodiabbigliamento\".\"Modello\" set \"Sconto\"=?";
		int s;	
		if(sconto) {
				s = 1;
			}else {
				s = 0;
			}
			
			try {
				CRUD(query, sconto);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}


	@Override
	protected Modello makeBean(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
