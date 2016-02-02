<!---------------------------------------------------->
<!------------------PAGE-DU-COMPTE-------------------->
<!---------------------------------------------------->

<div class="container">
	<div class="titre">
		<h2>Mon compte</h2>
	</div>
	<div class="menusecondaire">
		<ul>
			<li data-zone="zonestatistiques"><i class="icon-chart-line"></i>Statistiques</li>
			<li data-zone="zonescores"><i class="icon-child"></i>Scores</li>
			<li data-zone="zoneparametres"><i class="icon-tools"></i>Paramètres</li>
			<li data-zone="zonecontact"><i class="icon-doc-text"></i>Contact</li>
		</ul>
	</div>
	<div class="content hidden" id="zonestatistiques">
		<div class="zonecube">
			<div id="cube1" class="cube"><?php echo '<span data-value="'.$total.'">'.$total.'</span>'; ?><p>parties jouées</p></div>
			<div id="cube2" class="cube"><?php echo '<span data-value="'.$moyenne.'">'.$moyenne.'</span>'; ?><p>score moyen</p></div>
		</div>
		<div class="zonecube">
			<div id="cube3" class="cube"><?php echo '<span data-value="'.$pire.'">'.$pire.'</span>'; ?><p>pire score</p></div>
			<div id="cube4" class="cube"><?php echo '<span data-value="'.$meilleur.'">'.$meilleur.'</span>'; ?><p>meilleur score</p></div>
		</div>
	</div>
	<div class="content hidden" id="zonescores">
		<h3>Mes meilleurs scores</h3>
			<div id="palmares">
				<div><span>Place</span><span>Date</span><span>Score</span></div>
				<?php
					for($i = 1; $i < count($tabScores) + 1; $i++) {
						if($i < 11) {
							echo '<div><span>'.$i.'</span><span>'.$tabDates[$i-1].'</span><span>'.$tabScores[$i-1].'</span></div>';
						}
					}
				?>
			</div>
	</div>
	<div class="content hidden" id="zoneparametres">
		<p>Vous pouvez supprimer votres compte (toutes vos données seront définitivement<br>perdues), ou réinitialiser vos scores.</p>
		<div>
			<span class="buttonparam" id="supprcompte">Supprimer mon compte</span>
			<span class="buttonparam" id="supprscores">Réinitialiser mes scores</span>
		</div>
		<div class="popuphidden" id="popupsupprcompte">
			<h4>Voulez-vous supprimer votre compte ?</h4>
			<span><a href="index.php?page=compte&delete=all">Oui</a></span><span><a class="non" href="#">Non</a></span>
		</div>
		<div class="popuphidden" id="popupsupprscores">
			<h4>Voulez-vous réinitialiser vos scores ?</h4>
			<span><a href="index.php?page=compte&delete=scores">Oui</a></span><span><a class="non" href="#">Non</a></span>
		</div>
		<div class="popupbg"></div>
	</div>
	<div class="content hidden" id="zonecontact">
		<p>Vous pouvez nous contacter pour nous faire part de vos idées de questions, ou pour signaler des bugs.</p>
	
		<form class="formulaire" id="mycontactform" action="" method="post">
			<input required placeholder="Titre" type="text" name="titre">
			<input id="inputhidden" type="text" name="pseudo" value="<?php echo $pseudo; ?>">
			<textarea required placeholder="Message"></textarea>
			<input type="submit" id="submit" value="Envoyer">
			<div id="success" style="color: green;"></div>
		</form>
	</div>
</div>