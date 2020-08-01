package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Questa classe permette la gestione delle righe di una tabella , dove la giacenza non supera la soglia dei 5 capi.
 * La casella della giacenza di tale capo, verrà evidenziata di rosso.
 * @author Antonio Di Nuzzo
 *
 */
public class TableColorCellRenderer implements TableCellRenderer {

	private  TableCellRenderer RENDERER = new DefaultTableCellRenderer();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(column == 3) {
			Object result = table.getModel().getValueAt(row, column);
			int average = Integer.parseInt(result.toString());
			Color color = null;
			if(average < 5) {
				color = Color.RED;
			}
			c.setBackground(color);
		}else {
			c.setBackground(null);

		}
		
		return c;
	}

}
