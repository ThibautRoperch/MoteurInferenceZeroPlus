import java.util.HashMap;
import java.util.Iterator;

public class Propositions {

	protected HashMap<String, String> content;

	// constructeur

	public Propositions() {
		this.content = new HashMap<String, String>();
	}

	public Propositions(Proposition p) {
		this();
		this.set(p);
	}

	public Propositions(Propositions ps) {
		this();
		Iterator i = ps.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = ps.content.get(variable);
			this.set(variable, valeur);
		}
	}

	// autres

	public void set(String variable, String valeur) {
		this.content.put(variable, valeur);
	}
	
	public void set(Proposition p) {
		this.set(p.get_variable(), p.get_valeur());
	}

	public void set(Propositions ps) {
		Iterator i = ps.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = this.content.get(variable);
			this.set(variable, valeur);
		}
	}
	
	public boolean contains(String variable, String valeur) {
		if (valeur.equals("")) return this.content.containsKey(variable); 
		return this.content.containsKey(variable) && this.content.get(variable).equals(valeur);
	}
	
	public boolean contains(Proposition p) {
		return this.contains(p.get_variable(), p.get_valeur());
	}

	public boolean contains(Propositions ps) {
		Iterator i = ps.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = ps.content.get(variable);
			if (!this.contains(variable, valeur)) return false;
		}
		return true;
	}

	public boolean conflit(Proposition p) {
		return this.content.containsKey(p.get_variable()) && !this.content.get(p.get_variable()).equals(p.get_valeur());
	}

	public void clear() {
		content.clear();
	}

	// operateur de sortie

	public String toString(String separateur) {
		String res = "";

		Iterator i = this.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			res += variable;
			res += " = ";
			res += this.content.get(variable);

			if (i.hasNext()) {
				res += separateur;
			}
		}

		return res;
	}

	// clone

	public Propositions clone() {
		Propositions res = new Propositions();
		Iterator i = this.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = this.content.get(variable);
			res.set(variable, valeur);
		}
		return res;
	}
}