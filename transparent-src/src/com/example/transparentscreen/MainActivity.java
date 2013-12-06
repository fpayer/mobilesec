package com.example.transparentscreen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
