README - WEB

/*----------INSTALATION DU SITE----------*/

Après avoir décompréssé le dossier burgerquiz, le placer à la racine d'un serveur.
Ainsi, il sera accessible a l'adresse http://localhost/burgerquiz


/*----------CONNEXION A LA BASE DE DONNEE----------*/

Installer la base de donnée et y importer le script SQL contenant nos données.

Pour lier la BDD au site:
Dans le fichier pages/bdd_connect.php (ligne 8), modifier les champs en majuscule ci-dessous:
$db = new PDO('mysql:host=ADRESSE_DE_LA_BASE;dbname=NOM_BASE;charset=utf8', 'LOGIN', 'MOT_DE_PASSE');


/*----------JEU MULTIJOUEUR----------*/

Après avoir installé node.js:
Lancer le serveur: aller dans dans burgerquiz/serveur, saisir: node serveur.js puis taper sur "entrée".

Le jeu multijoueur fonctionne par défaut en local.

Pour jouer en multijoueur, il faut se connecter avec deux comptes différents sur le site. Il faut donc utiliser deux navigateurs différents OU, dans le même navigateur, ouvrir une fenêtre en mode navigation privée et une fenêtre en mode normal.


/*----------INFORMATIONS COMPLEMENTAIRES SUR LE MODE MULTIJOUEUR----------*/

Pour faire fonctionner le quizz en multijoueur entre deux ordinateurs distants, deux manipulations sont à faire:

- Dans pages/vues/header.php, remplacer LOCALHOST (ligne 27) par l'adresse ip du PC ou se trouve le serveur
- Dans js/node.js, remplacer LOCALHOST (ligne 4) par par l'adresse ip du PC ou se trouve le serveur (L4).


!!! ATTENTION, SOUS WINDOWS, IL EST EGALEMENT NECESSAIRE DE DESACTIVER LE PARE-FEU !!!
