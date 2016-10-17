#include <iostream>
#include <vector>

#include "Regle.h"
#include "Propositions.h"
#include "Proposition.h"

using namespace std;

void chainage_avant(const vector<Regle>& base_de_regles, Propositions& base_de_faits, Proposition& but) {
	while (!base_de_faits.contains(but)) {
		for (Regle r : base_de_regles) {
			if (base_de_faits.contains(r.get_premisses())) {
				base_de_faits.set(r.get_conclusion());
			}
		}
	}
}

int main() {
	vector<Regle> base_de_regles;
	Propositions base_de_faits;
	Proposition but;

	// Création des règles et ajout à la base de règles

	Regle r1(
		{ Proposition("action", "glisser"), Proposition("environement", "neige") },
		Proposition("sport", "ski_de_fond")
	);
	base_de_regles.push_back(r1);

	// Création du but

	but = Proposition("sport", "ski_de_fond");

	int strategie = 1;
	switch (strategie)
	{
		case 1:
			// Création des faits et ajout à la base de faits
			
			base_de_faits.set("action", "glisser"); // this.["action"] = "glisser"
			base_de_faits.set("environnement", "neige");

			chainage_avant(base_de_regles, base_de_faits, but);

			break;
		case 2:
			chainage_arriere(base_de_regles, but);

			break;
		default:
			break;
	}

	return EXIT_SUCCESS;
}