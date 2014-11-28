<?php
spl_autoload_register(array('AutoLoader','myAutoloadBasic'));

class AutoLoader
{
//    // autoload function
//    static function myAutoload($class) 
//    {
//        //convert namespace to full file path
//        $class = 'Classes/Exercises/' . str_replace('\\', '/', $class) . '.php';
//        require_once($class);
//    }

    static function myAutoloadBasic($class) 
    {
        // convert namespace to full file path
        $class = str_replace('\\', '/', $class) . '.php';
        require_once($class);
    }
    
    
}



//function __autoload($class){
//    $class = 'classes/' . str_replace('\\', '/', $class) . '.php';
//    require_once($class);
//}
