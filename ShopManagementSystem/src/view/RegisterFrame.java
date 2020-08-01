package view;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import com.toedter.calendar.JDateChooser;
import dao.ImpiegatoDAO;
import model.Controllo;

import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Permette di registrarsi al servizio come membro
 * 
 * @author Antonio Di Nuzzo
 *
 */
public class RegisterFrame extends JFrame {
	private JPanel contentPane;
	private ImpiegatoDAO IDAO = new ImpiegatoDAO();
	private Controllo control = new Controllo();
    private String sex, username, strDate, cf, email, cell, firstName, lastName, psw;
    
	/*                Questo blocco di variabili contiene 
	 *                   i field per l'immissione dei dati                                  */
	private JTextField txtFirstname, txtLastName, txtCf, txtUsername,txtEmail, txtCell;
	private JPasswordField pwdPassword;
	private JDateChooser dateChooser = new JDateChooser();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnMale ,rdbtnFemale;
	/****************************************************************************************/
	
	
	/*Questo blocco contiene due array che immagazzinano rispettivamente messaggi d'errore
	 *  e campi importanti, utili ad un metodo per controllare se ci sono errori attivi */
	private ArrayList <JLabel> errori = new ArrayList<JLabel>();
	/*****************************************************************************************/
	
	
	/*           Questo blocco di variabili riguarda le intestazioni/titoli                 */
	private JLabel lblBirthday, lblUsername, lblEmail, lblPassword, lblCell, lblFirstName,
	lblCF, lblLastName, lblSesso, lblAccount, lblPersonal, lblTitle, lblSelectedImage;
	private JSeparator separator, separator2;
	/****************************************************************************************/
	
	/*          Questo blocco di variabili riguarda i messaggi d'errore                     */        
	private JLabel lblErrorMessageEmail     = new JLabel("");
	private JLabel lblErrorMessageUsername  = new JLabel("");
	private JLabel lblErrorDate 		    = new JLabel("");
	private JLabel lblErrorMessageCf        = new JLabel("");
	private JLabel lblErrorMessageCell      = new JLabel("");
	private JLabel lblErrorMessageLastName  = new JLabel("");
	private JLabel lblErrorMessageFirstName = new JLabel("");
 	/****************************************************************************************/

	/*          Questo blocco di variabili riguarda la selezione dell'immagine,
	 *          è presente il label in cui verrà mostrata l'immagine 
	 *          e i rispettivi label per la selezione/eliminazione                          */
	private JLabel lblSelectImageActive;
	private JLabel lblSelectImageInactive;
	byte[] photo = null;
	private String fileName = null;
	private JLabel lblRemovePicActive;
	private JLabel lblRemovePicInactive;
	/****************************************************************************************/
	
	/*          Questo blocco contiene i label che simulano l'azione di un button
	 *          nello specifico:
	 *                            - chiusura del frame
	 * 							  - hide/show password
	 *							  - Sign Up button                                          */
	private JLabel lblSignUPActive ;
	private JLabel lblSignUPInactive;
	private JLabel lblHidepsw = new JLabel("") ;
    private JLabel lblShowpsw = new JLabel("");
    private JLabel lblCloseActive;
	private JLabel lblCloseInactive;
	/****************************************************************************************/
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegisterFrame frame = new RegisterFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Creazione del Frame
	 */
	
	public RegisterFrame() {
		setUndecorated(true);
		setResizable(false);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 600);
		
		contentPane = new JPanel();
		contentPane.setBackground(new java.awt.Color(44, 46, 60));
		contentPane.setForeground(new java.awt.Color(144, 148, 155));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//                                                             Intestazione del frame
		lblTitle = new JLabel("Member registration");
		lblTitle.setOpaque(true);
		lblTitle.setBackground(new Color(124,127,136));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblTitle.setForeground(new java.awt.Color(241, 242, 237));
		lblTitle.setBounds(21, 28, 394, 52);
		contentPane.add(lblTitle);
		
		//                                                              Sezione dati personali
		lblPersonal = new JLabel("Personal");
		lblPersonal.setForeground(new java.awt.Color(144, 148, 155));
		lblPersonal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPersonal.setBounds(21, 91, 44, 16);
		contentPane.add(lblPersonal);
		
		separator = new JSeparator();
		separator.setForeground(new java.awt.Color(144, 148, 155));
		separator.setBounds(71, 101, 343, 2);
		contentPane.add(separator);
		
		//                                                                       Nome
		lblFirstName= new JLabel("First name");
		lblFirstName.setForeground(new java.awt.Color(241, 242, 237));
		lblFirstName.setHorizontalAlignment(SwingConstants.LEFT);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFirstName.setBounds(20, 118, 71, 14);
		contentPane.add(lblFirstName);
		
		txtFirstname = new JTextField();
		txtFirstname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				firstName = txtFirstname.getText();
			}
		});
		txtFirstname.addMouseListener(new TextMouseAdapter(txtFirstname) {});
		txtFirstname.setBounds(21, 140, 180, 28);
		contentPane.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		// Cognome
		lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(new java.awt.Color(241, 242, 237));
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(234, 121, 86, 14);
		contentPane.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				lastName = txtLastName.getText();
			}
		});
		txtLastName.addMouseListener(new TextMouseAdapter(txtLastName) {});
		txtLastName.setBounds(234, 140, 180, 28);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);
		
		//                                                                   Codice Fiscale
		lblCF = new JLabel("CF");
		lblCF.setForeground(new java.awt.Color(241, 242, 237));
		lblCF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCF.setBounds(21, 198, 55, 16);
		contentPane.add(lblCF);
		
		txtCf = new JTextField();
		txtCf.addMouseListener(new TextMouseAdapter(txtCf) {});
		txtCf.addKeyListener(new KeyAdapter() {
			
			/*
			 * Non processa i dati che non rappresentano un codice fiscale
			 *  o superano la lunghezza fissata
			 */
			@Override
			public void keyTyped(KeyEvent evt) {
				 cf = txtCf.getText().toUpperCase();
					char c =evt.getKeyChar();
					if(cf.length()>15) {
						evt.consume();
					}
			}
			/*
			 * Rende visibile un messaggio d'errore ad ogni evento da tastiera
			 * finquando i dati immessi non raggiungono il numero di cifre ammesso.
			 * Tale messaggio d'errore non è visibile se non ci sono dati presenti nel field
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				 cf = txtCf.getText().toUpperCase();

				 if( !cf.isEmpty() && cf.length()<16) {
					 lblErrorMessageCf.setText("Caratteri mancanti");
				 }else {
					 lblErrorMessageCf.setText("");

				 }
			}
		});
		txtCf.setText("");
		txtCf.setBounds(21, 220, 169, 28);
		contentPane.add(txtCf);
		txtCf.setColumns(10);
		
		//                                                                  	 Sesso
		lblSesso = new JLabel("Gender");
		lblSesso.setForeground(new Color(241, 242, 237));
		lblSesso.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSesso.setBounds(21, 277, 55, 16);
		contentPane.add(lblSesso);
		
		//                                                                      Maschio
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBackground(new Color(44, 46, 60));
		rdbtnMale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex = "Maschio";
			}
		});
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setForeground(new Color(241, 242, 237));
		rdbtnMale.setBounds(20, 300, 71, 18);
		contentPane.add(rdbtnMale);
		
		//																		Femmina
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBackground(new Color(44, 46, 60));
		rdbtnFemale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sex  = "Femmina";
			}
		});
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setForeground(new Color(241, 242, 237));
		rdbtnFemale.setBounds(104, 300, 86, 18);
		contentPane.add(rdbtnFemale);
		
		//																	 Sezione Account
		lblAccount = new JLabel("Account");
		lblAccount.setForeground(new Color(144, 148, 155));
		lblAccount.setBounds(21, 381, 55, 9);
		contentPane.add(lblAccount);
		
		separator2 = new JSeparator();
		separator2.setForeground(new Color(144, 148, 155));
		separator2.setBounds(72, 388, 343, 2);
		contentPane.add(separator2);
		
		//																		Compleanno
		lblBirthday = new JLabel("BirthDay");
		lblBirthday.setForeground(new Color(241, 242, 237));
		lblBirthday.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblBirthday.setBounds(234, 198, 55, 16);
		contentPane.add(lblBirthday);
		
		dateChooser.setBounds(234, 220, 180, 28);
		dateChooser.setDateFormatString("dd/MM/yyyy");
		
		contentPane.add(dateChooser);
		
		//																		Username
		lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(241, 242, 237));
		lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblUsername.setBounds(20, 401, 71, 16);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.addMouseListener(new TextMouseAdapter(txtUsername) {});
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				username = txtUsername.getText().trim();
				 /*
				  * Questo blocco if garantisce la presenza immediata di un messaggio d'errore
				  * alla scelta di un username già esistente o troppo breve.
				  * 
				  */
				if(IDAO.usernameAlredyExists(username) && !txtUsername.getText().isEmpty()) {
					lblErrorMessageUsername.setText("Username già utilizzato");
				}else if(username.length()<3) {
					lblErrorMessageUsername.setText("Inserire più di 2 caratteri");

				}
				else {
					lblErrorMessageUsername.setText("");

				}
			}
		});
		txtUsername.setBounds(21, 428, 180, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		//																	  Email
		lblEmail= new JLabel("Email");
		lblEmail.setForeground(new Color(241, 242, 237));
		lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblEmail.setBounds(234, 401, 55, 16);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.addMouseListener(new TextMouseAdapter(txtEmail) {});
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				email = txtEmail.getText().trim();
				 /*
				  * Questo blocco if garantisce la segnalazione di un formato Email incorretto
				  * 
				  */
				if(!control.correctEmail(email) && !email.isEmpty()) {
					lblErrorMessageEmail.setText("Invalid Email Format");
				}
				else {
					lblErrorMessageEmail.setText("");

				}
			}
		});
		
		txtEmail.setText("");
		txtEmail.setBounds(235, 428, 180, 28);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		//																	Password
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(241, 242, 237));
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblPassword.setBounds(21, 485, 105, 16);
		contentPane.add(lblPassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.addMouseListener(new TextMouseAdapter(pwdPassword) {});
		char symbol = pwdPassword.getEchoChar();
		pwdPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				  psw = String.valueOf(pwdPassword.getPassword());

			}
		});
		pwdPassword.setBounds(21, 512, 190, 28);
		contentPane.add(pwdPassword);
		
		/*
		 * Utilizzando dei label con icone diverse simula un button,
		 * permettendo di rendere visibile/celata ,
		 * la password immessa dall'utente
		 * 
		 */
		lblHidepsw.setVisible(false);
		lblShowpsw.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblShowpsw.setVisible(false);
				lblHidepsw.setVisible(true);
				pwdPassword.setEchoChar((char)0);
			}
		});
		lblShowpsw.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowpsw.setForeground(new Color(0, 0, 0));
		lblShowpsw.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/show-regular-24.png")));
		lblShowpsw.setBounds(210, 512, 37, 28);
		contentPane.add(lblShowpsw);
		
		lblHidepsw.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblShowpsw.setVisible(true);
				lblHidepsw.setVisible(false);
				pwdPassword.setEchoChar(symbol);
			}
		});
		lblHidepsw.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/hide-regular-24.png")));
		lblHidepsw.setHorizontalAlignment(SwingConstants.CENTER);
		lblHidepsw.setForeground(Color.BLACK);
		lblHidepsw.setBounds(210, 512, 37, 28);
		contentPane.add(lblHidepsw);
		
		//																	Cell
		lblCell = new JLabel("Cell");
		lblCell.setForeground(new Color(241, 242, 237));
		lblCell.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCell.setBounds(301, 485, 55, 16);
		contentPane.add(lblCell);
		
		txtCell = new JTextField();
		txtCell.addMouseListener(new TextMouseAdapter(txtCell) {});
		txtCell.addKeyListener(new KeyAdapter() {
			@Override
			/*
			 *Non processa i dati  diversi dai numeri e aventi lunghezza superiore a quella fissata 
			 *
			 */
			public void keyTyped(KeyEvent evt) {
				 cell = txtCell.getText();
				char c =evt.getKeyChar();
				if(!control.isTheCorrectNumberFormat(c, cell,10)) {
					evt.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				cell = txtCell.getText();
				/*
				 * Garantisce un messaggio d'errore immediato
				 * quando la lumghezza del numero di cellulare 
				 * è inferiore a quella standard
				 * 
				 */
				if(!cell.isEmpty() && cell.length()<10) {
					lblErrorMessageCell.setText("Cifre Mancanti");
				}
				else {
					lblErrorMessageCell.setText("");

				}
			}
		});
		txtCell.setBounds(301, 512, 112, 28);
		contentPane.add(txtCell);
		txtCell.setColumns(10);
		
		//																	Sign Up
		lblSignUPActive = new JLabel("");
		lblSignUPActive.setVisible(false);
		lblSignUPActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					strDate  = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
					//strDate = dateChooser.getDate().toString();

				}
				catch(NullPointerException nul) {
					JOptionPane.showMessageDialog(null, "Formato data non valido");
				}
				try {
				if(!(txtFirstname.getText().isEmpty() || txtLastName.getText().isEmpty() || txtCf.getText().isEmpty() || txtEmail.getText().isEmpty()
						|| txtUsername.getText().isEmpty() || pwdPassword.getPassword().toString().isEmpty() || strDate.isEmpty()) || sex.isEmpty()){
					 
					if(!control.ciSonoErrori(errori)) {								
						 
						if(!IDAO.EsisteImpiegato(cf)) {								
	
							IDAO.AggiungiImpiegato(firstName, lastName, strDate, cf, username, email, cell, sex, psw, photo, "");
							 
							 MainFrame mainFrame = new MainFrame();
							 mainFrame.setVisible(true);
							 JOptionPane.showMessageDialog(null, "Il tuo Profilo è stato creato.\nSei stato reindirizzato alla pagina di LogIn.");
							 RegisterFrame.this.dispose();
							
							
						 //l'impiegato risulta registrato
						 }else {
							 MainFrame mainFrame = new MainFrame();
							 mainFrame.setVisible(true);
							 JOptionPane.showMessageDialog(null, "Il tuo profilo è già registrato?","Info",JOptionPane.INFORMATION_MESSAGE);
							 RegisterFrame.this.dispose();
							 
						 }
						//Sono presenti errori
					 }else {
						 JOptionPane.showMessageDialog(null, "Sono presenti errori!", "Attenzione", JOptionPane.ERROR_MESSAGE);
					 }
				}
				else{
					JOptionPane.showMessageDialog(null, "Uno o piu campi sono vuoti", "Attenzione", JOptionPane.ERROR_MESSAGE);
				}
			}catch(NullPointerException nulp) {
				JOptionPane.showMessageDialog(null, "Uno o piu campi sono vuoti", "Attenzione", JOptionPane.ERROR_MESSAGE);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSignUPActive.setVisible(false);
				lblSignUPInactive.setVisible(true);
			}
		});
		lblSignUPActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUPActive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/SignUpInactive.png")));
		lblSignUPActive.setBounds(161, 551, 128, 49);
		contentPane.add(lblSignUPActive);
		
		lblSignUPInactive = new JLabel("");
		lblSignUPInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSignUPInactive.setVisible(false);
				lblSignUPActive.setVisible(true);
			}
		});
		lblSignUPInactive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/SignUpActive.png")));
		lblSignUPInactive.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUPInactive.setBounds(161, 551, 128, 49);
		contentPane.add(lblSignUPInactive);
		
		//														Label per la segnalazione degli errori
		lblErrorMessageEmail.setForeground(Color.RED);
		lblErrorMessageEmail.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblErrorMessageEmail.setBounds(234, 458, 180, 16);
		errori.add(lblErrorMessageEmail);
		contentPane.add(lblErrorMessageEmail);
		
		lblErrorMessageUsername.setForeground(Color.RED);
		lblErrorMessageUsername.setBounds(21, 460, 169, 14);
		errori.add(lblErrorMessageUsername);
		contentPane.add(lblErrorMessageUsername);
		
		lblErrorDate.setForeground(Color.RED);
		lblErrorDate.setBounds(234, 254, 180, 14);
		errori.add(lblErrorDate);
		contentPane.add(lblErrorDate);
		
		lblErrorMessageCf.setForeground(Color.RED);
		lblErrorMessageCf.setBounds(21, 254, 169, 14);
		errori.add(lblErrorMessageCf);
		contentPane.add(lblErrorMessageCf);
		
		//																Selezione Immagine
		lblSelectedImage = new JLabel("Foto");
		lblSelectedImage.setOpaque(true);
		lblSelectedImage.setBorder(new LineBorder(new Color(124,127,136)));
		lblSelectedImage.setBackground(new Color(124,127,136));
		lblSelectedImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedImage.setBounds(301, 277, 114, 100);
		contentPane.add(lblSelectedImage);
		
		lblSelectImageActive = new JLabel("");
		lblSelectImageActive.setVisible(false);
		lblSelectImageActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					String userDir = System.getProperty("user.home");
					JFileChooser chooser = new JFileChooser(userDir+"\\Desktop");
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
					 lblSelectedImage.setIcon(null);
					 lblSelectedImage.revalidate();
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
						fis.close();
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					
					}
					
				}catch(NullPointerException nulp) {}
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSelectImageActive.setVisible(false);
				lblSelectImageInactive.setVisible(true);
			}
		});
		lblSelectImageActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectImageActive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/addImageActive-24.png")));
		lblSelectImageActive.setBounds(258, 349, 31, 28);
		contentPane.add(lblSelectImageActive);
		
		//															Messaggi d'errore
		lblErrorMessageCell.setForeground(Color.RED);
		lblErrorMessageCell.setBounds(301, 546, 114, 14);
		errori.add(lblErrorMessageCell);
		contentPane.add(lblErrorMessageCell);
		
		lblErrorMessageFirstName.setForeground(Color.RED);
		errori.add(lblErrorMessageFirstName);
		lblErrorMessageFirstName.setBounds(10, 173, 169, 14);
		contentPane.add(lblErrorMessageFirstName);
		
		lblErrorMessageLastName.setForeground(Color.RED);
		errori.add(lblErrorMessageLastName);
		lblErrorMessageLastName.setBounds(234, 173, 169, 14);
		contentPane.add(lblErrorMessageLastName);
		
		lblCloseActive = new JLabel("");
		lblCloseActive.setVisible(false);
		lblCloseActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//se i campi importanti non sono vuoti, chiede conferma riguardo l'azione di chiusura
				
					
					if(JOptionPane.showConfirmDialog(null,"I dati andranno persi, chiudere comunque?","Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
		lblCloseActive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/x-regular-24-active.png")));
		lblCloseActive.setBounds(406, 0, 31, 28);
		contentPane.add(lblCloseActive);
		
		lblCloseInactive = new JLabel("");
		lblCloseInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCloseInactive.setVisible(false);
				lblCloseActive.setVisible(true);
			}
		});
		lblCloseInactive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/x-regular-24-inactive.png")));
		lblCloseInactive.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseInactive.setBounds(406, 0, 31, 28);
		contentPane.add(lblCloseInactive);
		
		lblSelectImageInactive = new JLabel("");
		lblSelectImageInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSelectImageActive.setVisible(true);
				lblSelectImageInactive.setVisible(false);
			}
		});
		lblSelectImageInactive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/addImageInactive-24.png")));
		lblSelectImageInactive.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectImageInactive.setBounds(258, 349, 31, 28);
		contentPane.add(lblSelectImageInactive);
		
		lblRemovePicActive = new JLabel("");
		lblRemovePicActive.setVisible(false);
		lblRemovePicActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    lblSelectedImage.setIcon(null);
			    lblSelectedImage.revalidate();
			    lblSelectedImage.setText("Foto");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRemovePicActive.setVisible(false);
				lblRemovePicInactive.setVisible(true);
			}
		});
		lblRemovePicActive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/icons8-remove-image-24.png")));
		lblRemovePicActive.setBounds(258, 320, 31, 28);
		contentPane.add(lblRemovePicActive);
		
		lblRemovePicInactive = new JLabel("");
		lblRemovePicInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRemovePicInactive.setVisible(false);
				lblRemovePicActive.setVisible(true);
			}
		});
		lblRemovePicInactive.setIcon(new ImageIcon(RegisterFrame.class.getResource("/img/icons8-remove-image-24-inactive.png")));
		lblRemovePicInactive.setBounds(258, 320, 31, 28);
		contentPane.add(lblRemovePicInactive);
		
		
	}
	
	private void closeFrame() {
		MainFrame main = new MainFrame();
		main.setVisible(true);
		RegisterFrame.this.dispose();
	}
}
