
public class Regle {

	protected Propositions premisses;
	protected Proposition conclusion;

	// constructeurs

	public Regle(Proposition premisse, Proposition conclusion) {
		Propositions premisses = new Propositions();
		premisses.set(premisse);
		this.premisses = premisses;
		this.conclusion = conclusion;
	}

	public Regle(Propositions premisses, Proposition conclusion) {
		this.premisses = premisses;
		this.conclusion = conclusion;
	}

	// accesseurs

	public Propositions get_premisses() {
		return this.premisses;
	}

	public Proposition get_conclusion() {
		return this.conclusion;
	}

	// mutateurs

	public void set_premisses(Propositions premisses) {
		this.premisses = premisses;
	}

	public void set_conclusion(Proposition conclusion) {
		this.conclusion = conclusion;
	}

	// operateur de sortie

	public String toString() {
		String res = "SI ";
		for (Proposition prem : this.premisses) res += prem + " ";
		res += "ALORS " + this.conclusion;
		return res;
	}
}