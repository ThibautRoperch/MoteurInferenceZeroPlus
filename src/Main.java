import java.util.Vector;
import java.io.*;

public class Main {

	protected Vector<Regle> base_de_regles;
	protected Propositions base_de_faits;
	protected Proposition but;

	public void chainage_avant(Vector<Regle> base_de_regles, Propositions base_de_faits, Proposition but) {
		while(!base_de_faits.contains(but)) {
			for(Object regle : base_de_regles) {
				Regle r = (Regle)regle;
				if(base_de_faits.contains(r.get_premisses())) {
					base_de_faits.set(r.get_conclusion());
				}
			}
		}
	}

	public void chainage_arriere(Vector<Regle> base_de_regles, Proposition but) {

	}

	public static void main(String[] args) {
		base_de_regles = new Vector<Regle>();
		base_de_faits = new Propositions();

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
		Proposition but = new Proposition("sport", "ski_de_fond");

		String strategie = "1";
		switch (strategie) {
			case "1":
				//Création des faits et ajout à la base de faitss
				base_de_faits.set("action", "glisser");
				base_de_faits.set("environnement", "neige");

				chainage_avant(base_de_regles, base_de_faits, but);

			break;
			case "2":
				chainage_arriere(base_de_regles, but);

				break;
			default:
				break;
		}
	}

}