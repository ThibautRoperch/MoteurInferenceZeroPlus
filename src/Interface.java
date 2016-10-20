import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame implements ActionListener, WindowListener {

	protected JTextArea tOutput;
	protected Choice cStrategie;
	protected JButton bLaunch;
	
	public Interface() {
		super("Interface d'un moteur d'inférence' 0+");
		
		// panels
		JPanel outputPanel = new JPanel();
		JPanel inputPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		// outputPanel composants
		tOutput = new JTextArea(35, 50);
		JScrollPane sOutput = new JScrollPane(tOutput);
		tOutput.setEditable(false);
		
		// inputPanel composants
		Label lStrategie = new Label("Stratégie de chaînage");
		cStrategie = new Choice();
		cStrategie.add("Avant largeur");
		cStrategie.add("Avant profondeur");
		cStrategie.add("Arrière");
		cStrategie.add("Mixte");

		// bottomPanel composants
		bLaunch = new JButton("Lancer le moteur");
		bLaunch.addActionListener(this);

		// placement des composants
		outputPanel.add(sOutput);
		inputPanel.add(lStrategie);
		inputPanel.add(cStrategie);
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(bLaunch);
		
		// placement des panels
		setLayout(new BorderLayout());
		add(BorderLayout.WEST, inputPanel);
		add(BorderLayout.EAST, outputPanel);
		add(BorderLayout.SOUTH, bottomPanel);

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
		
	}
	
	public static void main(String[] args) {
		Interface gui = new Interface();
	}
}
