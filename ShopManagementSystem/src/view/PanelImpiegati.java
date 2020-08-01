package view;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.JTableHeader;
import javax.swing.text.MaskFormatter;
import model.Impiegato;
import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import dao.ImpiegatoDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import java.awt.Cursor;
import javax.swing.JCheckBox;

/**
 * Questa classe permette la presa visione di tutti gli impiegati e la modifica di alcuni dati lavorativi
 * 
 * @author Antonio Di Nuzzo
 * @author Eleonora DI Santi
 *
 */
public class PanelImpiegati extends AbstractPanel {
	
	private JTable table;
	private JTextPane textPaneNomeCognome = new JTextPane();
	
	private JCheckBox chckbxRendiAdmin;

	private JTextField txtidoperatore = new JTextField() , txtemail = new JTextField(),  txtcell = new JTextField(), txtstipendio = new JTextField(),
					txtdn = new JTextField(), txtcf = new JTextField(), txtscelta = new JTextField(), txtFatturato = new JTextField();
	  
	private JFormattedTextField formattedTextField = new JFormattedTextField(); 
	    
	private String  CF, privilegio;
	private JPanel panelShowEmployee = new JPanel();
	private ImpiegatoDAO IDAO = new ImpiegatoDAO();
	private Impiegato I ;
	private JLabel lblRefreshInactive = new JLabel(""), lblRefreshActive;
	private JButton btnUpdate = new JButton("Aggiorna");
	private JLabel lblDelete = new JLabel("");
	private JLabel lblFotoProf ;
	private double stipendio;


	/**
	 * Create the panel. 
	 */
	public PanelImpiegati(String cfUser) {
		
		JPanel panel = new JPanel();
		setBounds(0, 0, 899, 683);
		panel.setBackground(new java.awt.Color(144,148,155));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
		);
		
		JLabel lblImpiegati = new JLabel("Impiegati");
		lblImpiegati.setHorizontalAlignment(SwingConstants.LEFT);
		lblImpiegati.setForeground(new java.awt.Color(124,127,136));
		lblImpiegati.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new java.awt.Color(144, 148, 155));
		
		 lblFotoProf = new JLabel("");
		 lblFotoProf.setBorder(null);
			lblFotoProf.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		setLayout(groupLayout);	
		table = new JTable();
		
			
		/*
		 * Modifico il colore dell'header della tabella
		 */
		JTableHeader tHeader = table.getTableHeader();
		tHeader.setBackground(new Color(44, 46, 60));
		tHeader.setForeground(new Color(241, 242, 237));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

            	  panelShowEmployee.setVisible(true);// E' reso visibile il panel impiegati
            	  btnUpdate.setVisible(true);		// E' reso visibile il button aggiornamento
            	  lblFotoProf.setIcon(null);
				 lblFotoProf.revalidate();
          		int row1=table.getSelectedRow();
				CF=(table.getModel().getValueAt(row1,2 ).toString());
				I = new Impiegato(IDAO.MostraImpiegatobyCf(CF));
				
				adattaFoto(I.getImg());
				if(cfUser.equals(I.getCf())) {//se siamo noi stessi
					userSelected(true);
				}else {
					userSelected(false);
				}

				textPaneNomeCognome.setText(I.getNome()+" "+I.getCognome());
				txtcf.setText(I.getCf());
				txtidoperatore.setText(I.getIdOperatore());
				txtcell.setText(I.getCell());
				txtemail.setText(I.getEmail());
				txtFatturato.setText(arrotonda(IDAO.totalefatturatoperimpiegato(I.getIdOperatore()))+" €");
				txtdn.setText(I.getDataNascita());
				formattedTextField.setText(I.getDataAssunzione());

				
					txtstipendio.setText(String.valueOf(I.getStipendio()));
			}

		});
		scrollPane.setViewportView(table);
		
		btnUpdate.setVisible(false);
		btnUpdate.setBackground(new Color(0, 128, 128));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(formattedTextField.getText().isEmpty() || txtstipendio.getText().isEmpty()  || txtcf.getText().isEmpty() || txtidoperatore.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Uno o più campi sono vuoti", "Errore", JOptionPane.ERROR_MESSAGE);
				}else {
					
					if(JOptionPane.showConfirmDialog(null, "Si stanno per modificare i dati di "+I.getNome()+" "+I.getCognome()+"\nContinuare?", "Aggiornamento",JOptionPane.YES_NO_OPTION)
							== JOptionPane.YES_OPTION) {
						if(cfUser.equals(I.getCf())) {
							IDAO.ModificaDatiPersonali(I.getIdOperatore(), txtemail.getText(), txtcell.getText(), I.getPsw(), I.getImg(), I.getCf());
							
						}else {
							IDAO.ModificaDatiLavorativiByCf(formattedTextField.getText(),stipendio,txtcf.getText(), privilegio);
						}
					JOptionPane.showMessageDialog(null,"Modificato");
					refreshTable1();  	
					}
					
				}
	    	  
			}
		});
		
		JComboBox<String> scelta = new JComboBox<String>();
		scelta.setModel(new DefaultComboBoxModel<String>(new String[] {"Nome", "Cognome", "CF"}));
		
		txtscelta = new JTextField();
		txtscelta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
	    			String select =(String)scelta.getSelectedItem();
	    			table.setModel(DbUtils.resultSetToTableModel(IDAO.MostraImpiegato(select, txtscelta.getText())));
	    		
			}
		});
		txtscelta.setColumns(10);
		
		panelShowEmployee.setVisible(false);
		panelShowEmployee.setBackground(new Color(124,127,136));
		
		
		 formattedTextField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		 try {
				if(formattedTextField.getText().isEmpty()) {
					try {
						MaskFormatter priceMask = new MaskFormatter("##/##/####");
						priceMask.install(formattedTextField);
						formattedTextField.setText(I.getDataAssunzione());
						
					} catch (ParseException ex) {
						
					}
				}else {
					formattedTextField.setText(I.getDataAssunzione());
				}
			}catch(NullPointerException nulp) {}
			
		 formattedTextField.addKeyListener(new KeyAdapter() {
			 	@Override
			 	public void keyTyped(KeyEvent evt) {
			 		char c =evt.getKeyChar();
			 		if(Character.isAlphabetic(c) || evt.isAltDown()) {
			 			evt.consume();
			 		}
			 	}
			 });
		JLabel lblCf = new JLabel("CF");
		
		JLabel lblId = new JLabel("ID");
		
		JLabel lblDataAssunzione = new JLabel("Data Assunzione");
		
		JLabel lblDataNascita = new JLabel("Data Nascita");
		
		JLabel lblStipendio = new JLabel("Stipendio");
		
		JLabel lblEmail = new JLabel("EMail");
		
		JLabel lblCell = new JLabel("Cell");
		
		JSeparator separator_1 = new JSeparator();
		
		
		lblDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					if(JOptionPane.showConfirmDialog(null, "Sei sicuro di eliminare questo record?", "Elimina",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
						IDAO.RimuoviImpiegatoByCf(CF);
						btnUpdate.setVisible(false);
						panelShowEmployee.setVisible(false);
						refreshTable1();
						JOptionPane.showMessageDialog(null, "Record eliminato."); 
					}
				  
				}
			
			
		});
		lblDelete.setIcon(new ImageIcon(PanelImpiegati.class.getResource("/img/trash-alt-regular-24.png")));
		
		lblRefreshActive = new JLabel("");
		lblRefreshActive.setVisible(false);
		lblRefreshActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnUpdate.setVisible(false);
				panelShowEmployee.setVisible(false);
				refreshTable1();
				

			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRefreshActive.setVisible(false);
				lblRefreshInactive.setVisible(true);
			}
		});
		lblRefreshActive.setIcon(new ImageIcon(PanelImpiegati.class.getResource("/img/refresh-regular-24.png")));
		
		lblRefreshInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRefreshInactive.setVisible(false);
				lblRefreshActive.setVisible(true);
			}
		});
		lblRefreshInactive.setIcon(new ImageIcon(PanelImpiegati.class.getResource("/img/refresh-regular-24-2.png")));
		
		JLabel lblErrorStipendio = new JLabel("");
		lblErrorStipendio.setForeground(Color.RED);
		
		chckbxRendiAdmin = new JCheckBox("Rendi Admin");
		chckbxRendiAdmin.setIcon(new ImageIcon(PanelImpiegati.class.getResource("/img/square.png")));
		chckbxRendiAdmin.setSelectedIcon(new ImageIcon(PanelImpiegati.class.getResource("/img/check-square.png")));
		chckbxRendiAdmin.setBackground(new Color(124,127,136));
		
		chckbxRendiAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxRendiAdmin.isSelected()) {
					privilegio = "Admin";
				}
				else {
					privilegio = "";
				}
			}
		});
		
		JLabel lblFatturato = new JLabel("Fatturato");
		
		
		
		
		GroupLayout gl_panelShowEmployee = new GroupLayout(panelShowEmployee);
		gl_panelShowEmployee.setHorizontalGroup(
			gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelShowEmployee.createSequentialGroup()
					.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(txtidoperatore, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(txtemail, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(lblDataNascita, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(txtdn, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(lblDataAssunzione, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(formattedTextField, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addGap(197)
							.addComponent(lblDelete))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addComponent(lblCf, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(10)
							.addComponent(lblFotoProf, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.addGap(43)
							.addComponent(textPaneNomeCognome, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
								.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelShowEmployee.createSequentialGroup()
									.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
										.addComponent(txtstipendio, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.TRAILING, false)
											.addGroup(gl_panelShowEmployee.createSequentialGroup()
												.addComponent(lblStipendio, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblErrorStipendio, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
											.addComponent(txtcf, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panelShowEmployee.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
												.addComponent(chckbxRendiAdmin))
											.addComponent(lblFatturato, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
										.addComponent(txtFatturato, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addGap(16)
							.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCell, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtcell, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))))
					.addGap(10))
		);
		gl_panelShowEmployee.setVerticalGroup(
			gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelShowEmployee.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFotoProf, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPaneNomeCognome, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addComponent(lblCf)
					.addGap(11)
					.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtcf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxRendiAdmin))
					.addGap(11)
					.addComponent(lblId)
					.addGap(11)
					.addComponent(txtidoperatore, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblEmail)
					.addGap(7)
					.addComponent(txtemail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
						.addComponent(lblStipendio)
						.addComponent(lblErrorStipendio, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFatturato))
					.addGap(11)
					.addGroup(gl_panelShowEmployee.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelShowEmployee.createSequentialGroup()
							.addComponent(txtstipendio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(lblDataNascita)
							.addGap(10)
							.addComponent(txtdn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(lblCell)
							.addGap(11)
							.addComponent(txtcell, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(14)
							.addComponent(lblDataAssunzione)
							.addGap(11)
							.addComponent(formattedTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(lblDelete))
						.addComponent(txtFatturato, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		txtstipendio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				/*
			 	 * Controlla che il numero inserito sia double
			 	 * emettendo un messaggio d'errore .
			 	 */
		 	
		 		try {
			 		stipendio = Double.parseDouble(txtstipendio.getText());
		 		}catch(NumberFormatException e) {
		 			if(!txtstipendio.getText().isEmpty()) {
		 				lblErrorStipendio.setText("Numero invalido");
		 				lblErrorStipendio.setIcon(new ImageIcon(PanelImpiegati.class.getResource("/img/alert-circle.png")));

		 			}else {
		 				lblErrorStipendio.setText("");
		 				lblErrorStipendio.setIcon(null);

		 			}
		 		}
			}
		});
		panelShowEmployee.setLayout(gl_panelShowEmployee);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblImpiegati, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 741, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelShowEmployee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 473, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(92)
							.addComponent(scelta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(txtscelta, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRefreshInactive, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRefreshActive, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblImpiegati, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(21)
									.addComponent(scelta, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(22)
									.addComponent(txtscelta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(23)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRefreshInactive, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblRefreshActive, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
							.addGap(7)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
							.addGap(81))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(panelShowEmployee, GroupLayout.PREFERRED_SIZE, 544, GroupLayout.PREFERRED_SIZE)
							.addGap(28))))
		);
		panel.setLayout(gl_panel);
		
	
		 refreshTable1();
	}
	/**
	 * Questo metodo cambia il pannello degli impiegati e le possibilità di modifica,
	 * permette di modificare :
	 * <ul>
	 * 	<li> Username
	 * 	<li> Email
	 * 	<li> Cell
	 * </ul>
	 * altrimenti non editabili nei pannelli degli impiegati.
	 * 
	 * @param answer boolean contiene la richiesta del cambio pannello</br>
	 * <b>true</b> Cambio pannello</br>
	 * <b>false</b> Non cambio pannello
	 * 
	 */
	private void userSelected(boolean answer) {
		if(answer) {
			adattaFoto(I.getImg());
			
			chckbxRendiAdmin.setVisible(false);
			
			textPaneNomeCognome.setForeground(Color.BLACK);
			textPaneNomeCognome.setBackground(new Color(124,127,136));
			textPaneNomeCognome.setEditable(false);
			textPaneNomeCognome.setFont(new Font("Tahoma", Font.BOLD, 18));

			txtcf.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtcf.setBorder(null);
			txtcf.setEditable(false);
			txtcf.setBackground(new Color(124,127,136));
			txtcf.setColumns(10);
			
			txtidoperatore.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtidoperatore.setBorder(null);
			txtidoperatore.setEditable(false);
			txtidoperatore.setBackground(new Color(241, 242, 237));
			txtidoperatore.setColumns(10);
			
			txtemail.setEditable(true);
			txtemail.setBorder(null);
			txtemail.setBackground(new Color(241, 242, 237));
			txtemail.setColumns(10);
			
			txtcell.setBorder(null);
			txtcell.setEditable(true);
			txtcell.setBackground(new Color(241, 242, 237));
			txtcell.setColumns(10);
			
			txtstipendio.setBorder(null);
			txtstipendio.setEditable(false);
			txtstipendio.setBackground(new Color(124,127,136));
			txtstipendio.setColumns(10);
			
			txtcf.setBorder(null);
			txtcf.setEditable(false);
			txtcf.setBackground(new Color(124,127,136));
			
			txtdn.setBorder(null);
			txtdn.setEditable(false);
			txtdn.setBackground(new Color(124,127,136));
			txtdn.setColumns(10);
			
			txtFatturato.setBorder(null);
			txtFatturato.setEditable(false);
			txtFatturato.setBackground(new Color(124,127,136));
			
			txtidoperatore.setBorder(null);
			txtidoperatore.setEditable(false);
			txtidoperatore.setBackground(new Color(124,127,136));
			
			formattedTextField.setBorder(null);
			formattedTextField.setEditable(false);
			formattedTextField.setBackground(new Color(124,127,136));
		lblDelete.setVisible(false);
		}
		else {
			
			chckbxRendiAdmin.setVisible(true);

			textPaneNomeCognome.setForeground(Color.BLACK);
			textPaneNomeCognome.setBackground(new Color(124,127,136));
			textPaneNomeCognome.setEditable(false);
			textPaneNomeCognome.setFont(new Font("Tahoma", Font.BOLD, 18));
			
			txtcf.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtcf.setBorder(null);
			txtcf.setEditable(false);
			txtcf.setBackground(new Color(124,127,136));
			txtcf.setColumns(10);
			
			txtidoperatore.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtidoperatore.setBorder(null);
			txtidoperatore.setEditable(false);
			txtidoperatore.setBackground(new Color(124,127,136));
			txtidoperatore.setColumns(10);
			
			txtemail.setBorder(null);
			txtemail.setEditable(false);
			txtemail.setBackground(new Color(124,127,136));
			txtemail.setColumns(10);
			
			txtcell.setBorder(null);
			txtcell.setEditable(false);
			txtcell.setBackground(new Color(124,127,136));
			txtcell.setColumns(10);
			
			txtdn.setBorder(null);
			txtdn.setEditable(false);
			txtdn.setBackground(new Color(124,127,136));
			txtdn.setColumns(10);
			
			txtstipendio.setBorder(null);
			txtstipendio.setEditable(true);
			txtstipendio.setBackground(new Color(241, 242, 237) );
			txtstipendio.setColumns(10);
			
			formattedTextField.setBorder(null);
			formattedTextField.setEditable(true);
			formattedTextField.setBackground(new Color(241, 242, 237));
			
			lblDelete.setVisible(true);
			
		}
	}
	/**
	 * Adatta l'immagine al label specifico
	 * @param img L'immagine
	 */
	private void adattaFoto(byte[] img) {
		  
		try {
			ImageIcon image = new ImageIcon(img );
			Image im = image.getImage();
			Image myImg = im.getScaledInstance(lblFotoProf.getWidth(),lblFotoProf.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon newImage = new ImageIcon(myImg);
			lblFotoProf.setIcon(newImage);
		}catch(NullPointerException e) {
			lblFotoProf.setText("Foto Non Presente");
		}
	}
	
	public void refreshTable1() {
		String query = "select  \"Impiegato\".\"Nome\" ,\"Impiegato\".\"Cognome\",\"Impiegato\". \"CF\"  from \"Negoziodiabbigliamento\".\"Impiegato\"";
		refreshTable(query, table);
	}
	/**
	 * 
	 * 
	 * @param operation Il carattere dell'operazione da effettuare
	 * @param price Il prezzo da aggiungere/sottrarre
	 */
	private String arrotonda ( double tot) {
		
		String totale = String.valueOf(tot);
		int cifreDopoPunto = (totale.substring(totale.indexOf('.'), totale.length())).length();
		 if( cifreDopoPunto > 4) {
			totale = totale.substring(0, totale.indexOf('.')+2);
			return totale;
		 }else {
			 return totale;
			 }
		
	}
}
