package com.example.transparentscreen;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class DisplayTransparentScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_transparent_screen);

		int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LOW_PROFILE
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

		getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
		
		final AlertDialog alertDialog = new
				AlertDialog.Builder(this).create();
		alertDialog.setTitle("Correct!");
		alertDialog.setMessage("Correct Image Selection!");
		alertDialog.setIcon(R.drawable.ic_launcher);

		Button hiddenbutton = (Button) findViewById(R.id.buttonOK);
		hiddenbutton.setVisibility(View.VISIBLE);
		hiddenbutton.setBackgroundColor(Color.TRANSPARENT);

		hiddenbutton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		alertDialog.show();
        	}
        });
	}
}


