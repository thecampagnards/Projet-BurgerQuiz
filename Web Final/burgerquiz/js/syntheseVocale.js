
$(document).ready(function() {

	meSpeak.loadConfig("../../js/mespeak/mespeak_config.json");
	meSpeak.loadVoice("../../js/mespeak/voices/fr.json");
	
	var texte = $('#actuel h3').text();
	meSpeak.speak(texte);

});