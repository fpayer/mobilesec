package com.example.mobilesec;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmAdapter extends ArrayAdapter{

	Context context;
	int layoutResourceId;
	Alarm data[] = null;

	@SuppressWarnings("unchecked")
	public AlarmAdapter(Context context, int layoutResourceId, Alarm[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AlarmHolder holder = null;

		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new AlarmHolder();
			holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
			holder.imgIcon.setMaxHeight(32);
			holder.imgIcon.setMaxWidth(32);
			holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
			holder.infoIcon = (ImageView)row.findViewById(R.id.infoIcon);
			holder.infoIcon.setMaxHeight(32);
			holder.infoIcon.setMaxWidth(32);
			row.setTag(holder);
		}
		else
		{
			holder = (AlarmHolder)row.getTag();
		}

		Alarm alarm = data[position];
		holder.imgIcon.setTag(alarm);
		holder.infoIcon.setTag(alarm);
		holder.txtTitle.setText(alarm.getTitle());
		holder.imgIcon.setImageResource(alarm.getIcon());
		holder.infoIcon.setImageResource(alarm.getInfo());
		return row;
	}

	static class AlarmHolder
	{
		ImageView imgIcon;
		TextView txtTitle;
		ImageView infoIcon;
	}
}

