package com.example.mobilesec;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public int currentPosition=0;
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		index%=3;
		if (index==0) {
			currentPosition=0;
			return new PhysicalFragment();
		} else if (index==1) {
			currentPosition=1;
			return new InternalFragment();
		} else if (index==2) {
			currentPosition=2;
			return new ExternalFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}