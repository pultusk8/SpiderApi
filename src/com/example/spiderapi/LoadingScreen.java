package com.example.spiderapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LoadingScreen extends Activity
{
	MediaPlayer Player = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_backg_sound);
		
		//seting up sound player
		Player = MediaPlayer.create(LoadingScreen.this, R.raw.menu_sound);
		
		//Get preference value about Music on or  off
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean IsSoundON = getPrefs.getBoolean("checkbox", true);
		
		if(IsSoundON) 
			Player.start(); //start playing 

		//starting Main Menu activity before 5sec
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
					Intent MainMenu = new Intent("com.example.spiderapi.MENU");
					startActivity(MainMenu);
				}
			}
		};
		
		tTimer.start();
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		
		Player.release(); 	//stop playing song and release ram
		finish();      		//kill loading screen activity
	}
	
	
}
