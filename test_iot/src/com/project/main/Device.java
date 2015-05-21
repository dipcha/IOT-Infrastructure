package com.project.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Path;

import org.json.JSONObject;

import com.model.DeviceModel;
import com.model.RealTimeModel;
import com.model.UserModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Path("device")
public class Device implements Runnable {
    
	static final int NUMBER_OF_DEVICES = 40;
	DeviceModel device;
	
//	public ArrayList<DeviceModel> getJsonFromUrl() {
//		JSONObject json = null;
//		ArrayList<DeviceModel> alDevice = new ArrayList<DeviceModel>();
//		try { 
//			for(int i=0; i < 4;i++){
//				URL url = new URL("http://Default-Environment-qnbzq3unpn.elasticbeanstalk.com/1/getTemp"); 
//	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	            connection.setDoOutput(true); 
//	            connection.setInstanceFollowRedirects(false); 
//	            connection.setRequestMethod("GET"); 
//	            connection.setRequestProperty("Content-Type", "application/json");
//
//	            //input = connection.getInputStream();
//	            
//	            String line;
//	            StringBuilder builder = new StringBuilder();
//	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//	            while((line = reader.readLine()) != null) {
//	             builder.append(line);
//	            }
//
//	            json = new JSONObject(builder.toString());
//	            alDevice.addAll((Collection<? extends DeviceModel>)json);
//	            connection.disconnect();
//			}
//             
//        } catch(Exception e) { 
//        	System.out.println("Exception in getJsonFromUrl" + e);
//        }
//		
//		return alDevice;
//	}
	
	
	///
	//waterLevel , waterTemp, powderLevel,powerState ,changeFilter;
	//public void getJsonFromUrl(String numberOfDevices) {
	public void getJsonFromUrl() {
		JSONObject json = null;
		ArrayList<URL> allURLs;
		try { 
			URL url = null ;
			//for(int i=1; i <= Integer.parseInt(numberOfDevices) ;i++){
			  for(int i=1; i <= NUMBER_OF_DEVICES ;i++){  
				//allURLs = new ArrayList<URL>();
				
   			 	url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getStatus");
                //allURLs.add(url);
				
//				if(i >=1 && i <=10){ /// thermostat
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getTemp");
//					 allURLs.add(url);
//				}
//				if(i >=11 && i <=20){ /// alarm clock 
////					 url = new URL("http://Default-Environment-qnbzq3unpn.elasticbeanstalk.com/"+i+"/getState");
////					 allURLs.add(url);
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getTimer");
//					 allURLs.add(url);
//				}
//				if(i >=21 && i <=30){ /// coffee maker
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getWaterLevel"); 
//					 allURLs.add(url);
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getPowderLevel");
//					 allURLs.add(url);
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getChangeFilter");
//					 allURLs.add(url);
//				}
//				if(i >=31 && i <=40){ /// toaster
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/isEmpty");
//					 allURLs.add(url);
//					 url = new URL("http://iotproject.elasticbeanstalk.com/"+i+"/getTimer");
//					 allURLs.add(url);
//				}
				
				//System.out.println(allURLs.size());
				//for(int j=0; j<allURLs.size(); j++) {
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setDoOutput(true); 
		            connection.setInstanceFollowRedirects(false); 
		            connection.setRequestMethod("GET"); 
		            connection.setRequestProperty("Content-Type", "application/json");
		            
		            String line;
		            StringBuilder builder = new StringBuilder();
		            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		            while((line = reader.readLine()) != null) {
		            	builder.append(line);
		            }
		            json = new JSONObject(builder.toString());
		          //  System.out.println(json.toString());
		            insertJSONfromURL(json);
		            connection.disconnect();
				// }
			}
             
        } catch(Exception e) { 
        	System.out.println("Exception in getJsonFromUrl" + e);
        }
		
//		return json;
	}
	
	public DeviceModel setDeviceDetails(JSONObject json) {
		try {
			device = new DeviceModel();
				device.setDevID(Integer.parseInt(json.get("devID").toString()));
				Date date = new Date();
				device.setDate((new Timestamp(date.getTime())).toString());
				
				int devIDValue = Integer.parseInt(json.get("devID").toString());
				
				if(devIDValue >= 1 && devIDValue <= 10){
					device.setDevicetype("thermostat");
					if(json.has("temperature"))
						device.setTemperature(json.get("temperature").toString());
					if(json.has("state"))
						device.setPowerState(json.get("state").toString());
				}
				if(devIDValue >= 11 && devIDValue <= 20){
					device.setDevicetype("alarm clock");
					if(json.has("timeSet"))
						device.setTime( json.get("timeSet").toString());
					if(json.has("state"))
						device.setPowerState( json.get("state").toString());
				}
				if(devIDValue >= 21 && devIDValue <= 30){
					device.setDevicetype("coffee maker");
					if(json.has("waterLevel"))
						device.setWaterLevel(json.get("waterLevel").toString());
					if(json.has("powderLevel"))
						device.setPowderLevel(json.get("powderLevel").toString());
					if(json.has("isChangeFilter"))
						device.setchangeFilter(json.get("isChangeFilter").toString());
					if(json.has("state"))
						device.setPowerState( json.get("state").toString());
					
				}
				if(devIDValue >= 31 && devIDValue <= 40){
					device.setDevicetype("toaster");
					if(json.has("timerSet"))
						device.setTimer(json.get("timerSet").toString());
					if(json.has("isEmpty"))
						device.setIsEmpty((Boolean)json.get("isEmpty"));
					if(json.has("state"))
						device.setPowerState( json.get("state").toString());
				}
				//device.setDevicetype("thermostat");
			} catch (Exception e) {
				System.out.println("Exception in setDeviceDetails" + e);
		}
		return device;
	}


	public ArrayList<BasicDBObject> getDBData() throws UnknownHostException{	
		
		String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
		MongoClientURI uri = new MongoClientURI(texturi);
		MongoClient mongo = new MongoClient(uri);
		
		ArrayList<BasicDBObject> list = new ArrayList<BasicDBObject>();
		DB db = mongo.getDB("iot");
		DBCollection coll = db.getCollection("deviceDetails");
		DBCursor cursor = coll.find();
		DeviceModel devices = null;
		while(cursor.hasNext()){
			   //System.out.println("inside cursor");
			   DBObject obj = cursor.next();
			   
			   int devID = Integer.valueOf(obj.get("devID").toString());
			   //common 
			   String devicetype = obj.get("deviceType").toString();
			   String date = obj.get("date").toString();
			   String state = obj.get("state").toString();
			   String temperature = "";
			   String timerSet = "";
			   String waterLevel  = "";
			   String powderLevel = "";
			   String changeFilter = "";
			   String timer  = "";
			   Boolean isEmpty = false;
			   
			   BasicDBObject document = null;
			   
//			   devices = new DeviceModel();
//			   devices.setDevID(devID);
//			   devices.setDate(date);
//			   devices.setDevicetype(devicetype);
			   
			   if(devID >= 1 && devID <= 10){
				   temperature = obj.get("temperature").toString();
				   
				   document = new BasicDBObject("devID", devID)
					.append("deviceType", devicetype)
					.append("state", state)
					.append("temperature", temperature)
					.append("date", date);
				   
			   }
			   if(devID >= 11 && devID <= 20){
				   timerSet = obj.get("timeSet").toString();
				   
				   document = new BasicDBObject("devID", devID)
					.append("deviceType", devicetype)
					.append("state", state)
					.append("timeSet", timerSet)
					.append("date", date);
				   
				   //devices.setTime(timerSet);
			   }
			   if(devID >= 21 && devID <= 30){
				   waterLevel = obj.get("waterLevel").toString();
				   powderLevel = obj.get("powderLevel").toString();
				   changeFilter = obj.get("isChangeFilter").toString();
				   
				   document = new BasicDBObject("devID", devID)
					.append("deviceType", devicetype)
					.append("state", state)
					.append("waterLevel", waterLevel)
					.append("powderLevel", powderLevel)
				    .append("isChangeFilter", changeFilter)
				    .append("date", date);
				   
//				   devices.setWaterLevel(waterLevel);
//				   devices.setPowderLevel(powderLevel);
//				   devices.setchangeFilter(changeFilter);
			   }
			   if(devID >= 31 && devID <= 40){
				   timer = obj.get("timerSet").toString();
				   isEmpty = (Boolean) obj.get("isEmpty");
				   
				   document = new BasicDBObject("devID", devID)
					.append("deviceType", devicetype)
					.append("state", state)
					.append("timerSet", timer)
					.append("isEmpty", isEmpty)
					.append("date", date);
				   
//				   devices.setTimer(timer);
//				   devices.setIsEmpty(isEmpty);
			   }
			   		   
			   //devices = new DeviceModel();
			    list.add(document);
		}
		return list;
		}
	
	public void insertJSONfromURL(JSONObject jsonobject) throws UnknownHostException {
		try { 
			String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
			MongoClientURI uri = new MongoClientURI(texturi);
			MongoClient mongo = new MongoClient(uri);
			DB db = mongo.getDB("iot");
			DBCollection coll = db.getCollection("deviceDetails");
				
//				Object o = JSON.parse(jsonobject.toString());
//	            DBObject dbobject = (DBObject) o;
				
				DeviceModel device = setDeviceDetails(jsonobject);
				coll.insert(insertNewDocument(device));
//				System.out.println(device.toString());				
			
        } catch(Exception e) { 
        	System.out.println("Exception in insertJSONFromURL" + e);
        }
	}
	
	public BasicDBObject insertNewDocument(DeviceModel device) throws UnknownHostException {
		Date date = new Date();
		BasicDBObject document = null;
		
		if(device.getDevID() >= 1 && device.getDevID() <= 10){
			document = new BasicDBObject("devID", device.getDevID())
			.append("deviceType", device.getDevicetype())
			.append("state", device.getPowerState())
			.append("temperature", device.getTemperature())
			.append("date", device.getDate());
	
		}
		else if(device.getDevID() >= 11 && device.getDevID() <= 20){
			document = new BasicDBObject("devID", device.getDevID())
			.append("deviceType", device.getDevicetype())
			.append("state", device.getPowerState())
			.append("timeSet", device.getTime())
			.append("date", device.getDate());
	
		}
		else if(device.getDevID() >= 21 && device.getDevID() <= 30){
			document = new BasicDBObject("devID", device.getDevID())
			.append("deviceType", device.getDevicetype())
			.append("state", device.getPowerState())
			.append("waterLevel", device.getWaterLevel())
			.append("powderLevel", device.getPowderLevel())
			.append("isChangeFilter", device.getchangeFilter())
			.append("date", device.getDate());
	
		}
		else if(device.getDevID() >= 31 && device.getDevID() <= 40){
			document = new BasicDBObject("devID", device.getDevID())
			.append("deviceType", device.getDevicetype())
			.append("state", device.getPowerState())
			.append("timerSet", device.getTimer())
			.append("isEmpty", device.getIsEmpty())
			.append("date", device.getDate());
	
		}

//		document = new BasicDBObject("devID", device.getDevID())
//				.append("temperature", device.getTemperature())
//				.append("date", device.getDate())
//				.append("deviceType", device.getDevicetype());
		
		return document;
	}
	
	
	
	public static void insertRealTimeData(String devID, String temperature) throws UnknownHostException{
		
		try{		
			String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
			MongoClientURI uri = new MongoClientURI(texturi);
			MongoClient mongo = new MongoClient(uri);
			DB db = mongo.getDB("iot");
			DBCollection coll = db.getCollection("realTimeDeviceData");
			
			Date date = new Date();
			BasicDBObject document = null;
			
			RealTimeModel rtm = new RealTimeModel();
			rtm.setDevID(devID);
			rtm.setTemperature(temperature);
			rtm.setDate((new Timestamp(date.getTime())).toString());
			if(devID.equals("1")){
				rtm.setDeviceType("Indoor Sensor");		
			}
			else if(devID.equals("2")){
				rtm.setDeviceType("Outdoor Sensor");		
			}
			else if(devID.equals("3")){
				rtm.setDeviceType("Kitchen Sensor");		
			}
			
			document = new BasicDBObject("devID", rtm.getDevID())
			.append("deviceType", rtm.getDeviceType())
			.append("temperature", rtm.getTemperature())
			.append("date", rtm.getDate());
			
			coll.insert(document);
		}
		catch(Exception e){
			System.out.println("Exception in insertRealTimeData" + e);			
		}
	}
	
	public DBObject getRealTimeData() throws UnknownHostException{
		
		String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
		MongoClientURI uri = new MongoClientURI(texturi);
		MongoClient mongo = new MongoClient(uri);
		
		DB db = mongo.getDB("iot");
		DBCollection coll = db.getCollection("realTimeDeviceData");
		
		RealTimeModel rtm = new RealTimeModel();
		
		BasicDBObject sortOrder=new BasicDBObject();
		sortOrder.put("date",-1);
		DBCursor cursor = (DBCursor) coll.find().limit(1).sort(sortOrder);
		DBObject obj = cursor.next();
		
		String devID = obj.get("devID").toString();
		String deviceType = obj.get("deviceType").toString();
		String temperature = obj.get("temperature").toString();
		String date = obj.get("date").toString();
		
		BasicDBObject document = new BasicDBObject("devID", devID)
		.append("deviceType", deviceType)
		.append("temperature", temperature)
		.append("date", date);
		
		System.out.println("obj :::"+obj.toString());
		return document;		
	}
	
	public static void registerNewUser(UserModel user){
		
		try{
			String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
			MongoClientURI uri = new MongoClientURI(texturi);
			MongoClient mongo = new MongoClient(uri);
			DB db = mongo.getDB("iot");
			DBCollection coll = db.getCollection("userDetails");
	        BasicDBObject document = new BasicDBObject();
	        
			DBCursor cursor = coll.find();
				
				document = new BasicDBObject("firstName", user.getFirstName())
					.append("lastName",user.getLastName())
					.append("email", user.getEmail())
					.append("password", user.getPassword());

				System.out.println(document.toString());
					 coll.insert(document);
					 
			 } catch(Exception e) { 
	        	System.out.println("Exception in creating new user " + e);
	        }
	}
	
	public static String checkLogin(UserModel user){
		String result = "failure";
		try{
			String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
			MongoClientURI uri = new MongoClientURI(texturi);
			MongoClient mongo = new MongoClient(uri);
			DB db = mongo.getDB("iot");
			DBCollection coll = db.getCollection("userDetails");
			
//			BasicDBObject allQuery = new BasicDBObject();
//			BasicDBObject fields = new BasicDBObject();
//			fields.put("email" , user.getEmail());
//			fields.put("password" , user.getPassword());
//			
//	        DBCursor cursor = (DBCursor)coll.find(allQuery, fields);
//	        
//	        if(cursor.hasNext()){
//	        	System.out.println(cursor.next());
//	        	result = "success";
//	        }
			
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("email" , user.getEmail()));
			obj.add(new BasicDBObject("password" , user.getPassword()));
			andQuery.put("$and", obj);
		 
			System.out.println(andQuery.toString());
		 
			DBCursor cursor = coll.find(andQuery);
			if(cursor.hasNext()){
	        	//System.out.println(cursor.next());
	        	result = "success";
	        }
        	System.out.println(result);
		} catch(Exception e) { 
        	System.out.println("Exception in creating new user " + e);
        }
		return result;		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//System.out.println("-------- thread started -------");
		try{
			while(!Thread.currentThread().isInterrupted()){
				Thread.sleep(3000);
				//System.out.println("inside while loop for run()");
				getJsonFromUrl();	
			}
					
		}
		catch(InterruptedException e) { 
        	Thread.currentThread().interrupt();
			
		}
		
		//System.out.println("-------- thread ended -------");
	}
		
}