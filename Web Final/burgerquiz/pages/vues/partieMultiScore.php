<!---------------------------------------------------->
<!-----AFFICHAGE-DU-SCORE-EN-VERSION-MULTIJOUEUR------>
<!---------------------------------------------------->

<div class="container">
	<div class="titre">
		<h2>Fin de la partie !</h2>
	</div>
	<?php
		echo '<div class="score"><div><p>Votre score</p></div><div data-value="'.$score.'" class="resultat resultatmulti"><span id="chiffre1">'.$score.'</span><div class="max"><p>/100</p></div></div></div>';	
		echo '<div class="score"><div><p id="adv">Votre concurent</p></div><div data-value="" class="resultat resultatmulti"><span id="chiffre2"></span><div class="max"><p>/100</p></div></div></div>';	
	?>

	<div class="savescore">
		<a href="index.php?page=palmares">Voir le palmares</a>
	</div>
</div>

<!--SCRIPTS ASSOCIES AU MULTIJOUEUR-->

<script src="js/node.js"></script>
<?php echo '<script> game("'.$pseudo.'","'.$score.'"); </script>'; ?>