package vues;

import java.awt.*;
import javax.swing.*;

/**
 * Class qui permet de creer un splashscreen extends de JWindows
 * @author Konstantin&David
 * @version 1.0
 */
public class Splash extends JWindow {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de la classe on lui envoie en parametre le temps de chargement du splashscreen
	 * elle va creer tout le splashscreen
	 * @param d temps du thread en miliseconde
	 */
	public Splash(int d) {
		JPanel content = (JPanel) getContentPane();
		content.setBackground(Color.white);

		// Set the window's bounds, centering the window
		int width = 300;
		int height = 300;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		// Build the splash screen
		JLabel label = new JLabel(new Icone("/img/splash.gif").getIcone());
		JLabel copyrt = new JLabel("Copyright 2015, Konstantin & David",
				JLabel.CENTER);
		copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		content.add(label, BorderLayout.CENTER);
		//content.add(copyrt, BorderLayout.SOUTH);
		Color oraRed = new Color(41, 128, 185, 255);
		content.setBorder(BorderFactory.createLineBorder(oraRed, 5));
		// Display it
		setVisible(true);

		// Wait a little while, maybe while loading resources
		try {
			Thread.sleep(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(false);
	}


	/**
	 * Méthode qui va lancer un objet ici Login apres le chargement du thread
	 */
	public void showSplashAndExit() {
		new Login();
	}

}