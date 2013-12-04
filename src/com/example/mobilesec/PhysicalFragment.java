package com.example.mobilesec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class PhysicalFragment extends Fragment implements AlarmFragment {
	private ListView listView1;
	private Alarm data[];
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		View view = inflater.inflate(R.layout.fragment_physical_alarms, container, false);
		data = new Alarm[]
				{
				new Alarm(11,Alarm.HIGH_SENSITIVITY,Alarm.ON, "Geo-Fence Alarm","The Geo-Fence Alarm monitors if the user leaves a pre-defined geo-fenced area; if he does, the alarm is triggered."),
				new Alarm(12,Alarm.LOW_SENSITIVITY,Alarm.OFF, "Untrusted Wifi Alarm","The Untrusted Wifi Alarm maintains a set of wifi networks to which the device connects. The alarm is always raised when the user connects to a new wifi network."),
				new Alarm(13,Alarm.MEDIUM_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Signal Loss Alarm","The Signal Loss Alarm watches for sudden loss of signal (from either airplane mode) or an exterior container, and would trigger an alarm. "),
				new Alarm(14,Alarm.MEDIUM_SENSITIVITY,Alarm.OFF, "Accelerometer Alarm","The Accelerometer Alarm raises an alarm when the accelerometer detects a gross change in motion. "),
				new Alarm(14,Alarm.LOW_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Walking Cadence Alarm","The Walking Cadence Alarm would monitor the typical walking cadence of the user, and trigger an alarm if this cadence has been grossly deviated from. It is important to note that running should not trigger the alarm - every user has their own running, walking, and jogging cadence, and the app should recognize it. "),
				new Alarm(15,Alarm.LOW_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Typing Cadence Alarm","The Typing Cadence Alarm would monitor the typical typing cadence of the user, and trigger an alarm if this cadence has been grossly deviated from.")
				};
		AlarmAdapter adapter = new AlarmAdapter(getActivity(),
				R.layout.physical_layout, data);
		listView1 = (ListView)view.findViewById(R.id.physical_alarms_list);
		listView1.setAdapter(adapter);
		return view;
	}
	public Alarm getById(int id){

		for(Alarm item : data) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}
	public void swapItems(Alarm[] alarms){
		this.data=alarms;
	}
	public Alarm[] getData() {
		return data;
	}


}