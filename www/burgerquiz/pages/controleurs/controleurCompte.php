<?php
	/*--CONTROLEUR-DE-LA-SECTION-COMPTE-APPELE-PAR-LE-CONTROLEUR-PRINCIPAL--*/
	
	/*----------------------------------------------------------------------*/
	/*-----Si l'utilisateur N'EST PAS connecté, connexion ou inscription----*/
	/*----------------------------------------------------------------------*/
	
	if(empty($_SESSION['utilisateur'])) {
	
		$utilisateur = new Utilisateur;
		
		/*---------------------------------------------*/
		/*--------------PAGE-DE-CONNEXION--------------*/
		/*---------------------------------------------*/
			
		if(!empty($_GET['erreur'])) {
			$message = 'Erreur de saisie';
		} else {
			$message = '';
		}
		
		if(!strcmp($_GET['page'], 'inscription')) {
			
			include_once('pages/vues/inscription.php');
			
		/*---------------------------------------------*/
		/*--------------PAGE-D'INSCRIPTION-------------*/
		/*---------------------------------------------*/
		
		} else if(!strcmp($_GET['page'], 'connexion')) {
			include_once('pages/vues/connexion.php');
			
		/*---------------------------------------------*/
		/*---------ACCES AU COMPTE-ET-GESTION----------*/
		/*----------CONNEXION-OU-INSCRIPTION-----------*/
		/*---------------------------------------------*/
		
		} else {
			if(!empty($_GET['action'])) {
					
				/*------------------CONNEXION------------------*/
				
				if(!empty($_POST['pseudo']) && !empty($_POST['login']) && !strcmp($_GET['action'], 'connexion')) {
					if($utilisateur->readUtilisateur($_POST['pseudo'], $_POST['login'])) {
						$_SESSION['utilisateur'] = $utilisateur;
						header('Location: index.php');
					} else {
						header('Location: index.php?page=connexion&erreur=1');
					}
					
				/*-----------------INSCRIPTION-----------------*/
				
				} else if(!empty($_POST['pseudo']) && !empty($_POST['login']) && !strcmp($_GET['action'], 'inscription')) {
					if($utilisateur->createUtilisateur($_POST['pseudo'], $_POST['login'])) {
						$_SESSION['utilisateur'] = $utilisateur;
						header('Location: index.php');
					} else {
						header('Location: index.php?page=inscription&erreur=1');;
					}
				} else if(!strcmp($_GET['action'], 'connexion')) {
					header('Location: index.php?page=connexion&erreur=1');
				} else if(!strcmp($_GET['action'], 'inscription')) {
					header('Location: index.php?page=inscription&erreur=1');
				} else {
					header('Location: index.php');
				}
				
			} else {
				header('Location: index.php');
			}
		}
	
	/*----------------------------------------------------------------------*/
	/*----------Si l'utilisateur EST connecté, affichage du compte----------*/
	/*----------------------------------------------------------------------*/
		
	} else {
		$utilisateur = &$_SESSION['utilisateur'];
		$pseudo = $utilisateur->getPseudo();
		
		/*----------EN-CAS-DE-SUPPRESSION-DU-COMPTE----------*/
		
		if(!empty($_GET['delete'])) {
			if(!strcmp($_GET['delete'], 'all')) {
				$utilisateur->deleteUtilisateur();
				unset($_SESSION['utilisateur']);
				header('Location: index.php');
			} else if(!strcmp($_GET['delete'], 'scores')) {
				$historique = new Historique();
				$historique->deleteHistorique($utilisateur->getPseudo());
			}
		}
		
		/*-----------------EN-CAS-DE-DECONNEXION-----------------*/
		
		if(!empty($_GET['action'])) {			
			if(!strcmp($_GET['action'], 'deconnexion')) {
				unset($_SESSION['utilisateur']);
				header('Location: index.php');
			}
		}
		
		/*-----AFFICHAGE-DU-COMPTE-ET-DES-DONNEES-ASSOCIEES------*/
		
		$utilisateur->setScoresUtilisateur();
		$tabScores = $utilisateur->getScoresUtilisateur();
		$tabDates = $utilisateur->getScoresUtilisateurDates();
		
		$total = count($tabScores);
		if($total != 0) {				// Si l'utilisateur a fait au moins une partie
			$moyenne = round(array_sum($tabScores)/$total);
			$pire = end($tabScores);
			$meilleur = $tabScores[0];
		} else {						// Si l'utilisateur n'a joué à aucune partie
			$moyenne = $pire = $meilleur = '/';
		}
		
		include_once('pages/vues/compte.php');
	}
?>