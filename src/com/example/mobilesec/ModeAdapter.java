package com.example.mobilesec;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ModeAdapter extends ArrayAdapter{
	
	Context context;
	int layoutResourceId;
	Mode data[] = null;

	@SuppressWarnings("unchecked")
	public ModeAdapter(Context context, int layoutResourceId, Mode[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ModeHolder holder = null;

		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ModeHolder();
			holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
			holder.imgIcon.setMaxHeight(32);
			holder.imgIcon.setMaxWidth(32);
			holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
			holder.description=(TextView)row.findViewById(R.id.details);
			row.setTag(holder);
		}
		else
		{
			holder = (ModeHolder)row.getTag();
		}

		Mode mode = data[position];
		holder.imgIcon.setTag(mode);
		holder.description.setTag(mode);
		
		holder.txtTitle.setText(mode.getTitle());
		holder.imgIcon.setImageResource(mode.getIcon());
		holder.description.setText(mode.getDescription());
		return row;
	}

	static class ModeHolder
	{
		ImageView imgIcon;
		TextView txtTitle;
		TextView description;
	}
}

