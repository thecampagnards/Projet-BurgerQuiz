<?php

/*-------------------------------------------------------------*/
/*----CONNEXION-A-LA-BASE-DE-DONNEES - A MODIFIER (LIGNE 8)----*/
/*-------------------------------------------------------------*/

try {
	$db = new PDO('mysql:host=localhost;dbname=testf;charset=utf8', 'root', 'Yohann24*');
} catch (Exception $e) {
	echo $e->getMessage();
}

?>
