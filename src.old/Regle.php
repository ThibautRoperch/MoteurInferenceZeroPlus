<?php

class Regle {
	private $premisses;
	private $conclusion;

	public function __construct(&$premisses, &$conclusion) {
		$this->premisses = Propositions(premisses);
		$this->conclusion = conclusion;
	}

	// accesseurs

	public function get_premisses() {
		return $this->premisses;
	}

	public function get_conclusion() {
		return $this->conclusion;
	}

	// mutateurs

	public function set_premisses(&$premisses) {
		$this->premisses = premisses;
	}
	
	public function set_conclusion(&$conclusion) {
		$this->conclusion = conclusion;
	}

	// opérateur de sortie

	public function __toString() {
		$res = "SI ";
		foreach ($this->premisses as $prem) $res .= "$prem ";
		$res .= "ALORS ";
		retourn $res;
	}
}

?>