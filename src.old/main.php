<?php

require_once("Regle.php");
require_once("Proposition.php");

function chainage_avant(&$base_de_regles, &$base_de_faits, &$but) {
	while (!isset($base_de_faits[$but.get_type()]) && $base_de_faits[$but.get_type()] != $but.get_value()) {
		foreach ($base_de_regles as $r) {
			$valide = true;
			foreach ($r.get_premisses() as $p) {
				if (isset($base_de_faits[$p.get_type()]) && $base_de_faits[$p.get_type()] == $p.get_value()) {
					$valide = false;
					break;
				}
			}
			if ($valide) $base_de_faits[$r.get_conclusion().get_type()] = $r.get_conclusion().get_value();
		}
	}
}

$base_de_regles = array();
$base_de_faits = array();
$but;

 // Création des règles et ajout à la base de règles

$r1 = new Regle(
	array(new Proposition("action", "glisser"), new Proposition("environnement", "neige")),
	new Proposition("sport", "ski_de_fond")
);
$base_de_regles[] = $r1;

// Création du but

$but = new Proposition("sport", "ski_de_fond");

$strategie = 1;
switch (strategie)
{
	case 1:
		// Création des faits et ajout à la base de faits
		
		$base_de_faits["action"] = "glisser";
		$base_de_faits["environnement"] = "neige";

		chainage_avant($base_de_regles, $base_de_faits, $but);

		break;
	case 2:
		chainage_arriere($base_de_regles, $but);

		break;
	default:
		break;
}

?>