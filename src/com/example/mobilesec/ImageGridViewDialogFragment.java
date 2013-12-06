package com.example.mobilesec;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ImageGridViewDialogFragment extends DialogFragment {
    private ListView listView1;

    static ImageGridViewDialogFragment newInstance() {
        return new ImageGridViewDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_grid, container, false);
        getDialog().setTitle("Authentication");
        
        GridView gridview = (GridView) v.findViewById(R.id.gridview);
		ImageAdapter myAd = new ImageAdapter(this.getActivity());
		myAd.randomizeArray( System.nanoTime() );
		gridview.setAdapter( myAd );
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				ImageView x = (ImageView) v;
				String mystr;
				if( x.getId() == R.drawable.sample_3 ) {
					mystr = "Correct Selection";
					MainActivity.newFragment.dismiss();
				} else {
					mystr = "Incorrect Selection";
				}
				Toast.makeText(getActivity(), mystr, Toast.LENGTH_SHORT).show();
			}
		});
        return v;
    }
 
   
}