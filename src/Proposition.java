
public class Proposition {

    protected String type;
    protected String valeur;

	// accesseurs
    public String get_type() {
        return type;
    } 

    public String get_valeur() {
        return valeur;
    }

	
    // constructeurs

    public Proposition(String v_variable, String v_valeur) {
        this.variable = v_variable;
        this.valeur = v_valeur;
    }

	public String toString() {
		String res = "";
		res += type + " = " + valeur;
		return res;
	}


}