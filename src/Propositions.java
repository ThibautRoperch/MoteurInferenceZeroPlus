import java.util.HashMap;
import java.util.Iterator;

public class Propositions {

	protected HashMap content;

	// constructeur

	public Propositions() {
		this.content = new HashMap();
	}

	// autres

	public void set(Proposition p) {
		this.content.put(p.get_variable(), p.get_valeur());
	}

	public void set(String variable, String value) {
		this.content.put(variable, value);
	}

	public boolean contains(Proposition p) {
		return this.content.containsKey(p.get_variable()) && this.content.get(p.get_variable()) == p.get_valeur();
	}

	public boolean contains(Propositions p) {
		Iterator i = p.content.keySet().iterator();
		while (i.hasNext())
		{
			Proposition prop = (Proposition)i.next();
			if (!contains(prop)) return false;
		}
		return true;
	}

	// operateur de sortie

	public String toString() {
		String res = "";

		Iterator i = this.content.keySet().iterator();
		while (i.hasNext())
		{
			String variable = (String)i.next();
			res += variable;
			res += " = ";
			res += (String)this.content.get(variable);

			if (i.hasNext()) {
				res += " && ";
			}
		}

		return res;
	}
}