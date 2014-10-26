package com.huertasApp.citytaxi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;



public class UpdateGpsUser{
	
	private static final String URL = "http://web-huertas.com/work/programs/CityTaxi_Server/UpdateGPS.php";
	private double Latitude;
	private double Longitud;

	//// Initialize calss MainActivity
	MainActivity main = new MainActivity();
	
	//// Initialize Session Manager Class
    SessionManager session;

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
	
	
	
	
	 protected void UpdateGPS(final String Email){
	       	
		 
		 	Thread t = new Thread() {

	            public void run() {
	            
	            	while(true){
	            		
	            		try{
			            	Thread.sleep(10000);
			               
			                HttpClient client = new DefaultHttpClient();
			                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
			                HttpResponse response;
			                JSONObject json = new JSONObject();
		                
	           
			                try {
			                	
			                    HttpPost post = new HttpPost(URL);
			                    json.put("Lat", getLongitud());
			                    json.put("Lon", getLatitude());
			                    json.put("UserEmail", Email);
			                   
			           
			                    StringEntity se = new StringEntity( json.toString() );  
			                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			                    post.setEntity(se);
			                    response = client.execute(post);
			                    	
			                    Log.e("Data to update",json.toString());
			                    
			                    /*Checking response */
			                    if(response!=null){
			                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
			   
			                        // Convert data InputStream into String
			                        String result = slurp(in);
			                        
			                        // Print on LogCat to debugin purpose
			                       // Log.e("Personal message",result);
			                        
			                        // Conver String to Json
			                      
			                        JSONObject reader = new JSONObject(result);
			                        
			                        respond(reader);
			               
			                    }
		
			                } catch(Exception e) {
			                    e.printStackTrace();
			                 // createDialog("Error", "Cannot Estabilish Connection");
			                }
			              
			                
			                
			                
	            		} catch(Exception e) {
		                    e.printStackTrace();
		                 // createDialog("Error", "Cannot Estabilish Connection");
		                }
	            		
	            	} // End while loop
	               
	            }
	        };

	        t.start();
			

	    }
	 
	 
	 public static String slurp(final InputStream is) throws IOException
	    {
	    	 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    	    StringBuilder out = new StringBuilder();
	    	    String newLine = System.getProperty("line.separator");
	    	    String line;
	    	    while ((line = reader.readLine()) != null) {
	    	        out.append(line);
	    	        out.append(newLine);
	    	    }
	    	    return out.toString();
	    }
	 
	 



		//// This will execute the result from server
		
		 public void respond(JSONObject CallBackResult) throws JSONException{
				
			 
			 
			
			 
		   JSONObject StatusRequest  = CallBackResult.getJSONObject("Result");
           int Status = Integer.parseInt(StatusRequest.getString("Status"));
           
           JSONObject TaxiDrivers = StatusRequest.getJSONObject("TaxiDrivers");
           String UserID = TaxiDrivers.getString("UserID");
           String Username = TaxiDrivers.getString("Username");
           String Email = TaxiDrivers.getString("Email");
           String Phone = TaxiDrivers.getString("Phone");
           String Latitude = TaxiDrivers.getString("Latitude");
           String Longitude = TaxiDrivers.getString("Longitude");
           String TaxiID = TaxiDrivers.getString("TaxiID");
           String CarModel = TaxiDrivers.getString("CarModel");
           String CabNumber = TaxiDrivers.getString("CabNumber");
         
           Log.e("Respond",Integer.toString(Status));
           Log.e("UserID",UserID);
           Log.e("Username",Username);
           Log.e("Email",Email);
           Log.e("Phone",Phone);
           Log.e("Latitude",Latitude);
           Log.e("Longitude",Longitude);
           Log.e("TaxiID",TaxiID);
           Log.e("CarModel",CarModel);
           Log.e("CabNumber",CabNumber);
     
   
		} // End method respond
	 
	 
	 
	 
}
	
	