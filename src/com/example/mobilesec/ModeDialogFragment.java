package com.example.mobilesec;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ModeDialogFragment extends DialogFragment {
    private ListView listView1;

    static ModeDialogFragment newInstance() {
        return new ModeDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modes, container, false);
        getDialog().setTitle("Modes");
        Mode data[] = new Mode[]
				{
        		new Mode(Alarm.ON, "Child Proof Mode","Tactile and easily tripped alarms, such as the honeypot app, are disabled."),
				new Mode(Alarm.NOT_IMPLEMENTED, "Data-Safe Mode","No counter-measures are taken that damage, alter, or delete data.  Additionally, device will not brick."),
				new Mode(Alarm.OFF, "High-Sensitivity","All alarms are set to highest sensitivity.  Use with caution."),
				new Mode(Alarm.ON, "Medium-Sensitivity","All alarms are set to default or medium sensitivity.  Recommended."),
				new Mode(Alarm.OFF, "Low-Sensitivity","All alarms are set to low sensitivity.  Use with caution only in environment with many false positives."),
				new Mode(Alarm.OFF, "Custom","User defined profile of alarms."),
				};
		ModeAdapter adapter = new ModeAdapter(getActivity(),
				R.layout.modes_layout, data);
		listView1 = (ListView)v.findViewById(R.id.modes_list);
		listView1.setAdapter(adapter);
 

        return v;
    }
 
   
}