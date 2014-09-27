package com.huertasApp.citytaxi;


import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;




	public class MainActivity extends Activity {
	 
	    
	


		// Google Map
	    GoogleMap googleMap;
	    Marker UserMarket;
	    double Lat, Lon;
	 
	     
	    // Session Manager Class
	    SessionManager session;
	 
	    
	    
	    
		  ///Execute function each 10 seconds
			

		

		public double getLat() {
			return Lat;
		}

		public void setLat(double lat) {
			Lat = lat;
		}

		public double getLon() {
			return Lon;
		}

		public void setLon(double lon) {
			Lon = lon;
		}
	    
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        // Session Manager
	        session = new SessionManager(getApplicationContext());
	        //  Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
	 
	        // Execute the background function to update user position each 10 second
	        UpdateGpsUser updateGPS = new UpdateGpsUser();
	        updateGPS.UpdateGPS();
	        
	        
	        
	        
	        if(session.isLoggedIn() == false){
	        	Intent goLogin = new Intent(this, LoginActivity.class);
	    		startActivity(goLogin);
	    		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	        	
	        }else{
	        
			        try {
			        	
			        	GPSTracker gps = new GPSTracker(this);
			        	if(gps.canGetLocation()){ 
			        		Lat = gps.getLatitude(); // returns latitude
			        		Lon = gps.getLongitude(); // returns longitude
			        		
			        		 // Loading map
			        		initilizeMap();
			        		
			        		LatLng CURRENT_LOCATION = new LatLng(Lat,Lon);
			        		//Get current position on map
			        		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 15);
			        		googleMap.animateCamera(update);
			        		
			        		/////////// GET INFORMATION OF USER WHO IS LOGED /////////////
			        		HashMap<String, String> User = session.getUserDetails();
			        		String UserName = User.get("name");
			        		String Role = User.get("role");
			        		int intRole = Integer.parseInt(Role);
			        		
			        		//////////////// PRINT TO DEBUG //////////////////
			        	    Log.e("Mi name is:",User.toString());
			        	    
			        	    
			        	    
			        	    ///////// DEPENDING WHAT IS THE ROLE IT CHANGE THE MARKERT IMG
			        	    
			        	    /////////// ROLE 1   --  USER
			        	    if(intRole == 1){
			        	    
			        	    	UserMarket = googleMap.addMarker(new MarkerOptions()
			        		       .position(CURRENT_LOCATION)
			        		       .title(UserName)
			        		       .snippet("Current Location")
			        		       .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_position))
			        				);
			        	    
			        	    /////////// ROLE 2   --  TAXI DRIVER
			        	    }else if(intRole == 2){
			        	    	
			        	    	UserMarket = googleMap.addMarker(new MarkerOptions()
			        		       .position(CURRENT_LOCATION)
			        		       .title(UserName)
			        		       .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi))
			        				);
			        	    	
			        	    /////////// ROLE 2   --  ADMINISTRATOR	
			        	    }else {
			        	    	
			        	    	UserMarket = googleMap.addMarker(new MarkerOptions()
			        	    	   .position(CURRENT_LOCATION)
			        		       .title(UserName)
			        		       .snippet("Current Location")
			        		       .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_position))
			        	    			);
			        	    }
			        		
			        	    ///////// HERE I NEED TO CONTROL IF EXIST CHANGE ON THE LAT LNG
			        	    
			        	    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			        	    LocationListener ll = new mylocationlistener();
			        	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,ll);
			        	    
			        	    
			        	    
			        	    
			        	    
				       } else{
				        		
				        	gps.showSettingsAlert();
				       }
			        	
			        	
			 
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
	        } // end if else condition
	 
	    }
	    
	    
	    
	    
	    class mylocationlistener implements LocationListener{

			@Override
			public void onLocationChanged(Location location) {
			
				if(location != null){
				
						double newLat = location.getLatitude();
						double newLng = location.getLongitude();
						
						setLat(newLat);
						setLon(newLng);
						
						//Log.e("Latitude:",Double.toString(newLat));
						//Log.e("Longitued:",Double.toString(newLng));
						
						
						LatLng CURRENT_CHANGED = new LatLng(newLat,newLng);
						UserMarket.setPosition(CURRENT_CHANGED);
						
						//// Open updateGPSUser Class
					
					    
			
				}
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
	 
			
			
			
	    	
	    	
	    } // end LocationListener
	    
	    
	 
	    /**
	     * function to load map. If map is not created it will create it for you
	     * */
	    private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	                    R.id.map)).getMap();
	 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	 
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }
	    
	    
	    
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	
	    	
	    	MenuInflater mif = getMenuInflater();
	    	mif.inflate(R.menu.main_activity_actions, menu);
	    	
	     return super.onCreateOptionsMenu(menu);
	     
	    }
	    
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle presses on the action bar items
	        switch (item.getItemId()) {
	            case R.id.searchIcon:
	                
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
	    
	  
	 
	    
	    
	    
	    
	    
} // End class MainActivity