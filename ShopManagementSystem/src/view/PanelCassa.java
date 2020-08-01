package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import dao.ModelloDAO;
import dao.ScontrinoDAO;
import dao.TagliaColoreDAO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * Questa classe rappresenta la cassa con possibilità di acquisto e riduzione dei capi in magazino, 
 * nonchè l'emissione di uno scontrino
 * 
 * @author Antonio Di Nuzzo
 * @author Eleonora Di Santi
 *
 */
public class PanelCassa extends AbstractPanel {
	private JTable tableMagazzino;
	private String EID_1, EID_2, idOp;
	
	private TagliaColoreDAO TCDAO = new TagliaColoreDAO();
	private ModelloDAO MDAO = new ModelloDAO();
	private ScontrinoDAO SDAO = new ScontrinoDAO();
	
	private JTextField txtTotale, txtCerca;
	private double price, tot;

	private int idScontrino;
	private JTable tableDettagliScnt;
	private JLabel lblScontrinoActive, lblScontrinoInactive, lblCassa, lblDeleteOrderInactive, lblDeleteOrderActive;
	
	private TableColorCellRenderer renderer = new TableColorCellRenderer();
	
	/*Questo array list serve ad immagazzinare i modelli acquistati
	 * per prelevarne successivamente i dati utili allo scontrino*/
	private ArrayList<String>modelli = new ArrayList<String>();
	/************************************************************/
	
	/*Questo array list si aggiorna ogni qualvolta un capo è immesso/rimosso in dettagli.
	 * Ciò viene fatto in caso di eliminazione voluta dello scontrino/ordine, viene iterato
	 * ripristinando la giacenza dei capi                                                  */
	private ArrayList<String>eans = new ArrayList<String>();
	/***************************************************************************************/

	/**
	 * Create the panel.
	 */
	public PanelCassa(String IDOperatore) {
		idOp = IDOperatore;
		setBackground(new Color(144,148,155));
		idScontrino = SDAO.numeroscontrino()+1;
		
		JScrollPane scrollPane = new JScrollPane();
		
		tableMagazzino = new JTable();
		tableMagazzino.setVisible(false);
		/*
		 * Modifico il colore dell'header della tabella
		 */
		JTableHeader tHeader1 = tableMagazzino.getTableHeader();
		tHeader1.setBackground(new Color(44, 46, 60));
		tHeader1.setForeground(new Color(241, 242, 237));
		tableMagazzino.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tableMagazzino.getSelectedRow();
				 EID_1=(tableMagazzino.getModel().getValueAt(row,0 ).toString());
				 EID_2 = (tableMagazzino.getModel().getValueAt(row,4).toString());
				 //riduco la giacenza del capo
				 TCDAO.riduciGiacenza(EID_1, 1);
				 refreshTableMagazzino();
				 modelli.add(EID_2);
				 // ne ricavo il prezzo
				 price = 0;
				 price = MDAO.getPrice(EID_2);
				 //aggiorno il totale
				 aggiornaTotale('+',price);
				 //inserisco il capo nei dettagli dello scontrino
				 SDAO.inserisciDettagli(EID_1, SDAO.getScontrinoAttivo());
				 eans.add(EID_1);
				 refreshTable2( SDAO.getScontrinoAttivo());

				 


			}
		});
		scrollPane.setViewportView(tableMagazzino);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		tableDettagliScnt = new JTable();
		tableDettagliScnt.setDefaultRenderer(Object.class, renderer);

		/*
		 * Modifico il colore dell'header della tabella
		 */
		JTableHeader tHeader2 = tableDettagliScnt.getTableHeader();
		tHeader2.setBackground(new Color(44, 46, 60));
		tHeader2.setForeground(new Color(241, 242, 237));
		tableDettagliScnt.setVisible(false);
		tableDettagliScnt.addMouseListener(new MouseAdapter() {
			/**
			 * Al click del mouse sulla tabella dei dettagli viene chiesto se il capo selezionato deve essere rimosso.
			 * In caso di risposta affermativa si procede con il prelevare i dati utili per ripristinare la giacenza di quel capo.
			 * Viene aggiornato il prezzo e i rispettivi array list.
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tableDettagliScnt.getSelectedRow();
				if(JOptionPane.showConfirmDialog(null, "Rimuovere capo dall'ordine?", "Attenzione", JOptionPane.YES_NO_OPTION)
						== JOptionPane.YES_OPTION) {
					 EID_1=(tableDettagliScnt.getModel().getValueAt(row,0 ).toString()).trim();
					 int qta = (int) (tableDettagliScnt.getModel().getValueAt(row,2)); 
					 eans.remove(EID_1);
					 TCDAO.aumentaGiacenza(EID_1, qta);
					 // ne ricavo il prezzo
					 price = MDAO.getPrice(EID_2);
					 
					 //aggiorno il totale
					 for(int i = 0; i< qta; i++) {
						 aggiornaTotale('-',price);
					 }
					 //rimuovo il capo nei dettagli dello scontrino
					 SDAO.rimuoviDettagli(EID_1, idScontrino);
					 refreshTable2(SDAO.getScontrinoAttivo());
					 refreshTableMagazzino();
					 					 
				}
			}
		});
		scrollPane_1.setViewportView(tableDettagliScnt);
		
		
		JComboBox comboBoxCERCA = new JComboBox();
		comboBoxCERCA.setVisible(false);
		comboBoxCERCA.setModel(new DefaultComboBoxModel(new String[] {"EAN", "Colore", "Taglia", "Nome", "Sesso"}));
		txtCerca = new JTextField();
		txtCerca.setVisible(false);
		txtCerca.setBackground(new Color(241, 242, 237));
		txtCerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String select =(String)comboBoxCERCA.getSelectedItem();
				String query = "select * from \"Negoziodiabbigliamento\".\"Magazzino\" where \""+select+"\"=?";
				refreshTable(query, tableMagazzino);
			}
		});
		txtCerca.setColumns(10);
		
		
		JButton btnCreaOrdine = new JButton("Emetti");
		btnCreaOrdine.setVisible(false);
		btnCreaOrdine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtTotale.getText().equals("0.0")) {
					JOptionPane.showMessageDialog(null, "Nessun Ordine Attivo","Info", JOptionPane.INFORMATION_MESSAGE);
				}else {
					if(JOptionPane.showConfirmDialog(null, "Confermi?","Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						stampaScontrino();
						SDAO.emettiScontrino(SDAO.getScontrinoAttivo(), Double.parseDouble(txtTotale.getText()));
						JOptionPane.showMessageDialog(null, "Grazie, Arrivederci!");
						txtTotale.setText("0.0");
						refreshTable2(SDAO.getScontrinoAttivo());
						
						eans.clear();
						modelli.clear();
						tableDettagliScnt.setVisible(false);
						tableMagazzino.setVisible(false);
					}
				}
				
			}
		});
		
		txtTotale = new JTextField("0.0");
		txtTotale.setVisible(false);
		txtTotale.setBackground(new Color(124,127,136));
		txtTotale.setEditable(false);
		txtTotale.setColumns(10);
		
		JLabel lblTotale = new JLabel("Totale");
		lblTotale.setVisible(false);
		
		lblCassa = new JLabel("Cassa");
		lblCassa.setHorizontalAlignment(SwingConstants.LEFT);
		lblCassa.setForeground(new Color(124, 127, 136));
		lblCassa.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(144, 148, 155));
		
		lblScontrinoActive = new JLabel("");
		lblScontrinoActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblScontrinoActive.setVisible(false);
				lblScontrinoInactive.setVisible(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				idScontrino = SDAO.numeroscontrino(); 
				SDAO.creaScontrino(idScontrino, 0.0, IDOperatore);
				txtCerca.setVisible(true);
				comboBoxCERCA.setVisible(true);
				tableMagazzino.setVisible(true);
				tableDettagliScnt.setVisible(true);
				lblTotale.setVisible(true);
				txtTotale.setVisible(true);
				btnCreaOrdine.setVisible(true);
				lblDeleteOrderInactive.setVisible(true);
				

			}
		});
		lblScontrinoActive.setVisible(false);
		lblScontrinoActive.setIcon(new ImageIcon(PanelCassa.class.getResource("/img/scontrinoActive.png")));
		
		lblScontrinoInactive = new JLabel("");
		lblScontrinoInactive.setVisible(true);
		lblScontrinoInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblScontrinoInactive.setVisible(false);
				lblScontrinoActive.setVisible(true);
			}
		});
		lblScontrinoInactive.setIcon(new ImageIcon(PanelCassa.class.getResource("/img/scontrinoInactive.png")));
		
		lblDeleteOrderActive = new JLabel("");
		lblDeleteOrderActive.setVisible(false);
		lblDeleteOrderActive.addMouseListener(new MouseAdapter() {
			/**
			 * Elimina lo scontrino e ripristina le giacenze, blocca tutte le table 
			 * in modo da obligare ad emettere un nuovo scontrino
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Eliminare questo scontrino?","Attenzione", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
					for(String ean : eans) {
						TCDAO.aumentaGiacenza(ean, 1);
					}
					refreshTableMagazzino();
					txtTotale.setText("0.0");
					tot = 0;
					SDAO.eliminaScontrino(idScontrino);
					refreshTable2(SDAO.getScontrinoAttivo());
					eans.clear();
					modelli.clear();
					tableMagazzino.setVisible(false);
					tableDettagliScnt.setVisible(false);
					JOptionPane.showMessageDialog(null, "Scontrino Eliminato!\nGiacenze Ripristinate");
					
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDeleteOrderActive.setVisible(false);
				lblDeleteOrderInactive.setVisible(true);
			}
		});
		lblDeleteOrderActive.setIcon(new ImageIcon(PanelCassa.class.getResource("/img/trashInactive24.png")));
		
		lblDeleteOrderInactive = new JLabel("");
		lblDeleteOrderInactive.setVisible(false);
		lblDeleteOrderInactive.setToolTipText("Elimina Scontrino");
		lblDeleteOrderInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblDeleteOrderActive.setVisible(true);
				lblDeleteOrderInactive.setVisible(false);
			}
		});
		lblDeleteOrderInactive.setIcon(new ImageIcon(PanelCassa.class.getResource("/img/trash-alt-regular-24.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblScontrinoInactive, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblScontrinoActive, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addComponent(comboBoxCERCA, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(txtCerca, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
					.addGap(96)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDeleteOrderInactive, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDeleteOrderActive, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTotale, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtTotale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnCreaOrdine, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE))
					.addGap(29))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCassa, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(719, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(9)
					.addComponent(lblCassa, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
					.addGap(81)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblScontrinoInactive)
								.addComponent(lblScontrinoActive)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(comboBoxCERCA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(17)
									.addComponent(txtCerca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(9)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDeleteOrderInactive, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDeleteOrderActive, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblTotale))
								.addComponent(txtTotale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCreaOrdine, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))))
		);
		setLayout(groupLayout);
		
		
		
		
		
		refreshTableMagazzino();
	}
	/**
	 * Aggiorna la tabella del magazzino
	 */
	private void refreshTableMagazzino() {
		String query = "select * from \"Negoziodiabbigliamento\".\"Magazzino\" where \"Giacenza\">1 ";
		refreshTable(query, tableMagazzino);
    }
	
	/**
	 * Funzione necessara a visuaizzare in maniera sempre aggiornata la tabella DETTAGLI riferita allo scontrino che stiamo per generare
	 * @param nScontr Il numero dello scontrino
	 */
	public void	 refreshTable2( int nScontr) {
		String query = "select * from \"Negoziodiabbigliamento\".\"Dettagli\" where \"IDScontrino\"=?";
		 refreshTable(query, tableDettagliScnt);
		
		}
	
		/**
		 * Aggiorna il  totale dell'ordine in corso
		 * 
		 * @param operation Il carattere dell'operazione da effettuare
		 * @param price Il prezzo da aggiungere/sottrarre
		 */
		private void aggiornaTotale (char operation, double price) {
			tot = Double.parseDouble(txtTotale.getText());
			switch(operation) {
			case '+':
				tot += price;
				break;
			case '-':
				tot -= price;
				break;

			}
			String totale = String.valueOf(tot);
			int cifreDopoPunto = (totale.substring(totale.indexOf('.'), totale.length())).length();
			 if( cifreDopoPunto > 4) {
				totale = totale.substring(0, totale.indexOf('.')+2);
				 txtTotale.setText(totale);

			 }else {
				 txtTotale.setText(totale);
			 }
			
		}
		
		
	
	
	
	
	
	private void stampaScontrino() {
		
		try {
			File file = new File("Scontrino"+SDAO.getScontrinoAttivo()+".txt");
			
			if(!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			pw.print("******************************************\n"
				+    "\t\tRECEIPT\n"
				+    "******************************************\n"
				+    "\n"
				+    "Data:"+SDAO.getScontrinoAttivoData()+"\n"
				+    "DESCRIZIONE\t\tPrezzo(€)\n");
			for(String idModello : modelli) {
				 
				pw.print(MDAO.getDescrizione(idModello)+"\t\t"+MDAO.getPrice(idModello)+"\n");
		   }
			pw.print("-------------------------------------------\n"
					+ "TOTALE                      EUR  "+txtTotale.getText()+"\n"
					+ "===========================================\n"
					+ "OPERATORE "+idOp+"\n"
					+ "SCONTRINO-NR "+SDAO.getScontrinoAttivo()+"\n"
					+ "-------------------------------------------\n"
					+ "\tSe hai questo, è andato tutto bene      ");
				pw.close();
		}catch(IOException e) {
			
		} 
	}
}
