<?php
require_once 'AutoLoader.php';
use App\Lib1\MyClass as MC;

error_reporting(E_ALL);
ini_set('display_errors',1);

$obj = new MC();
echo $obj->WhoAmI();

?>