import java.io.*;

public class Main {

	public static void main(String[] args) {
		Moteur m = new Moteur();

		String fichier = "base_chainage_arriere.txt";
		
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

		//System.out.println(m);

		String strategie = "Arriere";
		String trace = "Stratégie inexistante, impossible de lancer le moteur";
		boolean tracer = true;
		switch (strategie) {
			case "Avant largeur":
				//System.out.println(m);
				trace = m.chainage_avant_largeur();
				break;

			case "Avant profondeur":
				//System.out.println(m);
				trace = m.chainage_avant_profondeur("premiere");
				break;

			case "Arriere":
				//System.out.println(m);
				trace = m.chainage_arriere("premiere");
				break;

			case "Mixte":
				//System.out.println(m);
				trace = m.chainage_mixte();
				break;

			default:
				break;
		}

		if (tracer) System.out.println(trace);
		//System.out.println(m);
	}
}