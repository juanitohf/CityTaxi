<?php
require_once(dirname(__FILE__)).'/../core/init.php';
	

class User
{
	
	
		private $_db;
		
		public
		$UserID,
		$Username,
		$Email,
		$Password,
		$Phone,
		$EffeciveDate,
		$Latitude,
		$Longitude,
		$RoleID;
		
			
		public function __construct(){
			$this->_db = new DB();
			$this->_db = $this->_db ->Connect();
		}
		
			
		
		
	public function Insert_User($Username,$Email,$Password,$Phone)
		{
			$Password = md5($Password);
			$RoleID = 1;   // Role Id is equal to 1 because it is client
			$EffeciveDate = date('Y-m-d');
			
			$query_checkEmail = $this->_db->prepare("SELECT * FROM USER WHERE Email = ?");
			$query_checkEmail->bindParam(1,$Email);
			$query_checkEmail->execute();
			
			$resultCheckMail = $query_checkEmail->rowCount();
			
			if($resultCheckMail == 1){
				return 2;  // 2 is false ----  Email already exist on the database
				
			}else if($resultCheckMail != 1){
				
				
				$query_Insert = $this->_db->prepare("INSERT INTO USER 
														(Username,Email,Password,Phone,EffeciveDate,RoleID) 
														VALUES(?,?,?,?,?,?)");
				$query_Insert->bindParam(1, $Username);
				$query_Insert->bindParam(2, $Email);
				$query_Insert->bindParam(3, $Password);
				$query_Insert->bindParam(4, $Phone);		
				$query_Insert->bindParam(5, $EffeciveDate);		
				$query_Insert->bindParam(6, $RoleID);
				$query_Insert->execute();
				
				if($query_Insert->rowCount() == 0){
					return 0;   //  0 error inserting new User 
				}else{
					return 1;  // 1 is because everything work properly.
				}
	
				
				
			} // End else condition
		} // End function insert User
		
		
		
		
				
	public function Update_User($UserID,$Username,$Email,$Phone,$RoleID){
			
		$query_Update = $this->_db->prepare("UPDATE USER SET 
												Username=?,Email=?,Phone=?,RoleID=? 
												WHERE UserID = ?");
										
				$query_Update->bindParam(1, $Username);
				$query_Update->bindParam(2, $Email);
				$query_Update->bindParam(3, $Phone);
				$query_Update->bindParam(4, $RoleID);
				$query_Update->bindParam(5, $UserID);
				$query_Update->execute();
				
				if($query_Update->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
				}else if($query_Update->rowCount() != 1){
					
					return 2; // return 2 because was not update any entity, because was nothing to modify
						
				}else{
					return 0;  // return 1 because it is false
					}
			
	 } // end function Update_User;
		
		
		
	public function Update_User_Location($UserID,$Latitude,$Longitude){
			
				$query_Update = $this->_db->prepare("UPDATE USER SET Latitude=?,Longitude=? WHERE UserID = ?");
				$query_Update->bindParam(1, $Latitude);
				$query_Update->bindParam(2, $Longitude);
				$query_Update->bindParam(3, $UserID);
				$query_Update->execute();
				
				if($query_Update->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
				}else if($query_Update->rowCount() != 1){
					
					return 2; // return 2 because was not update any entity, because was nothing to modify
						
				}else{
					return 0;  // return 1 because it is false
					}
			
	 } // end function Update_User;
	
	
	public function Update_User_Location_by_Email($Email,$Latitude,$Longitude){
			
				$query_Update = $this->_db->prepare("UPDATE USER SET Latitude=?,Longitude=? WHERE Email = ?");
				$query_Update->bindParam(1, $Latitude);
				$query_Update->bindParam(2, $Longitude);
				$query_Update->bindParam(3, $Email);
				$query_Update->execute();
				
				if($query_Update->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
				}else if($query_Update->rowCount() != 1){
					
					return 2; // return 2 because was not update any entity, because was nothing to modify
						
				}else{
					return 0;  // return 1 because it is false
					}
			
	 } // end function Update_User;
			
		
		
		
		public function Login($Email,$Password)
		{
				
			   $Password = md5($Password);
			
				$query_Login = $this->_db->prepare("SELECT * FROM USER WHERE Email = ? AND Password = ?");
				$query_Login->bindParam(1, $Email);
				$query_Login->bindParam(2, $Password);
				$query_Login->execute();
				
					
					if($query_Login->rowCount() == 1)
						{
							return 1;  // return 1 because it is true
						}else
							{
							return 0;  // return 1 because it is false
								
							}
	
		
		} // this is the end of my Login($Email,$Password) funcoon
		
		
		
		public function Delete_User($UserID)
		{
			$query_Delete = $this->_db->prepare("DELETE QUICK FROM USER WHERE UserID = ? ");
			$query_Delete->bindParam(1, $UserID);
			$query_Delete->execute();
			
			if($query_Delete->rowCount() == 1){	
			
					return 1;  // return 1 because it is true
					
			}else if($query_Delete->rowCount() != 1){
				
					return 2; // return 2 because was not deleted any entity, because was not any entity with this id.
					
			}else{
					return 0;  // return 1 because it is false
				}
			
		} // end Delete_User($User_Id)
		
		
		
			public function get_User_Info_by_Email($Email)
			{
				
				$query_getUser = $this->_db->prepare("SELECT * FROM USER WHERE Email = ?");
				$query_getUser->bindParam(1, $Email);
				$query_getUser->execute();
				$result = $query_getUser->fetchAll(PDO::FETCH_ASSOC);
				@$result = $result[0];
				
		
				$this->User_Id 		= $result['UserID'];
				$this->User_name		= $result['Username'];
				$this->User_email		= $result['Email'];
				$this->Phone			= $result['Phone'];
				$this->Effective_Date	= $result['EffeciveDate'];
				$this->Lat				= $result['Latitude'];
				$this->Lon				= $result['Longitude'];
				$this->Role_Id			= $result['RoleID'];
				
			
			} // END function get_User_Info_by_Email($Email)
			
			
			
			
			public function get_Taxi_drivers(){
				
				$RoleID = 2; // 2 because the taxi drivers have the role 2
				$query_getUser = $this->_db->prepare("SELECT * FROM USER,TAXI WHERE USER.UserID = TAXI.UserID AND RoleID = ?");
				$query_getUser->bindParam(1,$RoleID);
				$query_getUser->execute();
				$Taxi_Driver = array();  /// Array to store the return result
					
					$i = 0;
				if($query_getUser->rowCount() != 0){
				
							while($Result = $query_getUser->fetch(PDO::FETCH_ASSOC)){
							
								
								
								$Taxi_Driver[$i]['UserID']		= $Result['UserID'];
								$Taxi_Driver[$i]['Username']		= $Result['Username'];
								$Taxi_Driver[$i]['Email']			= $Result['Email'];
								$Taxi_Driver[$i]['Phone']			= $Result['Phone'];
								$Taxi_Driver[$i]['EffeciveDate']	= $Result['EffeciveDate'];
								$Taxi_Driver[$i]['Latitude']		= $Result['Latitude'];
								$Taxi_Driver[$i]['Longitude']		= $Result['Longitude'];
								$Taxi_Driver[$i]['RoleID']		= $Result['RoleID'];
								$Taxi_Driver[$i]['TaxiID']		= $Result['TaxiID'];
								$Taxi_Driver[$i]['CarModel']		= $Result['CarModel'];
								$Taxi_Driver[$i]['CabNumber']		= $Result['CabNumber'];
								
								$i++;
						
							}
							
							return $Taxi_Driver;
						
				}else{
							return 0;
					}
						
			} // End get_Taxi_drivers
		
		
		
		
		
		
} // End of my Users Class

/*
$getTaxi = new User();
$result = $getTaxi->get_Taxi_drivers();
$resultJson = json_encode($result );
echo $resultJson;
*/