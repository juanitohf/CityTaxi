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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener  {

	Button btnLogin, linkToRegister;
	EditText EtLogEmail,EtLogPass;
	String strEmail,strPass;
	private static final String URL = "http://web-huertas.com/work/programs/Server4350/Login.php";
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Get values from register activity
        Intent intent =  getIntent();
    	String Email_User_From_register = intent.getStringExtra("Email");
    	String PassUser_From_register = intent.getStringExtra("Pass");
    	
    	EtLogEmail = (EditText)findViewById(R.id.LogEmail);
    	EtLogPass = (EditText)findViewById(R.id.LogPass);
    	
    	/// implement on the edit text the values from the register page
    	EtLogEmail.setText(Email_User_From_register);
    	EtLogPass.setText(PassUser_From_register);
        
    	
    	
    	/// Initialize my butons
        btnLogin = (Button)findViewById(R.id.btnLogin);
        linkToRegister = (Button)findViewById(R.id.LinkRegisterId);
        
        btnLogin.setOnClickListener(this);
        linkToRegister.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
	
		switch(arg0.getId()){

	      case R.id.btnLogin: 
	        
	    	  strEmail = EtLogEmail.getText().toString();
	    	  strPass = EtLogPass.getText().toString();
	    	 

 ////// CHECK THAT INPUT ARE NOT EMPTY ////
	    	  
	    	  if(strEmail.matches("")) {
	    		  alertDialog("Email is required");
	    		  break;
	    	  }else if(strPass.matches("")) {
	    		  alertDialog("Pasword is required");
	    		  break;
	    	  } 
	    	  
	    	  ///// CONTINUING VALIDATION ///// 
	    	  ///// CHECK THAT USER INPUT A VALID EMAIL //////
	    	  
	    	  else if(!isValidEmailAddress(strEmail)) {
	    		  alertDialog("Introduce a valid email format");
	    		  break;
	    	  } 
	    	  
	    	///// IF THE CODE IS EXECUTE AT THIS POINT MEANS THAT EVERY VALIDATION PASS ///// 
	    	  else{
	    		
	    		  // Execute the method Register to complete the registrations\
	    		  
	    		  LoginHttpJson(strEmail, strPass);
	    
	    		  
	    	  } // End if else condition group.
	    	  
	    	
	        break;

	      case R.id.LinkRegisterId: 
	       
	    		Intent goRegis = new Intent(this, RegisterActivity.class);
	    		startActivity(goRegis);
	    		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	    	  
	        break;
	       
	    }
		
	} // End onClick function
	
	
	
	
	
	

	public void alertDialog(String Message){
		
		
		 new AlertDialog.Builder(this)
		    .setTitle("TaxiCab Message")
		    .setMessage(Message)
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		         // Include function if you need it on this event
		        
		        }
		     })
		   
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		
	}
	
	
	
	 public boolean isValidEmailAddress(String email) {
	        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	        java.util.regex.Matcher m = p.matcher(email);
	        return m.matches();
	 }
	
	
	 
	 
	 

	 
	 
	 ////////  HERE START MY FUNCTION TO INTERECT WITH THE SERVER ////////
	 ////////  THE INFORMATION WILL BE SENDING BY JSON FORMAT ///////////
	 
	 
	 

	 protected void LoginHttpJson(final String Email,final String Password){
	       	
		 
		 	Thread t = new Thread() {

	            public void run() {
	                Looper.prepare(); //For Preparing Message Pool for the child Thread
	                HttpClient client = new DefaultHttpClient();
	                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
	                HttpResponse response;
	                JSONObject json = new JSONObject();
	                
	              // Print on LogCat to debugin purpose
	                Log.e("Personal message","I am inside of the thread");
	                Log.e("Personal message",Email + " " +Password);
	                
	                try {
	                	
	                    HttpPost post = new HttpPost(URL);
	                    json.put("User_email", Email);
	                    json.put("User_pass", Password);
	                   
	           
	                    StringEntity se = new StringEntity( json.toString() );  
	                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                    post.setEntity(se);
	                    response = client.execute(post);
	                    
	                    /*Checking response */
	                    if(response!=null){
	                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
	                        
	                        // Convert data InputStream into String
	                        String result = slurp(in);
	                        
	                        // Print on LogCat to debugin purpose
	                        Log.e("Personal message",result);
	                        
	                        // Conver String to Json
	                      
	                        JSONObject reader = new JSONObject(result);
	                        
	                        respond(reader);
	                        
	                      
	                       
	                    
	                    }

	                } catch(Exception e) {
	                    e.printStackTrace();
	                 // createDialog("Error", "Cannot Estabilish Connection");
	                }

	                Looper.loop(); //Loop in the message queue
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
				
			 /*
			  * If Result Status is 0 = "Error Login. Wrong email and password " 
			  * If Result Status is 1 = "Success Login"  
			  * If Result Status is 2 = "Unknow error" 
			
			  * 
			  */
			 
			  JSONObject StatusRequest  = CallBackResult.getJSONObject("Result");
              int Status = Integer.parseInt(StatusRequest.getString("Status"));
              
              JSONObject UserRequest  = CallBackResult.getJSONObject("User");
              String User_name = UserRequest.getString("User_name");
              String User_email = UserRequest.getString("User_email");
              String Role_Id = UserRequest.getString("Role_Id");
              
      
			 if(Status == 1){
				 //// Success here is necessary to Handle all information about user
				
			       // Session Manager
				 	SessionManager session = new SessionManager(getApplicationContext());
				 	session.createLoginSession(User_name, User_email,Role_Id);
				 
				
					Intent goLogin = new Intent(this, MainActivity.class);
		    		startActivity(goLogin);
		    		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
		    
			 
			 }else if(Status == 0){
				 
				 alertDialog("Wrong email and password");
				
			 }else{
				 alertDialog("System error, please contact with administrator");
			 }
		} // End method respond

	 
	 
	 
	 
	
	
	
	
	
	
} // End class LoginActivity