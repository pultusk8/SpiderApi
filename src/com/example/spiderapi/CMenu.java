package com.example.spiderapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CMenu extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_backg_sound);
		
		Thread tTimer = new Thread()
		{
			public void run()
			{
				try
				{
					sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent MainMenu = new Intent("com.example.spiderapi.ACTIVITYCORE");
					startActivity(MainMenu);
				}
			}
		};
		
		tTimer.start();
	}

	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}
