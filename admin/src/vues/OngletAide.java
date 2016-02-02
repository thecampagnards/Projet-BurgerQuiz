package vues;

import java.awt.Color;

import javax.swing.JTextArea;

/**
 * Objet onglet aide qui est extends d'onglet dans cette onglet il y a les informations
 * de comment utiliser l'interface Java
 * @author Konstantin&David
 * @version 1.0
 */
public class OngletAide extends Onglet{

	/**
	 * Constructeur sans parametre 
	 * on place juste dans la fenetre un JtextArea non editable
	 */
	public OngletAide() {
		super("Aide");

		JTextArea texte = new JTextArea("Pour la navigation :"
				+ "\n\n-En haut de votre fenêtre, vous disposez d'une barre de navigation. Elle est composée de 5 onglets ; configuration du jeu, options, recherche, aide"
				+ "\net à propos. Et de 3 boutons ; rafraichir, déconnexion, et quitter."
				+ "\n\n-Pour naviguer, il suffit de cliquer sur les différents onglets. Le bouton rafraichir a pour effet de rafraichir toutes les listes de chacun des onglets"
				+ "\nen plus d'être rafraichie automatiquement à chaque modification, ajout et suppression de question. Le bouton quitter permet de fermer la fenêtre."
				+ "\nLe bouton déconnexion lui permet de revenir à la fenêtre de connexion pour éventuellement, vous reconnectez."
				+ "\n\n\nPour l'utilisation :"
				+ "\n\n-Pour l'onglet de configuration du jeu ; pour naviguer entre les différentes catégories, questions et sous-questions, il suffit de sélectionner"
				+ "\nune catégorie dans la liste déroulante et vous pourrez voir toutes les questions qui appartiennent à cette catégorie, de même pour les questions."
				+ "\nEnsuite pour ajouter une catégorie, il suffit de cliquer sur le bouton en dessous de la liste des catégories, pour ajouter une question"
				+ "\nil faut d'abord sélectionner une catégorie et pour ajouter une sous-question, il faut sélectionner une question. Pour modifier et supprimer, "
				+ "\nil faut sélectionner un élément de cette même liste. Si le titre de la liste est rouge cela veut dire qu'il n'y a pas assez d'éléments dans cette liste,"
				+ "\nelle ne sera pas sélectionnée lors du jeu."
				+ "\n\n-Pour l'onglet option ; vous disposez de 4 boutons en haut de l'onglet le premier sert à \"installer\" la base de données, le 2ème supprime le jeu"
				+ "\nde votre base de données, le 3ème permet d'ajouter les jeux de question par défaut et le 4ème permet de régler le temps entre chaque question"
				+ "\ndu jeu entre 5 et 15 secondes. Après avoir utilisé l'un de ces 4 boutons, il faut utiliser le bouton rafraichir pour pouvoir réactualiser les"
				+ "\ndifférentes listes. Pour les listes en dessous des 4 boutons pour ajouter un élément, il suffit de cliquer sur le bouton ajouté"
				+ "\net pour modifier ou supprimer, il faut sélectionner un élément de cette même liste."
				+ "\n\n-Pour l'onglet rechercher ; il suffit de rentrer un texte dans le champ et vous trouverez des catégories, questions ou sous-questions "
				+ "\nqui contiennent ce champ. Le type (catégorie, question ou sous-question) de l'objet recherché est écrit après le résultat, "
				+ "\nils sont séparés par un tiret."
				+ "\n\n-Si vous obtenez ce message \"il y a un problème avec la base de données\", soit vous avez été deconnecté de la base de données soit il manque"
				+ "\ndes tables dans celle-ci");
		texte.setEditable(false);
		texte.setBackground(new Color(238,238,238));
		getPanel().add(texte);
	}

}
