import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame implements ActionListener, WindowListener {

	protected JTextArea tFichier;
	protected CheckboxGroup cStrategie;
	protected CheckboxGroup cConflits;
	protected JButton bLaunch;
	protected JTextArea tOutput;

	public Interface() {
		super("Interface d'un moteur d'inférence 0+");
		
		// panels
		JPanel outputPanel = new JPanel();									// contient les traces dans un textarea
		JPanel inputPanel = new JPanel();							// contient les controles et le bouton final
		//JSplitPane splitPane = new JSplitPane();					// contient le inputPanel et le outputPanel
		JTabbedPane tabbedPane = new JTabbedPane();					// contient le sFichier dans un onglet et le inputPanel dans un autre ongler

		// outputPanel composants
		tOutput = new JTextArea();
		tOutput.setEditable(false);
		tOutput.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane sOutput = new JScrollPane(tOutput);
		sOutput.setPreferredSize(new Dimension(800, 500));

		// inputPanel composants
		JPanel strategiesPanel = new JPanel();
		strategiesPanel.setLayout(new BoxLayout(strategiesPanel, BoxLayout.Y_AXIS));
			cStrategie = new CheckboxGroup();
			Checkbox cb11 = new Checkbox("Avant largeur", cStrategie, true);
			Checkbox cb12 = new Checkbox("Avant profondeur", cStrategie, false);
			Checkbox cb13 = new Checkbox("Arrière", cStrategie, false);
			Checkbox cb14 = new Checkbox("Mixte", cStrategie, false);
			strategiesPanel.add(cb11);
			strategiesPanel.add(cb12);
			strategiesPanel.add(cb13);
			strategiesPanel.add(cb14);
			cb11.setMaximumSize(new Dimension(250, 50));
			cb12.setMaximumSize(new Dimension(250, 50));
			cb13.setMaximumSize(new Dimension(250, 50));
			cb14.setMaximumSize(new Dimension(250, 50));

		JPanel conflitsPanel = new JPanel();
		conflitsPanel.setLayout(new BoxLayout(conflitsPanel, BoxLayout.Y_AXIS));
			cConflits = new CheckboxGroup();
			Checkbox cb21 = new Checkbox("Conserver la première règle", cConflits, true);
			Checkbox cb22 = new Checkbox("Utiliser la règle la plus précise", cConflits, false);
			Checkbox cb23 = new Checkbox("Utiliser la règle la plus récente", cConflits, false);
			conflitsPanel.add(cb21);
			conflitsPanel.add(cb22);
			conflitsPanel.add(cb23);
			cb21.setMaximumSize(new Dimension(250, 50));
			cb22.setMaximumSize(new Dimension(250, 50));
			cb23.setMaximumSize(new Dimension(250, 50));

		tFichier = new JTextArea();
		tFichier.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane sFichier = new JScrollPane(tFichier);

		bLaunch = new JButton("Lancer le moteur");
		bLaunch.setPreferredSize(new Dimension(0, 75));
		bLaunch.addActionListener(this);

		// placement des composants
		/*inputPanel.add(strategiesPanel);
		inputPanel.add(conflitsPanel);*/
		tabbedPane.addTab("Base de connaissances", sFichier);
		tabbedPane.addTab("Stratégie de chaînage", strategiesPanel);
		tabbedPane.addTab("Gestion des conflits", conflitsPanel);
		tabbedPane.setPreferredSize(new Dimension(300, 350));
		outputPanel.add(sOutput);

		// placement des panels
		setLayout(new BorderLayout());
		add(BorderLayout.WEST, tabbedPane);
		add(BorderLayout.SOUTH, bLaunch);
		add(BorderLayout.CENTER, outputPanel);
		/*splitPane.setTopComponent(inputPanel);
		splitPane.setBottomComponent(outputPanel);*/

		addWindowListener(this);
		
		pack();
		setVisible(true);
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

		String strategie = cStrategie.getSelectedCheckbox().getLabel();
		String conflits = cConflits.getSelectedCheckbox().getLabel();
		String base = tFichier.getText();

		// a compléter
	}
	
	public static void main(String[] args) {
		Interface gui = new Interface();
	}
}
