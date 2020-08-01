package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.ImpiegatoDAO;
import model.Impiegato;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLayeredPane;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * Classe principale per lo svolgimento delle azioni di scelta dei reparti.
 * Tramite un menù laterale sarà possibile infatti rendere visibili i panel che permetteranno azioni come:
 * <ul>
 * 	<li>Gestione della cassa
 * 	<li>Gestione Impiegati
 *  <li>Gestione Magazzino
 *  <li>Gestione Profilo
 *  <li>Gestione Fornitori
 * </ul> 
 * 
 * @author Antonio Di Nuzzo
 * @author Eleonora Di Santi
 *
 */
public class DashboardFrame extends JFrame {

	private JPanel contentPane;
	private JPanel lateralPanel;
	private ImpiegatoDAO IDAO;
	private Impiegato I;
	
	/*               I panel dei reparti                           */
	private PanelFornitore fornitori;
	private PanelImpiegati impiegati;
	private PanelMagazzino magazzino;
	private PanelCassa 	   cassa;
	public PanelProfile   profile;
	/***************************************************************/
	
	/*                Rappresentano il collegamento ai vari panel e le intestazioni/titoli                  */
	private JLabel lblImpiegati, lblSignOut, lblMagazzino, lblFornitori, lblCassa, lblProfile, lblDashboard;
	private JSeparator separator;
	/********************************************************************************************************/

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			String idOperatore ;
//			public void run() {
//				try {
//					DashboardFrame frame = new DashboardFrame(idOperatore);
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
	public DashboardFrame(String idOperatore) {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 905, 744);//100 100 900 608
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 148, 155));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		/*
		 * Tramite l'id dell'operatore fornito dalla fase di login
		 * crea un oggetto Impiegato che fornirà le opportune informazioni ai panel,
		 * bloccherà inoltre la possibilità di vedere tutti gli impiegati se ,
		 * l'impiegato loggato, non è un admin.
		 */
		IDAO  = new ImpiegatoDAO();
		I = new Impiegato( IDAO.MostraImpiegatobyId(idOperatore));
		/************************************************************************/
		
		lateralPanel = new JPanel();
		lateralPanel.setBackground(new Color(44, 46, 60));
		
		lblDashboard = new JLabel("Dashboard");
		lblDashboard.setOpaque(true);
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard.setForeground(new Color(241, 242, 237));
		lblDashboard.setBackground(new Color(124,127,136));
		lblDashboard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		separator = new JSeparator();
		
		//                                                                     Profilo
		lblProfile = new JLabel("Profilo");
		lblProfile.setIcon(new ImageIcon(DashboardFrame.class.getResource("/img/user.png")));
		lblProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfile.setForeground(new Color(144, 148, 155));
		lblProfile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProfile.addMouseListener(new PanelButtonMouseAdapter(lblProfile) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(profile);
			}
		});
		
		//																		Cassa
		lblCassa = new JLabel("Cassa");
		lblCassa.setIcon(new ImageIcon(DashboardFrame.class.getResource("/img/cash-register-2043338.png")));
		lblCassa.setHorizontalAlignment(SwingConstants.CENTER);
		lblCassa.setForeground(new Color(144, 148, 155));
		lblCassa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCassa.addMouseListener(new PanelButtonMouseAdapter(lblCassa) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(cassa);
			}
		});

		//																	Magazzino
		lblMagazzino = new JLabel("Magazzino");
		lblMagazzino.setIcon(new ImageIcon(DashboardFrame.class.getResource("/img/delivery-transport-warehouse-godown-storage-storehouse-8.png")));
		lblMagazzino.setHorizontalAlignment(SwingConstants.CENTER);
		lblMagazzino.setForeground(new Color(144, 148, 155));
		lblMagazzino.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMagazzino.addMouseListener(new PanelButtonMouseAdapter(lblMagazzino) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(magazzino);
			}
		});

		//																	Fornitori
		lblFornitori = new JLabel("Fornitori");
		lblFornitori.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornitori.setIcon(new ImageIcon(DashboardFrame.class.getResource("/img/icons8-fornitore-24.png")));
		lblFornitori.setForeground(new Color(144, 148, 155));
		lblFornitori.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFornitori.addMouseListener(new PanelButtonMouseAdapter(lblFornitori) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(fornitori);
			}
		});

		//																	Uscita
		lblSignOut = new JLabel("Uscita");
		lblSignOut.setIcon(new ImageIcon(DashboardFrame.class.getResource("/img/exit-regular-24.png")));
		lblSignOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignOut.setForeground(new Color(144, 148, 155));
		lblSignOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSignOut.addMouseListener(new PanelButtonMouseAdapter(lblSignOut) {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					if(JOptionPane.showConfirmDialog(null, "Sei sicuro di uscire?", "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					MainFrame login = new MainFrame();
					login.setVisible(true);
					DashboardFrame.this.dispose();
					}
				
			}
			
		});
		
		//																Impiegati
		lblImpiegati = new JLabel("Impiegati");
		lblImpiegati.setVisible(false);
		mostraPrivilegio(I);
		lblImpiegati.setIcon(new ImageIcon(DashboardFrame.class.getResource("/img/employee-24.png")));
		lblImpiegati.setHorizontalAlignment(SwingConstants.CENTER);
		lblImpiegati.setForeground(new Color(144, 148, 155));
		lblImpiegati.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImpiegati.addMouseListener(new PanelButtonMouseAdapter(lblImpiegati) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(impiegati);
			}
		});
		

		GroupLayout gl_LateralPanel = new GroupLayout(lateralPanel);
		gl_LateralPanel.setHorizontalGroup(
			gl_LateralPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_LateralPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_LateralPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_LateralPanel.createSequentialGroup()
							.addGroup(gl_LateralPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblMagazzino, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
								.addComponent(lblProfile, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
								.addComponent(lblCassa, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
							.addGap(8))
						.addGroup(gl_LateralPanel.createSequentialGroup()
							.addComponent(lblImpiegati, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_LateralPanel.createSequentialGroup()
							.addComponent(lblFornitori, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
							.addGap(8))))
				.addGroup(gl_LateralPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_LateralPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDashboard, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_LateralPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblSignOut, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_LateralPanel.setVerticalGroup(
			gl_LateralPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_LateralPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDashboard, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(112)
					.addComponent(lblProfile, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblCassa, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMagazzino, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblFornitori, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblImpiegati, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
					.addComponent(lblSignOut, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(25))
		);
		lateralPanel.setLayout(gl_LateralPanel);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lateralPanel, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 1646, Short.MAX_VALUE)
					.addGap(0))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(layeredPane)
						.addComponent(lateralPanel, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE))
					.addGap(0))
		);
		
		magazzino = new PanelMagazzino();
		magazzino.setVisible(false);
		profile = new PanelProfile(I);
		profile.setVisible(false);
		cassa = new PanelCassa(I.getIdOperatore());
		cassa.setVisible(false);
		fornitori = new PanelFornitore();
		fornitori.setVisible(false);
		impiegati = new PanelImpiegati(I.getCf());
		impiegati.setVisible(false);
		
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(fornitori, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(impiegati, GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
				.addComponent(magazzino, GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
				.addComponent(cassa, GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
				.addComponent(profile, GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(fornitori, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
				.addComponent(magazzino, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
				.addComponent(impiegati, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
				.addComponent(cassa, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
				.addComponent(profile, GroupLayout.PREFERRED_SIZE, 707, Short.MAX_VALUE)
		);
		layeredPane.setLayout(gl_layeredPane);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	/**
	 * Generalizza l'azione sui label del menu laterale.
	 * Facendo in modo che il label scelto abbia un panel associato,
	 * esso viene passato come parametro e reso visibile.
	 * 
	 * @param selectedPanel Panel da rendere visibile quindi utilizzabile
	 */
	private void menuClicked(JPanel selectedPanel) {
		fornitori.setVisible(false);
		magazzino.setVisible(false);
		cassa.setVisible(false);
		profile.setVisible(false);
		impiegati.setVisible(false);
		selectedPanel.setVisible(true);
		
	}
	
	/**
	 * Rende possibile l'animazione dei label evidenziati dal mouse
	 * usando due colorazioni differenti.
	 * 
	 * @author Antonio Di Nuzzo
	 *
	 */
	private class PanelButtonMouseAdapter extends MouseAdapter {
		JLabel label;
		/**
		 * Costruttore della classe prende come parametro un label
		 * 
		 * @param label Il label da animare
		 */
		public PanelButtonMouseAdapter(JLabel label) {
			this.label = label;
		}
		public void mouseEntered(MouseEvent e) {
			label.setForeground(new Color(241, 242, 237));

		}
		public void mouseExited(MouseEvent e) {
			label.setForeground(new Color(144, 148, 155));

		}
		public void mousePressed(MouseEvent e) {
			label.setForeground(new Color(120, 118, 119));

		}
		public void mouseReleased(MouseEvent e) {
			label.setBackground(new Color(144, 148, 155));

		}
	}
	/**
	 * Ricarica la pagina 
	 * @param username
	 */
	public void reload(String username) {
		I = new Impiegato( IDAO.MostraImpiegatobyId(username));

		profile = new PanelProfile(I);
	}
	
	private void mostraPrivilegio(Impiegato I) {
		try {
		if(I.getPrivilegio().equals("Admin")) {
			lblImpiegati.setVisible(true);
		}
		}catch(NullPointerException nulp) {}
	}
	
	
	
}
