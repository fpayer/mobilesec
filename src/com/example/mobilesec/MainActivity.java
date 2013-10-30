package com.example.mobilesec;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener{

	static int count = 0;
	static Location lastLocation = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		String provider = LocationManager.NETWORK_PROVIDER;
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(provider, 0, 0, this);

		//LocationManager locationManger2 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//locationManager2

		//new HttpRequestTask().execute("");
		/*
		try{
			GMailSender sender = new GMailSender("Fran780@gmail.com", "test");
			sender.sendMail("Test", "Body", "Franz780@gmail.com", "fpayer@umd.edu");
		} catch (Exception e){
			Log.e("SendMail", e.getMessage(), e);
		}
		 */
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	//Onclick
	public void updateTextField(View view){
	}

	public class HttpRequestTask extends AsyncTask<String, Long, Integer>{

		@Override
		protected Integer doInBackground(String... coordinates) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://162.243.27.156/gps");
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("json", (String)coordinates[0]));

				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(post);
				System.out.println("=======RESPONSE: " + response + " =========");

			} catch (ClientProtocolException e) {
				System.out.println("CLIENT PROTOCOL EXCEPTION: " + e);
			} catch (IOException e) {
				System.out.println("IO EXCEPTION: " + e);
			}
			return Integer.valueOf(0);
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		protected void onPostExecute(Long result) {
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		if(lastLocation == null || location.distanceTo(lastLocation) > 25){
			new HttpRequestTask().execute("{\"lat\" : \""+latitude+"\", \"lon\" : \""+longitude+"\"}");
			Toast.makeText(MainActivity.this, "Latitude: " + latitude + ", Longitude" + longitude, Toast.LENGTH_SHORT).show();
			lastLocation = location;
		}
		/*
		new HttpRequestTask().execute("{\"lat\" : \""+latitude+"\", \"lon\" : \""+longitude+"\"}");
		Toast.makeText(MainActivity.this, "Latitude: " + latitude + ", Longitude" + longitude, Toast.LENGTH_SHORT).show();
*/

		//Log.i("Location", "Latitude: " + latitude + ", Longitude" + longitude);
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
