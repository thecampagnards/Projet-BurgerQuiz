<?php

/*---------------------------------------------------------------------*/
/*-----------------------------CLASSE PARTIE---------------------------*/
/*------Contient toutes les informations nécessaires à une partie------*/
/*---------------------------------------------------------------------*/
/*-----------Auteurs: David Delemotte & Konstantin Sidorenko-----------*/
/*---------------------------------------------------------------------*/

class Partie {
	private $_questions;
	private $_sousQuestions;
	private $_nbSousQuestions;
	private $_reponses;
	private $_etape;
	private $_numSousQuestion;
	private $_points;
	private $_temps;
	
	public function __construct() {
		$this->setQuestions();
		$this->setSousQuestions();
		$this->setEtape();
		$this->setNumSousQuestion();
		$this->setTemps();
		$this->setTempsPrecedent();

		$this->_points = 0;
		$this->_temps['total'] = 0;
	}
	
	/*--------------------------------------------------------------------------------------------*/
	/*---Récupération de deux catégories et de deux jeux de questions associés à ces catégories---*/
	/*--------------------------------------------------------------------------------------------*/
	
	private function setQuestions() {
		
		$categories = new Categorie;
		$questions = new Question;
		
		$questions->readQuestions($categories->getCategories());
		$tabUn = $questions->getUn();
		$tabAutre = $questions->getAutre();
		$this->_questions = array ('lun1' => $tabUn[0], 'lautre1' => $tabAutre[0], 'lun2' => $tabUn[1], 'lautre2' => $tabAutre[1]);
	}
	
	/*----------------------------------------------------------------------------------------------*/
	/*---Récupération de sous questions associées aux jeux de questions précédemment selectionnés---*/
	/*----------------------------------------------------------------------------------------------*/
	
	private function setSousQuestions() {
	
		$sousQuestions = new Sousquestion;
		$sousQuestions->readSousQuestion($this->_questions);
		
		$this->_sousQuestions = $sousQuestions->getSousQuestion();
		$this->_reponses = $sousQuestions->getReponse();
		
		$tabUn = $sousQuestions->getUn();
		$nb = array_count_values($tabUn);
		$this->_nbSousQuestions = array ('question1' => $nb[$tabUn[0]], 'question2' => (count($tabUn) - $nb[$tabUn[0]]));
	}
	
	/*-----------------------------------------------------------------------------------------------*/
	/*---Permet de définir l'étape du jeu à laquelle on est. Par défaut, vaut 0 (lancement du jeu)---*/
	/*-----------------------------------------------------------------------------------------------*/
	
	public function setEtape($etape = 0) {
		$this->_etape = $etape;
	}
	
	/*----------------------------------------------------------------------------------*/
	/*---Permet de définir le numéro de la sous question actuelle. Par défaut, vaut 0---*/
	/*----------------------------------------------------------------------------------*/
	
	public function setNumSousQuestion($num = 0) {
		if($num = 0) {
			$this->_numSousQuestion = $num;
		} else {
			$this->_numSousQuestion++;
		}
	}
	
	/*-------------------------------------------------------------------*/
	/*---Incrémentation du compteur des points si la réponse est juste---*/
	/*-------------------------------------------------------------------*/
	
	public function setPoints($resultat) {
		if(!strcmp($resultat, 'vrai')) {
			$this->_points+= 1;
		}
	}
	
	/*-----------------------------------------------------------*/
	/*---Incrémentation du compteur du temps passé dans le jeu---*/
	/*-----------------------------------------------------------*/
	
	public function setTemps() {
			if(!empty($this->_temps['precedent'])) {
				$this->_temps['total'] += (strtotime(date("H:i:s")) - strtotime($this->_temps['precedent']));
			} else {
				$this->_temps['total'] = 0;
			}
			$this->_temps['precedent'] = date("H:i:s");
	}
	
	public function setTempsPrecedent() {
		$this->_temps['precedent'] = NULL;
	}

	/*-----------*/
	/*--GETTERS--*/
	/*-----------*/
	
	public function getQuestions() { return $this->_questions; }
	public function getSousQuestions() { return $this->_sousQuestions; }
	public function getReponses() { return $this->_reponses; }
	public function getEtape() { return $this->_etape; }
	public function getNbSousQuestions() { return $this->_nbSousQuestions; }
	public function getNumSousQuestion() { return $this->_numSousQuestion; }
	public function getPoints() { return $this->_points; }
	public function getTemps() { return $this->_temps; }
	
	/*--------------------------------------------------------------------------------------------*/
	/*---Calcul du score entre 0 et 100 en fonction du % de bonnes réponses et du temps (malus)---*/
	/*--------------------------------------------------------------------------------------------*/
	
	public function getScore() {
		$pourcentagePoints = ($this->_points / count($this->_sousQuestions)) * 100;
		$malustemps = $this->_temps['total'] / count($this->_sousQuestions);
		
		if(round($pourcentagePoints - $malustemps) > 0) {
			return round($pourcentagePoints - $malustemps);
		} else {
			return 0;
		}
	}
}
?>
