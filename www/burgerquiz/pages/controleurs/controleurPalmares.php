<?php
	/*--CONTROLEUR-DE-LA-SECTION-PALMARES-APPELE-PAR-LE-CONTROLEUR-PRINCIPAL--*/
	
	$palmares = new Historique;
	
	/*-----------------------------------------------------------------------*/
	/*----------Si un utilisateur à choisi d'enregistrer son score-----------*/
	/*-----------------------------------------------------------------------*/
	
	if(!empty($_SESSION['partie']) && !empty($_SESSION['utilisateur'])) {
		$partie = &$_SESSION['partie'];
		$utilisateur = &$_SESSION['utilisateur'];
		if(is_object($partie) && empty($_SESSION['adversaire'])) { // SEUL LES SCORES EN SOLO SONT SAUVEGARDES
			if($partie->getEtape() == 2) {
				$palmares->createHistorique($utilisateur->getPseudo(), $partie->getScore()); // On insère le score
				$palmares->readHistorique(); // On recharge l'historique au cas ou le score soit dans le top 10
			}
			unset($_SESSION['partie']);
		}
	}
	
	/*------------------------------------------*/
	/*----------Affichage du palmares-----------*/
	/*------------------------------------------*/
	
	$tabScores = $palmares->getScore();
	$tabPseudos = $palmares->getPseudo();

	include_once('pages/vues/palmares.php');
?>