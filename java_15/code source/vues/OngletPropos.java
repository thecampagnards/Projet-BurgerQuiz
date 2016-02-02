package vues;

import java.awt.Dimension;

import javax.swing.JLabel;


/**
 * Onglet a propos qui extends de Onglet, ou il y a juste une image de placé
 * @author Konstantin&David
 * @version 1.0
 */
public class OngletPropos extends Onglet{

	/**
	 * Constructeur sans parametre de la classe OngletPropos on dispose juste une image dans un panel
	 */
	public OngletPropos() {
		super("A propos");
		JLabel image = new JLabel(new Icone("/img/propos.png").getIcone());
		image.setPreferredSize(new Dimension(800,800));
		getPanel().add(image);
	}

}
