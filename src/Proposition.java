
public class Proposition {

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
		return variable;
	} 

	public String get_valeur() {
		return valeur;
	}

	// opérateur de sortie

	public String toString() {
		String res = "";
		res += variable + " = " + valeur;
		return res;
	}
}