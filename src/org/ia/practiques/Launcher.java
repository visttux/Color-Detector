package org.ia.practiques;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;

/**
 * The main activity of the application, it contains a frame layout filled by fragments
 * which takes a Bitmap from different resources
 * 
 * @author Victor Martinez
 * @see Bitmap
 * @see Fragment
 * @see ActionBar
 */
public class Launcher extends Activity {

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        
        Fragment downloadFragment = new DownloadFragment();
        
        actionBar.addTab(actionBar.newTab().setText(R.string.download_tab)
        		.setTabListener(new MyTabListener(downloadFragment)));
        
        Fragment photoFragment = new PhotoFragment();
        actionBar.addTab(actionBar.newTab().setText(R.string.photo_fragment)
        		.setTabListener(new MyTabListener(photoFragment)));
        
                
    
    }
}
  
    

