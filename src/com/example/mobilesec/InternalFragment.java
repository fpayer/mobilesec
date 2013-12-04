package com.example.mobilesec;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
 
public class InternalFragment extends Fragment implements AlarmFragment {
	private ListView listView1;
	public static Alarm data[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        
        if (container == null) {
	          return null;
	       }
	                View view = inflater.inflate(R.layout.fragment_internal_alarms, container, false);
	                data = new Alarm[]
	        				{
	        				new Alarm(21,Alarm.LOW_SENSITIVITY,Alarm.ON, "Power Button Listener Alarm","The Power Button listener watches for a power button press. If the power button is pressed, a smaller flag is raised - if the power button is pressed and held, a larger flag is raised. "),
	        				new Alarm(22,Alarm.HIGH_SENSITIVITY,Alarm.OFF, "Unrecognized Process Alarm","The Unrecognized Process Alarm would watch the process list to see if a new background process (unrecognized) has been started. If it is known malware/keylogger/etc, kill the process (or enter honeypot mode immediately), and raise an alarm. "),
	        				new Alarm(23,Alarm.HIGH_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "Internal Malware Alarm","The Internal Malware Alarm watches for internal malware, and a large alarm is fired when it is detected."),
	        				new Alarm(24,Alarm.MEDIUM_SENSITIVITY,Alarm.ON, "Area Code Alarm","The Area Code Alarm is triggered if the device calls a phone number with a 'black-listed'/'gray-listed' area code. If the area code is not recognized, in a specifically blacklisted area, or the number is not in your contacts already, an alarm is raised. Again, the alarm would gradient with the level of offense; calling a blacklisted area code is significantly higher alarm than a new number. "),
	        				new Alarm(25,Alarm.HIGH_SENSITIVITY,Alarm.ON, "Honeypot App Alarm","The HoneyPot Trap maintains a fake app on the home screen, which an attacker would be drawn to. Examples include a false gmail app, false Facebook, false dropbox, etc."),
	        				new Alarm(26,Alarm.LOW_SENSITIVITY,Alarm.NOT_IMPLEMENTED, "New Number Called Alarm","The New Number Called Alarm watches for new numbers being called, and fires a small alert when it is called."),
	        				new Alarm(27,Alarm.LOW_SENSITIVITY,Alarm.NOT_IMPLEMENTED,"Untrstued Computer Alarm","The Untrstued Computer Alarm maintains a protected list of 'trusted' devices/computers that the device has connected to in the past. The alarm is raised if a new device is connected to. ")
	        				};
	        		AlarmAdapter adapter = new AlarmAdapter(getActivity(),
	        				R.layout.physical_layout, data);
	        		listView1 = (ListView)view.findViewById(R.id.internal_alarms_list);
	        		listView1.setAdapter(adapter);
        return view;
    }
    public Alarm[] getData() {
		return data;
	}
}