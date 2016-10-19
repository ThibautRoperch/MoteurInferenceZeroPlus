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
		this.base_de_regles = br;
		this.base_de_faits = bf;
		this.but = but;
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

	// gestion de la cohérence

	public boolean regles_coherentes() {
		// ?
		return true;
	}

	// algorithmes d'exploitation

	public String chainage_avant_largeur() {
		String trace = "";
		int etape = 0;

		// Lire les règles tant que la base de faits ne contient pas le type de but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Mettre de côté les règles valides (celles qui ont leur(s) prémisse(s) en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			// Ajouter à la base de faits la conclusion des règles mises de côté et les supprimer de la base de règle
			for (Object regle_valide : regles_valides) { // ajoute la conclusion de chaque regle mise de côté et supprimer cette règle de la base de connaissances
				Regle r_valide = (Regle)regle_valide;
				if (this.base_de_faits.conflit(r_valide.get_conclusion())) { // verifier que ce type de fait n'existe pas deja dans la base de faits avec une valeur différente
					trace += "\nErreur : conflit de règles, une règle a été appliquée et elle donne une valeur différente d'une variable déjà de la base de fait\n";
					trace += "Erreur : base de connaissances inconsistante : " + r_valide + "\n";
					return trace;
				}
				this.base_de_regles.remove(r_valide); // ôter la règle de la base de règles
				this.base_de_faits.set(r_valide.get_conclusion());
				trace += "[CHANGEMENTS]\nUtilisation de la règle " + r_valide + ", ôtée de la base de règles\nAjout du fait " + r_valide.get_conclusion() + " à la base de faits\n\n";
			}
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";

			if (etape > this.base_de_regles.size()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
		}

		return trace;
	}

	public String chainage_avant_profondeur() {
		String trace = "";
		int etape = 0;
		
		// Lire les règles tant que la base de faits ne contient pas le type de but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Mettre de côté les règles valides (celles qui ont leur(s) prémisse(s) en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			// Définir la façon dont la règle à appliquer sera choisie
			// Ajouter à la base de faits la conclusion de la règle mise de côté et choisie, et la supprimer de la base de règles
			for (Object regle_valide : regles_valides) { // ajoute la conclusion de chaque regle mise de côté et supprimer cette règle de la base de connaissances
				Regle r_valide = (Regle)regle_valide;
				if (this.base_de_faits.conflit(r_valide.get_conclusion())) { // verifier que ce type de fait n'existe pas deja dans la base de faits avec une valeur différente
					trace += "\nErreur : conflit de règles, une règle a été appliquée et elle donne une valeur différente d'une variable déjà de la base de fait\n";
					trace += "Erreur : base de connaissances inconsistante : " + r_valide + "\n";
					return trace;
				}
				this.base_de_regles.remove(r_valide); // ôter la règle de la base de règles
				this.base_de_faits.set(r_valide.get_conclusion());
				trace += "[CHANGEMENTS]\nUtilisation de la règle " + r_valide + ", ôtée de la base de règles\nAjout du fait " + r_valide.get_conclusion() + " à la base de faits\n\n";
				break;
			}
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";

			if (etape > this.base_de_regles.size()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
		}

		return trace;
	}

	public String chainage_arriere() {
		String trace = "";
		int etape = 0;

		// Lire les règles tant que la base de faits ne contient pas le but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			trace += "\n==     ETAPE " + ++etape + "     ==\n\n";

			// Ajout du but à la base de faits
			this.base_de_faits.set(but);

			// Mettre de côté les règles valides (celles qui ont leur conclusion en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_conclusion())) { // test si la conclusion de la règle correspond à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la règle
				}
			}
			// Ajouter à la base de faits les prémisses des règles mises de côté et les supprimer de la base de règle
			for (Object regle_valide : regles_valides) { // ajoute les prémisses de chaque regle mise de côté et supprimer cette règle de la base de connaissances
				Regle r_valide = (Regle)regle_valide;
				if (this.base_de_faits.conflit(r_valide.get_premisses())) { // verifier que ce type de fait n'existe pas deja dans la base de faits avec une valeur différente
					trace += "\nAttention : conflit de règles, une règle a été appliquée et elle donne une valeur différente d'une variable déjà de la base de fait\n";
					// resoudre le conflit en choisissant la valeur à garder dans la base de faits
					trace += "Attention : la nouvelle règle a la priorité sur l'ancienne, la(les) valeur(s) gardée(s) est(sont) " + r_valide.get_premisses().toString(", ") + "\n";
				}
				this.base_de_regles.remove(r_valide); // ôter la règle de la base de règles
				this.base_de_faits.set(r_valide.get_premisses());
				trace += "[CHANGEMENTS]\nUtilisation de la règle " + r_valide + ", ôtée de la base de règles\nAjout du(des) fait(s) " + r_valide.get_premisses().toString(", ") + " à la base de faits\n\n";
			}
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";

			if (etape > this.base_de_regles.size()) {
				trace += "\nErreur : impossible de terminer la recherche, vérifier que les données envoyées (base de fait et but) sont conformes au dictionnaire de la base de règles\n";
				trace += "Erreur : pas de solution possible dans cette base de connaissances\n";
				return trace;
			}
		}

		return trace;
	}

	public String chainage_mixte() {
		String trace = "";
		int etape = 0;

		return trace;
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
		return "\n[BASE DE REGLES]\n" + this.br_toString() + "\n[BASE DE FAITS]\n" + this.br_toString() + "\n[BUT RECHERCHE]\n" + this.but_toString() + "\n";
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