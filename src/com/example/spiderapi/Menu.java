package com.example.spiderapi;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//Main ProgrAM mENU
public class Menu extends ListActivity
{
	String classes[] = { "ActivityCore", "Text", "example2", "example3"
			, "example4", "gfx", "example6"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//full screen before all shit happens
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String strString = classes[position];
		
		//starting activity
		try
		{
			Class tempClass = Class.forName("com.example.spiderapi." + strString);
			Intent tempIntent = new Intent(Menu.this, tempClass);
			startActivity(tempIntent);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	//Here we create phone_menu.xml by pushing menu button on phone console
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		
		MenuInflater PhoneMenu = getMenuInflater();
		PhoneMenu.inflate(R.menu.phone_menu, menu);
		
		return true;
	}

	//Here are support for phone_menu buttons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case R.id.phonemenu_aboutus:
				Intent i = new Intent("com.example.spiderapi.PHONEMENU_ABOUTUS");
				startActivity(i);
				break;
			case R.id.phonemenu_pref:
				Intent id = new Intent("com.example.spiderapi.PHONEMENU_PREF");
				startActivity(id);
				break;
			case R.id.phonemenu_exit:
				finish();
				break;
		}
		
		return false;
	}
}
