<?php
/* Develop by juan huertas */

class DB
	{
	
		private 
			$_pdo, 
			$_query, 
			$_error = false, 
			$_results, 
			$_count = 0;
		
		
		public function Connect(){
			 
			try{
				
				  $this->_pdo = new PDO('mysql:host='.Config::get('mysql/host') . 
										  ';dbname='.Config::get('mysql/db'),
										   Config::get('mysql/username'),
										   Config::get('mysql/password'));
								
				//echo "connected";
				
				return $this->_pdo;
			}catch(PDOException $e){
				die($e->getMessage());
			} 
		} // End construct
		
		
		
	}	 // End class Connection
	
	