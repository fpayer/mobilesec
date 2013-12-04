package com.example.mobilesec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
 
public class ExternalFragment extends Fragment implements AlarmFragment {
	private ListView listView1;
	public static Alarm data[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
    	if (container == null) {
	          return null;
	       }
	                View view = inflater.inflate(R.layout.fragment_external_alarms, container, false);
	                data = new Alarm[]
	        				{
	        				new Alarm(31,Alarm.LOW_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Failed Network Authentication Alarm","The Failed Network Authentication Alarm would be triggered remotely if the device fails network authentication, either through a failure of SSL (MITM) or otherwise."),
	        				new Alarm(32,Alarm.LOW_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Failed Port Knock Alarm","The Failed Port Knock Alarm would be triggered remotely if the device fails to correctly port knock."),
	        				new Alarm(33,Alarm.HIGH_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Unusual Network Probe Alarm","The Unusual Network Probe Alarm would be triggered remotely if the device floods ICMP packets."),
	        				new Alarm(34,Alarm.LOW_SENSITIVITY,Alarm.ON, "Remotely Triggered Alarm","The Remotely Triggered Alarm is a generic, remotely triggered alarm, either by the user or enterprise, to lock the device."),
	        				new Alarm(35,Alarm.HIGH_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Rogue Transfers Detected Alarm","The Rogue Transfers Detected Alarm would watch for rogue file transfers from the device, and trip locally.")
	        				};
	        		AlarmAdapter adapter = new AlarmAdapter(getActivity(),
	        				R.layout.physical_layout, data);
	        		listView1 = (ListView)view.findViewById(R.id.external_alarms_list);
	        		listView1.setAdapter(adapter);
      return view;
 
    }
    public Alarm[] getData() {
		return data;
	}
 
}