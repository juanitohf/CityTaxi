<?php 

/* Develop by juan huertas */

/* This class is to colect all method from my folder classes */


	function __autoload($classname)
	{
	
		include("classes/".$classname.".php");
				
	}
	
	
