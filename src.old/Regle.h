#ifndef REGLE_H_
#define REGLE_H_

class Regle {

private:
	Propositions _premisses;
	Proposition _conclusion;

public:
	Regle(const Proposition[]& premisses, const Proposition& conclusion) : _premisses(Propositions(premisses)), _conclusion(conclusion) {};
	Regle(const Propositions& premisses, const Proposition& conclusion) : _premisses(premisses), _conclusion(conclusion) {};
	~Regle() {};

	// accesseurs

	Propositions& get_premisses() const { return _premisses; };
	Proposition& get_conclusion() const { return _conclusion; };

	// mutateurs

	void set_premisses(const Propositions& premisses) { _premisses = premisses; };
	void set_conclusion(const Proposition& conclusion) { _conclusion = conclusion; };

	// autres

	std::ostream& print(std::ostream& out) {
		out << "SI ";
		for (auto prem : _premisses) out << prem << " ";
		out << "ALORS " << conclusion;
		retourn out;
	}

	// operateur de sortie

	std::ostream& operator<<(std::ostream& out, const Regle& r) {
		return r.print(out);
	}
};

#endif