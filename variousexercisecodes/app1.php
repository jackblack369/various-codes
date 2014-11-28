<?php
use App\Lib1 as L;
use App\Lib2\MyClass as Obj;
error_reporting(E_ALL);
ini_set('display_errors',1);
header('Content-type: text/plain');
require_once('lib1.php');
require_once('lib2.php');

echo L\MYCONST . "\n";
echo L\MyFunction() . "\n";
echo L\MyClass::WhoAmI() . "\n";
echo Obj::WhoAmI() . "\n";

$paths = explode(DIRECTORY_SEPARATOR, __FILE__);
$file = 'lib1'.'.php';

try{
    
    foreach ($paths as $path) {
        $combined = '.' . $path . DIRECTORY_SEPARATOR . $file;

        if (file_exists($combined)) {
            //include($combined);
            echo $combined;
            return;
        }
    }

    throw new Exception("class not found");

}
catch (Exception $e) {
    echo $e->getMessage();
     
}

$counts = array(1,2,6,9,7,12);

try{
    
    foreach ($counts as $count) {

        if ($count == 6) {
            //include($combined);
            echo 'ok';
            return;
        }
        echo $count;
    }

    throw new Exception("pas ok");

}
catch (Exception $e) {
    echo $e->getMessage();
     
}