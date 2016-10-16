<?php

function chainage_avant(&$base_de_regles, &$base_de_faits, &$but) {
	while (!$base_de_faits.contains($but)) {
		for (Regle $r : $base_de_regles) {
			if ($r.validate($base_de_faits)) {
				$base_de_faits.insert($r.get_conclusion());
			}
		}
	}
}

$base_de_regles = array();
$base_de_faits = array();
$but;

 // Création des règles et ajout à la base de règles

$r1 = new Regle(
    array(new Proposition("action", "glisser"), new Proposition("environement", "neige")),
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
        
        $base_de_faits.insert( new Proposition("sport", "ski_de_fond") );
        $base_de_faits.insert( new Proposition("sport", "ski_de_fond") );

        chainage_avant($base_de_regles, $base_de_faits, $but);

        break;
    case 2:
        chainage_arriere($base_de_regles, $but);

        break;
    default:
        break;
}

?>