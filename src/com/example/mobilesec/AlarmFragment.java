package com.example.mobilesec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface AlarmFragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	public Alarm[] getData();
}
