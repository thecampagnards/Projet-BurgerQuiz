package vues;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe qui creer un onglet de base cad : <br>
 * - le titre de l'onglet recu dans le contructeur <br>
 * - un message d'introduction ou d'explication de l'onglet <br>
 * - un message d'erreur<br>
 * - un message de validation<br>
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class Onglet {

	// elements de base pour un l'onglet
	private JPanel pan, pan_text;
	private JLabel jlab_titre;
	private JLabel jlab_erreur, jlab_intro, jlab_valide;
	// popup permettant la modif suppression et ajout
	protected Popup popup;

	/**
	 * Constructeur de la classe onglet, il créer les onglets et les places dans
	 * un JPanel
	 * 
	 * @param str
	 *            titre de l'onglet
	 */
	public Onglet(String str) {

		// init des panels
		pan = new JPanel();
		pan_text = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));

		// creation d'un grid pour aligner les elements
		GridLayout gl = new GridLayout(4, 2);
		gl.setVgap(-20);
		pan_text.setLayout(gl);

		// init des jlab
		jlab_erreur = new JLabel();
		jlab_valide = new JLabel();
		jlab_intro = new JLabel();

		addTitre(str);

		jlab_erreur.setForeground(new Color(255, 0, 0));
		jlab_valide.setForeground(new Color(0, 128, 0));

		pan_text.add(jlab_titre);
		pan_text.add(jlab_intro);
		pan_text.add(jlab_valide);
		pan_text.add(jlab_erreur);

		pan.remove(jlab_titre);
		pan.add(pan_text);
	}

	public void addTitre(String str) {
		jlab_titre = new JLabel(str);
		Font font = new Font("Arial", Font.BOLD, 20);
		try {
			// import d'une police d'ecriture
			font = Font.createFont(Font.TRUETYPE_FONT, getClass()
					.getResourceAsStream("/doc/Horsal.ttf"));
			font = font.deriveFont(Font.TRUETYPE_FONT, 40);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		jlab_titre.setFont(font);
		pan.add(jlab_titre);
	}

	/**
	 * Permet de rafraichir le panel
	 */
	public void repaint() {
		pan.setVisible(false);
		pan.setVisible(true);
	}

	/**
	 * permet de rentrer un message d'intro pour l'onglet
	 * 
	 * @param str
	 *            texte d'intro
	 */
	public void setIntro(String str) {
		jlab_intro.setText(str);
		repaint();
	}

	/**
	 * permet d'afficher un message d'erreur
	 * 
	 * @param str
	 *            message d'erreur
	 */
	public void setErreur(String str) {
		jlab_erreur.setText(str);
		repaint();
	}

	/**
	 * permet d'afficher un message d'operation effectuée correctement
	 * 
	 * @param str
	 *            message d'operation
	 */
	public void setValide(String str) {
		jlab_valide.setText(str);
		repaint();
	}

	/**
	 * Permet de recuperer le Jpanel de l'onglet
	 * 
	 * @return panel de l'onglet
	 */
	public JPanel getPanel() {
		return pan;
	}

	/**
	 * Permet d'ajouter un objet au panel text
	 * 
	 * @param objet
	 *            à rajouter dans le panel de texte de l'onglet
	 * @param place
	 *            dans le panel
	 */
	public void addCompomentText(Component objet, int place) {
		pan_text.add(objet, place);
	}

	/**
	 * Méthode permettant de fermer un popup
	 * 
	 */
	public void closePopup() {
		try {
			if(popup != null) {
				popup.getFrame().dispose();
				popup = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
