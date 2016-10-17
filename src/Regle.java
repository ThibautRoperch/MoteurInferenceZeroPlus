
public class Regle {

	private Propositions _premisses;
	private Proposition _conclusion;

	// constructeurs

	public Regle(Proposition[] premisses, Proposition conclusion) {
		_premisses = Propositions(premisses);
		_conclusion = conclusion;
	}

	public Regle(Propositions premisses, Proposition conclusion) {
		_premisses = premisses;
		_conclusion = conclusion;
	}

	// accesseurs

	public Propositions get_premisses() {
		return _premisses;
	}

	public Proposition get_conclusion() {
		return _conclusion;
	}

	// mutateurs

	public void set_premisses(Propositions premisses) {
		_premisses = premisses;
	}

	public void set_conclusion(Proposition conclusion) {
		_conclusion = conclusion;
	}

	// operateur de sortie

	public String toString() {
		String res = "SI ";
		for (Proposition prem : _premisses) res += prem + " ";
		res += "ALORS " + conclusion;
		return res;
	}
}