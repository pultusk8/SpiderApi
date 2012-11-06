package com.example.spiderapi;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class CMenu extends Activity
{

	MediaPlayer Player;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_backg_sound);
		
		//seting up sound player
		Player = MediaPlayer.create(CMenu.this, R.raw.menu_sound);
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
		super.onPause();
		
		Player.release(); 	//stop playing song and release ram
		finish();      		//kill loading screen activity
	}
	
	
}
