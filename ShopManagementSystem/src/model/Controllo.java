package model;

import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JLabel;

/**
 * 
 * @author Antonio Di Nuzzo
 * @author Eleonora Di Santi
 *
 */
public class Controllo {

	public Controllo() {
		
	}
	/**
	 * Controlla se dei campi importanti sono vuoti o non utilizzati,
	 * verificando se il contenuto della stringa è vuoto
	 * o uguale ad un testo che sta ad indicare una non scelta dell'utente
	 * 
	 * @param campiImportanti  Array list di tutte le variabili importanti
	 * @return
	 *<b>true</b>  Ci sono campi vuoti</br>
	 *<b>false</b>  Non ci sono campi vuoti
	 */
	public boolean campiImportantiVuoti(ArrayList<String> campiImportanti ) {
	
		for(String campo : campiImportanti) {
			if(campo.isEmpty() || campo.equals("---Choose Collections---") || campo.equals("Fornitori") || campo.equals("Sizes") ) {
				return true;
			}
		}
	
		return false;

	}
	
	/**
	 * Controlla se i label che forniscono un messaggio d'errore
	 *  sono spenti(vuoti)
	 *  
	 * @param errori ArrayList di Label
	 * @return
	 * <b>true</b>   Errori presenti</br>
	 * <b>false</b>  Errori non presenti
	 */
	public boolean ciSonoErrori(ArrayList<JLabel> errori) {

		for(JLabel errore : errori) {
			if(!errore.getText().equals("")) {
				return true;
			}
		}

		return false;
		
	}
	
	
	/**
	 * Verifica la correttezza dell'email con restrizioni lato username.
	 * <ul><b>Caratteri ammessi</b></br>
	 * 	<li> A-Z o a-z
	 *  <li> 0-9
	 *  <li> dot (.), dash(-), underscore(_)
	 * </ul>
	 * @param email L'email da controllare
	 * @return
	 * <b>true</b> Email corretta
	 *<b>false</b> Email non corretta
	 *
	 */
	public boolean correctEmail(String email) {
			
			if(!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)){
				return false;
			}
			return true;
		}
	/**
	 * Contolla se il formato del numero è corretto.
	 * Controlla che la lunghezza sia quella fornita dall'utente
	 * e le cifre siano numeri.
	 * 
	 * @param c  Parametro passato tramite evt.getKeyChar(),</br>
	 * è il carattere da controllare.
	 * @param number Il numero completo per verificarne la lunghezza
	 * @param allowedDigits  Cifre ammesse per il numero
	 * @return
	 * <b>true</b> Numero che rispetta la lunghezza.
	 * <b>false</b>Numero/non numero e/o non rispetta la lunghezza.
	 */
	public boolean isTheCorrectNumberFormat(char c, String number,  int allowedDigits) {
		if(!Character.isDigit(c) || number.length()> allowedDigits-1) {
			return false;
		}
		
		return true;
		
	}
	
}
