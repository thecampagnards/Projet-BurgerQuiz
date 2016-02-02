<?php
// Here we get all the information from the fields sent over by the form.
$pseudo = $_POST['pseudo'];
$titre = $_POST['titre'];
$message = $_POST['message'];
 
$to = 'konstantin.sidorenko@orange.fr';
$subject = 'Contact Burger Quiz';
$message = 'DE: '.$name.' Titre: '.$titre.'Message: '.$message;
$headers = 'De: BurgerQuiz';
 
if (filter_var($email, FILTER_VALIDATE_EMAIL)) { // this line checks that we have a valid email address
mail($to, $subject, $message, $headers); //This method sends the mail.
echo "Votre message a été envoyé !"; // success message
}else{
echo "Il y a eu un probleme lors de l'envoi de votre message !'";
}
?>