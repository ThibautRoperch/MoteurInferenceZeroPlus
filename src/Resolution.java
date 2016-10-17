import java.util.Vector;
import java.io.*;

public class Resolution {

	private Vector<Regle> base_de_regles = new Vector<Regle>();
	private Propositions base_de_faits = new Propositions();
	private Proposition but;
	
	public Resolution() {
		base_de_regles = new Vector<Regle>();
		base_de_faits = new Propositions();
		but = new Proposition();
	}

<<<<<<< HEAD
	public void chainage_avant_larg(Vector<Regle> base_de_regles, Propositions base_de_faits, Proposition but) {
		while(!base_de_faits.contains(but)) {
			for(Object regle : base_de_regles) {
=======
	public void chainage_avant() {
		while(!this.base_de_faits.contains(but)) {
			for(Object regle : this.base_de_regles) {
>>>>>>> ae7b99873b64d8b3fa6a49dba98193cd23a0b595
				Regle r = (Regle)regle;
				if(this.base_de_faits.contains(r.get_premisses())) {
					this.base_de_faits.set(r.get_conclusion());
				}
			}
		}
	}

<<<<<<< HEAD
	public void chainage_avant_prof(Vector<Regle> base_de_regles, Propositionsbase_de_faits, Proposition but) {

	}	

	public void chainage_arriere(Vector<Regle> base_de_regles, Proposition but) {

	}

	public void resoudre() {
		Vector<Regle> base_de_regles = new Vector<Regle>();
		Propositions base_de_faits = new Propositions();
		Proposition but;

=======
	public void chainage_arriere() {

	}

	public void run() {
>>>>>>> ae7b99873b64d8b3fa6a49dba98193cd23a0b595
		// Création des règles et ajout à la base de règles
		Propositions p = new Propositions();
		p.set("action", "glisser");
		p.set("environnement", "neige");
		Regle r1 = new Regle(
			p,
			new Proposition("sport", "ski_de_fond")
		);

		base_de_regles.addElement(r1);

		// Création du but
		but = new Proposition("sport", "ski_de_fond");

		String strategie = "1";
		switch (strategie) {
			case "1":
				//Création des faits et ajout à la base de faitss
				base_de_faits.set("action", "glisser");
				base_de_faits.set("environnement", "neige");

<<<<<<< HEAD
				chainage_avant_larg(base_de_regles, base_de_faits, but);

			break;
			case "2":
				chainage_avant_prof(base_de_regles, but);
=======
				chainage_avant();

			break;
			case "2":
				chainage_arriere();
>>>>>>> ae7b99873b64d8b3fa6a49dba98193cd23a0b595

				break;
			case "3":
				chainage_arriere(base_de_regles, but);

			break;
			default:
				break;
		}

		System.out.println(base_de_faits);
	}
}