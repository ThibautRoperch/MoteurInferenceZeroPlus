import java.util.Vector;
import java.io.*;

public class Resolution {

	protected Vector<Regle> base_de_regles = new Vector<Regle>();
	protected Propositions base_de_faits = new Propositions();
	protected Proposition but;
	
	public Resolution() {
		base_de_regles = new Vector<Regle>();
		base_de_faits = new Propositions();
		but = new Proposition();
	}

	public void chainage_avant() {
		while(!this.base_de_faits.contains(but)) {
			for(Object regle : this.base_de_regles) {
				Regle r = (Regle)regle;
				if(this.base_de_faits.contains(r.get_premisses())) {
					this.base_de_faits.set(r.get_conclusion());
				}
			}
		}
	}

	public void chainage_arriere() {

	}

	public void run() {
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

				System.out.println("\n=== BF AVANT ===");
				System.out.println(base_de_faits);
				chainage_avant();

			break;
			case "2":
				chainage_arriere();

				break;
			default:
				break;
		}

		System.out.println("\n=== BF APRES ===");
		System.out.println(base_de_faits);
	}
}