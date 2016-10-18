import java.io.*;

public class Main {

	public static void main(String[] args) {
		Moteur m = new Moteur();


		String fichier = "base_de_regles.txt";
		
		// lecture du fichier texte	
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			Propositions premisses = new Propositions();
			Proposition conclusion = new Proposition();
			boolean lire_fichier = ((ligne = br.readLine()) != null) ? true : false;
			while (lire_fichier) {
				while (ligne.equals("")) ligne = br.readLine();
				if (ligne.equals("SI")) {
					ligne = br.readLine();
					while (!ligne.equals("ALORS") && !ligne.equals("") && ligne != null) {
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
				if (ligne == null) lire_fichier = false;
			}
			br.close(); 
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}


		System.out.println("\n=== Base de Règles ===");
		m.afficher_br();

		String strategie = "1";
		switch (strategie) {
			case "1":
				m.ajouter_fait("action", "glisser");
				m.ajouter_fait("environnement", "neige");
				System.out.println("\n=== Base de Faits AVANT ===");
				m.afficher_bf();
				m.chainage_avant_largeur();
				break;

			case "2":
				m.ajouter_fait("action", "glisser");
				m.ajouter_fait("environnement", "neige");
				System.out.println("\n=== Base de Faits AVANT ===");
				m.afficher_bf();
				m.chainage_avant_profondeur();
				break;

			case "3":
				System.out.println("\n=== Base de Faits AVANT ===");
				m.afficher_bf();
				m.chainage_arriere();
				break;
				
			default:
				break;
		}

		System.out.println("\n=== Base de Faits APRES ===");
		m.afficher_bf();
	}
}