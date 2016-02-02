<?php

/*---------------------------------------------------------------------*/
/*---------------------------CLASSE CATEGORIE--------------------------*/
/*--------------Récupères les données de la table associée-------------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Categorie {
	private $_categories;
	
	public function __construct() {
		$this->readCategories();
	}
	
	public function readCategories() {
		require('pages/bdd_connect.php');
		$req = $db->query('SELECT * FROM categorie ORDER BY RAND()');
		$this->_categories = array();
		while($donnees = $req->fetch()) {
			$req2 = $db->query('SELECT question.AUTRE as AUTRE, question.UN as UN FROM question, categorie WHERE categorie.CATEGORIE = question.CATEGORIE AND categorie.CATEGORIE = "'.$donnees['CATEGORIE'].'"');
			if($req2->rowCount() > 1) {
				
				while($donnees2 = $req2->fetch()) {
					$req3 = $db->query('SELECT sous_question.SOUS_QUESTION FROM question, sous_question WHERE question.UN = sous_question.UN AND question.AUTRE = sous_question.AUTRE AND question.UN = "'.$donnees2['UN'].'" AND question.AUTRE = "'.$donnees2['AUTRE'].'"');
					if($req3->rowCount() > 2 && !(in_array($donnees['CATEGORIE'], $this->_categories))) {
						array_push($this->_categories, $donnees['CATEGORIE']);
					} else if(in_array($donnees['CATEGORIE'], $this->_categories)) {
						array_pop($this->_categories);
					}
					$req3->closeCursor();
				}
					
			}
			$req2->closeCursor();
		}
		$req->closeCursor();
	}
	
	public function getCategories() { return $this->_categories; }
}

?>
