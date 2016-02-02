package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controles.GestionBDD;

/**
 * Interface de connexion a la base de données, contenant 3 champs pour se
 * connecter ainsi que 3 boutons pour quitter se connecter ou vider les champs.
 * Si le connexion n'est pas possible un message d'erreur est affiché
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class Login extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	// elements pour la fenetre de connexion
	private JTextField jtf_url, jtf_user, jtf_mdp;
	private GestionBDD bdd;
	private JLabel erreur;
	private JButton button_esc, button_supprimer, button_connexion;

	/**
	 * Constructeur de la classe, il créer un fenetre contenant les champs et
	 * les boutons et les places
	 */
	public Login() {
		// creation de la fenetre de base
		super("Burger Quiz");
		this.setLocation(400, 350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		// ajout de l'icone
		this.setIconImage(new Icone("/img/icon.png").getImage());

		JPanel p_center = new JPanel();
		JPanel p_bottom = new JPanel();

		// ajout du titre du jpanel
		JLabel titre = new JLabel("Connexion");
		Font font = new Font("Arial", Font.BOLD, 20);
		titre.setFont(font);
		getContentPane().add(titre, BorderLayout.NORTH);

		// ajout des champs de conexion
		jtf_url = new JTextField("mysql://ADRESSE_DE_LA_BDD/LA_BDD", 25);
		jtf_user = new JTextField("utilisateur", 15);
		jtf_mdp = new JPasswordField("password", 15);
		p_center.add(jtf_url);
		p_center.add(jtf_user);
		p_center.add(jtf_mdp);

		button_connexion = new JButton("Connexion");
		button_esc = new JButton("Quitter");
		button_supprimer = new JButton("Supprimer");
		erreur = new JLabel();
		erreur.setForeground(new Color(255, 0, 0));

		p_bottom.add(button_connexion);
		p_bottom.add(button_supprimer);
		p_bottom.add(button_esc);
		p_bottom.add(erreur);

		button_connexion.addActionListener(this);
		button_supprimer.addActionListener(this);
		button_esc.addActionListener(this);
		// ajout de mouselistener pour les champs qui permet de faire comme
		// un placeholder en html
		jtf_url.addMouseListener(this);
		jtf_user.addMouseListener(this);
		jtf_mdp.addMouseListener(this);

		getContentPane().add(p_center, BorderLayout.CENTER);
		getContentPane().add(p_bottom, BorderLayout.SOUTH);
		this.pack();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// bouton pour fermer la fenetre
		if (e.getSource() == button_esc) {
			Runtime.getRuntime().exit(1);
		}
		// bouton pour supprimer les champs
		if (e.getSource() == button_supprimer) {

			jtf_url.setText("");
			jtf_user.setText("");
			jtf_mdp.setText("");
		}
		// boutton pour la connexion
		if (e.getSource() == button_connexion) {

			try {
				bdd = new GestionBDD(jtf_url.getText(), jtf_user.getText(),
						jtf_mdp.getText());
				// si il arrive a se connecter alors on ferme la fenetre et on
				// ouvre l'interface
				// d'administration
				bdd.connexion();
				bdd.deconnexion();
				this.dispose();
				new Interface(bdd);
			} catch (Exception e1) {
				erreur.setText("Impossible de se connecter à la base de données.");
			}
		}
		this.pack();
	}

	// placeholder pour les champs
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == jtf_url
				&& "mysql://ADRESSE_DE_LA_BDD/LA_BDD".equals(jtf_url.getText())) {
			jtf_url.setText("");
		}
		if (arg0.getSource() == jtf_user
				&& "utilisateur".equals(jtf_user.getText())) {
			jtf_user.setText("");
		}
		if (arg0.getSource() == jtf_mdp
				&& "password".equals(jtf_mdp.getText())) {
			jtf_mdp.setText("");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Do nothing
	}

	@Override
	public void mouseExited(MouseEvent arg1) {
		// Do nonothing
	}

	@Override
	public void mousePressed(MouseEvent arg2) {
		// Do nononothing
	}

	@Override
	public void mouseReleased(MouseEvent arg3) {
		// Do nonononothing
	}
}
