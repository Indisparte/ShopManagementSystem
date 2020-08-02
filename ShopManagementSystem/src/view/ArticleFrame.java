package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.CollezioneDAO;
import dao.FornitoreDAO;

import dao.ModelloDAO;
import dao.TagliaColoreDAO;
import model.Collezione;
import model.Controllo;
import model.Fornitore;
import model.Modello;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.postgresql.util.PSQLException;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.border.LineBorder;
import java.awt.ComponentOrientation;
import java.awt.Image;

/**
 * Permette l'aggiunta di articoli con i dovuti controlli.
 * @author Antonio Di Nuzzo
 *
 */
public class ArticleFrame extends JFrame {

	private JPanel contentPane;
	private CollezioneDAO CDAO = new CollezioneDAO();
	private FornitoreDAO FDAO = new FornitoreDAO();
	private ModelloDAO MDAO = new ModelloDAO();
	private Controllo control = new Controllo();
	private TagliaColoreDAO TCDAO = new TagliaColoreDAO();
	
	/*							In questo blocco sono presenti i fields per l'inserimento dati, i loro titoli, le variabili,
	 * 							i messaggi d'errore.																			                                 */
	private JTextField txtEan, txtColor, txtPrice;
	private String size ="Sizes", ean = " ", descrizione = " ", colore = " ", fornitore = " ", collezione = " ", id = " ", IDModello= " ";
	private int giacenza, verifica;
	private double price = 00.00;
	private JTextPane txtpnDescription ;
	private JLabel lblPrice ,lblColor,lblDescription,lblArticle,lblVariety,lblModel, lblEan, lblContatoreCaratteri, lblEanError, lblErrorColor,
					lblErrorPrice;
	private JSpinner spinnerGiacenza ;
	/*********************************************************************************************************************************************************/
	
	/* In questo blocco ci sono gli array list per errori, collezioni, fornitori, campi importanti e taglie     */
	private JComboBox<String> comboBoxCollections = new JComboBox<String>();
	private JComboBox<String> comboBoxFornitori = new JComboBox<String>();
	private ArrayList<JLabel> errori = new ArrayList<JLabel>();
	private ArrayList<String> campiImportanti = new ArrayList<String>();
	private JComboBox<String> comboBoxSize = new JComboBox<String>();
	/************************************************************************************************************/
		
	/*          I due panel principali                   */	
	private JPanel panelVariety, panelModel;
	/*****************************************************/	
	
	/*								I label per la simulazione dei button e le variabili per l'imagazzinamento della foto                                    */
	private JLabel lblButtonAddDisactive,lblButtonAddActive, lblAddCollectionInactive, lblAddCollectionActive , lblCloseActive, lblCloseInactive,
				 lblSelectedImage, lblRemovePicActive, lblRemovePicInactive, lblSelectImageActive, lblLockClose, lblLockOpen,  lblSelectImageInactive;
	private byte [] photo;
	private String fileName;
	private JLabel lblRefreshActive;
	private JLabel lblRefreshInactive;
	/*********************************************************************************************************************************************************/	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ArticleFrame frame = new ArticleFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Creazione del frame.
	 */
	public ArticleFrame(Modello M) {
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 655, 465);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(44, 46, 60));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		//																	Panel principali
		panelModel = new JPanel();
		panelModel.setBorder(new LineBorder(new Color(120, 118, 119)));
		panelModel.setBackground(new Color(120, 118, 119));
		panelModel.setBounds(10, 114, 417, 314);
		contentPane.add(panelModel);
		panelModel.setLayout(null);
		
		lblModel = new JLabel("Model");
		lblModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel.setForeground(new Color(241, 242, 237));
		lblModel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblModel.setBounds(192, 89, 46, 14);
		contentPane.add(lblModel);
		
		panelVariety = new JPanel();
		panelVariety.setBorder(new LineBorder(new Color(120, 118, 119)));
		panelVariety.setBackground(new Color(120, 118, 119));
		panelVariety.setBounds(452, 84, 187, 222);
		contentPane.add(panelVariety);
		panelVariety.setLayout(null);
		
		lblVariety = new JLabel("Variety");
		lblVariety.setHorizontalAlignment(SwingConstants.CENTER);
		lblVariety.setForeground(new Color(241, 242, 237));
		lblVariety.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVariety.setBounds(518, 63, 71, 14);
		contentPane.add(lblVariety);
		
		
		 
		 //															Collezioni
		 comboBoxCollections = new JComboBox<String>();
		 comboBoxCollections.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		collezione = comboBoxCollections.getSelectedItem().toString();
		 	}
		 });
		 comboBoxCollections.setBackground(new Color(144, 148, 155));
		 comboBoxCollections.setForeground(Color.BLACK);
		 comboBoxCollections.setBounds(37, 38, 207, 26);
		 panelModel.add(comboBoxCollections);
		 aggiornaListaCollezione(M);
		 
		//														Fornitori 
		comboBoxFornitori.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				 fornitore = FDAO.getPIvaByNome((String)comboBoxFornitori.getSelectedItem());
		 	}
		 });
		 comboBoxFornitori.setForeground(Color.BLACK);
		 comboBoxFornitori.setBackground(new Color(144, 148, 155));
		 comboBoxFornitori.setBounds(10, 99, 145, 26);
		 panelModel.add(comboBoxFornitori);
		 comboBoxFornitori.addItem("Fornitori");
		 //aggiunge alla combobox, tutti i fornitori presenti
		 for(Fornitore f :FDAO.listaFornitori()) {
				
			 comboBoxFornitori.addItem(f.getNome());
				
			}
		 
		 //																Descrizione
		 txtpnDescription = new JTextPane();
		 txtpnDescription.setForeground(Color.BLACK);
		 txtpnDescription.addKeyListener(new KeyAdapter() {	
		 	@Override
		 	public void keyReleased(KeyEvent evt) {
		 			
			 		descrizione = txtpnDescription.getText();
			 		lblContatoreCaratteri.setText("- "+(25-descrizione.length())+" caratteri");
		 	}
		 	
		 	@Override
		 	public void keyTyped(KeyEvent evt) {
		 		if (txtpnDescription.getText().length() > 24) {
		 	        evt.consume();
		 	    }
		 	}
		 });
		 txtpnDescription.setBackground(new Color(144, 148, 155));
		 txtpnDescription.setBorder(new LineBorder(new Color(144, 148, 155) , 1, true));
		 txtpnDescription.setBounds(10, 232, 240, 57);
		 panelModel.add(txtpnDescription);
		 
		 lblDescription = new JLabel("Description");
		 lblDescription.setForeground(new Color(241, 242, 237) );
		 lblDescription.setBackground(new Color(255, 255, 255));
		 lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		 lblDescription.setBounds(10, 207, 77, 17);
		 panelModel.add(lblDescription);
		 
		 lblContatoreCaratteri = new JLabel("- 25 caratteri");
			lblContatoreCaratteri.setBounds(80, 210, 89, 14);
			panelModel.add(lblContatoreCaratteri);
			
		 
		 //															Prezzo
		 lblPrice = new JLabel("");
		 lblPrice.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/euro-regular-24.png")));
		 lblPrice.setBounds(10, 150, 29, 26);
		 panelModel.add(lblPrice);
		 	
	txtPrice = new JTextField();
	txtPrice.setForeground(Color.BLACK);
	txtPrice.setColumns(10);
	txtPrice.setBorder(new LineBorder(new Color(144, 148, 155)));
	txtPrice.setBackground(new Color(144, 148, 155));
	txtPrice.setBounds(37, 150, 69, 26);
	 txtPrice.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyTyped(KeyEvent evt) {
		 		char c =evt.getKeyChar();
		 		if(Character.isAlphabetic(c) || evt.isAltDown() ) {
		 			evt.consume();
		 		}
		 	}
		 	/*
		 	 * Controlla che il numero inserito sia double
		 	 * emettendo un messaggio d'errore .
		 	 */
	 	@Override
	 	public void keyReleased(KeyEvent evt) {
	 		try {
	 			
	 		price = Double.parseDouble(txtPrice.getText());
	 		lblErrorPrice.setText("");
				lblErrorPrice.setIcon(null);
	 		
	 		}catch(NumberFormatException e) {
	 			if(!txtPrice.getText().isEmpty()) {
	 				lblErrorPrice.setText("Prezzo invalido");
	 				lblErrorPrice.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/alert-circle.png")));

	 			}
	 			else {
	 				lblErrorPrice.setText("");
	 				lblErrorPrice.setIcon(null);

	 			}
	 		}
	 		
	 		
	 	}
	});
	panelModel.add(txtPrice);
	
	lblErrorPrice = new JLabel();
	lblErrorPrice.setForeground(Color.RED);
	lblErrorPrice.setBounds(37, 176, 132, 14);
	panelModel.add(lblErrorPrice);
	
	/*																	Aggiungi Collezione
	 *						In questo blocco sono presenti azioni per la trasformazione di label in button con il dovuto comportamento 
	 * 													all'ingresso, all'uscita e al click del mouse	
	 */
	lblAddCollectionActive = new JLabel("");
	lblAddCollectionActive.setToolTipText("Aggiungi Collezione");
	lblAddCollectionActive.setVisible(false);
	lblAddCollectionActive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			CollectionFrame collection = new CollectionFrame();
			collection.setVisible(true);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			lblAddCollectionActive.setVisible(false);
			lblAddCollectionInactive.setVisible(true);
		}
	});
	lblAddCollectionActive.setHorizontalAlignment(SwingConstants.CENTER);
	lblAddCollectionActive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/add-16-active.png")));
	lblAddCollectionActive.setBounds(245, 38, 24, 26);
	panelModel.add(lblAddCollectionActive);
	
	lblAddCollectionInactive = new JLabel();
	lblAddCollectionInactive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			lblAddCollectionActive.setVisible(true);
			lblAddCollectionInactive.setVisible(false);
		}
	});
	lblAddCollectionInactive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/add-16-Inactive.png")));
	lblAddCollectionInactive.setHorizontalAlignment(SwingConstants.CENTER);
	lblAddCollectionInactive.setBounds(245, 38, 24, 26);
	panelModel.add(lblAddCollectionInactive);
	
	lblSelectedImage = new JLabel("Foto");
	lblSelectedImage.setOpaque(true);
	lblSelectedImage.setHorizontalAlignment(SwingConstants.CENTER);
	lblSelectedImage.setBorder(new LineBorder(new Color(124,127,136)));
	lblSelectedImage.setBackground(new Color(124, 127, 136));
	lblSelectedImage.setBounds(269, 76, 138, 148);
	panelModel.add(lblSelectedImage);
	
	
	/*																	Lucchetto
	 *                       In questo blocco sono presenti azioni per la trasformazione di label in button con il dovuto comportamento 
	 * 													all'ingresso, all'uscita e al click del mouse	
	 */
	lblLockOpen = new JLabel();
	lblLockOpen.setBounds(393, 0, 24, 34);
	panelModel.add(lblLockOpen);
	lblLockOpen.addMouseListener(new MouseAdapter() {
	 	@Override
	 	public void mouseClicked(MouseEvent e) {
			verifica = JOptionPane.showConfirmDialog(null,"Hai aggiunto un capo con entrambi i panel sbloccati?", "Attenzione",JOptionPane.YES_NO_OPTION);
	 		lockModelPanel(true, M, verifica);		 		
	 	}
	 });
	lblLockOpen.setToolTipText("<html>Blocco contenuti pannello<br/>per aggiungere variet\u00E0 al medesimo capo</html>\"");
	lblLockOpen.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/lock-open-alt-regular-24.png")));
	
	lblLockClose = new JLabel();
	lblLockClose.setVisible(false);
	lblLockClose.setBounds(393, 0, 24, 34);
	panelModel.add(lblLockClose);
	
	lblLockClose.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/lock-alt-regular-24.png")));
	lblLockClose.setToolTipText("<html>Blocco contenuti pannello<br/>per aggiungere variet\u00E0 al medesimo capo</html>\"");
	lblLockClose.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			verifica = JOptionPane.showConfirmDialog(null,"Hai aggiunto un capo con entrambi i panel sbloccati?", "Attenzione",JOptionPane.YES_NO_OPTION);
			lockModelPanel(false, M,verifica);
		}
	});
	lblLockClose.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/lock-alt-regular-24.png")));
	lblLockClose.setToolTipText("<html>Blocco contenuti pannello<br/>per aggiungere variet\u00E0 al medesimo capo</html>\"");
	
	/*																	Selezione Immagine
	 *                       In questo blocco sono presenti azioni per la trasformazione di label in button con il dovuto comportamento 
	 * 													all'ingresso, all'uscita e al click del mouse	
	 */
	lblSelectImageActive = new JLabel("");
	lblSelectImageActive.setVisible(false);
	lblSelectImageActive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
					//Scelta del file
				String userDir = System.getProperty("user.home");
				JFileChooser chooser = new JFileChooser(userDir +"\\Desktop");
				FileFilter filter = new FileNameExtensionFilter("Images", "jpg","png");
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				/*
				 * Questo blocco ridimensiona l'immagine per adattarla al label
				 * 
				 */
				ImageIcon image = new ImageIcon(f.toString());
				Image myImage = image.getImage().getScaledInstance(lblSelectedImage.getWidth(), lblSelectedImage.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon myImageScaled = new ImageIcon(myImage);
				lblSelectedImage.setText("");
				lblSelectedImage.setIcon(myImageScaled);
				/***************************************************************/
				fileName = f.getAbsolutePath();
				try {
					File image2 = new File(fileName);
					FileInputStream fis = new FileInputStream(image2);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for(int readNum; (readNum=fis.read(buf))!=-1;) {
						bos.write(buf, 0, readNum);
					}
					photo = bos.toByteArray();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
					
				}
			}catch(NullPointerException nulp) {/*nessuna immagine scelta*/}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			lblSelectImageActive.setVisible(false);
			lblSelectImageInactive.setVisible(true);
		
		}
		
	});
	lblSelectImageActive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/addImageActive-24.png")));
	lblSelectImageActive.setBounds(239, 161, 30, 26);
	panelModel.add(lblSelectImageActive);
	
	lblRemovePicActive = new JLabel();
	lblRemovePicActive.setVisible(false);
	lblRemovePicActive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			    lblSelectedImage.setIcon(null);
			    lblSelectedImage.revalidate();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRemovePicActive.setVisible(false);
				lblRemovePicInactive.setVisible(true);
			}
		
	});
	lblRemovePicActive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/icons8-remove-image-24.png")));
	lblRemovePicActive.setBounds(239, 136, 30, 26);
	panelModel.add(lblRemovePicActive);
	
	lblRemovePicInactive = new JLabel();
	lblRemovePicInactive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			lblRemovePicInactive.setVisible(false);
			lblRemovePicActive.setVisible(true);
		}
	});
	lblRemovePicInactive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/icons8-remove-image-24-inactive.png")));
	lblRemovePicInactive.setBounds(239, 136, 30, 26);
	panelModel.add(lblRemovePicInactive);
	
	lblSelectImageInactive = new JLabel("");
	lblSelectImageInactive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent arg0) {
			lblSelectImageActive.setVisible(true);
			lblSelectImageInactive.setVisible(false);
		}
	});
	lblSelectImageInactive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/addImageInactive-24.png")));
	lblSelectImageInactive.setBounds(239, 161, 29, 23);
	panelModel.add(lblSelectImageInactive);
	
	lblRefreshActive = new JLabel("");
	lblRefreshActive.setToolTipText("Aggiorna Lista");
	lblRefreshActive.setVisible(false);
	lblRefreshActive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			aggiornaListaCollezione(M);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			lblRefreshActive.setVisible(false);
			lblRefreshInactive.setVisible(true);
		}
	});
	lblRefreshActive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/refresh-regular-24.png")));
	lblRefreshActive.setBounds(10, 38, 24, 26);
	panelModel.add(lblRefreshActive);
	
	lblRefreshInactive = new JLabel("");
	lblRefreshInactive.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			lblRefreshActive.setVisible(true);
			lblRefreshInactive.setVisible(false);
		}
	});
	lblRefreshInactive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/refresh-regular-24-2.png")));
	lblRefreshInactive.setBounds(10, 38, 24, 26);
	panelModel.add(lblRefreshInactive);
	
	
		//																	Titolo frame
		lblArticle = new JLabel("Article");
		lblArticle.setHorizontalTextPosition(SwingConstants.LEFT);
		lblArticle.setHorizontalAlignment(SwingConstants.LEFT);
		lblArticle.setForeground(new Color(124, 127, 136));
		lblArticle.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblArticle.setBounds(10, 3, 96, 49);
		contentPane.add(lblArticle);
		
		
		
		//																Taglie
		comboBoxSize.setForeground(Color.BLACK);
		comboBoxSize.setBackground(new java.awt.Color(144, 148, 155));
		comboBoxSize.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		comboBoxSize.addItem("Sizes");
		comboBoxSize.addItem("XS");
		comboBoxSize.addItem("S");
		comboBoxSize.addItem("M");
		comboBoxSize.addItem("L");
		comboBoxSize.addItem("XL");
		comboBoxSize.addItem("2XL");
		comboBoxSize.setBounds(10, 161, 76, 26);
		comboBoxSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			size = comboBoxSize.getSelectedItem().toString();
			}
		});
		panelVariety.add(comboBoxSize);
		
		//																	EAN
		lblEanError = new JLabel("");
		lblEanError.setForeground(Color.RED);
		lblEanError.setBounds(51, 17, 126, 14);
		panelVariety.add(lblEanError);
		
		lblEan = new JLabel("EAN");
		lblEan.setForeground(new Color(241, 242, 237));
		lblEan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEan.setBounds(10, 15, 46, 14);
		panelVariety.add(lblEan);
		
		lblEanError = new JLabel("");
		lblEanError.setForeground(Color.RED);
		lblEanError.setBounds(51, 17, 104, 14);
		panelVariety.add(lblEanError);
		
		txtEan = new JTextField();
		txtEan.setBounds(10, 40, 145, 26);
		panelVariety.add(txtEan);
		txtEan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c =evt.getKeyChar();
				if(!control.isTheCorrectNumberFormat(c, txtEan.getText(), 13)) {
					evt.consume();
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtEan.getText().length()<13 && !txtEan.getText().isEmpty()) {
					lblEanError.setText("Cifre Mancanti");
					lblEanError.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/alert-circle.png")));

				}
				else {
					lblEanError.setText("");
					lblEanError.setIcon(null);

					ean = txtEan.getText();
				}
				
			}
		});
		txtEan.setForeground(Color.BLACK);
		txtEan.setBackground(new java.awt.Color(144, 148, 155));
		txtEan.setBorder(new LineBorder(new Color(120, 118, 119)));
		txtEan.setColumns(10);

		//																Colore
		lblColor = new JLabel("Color");
		lblColor.setForeground(new Color(241, 242, 237));
		lblColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblColor.setBounds(10, 88, 54, 14);
		panelVariety.add(lblColor);
		
		lblErrorColor = new JLabel();
		lblErrorColor.setForeground(Color.RED);
		lblErrorColor.setBounds(52, 90, 103, 14);
		panelVariety.add(lblErrorColor);
		
		txtColor = new JTextField();
		txtColor.addKeyListener(new KeyAdapter() {
			/*Ammesse solo lettere*/
			@Override
			public void keyTyped(KeyEvent evt) {
				char c =evt.getKeyChar();
				if(!Character.isAlphabetic(c) || c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE || txtColor.getText().length()>21) {
					evt.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
					colore = txtColor.getText();
				
				}
			
		});
		txtColor.setForeground(Color.BLACK);
		txtColor.setBackground(new Color(144, 148, 155));
		txtColor.setBorder(new LineBorder(new Color(144, 148, 155)));
		txtColor.setBounds(10, 113, 122, 26);
		panelVariety.add(txtColor);
		txtColor.setColumns(10);
	
		//																	Giacenza
		spinnerGiacenza = new JSpinner();
		spinnerGiacenza.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		spinnerGiacenza.setToolTipText("Stock");
		spinnerGiacenza.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerGiacenza.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spinnerGiacenza.setForeground(Color.WHITE);
		spinnerGiacenza.setBackground(new Color(44, 46, 60));
		spinnerGiacenza.setBorder(new LineBorder(new Color(144, 148, 155)));
		spinnerGiacenza.setBounds(115, 160, 62, 26);
		spinnerGiacenza.getEditor().getComponent(0).setBackground( new java.awt.Color(144, 148, 155));//getComponent(0) mi da il text editor a cui cambio colore
		panelVariety.add(spinnerGiacenza);
		
		
		
		//																Aggiunta capo
		lblButtonAddDisactive = new JLabel();
		lblButtonAddDisactive.setToolTipText("Inserisci");
		lblButtonAddDisactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblButtonAddDisactive.setVisible(false);
				lblButtonAddActive.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblButtonAddDisactive.setVisible(true);
				lblButtonAddActive.setVisible(false);
			}
		});
		lblButtonAddDisactive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/plus-circle-regular-36.png")));
		lblButtonAddDisactive.setBounds(531, 352, 36, 42);
		contentPane.add(lblButtonAddDisactive);
		
		lblButtonAddActive = new JLabel();
		lblButtonAddActive.setVisible(false);
		lblButtonAddActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {	
				id = CDAO.showCollectionID(collezione.substring(0, collezione.indexOf(" ")), collezione.substring(collezione.indexOf(" ")+1)); 
				 giacenza = (Integer)spinnerGiacenza.getValue();
				 campiImportanti.add(colore);
				 campiImportanti.add(descrizione);
				 campiImportanti.add(ean);
				 campiImportanti.add(fornitore);
				 campiImportanti.add(size);
				 campiImportanti.add(id);
				 errori.add(lblEanError);
				 errori.add(lblErrorColor);
				 errori.add(lblErrorPrice);
				 
				 if(lblLockOpen.isVisible()) {
					 /*Se si è in questo blocco il pannello del modello è aperto quindi verranno presi i dati da entrambi i panel*/

					 IDModello = String.valueOf(MDAO.newModelloID());
					 if(!control.campiImportantiVuoti(campiImportanti)) {
						 
						 if(!control.ciSonoErrori(errori)) {
							
							 if(!MDAO.esisteModello(IDModello)) {
							 
								 MDAO.newModel(IDModello, id, price, fornitore, descrizione, photo);

							 if(TCDAO.specificheEsistenti(ean)) {
								 if(!TCDAO.specificheEsistenti(ean, colore, size)) {
						 		
									 JOptionPane.showMessageDialog(null, "EAN gia presente");
									
								 }else {
									 /*
									  * Se si è in questo blocco  si sta tentando di aggiungere un capo la cui varietà già esiste,
									  * allora si chiede se la giacenza vuole essere aggiornata
									  */
									 
									 if(JOptionPane.showConfirmDialog(null, "Si vuole aggiornare la giacenza di questo capo?","Articolo Esistente",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
										TCDAO.aumentaGiacenza(ean, giacenza);
									
						 			}
							 }else {
								 TCDAO.aggiungiTC(IDModello, ean, colore, size, giacenza);;
								 JOptionPane.showMessageDialog(null, "Articolo Aggiunto");
					 		
							 }

							 }else {
								 JOptionPane.showMessageDialog(null, "Questo Modello E' Gia' Presente\nse si vuole aggiungere più varietà al modello\nbloccare il panel","Errore", JOptionPane.ERROR_MESSAGE);
							 }
						}else {
							
							JOptionPane.showMessageDialog(null, "Sono presenti errori", "Errore", JOptionPane.ERROR_MESSAGE);
							
						}
					}else {
							
						JOptionPane.showMessageDialog(null, "Sono presenti campi vuoti");
							
						}
				}else {
					 /*
					  * Se si è in questo blocco il panel Model è stato chiuso per aggiungere più varietà al medesimo capo
					  * quindi sono stati bloccati l'inserimento dei dati del modello e verranno inseriti solo i dettagli riguardo la varietà.
					  *
					  */
					if(!control.campiImportantiVuoti(campiImportanti)){
						if(txtEan.getText().isEmpty() || txtColor.getText().isEmpty() || comboBoxSize.getSelectedItem().toString().contentEquals("Sizes") ) {
							JOptionPane.showMessageDialog(null, "Devono essere presenti delle specifiche");
						}else {
							if(TCDAO.specificheEsistenti(ean)) {
								 if(!TCDAO.specificheEsistenti(ean, colore, size)) {
								 		
									 JOptionPane.showMessageDialog(null, "EAN gia presente");
									
								 }else {
									 /*
									  * Se si è in questo blocco  si sta tentando di aggiungere un capo la cui varietà già esiste,
									  * allora si chiede se la giacenza vuole essere aggiornata
									  */
									 
									 if(JOptionPane.showConfirmDialog(null, "Si vuole aggiornare la giacenza di questo capo?","Articolo Esistente",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
										TCDAO.aumentaGiacenza(ean, giacenza);
									
						 			}
							 }else {
								
								 TCDAO.aggiungiTC(IDModello, ean, colore, size, giacenza);
								
								 
					 		
							 }
					   }
					}else {
						JOptionPane.showMessageDialog(null, "Sono presenti campi vuoti");

					}
				}
			}catch (NullPointerException nulp) {
				nulp.printStackTrace();
				JOptionPane.showMessageDialog(null, "Uno o più campi sono vuoti", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		
				 
		});
		lblButtonAddActive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/plus-circle-regular-36-2.png")));
		lblButtonAddActive.setBounds(531, 352, 36, 42);
		contentPane.add(lblButtonAddActive);
		
		//																	Chiusura frame
		lblCloseActive = new JLabel();
		lblCloseActive.setVisible(false);
		lblCloseActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
						
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
		lblCloseActive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/x-regular-24-active.png")));
		lblCloseActive.setBounds(631, 3, 24, 24);
		contentPane.add(lblCloseActive);
		
		lblCloseInactive = new JLabel();
		lblCloseInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
					lblCloseInactive.setVisible(false);
					lblCloseActive.setVisible(true);
				
			}
		});
		lblCloseInactive.setIcon(new ImageIcon(ArticleFrame.class.getResource("/img/x-regular-24-inactive.png")));
		lblCloseInactive.setBounds(631, 3, 24, 24);
		contentPane.add(lblCloseInactive);
	
		modelloEsistente(M);
	}
	/**
	 * Libera i fields del panel variety allo sblocco del panel model
	 */
	private void cleanVarietyFields() {
		txtEan.setText("");
		comboBoxSize.setSelectedItem(comboBoxSize.getItemAt(0));
		spinnerGiacenza.setValue(1);
		txtColor.setText("");
		
	}
	/**
	 * Rende non editabile il pannello Model e ricolora i suoi elementi,
	 * in modo da segnalare che quegli elementi sono irrevocabili.
	 * Tale metodo agisce anche sul panel variety in quanto al suo sblocco
	 * provvede ad eliminare i dati presenti del panel variety.
	 * 
	 * @param choose 
	 * <p><b>true</b> se si vuole bloccare il pannello<br/>
	 * <b>false</b> se si vuole sbloccare il pannello</p>
	 */
	private void lockModelPanel(boolean choose, Modello m, int verifica) {
		if(choose) {
			if(verifica == JOptionPane.YES_OPTION) {
			lblLockOpen.setVisible(false);
			panelModel.setBackground(new Color(44, 46, 60));
			comboBoxCollections.setEnabled(false);
			
			txtPrice.setEditable(false);
			txtPrice.setBackground(new Color(44, 46, 60));
			txtPrice.setBorder(new LineBorder(new Color(44, 46, 60)));
			
	
			comboBoxFornitori.setEnabled(false);
			
			txtpnDescription.setEditable(false);
			txtpnDescription.setBackground(new Color(44, 46, 60));
			txtpnDescription.setBorder(new LineBorder(new Color(44, 46, 60)));
			
			txtPrice.setBorder(new LineBorder(new Color(44, 46, 60)));
			
			lblSelectImageInactive.setVisible(false);
			lblSelectImageActive.setVisible(false);

			
			lblRemovePicInactive.setVisible(false);
	
			lblLockClose.setVisible(true);
			if(m != null) {
				lblLockClose.setVisible(false);

			}
			}
			
		}
		else {
			
			if(JOptionPane.showConfirmDialog(null,"Le modifiche apportate in questo panel genereranno un nuovo capo\n"
					+ "I valori del panel variety andranno persi\nProcedere con lo sblocco?", "Attenzione",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				cleanVarietyFields();
				
				lblLockOpen.setVisible(true);
				lblContatoreCaratteri.setText("- 25 caratteri");
				panelModel.setBackground(new Color(120, 118, 119));
				
				comboBoxCollections.setEnabled(true);
				comboBoxCollections.setSelectedItem(comboBoxCollections.getItemAt(0));

	
				txtPrice.setEditable(true);
				txtPrice.setBackground(new Color(144, 148, 155));
				txtPrice.setBorder(new LineBorder(new Color(144, 148, 155)));
				txtPrice.setText("");
				
				comboBoxFornitori.setEnabled(true);
				comboBoxFornitori.setSelectedItem(comboBoxFornitori.getItemAt(0));

				
				txtpnDescription.setEditable(true);
				txtpnDescription.setBackground(new Color(144, 148, 155));
				txtpnDescription.setBorder(new LineBorder(new Color(144, 148, 155)));
				txtpnDescription.setText("");
				
				lblSelectImageInactive.setVisible(true);
				lblSelectImageActive.setVisible(false);
				lblSelectedImage.setIcon(null);
				
				lblRemovePicInactive.setVisible(true);
		
				txtColor.setText("");

				lblLockClose.setVisible(false);
				
			}
			
	}
}
	private void closeFrame() {
	
		ArticleFrame.this.dispose();
	}
	
	/**
	 * Aggiorna la lista della collezione nell'eventualità siano state aggiunte/rimosse altre
	 * 
	 */
	private  void aggiornaListaCollezione(Modello m) {
		if(m == null) {
		try {
		 comboBoxCollections.removeAllItems();
		}catch(NullPointerException exc) {}
		 comboBoxCollections.addItem("---Choose Collections---");
		for(Collezione col : CDAO.listaCollezioni()) {
			
			 String collection = col.getNome()+" "+col.getSesso();
			 
				comboBoxCollections.addItem(collection);
				
			}
		}else {
			comboBoxCollections.addItem(m.getNome()+" "+m.getSesso());

		}
		
		}
		
	
	/**
	 * Rimodella il frame perchè non si vuole aggiungere un modello,
	 * bensì varietà ad un modello esistente
	 * @param m il modello
	 */
	private void modelloEsistente(Modello m) {
		if(m != null) {//se il frame ha ricevuto un modello
			comboBoxFornitori.setSelectedItem(FDAO.getNomeFornitorebyPiva(m.getIdFornitore()));
			txtPrice.setText(String.valueOf(m.getPrezzo()));
			txtpnDescription.setText(m.getDescrizione());
			adattaFoto(m.getImg());
			lblLockClose.setEnabled(false);
			lockModelPanel(true, m,0);
			IDModello = m.getIDModello();
			
			
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
			Image myImg = im.getScaledInstance(lblSelectedImage.getWidth(),lblSelectedImage.getHeight(), Image.SCALE_DEFAULT);
			ImageIcon newImage = new ImageIcon(myImg);
			lblSelectedImage.setText("");
			lblSelectedImage.setIcon(newImage);
		}catch(NullPointerException e) {
			lblSelectedImage.setText("Foto Non Presente");
		}
	}
}
