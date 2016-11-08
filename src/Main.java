import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String fichier = "base_connaissances.txt";
		String lignes_fichier = new String();

		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			/*Propositions premisses = new Propositions();
			Proposition conclusion = new Proposition();*/
			boolean lire_fichier = ((ligne = br.readLine()) != null) ? true : false;
			//String declaration_section = new String();
			while (lire_fichier) {
				lignes_fichier += "\n" + ligne;
				ligne = br.readLine();
				if (ligne == null) lire_fichier = false;
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}

		Moteur m = new Moteur(lignes_fichier);
		System.out.println(m);

		//Scanner pour demander à l'utilisateur la stratégie d'exploitation de la base de règles.
		Scanner scan = new Scanner(System.in);

		System.out.println("Veuillez entrer la strategie d'exploitation des règles : ");
		System.out.println("1 : chainage avant largeur");
		System.out.println("2 : chainage avant profondeur");
		System.out.println("3 : chainage arrière");
		System.out.println("4 : chainage mixte");
		String strategie = scan.nextLine();

		System.out.println("Veuillez entrer la stratégie de gestion des conflits : ");
		System.out.println("1 : conserve la première règle");
		System.out.println("2 : utiliser la règle la plus précise");
		System.out.println("3 : utiliser la règle la plus récente");
		String conflits = scan.nextLine();

		boolean tracer = true;
		boolean calculer = true;
		
		String trace = "Stratégie de gestion des conflits inexistante, impossible de lancer le moteur";

		switch (conflits) {	
			case "1":
				conflits = "premiere";
				break;

			case "2":
				conflits = "precise";
				break;

			case "3":
				conflits = "recente";
				break;

			default:
				calculer = false;
				break;
		}
		
		if (calculer) {
			trace = "Stratégie d'exploitation inexistante, impossible de lancer le moteur";

			switch (strategie) {	
				case "1":		//Chainage avant_largeur
					trace = m.chainage_avant_largeur();
					break;

				case "2":		//Chainage avant_profondeur
					trace = m.chainage_avant_profondeur(conflits);
					break;

				case "3":		//Chainage arrière
					trace = m.chainage_arriere();
					break;

				case "4":		//Chainage mixte
					trace = m.chainage_mixte();
					break;

				default:
					break;
			}
		}

		if (tracer) System.out.println(trace);
		//System.out.println(m);
	}
}