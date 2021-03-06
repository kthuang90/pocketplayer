package edu.psu.ktwok.pocketplayer;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ContactMail extends Activity {
	Intent i;
	private static final int SETTINGS_ACTIVITY_REQUEST_CODE = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_mail); //needed?
		//implement action bar
		ActionBar aBar = getActionBar();
		aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		aBar.setDisplayHomeAsUpEnabled(true);
		
		//friend tab
		String lab1 = getResources().getString(R.string.friends); //what do i id?
		Tab tab = aBar.newTab();
		tab.setText(lab1);
		TabListener<friendFrag> friFrag = new TabListener<friendFrag>(this, lab1, friendFrag.class);
		tab.setTabListener(friFrag);
		aBar.addTab(tab);
		
		//mail tab
		String lab2 = getResources().getString(R.string.mail);
		tab = aBar.newTab();
		tab.setText(lab2);
		TabListener<mailFrag> mFrag = new TabListener<mailFrag>(this, lab2, mailFrag.class);
		tab.setTabListener(mFrag);
		aBar.addTab(tab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				i = new Intent(this, CharSelect.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				return true;
			case R.id.menu_settings:
				i = new Intent(this, SettingsActivity.class);
				startActivityForResult(i, SETTINGS_ACTIVITY_REQUEST_CODE);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private class TabListener<T extends Fragment> implements ActionBar.TabListener{
		private Fragment mFrag;
		private final Activity mAct;
		private final String mTag;
		private final Class<T> mClass;
		
		public TabListener(Activity a, String t, Class<T> c){
			mAct = a;
			mTag = t;
			mClass = c;
		}
		
		@Override
		public void onTabSelected(Tab t, FragmentTransaction ft){
			if(mFrag == null){
				mFrag = Fragment.instantiate(mAct, mClass.getName());
				ft.add(android.R.id.content, mFrag, mTag);
			}
			else
				ft.attach(mFrag);
		}
		
		@Override
		public void onTabUnselected(Tab t, FragmentTransaction ft){
			if (mFrag != null)
				ft.detach(mFrag);
		}
		
		@Override
		public void onTabReselected(Tab t, FragmentTransaction ft){
			//nothing when tapped twice :P
		}
	}

}
