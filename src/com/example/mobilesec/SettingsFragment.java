package com.example.mobilesec;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SettingsFragment extends DialogFragment {
    private ListView listView1;

    static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modes, container, false);
        getDialog().setTitle("Settings - Under Construction");
        Mode data[] = new Mode[]
				{
				new Mode(Alarm.NOT_IMPLEMENTED, "Settings","User customizable settings will appear here. Currently under construction."),
				};
		ModeAdapter adapter = new ModeAdapter(getActivity(),
				R.layout.modes_layout, data);
		listView1 = (ListView)v.findViewById(R.id.modes_list);
		listView1.setAdapter(adapter);
        

        return v;
    }
 
   
}