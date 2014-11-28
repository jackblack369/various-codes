<?php
require_once 'AutoLoader.php';
error_reporting(E_ALL);
ini_set('display_errors',1);

$mgr = new Manager("Smith", 300, 20);
$mgr->give_raise(50);
$emp = new Employee("Johnson", 100);
$emp->give_raise(50);

