package controles;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.sql.*;

/**
 * Classe qui gère toutes les actions sur la base de données ; recupération de
 * valeurs, modification et suppression. Pour les catégories, questions ,
 * sous-questions , palmares, et pour les utilisateurs. Mais aussi la
 * possibilité d'executer des scripts SQL.
 * 
 * @author Konstantin&David
 * @version 1.0
 */
public class GestionBDD {

	private ArrayList<String> list;
	private String driver, url, user, passwd;
	private Connection con;
	private Statement st;
	private PreparedStatement preparedStatement;
	private ResultSet rs;

	/**
	 * Constructeur de GestionBDD, récupere l'url, l'utilisateur et le mot de
	 * passe pour se connecter à la base de données.
	 * 
	 * @param url
	 *            de la bdd
	 * @param user
	 *            de la bdd
	 * @param mdp
	 *            de la bdd
	 */
	public GestionBDD(String url, String user, String mdp) {
		this.list = new ArrayList<String>();
		this.driver = "com.mysql.jdbc.Driver";
		this.url = "jdbc:" + url;
		this.user = user;
		this.passwd = mdp;
	}

	/**
	 * Méthode qui permet de se connecter à la base de données throws une
	 * exception si la connexion ne se fait pas.
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void connexion() throws Exception {
		try {
			con = DriverManager.getConnection(url, user, passwd);
			st = con.createStatement();
		} catch (SQLException ex) {
			throw new Exception("Exception: " + ex.getMessage());
		}
	}

	/**
	 * Méthode qui permet de se deconnecter de la base de données pour éviter de
	 * surcharger la bdd.
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void deconnexion() {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode qui retourne le tableau, résultat d'un requète.
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getList() {
		return list;
	}

	/**
	 * Méthode qui permet d'exécuter un script SQL, en paramètre elle reçoit le
	 * lien du fichier SQL
	 * 
	 * @param str
	 *            chemin du fichier
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void scriptBase(String str) throws Exception {
		connexion();
		BufferedReader in = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream(str), "UTF-8"));
		StringWriter out = new StringWriter();
		try {
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
			// obligé de decouper les requetes sql car executeUpdate
			// n'excecute
			// qu'une requete a la fois
			String[] table = out.toString().split("--#");
			Class.forName(driver).newInstance();
			int i = 0;
			while (table.length > i) {
				preparedStatement = con.prepareStatement(table[i]);
				preparedStatement.executeUpdate();
				i++;
			}
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception: " + e.getMessage());
		} finally {
			out.flush();
			out.close();
			in.close();
		}
		deconnexion();
	}

	/**
	 * Méthode qui permet de supprimer les tables de la base de données, elle
	 * enlève les contraintes de FK pour supprimer toutes les tables sans
	 * problème, et les remet apres la suppression.
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void suppressionBase() throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			st.executeUpdate("set foreign_key_checks =0");
			st.executeUpdate("DROP TABLE `categorie`, `historique`, `question`, `reponse`, `sous_question`, `temps`, `utilisateur`");
			st.executeUpdate("set foreign_key_checks =1");
		} catch (Exception ie) {
			throw new Exception("Exception: " + ie.getMessage());
		}
		deconnexion();
	}

	/*
	 * Categorie
	 */

	/**
	 * Méthode qui liste toute les catégories par ordre croissant enregistre le
	 * resultat dans un ArrayList<String>
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 * @return retourne le nombre de catégorie
	 */
	public int listerCategories() throws Exception {
		int nb_categorie = 0;
		try {
			connexion();
			Class.forName(driver).newInstance();
			rs = st.executeQuery("SELECT CATEGORIE FROM categorie ORDER BY CATEGORIE ASC");
			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String nom = rs.getString(1);
				list.add(nom); // ajout
			}
			rs = st.executeQuery("SELECT COUNT(*) FROM categorie");
			rs.next();
			nb_categorie = rs.getInt(1);
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
		return nb_categorie;
	}

	/**
	 * Méthode permettant d'ajouter une catégorie reçu en parametre, throws une
	 * exception qui va permetre apres d'afficher un message d'erreur sur
	 * l'interface java.
	 * 
	 * @param categorie
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void insertCategorie(String categorie) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "INSERT INTO categorie (CATEGORIE) VALUES (?)";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, categorie);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de supprimer une catégorie reçu en parametre, throws
	 * une exception qui va permetre apres d'afficher un message d'erreur sur
	 * l'interface java. Les questions et sous-questions sont supprimées
	 * automatiquement car les contraintes de FK sont en update on cascade et
	 * delete on cascade
	 * 
	 * @param categorie
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void deleteCategorie(String categorie) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "DELETE FROM categorie WHERE CATEGORIE=?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, categorie);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de modifier une catégorie reçu en parametre par un
	 * autre, throws une exception qui va permetre apres d'afficher un message
	 * d'erreur sur l'interface java. Les questions et sous-questions sont
	 * supprimées automatiquement car les contraintes de FK sont en update on
	 * cascade et delete on cascade
	 * 
	 * @param categorie
	 * @param categorie_modif
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void updateCategorie(String categorie_modif, String categorie)
			throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "UPDATE categorie SET CATEGORIE = ? WHERE CATEGORIE = ?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, categorie);
			preparedStatement.setString(2, categorie_modif);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/*
	 * Question
	 */
	/**
	 * Méthode qui liste toute les questions par ordre croissant appartement à
	 * la catégorie envoyée en parametre, enregistre le résultat sous la forme
	 * un+" ou "+autre+" ?" dans un ArrayList<String> .
	 * 
	 * @param categorie
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 * @return retourne le nombre de question appartenant à la catégorie envoyée
	 *         en parametre
	 */
	public int listerQuestions(String categorie) throws Exception {
		int nb_question = 0;
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "SELECT UN,AUTRE FROM question WHERE CATEGORIE = ?  ORDER BY UN ASC";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, categorie);
			rs = preparedStatement.executeQuery();
			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String question = rs.getString(1) + " ou " + rs.getString(2)
						+ " ?";
				list.add(question); // ajout
			}
			String selectSQL1 = "SELECT COUNT(*) FROM question WHERE CATEGORIE = ?";
			preparedStatement = con.prepareStatement(selectSQL1);
			preparedStatement.setString(1, categorie);
			rs = preparedStatement.executeQuery();
			rs.next();
			nb_question = rs.getInt(1);
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
		return nb_question;
	}

	/**
	 * Méthode qui permet de récuperer toutes les réponses possibles par ordre
	 * croissant.
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void listerReponses() throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();

			String selectSQL = "SELECT UN,AUTRE FROM question  ORDER BY UN ASC";
			preparedStatement = con.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();

			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add("Les deux");

			}
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant d'insérer une question dans la base de données, elle
	 * reçoit en parametre les champs à inserer dans la bdd
	 * 
	 * @param categorie
	 * @param un
	 * @param autre
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void insertQuestion(String categorie, String un, String autre)
			throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "INSERT INTO question (CATEGORIE,UN,AUTRE) VALUES (?,?,?)";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, categorie);
			preparedStatement.setString(2, un);
			preparedStatement.setString(3, autre);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de modifier une question ; sa catégorie, un et autre.
	 * Elle reçoit en parametre les anciennes valeurs ainsi que les nouvelles.
	 * Les contraintes de FK sont en update on cascade donc cela modifie les
	 * sous-questions qui possède un et autre en FK.
	 * 
	 * @param categorie
	 * @param un
	 * @param autre
	 * @param categorie_old
	 * @param un_old
	 * @param autre_old
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void updateQuestion(String categorie, String un, String autre,
			final String categorie_old, final String un_old,
			final String autre_old) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "UPDATE question SET UN = ? , AUTRE = ? , CATEGORIE = ? WHERE  UN = ? AND AUTRE = ? AND CATEGORIE = ?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, autre);
			preparedStatement.setString(3, categorie);
			preparedStatement.setString(4, un_old);
			preparedStatement.setString(5, autre_old);
			preparedStatement.setString(6, categorie_old);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de supprimer une question, elle reçoit en parametre
	 * les 2 champs permettant de supprimer la question
	 * 
	 * @param categorie
	 * @param un
	 * @param autre
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void deleteQuestion(String un, String autre) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "DELETE FROM question WHERE UN=? AND AUTRE=?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, autre);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/*
	 * Sous Question
	 */

	/**
	 * Méthode qui liste les sous-questions en fonction des champs un et autre
	 * envoyés.
	 * 
	 * @param un
	 * @param autre
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 * @return retourne le nombre de sous-question ayant comme question un et
	 *         autre
	 */
	public int listerSousQuestions(String un, String autre) throws Exception {
		int nb_sous_question = 0;
		try {
			connexion();
			Class.forName(driver).newInstance();

			String selectSQL = "SELECT SOUS_QUESTION,REPONSE,UN,AUTRE FROM sous_question WHERE UN = ? AND AUTRE = ?  ORDER BY SOUS_QUESTION ASC";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, autre);
			rs = preparedStatement.executeQuery();

			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String question = rs.getString(1) + " ? - ";
				if (rs.getInt(2) == 0) {
					question += rs.getString(3);
				} else if (rs.getInt(2) == 1) {
					question += rs.getString(4);
				} else {
					question += "Les deux";
				}
				list.add(question); // ajout
			}
			String selectSQL1 = "SELECT COUNT(*) FROM sous_question WHERE UN = ? AND AUTRE = ?";
			preparedStatement = con.prepareStatement(selectSQL1);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, autre);
			rs = preparedStatement.executeQuery();
			rs.next();
			nb_sous_question = rs.getInt(1);
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
		return nb_sous_question;
	}

	/**
	 * Méthode qui insere dans la base de données une nouvelle sous_question.
	 * 
	 * @param un
	 * @param autre
	 * @param question
	 * @param reponse
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void insertSousQuestion(String un, String autre, String question,
			int reponse) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "INSERT INTO sous_question (UN,AUTRE,SOUS_QUESTION,REPONSE) VALUES (?,?,?,?)";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, autre);
			preparedStatement.setString(3, question);
			preparedStatement.setInt(4, reponse);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de modifier une sous-question.
	 * 
	 * @param un
	 * @param autre
	 * @param question
	 * @param reponse
	 * @param question_old
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void updateSousQuestion(String un, String autre, String question,
			int reponse, String question_old) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "UPDATE sous_question SET SOUS_QUESTION = ?, REPONSE = ? WHERE  UN = ? AND AUTRE = ? AND SOUS_QUESTION = ?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, question);
			preparedStatement.setInt(2, reponse);
			preparedStatement.setString(3, un);
			preparedStatement.setString(4, autre);
			preparedStatement.setString(5, question_old);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode qui permet de supprimer une sous-question.
	 * 
	 * @param un
	 * @param autre
	 * @param question
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void deleteSousQuestion(String un, String autre, String question)
			throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "DELETE FROM sous_question WHERE UN=? AND AUTRE=? AND SOUS_QUESTION=?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, autre);
			preparedStatement.setString(3, question);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/*
	 * Palmares
	 */
	/**
	 * Méthode qui permet de lister le palmares, le résultat est enregistré dans
	 * un ArrayList sous la forme place+" place - "+pseudo+" - "+score.
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void listerPalmares() throws Exception {

		try {
			connexion();
			Class.forName(driver).newInstance();
			rs = st.executeQuery("SELECT PSEUDO,SCORE FROM historique ORDER BY SCORE DESC LIMIT 10");

			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// Stocker les enregistrements dans la liste
			int i = 1;
			while (rs.next()) {
				String question = i + " place - " + rs.getString(1) + " - "
						+ rs.getString(2);
				list.add(question);
				// ajout
				i++;
			}
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode qui permet d'ajouter un score
	 * 
	 * @param pseudo
	 * @param score
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void insertPalmares(String pseudo, String score) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "INSERT INTO historique (PSEUDO,SCORE) VALUES (?,?)";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, score);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de modifier un score.
	 * 
	 * @param pseudo_old
	 * @param score_old
	 * @param pseudo
	 * @param score
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void updatePalmares(String pseudo_old, String score_old,
			String pseudo, String score) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "UPDATE historique SET PSEUDO = ?, SCORE = ? WHERE  PSEUDO = ? AND SCORE = ?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, score);
			preparedStatement.setString(3, pseudo_old);
			preparedStatement.setString(4, score_old);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de supprimer un score du palmares.
	 * 
	 * @param pseudo
	 * @param score
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void deletePalmares(String pseudo, String score) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "DELETE FROM historique WHERE PSEUDO=? AND SCORE=?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, score);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/*
	 * Utilisateurs
	 */
	/**
	 * Méthode qui permet de lister les utilisateurs, enregistré dans un
	 * ArrayList<String>
	 * 
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void listerUtilisateurs() throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			rs = st.executeQuery("SELECT PSEUDO,MOT_DE_PASSE FROM utilisateur ORDER BY PSEUDO ASC");
			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String nom = rs.getString(1);
				list.add(nom); // ajout
			}
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant d'ajouter un utilisateur.
	 * 
	 * @param pseudo
	 * @param mdp
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void insertUtilisateurs(String pseudo, String mdp) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			//on encode le mot de passe en sha-512
			//on passe d'abord la chaine de cractere en bit
			//puis on l'encode et apres on la passe en hexa
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			digest.update(mdp.getBytes());
			byte[] messageDigest = digest.digest();
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}

			String selectSQL = "INSERT INTO utilisateur (PSEUDO,MOT_DE_PASSE) VALUES (?,?)";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, hexString.toString());

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/**
	 * Méthode permettant de supprimer un utilisateur
	 * 
	 * @param pseudo
	 * @throws Exception
	 *             pour le message d'erreur sur l'interface
	 */
	public void deleteUtilisateurs(String pseudo) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "DELETE FROM utilisateur WHERE PSEUDO=?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, pseudo);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/*
	 * TEMPS
	 */
	/**
	 * Méthode qui permet de recupérer le temps dans la bdd.
	 * 
	 * @return le temps mis dans la bdd
	 * @throws Exception
	 */
	public int getTemps() throws Exception {
		int temps = 10;
		try {
			connexion();
			Class.forName(driver).newInstance();
			rs = st.executeQuery("SELECT TEMPS FROM temps");
			rs.next();
			temps = rs.getInt(1);
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
		return temps;
	}

	/**
	 * Méthode qui permet de modifier le temps dans la bdd recu en parametre
	 * 
	 * @param temps
	 * @throws Exception
	 */
	public void setTemps(int temps) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			String selectSQL = "UPDATE temps SET TEMPS = ?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setInt(1, temps);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}

	/*
	 * Recherche
	 */
	/**
	 * Méthode qui permet de faire un recherche dans toute les tables de burger
	 * quiz
	 * 
	 * @param recherche
	 *            chaine de caractère recherchée
	 * @throws Exception
	 */
	public void rechercher(String recherche) throws Exception {
		try {
			connexion();
			Class.forName(driver).newInstance();
			// remise à 0 de la liste - utile pour les mises à jour
			list.clear();
			// categorie
			String selectSQL = "SELECT * FROM categorie WHERE CATEGORIE LIKE ? ";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, recherche + "%");

			rs = preparedStatement.executeQuery();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String nom = rs.getString(1) + " - catégorie";
				list.add(nom); // ajout
			}
			// question
			selectSQL = "SELECT UN,AUTRE FROM question WHERE UN LIKE ? OR AUTRE LIKE ?";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, recherche + "%");
			preparedStatement.setString(2, recherche + "%");

			rs = preparedStatement.executeQuery();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String nom = rs.getString(1) + " ou " + rs.getString(2)
						+ " - question";
				list.add(nom); // ajout
			}
			// sous_question
			selectSQL = "SELECT sous_question FROM SOUS_QUESTION WHERE SOUS_QUESTION LIKE ? ";
			preparedStatement = con.prepareStatement(selectSQL);
			preparedStatement.setString(1, recherche + "%");

			rs = preparedStatement.executeQuery();
			// Stocker les enregistrements dans la liste
			while (rs.next()) {
				String nom = rs.getString(1) + " - sous-question";
				list.add(nom); // ajout
			}
		} catch (Exception e) {
			throw new Exception("Exception: " + e.getMessage());
		}
		deconnexion();
	}
}