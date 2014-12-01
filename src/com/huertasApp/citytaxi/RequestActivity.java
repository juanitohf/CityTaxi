package com.huertasApp.citytaxi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class RequestActivity extends Activity{

	private static final String URL = "http://web-huertas.com/work/programs/CityTaxi_Server/Request.php";
	
	/** CREATE AND ARRAYLIST TO STORE THE INFORMATION THAT I DOWNLOAD FROM SQL */
	private List<Request> TaxiRequest = new ArrayList<Request>();
	
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		
		
		
		populateRequestList();
		
	}



/*

	private void populateCarList() {
		
		
		
		
		
		
		
		
		
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		TaxiRequest.add(new Request(1, 1, 1,"juanitohf@gmail.com", "2014-11-22","12:12:11",39.9203, -75.174));
		
	}
	*/
	
	
    ////////////////////////////////////////////////////////////////////////////////////////////
    //////////////// THIS FUNCTIONS IS TO GET REQUEST TAXIS/////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    
   
    
    
    
	private void populateRequestList(){
    
		 
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
		                  
		                   
		                    StringEntity se = new StringEntity( json.toString() );  
		                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		                    post.setEntity(se);
		                    response = client.execute(post);
		                    	
		                   
		                    
		                    /*Checking response */
		                    if(response!=null){
		                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
		   
		                        // Convert data InputStream into String
		                        final String result = slurp(in);
		                        
		                     
		                        
		                        try {
			                    	
			                    	
			                        runOnUiThread(new Runnable() {

			                       

										@Override
			                            public void run() {
			                            	
			                            		JSONObject Request = null;
			                            	
			                            		
			     							try {
			     							   
			     								Request = new JSONObject(result);
			     								JSONObject StatusRequest  = Request.getJSONObject("Result");
			     		
			     								int len = Request.length();
			     								//Debuggin purpose
			     								Log.e("Request",String.valueOf(len));
			     								
			     						        int Status = Integer.parseInt(StatusRequest.getString("Status"));
			     						       
			     								
			     						        if(Status != 0){
			     						        	
			     						        	/// Clear arraylist to not override the data
			     						        	TaxiRequest.clear();
			     						        
			     						        	for(int i = 0; i< len-1;i++){
			     						        	
			     						        		JSONObject RequestObject  = Request.getJSONObject(String.valueOf(i));
			     						        	
				     						        	int USER_requests_TAXI_ID  	= Integer.parseInt(RequestObject.getString("USER_requests_TAXI_ID"));
				     						        	int TaxiID  				= Integer.parseInt(RequestObject.getString("TaxiID"));
				     						        	int RequestStatusID  		= Integer.parseInt(RequestObject.getString("RequestStatusID"));
				     						        	double LocationOfCall_Lat  	= Double.parseDouble(RequestObject.getString("LocationOfCall_Lat"));
				     						        	double LocationOfCall_Lon  	= Double.parseDouble(RequestObject.getString("LocationOfCall_Lon"));
				     						        	String DateOfRequest 		= RequestObject.getString("DateOfRequest");
				     						        	String TimeOfRequest 		= RequestObject.getString("TimeOfRequest");
				     						        	String UserEmail 			= RequestObject.getString("UserEmail");
				     						        	
				     						        	
				     						        	//// Debuggin purpose
				     						        	/*
					     						        	Log.e("USER_requests_TAXI_ID",RequestObject.getString("USER_requests_TAXI_ID"));
					     						        	Log.e("TaxiID",RequestObject.getString("TaxiID"));
					     						        	Log.e("RequestStatusID",RequestObject.getString("RequestStatusID"));
					     						        	Log.e("LocationOfCall_Lat",RequestObject.getString("LocationOfCall_Lat"));
					     						        	Log.e("LocationOfCall_Lon",RequestObject.getString("LocationOfCall_Lon"));
					     						        	Log.e("DateOfRequest",RequestObject.getString("DateOfRequest"));
					     						        	Log.e("TimeOfRequest",RequestObject.getString("TimeOfRequest"));
					     						        	Log.e("UserEmail",RequestObject.getString("UserEmail"));
				     						        	
				     						        	*/
				     						        	
			     						               ////Add values into the arraylist
				     						        	TaxiRequest.add(new Request(USER_requests_TAXI_ID,TaxiID,RequestStatusID,UserEmail,DateOfRequest,TimeOfRequest,LocationOfCall_Lat,LocationOfCall_Lon));
				     						    
			     						        	} // End for loop
			     						        	
			     						        	
			     						        	populateListView();
			     						        }
			     								
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
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void populateListView() {
		
		ArrayAdapter<Request> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.RequestlistView);
		list.setAdapter(adapter);
		
	}
	
	
	private class MyListAdapter extends ArrayAdapter<Request>{
		public MyListAdapter(){
			super(RequestActivity.this, R.layout.item_view, TaxiRequest);
			
		}
		
		
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			/// Make sure we have a view to work with (may have been give null
			View itemView = convertView;
			
			if (itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
				
			}
			
			// Find the request work with
			
			Request currentRequest = TaxiRequest.get(position);
			
			
			/// Fill the Request
			
			//ImageView imageView = (ImageView)itemView.findViewById(R.id.userIcon);
			//imageView.setImageResource(currentRequest.getTaxiID());
			
			
			TextView emailUser = (TextView) itemView.findViewById(R.id.EmailListView);
			emailUser.setText(currentRequest.getUserEmail());
			
			TextView LatUser = (TextView) itemView.findViewById(R.id.LonListView);
			LatUser.setText("Latitud: " + String.valueOf(currentRequest.getLocationOfCall_Lat()));
			
			TextView LonUser = (TextView) itemView.findViewById(R.id.LatListView);
			LonUser.setText("Longitud: " + String.valueOf(currentRequest.getLocationOfCall_Lon()));
			
			
			return itemView;
		}
		
		
		
	
	}
	
	
	
	
	
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	
	    	
	    		MenuInflater mif = getMenuInflater();
		    	mif.inflate(R.menu.request_activity_actions, menu);
		    	
		        return super.onCreateOptionsMenu(menu);
	   
	     
	    }
	    
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle presses on the action bar items
	        switch (item.getItemId()) {
	           
	              
	            case R.id.BackToMap:
	              
	            	Intent goMap = new Intent(this, MainActivity.class);
		    		startActivity(goMap);
		    		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
	            	
	                return true;
	              
	            default:
	                return super.onOptionsItemSelected(item);
	        }
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
		 
	    
	    
	    
	    
	    
	    
	    
	    
	
	
} // This is the end of my RequestActivity class
