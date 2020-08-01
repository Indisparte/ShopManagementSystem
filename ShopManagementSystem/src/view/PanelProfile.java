package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import model.Controllo;
import model.Impiegato;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import dao.ImpiegatoDAO;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import java.awt.Component;
/**
 * Questa classe permette la presa visione del proprio profilo e la modifica di alcuni dati personali
 * 
 * @author Antonio Di Nuzzo
 *
 */
public class PanelProfile extends AbstractPanel {
	private ImpiegatoDAO IDAO = new ImpiegatoDAO();
	private String  psw;
	private Controllo control = new Controllo();
	private JTextPane textPaneNomeCognome ;
	private JPanel panelInformation = new JPanel();
	
	private JLabel lblErrorEmail = new JLabel(""), lblErrorUsername = new JLabel(""), lblErrorCell = new JLabel(""), lblCell,
			lblRefreshInactive , lblRefreshActive, lblProfileicon = new JLabel(""), lblHidePwd, lblShowPwd,  lblPrivilegio ;
	private ArrayList <JLabel> errori = new ArrayList<JLabel>();
	byte[] photo = null;
	
	private JPasswordField passwordField;
	private JTextField txtEmail, txtUsername, txtCell;
	
	private JPanel panelOrdiniFatti, panelTotOrdini;
	/**
	 * Create the panel.
	 */
	public PanelProfile(Impiegato I) {
		

		JPanel panel = new JPanel();
		setBounds(0, 0, 690, 652);
		panel.setBackground(new Color(144,148,155));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)//559
		);
		
		JLabel lblProfile = new JLabel("Profile");
		lblProfile.setHorizontalAlignment(SwingConstants.LEFT);
		lblProfile.setForeground(new Color(124,127,136));
		lblProfile.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(144, 148, 155));
		
		panelInformation.setBackground(new Color(241, 242, 237));
		lblProfileicon.setBounds(37, 6, 136, 113);
		lblProfileicon.setHorizontalAlignment(SwingConstants.CENTER);
		adattaFoto(I.getImg(), lblProfileicon);
		
		//																Nome Cognome
		textPaneNomeCognome = new JTextPane();
		textPaneNomeCognome.setAlignmentX(Component.LEFT_ALIGNMENT);
		textPaneNomeCognome.setBounds(10, 130, 185, 35);
		textPaneNomeCognome.setFont(new Font("Tahoma", Font.BOLD, 14));
		textPaneNomeCognome.setOpaque(false);
		textPaneNomeCognome.setText(I.getNome()+" "+I.getCognome());
		textPaneNomeCognome.setEditable(false);

		
		JPanel panelEditProfile = new JPanel();
		panelEditProfile.setBackground(new java.awt.Color(241, 242, 237));
		
		JLabel lblEditProfile = new JLabel("Edit Profile");
		lblEditProfile.setBounds(10, 10, 87, 24);
		lblEditProfile.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblEditProfile.setForeground(new java.awt.Color(120, 118, 119));
					panelEditProfile.setLayout(null);
					panelEditProfile.add(lblEditProfile);
					
					JLabel lblAccount = new JLabel("Account");
					lblAccount.setForeground(new Color(144, 148, 155));

					lblAccount.setBounds(10, 67, 64, 14);
					panelEditProfile.add(lblAccount);
					
					txtEmail = new JTextField();
					txtEmail.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							if(!control.correctEmail(txtEmail.getText()) && !txtEmail.getText().isEmpty() ) {
								lblErrorEmail.setText("Formato Email Non Valido");
								lblErrorEmail.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/alert-circle.png")));
							}
							else {
								lblErrorEmail.setText("");
								lblErrorEmail.setIcon(null);


							}
						}
					});
					txtEmail.setText((String) null);
					txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
					txtEmail.setForeground(new Color(144, 148, 155));
					txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtEmail.setColumns(10);
					txtEmail.setText(I.getEmail());
					panelEditProfile.add(txtEmail);
					
					JLabel lblEmail = new JLabel("Email");
					lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
					lblEmail.setForeground(new Color(144, 148, 155));
					lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblEmail.setBounds(10, 114, 64, 17);
					panelEditProfile.add(lblEmail);
					
					panelOrdiniFatti = new JPanel();
					
					panelTotOrdini = new JPanel();
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(10)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
										.addContainerGap())
									.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(lblProfile, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(panelInformation, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
												.addGap(47)
												.addComponent(panelEditProfile, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)))
										.addGap(10))))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(26)
								.addComponent(panelOrdiniFatti, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
								.addGap(30)
								.addComponent(panelTotOrdini, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(289, Short.MAX_VALUE))
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(11)
								.addComponent(lblProfile, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(11)
										.addComponent(panelInformation, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(12)
										.addComponent(panelEditProfile, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE)))
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(panelOrdiniFatti, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
									.addComponent(panelTotOrdini, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
								.addGap(443))
					);
					panelTotOrdini.setLayout(null);
					
					JLabel lblNewLabel = new JLabel("Totale Complessivo");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
					lblNewLabel.setBounds(10, 11, 162, 28);
					panelTotOrdini.add(lblNewLabel);
					
					//Avviene l'arrotondamento della cifra
					JLabel lblTotaleComplex = new JLabel(arrotonda(IDAO.totalefatturatoperimpiegato(I.getIdOperatore())));
					lblTotaleComplex.setHorizontalAlignment(SwingConstants.CENTER);
					lblTotaleComplex.setFont(new Font("Tahoma", Font.BOLD, 16));
					lblTotaleComplex.setBounds(10, 45, 162, 81);
					panelTotOrdini.add(lblTotaleComplex);
					panelOrdiniFatti.setLayout(null);
					
					JLabel lbltitoloOrdiniFatti = new JLabel("Ordini fatti");
					lbltitoloOrdiniFatti.setFont(new Font("Tahoma", Font.BOLD, 14));
					lbltitoloOrdiniFatti.setBounds(10, 11, 143, 27);
					panelOrdiniFatti.add(lbltitoloOrdiniFatti);
					
					JLabel lblContatoreOrdini = new JLabel( String.valueOf(IDAO.numOrdiniXImpiegato(I.getIdOperatore()))); 
					lblContatoreOrdini.setHorizontalAlignment(SwingConstants.CENTER);
					lblContatoreOrdini.setFont(new Font("Tahoma", Font.BOLD, 16));
					lblContatoreOrdini.setBounds(10, 44, 143, 76);
					panelOrdiniFatti.add(lblContatoreOrdini);
					panelInformation.setLayout(null);
					panelInformation.add(textPaneNomeCognome);
					panelInformation.add(lblProfileicon);
					
					JButton btnNewPic = new JButton("Foto");
					btnNewPic.setBounds(106, 237, 89, 23);
					btnNewPic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
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
								Image myImage = image.getImage().getScaledInstance(lblProfileicon.getWidth(), lblProfileicon.getHeight(), Image.SCALE_SMOOTH);
								ImageIcon myImageScaled = new ImageIcon(myImage);
								lblProfileicon.setIcon(null);
								lblProfileicon.revalidate();
								lblProfileicon.setIcon(myImageScaled);
								/***************************************************************/
								String fileName = f.getAbsolutePath();
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
								}catch(NullPointerException nulp) {}
						}
					});
					panelInformation.add(btnNewPic);
					
					lblPrivilegio = new JLabel("");
					lblPrivilegio.setBounds(10, 174, 120, 14);
					lblPrivilegio.setText(I.getPrivilegio());
					panelInformation.add(lblPrivilegio);
					
					JSeparator separatorAccount = new JSeparator();
					separatorAccount.setBounds(61, 79, 347, 2);
					panelEditProfile.add(separatorAccount);
					
					txtCell = new JTextField();
					txtCell.setForeground(new Color(144, 148, 155));
					txtCell.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtCell.setColumns(10);
					txtCell.setText(I.getCell());
					txtCell.addKeyListener(new KeyAdapter() {
						
						@Override
						public void keyTyped(KeyEvent evt) {
								char c =evt.getKeyChar();
								if(!control.isTheCorrectNumberFormat(c, txtCell.getText(), 10)) {
									evt.consume();
								}
						}
						
						@Override
						public void keyReleased(KeyEvent e) {
							if(txtCell.getText().length()<9 && !txtCell.getText().isEmpty()) {
								lblErrorCell.setText("Cifre mancanti");
								lblErrorCell.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/alert-circle.png")));
							}
							else {
								lblErrorCell.setText("");
								lblErrorCell.setIcon(null);

							}
						}
					});
					panelEditProfile.add(txtCell);
					
					lblCell = new JLabel();
					lblCell.setText("Cell");
					lblCell.setHorizontalAlignment(SwingConstants.LEFT);
					lblCell.setForeground(new Color(144, 148, 155));
					lblCell.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblCell.setBounds(10, 238, 64, 17);
					panelEditProfile.add(lblCell);
					
					txtUsername = new JTextField();
					txtUsername.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							if(IDAO.usernameAlredyExists(txtUsername.getText()) && !txtUsername.getText().isEmpty() && !txtUsername.getText().equals(I.getIdOperatore())) {
								lblErrorUsername.setText("Username già presente");
								lblErrorUsername.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/alert-circle.png")));
							}else {
								lblErrorUsername.setText("");
								lblErrorUsername.setIcon(null);

							}
						}
					});
					txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
					txtUsername.setForeground(new Color(144, 148, 155));
					txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtUsername.setColumns(10);
					txtUsername.setText(I.getIdOperatore());

					panelEditProfile.add(txtUsername);
					
					JLabel lblUsername = new JLabel("Username");
					lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
					lblUsername.setForeground(new Color(144, 148, 155));
					lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblUsername.setBounds(10, 176, 64, 17);
					panelEditProfile.add(lblUsername);
					
					lblErrorEmail.setForeground(Color.RED);
					lblErrorEmail.setBounds(61, 117, 219, 14);
					panelEditProfile.add(lblErrorEmail);
					
					lblErrorUsername.setForeground(Color.RED);
					lblErrorUsername.setBounds(84, 179, 219, 14);
					panelEditProfile.add(lblErrorUsername);
					
					lblErrorCell.setForeground(Color.RED);
					lblErrorCell.setBounds(42, 241, 219, 14);
					panelEditProfile.add(lblErrorCell);
					
					passwordField = new JPasswordField();
					char symbol = passwordField.getEchoChar();
					passwordField.setText(I.getPsw());
					psw = String.valueOf(passwordField.getPassword());
					passwordField.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							  psw = String.valueOf(passwordField.getPassword());

						}
					});
					passwordField.addMouseListener(new MouseAdapter() {
						/*
						 * Si vuole cambiare password, si richiede quella vecchia e se esatta, si abilita il field per la modifica
						 */
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!passwordField.isEditable()) {
								
								JPasswordField pf = new JPasswordField();
								if(JOptionPane.showConfirmDialog(null, pf, "Vecchia Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
										== JOptionPane.OK_OPTION) {
									  String oldPassword = new String(pf.getPassword());
									  if(oldPassword.equals(I.getPsw())) {
										  passwordField.setEditable(true);
										  lblShowPwd.setVisible(true);
									  }else {
										  JOptionPane.showMessageDialog(null, "Password errata!", "Errore", JOptionPane.ERROR_MESSAGE);
									  }
								}
								
						  }
						}
					});
					passwordField.setEditable(false);
					passwordField.setForeground(new Color(144, 148, 155));
					passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
					
					lblShowPwd = new JLabel("");
					lblShowPwd.setVisible(false);
					lblShowPwd.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							lblShowPwd.setVisible(false);
							lblHidePwd.setVisible(true);
							passwordField.setEchoChar((char)0);
						}
						
					});
					 lblShowPwd.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/show-regularActive-24.png")));
						lblShowPwd.setBounds(198, 332, 46, 24);
					lblHidePwd= new JLabel("");
					lblHidePwd.setVisible(false);
					lblHidePwd.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							lblShowPwd.setVisible(true);
							lblHidePwd.setVisible(false);
							passwordField.setEchoChar(symbol);
						}
					});
					lblHidePwd.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/hide-regularInactive24.png")));
					passwordField.setBounds(10, 332, 178, 24);
					panelEditProfile.add(passwordField);
					
					JLabel lblPassword = new JLabel();
					lblPassword.setText("Password");
					lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
					lblPassword.setForeground(new Color(144, 148, 155));
					lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblPassword.setBounds(10, 304, 64, 17);
					panelEditProfile.add(lblPassword);
					panelEditProfile.add(lblShowPwd);
					
					
					lblHidePwd.setBounds(198, 332, 46, 24);
					panelEditProfile.add(lblHidePwd);
					
					 lblRefreshActive = new JLabel("");
					 lblRefreshActive.setToolTipText("Aggiorna");
					 lblRefreshActive.setVisible(false);
					 lblRefreshActive.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseExited(MouseEvent e) {
								lblRefreshActive.setVisible(false);
								lblRefreshInactive.setVisible(true);
							}
					 	@Override
					 	public void mouseClicked(MouseEvent e) {
					 		errori.add(lblErrorCell);
							errori.add(lblErrorEmail);
							errori.add(lblErrorUsername);
							
							if(!(txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() || txtCell.getText().isEmpty() || 
									psw.isEmpty())) {
		          				if(!control.ciSonoErrori(errori)) {
		          					if(JOptionPane.showConfirmDialog(null, "Sei sicuro di aggiornare i dati?", "Aggiornamento.",JOptionPane.YES_NO_OPTION)
		          							== JOptionPane.YES_OPTION) {
		          						IDAO.ModificaDatiPersonali(txtUsername.getText().trim(), txtEmail.getText().trim(), txtCell.getText().trim(),
		          								psw, photo, I.getCf());
		          						Impiegato I = new Impiegato();
		          						I = IDAO.MostraImpiegatobyId(txtUsername.getText());
		          						PanelProfile profile = new PanelProfile(I);
		          						JOptionPane.showMessageDialog(null, "Modificato"); 
		          					}
		          				}else {
		          					JOptionPane.showMessageDialog(null, "Sono presenti errori!", "Errore", JOptionPane.ERROR_MESSAGE);
		          				}
		          			}
		          			else {
		          				IDAO.ModificaDatiPersonali(I.getIdOperatore(), I.getEmail(), I.getCell(), I.getPsw(), photo, I.getCf());
		              				loadPage(I);

		          				}
					 	}
						});
					lblRefreshActive.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/refres24.png")));
					lblRefreshActive.setBounds(384, 374, 24, 24);
					panelEditProfile.add(lblRefreshActive);
					
					lblRefreshInactive = new JLabel("");
					lblRefreshInactive.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							lblRefreshActive.setVisible(true);
							lblRefreshInactive.setVisible(false);
						}
					});
					lblRefreshInactive.setIcon(new ImageIcon(PanelProfile.class.getResource("/img/refresh-regularinactive.png")));
					lblRefreshInactive.setBounds(384, 374, 24, 24);
					panelEditProfile.add(lblRefreshInactive);
					
					txtEmail.setBounds(11, 138, 200, 24);
					panelEditProfile.add(txtEmail);
					txtEmail.setColumns(10);
					
					txtUsername.setBounds(10, 203, 201, 24);
					panelEditProfile.add(txtUsername);
					txtUsername.setColumns(10);
					
					txtCell.setBounds(10, 262, 136, 24);
					panelEditProfile.add(txtCell);
					txtCell.setColumns(10);
					panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
	}
	private void loadPage(Impiegato I) {
		//Carico l'immagine
		try {
			adattaFoto(I.getImg(), lblProfileicon);
			photo = I.getImg();
		}catch(NullPointerException e) {
			lblProfileicon.setText("Foto Non Presente");
		}
		
		//setto i dati del pannello edit
		txtUsername.setText(I.getIdOperatore());
		txtCell.setText(I.getCell());
		txtEmail.setText(I.getEmail());
		passwordField.setText(I.getPsw());
		lblErrorCell.setText("");
		lblErrorCell.setIcon(null);
		lblErrorEmail.setText("");
		lblErrorEmail.setIcon(null);
		lblErrorUsername.setText("");
		lblErrorUsername.setIcon(null);
		
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