	package vues;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controles.GestionBDD;

/**
 * creation de l'onglet pour les options qui herite de la class Onglet,
 * affichage de 2 listes utilisateurs et palmares et 3 boutons de modification
 * de la bdd, ajouter les tables, les supprimer et ajouter le jeu de question
 * par défaut
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class OngletOption extends Onglet implements ActionListener,
		WindowListener {

	private JButton bouton_creation_base, bouton_supprimer_base,
			bouton_default_base, bouton_temps;
	private Liste palmares, utilisateurs;
	private int score;
	private String pseudo;
	private GestionBDD bdd;

	/**
	 * Constructeur de la classe onglet option, ajout des 3 objets Liste et des
	 * 3 boutons de modification de la bdd
	 * 
	 * @param bdd
	 */
	public OngletOption(GestionBDD bdd) {
		super("Options");
		this.bdd = bdd;
		setIntro("Vous disposez de plusieurs options comme modifier la base de données des questions ou de modifier le palmarès.");

		JPanel pan_bouton = new JPanel();
		palmares = new Liste("Liste du palmarès");
		utilisateurs = new Liste("Liste des utilisateurs");

		bouton_creation_base = new JButton("Créer les tables");
		bouton_supprimer_base = new JButton("Supprimer les tables");
		bouton_default_base = new JButton("Ajouter les questions par défaut");
		bouton_temps = new JButton("Changer le temps des questions");
		bouton_creation_base.addActionListener(this);
		bouton_default_base.addActionListener(this);
		bouton_supprimer_base.addActionListener(this);
		bouton_temps.addActionListener(this);

		bouton_supprimer_base.setBackground(new Color(192, 57, 43));
		bouton_supprimer_base.setForeground(Color.WHITE);
		bouton_default_base.setBackground(new Color(39, 174, 96));
		bouton_default_base.setForeground(Color.WHITE);
		bouton_creation_base.setBackground(new Color(41, 128, 185));
		bouton_creation_base.setForeground(Color.WHITE);
		bouton_temps.setBackground(new Color(41, 128, 185));
		bouton_temps.setForeground(Color.WHITE);

		pan_bouton.add(bouton_creation_base);
		pan_bouton.add(bouton_supprimer_base);
		pan_bouton.add(bouton_default_base);
		pan_bouton.add(bouton_temps);

		try {
			bdd.listerPalmares();
		} catch (Exception e) {
			// affiche un message si l'utilisateur est deconnecte de la db ou
			// qu'il manque des tables
			// dans la bdd
			setErreur("Il y a un problème avec la base de données.");
		}

		// on ajoute la liste des categories
		palmares.setList(bdd.getList());
		palmares.getBouton_ajouter().addActionListener(this);
		palmares.getBouton_supprimer().addActionListener(this);
		palmares.getBouton_modifier().addActionListener(this);

		try {
			bdd.listerUtilisateurs();
		} catch (Exception e) {
			// affiche un message si l'utilisateur est deconnecte de la db ou
			// qu'il manque des tables
			// dans la bdd
			setErreur("Il y a un problème avec la base de données.");
		}

		utilisateurs.setList(bdd.getList());
		utilisateurs.removeButton(utilisateurs.getBouton_modifier());
		utilisateurs.getBouton_ajouter().addActionListener(this);
		utilisateurs.getBouton_supprimer().addActionListener(this);

		getPanel().add(pan_bouton);
		getPanel().add(palmares.getPanel());
		getPanel().add(utilisateurs.getPanel());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		setErreur("");
		closePopup();
		// boutons pour gerer la bdd
		if (arg0.getSource() == bouton_creation_base) {
			try {
				final JFrame parent = new JFrame();
				int retour = JOptionPane.showConfirmDialog(parent,
						"Voulez vous créer les tables ?", "Créer",
						JOptionPane.YES_NO_OPTION);

				if (retour == 0) {
					bdd.scriptBase("/doc/db.sql");
					setValide("Les tables ont bien été crées.");
					setErreur("");
					refreshPalmares();
					refreshUtilisateurs();
				}
			} catch (Exception e) {
				setValide("");
				setErreur("Il y a eu un problème lors de la création des tables.");
			}
		}
		if (arg0.getSource() == bouton_supprimer_base) {
			try {
				final JFrame parent = new JFrame();
				int supprimer = JOptionPane.showConfirmDialog(parent,
						"Voulez vous supprimer les tables ?", "Supprimer les tables",
						JOptionPane.YES_NO_OPTION);

				if (supprimer == 0) {
					bdd.suppressionBase();
					setValide("Les tables ont bien été supprimées.");
					setErreur("");
					refreshPalmares();
					refreshUtilisateurs();
				}
			} catch (Exception e) {
				setErreur("Il y a eu un problème lors de la suppression des tables.");
				setValide("");
			}
		}
		if (arg0.getSource() == bouton_default_base) {
			try {

				final JFrame parent = new JFrame();
				int ajouter = JOptionPane.showConfirmDialog(parent,
						"Voulez vous ajouter les questions par défaut ?",
						"Ajouter les questions par défaut", JOptionPane.YES_NO_OPTION);

				if (ajouter == 0) {
					bdd.scriptBase("/doc/default.sql");
					setValide("Les questions par défaut ont bien été ajoutées.");
					setErreur("");
					refreshPalmares();
					refreshUtilisateurs();
				}
			} catch (Exception e) {
				setValide("");
				setErreur("Il y a eu un problème lors de l'ajout par défaut des questions.");
			}
		}
		// boutons pour le palmares
		if (arg0.getSource() == palmares.getBouton_ajouter()) {
			popup = new Popup(bdd, null, 0, 1);
			popup.getFrame().addWindowListener(this);
		}
		if (palmares.getList().getSelectedValue() != null) {
			if (arg0.getSource() == palmares.getBouton_modifier()) {
				formatPalmares(palmares.getList().getSelectedValue());
				popup = new Popup(bdd, pseudo, score, 2);
				popup.getFrame().addWindowListener(this);
			}
			if (arg0.getSource() == palmares.getBouton_supprimer()) {
				formatPalmares(palmares.getList().getSelectedValue());
				popup = new Popup(bdd, pseudo, score, 3);
				popup.getFrame().addWindowListener(this);
			}
		}
		// boutons pour les utilisateurs
		if (arg0.getSource() == utilisateurs.getBouton_ajouter()) {
			popup = new Popup(null, bdd, 1);
			popup.getFrame().addWindowListener(this);
		}
		if (utilisateurs.getList().getSelectedValue() != null
				&& arg0.getSource() == utilisateurs.getBouton_supprimer()) {
			popup = new Popup(utilisateurs.getList().getSelectedValue(), bdd, 3);
			popup.getFrame().addWindowListener(this);
		}
		// bouton temps
		if (arg0.getSource() == bouton_temps) {
			popup = new Popup(bdd);
			popup.getFrame().addWindowListener(this);
		}
	}

	/**
	 * recupere le score et le pseudo d'un getSelectecValue() de la JList
	 * palmares
	 * 
	 * @param str
	 *            chaine de caractere sous forme (place - pseudo - score)
	 */
	private void formatPalmares(String str) {
		String[] temp = str.split(" - ");
		pseudo = temp[1];
		score = Integer.parseInt(temp[2]);
	}

	private void refreshPalmares() {
		// on efface le palmares du jpanel
		getPanel().remove(palmares.getPanel());
		try {
			// on recupere la liste u palmares
			bdd.listerPalmares();
			// on cree une liste du palmares
			palmares.setList(bdd.getList());
			// on l'ajoute au panel
			getPanel().add(palmares.getPanel());
		} catch (Exception e) {
			setErreur("Il y a un problème avec la base de données.");
			palmares.setList();
			getPanel().add(palmares.getPanel());
		}
		refreshUtilisateurs();
		repaint();
	}

	/**
	 * Méthode qui raffraichie la liste des utlisateurs, elle efface du panel
	 * les objets Liste utilisateur apres recupere un liste dans la bdd et passe
	 * cette liste a l'objet Liste par setList() et apres replace l'objet Liste
	 * dans le panel dans l'ordre
	 */
	private void refreshUtilisateurs() {
		// on efface le palmares du jpanel
		getPanel().remove(utilisateurs.getPanel());
		try {
			// on recupere la liste u palmares
			bdd.listerUtilisateurs();
			// on cree une liste du palmares
			utilisateurs.setList(bdd.getList());
			// on l'ajoute au panel
			getPanel().add(utilisateurs.getPanel());
		} catch (Exception e) {
			setErreur("Il y a un problème avec la base de données.");
			utilisateurs.setList();
			getPanel().add(utilisateurs.getPanel());
		}
		repaint();
	}

	// permet lorsque le popup de mod suppression ou modification se ferme
	// de raffraichir la liste du palmares ainsi qu'afficher un message de
	// l'action
	// effectuée
	// 1 => ajouter 2=>modifier 3=>supprimer
	@Override
	public void windowClosed(WindowEvent arg0) {

		// si le popup est une categorie (1)
		if (popup!= null && arg0.getSource() == popup.getFrame() && popup.getTyp() == 4) {
			// on raffraichi les listes
			refreshPalmares();
			// on affiche l'action
			if (popup.getAction() == 1) {
				setValide("Le score a été ajouté.");
			}
			if (popup.getAction() == 2) {
				setValide("Le score a été modifié.");
			}
			if (popup.getAction() == 3) {
				setValide("Le score a été supprimé.");
			}
		}
		if (popup!= null && arg0.getSource() == popup.getFrame() && popup.getTyp() == 5) {
			// on raffraichi les listes
			refreshPalmares();
			refreshUtilisateurs();
			// on affiche l'action
			if (popup.getAction() == 1) {
				setValide("L'utilisateur a été ajouté.");
			}
			if (popup.getAction() == 3) {
				setValide("L'utilisateur a été supprimé.");
			}
		}
		if (popup!= null && arg0.getSource() == popup.getFrame() && popup.getTyp() == 6) {
			setValide("Le temps a été modifié.");
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		//do nothing

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		//do nothing

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		//do nothing

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		//do nothing

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		//do nothing

	}

	@Override
	public void windowActivated(WindowEvent e) {
		//do nothing

	}

}
