package com.huertasApp.citytaxi;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.content.Context;

import android.os.Looper;
import android.util.Log;



public class UpdateGpsUser{
	
	private static final String URL = "http://web-huertas.com/work/programs/Server4350/UpdateGPS.php";
	double Latitude;
	double Longitud;

	MainActivity getValuesGPS = new MainActivity();
	
	protected Context context;


	public double getLatitude() {
		return this.Latitude;
	}


	public void setLatitude(double latitude) {
		this.Latitude = latitude;
	}


	public double getLongitud() {
		return this.Longitud;
	}


	public void setLongitud(double longitud) {
		this.Longitud = longitud;
	}


	public static String getUrl() {
		return URL;
	}
	
	
	public void UpdateGPS(){
		
		Thread t = new Thread() {
	
			
			  public void run() {
				  Looper.prepare(); //For Preparing Message Pool for the child Thread
					try {
						Thread.sleep(1000);
						
						 while(true){
							  
							  
							
							 
							 Latitude = getValuesGPS.getLat();
							 Longitud = getValuesGPS.getLon();
							  
							  
						    //Looper.prepare(); //For Preparing Message Pool for the child Thread
			                HttpClient client = new DefaultHttpClient();
			                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
			                HttpResponse response;
			                JSONObject json = new JSONObject();
					  
			                // Print on LogCat to debugin purpose
			                Log.e("Latitud",Double.toString(getValuesGPS.getLat()));
			                Log.e("Longitud",Double.toString(getValuesGPS.getLon()));
					  
			                
			                
			                
			                Looper.loop(); //Loop in the message queue
					  
						  } // end while loop
						
					
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						  
				 
				  
				  
			  } // End run()

		
		}; // end Thread
		
		 t.start();
		
		
		
	} // end UpdateGPS method
	

	
	



	
	
}
	
	   
	    