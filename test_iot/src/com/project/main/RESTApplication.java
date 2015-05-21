package com.project.main;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.model.UserModel;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Path("/devices")
public class RESTApplication {
		
		@GET
		@Path("/get")
		@Produces("application/json")
		public ArrayList<BasicDBObject> getDeviceDetails() throws UnknownHostException {
			Device device = new Device();
			ArrayList<BasicDBObject> dm = device.getDBData();
		    return dm;
		}
		
		@GET
//		@Consumes("text/plain")
		//@Path("/add/{data}")
		@Path("/add")
		//public Response addDeviceDetails(@PathParam("data") String data) throws UnknownHostException {
		public Response addDeviceDetails() throws UnknownHostException {
			Device device = new Device();
			//ArrayList<JSONObject> json = new ArrayList<JSONObject>();
			//device.insertJSONfromURL();
			device.getJsonFromUrl();
			//return json;	
			//return
			//data = "done";
			return Response.status(200).entity("success").build();
		}
		
		
		@POST
		@Path("/addsensordata")
		public Response insertRealTimeData(@QueryParam("devID") String devID, @QueryParam("temperature")  String temperature) throws UnknownHostException{
			
			Device device = new Device();
			device.insertRealTimeData(devID, temperature);
			return Response.status(200).entity("{ devID : "+devID +",temperature : "+ temperature +" }").build();
			
		}
		
		@GET
		@Path("/getsensordata")
		@Produces("application/json")
		public DBObject getRealTimeData() throws UnknownHostException{
			
			Device device = new Device();
			DBObject basicdbobject = device.getRealTimeData();
			return basicdbobject;
			
		}
		
		@POST
		@Path("/register")
		public Response registerUser(
				@FormParam("firstname") String firstName,
		 		@FormParam("lastname") String lastName,
		 		@FormParam("email") String email,
		 		@FormParam("password") String password, @Context HttpServletRequest req){
					UserModel user = new UserModel();
					Device device = new Device();

					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
					user.setPassword(password);
					
					HttpSession session = req.getSession(true);
					session.setAttribute("username", email);
					session.setAttribute("sessionId", session.getId());

					System.out.println("User added"); 
					device.registerNewUser(user);

		   return Response.status(200).entity("success").build();
		// return Response.status(200).contentLocation(new URI("/home")).build();

		}

		@POST
		@Path("/login")
		public Response checkUser(
				@FormParam("email") String email,
		 		@FormParam("password") String password, @Context HttpServletRequest req){
					UserModel user = new UserModel();

					user.setEmail(email);
					user.setPassword(password);
					
					HttpSession session = req.getSession(true);
					session.setAttribute("username", email);
					session.setAttribute("sessionId", session.getId());

					System.out.println("Login called with email and password" +user.getEmail() +" " + user.getPassword()); 
					String result = Device.checkLogin(user);

		   return Response.status(200).entity(result).build();
		// return Response.status(200).contentLocation(new URI("/home")).build();

		}
		
		@GET
		@Path("/run")
		public Response runThread() throws UnknownHostException, InterruptedException {
		
			
				Thread thread = new Thread(new Device() , "autoInsertThread");
				try{
					thread.start();
					for(int i = 0 ; i < 1000; i++){
						
						//thread.sleep(60000);
						thread.run();
					}
					thread.stop();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return Response.status(200).entity("success").build();
		}
		
		

}