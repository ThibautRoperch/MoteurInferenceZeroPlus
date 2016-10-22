import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Moteur m = new Moteur();

		String fichier = "base_connaissances.txt";
		
		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			Propositions premisses = new Propositions();
			Proposition conclusion = new Proposition();
			boolean lire_fichier = ((ligne = br.readLine()) != null) ? true : false;
			String declaration_section = new String();
			while (lire_fichier) {
				if (ligne.contains("#")) {
					declaration_section = ligne.substring(1);
					ligne = br.readLine();
				}
				while (ligne.equals("")) ligne = br.readLine();
				if (declaration_section.equals("REGLES")) {
					if (ligne.equals("SI")) {
						ligne = br.readLine();
						while (!ligne.equals("ALORS") && !ligne.equals("")) {
							String proposition[] = ligne.split("=");
							premisses.set(proposition[0], proposition[1]);
							ligne = br.readLine();
						}
						if (ligne.equals("ALORS")) {
							ligne = br.readLine();
							String proposition[] = ligne.split("=");
							conclusion.set_variable(proposition[0]);
							conclusion.set_valeur(proposition[1]);
							ligne = br.readLine();
						}
						m.ajouter_regle(premisses, conclusion);
						premisses.clear();
					}
				} else if (declaration_section.equals("FAITS")) {
					if (ligne.contains("=")) {
						String proposition[] = ligne.split("=");
						m.ajouter_fait(proposition[0], proposition[1]);
						ligne = br.readLine();
					}					
				} else if (declaration_section.equals("BUT")) {
					if (ligne.contains("=")) {
						String proposition[] = ligne.split("=");
						m.set_but(proposition[0], proposition[1]);
					} else if (!ligne.equals("")) {
						m.set_but(ligne);
					}
					ligne = br.readLine();
				}
				if (ligne == null) lire_fichier = false;
			}
			br.close(); 
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}

		System.out.println(m);


		//Scanner pour demander à l'utilisateur la stratégie d'exploitation de la base de règles.
		Scanner strat = new Scanner(System.in);
		System.out.println("Veuillez entrer la strategie d'exploitation des règles : ");
		System.out.println("1 : chainage avant largeur");
		System.out.println("2 : chainage avant profondeur");
		System.out.println("3 : chainage arrière");
		System.out.println("4 : chainage mixte");
		
		String strategie = strat.nextLine();
		while(!strategie.equals("1")&&!strategie.equals("2")&&!strategie.equals("3")&&!strategie.equals("4")) {
			System.out.println("Veuillez entrer un chiffre entre 1 et 4 afin de déterminer la stratégie d'exploitation des règles : ");
			System.out.println("1 : chainage avant largeur");
			System.out.println("2 : chainage avant profondeur");
			System.out.println("3 : chainage arrière");
			System.out.println("4 : chainage mixte");
			strategie = strat.nextLine();
		}

		String trace = "Stratégie inexistante, impossible de lancer le moteur";
		boolean tracer = true;
		System.out.println("Chainage " + strategie);
		switch (strategie) {	
			case "1":		//Chainage avant_largeur
				trace = m.chainage_avant_largeur();
				break;

			case "2":		//Chainage avant_profondeur
				trace = m.chainage_avant_profondeur("premiere");
				break;

			case "3":		//Chainage arrière
				trace = m.chainage_arriere("precise");
				break;

			case "4":		//Chainage mixte
				trace = m.chainage_mixte();
				break;

			default:
				break;
		}

		if (tracer) System.out.println(trace);
		//System.out.println(m);
	}
}