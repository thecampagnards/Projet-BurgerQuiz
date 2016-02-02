package vues;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controles.GestionBDD;

/**
 * creation de l'onglet pour l'administration du jeu qui herite de la class
 * Onglet, affichage des 3 listes categorie,question et sous-question et elles
 * interagissent ensemble
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class OngletJeu extends Onglet implements ActionListener,
		WindowListener, ListSelectionListener {

	// 3 listes pour ctaegorie question etc
	private Liste categorie, question, sous_question;
	// panel contenant les 3 listes
	private JPanel pan_liste;
	private String str_un, str_autre, str_question, str_reponse;
	private final static int NB_CATEGORIE_MIN = 3;
	private final static int NB_QUESTION_MIN = 2;
	private final static int NB_SOUS_QUESTION_MIN = 3;
	private GestionBDD bdd;

	/**
	 * Constructeur de la class elle reçoit en parametre l'objet GestionBDD pour
	 * pouvoir effectuer des modification sur la bdd. Elle creer 3 objets Liste
	 * et les places dans la fenetre.
	 * 
	 * @param bdd
	 */
	public OngletJeu(GestionBDD bdd) {
		super("Configuration du Jeu");
		this.bdd = bdd;

		setIntro("Pour naviguer dans les questions veuillez d'abord choisir une catégorie et après une question pour voir les sous-questions.");

		pan_liste = new JPanel();
		// grille permettant d'aligner les 3 listes
		pan_liste.setLayout(new GridLayout(1, 3));

		categorie = new Liste("Liste des catégories");
		question = new Liste("Liste des questions");
		sous_question = new Liste("Liste des sous-questions avec réponse");

		try {
			if (bdd.listerCategories() < NB_CATEGORIE_MIN) {
				categorie.setTitreColor(255, 0, 0);
				setErreur("Il n'y a pas assez de catégorie.");
			}
		} catch (Exception e) {
			// affiche un message si l'utilisateur est deconnecte de la db
			setErreur("Il y a un problème avec la base de données.");
		}

		// on ajoute la liste des categories
		categorie.setList(bdd.getList());
		pan_liste.add(categorie.getPanel());
		categorie.getList().addListSelectionListener(this);

		// on ajoute la liste des questions et sous question vide
		pan_liste.add(question.getPanel());
		pan_liste.add(sous_question.getPanel());

		// ajout de tout les actionlisteners (pas reussi a grouper les
		// actionslisteners dans la
		// classe liste)
		sous_question.getBouton_ajouter().addActionListener(this);
		sous_question.getBouton_supprimer().addActionListener(this);
		sous_question.getBouton_modifier().addActionListener(this);
		categorie.getBouton_ajouter().addActionListener(this);
		categorie.getBouton_supprimer().addActionListener(this);
		categorie.getBouton_modifier().addActionListener(this);
		question.getBouton_ajouter().addActionListener(this);
		question.getBouton_supprimer().addActionListener(this);
		question.getBouton_modifier().addActionListener(this);

		getPanel().add(pan_liste);
	}

	/**
	 * Permet de bien recuperer chaque champ de la JList pour les questions
	 * 
	 * @param str
	 *            chaine de caractere de question (un ou autre ?)
	 */
	private void formatUnAutre(String str) {
		String[] temp = str.split(" ou ");
		temp[1] = temp[1].replace(" ?", "");
		str_un = temp[0];
		str_autre = temp[1];
	}

	/**
	 * Permet de bien recuperer chaque champ de la JList pour les sous-questions
	 * 
	 * @param str
	 *            chaine de caractere d'une sous-question (sous-question ?
	 *            reponse)
	 */
	private void formatQuestionReponse(String str) {
		String[] temp = str.split(" ? - ");
		temp[0] = temp[0].replace(" ?", "");
		str_question = temp[0];
		str_reponse = temp[1];
	}

	/**
	 * Méthode qui raffraichie la liste des categories se qui implique le
	 * raffraichissement des autres listes, elle efface du panel les objets
	 * Liste apres recupere un liste dans la bdd et passe cette liste a l'objet
	 * Liste par setList() et apres replace l'objet Liste dans le panel dans
	 * l'ordre
	 */
	private void refreshCategorie() {
		// on efface les jpanels des categorie questions et sous question
		pan_liste.remove(sous_question.getPanel());
		pan_liste.remove(question.getPanel());
		pan_liste.remove(categorie.getPanel());
		try {
			// on recupere la liste des categories
			if (bdd.listerCategories() < NB_CATEGORIE_MIN) {
				categorie.setTitreColor(255, 0, 0);
				setErreur("Il n'y a pas assez de catégorie.");
			} else {
				categorie.setTitreColor(51, 51, 51);
			}
			question.setTitreColor(51, 51, 51);
			sous_question.setTitreColor(51, 51, 51);
			// on cree une liste remplie
			categorie.setList(bdd.getList());
			// on cree des listes vides
			question.setList();
			sous_question.setList();
			// on les ajoute au panel
			pan_liste.add(categorie.getPanel(), 0);
			pan_liste.add(question.getPanel(), 1);
			pan_liste.add(sous_question.getPanel(), 2);
			// on ajoute le mouse listener
			categorie.getList().addListSelectionListener(this);
		} catch (Exception e) {
			setErreur("Il y a un problème avec la base de données.");
		}
		repaint();
	}

	/**
	 * raffraichissement de la liste des questions se qui implique le
	 * raffraichissement de la liste des sous questions
	 */
	private void refreshQuestion() {
		// on efface le panel des questions et des sous questions
		pan_liste.remove(sous_question.getPanel());
		pan_liste.remove(question.getPanel());
		try {
			// on recupere la liste des question grace a la valeur selectionnée
			// dans la liste des categories
			if (bdd.listerQuestions(categorie.getList().getSelectedValue()) < NB_QUESTION_MIN) {
				question.setTitreColor(255, 0, 0);
				setErreur("Il n'y a pas assez de question dans cette catégorie.");
			} else {
				question.setTitreColor(51, 51, 51);
			}
			sous_question.setTitreColor(51, 51, 51);
			// on ajoute une liste remplie de question
			question.setList(bdd.getList());
			// on ajoute une liste vide de sous question
			sous_question.setList();
			// on les rajoute au jpanel
			pan_liste.add(question.getPanel(), 1);
			pan_liste.add(sous_question.getPanel(), 2);
			// on ajoute le listener
			question.getList().addListSelectionListener(this);

		} catch (Exception e) {
			setErreur("Il y a un problème avec la base de données.");
		}
		repaint();
	}

	/**
	 * raffraichissement de la liste des sous questions
	 */
	private void refreshSousQuestion() {
		// on supprime la liste du panel
		pan_liste.remove(sous_question.getPanel());
		try {
			// on format la question recupere en un et autre
			formatUnAutre(question.getList().getSelectedValue());
			if (bdd.listerSousQuestions(str_un, str_autre) < NB_SOUS_QUESTION_MIN) {
				sous_question.setTitreColor(255, 0, 0);
				setErreur("Il n'y a pas assez de sous-question dans cette question.");
			} else {
				sous_question.setTitreColor(51, 51, 51);
			}
			// on creer la liste
			sous_question.setList(bdd.getList());
			// on l'ajoute au panel
			pan_liste.add(sous_question.getPanel(), 2);
			sous_question.getList().addListSelectionListener(this);

		} catch (Exception e) {
			setErreur("Il y a un problème avec la base de données.");
		}
		repaint();
	}

	// creer un popup a chaque fois qu'on clique sur un popup ajouter supprimer
	// modifier
	// j'aurais pu griser les boutons mais j'ai eu l'idée trop tard
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// on efface les erreurs
		setErreur("");

		closePopup();

		/********* CATEGORIE **********/
		// pour le bouton ajouter de categorie
		if (arg0.getSource() == categorie.getBouton_ajouter()) {
			popup = new Popup(bdd, null, 1);// parametres : objet bdd,la
											// categorie selectionné,l'action
											// (1) ajouter (2) modifier (3)
											// supprimer
			// ajout du windows listener pour refresh et ajouter le message
			popup.getFrame().addWindowListener(this);
		}
		if (categorie.getList().getSelectedValue() != null) {
			// pour le bouton modifier de categorie
			if (arg0.getSource() == categorie.getBouton_modifier()) {
				popup = new Popup(bdd, categorie.getList().getSelectedValue(),
						2);
				popup.getFrame().addWindowListener(this);

			}
			// pour le bouton modifier de categorie
			if (arg0.getSource() == categorie.getBouton_supprimer()) {
				popup = new Popup(bdd, categorie.getList().getSelectedValue(),
						3);
				popup.getFrame().addWindowListener(this);

			}
			// pour le bouton ajouter de question
			if (arg0.getSource() == question.getBouton_ajouter()) {
				popup = new Popup(bdd, categorie.getList().getSelectedValue(),
						null, null, 1);// parametres : objet bdd,la categorie,
										// un , autre ,l'action
										// (1) ajouter (2) modifier (3)
										// supprimer
				popup.getFrame().addWindowListener(this);
			}
			/********** QUESTION **********/
			if (question.getList().getSelectedValue() != null) {
				// pour bouton modifier une question
				if (arg0.getSource() == question.getBouton_modifier()) {
					formatUnAutre(question.getList().getSelectedValue());
					popup = new Popup(bdd, categorie.getList()
							.getSelectedValue(), str_un, str_autre, 2);
					popup.getFrame().addWindowListener(this);

				}
				// pour bouton supprimer
				if (arg0.getSource() == question.getBouton_supprimer()) {
					formatUnAutre(question.getList().getSelectedValue());
					popup = new Popup(bdd, categorie.getList()
							.getSelectedValue(), str_un, str_autre, 3);
					popup.getFrame().addWindowListener(this);
				}
				/********** SOUS QUESTION ************/
				if (question.getList().getSelectedValue() != null) {
					// pour bouton ajouter une sous question
					if (arg0.getSource() == sous_question.getBouton_ajouter()) {
						formatUnAutre(question.getList().getSelectedValue());
						popup = new Popup(bdd, str_un, str_autre, null, null, 1);
						popup.getFrame().addWindowListener(this);

					}
					if (sous_question.getList().getSelectedValue() != null) {
						// pour bouton modifier des sous questions
						if (arg0.getSource() == sous_question
								.getBouton_modifier()) {
							formatUnAutre(question.getList().getSelectedValue());
							formatQuestionReponse(sous_question.getList()
									.getSelectedValue());
							popup = new Popup(bdd, str_un, str_autre,
									str_question, str_reponse, 2);
							popup.getFrame().addWindowListener(this);

						}
						// pour bouton supprimer des sous questions
						if (arg0.getSource() == sous_question
								.getBouton_supprimer()) {
							formatUnAutre(question.getList().getSelectedValue());
							formatQuestionReponse(sous_question.getList()
									.getSelectedValue());
							popup = new Popup(bdd, str_un, str_autre,
									str_question, str_reponse, 3);
							popup.getFrame().addWindowListener(this);

						}
					} else {
						setErreur("Veuillez d'abord sélectionner une sous-question.");
					}
				}

			} else {
				setErreur("Veuillez d'abord sélectionner une question.");
			}
		} else {
			setErreur("Veuillez d'abord sélectionner une catégorie.");
		}
	}

	@Override
	public void windowActivated(WindowEvent arg5) {
		// Do nothing
	}

	// permet lorsque le popup de mod suppression ou modification se ferme
	// de raffraichir les listes ainsi qu'afficher un message de l'action
	// effectuée
	// 1 => ajouter 2=>modifier 3=>supprimer
	@Override
	public void windowClosed(WindowEvent arg0) {

		// si le popup est une categorie (1)
		if (popup != null && arg0.getSource() == popup.getFrame()
				&& popup.getTyp() == 1) {
			// on raffraichi les listes
			refreshCategorie();
			// on affiche l'action
			if (popup.getAction() == 1) {
				setValide("La catégorie a été ajoutée.");
			}
			if (popup.getAction() == 2) {
				setValide("La catégorie a été modifiée.");
			}
			if (popup.getAction() == 3) {
				setValide("La catégorie a été supprimée.");
			}

		}
		// si le popup est pour une question(2)
		if (popup != null && arg0.getSource() == popup.getFrame()
				&& popup.getTyp() == 2) {
			// refresh des question
			refreshQuestion();
			// affichage de la modification
			if (popup.getAction() == 1) {
				setValide("La question a été ajoutée.");
			}
			if (popup.getAction() == 2) {
				setValide("La question a été modifiée.");
			}
			if (popup.getAction() == 3) {
				setValide("La question a été supprimée.");
			}
		}
		// si le popup est pour une sous question (3)
		if (popup != null && arg0.getSource() == popup.getFrame()
				&& popup.getTyp() == 3) {
			// raffraichissement des sous questions
			refreshSousQuestion();
			// et affichage de l'action faite
			if (popup.getAction() == 1) {
				setValide("La sous-question a été ajoutée.");
			}
			if (popup.getAction() == 2) {
				setValide("La sous-question a été modifiée.");
			}
			if (popup.getAction() == 3) {
				setValide("La sous-question a été supprimée.");
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent arg4) {
		// Do nothing
	}

	@Override
	public void windowDeactivated(WindowEvent arg1) {
		// Do nothing
	}

	@Override
	public void windowDeiconified(WindowEvent arg2) {
		// Do nothing
	}

	@Override
	public void windowIconified(WindowEvent arg3) {
		// Do nothing
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		if (arg0.getSource() == popup.getFrame()) {
			setErreur("");
		}
	}

	// permet d'afficher la liste suivante (categorie->question->sous_question)
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		setErreur("");
		if (arg0.getSource() == categorie.getList()) {
			// raffraichissement des questions
			refreshQuestion();
		}
		if (arg0.getSource() == question.getList()) {
			// raffraichissement des sous questions
			refreshSousQuestion();
		}

	}

}
