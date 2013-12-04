package com.example.authenticationmethods;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	
	ImageButton imgButton;
	AlertDialog alertDialog;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button myButton = (Button)findViewById(R.id.button1);
        myButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Integer [] pic = {R.drawable.number1, R.drawable.number2, R.drawable.number3, R.drawable.number4,
            			R.drawable.number5, R.drawable.number6};
            	Integer [] but = {R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4,
            			R.id.imageButton5, R.id.imageButton6};
            	ArrayList<Integer> butList = new ArrayList<Integer>(Arrays.asList( but ));
            	ArrayList<Integer> picList = new ArrayList<Integer>(Arrays.asList( pic ));
            	long seed = System.nanoTime();
            	Collections.shuffle( picList, new Random(seed));
            	//Collections.shuffle( butList, new Random(seed));
            	int i;
            	for( i = 0; i < 6; i++) {
            		ImageButton btn = (ImageButton)findViewById(butList.get( i ));
            		btn.setImageResource( picList.get( i ) );
            	}
        	}
        });

       
        alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Correct!");
		alertDialog.setMessage("Correct Image Selection!");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// nothing
			}
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		
		/*ImageButton okButton = (ImageButton)findViewById(R.id.imageButton2);
        okButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		alertDialog.show();
        	}
        });*/
        
    }
    public void onClick(View v) {
    	 Intent intent = new Intent(Intent.ACTION_PICK);
         intent.setType("image/*");
         imgButton = (ImageButton)v;
         startActivityForResult(intent, 0);
         if( (ImageButton)v == findViewById(R.id.imageButton2) ) {
        	 alertDialog.show();
         }
    }
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

    			 ParcelFileDescriptor fd;
    		        //imgButton.setImageResource( R.drawable.number2);

 				try {
 					fd = getContentResolver().openFileDescriptor(data.getData(), "r");
 				} catch (FileNotFoundException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 					return;
 				}
                 Bitmap bmp = BitmapFactory.decodeFileDescriptor( fd.getFileDescriptor() );
                 imgButton.setImageBitmap( bmp );

    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
