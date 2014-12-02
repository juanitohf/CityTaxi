<?php
header('Content-Type: application/json');

require_once(dirname(__FILE__)).'/assets/core/init.php';

	// get content from url post
	$foo = file_get_contents("php://input");
	// Decode json result into array
	$json = json_decode($foo, true);
	
	
	// Extract values that you need from array.
	$Lat 		= $json['Lat'];
	$Lon 		= $json['Lon'];
	$UserEmail = $json['UserEmail'];
	
	

	// Result array will store all information to later print like json
	$results = array();
	 

	$Update_User = new User();
	$Result_Update_Location = $Update_User->Update_User_Location_by_Email($UserEmail,$Lat,$Lon);
			 
		if($Result_Update_Location == 0){
			
			
			
			$results['Result']['Status'] = 0; // Error  -- Not inserted user //
			print json_encode($results);
			
		}else if($Result_Update_Location == 1){
			
			$ResultTaxiDrivers = $Update_User->get_Taxi_drivers();
			$results['Result']['TaxiDrivers'] = $ResultTaxiDrivers;
			$results['Result']['Status']  	 = 1; // success  -- Everything is correct //
			print json_encode($results);
			
			$Result_Login_User = null;
			
		}else{
			$ResultTaxiDrivers = $Update_User->get_Taxi_drivers();
			$results['Result']['TaxiDrivers'] = $ResultTaxiDrivers;
			$results['Result']['Status'] = 2; //// because it was not update, no modifications on the position
			print json_encode($results);
				
		}	
		
		
		
		
		
		
		