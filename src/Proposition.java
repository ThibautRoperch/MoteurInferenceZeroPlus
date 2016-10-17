
public class Proposition {

    protected String variable;
    protected String valeur;

	// accesseurs
    
    public String get_variable() {
        return variable;
    } 

    public String get_valeur() {
        return valeur;
    }
	
    // constructeurs

    public Proposition(String variable, String valeur) {
        this.variable = variable;
        this.valeur = valeur;
    }

	public String toString() {
		String res = "";
		res += variable + " = " + valeur;
		return res;
	}


}