<?php
header('Content-Type: application/json');

/* This is develop by Dana Shawareb */

require_once(dirname(__FILE__)).'/assets/core/init.php';

	// get content from url post
	$foo = file_get_contents("php://input");
	// Decode json result into array
	$json = json_decode($foo, true);
	

	

	

	// Result array will store all information to later print like json
	$results = array();
	 

	$Request = new RequestTaxi();
	$Result_Request = $Request->get_Taxi_Request();
			 
		if($Result_Request != 0){
			
			$results = $Result_Request;
			$results['Result']['Status'] = 1; // success  -- Everything is correct //
			print json_encode($results);
			$Request = null;
			
		}else{
			
			
			$results['Result']['Status'] = 0; // Error  -- Not inserted user //
			print json_encode($results);
			$Send_Message = null;
			
		}
		
		
		
		
		
		
		