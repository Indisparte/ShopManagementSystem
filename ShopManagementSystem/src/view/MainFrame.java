package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ImpiegatoDAO;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.border.MatteBorder;
/**
 * Rappresenta il frame d'accesso al programma di gestione del negozio
 * Con questo frame le opzioni dono 2:
 * <ol>
 *	 <li> Accedere alla Dashboard
 *	 <li> Isciversi come Membro
 * </ol>
 * 
 * @author Antonio Di Nuzzo
 *
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField UsernameField;
	private JPasswordField pwdPassword;
	private ImpiegatoDAO IDAO = new ImpiegatoDAO();
	private JLabel lblErrorMessage;
	private JLabel lblSignInActive ;
	private JLabel lblSignUpInactive ;
	private JLabel lblSignInInactive ;
	private JLabel lblSignUpActive;
	private JLabel lblUserIcon;
	private char symbol;
	private JLabel lblCloseActive;
	private JLabel lblCloseInactive;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mainFrame = new MainFrame();
					mainFrame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creazione del frame.
	 */
	public MainFrame() {
		setUndecorated(true);
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 354, 434);
		contentPane = new JPanel();
		contentPane.setBackground(new java.awt.Color(44, 46, 60));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Username
		UsernameField = new JTextField();
		UsernameField.addMouseListener(new TextMouseAdapter(UsernameField) {});
		UsernameField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		UsernameField.setBackground(new java.awt.Color(44, 46, 60));
		UsernameField.setForeground(new java.awt.Color(241, 242, 237));
		UsernameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		UsernameField.setSelectedTextColor(Color.WHITE);
		UsernameField.setBounds(78, 134, 199, 37);
		UsernameField.setColumns(10);
		
		//Password
		pwdPassword = new JPasswordField();
		symbol = pwdPassword.getEchoChar();//variabile che immagazzina il simbolo per nascondere la psw
		pwdPassword.addMouseListener(new TextMouseAdapter(pwdPassword) {});
		pwdPassword.setSelectedTextColor(Color.WHITE);
		pwdPassword.setOpaque(true);
		pwdPassword.setForeground(new java.awt.Color(241, 242, 237));
		pwdPassword.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		pwdPassword.setBounds(78, 213, 199, 37);
		pwdPassword.setBackground(new java.awt.Color(44, 46, 60));
		
		//Messaggio d'errore 
		lblErrorMessage = new JLabel("     Invalid Username or Password");
		lblErrorMessage.setVisible(false);
		lblErrorMessage.setIcon(new ImageIcon(MainFrame.class.getResource("/img/alert-circle.png")));
		lblErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblErrorMessage.setBounds(42, 290, 262, 45);
		lblErrorMessage.setForeground(new Color(220, 20, 60));
		lblErrorMessage.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.setLayout(null);
		
		lblUserIcon = new JLabel("");
		lblUserIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserIcon.setIcon(new ImageIcon(MainFrame.class.getResource("/img/user1.png")));
		lblUserIcon.setBounds(42, 134, 35, 37);
		contentPane.add(lblUserIcon);
		contentPane.add(UsernameField);
		contentPane.add(pwdPassword);
		contentPane.add(lblErrorMessage);
		
		JLabel lblMembersLogin = new JLabel("Members Log In");
		lblMembersLogin.setOpaque(true);
		lblMembersLogin.setBackground(new Color(144, 148, 155));
		lblMembersLogin.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblMembersLogin.setForeground(new java.awt.Color(241, 242, 237));
		lblMembersLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMembersLogin.setBounds(10, 33, 336, 62);
		contentPane.add(lblMembersLogin);
		
		// Check box per rendere visibile o meno la password
		JCheckBox chckbxShowPsw = new JCheckBox("Show Password");
		chckbxShowPsw.setBorder(null);
		chckbxShowPsw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( chckbxShowPsw.isSelected()) {
					pwdPassword.setEchoChar((char)0);
				}
				else{
					pwdPassword.setEchoChar(symbol);
				}
			}
			
		});
		chckbxShowPsw.setFont(new Font("SansSerif", Font.PLAIN, 12));
		chckbxShowPsw.setBackground(new java.awt.Color(44, 46, 60));
		chckbxShowPsw.setForeground(new java.awt.Color(241, 242, 237));
		chckbxShowPsw.setIcon(new ImageIcon(MainFrame.class.getResource("/img/square.png")));
		chckbxShowPsw.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/img/check-square.png")));
		chckbxShowPsw.setBounds(113, 261, 113, 18);
		contentPane.add(chckbxShowPsw);
		
		// Sign In Inattivo
		lblSignInInactive = new JLabel("");
		lblSignInInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblSignInActive.setVisible(true);
				lblSignInInactive.setVisible(false);
			}
		});
		lblSignInInactive.setIcon(new ImageIcon(MainFrame.class.getResource("/img/buttonActive.png")));
		lblSignInInactive.setBounds(68, 346, 113, 49);
		contentPane.add(lblSignInInactive);
		
		//Sign In Attivo
		lblSignInActive = new JLabel("");
		lblSignInActive.setVisible(false);
		lblSignInActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblSignInInactive.setVisible(true);
				lblSignInActive.setVisible(false);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(IDAO.verificaCredenziali(getUsername(), getPassword())) {
					//invoco la dashboard
					DashboardFrame Dash = new DashboardFrame(getUsername());
					Dash.setVisible(true);
					MainFrame.this.dispose();
				}
				else {
					lblErrorMessage.setVisible(true);
				}
			}			
		});
		lblSignInActive.setIcon(new ImageIcon(MainFrame.class.getResource("/img/button1.png")));
		lblSignInActive.setBounds(68, 346, 113, 49);
		contentPane.add(lblSignInActive);
		
		//Sign Up Attivo
		lblSignUpActive = new JLabel("");
		lblSignUpActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblSignUpActive.setVisible(false);
				lblSignUpInactive.setVisible(true);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//invoco RegisterFrame
				RegisterFrame register = new RegisterFrame();
				register.setVisible(true);
				MainFrame.this.dispose();
			}
		});
		lblSignUpActive.setVisible(false);
		lblSignUpActive.setIcon(new ImageIcon(MainFrame.class.getResource("/img/SignUpActive.png")));
		lblSignUpActive.setBounds(159, 346, 118, 49);
		contentPane.add(lblSignUpActive);
		
		//Sign Up Inattivo
		lblSignUpInactive = new JLabel("");
		lblSignUpInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSignUpActive.setVisible(true);
				lblSignUpInactive.setVisible(false);
			}
		});
		lblSignUpInactive.setIcon(new ImageIcon(MainFrame.class.getResource("/img/SignUpInactive.png")));
		lblSignUpInactive.setBounds(159, 346, 118, 49);
		contentPane.add(lblSignUpInactive);
		
		
		JLabel lblPasswordIcon = new JLabel("");
		lblPasswordIcon.setIcon(new ImageIcon(MainFrame.class.getResource("/img/key-regular-24.png")));
		lblPasswordIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordIcon.setBounds(42, 213, 35, 37);
		contentPane.add(lblPasswordIcon);
		
		lblCloseActive = new JLabel("");
		lblCloseActive.setVisible(false);
		lblCloseActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainFrame.this.dispose();

			}
			@Override
			public void mouseExited(MouseEvent e) {
				
					lblCloseActive.setVisible(false);
					lblCloseInactive.setVisible(true);
				
			}
		});
		lblCloseActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseActive.setIcon(new ImageIcon(MainFrame.class.getResource("/img/x-regular-24-active.png")));
		lblCloseActive.setBounds(323, 0, 31, 28);
		contentPane.add(lblCloseActive);
		
		lblCloseInactive = new JLabel("");
		lblCloseInactive.setIcon(new ImageIcon(MainFrame.class.getResource("/img/x-regular-24-inactive.png")));
		lblCloseInactive.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCloseInactive.setVisible(false);
				lblCloseActive.setVisible(true);
			}
		});
		lblCloseInactive.setBounds(323, 0, 31, 28);
		contentPane.add(lblCloseInactive);
		
		
	}
		
		
	private String getUsername() {
		return UsernameField.getText();
	}
	private String getPassword() {
		return String.valueOf(pwdPassword.getPassword());
	}
	
	
	
}
