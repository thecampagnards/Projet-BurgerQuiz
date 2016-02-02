package vues;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controles.GestionBDD;

/**
 * OngletRecherche extends de Onglet, dans cette onglet on peut rechercher une donnée rentrée dans un
 * jtfield dans la base de données
 * @author Konstantin&David
 * @version 1.0
 */
public class OngletRecherche extends Onglet implements KeyListener {

	private Liste recherche;
	private JTextField jtf_recherche;
	private static final Dimension DEFAULT_TEXT_FIELD_DIMENSION = new Dimension(
			4000, 30);
	private GestionBDD bdd;


	/**
	 * Constructeur de OngletRecherche on lui passe en parametre l'objet bdd pour avior acces a celle ci
	 * @param bdd GestionBDD pour avoir les acces a la bdd
	 */
	public OngletRecherche(GestionBDD bdd) {
		super("Recherche");
		this.bdd = bdd;
		JLabel texte = new JLabel(
				"Entrez votre recherche dans le champs pour trouver une catégorie ou une question ou une sous-question.");
		recherche = new Liste("");
		try {
			bdd.rechercher("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		recherche.setList(bdd.getList());
		recherche.removeButton(recherche.getBouton_ajouter());
		recherche.removeButton(recherche.getBouton_supprimer());
		recherche.removeButton(recherche.getBouton_modifier());

		jtf_recherche = new JTextField();
		jtf_recherche.setMaximumSize(DEFAULT_TEXT_FIELD_DIMENSION);
		jtf_recherche.addKeyListener(this);

		recherche.getScrollPan().setPreferredSize(new Dimension(450, 450));

		getPanel().add(texte);
		getPanel().add(Box.createHorizontalStrut(10));
		getPanel().add(jtf_recherche);
		getPanel().add(Box.createHorizontalStrut(2));
		getPanel().add(recherche.getPanel());
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getSource() == jtf_recherche) {
			try {
				bdd.rechercher(jtf_recherche.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			recherche.setList(bdd.getList());
			getPanel().add(recherche.getPanel(), 5);
			repaint();
			jtf_recherche.requestFocus();
			recherche.getScrollPan().setPreferredSize(new Dimension(450, 450));

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
