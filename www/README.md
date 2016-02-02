#README - WEB

##INSTALATION DU SITE

Après avoir cloné le projet burgerquiz, placer le contenue dans un dossier à la racine d'un serveur.
Ainsi, il sera accessible a l'adresse <code>http://localhost/mondossier</code>


##CONNEXION A LA BASE DE DONNEE

Installer la base de donnée et y importer le script SQL contenant nos données.

Pour lier la BDD au site:
Dans le fichier <code>pages/bdd_connect.php</code> (ligne 8), modifier les champs en majuscule ci-dessous:
<code>$db = new PDO('mysql:host=ADRESSE_DE_LA_BASE;dbname=NOM_BASE;charset=utf8', 'LOGIN', 'MOT_DE_PASSE');</code>


##JEU MULTIJOUEUR

Après avoir installé node.js:
Lancer le serveur: aller dans dans <code>serveur</code>, saisir: <code>node serveur.js</code> puis taper sur "entrée".

Le jeu multijoueur fonctionne par défaut en local.

Pour jouer en multijoueur, il faut se connecter avec deux comptes différents sur le site. Il faut donc utiliser deux navigateurs différents OU, dans le même navigateur, ouvrir une fenêtre en mode navigation privée et une fenêtre en mode normal.


##INFORMATIONS COMPLEMENTAIRES SUR LE MODE MULTIJOUEUR

Pour faire fonctionner le quizz en multijoueur entre deux ordinateurs distants, deux manipulations sont à faire:

- Dans <code>pages/vues/header.php</code>, remplacer LOCALHOST (ligne 27) par l'adresse ip du PC ou se trouve le serveur
- Dans <code>js/node.js</code>, remplacer LOCALHOST (ligne 4) par par l'adresse ip du PC ou se trouve le serveur (L4).


**ATTENTION, SOUS WINDOWS, IL EST EGALEMENT NECESSAIRE DE DESACTIVER LE PARE-FEU**
