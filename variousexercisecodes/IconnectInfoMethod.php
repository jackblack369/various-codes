<?php
include_once('IConnectInfo.php');
ini_set('display_errors',1);
error_reporting(E_ALL);

class ConSQL implements IConnectInfo
{
	//Passing values using scope resolution operator
	private $server= IConnectInfo::HOST;
	private $currentDB= IConnectInfo::DBNAME;
	private $user= IConnectInfo::UNAME;
	private $pass= IConnectInfo::PW;
	
	public function testConnection()
	{
		$hookup=new mysqli($this->server, $this->user, $this->pass,$this->currentDB);
		
		if (mysqli_connect_error()) {
			die('bad mojo');
		}
		
		print "You're hooked up Ace! <br />" . $hookup->host_info;
		$hookup->close();
	}
}

$useConstant = new ConSQL();
$useConstant->testConnection();
?>

