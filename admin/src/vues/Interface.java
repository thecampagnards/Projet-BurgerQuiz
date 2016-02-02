package vues;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import controles.GestionBDD;

/**
 * Classe qui creer l'interface administrative, elle gere les differents onglets
 * ; onglet pour la configuration du jeu, onglet des options, onglet pour
 * quitter , onglet pour raffraichir la fenetre et onglet pour se deconnecter
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class Interface extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JMenu jeu, option, rechercher, aide, propos, rafraichir,
			deconnexion, quitter;
	private GestionBDD bdd;
	private CardLayout cards;
	private JPanel cards_pan;
	private OngletJeu onglet_jeu;
	private OngletOption onglet_option;
	private OngletRecherche onglet_recherche;
	private OngletAide onglet_aide;
	private OngletPropos onglet_propos;

	/**
	 * Constructeur d'Interface, creer la fenetre avec un menu
	 * 
	 * @param bdd
	 *            l'objet GestionBDD pour permettre la connexion à la bdd.
	 */
	public Interface(GestionBDD bdd) {

		// creation de la fenetre de base
		super("Burger Quiz");
		this.bdd = bdd;

		// ajout d'un icone pour la fenetre
		this.setIconImage(new Icone("/img/icon.png").getImage());

		JMenuBar menu = new JMenuBar();
		jeu = new JMenu("Configuration du Jeu");
		option = new JMenu("Options");
		rechercher = new JMenu("Recherche");
		aide = new JMenu("Aide");
		propos = new JMenu("A propos");

		rafraichir = new JMenu("Rafraichir");
		deconnexion = new JMenu("Deconnexion");
		quitter = new JMenu("Quitter");
		rafraichir.setIcon(new Icone("/img/rafraichir.png").getIcone());
		quitter.setIcon(new Icone("/img/esc.png").getIcone());
		deconnexion.setIcon(new Icone("/img/deconnexion.png").getIcone());

		menu.add(jeu);
		menu.add(option);
		menu.add(rechercher);
		menu.add(aide);
		menu.add(propos);
		menu.add(Box.createHorizontalGlue());
		menu.add(rafraichir);
		menu.add(deconnexion);
		menu.add(quitter);

		jeu.addMouseListener(this);
		option.addMouseListener(this);
		rechercher.addMouseListener(this);
		aide.addMouseListener(this);
		propos.addMouseListener(this);
		rafraichir.addMouseListener(this);
		deconnexion.addMouseListener(this);
		quitter.addMouseListener(this);

		onglet_jeu = new OngletJeu(bdd);
		onglet_option = new OngletOption(bdd);
		onglet_recherche = new OngletRecherche(bdd);
		onglet_aide = new OngletAide();
		onglet_propos = new OngletPropos();

		cards = new CardLayout();
		cards_pan = new JPanel();
		cards_pan.setLayout(cards);
		cards_pan.add(onglet_jeu.getPanel(), "jeu");
		cards_pan.add(onglet_option.getPanel(), "options");
		cards_pan.add(onglet_recherche.getPanel(), "recherche");
		cards_pan.add(onglet_aide.getPanel(), "aide");
		cards_pan.add(onglet_propos.getPanel(), "propos");

		this.add(cards_pan);
		this.setJMenuBar(menu);
		this.setLocation(300, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(930, 660));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == jeu) {
			cards.show(cards_pan, "jeu");
		} else if (arg0.getSource() == option) {
			cards.show(cards_pan, "options");
		} else if (arg0.getSource() == rechercher) {
			cards.show(cards_pan, "recherche");
		} else if (arg0.getSource() == aide) {
			cards.show(cards_pan, "aide");
		} else if (arg0.getSource() == propos) {
			cards.show(cards_pan, "propos");
		} else if (arg0.getSource() == rafraichir) {
			new Interface(bdd);
			this.dispose();
			onglet_jeu.closePopup();
			onglet_option.closePopup();
		} else if (arg0.getSource() == quitter) {
			Runtime.getRuntime().exit(1);
		} else if (arg0.getSource() == deconnexion) {
			new Login();
			this.dispose();
			onglet_jeu.closePopup();
			onglet_option.closePopup();

		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Do nothing

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Do nothing

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Do nothing

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Do nothing

	}
}
