<!---------------------------------------------------->
<!------------------PAGE-DU-PALMARES------------------>
<!---------------------------------------------------->

<div class="container">
	<div class="titre">
		<h2>Palmarès</h2>
	</div>
	<div id="palmares">
		<div><span>Place</span><span>Pseudo</span><span>Score</span></div>
		<?php
			for($i = 1; $i < count($tabScores)+1; $i++) {
				echo '<div><span>'.$i.'</span><span>'.$tabPseudos[$i-1].'</span><span>'.$tabScores[$i-1].'</span></div>';
			}
		?>
	</div>
</div>