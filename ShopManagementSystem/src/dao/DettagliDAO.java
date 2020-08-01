package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import model.Dettagli;


/**
 * Comunica con la relazione Dettagli
 * 
 * @author Antonio Di Nuzzo
 * @author Eleonora Di Santi
 *
 */
public class DettagliDAO extends AbstractDAO<Dettagli>{

		/**
		 * Aggiunge i dettagli alla relazione Dettagli
		 * @param ean Il codice del modello avente un tipo di caratteristiche ben definito
		 * @param idScontrino L'identificativo dello scontrino
		 */
		public void aggiungiDettagli(String ean, String idScontrino) {
			String query = "insert into \"Negoziodiabbigliamento\".\"Dettagli\" (\"EAN\",\"IDScontrino\")values (?, ?)";
			parameters = new ArrayList<Object>();
			parameters.add(ean);
			parameters.add(idScontrino);
			try {
				CRUD(query, parameters);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
		/**
		 * Mostra tutti i dettagli id uno specifico scontrino
		 * @param idScontrino L'identificativo dello scontrino
		 * @return ArrayList di Stringhe contenente tutti gli identificativi del modello
		 * con specifica caratteristica (EAN)
		 * 
		 */
		public ArrayList<String> mostraEanDettagliByIdScontrino(String idScontrino) {
			ArrayList<String> aE = new ArrayList<String>();
			String query = "select * from \"Negoziodiabbigliamento\".\"Dettagli\" where \"IDScontrino\"='"+idScontrino+"';";
			
				for (Dettagli dettagli : getAll(query)) {
					aE.add(dettagli.getEan());
				}
				return aE;
				
		
		}

		@Override
		protected Dettagli makeBean(ResultSet rs) throws SQLException {
			Dettagli dettagli = new Dettagli(rs.getString("IDScontrino"), rs.getString("EAN"));
			return dettagli;
		}
		
}
