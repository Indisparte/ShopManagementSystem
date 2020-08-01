/**
 * 
 */
package view;

import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import dao.Database;
import net.proteanit.sql.DbUtils;

/**
 * @author tonyd
 *
 */
public abstract class AbstractPanel extends JPanel{
	
	protected void refreshTable(String query, JTable table) {
		
		try {
			
			PreparedStatement pst = Database.getInstance().getConnection().prepareStatement(query);
			ResultSet rs= pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			 
			
		} catch (Exception m) {
			m.printStackTrace();
		
        }	
	
	}
	
	protected void refreshTable(String query, JTable table, Object parameter) {
		
	try {
			
			PreparedStatement pst = Database.getInstance().getConnection().prepareStatement(query);
			pst.setObject(1, parameter);
			ResultSet rs= pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			
		} catch (Exception m) {
			m.printStackTrace();
		
	    }
	}
	
	/**
	 * Adatta l'immagine al label specifico
	 * @param img L'immagine
	 */
	protected void adattaFoto(byte[] img, JLabel label) {
		try {
			ImageIcon image = new ImageIcon(img );
			Image im = image.getImage();
			Image myImg = im.getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon newImage = new ImageIcon(myImg);
			label.setIcon(newImage);
		}catch(NullPointerException e) {
			label.setText("Foto Non Presente");
		}
	} 
	
	
}
