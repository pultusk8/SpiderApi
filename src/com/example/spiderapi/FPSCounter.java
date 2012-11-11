package com.example.spiderapi;

import android.app.Activity;

public class FPSCounter extends Activity
{
	FPSCounter()
	{
		ThreadOne = new Thread();
		fps = 0;
		ThreadOne.start();
	}
	
	int fps;
	Thread ThreadOne;
	
	public int GetFPS()
	{

		return fps;
	}
	
	public void run()
	{
		boolean IsRunning = true;
		while(IsRunning)
		{
			if(fps > 59)
				fps++;
		}
	
	}
}
