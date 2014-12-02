<?php
session_start();


/* Develop by juan huertas */

		
$GLOBALS['config'] = array(
	'mysql'	=> array(
			'host' 				=> 'localhost',
			'username' 		=> 'juanhf',
			'password' 		=> 'Jhf#5479',
			'db' 				=> 'juanhf_City_Taxi'
	),
	
	'remember'	=> array(
			'cookie_name' 		=> 'hash',
			'cookie_expiry' 	=> 604800
	),
	
	'session'	=> array(
			'session_name' 	=> 'user',
			'token_name' 		=> 'token'
	)
	
);




spl_autoload_register(function($class){
	require_once(dirname(__FILE__) ."/../classes/". $class . '.php');
});

