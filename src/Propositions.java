import java.util.HashMap;
import java.util.Iterator;

public class Propositions implements Comparable<Propositions> {

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

	// accesseurs

	public int getSize() {
		return this.content.size();
	}

	public String get(String variable) {
		return this.content.get(variable);
	}

	// mutateurs

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
			String valeur = ps.content.get(variable);
			this.set(variable, valeur);
		}
	}

	// autres
	
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

	public boolean conflit(String variable, String valeur) {
		return this.content.containsKey(variable) && !this.content.get(variable).equals(valeur);
	}

	public boolean conflit(Proposition p) {
		return this.conflit(p.get_variable(), p.get_valeur());
	}

	public boolean conflit(Propositions ps) {
		Iterator i = ps.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = ps.content.get(variable);
			if (!this.conflit(variable, valeur)) return false;
		}
		return true;
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
			res += "=";
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

	// itérateur

	public Iterator iterator() {
		return this.content.keySet().iterator();
	}

	// comparable

	public int compareTo(Propositions p) {
		Iterator i;
		// Regarde si les propositions de this sont dans p
		i = this.content.keySet().iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = this.content.get(variable);
			if (!p.contains(variable, valeur)) {
				return -1;
			}
		}
		// Regarde si les propositions de p sont dans this
		i = p.iterator();
		while (i.hasNext()) {
			String variable = (String)i.next();
			String valeur = p.get(variable);
			if (!this.contains(variable, valeur)) {
				return 1;
			}
		}
		return 0;
	}
}