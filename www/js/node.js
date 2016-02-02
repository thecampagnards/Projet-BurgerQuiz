/**************************************************
** GAME VARIABLES
**************************************************/
var ip = 'localhost';
var socket;			// Socket
var pseudo;
/**************************************************
** GAME INITIALISATION
**************************************************/
function init(pseudo) {
	this.pseudo=pseudo;
    // Connexion à socket.io
    socket = io.connect('http://'+ip+':8000');
    // Gestion des evenements
    setEventHandlers();
    // On demande le pseudo a l'utilisateur, on l'envoie au serveur et on l'affiche dans le titre
    socket.emit('nouveau', pseudo);
};

function game(pseudo,score){
	this.pseudo=pseudo;
    // Connexion à socket.io
    socket = io.connect('http://'+ip+':8000');
    // Gestion des evenements
    setEventHandlers();
    // On demande le pseudo a l'utilisateur, on l'envoie au serveur et on l'affiche dans le titre
    socket.emit('game', pseudo);
	socket.emit('score', pseudo,parseInt(score));
	
}
/**************************************************
** RECEPTION DU SERVEUR
**************************************************/
var setEventHandlers = function() {
	socket.on("versus", onAdversaire);
	socket.on("repondu",onRepondu);
	//socket.on("disconnect",onDisconnect);
	socket.on("score",onScore);
	//socket.on("ingame",onIngame);
};
//affiche la div si les 2 joueurs ont leur page chagée
function onIngame(){
	
}
//modification du salon
function onAdversaire(pseudo,pseudo_adv) {
	$("#salon h2").html(pseudo+' versus '+pseudo_adv);
	$("#messagesalon").html("Vous avez trouvé un adversaire, le jeu va débuter !");
	setTimeout(function(){
		window.location.replace("index.php?page=partie&partie=start&adversaire="+pseudo_adv);
	}, 5000);
};
//recupere le score de l'adversaire
function onScore(score,pseudo_adv){

if($('#chiffre2').length) {
	
	$('#adv').html('Score de '+pseudo_adv);
	
	if(score === undefined){
		alert('votre adversaire a quité la partie');
		$('#chiffre2').html("Votre adversaire a quité la partie.");
		$('#chiffre2').parent().attr("data-value", "Votre adversaire a quité la partie.");
	}
	else {
		$('#chiffre2').html(score);
		$('#chiffre2').parent().attr("data-value", score);
	}
}
};
//si l'adversaire repond avant vous
/*$.urlParam = function(name){
    var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
}*/
function onRepondu(){
	if(1/*$.urlParam('choix')*/){
		window.location.href += '&choix=4';
	}
};
/*
function onDisconnect(){
	alert('votre opposant a quitté la partie !');
};*/
/**************************************************
** ENVOIE AU SERVEUR
**************************************************/
//quand on repond envoie un message au serveur
function reponse(){
	socket.emit("reponse", this.pseudo);
};
