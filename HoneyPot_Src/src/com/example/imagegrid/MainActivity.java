package com.example.imagegrid;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ImageAdapter myAd = new ImageAdapter(this);
		myAd.randomizeArray( System.nanoTime() );
		gridview.setAdapter( myAd );

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				ImageView x = (ImageView) v;
				if( x.getId() == R.drawable.sample_3 ) {
					Toast.makeText(MainActivity.this, "Correct Selection", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "Incorrect Selection! Triggering Alarm.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
