package com.example.mobilesec;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.mobilesec.MainActivity;
import com.example.mobilesec.MainActivity.HttpRequestTask;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends FragmentActivity implements
ActionBar.TabListener,LocationListener{
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private boolean alertShown=false;
	static Location lastLocation = null;
	private boolean gpsEnabled=false, accelerometerEnabled=false;
	// Tab titles
	private String[] tabs = { "Physical", "Internal", "External" };
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			Alarm[] data = ((AlarmFragment)mAdapter.getItem(mAdapter.currentPosition)).getData();
			if (accelerometerEnabled) {
				float x = se.values[0];
				float y = se.values[1];
				float z = se.values[2];
				mAccelLast = mAccelCurrent;
				mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
				float delta = mAccelCurrent - mAccelLast;
				mAccel = mAccel * 0.9f + delta; // perform low-cut filter
				if (mAccel>(data[3].getSensitivity()+3)) {
					//playBeep();
					showAlert("Accelerometer","Fired!");

				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		String provider = LocationManager.NETWORK_PROVIDER;
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(provider, 0, 0, this);		
		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);     
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void turnAlarmOnOrOff(View v) {
		boolean isEnabled=false;
		ImageView icon= (ImageView) v;
		String s = icon.getTag().toString();
		if (getDrawableId(icon)== R.drawable.off) {
			icon.setImageResource(R.drawable.on);
			isEnabled=true;
		} else if (getDrawableId(icon)== R.drawable.on) {
			icon.setImageResource(R.drawable.off);
			isEnabled=false;
		}
		String enabledString = (isEnabled)?" Enabled": " Disabled";

		if (s.equals("Geo-Fence Alarm")) {
			gpsEnabled= isEnabled;

		} else if (s.equals("Untrusted Wifi Alarm")) {

		} else if (s.equals("Accelerometer Alarm")) {
			accelerometerEnabled=isEnabled;
		} else if (s.equals("Untrusted Wifi Alarm")) {

		} 
		if (getDrawableId(icon)!= R.drawable.nuetral) {
			showAlert(s,s+enabledString);
		} else {
			showAlert(s,s+" has not yet been implemented.");
			icon.setImageResource(R.drawable.nuetral);
		}
	}
	public void toggleImplementedIcons(boolean isEnabled) {
		if (!isEnabled) {
			Alarm[] data = ((AlarmFragment)mAdapter.getItem(mAdapter.currentPosition)).getData();
			for (int i=0;i<data.length;i++){
				if (data[i].getIcon()==R.drawable.nuetral) {
					data[i].setIcon(R.drawable.off);
				}
			}
			for (int i=0;i<data.length;i++){
				if (data[i].getIcon()==R.drawable.nuetral) {
					data[i].setIcon(R.drawable.off);
				}
			}
			for (int i=0;i<data.length;i++){
				if (data[i].getIcon()==R.drawable.nuetral) {
					data[i].setIcon(R.drawable.off);
				}
			}
		}
	}
	public static int getDrawableId(ImageView obj) {
		Field f;
		Object out = null;
		try {
			f = ImageView.class.getDeclaredField("mResource");
			f.setAccessible(true);
			out = f.get(obj);
		}
		catch (Exception e) {}
		return ((Integer)out);
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
		if (gpsEnabled) {
			if (lastLocation == null) {
				lastLocation = location;
			}
			if(location.distanceTo(lastLocation) > 1){
				showAlert("GPSAlarm!","GPS_ALARM FIRED! Latitude: " + latitude + ", Longitude" + longitude);
			}
		}
		if(lastLocation == null || location.distanceTo(lastLocation) > 25){
			new HttpRequestTask().execute("{\"lat\" : \""+latitude+"\", \"lon\" : \""+longitude+"\"}");
			Toast.makeText(MainActivity.this, "Latitude: " + latitude + ", Longitude" + longitude, Toast.LENGTH_SHORT).show();
			lastLocation = location;
		}
	}
	public void launchConfiguration(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		ImageView icon = (ImageView) v;
		final Alarm a = ((Alarm)icon.getTag());
		String title=a.getTitle();
		String description = ((Alarm)icon.getTag()).getDescription();
		LinearLayout linear=new LinearLayout(this); 
		linear.setOrientation(1); 
		final SeekBar seek=new SeekBar(this);
		TextView textLow = new TextView(this);
		TextView textHigh = new TextView(this);

		seek.setMax(10);
		seek.setProgress(a.getSensitivity());
		linear.addView(seek); 
		textLow.setText("Low Sensitivity");
		textHigh.setText("High Sensitivity");
		alertDialogBuilder.setView(linear);
		alertDialogBuilder.setTitle("Configure "+title);
		alertDialogBuilder
		.setMessage(description+"\n\nSensitivity of alarm:")
		.setCancelable(false)
		.setPositiveButton("Save",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				a.setSensitivity(seek.getProgress());
			}
		})
		.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public boolean launchModes(MenuItem item) {
		DialogFragment newFragment = ModeDialogFragment.newInstance();
		newFragment.show(getFragmentManager(), "dialog");
		return true;
	}
	public boolean launchSettings(MenuItem item) {
		DialogFragment newFragment = SettingsFragment.newInstance();
		newFragment.show(getFragmentManager(), "dialog");
		return true;
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
	public void showAlert(String title, String message) {
		if (!alertShown) {
			alertShown=true;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(message);
			builder.setTitle(title);
			builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					alertShown=false;
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		} else {
			alertShown=false;
		}
	}

	public void launchGridView(){
		setContentView(R.layout.activity_main);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		ImageAdapter myAd = new ImageAdapter(this);
		myAd.randomizeArray( System.nanoTime() );
		gridview.setAdapter( myAd );

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				ImageView x = (ImageView) v;
				String mystr;
				if( x.getId() == R.drawable.sample_3 ) {
					mystr = "Correct Selection";
				} else {
					mystr = "Incorrect Selection";
				}
				Toast.makeText(MainActivity.this, mystr, Toast.LENGTH_SHORT).show();
			}
		}); 
	}
}