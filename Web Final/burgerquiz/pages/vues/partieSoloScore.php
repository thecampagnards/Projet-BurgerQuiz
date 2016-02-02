<!---------------------------------------------------->
<!---------AFFICHAGE-DU-SCORE-EN-VERSION-SOLO--------->
<!---------------------------------------------------->

<div class="container">
	<div class="titre">
		<h2>Fin de la partie !</h2>
	</div>
	
	<?php
		echo '<div data-value="'.$score.'" class="resultat"><span id="chiffre1">'.$score.'</span><div class="max"><p>/100</p></div></div>';	
	?>

	<div class="savescore">
		<a href="index.php?page=palmares">Sauvegarder mon score<br>et voir le palmares</a>
	</div>
</div>