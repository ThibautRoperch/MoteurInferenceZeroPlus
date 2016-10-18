
public class Regle {

	protected Propositions premisses;
	protected Proposition conclusion;

	// constructeurs

	public Regle(Proposition premisse, Proposition conclusion) {
		this.premisses = new Propositions(premisse);
		this.conclusion = conclusion.clone();
	}

	public Regle(Propositions premisses, Proposition conclusion) {
		this.premisses = premisses.clone();
		this.conclusion = conclusion.clone();
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
		String res = "SI " + this.premisses.toString(" && ") + " ALORS " + this.conclusion;
		return res;
	}
}