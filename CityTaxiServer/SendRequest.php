<?php
header('Content-Type: application/json');

/* Made by Tal Eidenzon  */

require_once(dirname(__FILE__)).'/assets/core/init.php';

	// get content from url post
	$foo = file_get_contents("php://input");
	// Decode json result into array
	$json = json_decode($foo, true);
	
	
	// Extract values that you need from array.
	$Lat 		= $json['Lat'];
	$Lon 		= $json['Lon'];
	$UserEmail 	= $json['UserEmail'];
	
	

	

	// Result array will store all information to later print like json
	$results = array();
	 

	$Send_Message = new RequestTaxi();
	$Result_Send_Message = $Send_Message->Request_Taxi($UserEmail,$Lat,$Lon);
			 
		if($Result_Send_Message != 0){
			
	
			$results['Result']['Status'] = 1; // success  -- Everything is correct //
			print json_encode($results);
			$Send_Message = null;
			
		}else{
			
			
			$results['Result']['Status'] = 0; // Error  -- Not inserted user //
			print json_encode($results);
			$Send_Message = null;
			
		}
		
		
		
		
		
		
		