<?php
require_once 'AutoLoader.php';
use Classes\Exercises\NorthRegion;
use Classes\Exercises\WestRegion;
use Classes\Exercises\SouthRegion;
use Classes\Exercises\EastRegion;

class Client
{
	public function __construct()
	{
            $north = new NorthRegion();
            $west = new WestRegion();
            $south = new SouthRegion(); 
            $east = new EastRegion();
            $this->showInterface($north);
            $this->showIdentities($north);
            $this->showInterface($west);
            $this->showIdentities($west);
            $this->showInterface($south);
            $this->showIdentities($south);
            $this->showInterface($east);
            $this->showIdentities($east);
	}
	
	private function showInterface(Classes\Exercises\IAbstract $region)
	{
            echo $region->displayShow() . "<br/>";
	}
        
        private function showIdentities(Classes\Exercises\IAbstract $region){
            echo $region->whoIam() . "<br/>";
        }
}

$worker=new Client();
