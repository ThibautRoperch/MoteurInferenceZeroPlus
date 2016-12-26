
public class Regle implements Comparable<Regle> {

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

	public void reset() {
		indexation = 1;
	}

	// accesseurs

	public Propositions get_premisses() {
		return this.premisses;
	}

	public Proposition get_conclusion() {
		return this.conclusion;
	}

	public int get_indice() {
		return this.indice;
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

	// autres

	public String toFile() {
		String res = "SI\n" + this.premisses.toString("\n") + "\nALORS\n" + this.conclusion;
		return res;
	}

	// comparable (implements Comparable avec une autre Regle, utilisée lors de l'ajout d'une Regle ssi elle n'est pas déjà dans le vecteur de Regle)

    public int compareTo(Regle r) {
		// < 0	r est avant this
		// > 0	r est après this
		// 0	r est égal à this
        if (this.premisses.compareTo(r.get_premisses()) == 0 && this.conclusion.compareTo(r.get_conclusion()) == 0) {
			return 0;
		}
		return this.indice - r.get_indice();
    }
}