package com.example.imagegrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	private Integer[] mThumbIds = {
			R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5,
			R.drawable.sample_6, R.drawable.sample_7,
			R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5,
		
	};
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
			imageView.setLayoutParams(new GridView.LayoutParams(375, 375));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(2, 2, 2, 2);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		imageView.setId( mThumbIds[position] );
		return imageView;
	}
	private void configureArray() {
		mThumbIds= new Integer[12];
		mThumbIds[0]=R.drawable.sample_0;
		mThumbIds[1]=R.drawable.sample_1;
		mThumbIds[2]=R.drawable.sample_2;
		mThumbIds[3]=R.drawable.sample_3;
		mThumbIds[4]=R.drawable.sample_4;
		mThumbIds[5]=R.drawable.sample_5;
		mThumbIds[6]=R.drawable.sample_6;
		mThumbIds[7]=R.drawable.sample_7;
		
	}
}
