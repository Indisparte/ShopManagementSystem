package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.TagliaColore;
/**
 * Mette in comunicazione l'applicativo con la relazione taglia e colore
 * 
 * @author Eleonora Di Santi
 *
 */
public class TagliaColoreDAO extends AbstractDAO<TagliaColore> {
	
	public void aggiungiTC(String IDModello, String ean, String colore, String taglia, int giacenza) {
		String query = "insert into \"Negoziodiabbigliamento\".\"TagliaColore\"(\"IDModello\",\"EAN\", \"Colore\","
				+ " \"Giacenza\", \"Taglia\") values(?,?,?,?,?)";
		parameters = new ArrayList<Object>();
		parameters.add(IDModello);
		parameters.add(ean);
		parameters.add(colore);
		parameters.add(giacenza);
		parameters.add(taglia);
		try {
			CRUD(query, parameters);
			 JOptionPane.showMessageDialog(null, "Articolo Aggiunto");

		} catch (SQLException e) {

			 JOptionPane.showMessageDialog(null, "Hai bloccato il panel model prima di aver inserito il model, procedi in questo modo:"
			 		+ "\n - Sblocca il panel model ed inserisci i dati.\n - Inserisci i dati nel panel variety.\n"
			 		+ " - Aggiungi il capo e ora se devi inserire più variety, puoi bloccare il panel!", null, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rimuoviTC(String ean) {
		String query = "delete from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"EAN\"=? ";
		
		try {
			CRUD(query, ean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean specificheEsistenti(String ean) {
		String query = "select * from\"Negoziodiabbigliamento\".\"TagliaColore\"where \"EAN\"=?";
		
		return exists(query, ean);
	}

	public boolean specificheEsistenti(String ean, String colore, String taglia) {
		String query = "select * from\"Negoziodiabbigliamento\".\"TagliaColore\"where \"EAN\"=? and \"Colore\"=? and \"Taglia\"=?";
		parameters = new ArrayList<Object>();
		parameters.add(ean);
		parameters.add(colore);
		parameters.add(taglia);
		return exists(query, parameters);

	}
	/**
	 * Restituisce quante taglie ha un determinato capo di un determinato colore.
	 * @param ean - Il codice del capo
	 * @param colore - Il colore del capo
	 * @return
	 */
	public ArrayList<String> getColoriByModellotaglie(String IDModello, String taglia) {
		ArrayList<String> colori = new ArrayList<String>();
		ResultSet result;
		String query = "select \"Colore\" from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"IDModello\"=? and \"Taglia\"=?";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, IDModello);
			pst.setString(2, taglia);
			result = pst.executeQuery();
			while(result.next()) {
				colori.add(result.getString("Colore"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colori;
	}
	
	/**
	 * Restituisce tutte le taglie distinte di un modello.
	 * 
	 * @param IDModello L'ID del modello di cui si vogliono conoscere le taglie disponibili
	 * @return Array List contenente le taglie disponibili del modello
	 */
	public ArrayList<String> getTaglieByIDModello(String IDModello) {
		ArrayList<String> taglie = new ArrayList<String>();
		ResultSet result;
		String query = "select distinct \"Taglia\" from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"IDModello\"=? ";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, IDModello);
			result = pst.executeQuery();
			while(result.next()) {
				taglie.add(result.getString("Taglia"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taglie;
	}


	/**
	 * Restituisce la specifica quantità di uno specifico indumento che ha taglia e colore specificati.
	 * @param ean
	 * @param taglia
	 * @param colore
	 * @return
	 */
	public int getGiacenzaByEan(String ean) {
		int giacenza = 0;
		ResultSet result;
		String query = "select \"Giacenza\" from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"EAN\"=? ";
		try {
			PreparedStatement pst =prepareStatement(getConnection(), query);
		pst.setString(1, ean);
		result =pst.executeQuery();
		if(result.next()) {
			giacenza = result.getInt("Giacenza");
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return giacenza;
	}
	
	public int getGiacenzaByEan(String IDmodello, String taglia) {
		int giacenza = 0;
		ResultSet result;
		String query = "select \"Giacenza\" from \"Negoziodiabbigliamento\".\"Magazzino\" where \"IDModello\"=? and \"Taglia\"=? ";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
		pst.setString(1, IDmodello);
		pst.setString(2, taglia);
		
		result = pst.executeQuery();
		if(result.next()) {
			giacenza = result.getInt("Giacenza");
			return giacenza;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(giacenza);
		return giacenza;
	}
	
	public boolean coloreEsistePerEan(String ean, String colore) {
		ResultSet result;
		String query = "select  \"Colore\" from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"EAN\"=? ";
		try {
			PreparedStatement pst = prepareStatement(getConnection(), query);
			pst.setString(1, ean);
			result = pst.executeQuery();
			if(result.next()) {
				if(result.getString("Colore").equalsIgnoreCase(colore)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 * Restituisce tutti i colori di un determinato capo.
	 * @param ean
	 * @return
	 */
	public ArrayList<String> getColoreByModello(String IdModello) {
		ArrayList<String> colori = new ArrayList<String>();
		ResultSet result;
		String query = "select distinct \"Colore\" from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"IDModello\"=? and\"Giacenza\">0";
		try {
			PreparedStatement pst =prepareStatement(getConnection(), query);
			pst.setString(1, IdModello);
			result = pst.executeQuery();
			while(result.next()) {
				colori.add(result.getString("Colore"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colori;
	}
	
	
	public void riduciGiacenza(String ean, int qta) {
		String query = "update \"Negoziodiabbigliamento\".\"TagliaColore\" set \"Giacenza\"=\"Giacenza\"-"+qta+" where \"EAN\"=?";
		try {
			CRUD(query, ean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	public void aumentaGiacenza(String ean, int qta) {
		String query = "update \"Negoziodiabbigliamento\".\"TagliaColore\" set \"Giacenza\"=\"Giacenza\"+"+qta+" where \"EAN\"=? ";
		try {
			CRUD(query, ean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	public boolean esistonoVarietàDiQuestoModello(String idModello) {
		String query = "select  * from \"Negoziodiabbigliamento\".\"TagliaColore\" where \"IDModello\"=? ";
		return exists(query, idModello);
	}
	
	/**
	 * Controlla se la giacenza totale di un modello
	 * supera la soglia prefissata per lo sconto.
	 * 
	 * @param IDModello
	 * @return
	 * true - Giacenza Totale supera la soglia
	 * false - Giacenza Totale non supera la soglia
	 */
	public boolean giacenzaTotSuperaSoglia(String IDModello) {
		String query = " SELECT SUM(\"TagliaColore\".\"Giacenza\") FROM  \"Negoziodiabbigliamento\".\"TagliaColore\"\r\n" + 
				"   WHERE \"IDModello\"=? ;";
		return exists(query, IDModello);
	}

	@Override
	protected TagliaColore makeBean(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

