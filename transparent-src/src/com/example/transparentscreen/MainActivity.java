package com.example.transparentscreen;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Random;

//import com.example.authenticationmethods.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        Button myButton = (Button)findViewById(R.id.buttonTest);
		myButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		updateScreen( v );
        	}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateScreen(View view) {
		Intent intent = new Intent(this, DisplayTransparentScreen.class);
		startActivity(intent);
    	
    }

}
