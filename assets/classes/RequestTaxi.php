<?php
require_once(dirname(__FILE__)).'/../core/init.php';


class RequestTaxi
{
	
	
		private $_db;
		
		public
		$USER_requests_TAXI_ID,
		$UserID,
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
		
			
		
		
	public function Insert_Request_Taxi($UserID,$TaxiID,$DateOfRequest,$TimeOfRequest,
										  $LocationOfCall_Lat,$LocationOfCall_Lon,$RequestStatusID){
			
			
				$query_Insert = $this->_db->prepare("INSERT INTO USER_requests_TAXI
														(UserID,TaxiID,DateOfRequest,TimeOfRequest,
														LocationOfCall_Lat,LocationOfCall_Lon,RequestStatusID) 
														VALUES(?,?,?,?,?,?,?)");
				$query_Insert->bindParam(1, $UserID);
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
		
		
		
} // End of my Users Class
