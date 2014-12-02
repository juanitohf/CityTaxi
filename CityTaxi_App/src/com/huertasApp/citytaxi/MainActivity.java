/* Develop by juan huertas */

package com.huertasApp.citytaxi;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;




	public class MainActivity extends Activity  {
	 
	    
		// Google Map
	    GoogleMap googleMap;
	    Marker UserMarket, TaxiMarker;
	    private double Lat, Lon;
	    private String UserEmail;
	    private static final String URL = "http://web-huertas.com/work/programs/CityTaxi_Server/UpdateGPS.php";
	    private static final String URLREQUEST = "http://web-huertas.com/work/programs/CityTaxi_Server/SendRequest.php";
	    
	    
	    //// GLOBAL VARIABLES ////
	    private int intRole = 0;
	    
	     
	    
	    
	    // Session Manager Class
	    SessionManager session;
	 
	    
	    
	    /////  GETTER AND SETTER OF MY VARIABLES//////////
	    
	    public void setLat(double Lat){	
	    	this.Lat = Lat;
	    }
	    
	    public void setLon(double Lon){	
	    	this.Lon = Lon;
	    }
	    
	    public double getLat(){
	    	return this.Lat;
	    }
	    public double getLon(){
	    	return this.Lon;
	    }
	    
	  
	    
	    
	    
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        // Session Manager
	        session = new SessionManager(getApplicationContext());
	        //  Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
	 

	        
	        if(session.isLoggedIn() == false){
	        	Intent goLogin = new Intent(this, LoginActivity.class);
	    		startActivity(goLogin);
	    		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	        	
	        }else{
	        
			        try {
			        	
			        	GPSTracker gps = new GPSTracker(this);
			        	if(gps.canGetLocation()){ 
			        		
			        		
			        		/////////// GET INFORMATION OF USER WHO IS LOGED /////////////
			        		HashMap<String, String> User = session.getUserDetails();
			        		String UserName = User.get("name");
			        		String Role = User.get("role");
			        		this.intRole = Integer.parseInt(Role);
			        		this.UserEmail = User.get("email");
			        		
			        	
			        		this.Lat = gps.getLatitude(); // returns latitude
			        		this.Lon = gps.getLongitude(); // returns longitude
			        		
			        		
			        		/////////INITIALIZE THE MAP
			        		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				                    R.id.map)).getMap();
			        		
			        	
			        		
			        		LatLng CURRENT_LOCATION = new LatLng(this.Lat,this.Lon);
			        		//Get current position on map
			        		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 15);
			        		googleMap.animateCamera(update);
			        		
			        	
			        		//////////////// PRINT TO DEBUG //////////////////
			        	    Log.e("Mi name is:",User.toString());
			     
			        	    
			        	    ///////// DEPENDING WHAT IS THE ROLE IT CHANGE THE MARKERT IMG
			        	    
			        	    /////////// ROLE 1   --  USER
			        	    if(this.intRole == 1){
			        	    	
			        	    	googleMap.setMyLocationEnabled(true);
			        	    	UpdateGPS(this.UserEmail);
			        	    
			        	    /////////// ROLE 2   --  TAXI DRIVER
			        	    }else if(this.intRole == 2){
			        	    	
			        	    	this.TaxiMarker = this.googleMap.addMarker(new MarkerOptions()
			        		       .position(CURRENT_LOCATION)
			        		       .title(UserName)
			        		       .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi))
			        				);
			        	    	UpdateGPS(this.UserEmail);
			        	    	
			        	    /////////// ROLE 3   --  ADMINISTRATOR	
			        	    }else {
			        	    	
			        	    	googleMap.setMyLocationEnabled(true);
			        	    	UpdateGPS(this.UserEmail);
			        	    }
			        		
			        	  
			        	  
				       } else{
				    	   	gps.showSettingsAlert();
				       }
			        	
			        	
			 
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
	        } // end if else condition
	 
	    }
	    
	    
	   
	    @Override
	    protected void onResume() {
	        super.onResume();
	       
	    }
	    
	  
	    
	    
	    ///////// THIS CREATE THE HEADER METHOD //////////////////
	    //////////////////////////////////////////////////////////
	    
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	
	    	if(this.intRole != 2){
	    		MenuInflater mif = getMenuInflater();
		    	mif.inflate(R.menu.main_activity_actions, menu);
		    	
		        return super.onCreateOptionsMenu(menu);
	    	
	    	}else{
	    		
	    		MenuInflater mif = getMenuInflater();
		    	mif.inflate(R.menu.main_activity_actions_taxi, menu);
		    	
		        return super.onCreateOptionsMenu(menu);
	    	}
	    	
	     
	    }
	    
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle presses on the action bar items
	        switch (item.getItemId()) {
	            case R.id.getTaxi:
	                
	            	//////// SEND MESSAGE TO REQUEST A TAXI ///////////
	            	
	            	RequestTaxi(this.UserEmail, this.Lat, this.Lon);
	            	
	                return true;
	                
	            case R.id.TaxiFunction:
	                
	            	//////// SEND MESSAGE TO REQUEST A TAXI ///////////
	            	
	            	Intent goRequest= new Intent(this, RequestActivity.class);
		    		startActivity(goRequest);
		    		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
	            	
	                return true;
	            case R.id.LogOut:
	              
	                // Session Manager
	            	session.logoutUser();
	            	Intent goLogin = new Intent(this, LoginActivity.class);
		    		startActivity(goLogin);
		    		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	            	
	                return true;
	              
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }
	    
	  
	  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    ////////////////////////////////////////////////////////////////////////////////////////////
	    //////////////// THIS FUNCTIONS IS TO UPDATE THE GPS LOCATION /////////////////////////////
	    ////////////////////////////////////////////////////////////////////////////////////////////
	    
	   
	    
	    
	    
	    protected void UpdateGPS(final String Email){
	    	GPSTracker gps = new GPSTracker(this);
	    	final double lat = gps.getLatitude();
	    	final double lon = gps.getLongitude();
			 
		 	Thread t = new Thread() {

	            public void run() {
	            
	            	while(true){
	            		
	            		try{
			            	
			               
			                HttpClient client = new DefaultHttpClient();
			                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
			                HttpResponse response;
			                JSONObject json = new JSONObject();
		                
	           
			                try {
			                	
			                    HttpPost post = new HttpPost(URL);
			                    json.put("Lat",lat);
			                    json.put("Lon", lon);
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
			                        final String result = slurp(in);
			                        
			                        // Print on LogCat to debugin purpose
			                       // Log.e("Personal message",result);
			                        
			                        // Conver String to Json
			                      
			                 
			                        
			                        try {
				                    	
				                    	
				                        runOnUiThread(new Runnable() {

				                            @Override
				                            public void run() {
				                            	
				                            		JSONObject reader = null;
				                            		
				     							try {
				     							   
				     								reader = new JSONObject(result);
				     								
				     								JSONObject StatusRequest  = reader.getJSONObject("Result");
				     						      //  int Status = Integer.parseInt(StatusRequest.getString("Status"));
				     						        
				     						        
				     						        JSONArray TaxiDrivers = StatusRequest.getJSONArray("TaxiDrivers");
				     						       
				     						        int lenArray = TaxiDrivers.length();
				     						        
				     						        for(int i=0; i<lenArray;i++){
				     						        	
				     						        	 	JSONObject resultArrayJson  = TaxiDrivers.getJSONObject(i);
				     								        
				     								        String UserID = resultArrayJson.getString("UserID");
				     								        String Username = resultArrayJson.getString("Username");
				     								        String Email = resultArrayJson.getString("Email");
				     								        String Phone = resultArrayJson.getString("Phone");
				     								        String Latitude = resultArrayJson.getString("Latitude");
				     								        String Longitude = resultArrayJson.getString("Longitude");
				     								        String TaxiID = resultArrayJson.getString("TaxiID");
				     								        String CarModel = resultArrayJson.getString("CarModel");
				     								        String CabNumber = resultArrayJson.getString("CabNumber");
				     								     
				     								        
				     								        ////////////// THIS IS FOR DEBUGIN PURPOSE /////////////
				     									        
				     									     /*   Log.e("UserID",UserID);
				     									        Log.e("Username",Username);
				     									        Log.e("Email",Email);
				     									        Log.e("Phone",Phone);
				     									        Log.e("Latitude",Latitude);
				     									        Log.e("Longitude",Longitude);
				     									        Log.e("TaxiID",TaxiID);
				     									        Log.e("CarModel",CarModel);
				     									        Log.e("CabNumber",CabNumber);
				     									     */
				     								        
				     								        
				     								        
				     									  ///////////////////////////////////////////////////////////////////////////////////////////////
				     									  ///////////////////// NOW IT IS NECESSARY TO PUT THE TAXIS ON THE MAP /////////////////////////      
				     									  ///////////////////////////////////////////////////////////////////////////////////////////////      
				     									        double Lat = Double.parseDouble(Latitude);
				     									        double Lon = Double.parseDouble(Longitude);
				     									        
				     									      
				     									    	
				     									    	LatLng Taxi_Location = new LatLng(Lat,Lon);
				     									    	
				     									    	googleMap.addMarker(new MarkerOptions()
				     								            .position(Taxi_Location)
				     								            .title(CarModel)
				     								            .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
				     									    
				     						        	
				     						        	
				     						        } // End for loop
				     			
				     	   
				     							} catch (JSONException e) {
				     								// TODO Auto-generated catch block
				     								e.printStackTrace();
				     							}	
				     			        	
				                         
				                            	
				                            }
				                        });
				                        Thread.sleep(10000);
				                    } catch (InterruptedException e) {
				                        e.printStackTrace();
				                    }
			                        
			                 
			               
			                    }
		
			                } catch(Exception e) {
			                    e.printStackTrace();
			                 
			                }
			              
			                
			                
			                
	            		} catch(Exception e) {
		                    e.printStackTrace();
		                 
		                }
	            		
	            	} // End while loop
	               
	            }
	        };

	        t.start();
			

	    }
	    
	    
//////////////////////////////////////////////////////////////////////////////
	    ///////////// FUNCTION TO CREATE THE REQUEST TAXI/////////////
//////////////////////////////////////////////////////////////////////////////	    
	    

	    public void RequestTaxi(final String UserEmail, final double Lat, final double Lon){
	    	
	    
	    	Thread RequestTaxi = new Thread() {
	    
	            public void run() {
	            	

	                HttpClient client = new DefaultHttpClient();
	                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
	                HttpResponse response;
	                JSONObject json = new JSONObject();
	                
	                HttpPost post = new HttpPost(URLREQUEST);
	                try {
						json.put("UserEmail",UserEmail);
		                json.put("Lat",Lat);
		                json.put("Lon",Lon);
		                
		               
		                StringEntity se = new StringEntity( json.toString() );  
		                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		                post.setEntity(se);
		                response = client.execute(post);
		                	
		                Log.e("Data to update",json.toString());
	            	
		                if(response!=null){
	                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
	   
	                        // Convert data InputStream into String
	                        final String result = slurp(in);
	                        
	                        // Print on LogCat to debugin purpose
	                        
	                         JSONObject reader = new JSONObject(result);
	                         JSONObject StatusRequest  = reader.getJSONObject("Result");
	                         final int Status = Integer.parseInt(StatusRequest.getString("Status"));
	                         //int value = Integer.parseInt(reader.getString("Status"));
	                        
	                         
	                         runOnUiThread(new Runnable() {

		                            @Override
		                            public void run() {
	                      
	                            	
		                            	alertDialog("Taxi Requested");
		                            	/*
		                            	Context context = getApplicationContext();
		                    			int duration = Toast.LENGTH_SHORT;

		                    			Toast toast = Toast.makeText(context,"Taxi Requested", duration);
		                    			toast.show();
	           	  					*/
	                             }

								
	                         });
	                         
	                     
		                
	                         
			                } // if response !- null
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	         
	            	
	            } /// End run method
	        }; ///End new thread

	        RequestTaxi.start();
		

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
	 
	 
	 
	 public void alertDialog(String Message){
			
			
		 new AlertDialog.Builder(MainActivity.this)
		    .setTitle(Message)
		    
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		         // Include function if you need it on this event
		        
		        }
		     })
		   
		    .setIcon(R.drawable.gettaxi)
		     .show();
		
	}
	

	    
	    
	    
	    
} // End class MainActivity