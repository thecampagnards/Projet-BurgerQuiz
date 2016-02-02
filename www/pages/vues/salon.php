<!---------------------------------------------------->
<!----------ZONE-D'ATTENTE-D'UN-AUTRE-JOUEUR---------->
<!---------------------------------------------------->

<div id="salon" class="container">
	<div class="titre">
		<h2>Salon d'attente</h2>
	</div>
	<div class="content">
		<i class="icon-spin6 animate-spin"></i>
		<p id="messagesalon">Merci d'attendre l'arivée d'un second joueur<br>La partie démarrera automatiquement</p>
	</div>
</div>

<!--SCRIPTS ASSOCIES AU MULTIJOUEUR-->

<script src="js/node.js"></script>
<script> init("<?php echo $pseudo; ?>") </script>