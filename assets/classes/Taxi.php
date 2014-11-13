<?php
require_once(dirname(__FILE__)).'/../core/init.php';
	

class Taxi
{
	
	
		private $_db;
		
		public
		$TaxiID,
		$CarModel,
		$CabNumber,
		$Latitude,
		$Longitude;
		
			
		public function __construct(){
			$this->_db = new DB();
			$this->_db = $this->_db ->Connect();
		}
		
			
		
		
	public function Insert_Taxi($CarModel,$CabNumber,$Latitude,$Longitude)
		{
			
			$query_checkCabNumber= $this->_db->prepare("SELECT * FROM TAXI WHERE CabNumber= ?");
			$query_checkCabNumber->bindParam(1,$CabNumber);
			$query_checkCabNumber->execute();
			
			$resultCheckTaxi = $query_checkCabNumber->rowCount();
			
			if($resultCheckTaxi == 1){
				return 2;  // 2 is false ----  CabNumber already exist on the database
				
			}else if($resultCheckTaxi != 1){
				
				
				$query_Insert = $this->_db->prepare("INSERT INTO TAXI(CarModel,CabNumber,Latitude,Longitude) VALUES(?,?,?,?)");
				$query_Insert->bindParam(1, $CarModel);
				$query_Insert->bindParam(2, $CabNumber);
				$query_Insert->bindParam(3, $Latitude);
				$query_Insert->bindParam(4, $Longitude);
				$query_Insert->execute();
				
				if($query_Insert->rowCount() == 0){
					return 0;   //  0 error inserting new User 
				}else{
					return 1;  // 1 is because everything work properly.
				}
	
			} // End else condition
		} // End function insert User
		
		
		
		
				
	public function Update_Taxi($TaxiID,$CarModel,$CabNumber){
			
				$query_Update = $this->_db->prepare("UPDATE TAXI SET CarModel=?,CabNumber=? WHERE TaxiID = ?");
				$query_Update->bindParam(1, $CarModel);
				$query_Update->bindParam(2, $CabNumber);
				$query_Update->bindParam(3, $TaxiID);
				$query_Update->execute();
				
				if($query_Update->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
				}else if($query_Update->rowCount() != 1){
					
					return 2; // return 2 because was not update any entity, because was nothing to modify
						
				}else{
					return 0;  // return 1 because it is false
					}
			
	 } // end function Update_User;
		
		
		public function Update_Taxi_Location($TaxiID,$Latitude,$Longitude){
			
				$query_Update = $this->_db->prepare("UPDATE TAXI SET Latitude=?,Longitude=? WHERE TaxiID = ?");
				$query_Update->bindParam(1, $Latitude);
				$query_Update->bindParam(2, $Longitude);
				$query_Update->bindParam(3, $TaxiID);
				$query_Update->execute();
				
				if($query_Update->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
				}else if($query_Update->rowCount() != 1){
					
					return 2; // return 2 because was not update any entity, because was nothing to modify
						
				}else{
					return 0;  // return 1 because it is false
					}
			
	 } // end function Update_User;
	
		
		
		public function Delete_User($TaxiID)
		{
			$query_Delete = $this->_db->prepare("DELETE QUICK FROM TAXI WHERE TaxiID = ? ");
			$query_Delete->bindParam(1, $TaxiID);
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

