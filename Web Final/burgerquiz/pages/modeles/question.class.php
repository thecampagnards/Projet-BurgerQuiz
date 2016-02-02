<?php

/*---------------------------------------------------------------------*/
/*----------------------------CLASSE QUESTION--------------------------*/
/*--------------Récupères les données de la table associée-------------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Question {
	private $_un;
	private $_autre;
	private $_categorie;
	
	public function __construct() {
		$this->_un = array ();
		$this->_autre = array ();
		$this->_categorie = array ();
	}
	
	/*----------------------------------------------------------------------*/
	/*---Lecture des questions (l'un et l'autre ) dans la base de données---*/
	/*----------------------------------------------------------------------*/
	
	public function readQuestions($categories) {
		require('pages/bdd_connect.php');
		
		for($i = 0; $i < 2; $i++) {
			$req = $db->query('SELECT * FROM question, categorie WHERE question.categorie = categorie.categorie AND question.categorie = "'. $categories[$i] .'" ORDER BY RAND()');
			$donnees = $req->fetch();
			array_push($this->_un, $donnees['UN']);
			array_push($this->_autre, $donnees['AUTRE']);
			array_push($this->_categorie, $donnees['CATEGORIE']);
			$req->closeCursor();
		}
	}
	
	/*-------------*/
	/*---GETTERS---*/
	/*-------------*/
	
	public function getUn() { return $this->_un; }
	public function getAutre() { return $this->_autre; }
	public function getCategorie() { return $this->_categorie; }
}
?>