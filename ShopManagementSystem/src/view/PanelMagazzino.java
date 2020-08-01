package view;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.SwingConstants;

import dao.CollezioneDAO;
import dao.FornitoreDAO;
import dao.ModelloDAO;
import dao.TagliaColoreDAO;
import javax.swing.GroupLayout.Alignment;
import model.Modello;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Visualizza tutti i capi presenti nel magazzino
 *  dando la possibilit‡ di aumentarne la giacenza o eliminare il capo selezionato
 *  
 * @author Antonio Di Nuzzo
 * @author Eleonora Di Santi
 *
 */
public class PanelMagazzino extends AbstractPanel {

	private JTable table;
	
	/*            I panel principali       */
	private JPanel panel, panelShowArticle;
	/***************************************/
	private JComboBox<String> comboBoxCERCA;
	private JComboBox<String> comboBoxColor  = new JComboBox<String>() ;
	private CollezioneDAO CDAO = new CollezioneDAO ();
	private ModelloDAO MDAO = new ModelloDAO();
	private Modello M;
	private FornitoreDAO FDAO = new FornitoreDAO();
	private TagliaColoreDAO TCDAO = new TagliaColoreDAO();
	
	/*           I label e i field per l'inserimento e la scelta dei dati    */
	private JLabel lblXS, lblS, lblM, lblL, lblXL, lbl2XL, lbl3XL, lblPrice, lblColori, lblFoto, lblDeleteActive, lblMagazzino, lblRefreshActive,
					lblRefreshInactive;
	private JLabel lblSconto = new JLabel("");

	private JTextField	txtcerca;
	private JTextPane textPaneDescrizione;
	//private String EAN, IDM;
	private JSpinner spinnerGiacenza;
	private JLabel lblDeleteInactive,lblAggiungiArticoloInactive;
	private JLabel lblAggiungiArticoloActive;
	private JLabel lblAggArticleActive;
	private JLabel lblAggArticleInactive;
	/*************************************************************************/
	private TableColorCellRenderer renderer = new TableColorCellRenderer();

	
	/**
	 * Create the panel.
	 */
	public PanelMagazzino() {
		panel = new JPanel();
		setBounds(0, 0, 806, 559);
		panel.setBackground(new java.awt.Color(144,148,155));
		
		panelShowArticle = new JPanel();
		panelShowArticle.setVisible(false);
		panelShowArticle.setDoubleBuffered(false);
		panelShowArticle.setBackground(new Color(124,127,136));
		
		lblPrice = new JLabel("");
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		lblColori = new JLabel("");
		lblColori.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		lblXS =  new JLabel("XS");
		lblXS.setForeground(new Color(144, 148, 155));
		lblXS.setBorder(new LineBorder(new Color(144, 148, 155)));
		lblXS.setPreferredSize(new Dimension(14, 14));
		lblXS.setHorizontalAlignment(SwingConstants.CENTER);
		lblXS.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lblS = new JLabel("S");
		lblS.setForeground(new Color(144, 148, 155));
		lblS.setPreferredSize(new Dimension(14, 14));
		lblS.setHorizontalAlignment(SwingConstants.CENTER);
		lblS.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblS.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		lblM = new JLabel("M");
		lblM.setForeground(new Color(144, 148, 155));
		lblM.setPreferredSize(new Dimension(14, 14));
		lblM.setHorizontalAlignment(SwingConstants.CENTER);
		lblM.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblM.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		lblL = new JLabel("L");
		lblL.setForeground(new Color(144, 148, 155));
		lblL.setPreferredSize(new Dimension(14, 14));
		lblL.setHorizontalAlignment(SwingConstants.CENTER);
		lblL.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblL.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		lblXL = new JLabel("XL");
		lblXL.setForeground(new Color(144, 148, 155));
		lblXL.setPreferredSize(new Dimension(14, 14));
		lblXL.setHorizontalAlignment(SwingConstants.CENTER);
		lblXL.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblXL.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		lbl2XL = new JLabel("2XL");
		lbl2XL.setForeground(new Color(144, 148, 155));
		lbl2XL.setPreferredSize(new Dimension(14, 14));
		lbl2XL.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2XL.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl2XL.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		lbl3XL = new JLabel("3XL");
		lbl3XL.setForeground(new Color(144, 148, 155));
		lbl3XL.setPreferredSize(new Dimension(14, 14));
		lbl3XL.setHorizontalAlignment(SwingConstants.CENTER);
		lbl3XL.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl3XL.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		lblFoto = new JLabel("");
		lblFoto.setBorder(null);
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblDeleteActive = new JLabel("");
		lblDeleteActive.setVisible(false);
		lblDeleteActive.addMouseListener(new MouseAdapter() {
			//Eliminazione articolo
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Sei sicuro di eliminare l'articolo selezionato?", "Delete",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					TCDAO.rimuoviTC(M.getEan());
					
					if(! TCDAO.esistonoVariet‡DiQuestoModello(M.getIDModello())) {
						MDAO.rimuoviModellobyIDM(M.getIDModello());
					}else {
						/**
						 * Questo blocco else controlla se lo sconto deve esserci o meno, data l'eliminazione di un capo,
						 * controlla se le giaceze superano la soglia, altrimenti procede a rimuovere lo sconto e a ripristinare il prezzo
						 */
						if(!TCDAO.giacenzaTotSuperaSoglia(M.getIDModello())) {
							if(MDAO.presenteSconto(M.getIDModello())==1) {//sconto presente
								MDAO.setSconto(M.getIDModello(), false);//rimuovi sconto
								MDAO.setPrice(M.getIDModello(), M.getPrezzo()*2);//ripristina prezzo
							}
						}
					}
					refreshTable1();
				}

			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDeleteActive.setVisible(false);
				lblDeleteInactive.setVisible(true);
			}
		});
		lblDeleteActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteActive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/trash-alt-regular-24.png")));
		
		
		lblAggiungiArticoloInactive = new JLabel("");
		lblAggiungiArticoloInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAggiungiArticoloActive.setVisible(true);
				lblAggiungiArticoloInactive.setVisible(false);
			}
		});
		lblAggiungiArticoloInactive.setToolTipText("Aumenta Giacenza");
		lblAggiungiArticoloInactive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/appendiabiti-24-inactive.png")));
		
		lblDeleteInactive = new JLabel("");
		lblDeleteInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblDeleteActive.setVisible(true);
				lblDeleteInactive.setVisible(false);
			}
		});
		lblDeleteInactive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/trashInactive24.png")));
		lblDeleteInactive.setHorizontalAlignment(SwingConstants.CENTER);
		
		textPaneDescrizione = new JTextPane();
		textPaneDescrizione.setEditable(false);
		textPaneDescrizione.setBackground(new Color(124,127,136));
		textPaneDescrizione.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		lblMagazzino = new JLabel("Magazzino");
		lblMagazzino.setHorizontalAlignment(SwingConstants.LEFT);
		lblMagazzino.setForeground(new java.awt.Color(124,127,136));
		lblMagazzino.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new java.awt.Color(144, 148, 155));
		
		comboBoxCERCA = new JComboBox<String>();
		comboBoxCERCA.setModel(new DefaultComboBoxModel<String>(new String[] {"EAN", "Colore", "Taglia", "Nome", "Sesso"}));
		comboBoxCERCA.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBoxCERCA.setBackground(new Color(0, 128, 128));
		
		txtcerca = new JTextField();
		txtcerca.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				String select =(String)comboBoxCERCA.getSelectedItem();
				String query = "select * from \"Negoziodiabbigliamento\".\"Magazzino\" where \""+select+"\"=?";
			refreshTable(query, table);
			}
		});
		txtcerca.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setDefaultRenderer(Object.class, renderer);
		/*
		 * Modifico il colore dell'header della tabella
		 */
		JTableHeader tHeader = table.getTableHeader();
		tHeader.setBackground(new Color(44, 46, 60));
		tHeader.setForeground(new Color(241, 242, 237));
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
	
					panelShowArticle.setVisible(true);
					celoTaglie();
					 lblFoto.setIcon(null);
					 lblFoto.revalidate();
					comboBoxColor.removeAllItems();
					int row1=table.getSelectedRow();
					//creo un oggetto modello
					M = creaModello(row1);
		 
					 textPaneDescrizione.setText(M.getDescrizione());
					lblPrice.setText("Ä "+M.getPrezzo());
					adattaFoto(M.getImg(), lblFoto);
					
					//Colori disponibili del capo messi  nel combobox
					for(String colore : TCDAO.getColoreByModello(M.getIDModello())) {
						comboBoxColor.addItem(colore);
					}
					lblColori.setText(String.valueOf(comboBoxColor.getItemCount())+" Colori");
					//taglie disponibili del capo 
					
					taglieDisponibili(TCDAO.getTaglieByIDModello(M.getIDModello()),M.getIDModello());
	
					if(M.getSconto()==1) {
						lblSconto.setVisible(true);
						
					}
				} catch (Exception m) {
					m.printStackTrace();
				}
				
			
			}
			
			
		});
		
		
		 lblRefreshActive = new JLabel("");
		lblRefreshActive.setVisible(false);
		lblRefreshActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshTable1();
				txtcerca.setText("");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRefreshActive.setVisible(false);
				lblRefreshInactive.setVisible(true);
			}
			
		});
		lblRefreshActive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/refresh-regular-24.png")));
		
		lblRefreshInactive = new JLabel("");
		lblRefreshInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRefreshInactive.setVisible(false);
				lblRefreshActive.setVisible(true);
			}
			
		});
		lblRefreshInactive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/refresh-regular-24-2.png")));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 806, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		lblAggiungiArticoloActive = new JLabel("");
		lblAggiungiArticoloActive.setVisible(false);
		lblAggiungiArticoloActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				spinnerGiacenza = new JSpinner();
				spinnerGiacenza.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
				spinnerGiacenza.setFont(new Font("Tahoma", Font.PLAIN, 12));
				spinnerGiacenza.setForeground(Color.WHITE);
				spinnerGiacenza.setBackground(new Color(44, 46, 60));
				if (JOptionPane.showOptionDialog(null, spinnerGiacenza, "Capi da aggiungere", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) 
						== JOptionPane.OK_OPTION)
				{
					TCDAO.aumentaGiacenza(M.getEan(), (Integer)spinnerGiacenza.getValue());
					JOptionPane.showMessageDialog(null, "Capi aggiunti");
					refreshTable1();
				} 
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAggiungiArticoloActive.setVisible(false);
				lblAggiungiArticoloInactive.setVisible(true);
				
			}
		});
		lblAggiungiArticoloActive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/appendiabiti-24-Active.png")));
		lblAggiungiArticoloActive.setToolTipText("Aumenta Giacenza");
		
		lblAggArticleActive = new JLabel("");
		lblAggArticleActive.setVisible(false);
		lblAggArticleActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArticleFrame articleFrame = new ArticleFrame(null);
				articleFrame.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAggArticleActive.setVisible(false);
				lblAggArticleInactive.setVisible(true);
			}
		});
		lblAggArticleActive.setToolTipText("Aggiungi Nuovo Capo");
		lblAggArticleActive.setHorizontalAlignment(SwingConstants.CENTER);
		lblAggArticleActive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/plus-circle-regular-36-2.png")));
		
		lblAggArticleInactive = new JLabel("");
		lblAggArticleInactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAggArticleActive.setVisible(true);
				lblAggArticleInactive.setVisible(false);
			}
		});
		lblAggArticleInactive.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/plus-circleInac.png")));
		lblAggArticleInactive.setToolTipText("Aggiungi Nuovo Capo");
		lblAggArticleInactive.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMagazzino, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panelShowArticle, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(54)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblAggArticleInactive, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAggArticleActive, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
									.addGap(10)
									.addComponent(comboBoxCERCA, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(txtcerca, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRefreshActive)
										.addComponent(lblRefreshInactive)))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
							.addGap(1)))
					.addGap(10))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblMagazzino, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panelShowArticle, GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(88)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAggArticleInactive)
								.addComponent(lblAggArticleActive)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(8)
									.addComponent(comboBoxCERCA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(8)
									.addComponent(txtcerca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(8)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRefreshActive)
										.addComponent(lblRefreshInactive))))
							.addGap(11)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE))))
		);
		
		JButton btnNewButton = new JButton("+ Variet\u00E0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArticleFrame articleFrame = new ArticleFrame(M);
				articleFrame.setVisible(true);
			}
		});
		lblSconto.setToolTipText("- 50%");
		
		lblSconto.setVisible(false);
		lblSconto.setHorizontalAlignment(SwingConstants.CENTER);
		lblSconto.setIcon(new ImageIcon(PanelMagazzino.class.getResource("/img/icons8-sconto-32.png")));
		GroupLayout gl_panelShowArticle = new GroupLayout(panelShowArticle);
		gl_panelShowArticle.setHorizontalGroup(
			gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelShowArticle.createSequentialGroup()
					.addGap(10)
					.addComponent(lblFoto, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panelShowArticle.createSequentialGroup()
							.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
								.addComponent(textPaneDescrizione, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelShowArticle.createSequentialGroup()
									.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSconto, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
							.addGap(6))
						.addGroup(gl_panelShowArticle.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(12))))
				.addGroup(gl_panelShowArticle.createSequentialGroup()
					.addGap(10)
					.addComponent(lblColori, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(comboBoxColor, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelShowArticle.createSequentialGroup()
					.addGap(20)
					.addComponent(lblXS, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblS, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblM, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(lblL, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblXL, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(lbl2XL, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(lbl3XL, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelShowArticle.createSequentialGroup()
					.addGap(274)
					.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelShowArticle.createSequentialGroup()
							.addGap(29)
							.addComponent(lblDeleteInactive, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelShowArticle.createSequentialGroup()
							.addGap(29)
							.addComponent(lblDeleteActive, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblAggiungiArticoloActive, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAggiungiArticoloInactive, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panelShowArticle.setVerticalGroup(
			gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelShowArticle.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblFoto, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelShowArticle.createSequentialGroup()
							.addComponent(textPaneDescrizione, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSconto, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
							.addComponent(btnNewButton)))
					.addGap(54)
					.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
						.addComponent(lblColori, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxColor, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
						.addComponent(lblXS, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblS, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblM, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblL, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblXL, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl2XL, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl3XL, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addGroup(gl_panelShowArticle.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDeleteInactive, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDeleteActive, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAggiungiArticoloActive)
						.addComponent(lblAggiungiArticoloInactive)))
		);
		panelShowArticle.setLayout(gl_panelShowArticle);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
		
		 refreshTable1();
	}
	/**
	 * Permette di evidenziare i riquadri delle taglie disponibili di un capo.
	 * Se una taglia Ë disponibile, il riquadro apposito si evidenzia.
	 * 
	 * @param taglie Array list delle taglie disponibili per uno specifico capo
	 * @param class1 
	 * <b>true</b> La giacenza Ë inferiore alla soglia fissata, il label della taglia associato si illumina di rosso
	 * <b>false</b>la giacenza supera la soglia, il label Ë evidenziato in nero.
	 */
	private void taglieDisponibili(ArrayList<String> taglie, String IDmodello) {
		for(String taglia : taglie) {
			switch(taglia) {
			case "XS":
				evidenziaTagliaDisponibile(lblXS, IDmodello);

				break;
			case"S":
				evidenziaTagliaDisponibile(lblS, IDmodello);
				
				break;
			case"M":
				evidenziaTagliaDisponibile(lblM, IDmodello);

				break;
			case "L":
				evidenziaTagliaDisponibile(lblL, IDmodello);

				break;
			case"XL":
				evidenziaTagliaDisponibile(lblXL, IDmodello);

				break;
			case"2XL":
				evidenziaTagliaDisponibile(lbl2XL, IDmodello);
				break;
				
			case"3XL":
				evidenziaTagliaDisponibile(lbl3XL, IDmodello);
				
				break;

			}
		}
		
	}
	
	private void evidenziaTagliaDisponibile(JLabel label, String IDmodello) {
		if(TCDAO.getGiacenzaByEan(IDmodello, label.getText())>4) {
		label.setBorder(new LineBorder(Color.BLACK));
		label.setForeground(Color.BLACK);
		}
		else {
			label.setBorder(new LineBorder(Color.red));
			label.setForeground(Color.red);
		}
	}
	
	/**
	 * Questo metodo nasconde le taglie ogni volta che un elemento viene evidenziato.
	 * Questo per evitare che le taglie del capo scelto in precedenza contagino la nuova scelta.
	 */
	private void celoTaglie() {
		lblXS.setForeground(new Color(144, 148, 155));
		lblXS.setBorder(new LineBorder(new Color(144, 148, 155)));

		lblS.setForeground(new Color(144, 148, 155));
		lblS.setBorder(new LineBorder(new Color(144, 148, 155)));

		lblM.setForeground(new Color(144, 148, 155));
		lblM.setBorder(new LineBorder(new Color(144, 148, 155)));

		lblL.setForeground(new Color(144, 148, 155));
		lblL.setBorder(new LineBorder(new Color(144, 148, 155)));

		lblXL.setForeground(new Color(144, 148, 155));
		lblXL.setBorder(new LineBorder(new Color(144, 148, 155)));

		lbl2XL.setForeground(new Color(144, 148, 155));
		lbl2XL.setBorder(new LineBorder(new Color(144, 148, 155)));

		lbl3XL.setForeground(new Color(144, 148, 155));
		lbl3XL.setBorder(new LineBorder(new Color(144, 148, 155)));
		
		
	}
	
	public void refreshTable1() {

		panelShowArticle.setVisible(false);
		String query = "select * from \"Negoziodiabbigliamento\".\"Magazzino\" ";
		refreshTable(query, table);
    }
	
	
	
	/**
	 * Crea un nuovo modello usando i valori della riga scelta dalla table
	 * @param rigaScelta
	 * @return Modello
	 */
	private Modello creaModello(int rigaScelta) {
		Modello modello ;
		String sesso, nome, IDModello, idCollezione, idFornitore, descrizione, ean;
		double prezzo ;
		int sconto;
		byte [] img;
		ean = table.getModel().getValueAt(rigaScelta,0).toString();
		sesso = table.getModel().getValueAt(rigaScelta,7).toString();
		nome = table.getModel().getValueAt(rigaScelta,6).toString();
		IDModello = table.getModel().getValueAt(rigaScelta,4).toString();
		idCollezione =  CDAO.showCollectionID(nome, sesso);
		prezzo = Double.parseDouble(table.getModel().getValueAt(rigaScelta,5).toString());
		idFornitore = FDAO.getPIvaByModello(IDModello);
		descrizione = MDAO.getDescrizione(IDModello);
		img = MDAO.getPic(IDModello);
		sconto = MDAO.presenteSconto(IDModello);
		return modello = new Modello(nome, sesso, ean, idCollezione, prezzo, idFornitore, descrizione, IDModello, img, sconto);
	}
}
