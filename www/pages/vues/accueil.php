<!---------------------------------------------------->
<!-------------------PAGE-D'ACCUEIL------------------->
<!---------------------------------------------------->

<div>
	<img id="logo1" src="img/logo2.png"/>
</div>
<div id="main">
	<div id="play">
		<div id="play_titre"><p>Jouer</p></div>
		<div id="play_hover">
			<div id="solo"><a href="index.php?page=partie&partie=start">Partie solo</a></div>
			<div id="multi"><a href="index.php?page=salon">Partie<br>multijoueur</a></div>
		</div>
	</div>
	
	<?php
		if($state == 'deconnecte') {
			echo '<div class="popup"><div class="triangle"></div><p>Vous devez vous inscrire ou vous connecter pour jouer</p></div>';
		} else if($jeu == 'off') {
			echo '<div class="popup"><div class="triangle"></div><p>Il y a un probléme avec les jeux de questions</p></div>';
		}
	?>
	
</div>