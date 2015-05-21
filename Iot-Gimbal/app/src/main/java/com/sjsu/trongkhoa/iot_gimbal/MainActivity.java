package com.sjsu.trongkhoa.iot_gimbal;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//=======Gimbal API ========//

import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.Place;
import com.gimbal.android.Visit;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.Communication;
import com.gimbal.android.Push;


import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.protocol.Beacon;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends Activity {
    private PlaceEventListener placeEventListener;
    private CommunicationListener communicationListener;
    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;
    private ArrayList<com.gimbal.android.Beacon> myBeacons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Set API Key: fc19ffbe-974e-401a-828d-3c108e99f32a");

        Gimbal.setApiKey(this.getApplication(), "fc19ffbe-974e-401a-828d-3c108e99f32a");
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                // This will be invoked when a place is entered. Example below shows a simple log upon enter
                Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
            }

            @Override
            public void onVisitEnd(Visit visit) {
                // This will be invoked when a place is exited. Example below shows a simple log upon exit
                Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
            }
        };
        communicationListener = new CommunicationListener() {
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Visit visit) {
                for (Communication comm : communications) {
                    Log.i("INFO", "Place Communication: " + visit.getPlace().getName() + ", message: " + comm.getTitle());
                }
                //allow Gimbal to show the notification for all communications
                return communications;
            }

            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Push push) {
                for (Communication comm : communications) {
                    Log.i("INFO", "Received a Push Communication with message: " + comm.getTitle());
                }
                //allow Gimbal to show the notification for all communications
                return communications;
            }

            @Override
            public void onNotificationClicked(List<Communication> communications) {
                Log.i("INFO", "Notification was clicked on");
            }
        };
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
//                for (Iterator< com.gimbal.android.Beacon> i = myBeacons.iterator() ; i.hasNext();){
//                    com.gimbal.android.Beacon b = i.next();
//                    if ()
//                }

                com.gimbal.android.Beacon b1 = sighting.getBeacon();
                if(b1.getName().contains("First")){
                    new RequestTask().execute("http://iotproject.elasticbeanstalk.com/1/setTemp?temperature=" + b1.getTemperature());
                  //  new RequestTask().execute("http://localhost:3000/1/setTemp?temperature=" + b1.getTemperature());
                }else if(b1.getName().contains("Second")){
                    new RequestTask().execute("http://iotproject.elasticbeanstalk.com/2/setTemp?temperature=" + b1.getTemperature());

                }else{
                    //new RequestTask().execute("http://localhost:3000/3/setTemp?temperature=" + b1.getTemperature());
                    new RequestTask().execute("http://iotproject.elasticbeanstalk.com/3/setTemp?temperature=" + b1.getTemperature());
                }
                Log.i("Beacon", b1.getName() + ":" +b1.getTemperature());
                //Log.i("INFO", sighting.toString());
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);

        CommunicationManager.getInstance().addListener(communicationListener);
        PlaceManager.getInstance().addListener(placeEventListener);

        PlaceManager.getInstance().startMonitoring();
        beaconManager.startListening();
        CommunicationManager.getInstance().startReceivingCommunications();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
              //  response = httpclient.execute(new HttpGet(uri[0]));
                response = httpclient.execute(new HttpPost(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    System.out.println(responseString);
                    out.close();
                } else{
                    //Closes the connection.
                    Log.d("ErrorNet:","Error!!!");
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }
    public void buttonClick(View view) throws IOException {
        //new RequestTask().execute("http://iotproject.elasticbeanstalk.com/2/setName?name=Kitchen");
        //new RequestTask().execute("http://iotproject.elasticbeanstalk.com/2/setName?name=Kitchen");
       // new RequestTask().execute("http://myserver.elasticbeanstalk.com/resource/devices/addsensordata?devID=2&temperature=100");
       // new RequestTask().execute("http://localhost:3000/1/setName?name=Kitchen");
        MqttAndroidClient mqttClient = new MqttAndroidClient(this,"mqtt://54.191.51.27","androidApp");
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
            if (mqttClient.isConnected()){
                Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();

            }
        Toast.makeText(this,"Hello World",Toast.LENGTH_LONG).show();
    }
}
