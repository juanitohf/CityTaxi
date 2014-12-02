<?php
/* Develop by juan huertas */
class Config{
	
	public static function get($path = null){
		
		if($path){
			$config = $GLOBALS['config'];
			$path = explode('/', $path);
			
			foreach($path as $bit){
				if(isset($config[$bit])){
					$config = $config[$bit];
				}
				
			} //End foreach	
			
			return $config;
		}
		
		return false;
		
		
	} // Emd get function
	
	
}// End class Config

