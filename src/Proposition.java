
public class Proposition implements Comparable<Proposition> {

	protected String variable;
	protected String valeur;
	
	// constructeurs

	public Proposition() {
		this("", "");
	}

	public Proposition(String variable, String valeur) {
		this.variable = variable;
		this.valeur = valeur;
	}

	// accesseurs
	
	public String get_variable() {
		return this.variable;
	} 

	public String get_valeur() {
		return this.valeur;
	}

	// mutateurs

	public void set_variable(String variable) {
		this.variable = variable;
	}

	public void set_valeur(String valeur) {
		this.valeur = valeur;
	}

	// opérateur de sortie

	public String toString() {
		String res = "";
		res += variable;
		if (!valeur.equals("")) res += "=" + valeur;
		return res;
	}

	// clone

	public Proposition clone() {
		Proposition res = new Proposition();
		res.set_variable(this.variable);
		res.set_valeur(this.valeur);
		return res;
	}

	// comparable

	public int compareTo(Proposition p) {
		if (this.variable.equals(p.get_variable()) && this.valeur.equals(p.get_valeur())) {
			return 0;
		}
		return -1;
	}
}