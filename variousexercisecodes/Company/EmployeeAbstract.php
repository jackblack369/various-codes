<?php
abstract class EmployeeAbstract
{
    protected $ename;
    protected $sal;
    
    function __construct($ename, $sal = 100) 
    {

    }

    abstract function give_raise($amount);

    
    function __destruct() 
    {

    }
    
}