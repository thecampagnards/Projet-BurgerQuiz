<?php
	require ('pages/modeles/indexClasses.php');
	session_start();
	
	/*-----------------------------------------------------------------------------------*/
	/*-------------------------CONTROLEUR PRINCIPAL DU SITE------------------------------*/
	/*----------------------------Tout passe par l'index---------------------------------*/
	
	/*INCLUSION DU HEAD ET DU MENU COMMUNS A TOUTES LES PAGES*/
	require('pages/vues/header.php');
	
	if(!empty($_SESSION['utilisateur'])) {
		$sectionMenu2 = '<a href="index.php?page=compte"><span>Compte</span><i class="icon-user-1"></i></a>';
		$sectionMenu4 = '<a href="index.php?page=compte&action=deconnexion"><span>Déconnexion</span></a>';
		$state = 'connecte';
		
	} else {
		$sectionMenu2 = '<a href="index.php?page=inscription"><span>Inscription</span><i class="icon-plus"></i></a>';
		$sectionMenu4 = '<a href="index.php?page=connexion"><span>Connexion</span></a>';
		$state = 'deconnecte';
	}
	require('pages/vues/menu.php');
	
	$categories = new Categorie();
	$jeu = (count($categories->getCategories()) < 3) ? 'off' : 'on'; // On active ou non le jeu
	
	/*INCLUSION DE LA PAGE DEMANDEE*/
	
	if(empty($_GET['page'])) {
		if(!empty($_SESSION['partie'])) unset($_SESSION['partie']);
		if(!empty($_SESSION['adversaire'])) unset($_SESSION['adversaire']);
		require('pages/vues/accueil.php');
	} else {
		if(!strcmp($_GET['page'], 'partie')) {
			require ('pages/controleurs/controleurPartie.php');
		} else if(!strcmp($_GET['page'], 'salon')) {
			require ('pages/controleurs/controleurSalon.php');
		} else if(!strcmp($_GET['page'], 'palmares')) {
			require ('pages/controleurs/controleurPalmares.php');
		} else if(!strcmp($_GET['page'], 'compte') || !strcmp($_GET['page'], 'inscription') || !strcmp($_GET['page'], 'connexion')) {
			require ('pages/controleurs/controleurCompte.php');
		} else if(!strcmp($_GET['page'], 'regles')) {
			require ('pages/vues/regles.php');
		} else {
			require ('pages/vues/error404.php');
		}
	}
	
	/*INCLUSION DU FOOTER COMMUN A TOUTES LES PAGES*/
	require('pages/vues/footer.php');
?>
