var io = require('socket.io');
var ent = require('ent'); // Chargement du module pour mettre en place les websockets
var express = require("express");

// Variables
var server; // Le socket
var liste_joueur = {};
var nb_joueur = 0;
var pseudo_last;
// Gestion des evenements
// Attend l'évènement "connection"
// Le client génère cet évènement lorsque la connexion est établie avec le serveur (voir l'établissement de connexion côté client)
// En cas de connexion appel à la fonctione onSocketConnection
// Un paramètre est envoyé en paramètre de l'évènement "connection" : ce paramètre représente le client
var setEventHandlers = function() {
    server.sockets.on("connection", onSocketConnection);
};

// Fonction prenant en paramètre le client (voir ci-dessus)
// Réception ou envoi d'évènement à partir de cet objet : client
function onSocketConnection(socket) {

    // Attente de l'évènement "new"
    // Dans cet exemple l'évènement "new" est envoyé avec un paramètre "pseudo"

    socket.on('nouveau', function(pseudo) {
        //encodage du pseudo pour eviter les injections
		pseudo = ent.encode(pseudo);
		//on rajoute un objet joueur
		var obj = new Object();
			obj["dispo"] = true;
			obj["adversaire"] ;
			obj["score"] = 0;
			obj["ingame"] = false;
			obj["socket"] = socket;
		liste_joueur[pseudo] = obj;
		nb_joueur ++;
		console.log('Nouveau Joueur : '+ pseudo +' id : '+socket.id+' nb de joueur :'+nb_joueur+'!');
		//recherche un joueur dispo (l'avant dernier rentré)
		if(pseudo_last != undefined && liste_joueur[pseudo_last]["dispo"] == true && pseudo_last != pseudo){
			//mets les 2 joueurs non dispo
			liste_joueur[pseudo_last]["adversaire"] = pseudo;
			liste_joueur[pseudo]["adversaire"] = pseudo_last;
			//garde le pseudo de leur adversaire
			liste_joueur[pseudo_last]["dispo"] = false;
			liste_joueur[pseudo]["dispo"] = false;
			//envoie le message
			console.log(pseudo +' versus '+pseudo_last);
			var socket_adv = liste_joueur[pseudo_last]["socket"];
			socket_adv.emit('versus',pseudo_last, pseudo);
			socket.emit('versus',pseudo , pseudo_last);
		}
		//sinon le dernier rentré est dans la queue
		pseudo_last=pseudo;
    });
	socket.on('game', function(pseudo) {
		//regarde si les 2 joueurs sont dans la partie
		/*pseudo_adv = liste_joueur[pseudo]["adversaire"];
		if(liste_joueur[pseudo]["ingmae"] == true && liste_joueur[pseudo_adv]["ingame"] == true){
			socket.broadcast.emit('ingame');
			socket.emit('ingame');
			//remet a false pour la prochaine question
			liste_joueur[pseudo]["ingmae"] = false;
			liste_joueur[pseudo_adv]["ingame"] == false;
			console.log(pseudo +' et '+pseudo_adv+' sont pret!');
		}else{
			liste_joueur[pseudo]["ingmae"] = true;
		}*/
		//affiche un message quand le joueur est dans la partie
		liste_joueur[pseudo]["socket"] = socket;
		console.log(pseudo +' est pret !');
	});
	socket.on('reponse', function(pseudo) {
		//envoie un message quand le mec a repondu
		pseudo_adv = liste_joueur[pseudo]["adversaire"];
		var socket_adv = liste_joueur[pseudo_adv]["socket"];
		socket_adv.emit('repondu');
		console.log(pseudo +' a repondu !');
	});
	socket.on('score', function(pseudo,score) {
		//on recupere le score du joueur
		liste_joueur[pseudo]["score"] = score;
		pseudo_adv = liste_joueur[pseudo]["adversaire"];
		//on envoie a son adversaire son score
		pseudo_adv = liste_joueur[pseudo]["adversaire"];
		var socket_adv = liste_joueur[pseudo_adv]["socket"];
		socket_adv.emit('score', score,pseudo);
		//lui envoie le score de son adversaire
		socket.emit('score', liste_joueur[pseudo_adv]["score"],pseudo_adv);
		console.log(pseudo +' score : '+score);
		console.log('le score de son adv est : '+liste_joueur[pseudo_adv]["score"]);
	});
	socket.on('disconnect', function() {
		//si la page du joueur se ferme
		liste_joueur[pseudo_last]["dispo"] = false;
		console.log(pseudo_last+ ' a quitté le salon !');
	});
};

// Initialisation
function init() {
    // Le server temps réel écoute sur le port 8000
    server = io.listen(8000);
    // Gestion des évènements
    setEventHandlers();
};

// Lance l'initialisation
init();

