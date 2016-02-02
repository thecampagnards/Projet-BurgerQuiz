import vues.Splash;

/**
 * Classe Principale qui creer un splashscreen.
 * @author Konstantin&David
 * @version 1.0
 */
public class Principale {
	//temps de chargement du splashscreen
	private static final int TEMP = 3000;
	
	private Principale(){
		
	}
	public static void main(String[] args) {
		Splash splash = new Splash(TEMP);
		splash.showSplashAndExit();
	}
}
