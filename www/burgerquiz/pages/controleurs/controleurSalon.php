<?php
	/*--CONTROLEUR-DE-LA-SECTION-COMPTE-APPELE-PAR-LE-CONTROLEUR-PRINCIPAL--*/
	
	/*----------------------------------------------------------------------*/
	/*----Si l'utilisateur EST connecté, il peut attendre un autre joueur---*/
	/*----------------------------------------------------------------------*/
	
	if(!empty($_SESSION['utilisateur'])) {
		$pseudo = $_SESSION['utilisateur']->getPseudo();
		
		/*CREATION D'UNE PARTIE ET ENREGISTREMENT DE CELLE-CI DANS UN FICHIER*/
		/*AFIN DE L'ENVOYER A L'AUTRE UTILISATEUR AFIN QU'ILS AIENT LES MEMES*/
		/*QUESTIONS DANS LE MEME ORDRE*/
		
		$partie = new Partie();
		file_put_contents("js/partie.json", serialize($partie));
		include_once('pages/vues/salon.php');
	
	/*----------------------------------------------------------------------*/
	/*---------Si l'utilisateur N'EST PAS connecté, retour à l'index--------*/
	/*----------------------------------------------------------------------*/
	
	} else {
		header('Location: index.php');
	}
?>