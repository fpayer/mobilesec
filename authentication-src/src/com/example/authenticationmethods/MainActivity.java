package com.example.authenticationmethods;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
//import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
//import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageButton imgButton;
	AlertDialog alertDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public void randomizeArray(long seed) {
			ArrayList<Integer> mThumbIdsList = new ArrayList<Integer>(Arrays.asList( mThumbIds ));
			Collections.shuffle( mThumbIdsList, new Random(seed) );
			Integer[] mThumbIdsNew = mThumbIdsList.toArray(new Integer[0]);
			mThumbIds = mThumbIdsNew;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {  // if it's not recycled, initialize some attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(285, 285));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(mThumbIds[position]);
			imageView.setId( mThumbIds[position] );
			return imageView;
		}

		// references to our images
		private Integer[] mThumbIds = {
				R.drawable.sample_2, R.drawable.sample_3,
				R.drawable.sample_4, R.drawable.sample_5,
				R.drawable.sample_6, R.drawable.sample_7,
				R.drawable.sample_0, R.drawable.sample_1,
				R.drawable.sample_2, R.drawable.sample_3,
				R.drawable.sample_4, R.drawable.sample_5,
				R.drawable.sample_6, R.drawable.sample_7,
				R.drawable.sample_0, R.drawable.sample_1,
				R.drawable.sample_2, R.drawable.sample_3,
				R.drawable.sample_4, R.drawable.sample_5,
				R.drawable.sample_6, R.drawable.sample_7
		};

	}

	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		ParcelFileDescriptor fd;
		try {
			fd = getContentResolver().openFileDescriptor(data.getData(), "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Bitmap bmp = BitmapFactory.decodeFileDescriptor( fd.getFileDescriptor() );
		imgButton.setImageBitmap( bmp );
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
