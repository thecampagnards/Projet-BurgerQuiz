<?php
	/*--CONTROLEUR-DE-LA-SECTION-PARTIE-APPELE-PAR-LE-CONTROLEUR-PRINCIPAL--*/
	
	/*----------------------------------------------------------------------*/
	/*-----Récupération de la partie en cours, ou création d'une partie-----*/
	/*----------------------------------------------------------------------*/
	
	$partie = &$_SESSION['partie'];
	if (!is_object($partie)) {
		$partie = new Partie();
	}
	
	/*----------------------------------------------------------------------*/
	/*-----------------------SI-PARTIE-MULTIJOUEUR:-------------------------*/
	/*----------Recuperation du pseudo de l'adversaire et création----------*/
	/*----------d'une partie commune (mêmes questions, même ordre)----------*/
	/*----------------------------------------------------------------------*/
	
	if(isset($_GET['adversaire'])) {
		$_SESSION['adversaire'] = $_GET['adversaire'];
		$partie = unserialize(file_get_contents("js/partie.json"));
		$partie->setTempsPrecedent();
	}
	
	$etape = $partie->getEtape();
	
	/*----------------------------------------------------------------------*/
	/*------ETAPE-0:-LE-JEU-N'A-PAS-COMMENCE:-AFFICHAGE-DES-CATEGORIES------*/
	/*----------------------------------------------------------------------*/
	
	if(!empty($_GET['partie']) && !empty($_SESSION['utilisateur'])) {
		
		if($etape == 0 && !strcmp($_GET['partie'], 'start')) {
			
			$questions = $partie->getQuestions();
			$partie->setEtape(1);
			include_once('pages/vues/partieSoloStart.php');
		
	/*----------------------------------------------------------------------*/
	/*-------ETAPE-1:-JEU-EN-COURS:-AFFICHAGE-QUESTIONS-PUIS-REPONSES-------*/
	/*----------------------------------------------------------------------*/
	
		} else if($etape == 1 && !strcmp($_GET['partie'], 'play'))  {
			
			$partie->setTemps(); // MISE A JOURS DU TEMPS
			
			/*----------------------RECUPERATION-DES-DONNEES-DU-JEU----------------------*/
			
			$questions = $partie->getQuestions();
			$SousQuestions = $partie->getSousQuestions();
			$nbSousQuestions = $partie->getNbSousQuestions();
			$numSousQuestion = $partie->getNumSousQuestion();
			
			/*----------------------DETERMINATION DES SOUS QUESTIONS ACTUELLES ET PRECEDENTES----------------------*/
			/*--------Il existe des cas particulier comme par exemple la première question ou il n'y a pas---------*/
			/*------------------------la réponse de la question précédente à afficher avant------------------------*/
			
			if(isset($SousQuestions[$numSousQuestion - 2]) && isset($SousQuestions[$numSousQuestion-1])) { // Si on est pas à la 1ére question
				$sousQuestionActuelle = $SousQuestions[$numSousQuestion - 1];
				$sousQuestionPrecedente = $SousQuestions[$numSousQuestion - 2];
			} else if(!isset($SousQuestions[$numSousQuestion - 2])) { // Si on est à la 1ére question (pas d'affichage de réponse car pas de question précédente)
				$sousQuestionActuelle = $SousQuestions[$numSousQuestion - 1]; 
				$sousQuestionPrecedente = 0;
			} else if (!isset($SousQuestions[$numSousQuestion-1])) { // Si on est aprés la derniere question (affichage de la réponse uniquement)
				$sousQuestionActuelle = 0;
				$sousQuestionPrecedente = $SousQuestions[$numSousQuestion - 2];
			}
			
			/*------------------------DETERMINATION DE L'UN ET L'AUTRE ACTUEL ET PRECEDENT------------------------*/
			
			if($numSousQuestion <= $nbSousQuestions['question1'] + 1) {
				$lunPrecedent = $questions['lun1'];
				$lautrePrecedent = $questions['lautre1'];
			} else {
				$lunPrecedent = $questions['lun2'];
				$lautrePrecedent = $questions['lautre2'];
			}
				
			if($numSousQuestion <= $nbSousQuestions['question1']) {
				$lun = $questions['lun1'];
				$lautre = $questions['lautre1'];
			} else {
				$lun = $questions['lun2'];
				$lautre = $questions['lautre2'];
			}
			
			/*--------------------SI-ARIVEE-A-LA-FIN-DU-JEU,-PASSAGE-AUX-RESULTATS--------------------*/
			
			if($numSousQuestion == $nbSousQuestions['question1'] + $nbSousQuestions['question2']+1) {
				$partie->setEtape(2);
			}
			
			$partie->setNumSousQuestion(1); // Incrémentation du compteur de questions de 1
			
			/*-----------TRAITEMENT-DES-REPONSES-DE-L'UTILISATEUR-ET-ATTRIBUTION-DES-POINTS-----------*/
			
			$classlun = $classlautre = $classlesdeux = 'faux'; // Couleur par défaut des 3 blocs de réponse: rouge (faux)
			
			if(isset($_GET['choix'])) {
				$bonnesreponses = $partie->getReponses();
				if($_GET['choix'] == $bonnesreponses[$numSousQuestion - 2]) {
					$partie->setPoints('vrai');
				} else {
					$partie->setPoints('faux');
				}
				
				if($bonnesreponses[$numSousQuestion - 2] == 0) {			/*--On colore uniquement la bonne--*/
					$classlun = 'vrai';										/*--------réponse en vert----------*/
				} else if($bonnesreponses[$numSousQuestion - 2] == 1) {
					$classlautre = 'vrai';
				} else {
					$classlesdeux = 'vrai';
				}
			}
			
			/*------------------AFFICHAGE-DU-NB-DE-BONNES-REPONSE-ET-DE-L'ETAPE-EN-BAS-DE-PAGE--------------*/
			
			$texteEtape = 'Q'.$numSousQuestion.'/'.($nbSousQuestions['question1'] + $nbSousQuestions['question2']);
			$texteEtape2 = $texteEtape;
			$texteEtape2[1] = $texteEtape[1] - 1;
			$nbBonnesReponses = $partie->getPoints();
			
			/*------------------RECUPERATION-DU-TEMPS-MAX-PAR-QUESTION--------------*/
			
			$tempsParQuestion = new Temps();
			$temps = $tempsParQuestion->getTemps() * 1000; // Temps en ms

			/*--------------------EN CAS DE PARTIE MULTIJOUEUR----------------------*/
			/*---------------SI L'AUTRE REPOND PLUS VITE, ON L'AFFICHE--------------*/
			
			if(!empty($_SESSION['adversaire']) && !empty($_GET['choix'])){
				$adversaire = $_SESSION['adversaire'];
				if($_GET['choix'] == '4') {
					$idMessage = 'rapidite';
				} else {
					$idMessage = 'inputhidden';
				}
			} else {
				$adversaire = '';
				$idMessage = 'inputhidden';
			}
			$monpseudo = $_SESSION['utilisateur']->getPseudo();
			$monscore = $_SESSION['partie']->getScore();

			include_once('pages/vues/partieSoloPlay.php');
			
	/*----------------------------------------------------------------------*/
	/*--------------ETAPE-2:-FIN-DU-JEU:-AFFICHAGE-DU-RESULTAT--------------*/
	/*----------------------------------------------------------------------*/
	
		} else if($etape == 2) {
			$score = $partie->getScore();
		
			/*-----------------PARTIE-MULTIJOUEUR-------------------------------*/
			
			if(!empty($_SESSION['adversaire'])) {
				$pseudo = $_SESSION['utilisateur']->getPseudo();
				include_once('pages/vues/partieMultiScore.php');
				
			/*-----------------PARTIE-SOLO--------------------------------------*/
			
			} else {
				include_once('pages/vues/partieSoloScore.php');
			}
		
		} else {
			header('Location: index.php');
		}
	} else {
		header('Location: index.php');
	}
?>
