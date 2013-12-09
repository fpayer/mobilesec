package com.example.mobilesec;

import java.lang.reflect.Field;

import com.example.mobilesec.MainActivity;
import com.example.mobilesec.HttpGPSPostTask;

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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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


	//Sensors
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	/** Minimum movement force to consider. */
	private static final int MIN_FORCE = 10;
	/**
	 * Minimum times in a shake gesture that the direction of movement needs to
	 * change.
	 */
	private static final int MIN_DIRECTION_CHANGE = 3;
	/** Maximum pause between movements. */
	private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;
	/** Maximum allowed time for shake gesture. */
	private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;
	/** Time when the gesture started. */
	private long mFirstDirectionChangeTime = 0;
	/** Time when the last movement started. */
	private long mLastDirectionChangeTime;
	/** How many movements are considered so far. */
	private int mDirectionChangeCount = 0;
	/** The last x position. */
	private float lastX = 0;
	/** The last y position. */
	private float lastY = 0;
	/** The last z position. */
	private float lastZ = 0;
	/** The last time alarm was fired */
	private long lastTime = 0;

	//Here be static variables. Beware
	static boolean gridShown=false;
	static DialogFragment newFragment = null;
	static WifiManager wm = null;

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		/**
		 * Resets the shake parameters to their default values.
		 */
		private void resetShakeParameters() {
			mFirstDirectionChangeTime = 0;
			mDirectionChangeCount = 0;
			mLastDirectionChangeTime = 0;
			lastX = 0;
			lastY = 0;
			lastZ = 0;
		}

		public void onSensorChanged(SensorEvent se) {
			if (accelerometerEnabled) {
				float x = se.values[SensorManager.DATA_X];
				float y = se.values[SensorManager.DATA_Y];
				float z = se.values[SensorManager.DATA_Z];

				// calculate movement
				float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

				if (totalMovement > MIN_FORCE) {

					// get time
					long now = System.currentTimeMillis();

					// store first movement time
					if (mFirstDirectionChangeTime == 0) {
						mFirstDirectionChangeTime = now;
						mLastDirectionChangeTime = now;
					}

					// check if the last movement was not long ago
					long lastChangeWasAgo = now - mLastDirectionChangeTime;
					if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {

						// store movement data
						mLastDirectionChangeTime = now;
						mDirectionChangeCount++;

						// store last sensor data 
						lastX = x;
						lastY = y;
						lastZ = z;

						// check how many movements are so far
						if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {

							// check total duration
							long totalDuration = now - mFirstDirectionChangeTime;
							if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE && now - lastTime > 1000 && !gridShown) {
								lastTime = now;
								Alarm.setTriggerTotal(Alarm.getTriggerTotal()+2);
								int total = Alarm.getTriggerTotal();
								new HttpStatusPostTask().execute("{\"event\" : \""+"Accelerometer Alarm"+"\", \"level\" : \""+Alarm.getTriggerTotal()+"\"}");

								if(total >0 && total < 5){
									launchGridView();
								} else if(total >= 5){
									launchGridView();
									//TODO
								}
								resetShakeParameters();
							}
						}

					} else {
						resetShakeParameters();
					}
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

		wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		
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
			new HttpGPSPostTask().execute("{\"lat\" : \""+latitude+"\", \"lon\" : \""+longitude+"\"}");
			//Toast.makeText(MainActivity.this, "Latitude: " + latitude + ", Longitude" + longitude, Toast.LENGTH_SHORT).show();
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
		if (!gridShown) {
			gridShown = true;
			newFragment = ImageGridViewDialogFragment.newInstance();
			newFragment.show(getFragmentManager(), "dialog");
			newFragment.setStyle(DialogFragment.STYLE_NORMAL, DialogFragment.TRIM_MEMORY_RUNNING_LOW);
		} 
	}
	
	public static String getDeviceId(){
		WifiInfo info = MainActivity.wm.getConnectionInfo();
		return info.getMacAddress().toUpperCase() + "@" + info.getSSID().replace("\"", "");
	}
}