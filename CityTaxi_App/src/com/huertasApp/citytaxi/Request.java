package com.huertasApp.citytaxi;



public class Request {

	
	int USER_requests_TAXI_ID,TaxiID,RequestStatusID;
	String UserEmail,DateOfRequest,TimeOfRequest;
	double LocationOfCall_Lat,LocationOfCall_Lon;
	
	
	
	
	
	public Request(int uSER_requests_TAXI_ID, int taxiID, int requestStatusID,
			String userEmail, String dateOfRequest, String timeOfRequest,
			double locationOfCall_Lat, double locationOfCall_Lon) {
		
		
		super();
		
		USER_requests_TAXI_ID 	= uSER_requests_TAXI_ID;
		TaxiID 					= taxiID;
		RequestStatusID 		= requestStatusID;
		UserEmail 				= userEmail;
		DateOfRequest 			= dateOfRequest;
		TimeOfRequest 			= timeOfRequest;
		LocationOfCall_Lat 		= locationOfCall_Lat;
		LocationOfCall_Lon 		= locationOfCall_Lon;
	}
	
	
	
	public int getUSER_requests_TAXI_ID() {
		return USER_requests_TAXI_ID;
	}
	public void setUSER_requests_TAXI_ID(int uSER_requests_TAXI_ID) {
		USER_requests_TAXI_ID = uSER_requests_TAXI_ID;
	}
	public int getTaxiID() {
		return TaxiID;
	}
	public void setTaxiID(int taxiID) {
		TaxiID = taxiID;
	}
	public int getRequestStatusID() {
		return RequestStatusID;
	}
	public void setRequestStatusID(int requestStatusID) {
		RequestStatusID = requestStatusID;
	}
	public String getUserEmail() {
		return UserEmail;
	}
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}
	public String getDateOfRequest() {
		return DateOfRequest;
	}
	public void setDateOfRequest(String dateOfRequest) {
		DateOfRequest = dateOfRequest;
	}
	public String getTimeOfRequest() {
		return TimeOfRequest;
	}
	public void setTimeOfRequest(String timeOfRequest) {
		TimeOfRequest = timeOfRequest;
	}
	public double getLocationOfCall_Lat() {
		return LocationOfCall_Lat;
	}
	public void setLocationOfCall_Lat(double locationOfCall_Lat) {
		LocationOfCall_Lat = locationOfCall_Lat;
	}
	public double getLocationOfCall_Lon() {
		return LocationOfCall_Lon;
	}
	public void setLocationOfCall_Lon(double locationOfCall_Lon) {
		LocationOfCall_Lon = locationOfCall_Lon;
	}
	
	
		
	
	
} // This is the end of my activity request
