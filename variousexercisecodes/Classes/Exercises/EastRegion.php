<?php
namespace Classes\Exercises;

class EastRegion extends IAbstract
{
	//Must return decimal value
	protected function giveCost()
	{
		$solarSavings = 2;
		$this->valueNow = 810.54/$solarSavings;
		return $this->valueNow;
	}
	
	//Must return string value
	protected function giveCity()
	{
		return "Somewhere in the east";
	}
}
