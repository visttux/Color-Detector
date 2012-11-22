package org.ia.practiques;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Tab listener for the ActionBar on the launcher
 *
 * @author Victor Martinez
 * @see ActionBar
 * @see TabListener
 */
public class MyTabListener implements ActionBar.TabListener {
	
	private Fragment mFragment;
	
	public MyTabListener(Fragment fragment) {
		mFragment = fragment;
	}
	
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.add(R.id.fragment_frame, mFragment, null);
		
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(mFragment);
		
	}

}
