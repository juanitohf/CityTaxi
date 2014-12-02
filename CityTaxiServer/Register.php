<?php
header('Content-Type: application/json');

/* This is develop by Juan Huertas */
require_once(dirname(__FILE__)).'/assets/core/init.php';

	// get content from url post
	$foo = file_get_contents("php://input");
	// Decode json result into array
	$json = json_decode($foo, true);
	
	
	// Extract values that you need from array.
	$User_name = $json['User_name'];
	$User_email = $json['User_email'];
	$User_pass = $json['User_pass'];
	$User_phone = $json['User_phone'];
	
		
	// Result array will store all information to later print like json
		$results = array();
		$Insert_User = new User();
		$Result_Isert_User = $Insert_User->Insert_User($User_name,$User_email,$User_pass,$User_phone);
			
		if($Result_Isert_User == 0){
			
			$results['Result']['Status'] = 0; // Error  -- Not inserted user //
			print json_encode($results);
			
		}else if($Result_Isert_User == 1){
			
			$results['Result']['Status']  = 1; // success  -- Everything is correct //
			print json_encode($results);
			
		}else if($Result_Isert_User == 2){
			
			$results['Result']['Status']  = 2; // Repeated user on database //
			print json_encode($results);
		
		}else{
			
			$results['Result']['Status'] = 3; //// Unknow Error  -- N//
			print json_encode($results);
				
		}	
		