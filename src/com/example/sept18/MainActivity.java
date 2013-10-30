package com.example.sept18;

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
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
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


	PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			HttpClient client = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost post = new HttpPost("http://162.243.27.156/img");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			try{
				MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test");
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				String path = file.getPath() + File.separator	+ "IMG_" + timeStamp + ".jpg";
				BufferedOutputStream mediaFile = new BufferedOutputStream(new FileOutputStream(path));
				mediaFile.write(data);
				mediaFile.flush();
				mediaFile.close();
				entity.addPart("image", new FileBody(new File(path)));
				post.setEntity(entity);
				HttpResponse response = client.execute(post, localContext);
			} catch (ClientProtocolException e) {
				System.out.println("CLIENT PROTOCOL EXCEPTION: " + e);
			} catch (IOException e) {
				System.out.println("IO EXCEPTION: " + e);
			}



			/*
			HttpPost post = new HttpPost("http://162.243.27.156/img");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("image", data));

			try {

				post.setEntity(new UrlEncodedFormEntity(data));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(post);
				System.out.println("=======RESPONSE: " + response + " =========");

			} catch (ClientProtocolException e) {
				System.out.println("CLIENT PROTOCOL EXCEPTION: " + e);
			} catch (IOException e) {
				System.out.println("IO EXCEPTION: " + e);
			}
			 */
			/*
			File pictureFile = getOutputMediaFile();
			if (pictureFile == null) {
				return;
			}
			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {
			}
			 */
		}
	};
	/*
	private static File getOutputMediaFile() {
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyCameraApp");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}
	 */


	//Onclick
	public void updateTextField(View view){



		/*
		Camera mCamera = getCameraInstance();
		System.out.println("Clicked");
		try{
			System.out.println(mCamera);
			SurfaceView dummy = new SurfaceView(getApplicationContext());
			mCamera.setPreviewDisplay(dummy.getHolder());
			mCamera.startPreview();
			mCamera.takePicture(null, null, mPicture);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally{
			mCamera.release();
		}
		 */
		/*
		Camera myCamera = Camera.open();
		myCamera.enableShutterSound(false);
		myCamera.takePicture(null, null, mPicture);

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, 11);
		 */
		/*
		TextView editText = (TextView) findViewById(R.id.textView1);
		count++;
		editText.setText("Button pressed: " + count + " times");
		if(count == 5){
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		}
		 */
	}

	private Camera getCameraInstance() {
		Camera camera = null;
		try {
			camera = Camera.open();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return camera;
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
			/*
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"fpayer@umd.edu"});
			i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
			i.putExtra(Intent.EXTRA_TEXT   , "body of email");
			try {
			    startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}

			 */
			return Integer.valueOf(0);
			/*
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://162.243.27.156");
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("json", "{\"stat\" : \"close\"}"));

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
			 */
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
