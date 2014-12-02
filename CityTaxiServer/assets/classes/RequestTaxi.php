<?php
require_once(dirname(__FILE__)).'/../core/init.php';
date_default_timezone_set('America/New_York');

/* This is develop by Dana Shawareb */
class RequestTaxi
{
	
	
		private $_db;
		
		public
		$USER_requests_TAXI_ID,
		$UserEmail,
		$TaxiID,
		$DateOfRequest,
		$TimeOfRequest,
		$LocationOfCall_Lat,
		$LocationOfCall_Lon,
		$RequestStatusID;

		
			
		public function __construct(){
			$this->_db = new DB();
			$this->_db = $this->_db ->Connect();
		}
		
			
		
		
	public function Insert_Request_Taxi($UserEmail,$TaxiID,$DateOfRequest,$TimeOfRequest,
										  $LocationOfCall_Lat,$LocationOfCall_Lon,$RequestStatusID){
			
			
				$query_Insert = $this->_db->prepare("INSERT INTO USER_requests_TAXI
														(UserEmail,TaxiID,DateOfRequest,TimeOfRequest,
														LocationOfCall_Lat,LocationOfCall_Lon,RequestStatusID) 
														VALUES(?,?,?,?,?,?,?)");
				$query_Insert->bindParam(1, $UserEmail);
				$query_Insert->bindParam(2, $TaxiID);
				$query_Insert->bindParam(3, $DateOfRequest);
				$query_Insert->bindParam(4, $TimeOfRequest);
				$query_Insert->bindParam(5, $LocationOfCall_Lat);
				$query_Insert->bindParam(6, $LocationOfCall_Lon);
				$query_Insert->bindParam(7, $RequestStatusID);
				$query_Insert->execute();
				
				if($query_Insert->rowCount() == 0){
					return 0;   //  0 error inserting new User 
				}else{
					return 1;  // 1 is because everything work properly.
				}
	
		
		} // End function insert User
		
		
		
		
			public function Request_Taxi($UserEmail,$LocationOfCall_Lat,$LocationOfCall_Lon){
						
						$DateOfRequest = date('Y-m-d');
						$TimeOfRequest = date('H:i:s');
						$RequestStatusID = 1;
			
				$query_Insert = $this->_db->prepare("INSERT INTO USER_requests_TAXI
														(UserEmail,DateOfRequest,TimeOfRequest,
														LocationOfCall_Lat,LocationOfCall_Lon,RequestStatusID) 
														VALUES(?,?,?,?,?,?)");
				$query_Insert->bindParam(1, $UserEmail);
				$query_Insert->bindParam(2, $DateOfRequest);
				$query_Insert->bindParam(3, $TimeOfRequest);
				$query_Insert->bindParam(4, $LocationOfCall_Lat);
				$query_Insert->bindParam(5, $LocationOfCall_Lon);
				$query_Insert->bindParam(6, $RequestStatusID);
				$query_Insert->execute();
				
				if($query_Insert->rowCount() == 0){
					return 0;   //  0 error inserting new User 
				}else{
					return 1;  // 1 is because everything work properly.
				}
	
		
		} // End function insert User
		
		
		

		
	public function Update_Taxi_Request($USER_requests_TAXI_ID,$LocationOfCall_Lat,$LocationOfCall_Lon){
			
				$query_Update = $this->_db->prepare("UPDATE USER_requests_TAXI SET LocationOfCall_Lat=?,LocationOfCall_Lon=? 
														WHERE USER_requests_TAXI_ID = ?");
				$query_Update->bindParam(1, $LocationOfCall_Lat);
				$query_Update->bindParam(2, $LocationOfCall_Lon);
				$query_Update->bindParam(3, $USER_requests_TAXI_ID);
				$query_Update->execute();
				
				if($query_Update->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
				}else if($query_Update->rowCount() != 1){
					
					return 2; // return 2 because was not update any entity, because was nothing to modify
						
				}else{
					return 0;  // return 1 because it is false
					}
			
	 } // end function Update_User;
	
		
		
		public function Delete_RequestTaxi($USER_requests_TAXI_ID)
		{
			$query_Delete = $this->_db->prepare("DELETE QUICK USER_requests_TAXI WHERE USER_requests_TAXI_ID = ?");
			$query_Delete->bindParam(1, $USER_requests_TAXI_ID);
			$query_Delete->execute();
			
			if($query_Delete->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
			}else if($query_Delete->rowCount() != 1){
				
					return 2; // return 2 because was not deleted any entity, because was not any entity with this id.
					
			}else{
					return 0;  // return 1 because it is false
				}
			
		} // end Delete_User($User_Id)
		
		
		
		
			public function get_Taxi_Request(){
				
				
				$query_getRequest = $this->_db->prepare("SELECT * FROM USER_requests_TAXI");
				$query_getRequest->execute();
				$Taxi_Request = array();  /// Array to store the return result
					
					$i = 0;
				if($query_getRequest->rowCount() != 0){
				
							while($Result = $query_getRequest->fetch(PDO::FETCH_ASSOC)){
							
						
	
								$Taxi_Request[$i]['USER_requests_TAXI_ID']	= $Result['USER_requests_TAXI_ID'];
								$Taxi_Request[$i]['UserEmail']				= $Result['UserEmail'];
								$Taxi_Request[$i]['TaxiID']					= $Result['TaxiID'];
								$Taxi_Request[$i]['DateOfRequest']			= $Result['DateOfRequest'];
								$Taxi_Request[$i]['TimeOfRequest']			= $Result['TimeOfRequest'];
								$Taxi_Request[$i]['LocationOfCall_Lat']		= $Result['LocationOfCall_Lat'];
								$Taxi_Request[$i]['LocationOfCall_Lon']		= $Result['LocationOfCall_Lon'];
								$Taxi_Request[$i]['RequestStatusID']			= $Result['RequestStatusID'];
							
								$i++;
						
							}
							
							return $Taxi_Request;
						
				}else{
							return 0;
					}
						
			} // End get_Taxi_drivers
		
		
		
		
		
		
		
		
		
} // End of my Users Class

