package com.vit.gravitas2014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class RegisterApp extends AsyncTask<Void, Void, String> {

 private static final String TAG = "GCMRelated";
 Context ctx;
 GoogleCloudMessaging gcm;
 String SENDER_ID = "937917907048";
 static String regid = null; 
 private int appVersion;
 public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion){
  this.ctx = ctx;
  this.gcm = gcm;
  this.appVersion = appVersion;
 }
 
 
 @Override
 protected void onPreExecute() {
  super.onPreExecute();
 }


 @Override
 protected String doInBackground(Void... arg0) {
  String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);
            }
            regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;

            System.out.println("Status: "+msg);
            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            sendData();

            // For this demo: we don't need to send it because the device
            // will send upstream messages to a server that echo back the
            // message using the 'from' address in the message.

            // Persist the regID - no need to register again.
            storeRegistrationId(ctx, regid);
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
            // If there is an error, don't just keep trying to register.
            // Require the user to click a button again, or perform
            // exponential back-off.
        }
        System.out.println("Status: "+msg);
        return msg;
 }

 private void storeRegistrationId(Context ctx, String regid) {
  final SharedPreferences prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(),
             Context.MODE_PRIVATE);
     Log.i(TAG, "Saving regId on app version " + appVersion);
     SharedPreferences.Editor editor = prefs.edit();
     editor.putString("registration_id", regid);
     editor.putInt("appVersion", appVersion);
     editor.commit();
  
 }


 /*private void sendRegistrationIdToBackend() {
  URI url = null;
  try {
   url = new URI("http://10.0.2.2/GCMDemo/register.php?regId=" + regid);
  } catch (URISyntaxException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } 
  HttpClient httpclient = new DefaultHttpClient();
  HttpGet request = new HttpGet();
  request.setURI(url);
  try {
   httpclient.execute(request);
  } catch (ClientProtocolException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }*/
 public static void sendData()
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;


		URL url = null;   
		//Jatin to tell me
		//Jatin to tell me
		String parameters ="reg_id="+regid;
				System.out.println("parameter departemnt"+parameters);
		
		try
		{
			url = new URL("http://vitgravitas.com/app/reggcm.php");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");    

			request = new OutputStreamWriter(connection.getOutputStream());
			request.write(parameters);
			request.flush();
			request.close();            
			String line = "";               
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			// Response from server after login process will be stored in response variable.                
			// You can perform UI operations here          
			isr.close();
			reader.close();

		}
		catch(IOException e)
		{
			// Error
		}
	}

 @Override
 protected void onPostExecute(String result) {
  super.onPostExecute(result);
  //Toast.makeText(ctx, "Registration Completed. Now you can see the notifications", Toast.LENGTH_SHORT).show();
  Log.v(TAG, result);
 }
}