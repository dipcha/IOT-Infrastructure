package com.project.main;

import java.net.UnknownHostException;

import com.model.DeviceModel;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBFunctions {

	
	public String getDBData() throws UnknownHostException{	
		String texturi = "mongodb://root:root@ds051831.mongolab.com:51831/iot";
		MongoClientURI uri = new MongoClientURI(texturi);
		MongoClient mongo = new MongoClient(uri);
		
		String finalString = "";
		DB db = mongo.getDB("iot");
		DBCollection coll = db.getCollection("deviceDetails");
		DBCursor cursor = coll.find();
		DeviceModel devices = null;
		while(cursor.hasNext()){
			   System.out.println("inside cursor");
			   DBObject obj = cursor.next();
			   int devID = Integer.valueOf(obj.get("id").toString());
			   String temperature = obj.get("temperature").toString();
			   String devicetype = obj.get("deviceType").toString();
			   String date = obj.get("date").toString();
					   
			   devices = new DeviceModel();
			   devices.setDevID(devID);
			   devices.setTemperature(temperature);
			   devices.setDate(date);
			   devices.setDevicetype(devicetype);
			   finalString += devices.toString();
		}
	return finalString;
	}
	
}