/*----------------------------------------------------------------*/
/*-----------------FONCTIONS-NECESSAIRES-AU-JEU-------------------*/
/*----------------------------------------------------------------*/

/*--------Récupère un paramètre de l'URL (et vérifie son exitance)--------*/

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    } else {
       return results[1] || 0;
    }
}    

/*--------Lance le jeu après l'affichage des categories--------*/

function play(){
		window.location.href = window.location.href.replace( /[\?#].*|$/, "?page=partie&partie=play" );
} 

/*--------Passe à la question suivante si l'utilisateur n'a pas répondu--------*/

function questionSuivante(){
		window.location.href = window.location.href.replace( /[\?#].*|$/, "?page=partie&partie=play&choix=3" );
}

/*--------Lecture du texte, compatible avec la majorité des navigateurs--------*/

function saidTexte(texte){
	responsiveVoice.speak(texte,'French Female');
}

/*--------Lecture du texte, compatible uniquement avec Chrome et Safari--------*/

function saidTexteNoChrome(texte) {
	meSpeak.speak(texte);
}

$(document).ready(function() {
	
	
	/*Récupération du temps maximal pour répondre*/
	
	if($('#time').attr("data-value") > 4999 && $('#time').attr("data-value") < 15001) {
		var temps = parseInt($('#time').attr("data-value"));
	} else {
		var temps = 5000; // Valeur par défaut
	}
	
	/*Gestion de l'affichage, de la durée de réponse et de la synthése vocale lors de la partie*/
	
	if($.urlParam('partie') == 'start') {
		window.setTimeout(play, 8000);
		$('#time').animate({'width': '100%'}, 8000);
	} else if($.urlParam('partie') == 'play' &&  !$('#resultat').length) {
	
		var texte = $('#actuel h3').text();
		var voix = $('#actuel h3').attr("class");
		var ischrome = /chrome/.test( navigator.userAgent.toLowerCase() );
		
		/*Activation d'une voix moche pour les non-utilisateurs de Google Chrome*/
		
		if(!ischrome && voix) {
			meSpeak.loadConfig("js/mespeak/mespeak_config.json");
			meSpeak.loadVoice("js/mespeak/voices/fr.json");
		}
		
		if($('#precedent h3').text() == '0') {
			$('#precedent').hide(0);
			$('#actuel').animate({'opacity':'1', 'top':'0px'}, 0);
			window.setTimeout(questionSuivante, temps);
			$('#time').animate({'width': '100%'}, temps);
			if(voix) {
				if(ischrome) {
					window.setTimeout(saidTexte, 200, texte);
				} else {
					window.setTimeout(saidTexteNoChrome, 10, texte);
				}
			}
			var test = $('#zoneetapetexte p').attr("data-value");
			$('#zoneetapetexte p').html($('#zoneetapetexte p').attr("data-value"));
			
		} else if($('#actuel h3').text() == '0') {
			$('#actuel').hide(0);
			window.setTimeout(questionSuivante, 3000);
		
		} else {
			$('#precedent').show(0).delay(2800).animate({'opacity':'0', 'top':'0px'}, 200).hide(0);
			$('#actuel').hide(0).delay(3000).show(0).animate({'opacity':'1', 'top':'0px'}, 200);
			$('#time').delay(3000).animate({'width': '100%'},temps);
			
			if(texte.length) {
				window.setTimeout(questionSuivante, temps + 3000);
				if(voix) {
					if(ischrome) {
						window.setTimeout(saidTexte, 3050, texte);
					} else {
						window.setTimeout(saidTexteNoChrome, 1500, texte);
					}
				}
			}
				
			$('#zoneetapetexte p').delay(3000).queue(function(n) {
				$(this).html($(this).attr("data-value"));
				n();
			});
		
		}
	}
});

/*----------------------------------------------------------------*/
/*-----FONCTIONS-NON-NECESSAIRES-AU-JEU-(PAGE-D'ACCUEIL,-ECT)-----*/
/*----------------------------------------------------------------*/

$(document).ready(function() {
		$('#play').hover(function() {
			if(!$('.popup').length) {
				$(this).find('p').toggleClass('disparition');
				$(this).find('a').toggleClass('apparition');
			}
			
			$(this).toggleClass('interdit');
	
			if($('.popup').length) {
				$('.popup').toggleClass('showpopup');
			}
		});
		
		$('#play_hover a').mouseenter(function() {
			$(this).parent().animate({'opacity':'0.6'},200);
		}).mouseout(function() {
			$(this).parent().animate({'opacity':'1'},200);
		});
		
		$('nav ul li a').mouseenter(function() {
			$(this).animate({'opacity':'0.6'},200);
		}).mouseout(function() {
			$(this).animate({'opacity':'1'},200);
		});
		
		if($('#chiffre1').length && !($('#chiffre2').length )) {
			var total1 = document.getElementById("chiffre1").innerHTML;
			document.getElementById("chiffre1").innerHTML = 0;
			window.setInterval("animChiffre("+total1+")", 45); 
			
			if(!($('#chiffre2').length)) {
				$('.resultat').each(function() {
					if(parseInt($(this).attr("data-value")) < 50) {
						$(this).addClass('faux');
					} else {
						$(this).addClass('vrai');
					}
				});
			}
		}
});

$(document).ready(function(){
	if(document.referrer == "" && !($.urlParam('page'))) {
		$('body').prepend('<div class="loading"><img alt="logo" src="img/logoanime4.gif"/></div>');
		$('.loading').delay(2700).animate({'opacity':'0','top':'-80px'}, 700).hide(0);
	}
	
});

/*----------------------------------------------------------------*/
/*--------------------------PAGE-COMPTE---------------------------*/
/*----------------------------------------------------------------*/

$(document).ready(function(){
	
	$('.hidden').css("display","none");
	$('#zonestatistiques').css("display","block");
	$('.menusecondaire ul li:first-child').css("opacity","1");
	
	$(".menusecondaire ul li").click(function() {

		$('.menusecondaire ul li').animate({'opacity':'0.3'},100);
		$(this).animate({'opacity':'1'},200);
		$('.hidden').animate({'opacity':'0','top':'20px'}, 180).hide(0).animate({'opacity':'1','top':'0px'}, 180);
		$('#'+$(this).attr("data-zone")).animate({'opacity':'0','top':'-20px'}, 100).show(0).animate({'opacity':'1','top':'0px'}, 180);
	});
		
	$('.cube span').each(function() {
		if($(this).html() != "/") {
			$(this).html(0);
			var element = $(this).parent().attr('id');
			var e = parseInt(element[4]);
			
			var i = 0;
				setInterval(function(){
					if($("#cube"+e+" span").html() < parseInt($("#cube"+e+" span").attr("data-value"))) {
						$("#cube"+e+" span").html(i)
						i++;
					}
				}, 45);
		}
	});
	
	/*POPUP SUPRESSION DU COMPTE*/
	
	$("#supprcompte").click(function() {
		$('#popupsupprcompte, .popupbg').css("display","block");
		$('.popupbg').animate({'opacity':'0.8'},200);
		$('#popupsupprcompte').animate({'opacity':'1'},200);
	});
	
	/*POPUP SUPPRESSION DES SCORES*/
	
	$("#supprscores").click(function() {
		$('#popupsupprscores, .popupbg').css("display","block");
		$('.popupbg').animate({'opacity':'0.8'},200);
		$('#popupsupprscores').animate({'opacity':'1'},200);
	});
	
	/*FERMETURE DES POPUPS*/
	
	$(".popupbg, .non").click(function() {
		$('.popupbg, .popuphidden').animate({'opacity':'0'},200).delay(600).queue(function(n) {
			$('.popupbg').css("display","none");
			$('.popuphidden').css("display","none");
			n();
		});
	});
	
	$("#mycontactform").submit(function(e){
		e.preventDefault();
		$.post("/pages/controleurs/controleurContact.php", $("#mycontactform").serialize(),  function(data) {   });
		$('#success').html('Message envoyé !'); 
	});
});

function animChiffre(total1) {
	var h = document.getElementById("chiffre1").innerHTML;
	if ( h<total1 ) {
		h++; 
		document.getElementById("chiffre1").innerHTML = h;
      }
}  