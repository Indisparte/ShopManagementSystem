package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Controllo;
import model.Fornitore;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;

import dao.FornitoreDAO;

import javax.swing.JSeparator;

import javax.swing.ImageIcon;
/**
 * Questa classe permette la presa visione di tutti i forntori e la modifica di alcuni dati 
 * 
 * @author Eleonora Di Santi
 *
 */
public class PanelFornitore extends AbstractPanel {
	private JComboBox<String> comboBoxSearchBar;
	private JTable tableFornitori;
	private FornitoreDAO FDAO = new FornitoreDAO();
	private JTextField txtNome = new JTextField();
	private JTextField txtSitoWeb = new JTextField();
	private JTextField txtIndirizzo = new JTextField();
	private JTextField txtCap = new JTextField();
	private JTextField txtTelefono = new JTextField();
	private JTextField txtpiva = new JTextField();
	private String cell, nome, piva, cap;
	Controllo control = new Controllo(); 
	String EID_1;
	JLabel lblrefresh_1 = new JLabel("");
	ArrayList<String> campiImportanti = new ArrayList<String>();
	private JTable table;



	
	 
	/**
	 * Create the panel.
	 */
	public PanelFornitore() {
		setBackground(new Color(144,148,155));
		
		JTextField txtSearchBar = new JTextField();
		txtSearchBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String select =(String)comboBoxSearchBar.getSelectedItem();
				String query = "select * from \"Negoziodiabbigliamento\".\"Fornitore\" where \""+select+"\"=?";
				refreshTable(query, tableFornitori, txtSearchBar.getText());
				
			}
		});
		txtSearchBar.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtSearchBar.setColumns(10);
		
	    comboBoxSearchBar = new JComboBox<String>();
		comboBoxSearchBar.setModel(new DefaultComboBoxModel<String>(new String[] {"PIva", "Nome", "Indirizzo", "CAP", "Telefono", "Sito Web"}));
		comboBoxSearchBar.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBoxSearchBar.setBackground(new Color(0, 128, 128));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(Color.WHITE);
		tableFornitori = new JTable(); 
		JTableHeader tHeader = tableFornitori.getTableHeader();
		tHeader.setBackground(new Color(44, 46, 60));
		tHeader.setForeground(new Color(241, 242, 237));
		tableFornitori.setSelectionBackground(new java.awt.Color(241, 242, 237));
		tableFornitori.setBackground(new java.awt.Color(144,148,155));
		/**
		 * questo metodo permette di selezionare una riga della tabella tableFornitori e visualizzare tutte le sue caratteristiche
		 */
		tableFornitori.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

					table.setVisible(true);//in questo modo rendo visibile la tabella articoli che riposta tutti gi artcoli ora disponibli nel negozio forniti dal fornitore da me selezionato
					int row1=tableFornitori.getSelectedRow();
					 EID_1=(tableFornitori.getModel().getValueAt(row1,0 ).toString());
					String query2 = "select \"TagliaColore\".\"EAN\" ,\"TagliaColore\".\"Colore\",\"TagliaColore\".\"Taglia\",\"TagliaColore\".\"Giacenza\"  "
							+ "from \"Negoziodiabbigliamento\".\"TagliaColore\" natural join \"Negoziodiabbigliamento\".\"Modello\" where \"Modello\".\"IDFornitore\"='"+EID_1+"'";
					Fornitore fornitore = FDAO.getFornitore(EID_1);
					refreshTable(query2, table);
					
						txtpiva.setText(fornitore.getPiva());
						txtIndirizzo.setText(fornitore.getIndirizzo());
						txtCap.setText(fornitore.getCap());
						txtNome.setText(fornitore.getNome());
						txtTelefono.setText(fornitore.getTelefono());
						txtSitoWeb.setText(fornitore.getSitoWeb());
				
			
			}
		});
		scrollPane_1.setViewportView(tableFornitori);
		//con questo metodo posso interire nella tabella fornitore una nuova componente effettuando i dovuti controlli
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
              	
              			if(!(txtNome.getText().isEmpty() || txtCap.getText().isEmpty() || txtIndirizzo.getText().isEmpty() || txtpiva.getText().isEmpty() ||
              					txtTelefono.getText().isEmpty())) {
              				if (!FDAO.esisteFornitore(txtpiva.getText())) {
              					
                  				FDAO.AggiungiFornitore(txtpiva.getText(), txtNome.getText(), txtIndirizzo.getText(), txtCap.getText(),
                  						txtTelefono.getText(), txtSitoWeb.getText());
                  				
                  				refreshTable2();  	
                  				
                  				JOptionPane.showMessageDialog(null, "Dati Salvati"); 
                  			}
                  			else {
                  				JOptionPane.showMessageDialog(null, "Piva già esistente."); 
                  			} 
              			}else {
              				JOptionPane.showMessageDialog(null, "Uno o piu campi sono vuoti");
              			}
              		
              	}
      
		});
		btnAdd.setBackground(new Color(0, 128, 128));
		//con questo metodo posso modificare i dati di un dato fornitore da me selezionato
		JButton btnupdate = new JButton("Update");
		btnupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					if(!(txtNome.getText().isEmpty() || txtCap.getText().isEmpty() || txtIndirizzo.getText().isEmpty() || txtpiva.getText().isEmpty() ||
          					txtTelefono.getText().isEmpty())) {
          				if(FDAO.esisteFornitore(txtpiva.getText())) {
          					if(JOptionPane.showConfirmDialog(null, "Sei sicuro di aggiornare i dati?", "Aggiornamento.",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
          						FDAO.ModificaFornitore(txtpiva.getText(), txtNome.getText(), txtIndirizzo.getText(), txtCap.getText(),
                  						txtTelefono.getText(), txtSitoWeb.getText());	
          						refreshTable2();
          						svuotaFields();
          						JOptionPane.showMessageDialog(null, "Modificato"); 
						}
          				}
          				else {
              				JOptionPane.showMessageDialog(null, "Fornitore Non esiste"); 

          				}
					}else {
          				JOptionPane.showMessageDialog(null, "Uno o piu campi sono vuoti");

					}
					
				refreshTable2();  	
			  
			}
		});
		btnupdate.setBackground(new Color(0, 128, 128));
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(44, 46, 60));
		txtNome.setBackground(new Color(44, 46, 60));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				nome = txtNome.getText();
				campiImportanti.add(nome);
			}
		});
		
		txtNome.setForeground(Color.WHITE);
		txtNome.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNome.addMouseListener(new TextMouseAdapter(txtNome) {});
		txtNome.setColumns(10);
		txtSitoWeb.setBackground(new Color(44, 46, 60));
		
		txtSitoWeb.setForeground(Color.WHITE);
		txtSitoWeb.addMouseListener(new TextMouseAdapter(txtSitoWeb) {});
		txtSitoWeb.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSitoWeb.setColumns(10);
		txtIndirizzo.setBackground(new Color(44, 46, 60));
		
		txtIndirizzo.setForeground(Color.WHITE);
		txtIndirizzo.addMouseListener(new TextMouseAdapter(txtIndirizzo) {});
		txtIndirizzo.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtIndirizzo.setColumns(10);
		txtCap.setBackground(new Color(44, 46, 60));
		
		
		txtCap.setForeground(Color.WHITE);
		txtCap.addMouseListener(new TextMouseAdapter(txtCap) {});
		txtCap.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtCap.setColumns(10);
		txtCap.addKeyListener(new KeyAdapter() {
			//allow only digit
			@Override
			public void keyTyped(KeyEvent evt) {
				 cap = txtCap.getText();
				char c =evt.getKeyChar();
				if(!control.isTheCorrectNumberFormat(c, cap, 5)) {
					evt.consume();
				}
			}
		});
		txtTelefono.setBackground(new Color(44, 46, 60));
		
		txtTelefono.setForeground(Color.WHITE);
		txtTelefono.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTelefono.addMouseListener(new TextMouseAdapter(txtTelefono) {});
		txtTelefono.setColumns(10);
		txtTelefono.addKeyListener(new KeyAdapter() {
			//allow only digit
			@Override
			public void keyTyped(KeyEvent evt) {
				 cell = txtTelefono.getText();
				 campiImportanti.add(cell);
					char c =evt.getKeyChar();
					if(!control.isTheCorrectNumberFormat(c, cell, 10)) {
						evt.consume();
					}			
			}
		});
		txtpiva.setBackground(new  Color(44, 46, 60));
		
		txtpiva.addKeyListener(new KeyAdapter() {
			//allow only digit
			@Override
			public void keyTyped(KeyEvent evt) {
				 piva = txtpiva.getText();
				 campiImportanti.add(piva);
					char c =evt.getKeyChar();
					if(!control.isTheCorrectNumberFormat(c, piva, 11)) {
						evt.consume();
					}
			}
			
		});
		txtpiva.setForeground(Color.WHITE);
		txtpiva.addMouseListener(new TextMouseAdapter(txtpiva) {});
		txtpiva.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtpiva.setColumns(10);
		
		JLabel lblDelete = new JLabel("");
		lblDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                if(!control.campiImportantiVuoti(campiImportanti)) {
                	
                		if(JOptionPane.showConfirmDialog(null, "Sei sicuro di eliminare questo record?\n"
                    			+ "Tutti i capi forniti da questo fornitore andranno persi.", "Elimina.",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                			FDAO.RimuoviFornitore(txtpiva.getText());
                			JOptionPane.showMessageDialog(null, "Record eliminato."); 
					
                			refreshTable2();  
                		}
                }
			}
		});
		lblDelete.setHorizontalAlignment(SwingConstants.CENTER);
		lblDelete.setIcon(new ImageIcon(PanelFornitore.class.getResource("/img/trash-alt-regular-24-inactive.png")));
		
		JLabel lblSito = new JLabel("Sito Web");
		lblSito.setForeground(Color.WHITE);
		
		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setForeground(Color.WHITE);
		
		JLabel lblcap = new JLabel("Cap");
		lblcap.setForeground(Color.WHITE);
		
		JLabel lblNewLabel = new JLabel("Telefono");
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblNewLabel_1 = new JLabel("PIva");
		lblNewLabel_1.setForeground(Color.WHITE);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtpiva, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
							.addComponent(lblDelete, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtIndirizzo, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addGap(25)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblcap)
								.addComponent(txtCap, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
						.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSitoWeb, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSito)
						.addComponent(lblIndirizzo)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(29)
					.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(25)
					.addComponent(lblSito)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSitoWeb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIndirizzo)
						.addComponent(lblcap))
					.addGap(7)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtIndirizzo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(14)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtpiva, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addComponent(txtCap, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(112)))
					.addComponent(lblDelete, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JLabel lblTitle = new JLabel("Fornitori");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setForeground(new Color(124, 127, 136));
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel lblrefresh = new JLabel("");
		lblrefresh.setVisible(false);
		lblrefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				svuotaFields();
				table.setVisible(false);
				refreshTable2();
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblrefresh.setVisible(false);
				lblrefresh_1.setVisible(true);
			}
		});
		lblrefresh.setIcon(new ImageIcon(PanelFornitore.class.getResource("/img/refresh-regular-24.png")));
		
		lblrefresh_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblrefresh_1.setVisible(false);
				lblrefresh.setVisible(true);
			}
		});
		lblrefresh_1.setIcon(new ImageIcon(PanelFornitore.class.getResource("/img/refresh-regular-24-2.png")));
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(144, 148, 155));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		table = new JTable();
		table.setVisible(false);
		scrollPane.setViewportView(table);
		GroupLayout groupLayout_1 = new GroupLayout(this);
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout_1.createSequentialGroup()
					.addGap(295)
					.addComponent(comboBoxSearchBar, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(264)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblrefresh_1)
						.addComponent(lblrefresh)))
				.addGroup(groupLayout_1.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnupdate, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(109)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)))
				.addGroup(Alignment.LEADING, groupLayout_1.createSequentialGroup()
					.addGap(7)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap(408, Short.MAX_VALUE)
					.addComponent(txtSearchBar, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
					.addGap(325))
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addGap(11)
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(2)
							.addComponent(comboBoxSearchBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblrefresh_1)
						.addComponent(lblrefresh))
					.addGap(2)
					.addComponent(txtSearchBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(31)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(221)
							.addComponent(btnupdate, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout_1.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))))
		);
		setLayout(groupLayout_1);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 933, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(295)
					.addComponent(comboBoxSearchBar, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txtSearchBar, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblrefresh_1)
						.addComponent(lblrefresh)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnupdate, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(109)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(comboBoxSearchBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(txtSearchBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblrefresh_1)
						.addComponent(lblrefresh))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(221)
							.addComponent(btnupdate, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))))
		);
		setLayout(groupLayout);
		
		
		
		
		
		refreshTable2();
		
	}
	
	/**
	 * questo metodo permette di inizializzare tutti i jtext " "
	 */
	
	private void svuotaFields() {
		txtCap.setText("");
		txtIndirizzo.setText("");
		txtNome.setText("");
		txtpiva.setText("");
		txtSitoWeb.setText("");
		txtTelefono.setText("");
		
	}
	
	/**
	 * questo metodo permette di visualizzare tutti i fornitori all'inteno della tabella
	 */
	 public void refreshTable2() {
		 String query = "select * from \"Negoziodiabbigliamento\".\"Fornitore\"";
			refreshTable(query, tableFornitori);
			
	} 
}
