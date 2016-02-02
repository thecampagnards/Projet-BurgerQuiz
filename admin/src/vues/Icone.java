package vues;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Classe qui permet de créer un icone à partir d'une image 
 * qui va après servir pour les fenetres et pour les onglets
 * @author Konstantin&David
 * @version 1.0
 */
public class Icone {

	private ImageIcon icon;

	/**
	 * Dans le constructeur on envoie le lien de l'image.
	 * ce lien va après être envoyé dans la méthode createImageIcon qui va ouvrir l'image.
	 * @param str lien de l'image
	 */
	public Icone(String str) {
		icon = createImageIcon(str);
	}
 
	/**
	 * ouverture de l'image grâce à un lien et return de l'ImageIcon
	 * @param path lien de l'image
	 * @return ImageIcon
	 */
	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Interface.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			return null;
		}
	}

	/**
	 * méthode permettant de recupérer un ImageIcon
	 * @return ImageIcon
	 */
	public ImageIcon getIcone() {
		return icon;
	}

	/**
	 * méthode permettant de recupérer une Image
	 * @return Image
	 */
	public Image getImage() {
		return icon.getImage();
	}
}
