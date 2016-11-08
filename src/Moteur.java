import java.util.Vector;
import java.io.*;

public class Moteur {

	protected Vector<Regle> base_de_regles;
	protected Propositions base_de_faits;
	protected Proposition but;
	
	// constructeurs

	public Moteur() {
		this.base_de_regles = new Vector<Regle>();
		this.base_de_faits = new Propositions();
		this.but = new Proposition();
	}

	public Moteur(Vector<Regle> br, Propositions bf, Proposition but) {
		this.base_de_regles = new Vector<Regle>();
		for (Object r : br) this.base_de_regles.addElement((Regle)r);
		this.base_de_faits = bf.clone();
		this.but = but.clone();
	}

	public Moteur(String base) {
		this();

		// Base représentée dans un vecteur de lignes
		Vector<String> base_de_connaissances = new Vector<String>();
		String ligne = "";
		int indice = 0;
		while (base.length() > 0) {
			if (indice == base.length() || base.subSequence(indice, indice+1).equals("\n")) {
				base_de_connaissances.add(ligne);
				if (indice == base.length()) break;
				ligne = "";
			} else {
				ligne += base.charAt(indice);
			}
			++indice;
		}

		// Traitement des lignes
		Propositions premisses = new Propositions();
		Proposition conclusion = new Proposition();
		String declaration_section = new String();
		indice = 0;
		ligne = "";
		while (indice < base_de_connaissances.size()) {
			ligne = base_de_connaissances.get(indice);
			if (ligne.contains("#")) {
				declaration_section = ligne.substring(1);
			} else if (declaration_section.equals("REGLES")) {
				if (ligne.equals("SI")) {
					ligne = base_de_connaissances.get(++indice);
					while (!ligne.equals("ALORS") && !ligne.equals("")) {
						String proposition[] = ligne.split("=");
						premisses.set(proposition[0], proposition[1]);
						ligne = base_de_connaissances.get(++indice);
					}
					if (ligne.equals("ALORS")) {
						ligne = base_de_connaissances.get(++indice);
						String proposition[] = ligne.split("=");
						conclusion.set_variable(proposition[0]);
						conclusion.set_valeur(proposition[1]);
					}
					ajouter_regle(premisses, conclusion);
					premisses.clear();
				}
			} else if (declaration_section.equals("FAITS")) {
				if (ligne.contains("=")) {
					String proposition[] = ligne.split("=");
					ajouter_fait(proposition[0], proposition[1]);
				}					
			} else if (declaration_section.equals("BUT")) {
				if (ligne.contains("=")) {
					String proposition[] = ligne.split("=");
					set_but(proposition[0], proposition[1]);
				} else if (!ligne.equals("")) {
					set_but(ligne);
				}
			}
			++indice;
		}
	}

	// accesseurs

	public Vector<Regle> get_base_de_regles() {
		return this.base_de_regles;
	}

	public Propositions get_base_de_faits() {
		return this.base_de_faits;
	}

	public Proposition get_but() {
		return this.but;
	}

	// mutateurs

	public void ajouter_regle(Propositions premisses, Proposition conclusion) {
		this.ajouter_regle(new Regle(premisses, conclusion));
	}

	public void ajouter_regle(Regle r) {
		this.base_de_regles.addElement(r);
	}

	public void ajouter_fait(String variable, String valeur) {
		this.base_de_faits.set(variable, valeur);
	}

	public void ajouter_fait(Proposition p) {
		this.ajouter_fait(p.get_variable(), p.get_valeur());
	}

	public void set_but(String variable) {
		this.but = new Proposition(variable, "");
	}

	public void set_but(String variable, String valeur) {
		this.but = new Proposition(variable, valeur);
	}

	public void set_but(Proposition but) {
		this.but = but;
	}
	
	// autres (pour algorithmes d'exploitation)

	public boolean regle_has_premisse(Vector<Regle> regles, Proposition premisse) {
		for (Object regle : regles) {
			Regle r = (Regle)regle;
			if (r.get_premisses().contains(premisse)) return true;
		}
		return false;
	}

	// algorithmes d'exploitation

	public String chainage_avant_largeur() {
		String trace = "";
		int etape = 0;
		int etape_max = this.base_de_regles.size();

		// Lire les règles tant que la base de faits ne contient pas le type de but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but)) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Mettre de côté les règles valides (celles qui ont leur(s) prémisse(s) en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			if (regles_valides.isEmpty()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
			// Ajouter à la base de faits la conclusion des règles mises de côté et les supprimer de la base de règle
			for (Object regle_valide : regles_valides) { // ajoute la conclusion de chaque regle mise de côté et supprimer cette règle de la base de connaissances
				Regle r_valide = (Regle)regle_valide;
				if (this.base_de_faits.conflit(r_valide.get_conclusion())) { // verifier que ce type de fait n'existe pas deja dans la base de faits avec une valeur différente
					trace += "\nErreur : conflit de règles, une règle a été appliquée et elle donne une valeur différente d'une variable déjà de la base de fait\n";
					trace += "Erreur : base de connaissances inconsistante : " + r_valide + "\n";
					return trace;
				}
				this.base_de_faits.set(r_valide.get_conclusion());
				this.base_de_regles.remove(r_valide); // ôter la règle de la base de règles
				trace += "[CHANGEMENTS]\nUtilisation de la règle " + r_valide + ", ôtée de la base de règles\nAjout du fait " + r_valide.get_conclusion() + " à la base de faits\n\n";
			}
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";

			if (etape > etape_max) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
		}

		return trace + "\n==     SUCCES      ==\n";
	}

	public String chainage_avant_profondeur(String strategie_conflit) {
		String trace = "";
		int etape = 0;
		int etape_max = this.base_de_regles.size();
		
		// Lire les règles tant que la base de faits ne contient pas le type de but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but)) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Mettre de côté les règles valides (celles qui ont leur(s) prémisse(s) en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			if (regles_valides.isEmpty()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
			// Définir la façon dont la règle à appliquer sera choisie
			Regle r_choisie = new Regle();
			switch (strategie_conflit) { // resoudre le conflit en choisissant la règle à appliquer
				case "premiere":
					r_choisie = regles_valides.firstElement();
					break;

				case "precise":
					int max_premisses = 0;
					for (Object regle_valide : regles_valides) {
						Regle r_valide = (Regle)regle_valide;
						if (r_valide.get_premisses().getSize() > max_premisses) {
							r_choisie = r_valide;
							max_premisses = r_valide.get_premisses().getSize();
						}
					}
					break;

				case "recente":
					r_choisie = regles_valides.lastElement();
					break;

				default:
					trace += "\nErreur : impossible de choisir une règle, vérifier la stratégie de résolution des conflits\n";
					trace += "Erreur : stratégie de résolution de conflit inexistante : " + strategie_conflit + "\n";
					break;
			}
			// Ajouter à la base de faits la conclusion de la règle mise de côté et choisie, et la supprimer de la base de règles
			if (this.base_de_faits.conflit(r_choisie.get_conclusion())) { // verifier que ce type de fait n'existe pas deja dans la base de faits avec une valeur différente
				trace += "\nErreur : conflit de règles, une règle a été appliquée et elle donne une valeur différente d'une variable déjà de la base de fait\n";
				trace += "Erreur : base de connaissances inconsistante : " + r_choisie + "\n";
				return trace;
			}
			this.base_de_faits.set(r_choisie.get_conclusion());
			this.base_de_regles.remove(r_choisie); // ôter la règle de la base de règles
			trace += "[CHANGEMENTS]\nUtilisation de la règle " + r_choisie + ", ôtée de la base de règles\nAjout du fait " + r_choisie.get_conclusion() + " à la base de faits\n\n";
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";

			if (etape > etape_max) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
		}

		return trace + "\n==     SUCCES      ==\n";
	}

	public String chainage_arriere() {
		String trace = "";
		int etape = 0;
		int etape_max = this.base_de_regles.size();
		Vector<Regle> hypotheses = new Vector<Regle>();
		
		// Lire les règles tant que la base de faits ne contient pas le but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but)) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Mettre de côté les règles valides (celles qui ont leur conclusion en commun avec la base de faits ou qui concluent sur le type de but)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (r.get_conclusion().get_variable().equals(but.get_variable()) || this.regle_has_premisse(hypotheses, r.get_conclusion())) { // test si la variable de la conclusion est égal à la variable du but ou si une hypothèse a comme prémisse la conclusion de la règle
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			if (regles_valides.isEmpty()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
			// Activer les règles conformes à la base de faits, mettre en hypothèse les autres
			trace += "[CHANGEMENTS]\n";
			for(Object regle_v : regles_valides) {
				Regle r_valide = (Regle)regle_v;
				if (this.base_de_faits.contains(r_valide.get_premisses())) {
					this.base_de_faits.set(r_valide.get_conclusion());
					trace += "Utilisation de la règle " + r_valide + ", ôtée de la base de règles\nAjout du(des) fait(s) " + r_valide.get_premisses().toString(", ") + " à la base de faits\n";
					this.base_de_regles.remove(r_valide);
					break;
				} else if (this.base_de_faits.conflit(r_valide.get_premisses())) {
					this.base_de_regles.remove(r_valide);
				} else {
					hypotheses.add(r_valide);
				}
			}

			trace += "\n";
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";
			
			if (etape > etape_max*2) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
		}

		return trace + "\n==     SUCCES      ==\n";
	}

	public String chainage_mixte(String strategie_conflit) {
		String trace = "";
		int etape = 0;
		int etape_max = this.base_de_regles.size();
		
		// Lire les règles tant que la base de faits ne contient pas le type de but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but)) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Mettre de côté les règles valides (celles qui ont leur(s) prémisse(s) en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			if (regles_valides.isEmpty()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
			// Définir la façon dont la règle à appliquer sera choisie
			Regle r_choisie = new Regle();
			switch (strategie_conflit) { // resoudre le conflit en choisissant la règle à appliquer
				case "premiere":
					r_choisie = regles_valides.firstElement();
					break;

				case "precise":
					int max_premisses = 0;
					for (Object regle_valide : regles_valides) {
						Regle r_valide = (Regle)regle_valide;
						if (r_valide.get_premisses().getSize() > max_premisses) {
							r_choisie = r_valide;
							max_premisses = r_valide.get_premisses().getSize();
						}
					}
					break;

				case "recente":
					r_choisie = regles_valides.lastElement();
					break;

				default:
					trace += "\nErreur : impossible de choisir une règle, vérifier la stratégie de résolution des conflits\n";
					trace += "Erreur : stratégie de résolution de conflit inexistante : " + strategie_conflit + "\n";
					break;
			}			
			// Ajouter à la base de faits la conclusion de la règle mise de côté et choisie, et la supprimer de la base de règles
			this.base_de_faits.set(r_choisie.get_conclusion());
			this.base_de_regles.remove(r_choisie); // ôter la règle de la base de règles
			trace += "[CHANGEMENTS]\nUtilisation de la règle " + r_choisie + ", ôtée de la base de règles\nAjout du fait " + r_choisie.get_conclusion() + " à la base de faits\n\n";
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";

			if (etape > etape_max*2) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
			
			if (!this.base_de_faits.contains(but)) {
				trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

				// Mettre de côté les règles valides (celles qui ont leur conclusion en commun avec la base de faits ou qui concluent sur le type de but)
				Vector<Regle> regles_valides_2 = new Vector<Regle>();
				for (Object regle : this.base_de_regles) {
					Regle r = (Regle)regle;
					if (r.get_conclusion().get_variable().equals(but.get_variable())) { // test si la variable de la conclusion est égal à la variable du but ou si une hypothèse a comme prémisse la conclusion de la règle
						regles_valides_2.addElement(r); // mettre de coté la règle
					}
				}
				if (regles_valides_2.isEmpty()) {
					trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait(s) et but) sont conformes au dictionnaire de la base de règles\n";
					trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
					return trace;
				}
				// Activer les règles conformes à la base de faits, mettre en hypothèse les autres
				trace += "[CHANGEMENTS]\n";
				for(Object regle_v : regles_valides_2) {
					Regle r_valide = (Regle)regle_v;
					if (this.base_de_faits.contains(r_valide.get_premisses())) {
						this.base_de_faits.set(r_valide.get_conclusion());
						trace += "Utilisation de la règle " + r_valide + ", ôtée de la base de règles\nAjout du(des) fait(s) " + r_valide.get_premisses().toString(", ") + " à la base de faits\n";
						this.base_de_regles.remove(r_valide);
						break;
					} else if (this.base_de_faits.conflit(r_valide.get_premisses())) {
						this.base_de_regles.remove(r_valide);
					}
				}

				trace += "\n";
				trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
				trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";
			}
		}

		return trace + "\n==     SUCCES      ==\n";
	}

	// affichages

	public String br_toString() {
		String res = "";
		for (Object regle : base_de_regles) {
			Regle r = (Regle)regle;
			res += r.toString() + "\n";
		}
		return res;
	}

	public String bf_toString() {
		return base_de_faits.toString("\n");
	}

	public String but_toString() {
		return this.but.toString();
	}

	// opérateur de sortie

	public String toString() {
		return "\n[BASE DE REGLES]\n" + this.br_toString() + "\n[BASE DE FAITS]\n" + this.bf_toString() + "\n\n[BUT RECHERCHE]\n" + this.but_toString() + "\n";
	}

	// main de test

	/*public static void main(String[] args) {
		Moteur m = new Moteur();

		// Création des règles et ajout à la base de règles
		Propositions p = new Propositions();
		p.set("action", "glisser");
		p.set("environnement", "neige");
		Regle r1 = new Regle(
			p,
			new Proposition("sport", "ski_de_fond")
		);

		m.ajouter_regle(r1);

		// Création du but
		Proposition a = new Proposition("sport", "ski_de_fond");
		m.set_but(a);

		// Chainage avant : Création des faits et ajout à la base de faits
		m.ajouter_fait(new Proposition("action", "glisser"));
		m.ajouter_fait(new Proposition("environnement", "neige"));
		//chainage_avant_largeur();
		m.chainage_avant_profondeur();

		// Chainage arrière
		//m.chainage_arriere();

		System.out.println("\n=== BF APRES ===");
		m.afficher_bf();
	}*/
}