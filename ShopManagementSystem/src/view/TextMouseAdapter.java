package view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

/**
 * Generalizza l'evento dell'ingresso del mouse in un fields.
 * Produce un cambiamento del colore del background e del foreground dei fields in cui si è entrati
 * per permettere una migliore evidenza dei campi da compilare.
 * 
 * @author Antonio Di Nuzzo 
 *
 */
public class TextMouseAdapter extends MouseAdapter {
	
	JTextField text;
	public TextMouseAdapter(JTextField text) {
		this.text = text;
	}
	
	public void mouseEntered(MouseEvent e) {
		text.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(144, 148, 155)));
		text.setBackground(new Color(144, 148, 155));
		text.setForeground(Color.BLACK);
	}
	public void mouseExited(MouseEvent e) {
		text.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		text.setBackground(new Color(44, 46, 60));
		text.setForeground(new Color(241, 242, 237));

	}
	

}
