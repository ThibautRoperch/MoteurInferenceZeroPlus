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

		// Lire les règles tant que la base de fait ne contient pas le tpye de but recherché et qu'il y a des règles encore non utilisées
		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			// Mettre de côté les règles valides (celles qui ont leur(s) prémisse(s) en commun avec la base de faits)
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la regle
				}
			}
			// Ajouter à la base de fait les conclusion des règles mises de côté et les supprimer de la base de règle
			for (Object regle_valide : regles_valides) { // ajoute la conclusion de chaque regle mise de côté et supprimer cette règle de la base de connaissances
				Regle r_valide = (Regle)regle_valide;
				this.base_de_faits.set(r_valide.get_conclusion()); // verifier que ce fait n'existe pas deja ?
				// attention, vu qu'on remove la regle, la valeur get_conclusion et peut etre remove aussi
				this.base_de_regles.remove(r_valide); // ôter la regle de l'ensemble de base_de_regles
			}
			trace += "\n==     ETAPE " + ++etape + "     ==\n";
			trace += "[BASE DE REGLES]\n" + this.br_toString() + "\n";
			trace += "[BASE DE FAITS]\n" + this.bf_toString() + "\n";
		}

		return trace;
	}

	public String chainage_avant_profondeur() {
		String trace = "";

		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			Vector<Regle> regles_valides = new Vector<Regle>();
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_premisses())) { // test si les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la regle
				}
			}
			Regle regle_a_utiliser = regles_valides.get(0);
			for (Object regle_valide : regles_valides) { // choix d'une règle mise de côté et supprimer cette règle de la base de connaissances, remettre les autres dans la base de regles
				Regle r_valide = (Regle)regle_valide;
				if (r_valide == regle_a_utiliser) {
					this.base_de_faits.set(r_valide.get_conclusion()); // verifier que ce fait n'existe pas deja ?
					// attention, vu qu'on remove la regle, la valeur get_conclusion et peut etre remove aussi
					this.base_de_regles.remove(r_valide); // ôter la regle de l'ensemble de base_de_regles
				}
				break;
			}
		}

		return trace;
	}

	public String chainage_arriere() {
		String trace = "";

		while (true) {
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_conclusion())) {
					this.base_de_faits.set(r.get_premisses()); // verifier qu'il n'existe pas deja ?
				}
			}
			if (true) break;
		}

		return trace;
	}

	// affichages

	public String bf_toString() {
		return base_de_faits.toString("\n");
	}

	public String br_toString() {
		String res = "";
		for (Object regle : base_de_regles) {
			Regle r = (Regle)regle;
			res += r + "\n";
		}
		return res;
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