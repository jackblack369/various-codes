<?php
class Manager implements IEmployee
{
    protected $dept;
    
    function __construct($ename, $sal, $dept) 
    {
        $this->ename = $ename;
        $this->sal = $sal;
        $this->dept = $dept;
    }
    
    function give_raise($amount) 
    {
        $this->sal+= $amount;
        printf("Employee %s got raise of %d dollars\n <br>", $this->ename, $amount);
        printf("New salary is %d dollars\n <br>", $this->sal);
        print "This employee is a manager <br>";
    }
    
    function __destruct() 
    {
        printf("Good bye, cruel world: MANAGER:%s\n <br>", $this->ename);
    }
}