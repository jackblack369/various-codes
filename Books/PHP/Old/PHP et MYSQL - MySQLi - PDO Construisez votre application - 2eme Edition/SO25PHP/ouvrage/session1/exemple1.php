<?php
session_start();		// initialisation

if (isset ($_SESSION['editeur']))		// si la session n'est vide
{

	echo ' Test 1 : Editeur est : ';
	echo $_SESSION['editeur']."<br>";

} else {					// si la session est vide

	echo 'Test 1 : Aucun editeur n\'est trouvé<br>';	

	$_SESSION['editeur']="ENI";	// on rempli la session

	if (isset ($_SESSION['editeur']))	// test si la session est rempli
	{
		echo ' Test 2 : Editeur est : ';
		echo $_SESSION['editeur']."<br>";

	} 
	else 					// sinon 
	{
		echo 'Test 2 : Aucun editeur n\'est trouvé <br>';
	}

}

session_destroy();			// on détruit la session
unset($_SESSION);		// libere la session
if (isset ($_SESSION['editeur'])) echo $_SESSION['editeur']; 		// verification de la session

?>