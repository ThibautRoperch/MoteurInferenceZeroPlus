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

	// algorithmes d'exploitation

	public void chainage_avant_largeur() {
		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				Vector<Regle> regles_valides = new Vector<Regle>();
				if (this.base_de_faits.contains(r.get_premisses())) { // les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la regle
				}
				for (Object regle_valide : regles_valides) { // ajoute la conclusion de chaque regle mise de côté et supprimer cette règle de la base de connaissances
					Regle r_valide = (Regle)regle_valide;
					this.base_de_faits.set(r.get_conclusion()); // verifier que ce fait n'existe pas deja ?
					// ôter la regle de l'ensemble de base_de_regles
				}
			}
		}
	}

	public void chainage_avant_profondeur() {
		while (!this.base_de_faits.contains(but) && this.base_de_regles.size() > 0) {
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				Vector<Regle> regles_valides = new Vector<Regle>();
				if (this.base_de_faits.contains(r.get_premisses())) { // les premisses de la règle correspondent à des propositions de la base de fait
					regles_valides.addElement(r); // mettre de coté la regle
				}
				for (Object regle_valide : regles_valides) { // choix d'une règle mise de côté et supprimer cette règle de la base de connaissances
					Regle r_valide = (Regle)regle_valide;
					this.base_de_faits.set(r.get_conclusion()); // verifier que ce fait n'existe pas deja ?
					// ôter la regle de l'ensemble de base_de_regles
					break;
				}
			}
		}
	}

	public void chainage_arriere() {
		while (true) {
			for (Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if (this.base_de_faits.contains(r.get_conclusion())) {
					this.base_de_faits.set(r.get_premisses()); // verifier qu'il n'existe pas deja ?
				}
			}
		}
	}

	// affichages

	public void afficher_br() {
		System.out.println(base_de_faits);
	}

	public void afficher_bf() {
		for (Object regle : base_de_regles) {
			Regle r = (Regle)regle;
			System.out.println(r);
		}
	}

	// main de test

	public static void main(String[] args) {
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
	}
}