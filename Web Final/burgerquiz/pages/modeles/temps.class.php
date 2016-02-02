<?php

/*---------------------------------------------------------------------*/
/*------------------------------CLASSE TEMPS---------------------------*/
/*--------------Récupères les données de la table associée-------------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Temps {
	private $_temps;
	
	public function __construct() {
		$this->readTemps();
	}
	
			
	/*--------------------------------------------------------*/
	/*---Lecture des sous-questions dans la base de données---*/
	/*--------------------------------------------------------*/
	
	public function readTemps() {
		require('pages/bdd_connect.php');
		$req = $db->query('SELECT * FROM temps');
		$donnees = $req->fetch();
		$this->_temps = $donnees['TEMPS'];
		$req->closeCursor();
	}
	
	/*-------------*/
	/*---GETTERS---*/
	/*-------------*/
	
	public function getTemps() { return $this->_temps; }
}

?>