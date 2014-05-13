package org.jhw.keep.app.fragments;

import org.jhw.keep.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class FindFragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);

        ImageView img = ((ImageView) rootView.findViewById(R.id.image));
        LayoutParams params = img.getLayoutParams();
        params.height = 800;
        params.width = 600;
        img.setLayoutParams(params);
        img.setImageResource(R.drawable.loading);
        return rootView;
		
	}
	
	
}
