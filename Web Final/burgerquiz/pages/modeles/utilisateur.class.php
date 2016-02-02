<?php

/*---------------------------------------------------------------------*/
/*--------------------------CLASSE UTILISATEUR-------------------------*/
/*--------------Récupères les données de la table associée-------------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Utilisateur {
	private $_pseudo;
	private $_scoresUtilisateur;
	private $_scoresUtilisateurDates;
	
	public function __construct() {
		$this->_pseudo = "";
	}
	
	/*------------------------------------------------------*/
	/*---Lecture de l'utilisateur dans la base de données---*/
	/*------------------------------------------------------*/
	
	public function readUtilisateur($pseudo, $login) {
		require('pages/bdd_connect.php');
		$login = hash('sha512',$login);
		$req = 'SELECT * FROM utilisateur WHERE LOWER(PSEUDO) = ? AND MOT_DE_PASSE = ?';
		$stmt = $db->prepare($req);
		$stmt->bindValue(1, $pseudo, PDO::PARAM_STR);
		$stmt->bindValue(2, $login, PDO::PARAM_STR);
		$stmt->execute();
		if($stmt->rowCount() == 1) {
			$this->_pseudo = $pseudo;
			return true;
		} else {
			return false;
		}
		
		$stmt->closeCursor();
	}
	
	/*--------------------------------------------------------*/
	/*---Insertion d'un utilisateur dans la base de données---*/
	/*--------------------------------------------------------*/
	
	public function createUtilisateur($pseudo, $login) {
		require('pages/bdd_connect.php');
		$login = hash('sha512',$login);
		$req = 'SELECT * FROM utilisateur WHERE LOWER(PSEUDO) = ? ';
		$stmt = $db->prepare($req);
		$stmt->bindValue(1, $pseudo, PDO::PARAM_STR);
		$stmt->execute();
		if($stmt->rowCount() == 0) {
			$req = 'INSERT INTO utilisateur VALUES (?,?)';
			$stmt = $db->prepare($req);
			$stmt->bindValue(1, $pseudo, PDO::PARAM_STR);
			$stmt->bindValue(2, $login, PDO::PARAM_STR);
			$stmt->execute();
			$this->_pseudo = $pseudo;
			return true;
		} else {
			return false;
		}
		
		$stmt->closeCursor();
	}
	
		
	/*----------------------------------------------------------*/
	/*---Suppression d'un utilisateur dans la base de données---*/
	/*----------------------------------------------------------*/
	
	public function deleteUtilisateur() {
		require('pages/bdd_connect.php');
		$req = $db->query('DELETE FROM utilisateur WHERE LOWER(PSEUDO) = "'.$this->_pseudo.'"');
		$req->closeCursor();
	}
	
	/*------------------------------------------------------------------------------*/
	/*---Récupération des scores associés à l'utilisateur dans la base de données---*/
	/*------------------------------------------------------------------------------*/
	
	public function setScoresUtilisateur() {
		require('pages/bdd_connect.php');
		
		$this->_scoresUtilisateur = array ();
		$this->_scoresUtilisateurDates = array ();
		
		$req = $db->query('SELECT SCORE, DATE_HISTORIQUE, DATE(DATE_HISTORIQUE) as DATEYMD FROM historique, utilisateur WHERE utilisateur.PSEUDO = historique.PSEUDO AND LOWER(utilisateur.PSEUDO) = "'.strtolower($this->_pseudo).'" ORDER BY SCORE DESC');
		
		while($donnees = $req->fetch()) {
			array_push($this->_scoresUtilisateur, $donnees['SCORE']);
			array_push($this->_scoresUtilisateurDates, $donnees['DATEYMD']);
		}
		
		$req->closeCursor();
	}
	
	/*-------------*/
	/*---GETTERS---*/
	/*-------------*/
	
	public function getPseudo() { return $this->_pseudo; }
	public function getScoresUtilisateur() { return $this->_scoresUtilisateur; }
	public function getScoresUtilisateurDates() { return $this->_scoresUtilisateurDates; }
}