package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

/**
 * Classe super pratique qui permet de creer un panel contenant un titre
 * une liste en dessous ainsi que 3 boutons ajouter supprimer et modifier.
 * @author Konstantin&David
 * @version 1.1
 */
public class Liste {

	// objets de base pour une liste de categorie question etc
	private JButton bouton_ajouter, bouton_supprimer, bouton_modifier;
	private JPanel pan,pan_boutons;
	private JScrollPane scrollPane;
	private JList<String> liste;
	private JLabel titre_liste;

	/**
	 * Constructeur de la classe Liste elle reçoit en parametres une chaine de caractere
	 * qui va etre le titre de cette liste
	 * @param str titre de la liste
	 */
	public Liste(String str) {

		pan = new JPanel();
		scrollPane = new JScrollPane();
		pan_boutons = new JPanel();

		// creation d'un grid pour le aligner les boutons
		GridLayout gl = new GridLayout(3, 1);
		gl.setVgap(5);

		titre_liste = new JLabel(str);
		bouton_ajouter = new JButton("Ajouter");
		bouton_supprimer = new JButton("Supprimer");
		bouton_modifier = new JButton("Modifier");
		
		bouton_supprimer.setBackground(new Color(192, 57, 43));
		bouton_supprimer.setForeground(Color.WHITE);
		bouton_ajouter.setBackground(new Color(39, 174, 96));
		bouton_ajouter.setForeground(Color.WHITE);
		bouton_modifier.setBackground(new Color(41, 128, 185));
		bouton_modifier.setForeground(Color.WHITE);
	
		pan.add(titre_liste, BorderLayout.NORTH);

		setList();
		pan_boutons.setLayout(gl);
		pan_boutons.add(bouton_ajouter);
		pan_boutons.add(bouton_modifier);
		pan_boutons.add(bouton_supprimer);
		
		pan.add(pan_boutons, BorderLayout.SOUTH);
	}

	/**
	 * Création d'une Jlist appartir d'un ArrayString et réinsertion dans le panel
	 * @param tmp ArrayList<String> 
	 */
	public void setList(ArrayList<String> tmp) {

		pan.remove(scrollPane);
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String s : tmp) {
			model.addElement(s);
		}
		liste = new JList<String>(model);
		//on empeche la possibilité de selectionner plusieurs elements
		liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//on empeche la possibilité d'utiliser les fleches directionelles
		liste.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("UP"), "none");
		liste.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DOWN"), "none");
		
		scrollPane.setViewportView(liste);
		scrollPane.setPreferredSize(new Dimension(300, 200));

		pan.add(scrollPane, 1);
	}

	/**
	 * Création d'une Jlist vide (grisée) et réinsertion dans le panel
	 */
	public void setList() {

		pan.remove(scrollPane);
		scrollPane = new JScrollPane();

		scrollPane.setViewportView(null);
		scrollPane.setPreferredSize(new Dimension(300, 200));

		pan.add(scrollPane, 1);
	}

	/**
	 * recuperation du panel qui contient la liste , boutons et le titre
	 * @return du panel de l'objet
	 */
	public JPanel getPanel() {
		return pan;
	}

	/**
	 * recuperation de la jlist pour le raffraichissement des jlists lors d'une modification de donnée
	 * @return JList<String> 
	 */
	public JScrollPane getScrollPan() {
		return scrollPane;
	}
	
	/**
	 * recuperation de la jlist pour le raffraichissement des jlists lors d'une modification de donnée
	 * @return JList<String> 
	 */
	public JList<String> getList() {
		return liste;
	}

	/**
	 * permet la modification du titre
	 * @param str titre de la liste
	 */
	public void setTitre(String str) {
		titre_liste.setText(str);
	}

	/**
	 * permet de modifier la couleur du titre
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public void setTitreColor(int r,int g,int b) {
		titre_liste.setForeground(new Color(r, g, b));
	}
	
	/**
	 * get du bouton ajouter
	 * @return JButton ajouter
	 */
	public JButton getBouton_ajouter(){
		return bouton_ajouter;
	}
	/**
	 * get du bouton modifier
	 * @return JButton modifier
	 */
	public JButton getBouton_modifier(){
		return bouton_modifier;
	}
	/**
	 * get du bouton supprimer
	 * @return JButton supprimer
	 */
	public JButton getBouton_supprimer(){
		return bouton_supprimer;
	}
	
	/**
	 * Permet de supprimer un bouton de la liste envoyé en parametre, récuperer par les getters des boutons.
	 * @param bouton bouton de la liste ; bouton_ajouter , bouton_modifier et bouton_supprimer
	 */
	public void removeButton(JButton bouton){
		pan_boutons.remove(bouton);
	}
}
