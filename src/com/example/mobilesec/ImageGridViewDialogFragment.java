package com.example.mobilesec;

import android.app.DialogFragment;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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
				if( x.getId() == R.drawable.sample_3 ) {
					Alarm.setTriggerTotal(0);
					new HttpStatusPostTask().execute("{\"event\" : \""+"Grid Reauth Passed"+"\", \"level\" : \""+Alarm.getTriggerTotal()+"\"}");
					Toast.makeText(getActivity(), "Correct Selection", Toast.LENGTH_SHORT).show();
				} else {
					Alarm.setTriggerTotal(Alarm.getTriggerTotal() + 3);
					new HttpStatusPostTask().execute("{\"event\" : \""+"Grid Reauth Failed"+"\", \"level\" : \""+Alarm.getTriggerTotal()+"\"}");
					Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
					Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
					r.play();
					new HttpStatusPostTask().execute("{\"event\" : \""+"Audible Alarm Fired"+"\", \"level\" : \""+Alarm.getTriggerTotal()+"\"}");
				}
				MainActivity.gridShown = false;
				MainActivity.newFragment.dismiss();
			}
		});
        return v;
    }
 
   
}