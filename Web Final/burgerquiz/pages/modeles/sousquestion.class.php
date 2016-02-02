<?php

/*---------------------------------------------------------------------*/
/*---------------------------CLASSE SOUSQUESTION-----------------------*/
/*--------------Récupères les données de la table associée-------------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Sousquestion {
	private $_sousQuestion;
	private $_reponse;
	private $_un;
	private $_autre;
	
	public function __construct() {
		$this->_sousQuestion = array ();
		$this->_reponse = array ();
		$this->_un = array ();
		$this->_autre = array ();
	}
	
		
	/*--------------------------------------------------------*/
	/*---Lecture des sous-questions dans la base de données---*/
	/*--------------------------------------------------------*/
	
	public function readSousQuestion($questions) {
		require('pages/bdd_connect.php');
		
		$req = $db->query('SELECT * FROM sous_question, question WHERE question.un = sous_question.un AND question.autre = sous_question.autre AND sous_question.un = "'. $questions['lun1'] .'" AND sous_question.autre = "'. $questions['lautre1'] .'" ORDER BY RAND()');
		
		while($donnees = $req->fetch()) {
			array_push($this->_sousQuestion, $donnees['SOUS_QUESTION']);
			array_push($this->_reponse, $donnees['REPONSE']);
			array_push($this->_un, $donnees['UN']);
			array_push($this->_autre, $donnees['AUTRE']);
		}
		$req->closeCursor();
		
		$req = $db->query('SELECT * FROM sous_question, question WHERE question.un = sous_question.un AND question.autre = sous_question.autre AND sous_question.un = "'. $questions['lun2'] .'" AND sous_question.autre = "'. $questions['lautre2'] .'" ORDER BY RAND()');
		
		while($donnees = $req->fetch()) {
			array_push($this->_sousQuestion, $donnees['SOUS_QUESTION']);
			array_push($this->_reponse, $donnees['REPONSE']);
			array_push($this->_un, $donnees['UN']);
			array_push($this->_autre, $donnees['AUTRE']);
		}
		$req->closeCursor();
	
	}
	
	/*-------------*/
	/*---GETTERS---*/
	/*-------------*/
	
	public function getSousQuestion() { return $this->_sousQuestion; }
	public function getReponse() { return $this->_reponse; }
	public function getUn() { return $this->_un; }
	public function getAutre() { return $this->_autre; }
}
?>