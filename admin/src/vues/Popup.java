package vues;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import controles.GestionBDD;

/**
 * Classe popup qui permet de creer un popup pour modifier un element dans la
 * bdd le popup est à chaque fois differents selon les parametres envoyés
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class Popup extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GestionBDD bdd;
	private JFrame frame;
	private JLabel erreur;
	private JPanel pan_text;
	private JButton categorie_ajouter, categorie_modifier, categorie_supprimer,
			question_ajouter, question_modifier, question_supprimer,
			sous_question_ajouter, sous_question_modifier,
			sous_question_supprimer, palmares_ajouter, palmares_modifier,
			palmares_supprimer, utilisateur_ajouter, utilisateur_supprimer,
			temps_modifier;
	private JTextField champ, champ2;
	private JComboBox<String> combobox;
	private int type, action;
	private String element1, element2, element3, element4;
	private JSlider jslide;
	private final static int max_temps = 15, min_temps = 5;

	/**
	 * popup pour modifier le temps
	 * 
	 * @param bdd
	 */
	public Popup(GestionBDD bdd) {
		creationPopup();
		this.bdd = bdd;
		type = 6;
		temps_modifier = new JButton("Modifier le temps");
		temps_modifier.setBackground(new Color(41, 128, 185));
		temps_modifier.setForeground(Color.WHITE);
		temps_modifier.addActionListener(this);
		JLabel titre = new JLabel("Changer le temps : ");
		try {
			jslide = new JSlider(JSlider.HORIZONTAL, min_temps, max_temps,
					bdd.getTemps());
		} catch (Exception e) {
			e.printStackTrace();
		}
		jslide.setMajorTickSpacing(2);
		jslide.setMinorTickSpacing(1);
		jslide.setPaintTicks(true);
		jslide.setPaintLabels(true);
		pan_text.add(titre);
		pan_text.add(jslide);
		pan_text.add(temps_modifier);
		this.pack();
	}

	/**
	 * popup pour utilisateur creation de la fenetre
	 * 
	 * @param pseudo
	 * @param bdd
	 * @param action
	 *            ajouter(action=1) modifier(action=2) ou supprimer(action=3)
	 */
	public Popup(String pseudo, GestionBDD bdd, int action) {

		creationPopup();
		this.bdd = bdd;
		this.action = action;

		// type = 5 => c'est un popup pour utilisateur
		type = 5;
		element1 = pseudo;
		JLabel titre = new JLabel();
		if (action == 1) {
			titre.setText("Ajouter un utilisateur : ");
			champ = new JTextField(10);
			JLabel mdp = new JLabel(" mot de passe : ");
			champ2 = new JTextField(10);
			utilisateur_ajouter = new JButton("Ajouter");
			utilisateur_ajouter.setForeground(Color.WHITE);
			utilisateur_ajouter.setBackground(new Color(39, 174, 96));
			utilisateur_ajouter.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(champ);
			pan_text.add(mdp);
			pan_text.add(champ2);
			// ajout du bouton specifique pour chaque type et action
			pan_text.add(utilisateur_ajouter);
		} else if (action == 3) {
			titre.setText("Supprimer l'utilisateur " + pseudo + " ? ");
			utilisateur_supprimer = new JButton("Supprimer");
			utilisateur_supprimer.setBackground(new Color(192, 57, 43));
			utilisateur_supprimer.setForeground(Color.WHITE);
			utilisateur_supprimer.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(utilisateur_supprimer);
		}
		this.pack();
	}

	/**
	 * popup pour catégorie creation de la fenetre
	 * 
	 * @param bdd
	 * @param categorie
	 * @param action
	 *            ajouter(action=1) modifier(action=2) ou supprimer(action=3)
	 */
	public Popup(GestionBDD bdd, String categorie, int action) {

		creationPopup();
		this.bdd = bdd;
		this.action = action;

		// type = 1 => c'est un popup categorie
		type = 1;
		element1 = categorie;
		JLabel titre = new JLabel();
		if (action == 1 || action == 2) {
			titre.setText("Ajouter une catégorie : ");
			champ = new JTextField(15);
			categorie_ajouter = new JButton("Ajouter");
			categorie_ajouter.setForeground(Color.WHITE);
			categorie_ajouter.setBackground(new Color(39, 174, 96));
			categorie_ajouter.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(champ);
			// ajout du bouton specifique pour chaque type et action
			pan_text.add(categorie_ajouter);
		}
		if (action == 2) {
			// pour la modification on prerempli les champs
			titre.setText("Modifier la catégorie : ");
			champ.setText(categorie);
			categorie_modifier = new JButton("Modifier");
			categorie_modifier.setBackground(new Color(41, 128, 185));
			categorie_modifier.setForeground(Color.WHITE);
			categorie_modifier.addActionListener(this);
			// suppression du bouton ajouter et on rajoute le bouton modifier
			pan_text.remove(categorie_ajouter);
			pan_text.add(categorie_modifier);
		} else if (action == 3) {
			titre.setText("Supprimer la catégorie " + categorie + " ? ");
			categorie_supprimer = new JButton("Supprimer");
			categorie_supprimer.setBackground(new Color(192, 57, 43));
			categorie_supprimer.setForeground(Color.WHITE);
			categorie_supprimer.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(categorie_supprimer);
		}
		this.pack();
	}

	/**
	 * popup pour ajouter modifier ou supprimer une question avec creation de la
	 * fenetre
	 * 
	 * @param bdd
	 * @param categorie
	 * @param un
	 * @param autre
	 * @param action
	 *            ajouter(action=1) modifier(action=2) ou supprimer(action=3)
	 */
	public Popup(GestionBDD bdd, String categorie, String un, String autre,
			int action) {
		creationPopup();
		this.bdd = bdd;
		this.action = action;

		// type = 2 => c'est un popup question
		type = 2;
		element1 = categorie;
		element2 = un;
		element3 = autre;
		JLabel titre = new JLabel();

		if (action == 1 || action == 2) {
			titre.setText("Ajouter une question : ");
			champ = new JTextField(10);
			JLabel ou = new JLabel(" ou ");
			JLabel jlab_categorie = new JLabel(" ? catégorie : ");
			champ2 = new JTextField(10);
			question_ajouter = new JButton("Ajouter");
			question_ajouter.setBackground(new Color(39, 174, 96));
			question_ajouter.setForeground(Color.WHITE);
			question_ajouter.addActionListener(this);
			// recuperation de la liste des categorie pour le menu deroulant
			try {
				bdd.listerCategories();
			} catch (Exception e) {
				e.printStackTrace();
			}
			setDeroulant(bdd.getList());
			combobox.setSelectedItem(categorie);
			pan_text.add(titre);
			pan_text.add(champ);
			pan_text.add(ou);
			pan_text.add(champ2);
			pan_text.add(jlab_categorie);
			pan_text.add(combobox);
			pan_text.add(question_ajouter);

		}
		// on prerempli les champs pour modifier
		if (action == 2) {
			titre.setText("Modifier une question : ");
			champ.setText(un);
			champ2.setText(autre);
			combobox.setSelectedItem(categorie);
			question_modifier = new JButton("Modifier");
			question_modifier.setBackground(new Color(41, 128, 185));
			question_modifier.setForeground(Color.WHITE);
			question_modifier.addActionListener(this);
			// suppression du bouton ajouter et on rajoute le bouton modifier
			pan_text.remove(question_ajouter);
			pan_text.add(question_modifier);
		} else if (action == 3) {
			titre.setText("Supprimer la question " + un + " ou " + autre + " ?");
			question_supprimer = new JButton("Supprimer");
			question_supprimer.setBackground(new Color(192, 57, 43));
			question_supprimer.setForeground(Color.WHITE);
			question_supprimer.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(question_supprimer);
		}
		this.pack();
	}

	/**
	 * popup pour ajouter modifier ou supprimer une question
	 * 
	 * @param bdd
	 * @param un
	 * @param autre
	 * @param question
	 * @param reponse
	 * @param action
	 *            ajouter(action=1) modifier(action=2) ou supprimer(action=3)
	 */
	public Popup(GestionBDD bdd, String un, String autre, String question,
			String reponse, int action) {
		creationPopup();
		this.bdd = bdd;
		this.action = action;

		// type = 3 => c'est un popup sous question
		type = 3;
		element1 = un;
		element2 = autre;
		element3 = question;
		element4 = reponse;

		JLabel titre = new JLabel();

		if (action == 1 || action == 2) {
			titre.setText("Ajouter une sous-question : ");
			champ = new JTextField(20);
			JLabel jlab_reponse = new JLabel(" ? réponse : ");
			sous_question_ajouter = new JButton("Ajouter");
			sous_question_ajouter.setBackground(new Color(39, 174, 96));
			sous_question_ajouter.setForeground(Color.WHITE);
			sous_question_ajouter.addActionListener(this);

			try {
				bdd.listerReponses();
			} catch (Exception e) {
				e.printStackTrace();
			}

			pan_text.add(titre);
			pan_text.add(champ);
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(element1);
			tmp.add(element2);
			tmp.add("Les deux");
			setDeroulant(tmp);
			pan_text.add(jlab_reponse);
			pan_text.add(combobox);
			pan_text.add(sous_question_ajouter);
		}
		if (action == 2) {
			titre.setText("Modifier une sous-question : ");
			champ.setText(question);
			combobox.setSelectedItem(element4);
			sous_question_modifier = new JButton("Modifier");
			sous_question_modifier.setBackground(new Color(41, 128, 185));
			sous_question_modifier.setForeground(Color.WHITE);
			sous_question_modifier.addActionListener(this);
			// suppression du bouton ajouter et on rajoute le bouton modifier
			pan_text.remove(sous_question_ajouter);
			pan_text.add(sous_question_modifier);
		} else if (action == 3) {
			titre.setText("Supprimer la sous-question : " + question
					+ " ? réponse : " + reponse + " ?");
			sous_question_supprimer = new JButton("Supprimer");
			sous_question_supprimer.setBackground(new Color(192, 57, 43));
			sous_question_supprimer.setForeground(Color.WHITE);
			sous_question_supprimer.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(sous_question_supprimer);
		}
		this.pack();
	}

	/**
	 * popup pour palmares avec creation de l'interface
	 * 
	 * @param bdd
	 * @param pseudo
	 * @param score
	 * @param action
	 *            ajouter(action=1) modifier(action=2) ou supprimer(action=3)
	 */
	public Popup(GestionBDD bdd, String pseudo, int score, int action) {

		creationPopup();
		this.bdd = bdd;
		this.action = action;

		// type = 1 => c'est un popup categorie
		type = 4;
		element1 = pseudo;
		element2 = Integer.toString(score);
		JLabel titre = new JLabel();
		if (action == 1 || action == 2) {
			titre.setText("Ajouter un score au plamares, pseudo : ");
			JLabel jlabscore = new JLabel(" score :");
			try {
				bdd.listerUtilisateurs();
			} catch (Exception e) {
				e.printStackTrace();
			}
			setDeroulant(bdd.getList());
			champ = new JTextField(15);
			palmares_ajouter = new JButton("Ajouter");
			palmares_ajouter.setBackground(new Color(39, 174, 96));
			palmares_ajouter.setForeground(Color.WHITE);
			palmares_ajouter.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(combobox);
			pan_text.add(jlabscore);
			pan_text.add(champ);
			// ajout du bouton specifique pour chaque type et action
			pan_text.add(palmares_ajouter);
		}
		if (action == 2) {
			// pour la modification on prerempli les champs
			titre.setText("Modifier un score du palmares : ");
			combobox.setSelectedItem(element1);
			champ.setText(element2);
			palmares_modifier = new JButton("Modifier");
			palmares_modifier.setBackground(new Color(41, 128, 185));
			palmares_modifier.setForeground(Color.WHITE);
			palmares_modifier.addActionListener(this);
			// suppression du bouton ajouter et on rajoute le bouton modifier
			pan_text.remove(palmares_ajouter);
			pan_text.add(palmares_modifier);
		} else if (action == 3) {
			titre.setText("Supprimer le score : " + element2 + " de "
					+ element1 + " ? ");
			palmares_supprimer = new JButton("Supprimer");
			palmares_supprimer.setBackground(new Color(192, 57, 43));
			palmares_supprimer.setForeground(Color.WHITE);
			palmares_supprimer.addActionListener(this);
			pan_text.add(titre);
			pan_text.add(palmares_supprimer);
		}
		this.pack();
	}

	/**
	 * creation d'un popup par defaut
	 */
	private void creationPopup() {

		// creation de la fenetre de base
		// this.frame me permet de faire un windowslistener qui va me permettre
		// apres de rafraichir mes listes
		this.frame = this;
		this.setLocation(500, 350);
		this.setVisible(true);
		this.setSize(400, 100);
		this.setAlwaysOnTop(true);

		// ajout d'un icone pour la fenetre
		this.setIconImage(new Icone("/img/icon.png").getImage());

		JPanel pan = new JPanel();
		pan_text = new JPanel();
		erreur = new JLabel();
		erreur.setForeground(new Color(255, 0, 0));

		// placement des elements
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		pan_text.setLayout(new BoxLayout(pan_text, BoxLayout.LINE_AXIS));

		pan.add(pan_text);
		pan.add(erreur);

		this.add(pan);
	}

	/**
	 * ajout d'une combobox appartir d'un tableau de string envoyé en parametre
	 * 
	 * @param tmp
	 */
	private void setDeroulant(ArrayList<String> tmp) {
		combobox = new JComboBox<String>();
		for (String s : tmp) {
			combobox.addItem(s);
		}
		combobox.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// si la requete c'est bien effectuée alors la fenetre se ferme sinon un
		// message d'erreur est affiché

		// bouton pour les categories
		if (arg0.getSource() == categorie_ajouter
				&& !"".equals(champ.getText())) {
			try {
				bdd.insertCategorie(champ.getText());
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("La catégorie existe déjà.");
			}
		}
		if (arg0.getSource() == categorie_modifier
				&& !"".equals(champ.getText())) {
			try {
				bdd.updateCategorie(element1, champ.getText());
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("La catégorie existe déjà.");
			}
		}
		if (arg0.getSource() == categorie_supprimer) {
			try {
				bdd.deleteCategorie(element1);
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Il y a eu un problème lors de la suppression de la catégorie.");
			}
		}
		// bouton pour les questions
		if (arg0.getSource() == question_ajouter && !"".equals(champ.getText())
				&& !"".equals(champ2.getText())
				&& !champ.getText().equals(champ2.getText())) {
			try {
				if (champ.getText().indexOf(" ou ") == -1
						&& champ2.getText().indexOf(" ou ") == -1) {
					bdd.insertQuestion(
							String.valueOf(combobox.getSelectedItem()),
							champ.getText(), champ2.getText());
					this.dispose();
				} else {
					erreur.setText("Veuillez ne pas entrer de \" ou \" dans votre question.");
				}

			} catch (Exception e1) {
				erreur.setText("La question existe déjà.");
			}
		}
		if (arg0.getSource() == question_modifier
				&& !"".equals(champ.getText()) && !"".equals(champ2.getText())
				&& !champ.getText().equals(champ2.getText())) {
			try {
				if (champ.getText().indexOf(" ou") == -1
						&& champ2.getText().indexOf(" ou ") == -1) {
					bdd.updateQuestion(
							String.valueOf(combobox.getSelectedItem()),
							champ.getText(), champ2.getText(), element1,
							element2, element3);
					this.dispose();
				} else {
					erreur.setText("Veuillez ne pas entrer de \" ou \" dans votre question.");
				}
			} catch (Exception e1) {
				erreur.setText("La question existe déjà.");
			}
		}
		if (arg0.getSource() == question_supprimer) {
			try {
				bdd.deleteQuestion(element2, element3);
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Il y a eu un problème lors de la suppression de la catégorie.");
			}
		}
		// boutons pour les sous questions
		if (arg0.getSource() == sous_question_ajouter
				&& !"".equals(champ.getText())) {
			try {
				if (champ.getText().indexOf(" ? -") == -1) {
					bdd.insertSousQuestion(element1, element2, champ.getText(),
							combobox.getSelectedIndex());
					this.dispose();
				} else {
					erreur.setText("Veuillez ne pas entrer de \" ? -\" dans votre sous-question.");
				}
			} catch (Exception e1) {
				erreur.setText("La sous-question existe déjà.");
			}
		}
		if (arg0.getSource() == sous_question_modifier
				&& !"".equals(champ.getText())) {
			try {
				if (champ.getText().indexOf(" ? -") == -1) {
					bdd.updateSousQuestion(element1, element2, champ.getText(),
							combobox.getSelectedIndex(), element3);
					this.dispose();
				} else {
					erreur.setText("Veuillez ne pas entrer de \" ? -\" dans votre sous-question.");
				}
			} catch (Exception e1) {
				erreur.setText("La sous-question existe déjà.");
			}
		}
		if (arg0.getSource() == sous_question_supprimer) {
			try {
				bdd.deleteSousQuestion(element1, element2, element3);
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Il y a eu un problème lors de la suppression de la sous-question.");
			}
		}
		// boutons pour le palmares
		if (arg0.getSource() == palmares_ajouter) {
			try {
				if (!"".equals(champ.getText())
						&& Integer.parseInt(champ.getText()) <= 100) {
					try {
						bdd.insertPalmares(
								String.valueOf(combobox.getSelectedItem()),
								champ.getText());
						this.dispose();
					} catch (Exception e1) {
						erreur.setText("le score doit etre positif et de 100 au maximum.");
					}
				} else {
					erreur.setText("le score doit etre positif et de 100 au maximum.");
				}
			} catch (Exception e) {
				erreur.setText("le score doit etre un nombre.");
			}
		}
		if (arg0.getSource() == palmares_modifier) {
			try {
				if (!"".equals(champ.getText())
						&& Integer.parseInt(champ.getText()) <= 100) {
					try {
						bdd.updatePalmares(element1, element2,
								String.valueOf(combobox.getSelectedItem()),
								champ.getText());
						this.dispose();
					} catch (Exception e1) {
						erreur.setText("le score doit etre positif et de 100 au maximum.");
					}
				} else {
					erreur.setText("le score doit etre positif et de 100 au maximum.");
				}
			} catch (Exception e) {
				erreur.setText("le score doit etre un nombre.");
			}
		}
		if (arg0.getSource() == palmares_supprimer) {
			try {
				bdd.deletePalmares(element1, element2);
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Il y a eu un problème lors de la suppression du score.");
			}
		}
		if (arg0.getSource() == utilisateur_ajouter
				&& !"".equals(champ2.getText()) && !"".equals(champ.getText())) {
			try {
				bdd.insertUtilisateurs(champ.getText(), champ2.getText());
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Veuillez entrer un utilisateur inexistant.");
			}
		}
		if (arg0.getSource() == utilisateur_supprimer) {
			try {
				bdd.deleteUtilisateurs(element1);
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Il y a eu un problème lors de la suppression de l'utilisateur.");
			}
		}
		// temps
		if (arg0.getSource() == temps_modifier) {
			try {
				bdd.setTemps(jslide.getValue());
				this.dispose();
			} catch (Exception e1) {
				erreur.setText("Il y a eu un problème lors de la modification du temps.");
			}
		}
		this.pack();
	}

	/**
	 * getter pour recuperer la fenetre qui va permettre apres d'actualiser les
	 * listes dans la fenetre du popup se fermera
	 * 
	 * @return de la JFrame du popup
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * getter pour recuperer l'action de la fenetre suppression modif qui va
	 * permet d'afficher un message de l'action effectué
	 * 
	 * @return action ajouter(action=1) modifier(action=2) ou
	 *         supprimer(action=3)
	 */
	public int getAction() {
		return action;
	}

	/**
	 * getter pour recuperer le type de la fenetre categorie , question sous
	 * question, palmares ou utilisateur qui va permet d'afficher un message de
	 * l'action effectué pour tel type
	 * 
	 * @return type categorie(type=1) question(type=2) sous-question(type=3)
	 *         palmares(type=4) utilisateur(type=5)
	 */
	public int getTyp() {
		return type;
	}
}