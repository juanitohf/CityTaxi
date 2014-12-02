/* create by Eric Bullock */

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

public class RegisterActivity extends Activity implements OnClickListener {

		Button btnReg, btnLogLink;
		EditText UserName, Email, Phone, Pass1, Pass2;
		String strName, strEmail, strPhone,strPass, strPass2;
		
		private static final String URL = "http://web-huertas.com/work/programs/CityTaxi_Server/Register.php";
	
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
     // store EditText and Button widges on objctes  
        UserName = (EditText)findViewById(R.id.RegUsername);
        Email = (EditText)findViewById(R.id.RegEmail);
        Phone = (EditText)findViewById(R.id.RegPhone);
        Pass1 = (EditText)findViewById(R.id.RegPass);
        Pass2 = (EditText)findViewById(R.id.RegPass2);
        
        btnReg = (Button)findViewById(R.id.btnRegister);
        btnLogLink = (Button)findViewById(R.id.btnLoginLink);
        
        btnReg.setOnClickListener(this);
        btnLogLink.setOnClickListener(this);
        
        
        
        
    } // End function on onCreate

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){

	      case R.id.btnRegister: 
	    	  
	    	// Get values from EditText
	    	  
	    	  strName = UserName.getText().toString();
	    	  strEmail = Email.getText().toString();
	    	  strPhone = Phone.getText().toString();
	    	  strPass = Pass1.getText().toString();
	    	  strPass2 = Pass2.getText().toString();
	    	  
	    	  //show this values on LogCat to see it that these values are there
	    	  
	    	  Log.e("Values to Retister", strName+", " +strEmail + ", " + strPhone +", "+strPass+", " +strPass2);
	    	  
	    	  
	    	  
	    	  ////// CHECK THAT INPUT ARE NOT EMPTY ////
	    	  
	    	  if (strName.matches("")) {
	    		  alertDialog("Username is required");
	    		  break;
	    	  }else if(strEmail.matches("")) {
	    		  alertDialog("Email is required");
	    		  break;
	    	  }else if(strPhone.matches("")) {
	    		  alertDialog("Phone is required");
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
	    	  
	    	///// CONTINUING VALIDATION ///// 
	    	  ///// CHECK THAT PASSWORDS ARE THE SAME //////
	    	  else if(!strPass.equals(strPass2)) {
	    		  alertDialog("Password not match");
	    		  break;
	    	  } 
	    	  
	    	///// IF THE CODE IS EXECUTE AT THIS POINT MEANS THAT EVERY VALIDATION PASS ///// 
	    	  else{
	    		
	    		  // Execute the method Register to complete the registrations\
	    		  
	    		 RegisterHttpJson(strName, strEmail, strPhone, strPass);
	    
	    		  
	    	  } // End if else condition group.
	    	  
	    	  
  
	        break;

	      case R.id.btnLoginLink: 
	       
	    		Intent goLogin = new Intent(this, LoginActivity.class);
	    		startActivity(goLogin);
	    		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
	    	  
	        break;
	       
	    }
		
	}// End onClick function
	
	
	
	
	
	
	
	
	
	

	public void alertDialog(String Message){
		
		
		 new AlertDialog.Builder(this)
		    .setTitle("TaxiCab Message")
		    .setMessage(Message)
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		         // Include function if you need it on this event
		        
		        }
		     })
		     /*
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
		     */
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
	 
	 
	 

	 protected void RegisterHttpJson(final String Username, final String Email,final String Phone, final String Password){
	       	
		 
		 	Thread t = new Thread() {

	            public void run() {
	                Looper.prepare(); //For Preparing Message Pool for the child Thread
	                HttpClient client = new DefaultHttpClient();
	                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
	                HttpResponse response;
	                JSONObject json = new JSONObject();
	                
	              // Print on LogCat to debugin purpose
	                Log.e("Personal message","I am inside of the thread");
	                Log.e("Personal message",Username + " " +Email + " " +Password);
	                
	                try {
	                	
	                    HttpPost post = new HttpPost(URL);
	                    json.put("User_name", Username);
	                    json.put("User_email", Email);
	                    json.put("User_pass", Password);
	                    json.put("User_phone", Phone);
	                    
	                    
	                    
	                    StringEntity se = new StringEntity( json.toString() );  
	                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                    post.setEntity(se);
	                    response = client.execute(post);
	                    
	                    /*Checking response */
	                    if(response!=null){
	                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
	                        String result = slurp(in);
	                        
	                        // Print on LogCat to debugin purpose
	                        Log.e("Personal message",result);
	                        
	                        // Conver String to Json
	                      
	                        JSONObject reader = new JSONObject(result);
	                        
	                        JSONObject sys  = reader.getJSONObject("Result");
	                        int Status = Integer.parseInt(sys.getString("Status"));
	                        
	                        // Print on LogCat to debugin purpose
	                        Log.e("Personal message",sys.getString("Status"));
	                        
	                        // Execute the callback with the result from server
	                        respond(Status);
	                    
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
		
		 public void respond(int callBack){
				
			 /*
			  * If Result is 0 = "Error no user inserted" 
			  * If Result is 1 = "Success user inserted"  
			  * If Result is 2 = "User already exit on database" 
			  * If Result is 3 = "Unknow error contact with adminstrator of programs" 
			  * 
			  */
			 if(callBack == 1){
				 
				 
				 //// SUCCESSSSSSSS /////
				 final Intent GoLogin = new Intent(this, LoginActivity.class);
				 
				 new AlertDialog.Builder(this)
				    .setTitle("TaxiCab Message")
				    .setMessage("User Inserted successfully, Please login and enjoy")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	
				        	 GoLogin.putExtra("Email", strEmail);
							 GoLogin.putExtra("Pass", strEmail);
							 startActivity(GoLogin);
					    	 overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
				        
				        }
				     })
				     /*
					    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // do nothing
					        }
					     })
				     */
				    .setIcon(android.R.drawable.ic_input_add)
				    .show();
				 
				
			 
		    
			 
			 }else if(callBack == 0){
				 
				 alertDialog("User was not inserted, please try again");
				
			 }else if(callBack == 2){
				 
				 alertDialog("Already exist a user with same email");
			 }else{
				 alertDialog("System error, please contact with administrator");
			 }
		} // End method respond

		 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
	
} // End class RegisterActivity