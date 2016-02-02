<?php

/*---------------------------------------------------------------------*/
/*---------------------------CLASSE HISTORIQUE-------------------------*/
/*--------------Récupères les données de la table associée-------------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Historique {
	private $_pseudo;
	private $_score;
	private $_dateHistorique;
	private $_idHistorique;
	
	public function __construct() {
		$this->readHistorique();
	}
	
	public function readHistorique() {
		require('pages/bdd_connect.php');
		
		$this->_pseudo = array ();
		$this->_score = array ();
		$this->_dateHistorique = array ();
		$this->_idHistorique = array ();
		
		$req = $db->query('SELECT * FROM historique ORDER BY SCORE DESC LIMIT 10');
		
		while($donnees = $req->fetch()) {
			array_push($this->_pseudo, $donnees['PSEUDO']);
			array_push($this->_score, $donnees['SCORE']);
			array_push($this->_dateHistorique, $donnees['DATE_HISTORIQUE']);
			array_push($this->_idHistorique, $donnees['ID_HISTORIQUE']);
		}
		$req->closeCursor();
	}
	
	public function createHistorique($pseudo, $score) {
	
		require('pages/bdd_connect.php');
	
		if(!empty($pseudo) && isset($score)) {
			if($score >= 0 && $score <= 100) {
				$req = $db->query('INSERT INTO historique VALUES ("'.$pseudo.'", '.$score.', CURRENT_TIMESTAMP, NULL)');
				$req->closeCursor();
			}
		}
	}
	
	public function deleteHistorique($pseudo) {
	
		require('pages/bdd_connect.php');
	
		if(!empty($pseudo)) {
				$req = $db->query('DELETE FROM historique WHERE LOWER(PSEUDO) = "'.strtolower($pseudo).'"');
				$req->closeCursor();
		}
	}
	
	public function getPseudo() { return $this->_pseudo; }
	public function getScore() { return $this->_score; }
	public function getDateHistorique() { return $this->_dateHistorique; }
	public function getIdHistorique() { return $this->_idHistorique; }
	
}