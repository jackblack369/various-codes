<?php
class Employee implements IEmployee
{
    protected $ename;
    protected $sal;
    
    function __construct($ename, $sal = 100, $dept = NULL) 
    {
        $this->ename = $ename;
        $this->sal = $sal;
    }

    function give_raise($amount) 
    {
        $this->sal+= $amount;
        printf("Employee %s got raise of %d dollars\n <br>", $this->ename, $amount);
        printf("New salary is %d dollars\n <br>", $this->sal);
    }
            
    function __destruct() 
    {
        printf("Good bye, cruel world: EMPLOYEE:%s\n <br>", $this->ename);
    }
}