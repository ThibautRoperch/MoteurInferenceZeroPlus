
public class Regle {

	protected static int indexation = 1;

	protected int indice;
	protected Propositions premisses;
	protected Proposition conclusion;

	// constructeurs

	public Regle() {
		indice = indexation++;
	}

	public Regle(Proposition premisse, Proposition conclusion) {
		this();
		this.premisses = new Propositions(premisse.clone());
		this.conclusion = conclusion.clone();
	}

	public Regle(Propositions premisses, Proposition conclusion) {
		this();
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
		String res = "R" + indice + "\tSI " + this.premisses.toString(" ET ") + " ALORS " + this.conclusion;
		return res;
	}
}