<?php
header('Content-Type: application/json');

/* This is develop by Juan Huertas */


require_once(dirname(__FILE__)).'/assets/core/init.php';

	// get content from url post
	$foo = file_get_contents("php://input");
	// Decode json result into array
	$json = json_decode($foo, true);
	
	
	// Extract values that you need from array.
	$User_email = $json['User_email'];
	$User_pass = $json['User_pass'];
	
	
		
	// Result array will store all information to later print like json
	$results = array();
	 
	$Login_User = new User();
	$Result_Login_User = $Login_User->Login($User_email,$User_pass);
			 
		if($Result_Login_User == 0){
			
			$results['Result']['Status'] = 0; // Error  -- Not inserted user //
			print json_encode($results);
			
		}else if($Result_Login_User == 1){
			
			$Login_User->get_User_Info_by_Email($User_email);
			$results['User']['User_Id'] 			= $Login_User->User_Id;
			$results['User']['User_name'] 		= $Login_User->User_name;
			$results['User']['User_email'] 		= $Login_User->User_email;
			$results['User']['Effective_Date'] 	= $Login_User->Effective_Date;
			$results['User']['Lat'] 				= $Login_User->Lat;
			$results['User']['Lon'] 				= $Login_User->Lon;
			$results['User']['Role_Id'] 			= $Login_User->Role_Id;
			
		
			
			$results['Result']['Status']  = 1; // success  -- Everything is correct //
			print json_encode($results);
			$Result_Login_User = null;
			
		}else{
			
			$results['Result']['Status'] = 2; //// Unknow Error  -- N//
			print json_encode($results);
				
		}	
		
		
		
		
		