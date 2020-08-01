package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.CollezioneDAO;
import model.Controllo;
import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.table.JTableHeader;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 * Crea un frame che permette l'agginta di nuove collezioni e la visione di quelle già esistenti
 * 
 * @author Antonio Di Nuzzo
 *
 */
public class CollectionFrame extends JFrame {

	private JPanel contentPane;
	private CollezioneDAO CDAO = new CollezioneDAO();
	
	/*             Fields e button per l'inserimento dei dati  
	 * 			   variabili per i dati                                   */
	private JTextField textCollectionName, txtIdcollection;
	private String collectionName , id, sesso , IDSelected;
	private  ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnMale, rdbtnFemale;

	/*********************************************************/
	
	private Controllo control = new Controllo();
	/*				Imagazzinano i messaggi d'errore e i campi importanti
	 * 					per un successivo controllo                       */
	private ArrayList<String> campiImportanti = new ArrayList<String>();
	private ArrayList<JLabel> errori = new ArrayList<JLabel>();
	/**********************************************************************/
	
	/*               Table per mostrare/eliminare le collezioni
	 * 					label di titolo/intestazione dei fields           */
	private JTable table;
	private JLabel lblGender, lblPencil, lblID, lblCollectionName;
	/**********************************************************************/
	
	/*                Messaggi d'errore             */
	private JLabel lblErrorCollection, lblErrorId;
	/************************************************/
	
	/*          Label che fungono da button         */
	private JLabel lblAddCollectionActive;
	private JLabel lblAddCollectionInactive;
	private JLabel lblCloseActive;
	private JLabel lblCloseInactive;
	/************************************************/

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CollectionFrame frame = new CollectionFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public CollectionFrame() {
		setUndecorated(true);
		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 259);
		contentPane = new JPanel();
		contentPane.setBackground(new java.awt.Color(44, 46, 60));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//																Nome Collezione
		textCollectionName = new JTextField();
		textCollectionName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			
				collectionName = textCollectionName.getText();
				/*
				 * Permette un messaggio d'errore immediato
				 *  nel caso in cui venga immesso il nome di una collezione già presente 
				 *  e viene visualizzata nella table.
				 *  Tale messaggio scompare se il field è vuoto e viene ricaricata la table.
				 *  
				 */
				if(CDAO.collezioneEsistente(textCollectionName.getText())!=null && !textCollectionName.getText().isEmpty()) {
					table.setModel(DbUtils.resultSetToTableModel(CDAO.collezioneEsistente(collectionName)));
						if(table.getRowCount()>0) {
							lblErrorCollection.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/alert-circle.png")));
							lblErrorCollection.setText("   Collezione Esistente");

						}
				
				}
				else {
					lblErrorCollection.setIcon(null);
					lblErrorCollection.setText("");
					aggiornaTabella();
				}
			}
		});
		textCollectionName.setForeground(Color.BLACK);
		textCollectionName.setBackground(new Color(144, 148, 155));
		textCollectionName.setBorder(new LineBorder(new Color(144, 148, 155)));
		textCollectionName.setBounds(23, 54, 186, 28);
		contentPane.add(textCollectionName);
		textCollectionName.setColumns(10);
		
		lblCollectionName = new JLabel("Collection Name");
		lblCollectionName.setForeground(Color.WHITE);
		lblCollectionName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCollectionName.setBounds(23, 20, 116, 23);
		contentPane.add(lblCollectionName);
		
		lblErrorCollection = new JLabel("");
		lblErrorCollection.setForeground(Color.RED);
		lblErrorCollection.setBounds(135, 26, 163, 14);
		contentPane.add(lblErrorCollection);
		
		
		//															Generazione ID
		lblPencil = new JLabel("");
		lblPencil.setToolTipText("Genera ID");
		lblPencil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*
				 * Verifica che i fields non siano vuoti altrimenti viene lanciata un'eccezione e mostrato un messaggio d'errore.
				 * Viene controllato se l'Id fornito è già presente visuallizzando un messaggio d'errore.
				 * 
				 */
				try {
						
						txtIdcollection.setText(CDAO.calcolaIdCollez(collectionName, sesso));
						id = txtIdcollection.getText();
						if(!id.isEmpty()) {
							txtIdcollection.setEditable(true);
						}
	
						if(CDAO.idEsistente(id) && !txtIdcollection.getText().isEmpty()) {
							lblErrorId.setText("    ID Esistente");
							lblErrorId.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/alert-circle.png")));						}
						else {
							lblErrorId.setText("");
							lblErrorId.setIcon(null);
						}
					
					
					}catch(NullPointerException str) {
						JOptionPane.showMessageDialog(null, "Uno o più campi sono vuoti","Campi vuoti",JOptionPane.ERROR_MESSAGE);
						txtIdcollection.setEditable(false);

					}

			}
		});
		lblPencil.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/pencil-regular-24.png")));
		lblPencil.setBounds(80, 187, 26, 23);
		contentPane.add(lblPencil);
		
		/*																	Sesso
		 *			
		 * 			Le azioni associate ai singoli radioButton permettono un controllo più specifico sull'esistenza di una collezione.
		 *			 Se la collezione immessa risultasse esistente, questo controllo per mette di capire quali siano i sessi di tale collezione
		 * 			Nel caso in cui il sesso di una collezione non fosse presente, il messaggio d'esistenza di tale collezione verrebbe annullato
		 * 			permettendone così l'aggiunta.
		 * 
		 */
		lblGender = new JLabel("Gender");
		lblGender.setForeground(new java.awt.Color(241, 242, 237));
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGender.setBounds(23, 92, 116, 14);
		contentPane.add(lblGender);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setForeground(new Color(241, 242, 237));
		rdbtnMale.setBackground(new Color(44, 46, 60));
		rdbtnMale.setEnabled(true);
		rdbtnMale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnMale.isSelected()) {
					sesso = "Maschio";
					table.setModel(DbUtils.resultSetToTableModel(CDAO.collezioneEsistente(collectionName, sesso)));

					if(!(table.getRowCount()>0)) {
						lblErrorCollection.setIcon(null);
						lblErrorCollection.setText("");
						}
					
				}
				
				
				
			}
		});
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMale.setBounds(20, 113, 71, 23);
		contentPane.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setForeground(new Color(241, 242, 237));
		rdbtnFemale.setBackground(new Color(44, 46, 60));
		rdbtnFemale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnFemale.isSelected()) {
					sesso = "Femmina";
					table.setModel(DbUtils.resultSetToTableModel(CDAO.collezioneEsistente(collectionName, sesso)));

					if(!(table.getRowCount()>0)) {
						lblErrorCollection.setIcon(null);
						lblErrorCollection.setText("");
						}
				}
				
			}
		});
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFemale.setBounds(102, 113, 65, 23);
		contentPane.add(rdbtnFemale);
		


		
		//																Tabella
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(230, 50, 199, 160);
		contentPane.add(scrollPane);
		
		table = new JTable();
		/*
		 * Modifico il colore dell'header della tabella
		 */
		JTableHeader tHeader = table.getTableHeader();
		tHeader.setBackground(new Color(44, 46, 60));
		tHeader.setForeground(new Color(241, 242, 237));
		table.addMouseListener(new MouseAdapter() {
			/*
			 * Permette la rimozione della collezione selezionata
			 * 
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=table.getSelectedRow();
				if(JOptionPane.showConfirmDialog(null, "Rimuovere Collezione?\nCosì facendo, tutti i modelli di questa collezione\nandranno persi!", "Attenzione", JOptionPane.YES_NO_OPTION )== JOptionPane.YES_OPTION) {
					 IDSelected=(table.getModel().getValueAt(row,0 ).toString());
					 CDAO.rimuoviCollezioneById(IDSelected);
					 JOptionPane.showMessageDialog(null, "Collezione Eliminata!");
					 aggiornaTabella();
					 
				}
			}
		});
		scrollPane.setViewportView(table);
		
		//																		ID
		lblID = new JLabel("ID");
		lblID.setForeground(new Color(241, 242, 237));
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID.setBounds(23, 143, 26, 17);
		contentPane.add(lblID);
		
		txtIdcollection = new JTextField();
		txtIdcollection.addKeyListener(new KeyAdapter() {
			/* 				Genera un messaggio d'errore se l'id presente nel field esiste già.
			 * 				Tale errore scompare se il field è vuoto								*/
			@Override
			public void keyReleased(KeyEvent arg0) {
				id = txtIdcollection.getText();
				if(CDAO.idEsistente(id) && !txtIdcollection.getText().isEmpty()) {
					lblErrorId.setText("    ID Esistente");
					lblErrorId.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/alert-circle.png")));
				}
				else {
					lblErrorId.setText("");
					lblErrorId.setIcon(null);
				}
			}
		});
		txtIdcollection.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtIdcollection.setForeground(Color.BLACK);
		txtIdcollection.setBackground(new java.awt.Color(144, 148, 155));
		txtIdcollection.setBorder(new LineBorder(new Color(144, 148, 155)));
		txtIdcollection.setEditable(false);
		txtIdcollection.setHorizontalAlignment(SwingConstants.LEFT);
		txtIdcollection.setBounds(20, 171, 57, 39);
		contentPane.add(txtIdcollection);
		txtIdcollection.setColumns(10);
		
		lblErrorId = new JLabel("");
		lblErrorId.setForeground(Color.RED);
		lblErrorId.setBounds(59, 146, 150, 14);
		contentPane.add(lblErrorId);
		
		/*																
		 * 																	Aggiungi Collezione
		 *								Gli eventi  mouseEntered e mouseExited servono ad animare i label affinchè possano sembrare un button. 
		 */
		lblAddCollectionActive = new JLabel("");
		lblAddCollectionActive.setToolTipText("Aggiungi Collezione");
		lblAddCollectionActive.setVisible(false);
		lblAddCollectionActive.addMouseListener(new MouseAdapter() {
			/*
			 *  Permette l'aggiunta della nuova collezione solo se i campi importanti non sono vuoti e non ci sono errori.
			 * In caso contrario provvederà alle opportune segnalazioni		
			 *
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
					try {
					campiImportanti.add(collectionName);
					campiImportanti.add(sesso);
					campiImportanti.add(id);
					errori.add(lblErrorCollection);
					errori.add(lblErrorId);
						
						if(!control.campiImportantiVuoti(campiImportanti)) {
							if(control.ciSonoErrori(errori)) {
								JOptionPane.showMessageDialog(null, "Sono presenti errori!","Errore", JOptionPane.ERROR_MESSAGE);
								
							
							}else {
								if(JOptionPane.showConfirmDialog(null, "Vuoi aggiungere questa collezione?","Aggiungi",JOptionPane.YES_NO_OPTION )== JOptionPane.YES_OPTION
										&& !CDAO.esisteCollezione(collectionName, sesso)) {
									CDAO.addCollection(collectionName, sesso, id);
									JOptionPane.showMessageDialog(null, "Nuova Collezione Aggiunta!");
									aggiornaTabella();
								}else {
									JOptionPane.showMessageDialog(null, "Collezione Esistente!","Errore", JOptionPane.ERROR_MESSAGE);

								}
														
							}
						}
					}catch(NullPointerException nullp) {
							JOptionPane.showMessageDialog(null, "Uno o piu campi sono vuoti!","Errore", JOptionPane.ERROR_MESSAGE);
					}
				}	
					
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblAddCollectionActive.setVisible(false);
				lblAddCollectionInactive.setVisible(true);
			}
		});
		lblAddCollectionActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddCollectionActive.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/plus-circle-regular-36-2.png")));
		lblAddCollectionActive.setBounds(152, 176, 57, 34);
		contentPane.add(lblAddCollectionActive);
		
		lblAddCollectionInactive = new JLabel("");
		lblAddCollectionInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAddCollectionActive.setVisible(true);
				lblAddCollectionInactive.setVisible(false);
			}
		});
		
		lblAddCollectionInactive.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/plus-circle-regular-36.png")));
		lblAddCollectionInactive.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddCollectionInactive.setBounds(152, 176, 57, 34);
		contentPane.add(lblAddCollectionInactive);
		
		lblCloseActive = new JLabel("");
		lblCloseActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
					if(JOptionPane.showConfirmDialog(null,"I dati andranno persi, chiudere comunque?","Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						closeFrame();
				
					}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCloseActive.setVisible(false);
				lblCloseInactive.setVisible(true);
			}
		});
		lblCloseActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseActive.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/x-regular-24-active.png")));
		lblCloseActive.setVisible(false);
		lblCloseActive.setBounds(423, 11, 26, 14);
		contentPane.add(lblCloseActive);
		
		lblCloseInactive = new JLabel("");
		lblCloseInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCloseInactive.setVisible(false);
				lblCloseActive.setVisible(true);
			}
		});
		lblCloseInactive.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseInactive.setIcon(new ImageIcon(CollectionFrame.class.getResource("/img/x-regular-24-inactive.png")));
		lblCloseInactive.setBounds(423, 11, 26, 14);
		contentPane.add(lblCloseInactive);
		

		
		aggiornaTabella();		
	}
	
	
	

	private void aggiornaTabella() {
		table.setModel(DbUtils.resultSetToTableModel(CDAO.mostraCollezioni()));

	}
	
	private void closeFrame() {
		
		CollectionFrame.this.dispose();

	}
}
