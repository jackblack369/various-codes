<?php
/**
 *
 * @author christophe
 */
interface IEmployee {
    
    function __construct($ename, $sal = 100, $dept); 

    function give_raise($amount); 
    
    function __destruct() ;

}

