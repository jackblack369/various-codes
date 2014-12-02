<?php 

class CnxBDD 
{ 

 var $host;
 var $login;
 var $password;
 var $base;
	

public function __construct($serveur='$serveur',$user='$user', $password='$password', $bdd='$bdd')
{
 $this->host=$serveur;
 $this->login=$user;
 $this->password=$password;
 $this->base=$bdd;
}

function connect() 
{ 
$connect = mysqli_connect ($this->host, $this->login, $this->password,$this->base); 

if (mysqli_connect_errno()) 
    die ("Echec de la connexion : ". mysqli_connect_error());
 
$this->connect = $connect; 
} 


function requete ($sql) 
{ 
$result = mysqli_query($this->connect,$sql); 
if(!$result) 
	die ("Probleme requete : ".$sql); 

while ($Row = mysqli_fetch_object( $result))
{
	$Rows[] = $Row;
}

return $Rows;

} 

function deconnect() 
{
	mysqli_close($this->connect); 
} 

} 
?>
