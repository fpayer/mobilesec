package com.example.mobilesec;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ImageGridViewDialogFragment extends DialogFragment {
    private ListView listView1;

    static ImageGridViewDialogFragment newInstance() {
        return new ImageGridViewDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_grid, container, false);
        getDialog().setTitle("Authentication");
        
 

        return v;
    }
 
   
}