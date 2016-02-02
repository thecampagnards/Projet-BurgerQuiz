<!---------------------------------------------------->
<!-----AFFICHAGE-DU-JEU-(QUESTIONS-ET-REPONSES)------->
<!---------------------------------------------------->


<div id="time_bar">
	<?php echo '<div data-value="'.$temps.'" id="time"></div>'; ?>
</div>
<div id="actuel" class="container" >
	<?php echo '<h2>'.$lun.', '.$lautre.' ou les deux ?</h2><h3 class="voixActivee">'.$sousQuestionActuelle.'</h3>'; ?>
			
	<div class="sous_container">
		<?php
			echo '<div class="reponse"><a href="index.php?page=partie&partie=play&choix=0" id="lun"><button onClick="reponse()" type="button">'.$lun.'</button></a></div>';
			echo '<div class="reponse"><a href="index.php?page=partie&partie=play&choix=1" id="lautre"><button onClick="reponse()" type="button">'.$lautre.'</button></a></div>';
			echo '<div class="reponse"><a href="index.php?page=partie&partie=play&choix=2" id="lesdeux"><button onClick="reponse()" type="button">Les deux</button></a></div>';
		?>
	</div>
	<div class="reset"></div>
</div>
<div id="precedent" class="container">
	<?php echo '<h2>'.$lunPrecedent.', '.$lautrePrecedent.' ou les deux ?</h2><h3>'.$sousQuestionPrecedente.'</h3>'; ?>
			
	<div class="sous_container">
		<?php
			echo '<div class="reponse"><p id="lun"><button class="'.$classlun.'" type="button" disabled>'.$lunPrecedent.'</button></p></div>';
			echo '<div class="reponse"><p id="lautre"><button class="'.$classlautre.'" type="button" disabled>'.$lautrePrecedent.'</button></p></div>';
			echo '<div class="reponse"><p id="lesdeux"><button class="'.$classlesdeux.'" type="button" disabled>Les deux</button></p></div>';
		?>
	</div>
	<div class="reset"></div>
	
	<?php echo '<div id="'.$idMessage.'"><p>'.$adversaire.' a été plus rapide que vous !</p></div>'; ?>

</div>
<div id="zoneetapetexte"><?php echo '<p data-value="'.$texteEtape.'">'.$texteEtape2.'<br></p><span>'.$nbBonnesReponses.' Bonnes réponses</span>'; ?> </div>
<div id="zoneetape"></div>

<!--SCRIPTS ASSOCIES AU MULTIJOUEUR-->

<?php if(isset($adversaire)) {
echo '	<script src="js/node.js"></script>
		<script>
			game("'.$monpseudo.'","'.$monscore.'");
		</script>';
} ?>