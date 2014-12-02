<?php
$NotreChoix="Mademoiselle";

$genre=array ('Monsieur','Madame','Mademoiselle');
if (in_array ($NotreChoix,$genre))
{
	echo "Notre choix est bien dans notre tableau";
}
else
{
	echo "Champ non trouvé";
}
?>