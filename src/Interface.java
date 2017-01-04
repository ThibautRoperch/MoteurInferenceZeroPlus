import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Interface extends JFrame implements ActionListener, WindowListener {

	protected JTextArea tInputFichier;
	protected ButtonGroup cStrategie;
	protected ButtonGroup cConflits;
	protected JCheckBoxMenuItem cActualiserBC;
	protected JButton bLaunch;
	protected JTextArea tOutputRegles1;
	protected JTextArea tOutputFaits1;
	protected JTextArea tOutputRegles2;
	protected JTextArea tOutputFaits2;
	protected JTextArea tOutputTraces;
	protected JTextArea tOutputBut;
	protected JTextArea tOutputOptions;

	protected String selectedStrategieChainage;
	protected String selectedGestionConflits;

	public Interface() {
		super("Interface d'un moteur d'inférence 0+");

		// barre de menus et panels
		JMenuBar menuBar = new JMenuBar();
		JPanel inputPanel = new JPanel();							// contient la base de faits éditable avec scrollars
		JPanel tracesPanel = new JPanel();							// contient les traces dans un textarea
		JPanel recapPanel = new JPanel();							// contient un récapitulatif de la base de connaissances avant et après inférence
		JTabbedPane inputTabbedPane = new JTabbedPane();			// contient inputPanel dans un onglet
		JTabbedPane outputTabbedPane = new JTabbedPane();			// contient tracesPanel dans un onglet et recapPanel dans un autre onglet

		// menu fichier
		JMenu fileMenu = new JMenu("Fichier");
		JMenuItem item;
		item = new JMenuItem("Nouveau", 'N');
		item.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { remiseAzero(); } } );
		fileMenu.add(item);
		JMenu exemplesMenu = new JMenu("Charger un exemple");
		fileMenu.add(exemplesMenu);
		item = new JMenuItem("Quitter", 'Q');
		item.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { System.exit(0); } } );
		fileMenu.add(item);
		fileMenu.insertSeparator(2);

		// menu options
		JMenu optionsMenu = new JMenu("Options du Moteur");
		JMenu strategiesMenu = new JMenu("Stratégie de chainage");
		JMenu conflitsMenu = new JMenu("Gestion des conflits");
		cActualiserBC = new JCheckBoxMenuItem("Actualiser la base de connaissances", false);
		optionsMenu.add(strategiesMenu);
		optionsMenu.add(conflitsMenu);
		optionsMenu.add(cActualiserBC);
		optionsMenu.insertSeparator(2);

		// menu exemples
		JMenuItem exemple;
		exemple = new JMenuItem("0 : Sujet que nous avons choisi - Description de sports");
		exemple.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { chargerExemple(0); } } );
		exemplesMenu.add(exemple);
		exemple = new JMenuItem("1 : Test simple - Exemple donné lorsqu'on lance le moteur sans base de connaissances");
		exemple.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { chargerExemple(1); } } );
		exemplesMenu.add(exemple);
		exemple = new JMenuItem("2 : Base de connaissances inconsistante - Deux règles avec les mêmes premisses mais des conclusions différentes");
		exemple.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { chargerExemple(2); } } );
		exemplesMenu.add(exemple);
		exemple = new JMenuItem("3 : Pas de solution - Pas de règle convergeant vers le but donné");
		exemple.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { chargerExemple(3); } } );
		exemplesMenu.add(exemple);
		exemple = new JMenuItem("4 : Test complexe - Pour tester le chainage arrière");
		exemple.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { chargerExemple(4); } } );
		exemplesMenu.add(exemple);
		exemple = new JMenuItem("5 : Autre thème - Différence entre parcours en largeur et en profondeur");
		exemple.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { chargerExemple(5); } } );
		exemplesMenu.add(exemple);

		// menu aide
		JMenu helpMenu = new JMenu("Aide");
		item = new JMenuItem("Aide en ligne");
		item.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { aideEnLigne(); } } );
		helpMenu.add(item);
		item = new JMenuItem("A propos");
		item.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { aPropos(); } } );
		helpMenu.add(item);
		helpMenu.insertSeparator(1);

		// menu CheckBox des stratégies de chainage dans strategiesMenu
		cStrategie = new ButtonGroup();
		JRadioButtonMenuItem cb11 = new JRadioButtonMenuItem("Avant largeur", true); selectedStrategieChainage = cb11.getLabel();
		JRadioButtonMenuItem cb12 = new JRadioButtonMenuItem("Avant profondeur");
		JRadioButtonMenuItem cb13 = new JRadioButtonMenuItem("Arrière");
		JRadioButtonMenuItem cb14 = new JRadioButtonMenuItem("Mixte");
		cb11.addActionListener(new ChangerStrategieChainage());
		cb12.addActionListener(new ChangerStrategieChainage());
		cb13.addActionListener(new ChangerStrategieChainage());
		cb14.addActionListener(new ChangerStrategieChainage());
		cStrategie.add(cb11);
		cStrategie.add(cb12);
		cStrategie.add(cb13);
		cStrategie.add(cb14);
		strategiesMenu.add(cb11);
		strategiesMenu.add(cb12);
		strategiesMenu.add(cb13);
		strategiesMenu.add(cb14);

		// menu CheckBox des gestions de conflits dans conflitsMenu
		cConflits = new ButtonGroup();
		JRadioButtonMenuItem cb21 = new JRadioButtonMenuItem("Conserver la première règle", true); selectedGestionConflits = cb21.getLabel();
		JRadioButtonMenuItem cb22 = new JRadioButtonMenuItem("Utiliser la règle la plus précise");
		JRadioButtonMenuItem cb23 = new JRadioButtonMenuItem("Utiliser la règle la plus récente");
		cb21.addActionListener(new ChangerGestionConflits());
		cb22.addActionListener(new ChangerGestionConflits());
		cb23.addActionListener(new ChangerGestionConflits());
		cConflits.add(cb21);
		cConflits.add(cb22);
		cConflits.add(cb23);
		conflitsMenu.add(cb21);
		conflitsMenu.add(cb22);
		conflitsMenu.add(cb23);

		// placement des menus
		menuBar.add(fileMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);

		// TextArea du fichier dans un ScrollPane dans inputPanel dans inputTabbedPane
		tInputFichier = new JTextArea();
		tInputFichier.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane sInputFichier = new JScrollPane(tInputFichier);
		inputPanel.add(sInputFichier);
		inputTabbedPane.addTab("Base de connaissances", inputPanel);

		// TextArea des traces dans un ScrollPane dans tracesPanel dans outputTabbedPane
		tOutputTraces = new JTextArea();
		tOutputTraces.setEditable(false);
		tOutputTraces.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane sOutputTraces = new JScrollPane(tOutputTraces);
		tracesPanel.add(sOutputTraces);
		outputTabbedPane.addTab("Traces", tracesPanel);

		// recapPanel dans outputTabbedPane
		recapPanel.setLayout(new GridLayout(0, 2, 10, 10));
		outputTabbedPane.addTab("Récapitulatif", recapPanel);

		// TextArea des règles dans un ScrollPane dans outputPanel dans outputTabbedPane
		tOutputRegles1 = new JTextArea();
		tOutputRegles1.setEditable(false);
		tOutputRegles1.setBorder(new TitledBorder("Base de règles avant"));
		JScrollPane sOutputRegles1 = new JScrollPane(tOutputRegles1);
		sOutputRegles1.setPreferredSize(new Dimension(50, 50));
		recapPanel.add(sOutputRegles1);

		// TextArea des règles dans un ScrollPane dans outputPanel dans outputTabbedPane
		tOutputRegles2 = new JTextArea();
		tOutputRegles2.setEditable(false);
		tOutputRegles2.setBorder(new TitledBorder("Base de règles après"));
		JScrollPane sOutputRegles2 = new JScrollPane(tOutputRegles2);
		sOutputRegles2.setPreferredSize(new Dimension(50, 50));
		recapPanel.add(sOutputRegles2);

		// TextArea de la base de faits dans un ScrollPane dans outputPanel dans outputTabbedPane
		tOutputFaits1 = new JTextArea();
		tOutputFaits1.setEditable(false);
		tOutputFaits1.setBorder(new TitledBorder("Base de faits avant"));
		JScrollPane sOutputFaits1 = new JScrollPane(tOutputFaits1);
		sOutputFaits1.setPreferredSize(new Dimension(50, 50));
		recapPanel.add(sOutputFaits1);
		
		// TextArea de la base de faits dans un ScrollPane dans outputPanel dans outputTabbedPane
		tOutputFaits2 = new JTextArea();
		tOutputFaits2.setEditable(false);
		tOutputFaits2.setBorder(new TitledBorder("Base de faits après"));
		JScrollPane sOutputFaits2 = new JScrollPane(tOutputFaits2);
		sOutputFaits2.setPreferredSize(new Dimension(50, 50));
		recapPanel.add(sOutputFaits2);
		
		// TextArea du but dans un ScrollPane dans outputPanel dans outputTabbedPane
		tOutputBut = new JTextArea();
		tOutputBut.setEditable(false);
		tOutputBut.setBorder(new TitledBorder("But"));
		JScrollPane sOutputBut = new JScrollPane(tOutputBut);
		sOutputBut.setPreferredSize(new Dimension(50, 50));
		recapPanel.add(sOutputBut);
		
		// TextArea des options dans un ScrollPane dans outputPanel dans outputTabbedPane
		tOutputOptions = new JTextArea();
		tOutputOptions.setEditable(false);
		tOutputOptions.setBorder(new TitledBorder("Options"));
		JScrollPane sOutputOptions = new JScrollPane(tOutputOptions);
		sOutputOptions.setPreferredSize(new Dimension(50, 50));
		recapPanel.add(sOutputOptions);

		// Button pour lancer le moteur
		bLaunch = new JButton("Lancer le moteur");
		bLaunch.setPreferredSize(new Dimension(1, 75));
		bLaunch.addActionListener(this);

		// tailles des éléments ScrollPane, la taille des éléments autour s'adapteront
		sInputFichier.setPreferredSize(new Dimension(350, 700));
		sOutputTraces.setPreferredSize(new Dimension(1000, 700));

		// mise en page de la Frame avec un BorderLayout
		BorderLayout mainLayout = new BorderLayout();
		mainLayout.setHgap(5);
		mainLayout.setVgap(10);
		setLayout(mainLayout);

		// placement des panels et menu dans la Frame
		add(BorderLayout.NORTH, menuBar);
		add(BorderLayout.WEST, inputTabbedPane);
		add(BorderLayout.EAST, outputTabbedPane);
		add(BorderLayout.SOUTH, bLaunch);

		// autres
		addWindowListener(this);
		pack(); // != setSize(x, y)
		//setResizable(false);
		setVisible(true);
		this.setLocation(300, 100);
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		// Nettoie l'interface et le compteur de règles
		nettoyage();

		String trace = "Champ \"Base de connaissances\" vide ou syntaxiquement incorrect\n\nUn exemple vous a été donné, respectez la syntaxe\n";

		if (!tInputFichier.getText().isEmpty()) {
			// Lit l'interface, affiche la base de connaissances du moteur, le lance et affiche la trace
			Moteur m = new Moteur(tInputFichier.getText());

			String strategie = selectedStrategieChainage;
			String conflits = selectedGestionConflits;

			// Récapitule la base de connaissances
			tOutputRegles1.setText("\n" + m.br_toString());
			tOutputFaits1.setText("\n" + m.bf_toString());

			// Récapitule le but et les options
			tOutputBut.setText("\n" + m.but_toString());
			tOutputOptions.setText("\nSTRATEGIE\tChainage " + selectedStrategieChainage + "\nCONFLITS\t" + selectedGestionConflits);

			switch (conflits) {	
				case "Conserver la première règle":
					conflits = "premiere";
					break;

				case "Utiliser la règle la plus précise":
					conflits = "precise";
					break;

				case "Utiliser la règle la plus récente":
					conflits = "recente";
					break;

				default:
					break;
			}

			switch (strategie) {	
				case "Avant largeur":		//Chainage avant_largeur
					trace = m.chainage_avant_largeur();
					break;

				case "Avant profondeur":	//Chainage avant_profondeur
					trace = m.chainage_avant_profondeur(conflits);
					break;

				case "Arrière":				//Chainage arrière
					trace = m.chainage_arriere();
					break;

				case "Mixte":				//Chainage mixte
					trace = m.chainage_mixte(conflits);
					break;

				default:
					break;
			}

			// Récapitule la base de connaissances
			tOutputRegles2.setText("\n" + m.br_toString());
			tOutputFaits2.setText("\n" + m.bf_toString());

			// Quelques règles consommées, quelques faits ajoutés : réinitialise les règles du moteur avant de calculer et d'ajouter la règle de regroupement
			m.retablir_base_de_regles();
			trace += "\n" + m.ajouter_regle_regroupement();

			// Actualise la base de connaissances
			if (cActualiserBC.getState()) tInputFichier.setText(m.toFile());

			// Affiche les traces
			tOutputTraces.setText("BUT\t" + m.but_toString() + "\nSTRATEGIE\tChainage " + selectedStrategieChainage + "\nCONFLITS\t" + selectedGestionConflits + "\n\n" + trace);
			
			System.out.println("\n\nBase de connaissances finale\n" + m);
		} else {
			tOutputTraces.setText(trace);
			tInputFichier.setText("#REGLES\n\nSI\nvar1=x\nALORS\nvar2=y\n\n#FAITS\n\nvar1=x\n\n#BUT\n\nvar2");
		}
	}

	public void resetRegles() {
		new Regle().reset();
	}

	public void remiseAzero() {
		tInputFichier.setText("");
		nettoyage();
	}

	public void nettoyage() {
		// Efface les TextArea
		tOutputTraces.setText("");
		tOutputRegles1.setText("");
		tOutputFaits1.setText("");
		tOutputRegles2.setText("");
		tOutputFaits2.setText("");
		tOutputBut.setText("");
		tOutputOptions.setText("");
		// Indexation des règles remise à 1
		resetRegles();
	}

	public void chargerExemple(int id) {
		remiseAzero();

		String chemin_fichier = "";
		String contenu_fichier = "";

		switch(id) {
			case 0:
				chemin_fichier += "base_connaissances.txt";
				break;
			case 1:
				chemin_fichier += "exemples/simple.txt";
				break;
			case 2:
				chemin_fichier += "exemples/bc_inconsistante.txt";
				break;
			case 3:
				chemin_fichier += "exemples/pas_de_solution.txt";
				break;
			case 4:
				chemin_fichier += "exemples/complexe.txt";
				break;
			case 5:
				chemin_fichier += "exemples/autre_theme.txt";
				break;
			default:
				chemin_fichier += "base_connaissances.txt";
				break;
		}

		File f = new File (chemin_fichier);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			try {
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					contenu_fichier += sCurrentLine + "\n";
				}
				br.close();
				fr.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier " + chemin_fichier + "n'a pas été trouvé");
		}
		tInputFichier.setText(contenu_fichier);
	}

	public void aideEnLigne() {
		try {
			java.awt.Desktop.getDesktop().browse(new URI("https://docs.google.com/document/d/1psd4K_UPGkzMvmdIjZs10DjAyrOsQY718ez6HGgJtxs/edit?usp=sharing"));
		} catch(URISyntaxException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void aPropos() {
		String message = "";
		message += "\nMoteur d'inférence 0+ réalisé dans le cadre d'un projet de Fondement de l'Intelligence Artificielle\nà l'université d'Angers (Master 1)\n";
		message += "\n                                                              __________________________________________\n\n";
		message += "\nPour plus d'informations concernant le fonctionnement de ce moteur d'inférence 0+,\nconsultez notre rapport à l'adresse suivante :\n\n";
		message += "https://docs.google.com/document/d/1psd4K_UPGkzMvmdIjZs10DjAyrOsQY718ez6HGgJtxs/edit?usp=sharing\n";
		message += "\n                                                              __________________________________________\n\n";
		message += "\nPierre GRANIER--RICHARD\nThibaut ROPERCH\nM1 Infos - Angers";
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, message, "A propos", JOptionPane.PLAIN_MESSAGE);
	}

	class ChangerStrategieChainage implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
			selectedStrategieChainage = e.getActionCommand();
		}
	}

	class ChangerGestionConflits implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
			selectedGestionConflits = e.getActionCommand();
		}
	}
	
	public static void main(String[] args) {
		Interface gui = new Interface();
	}
}
