package com.example.spiderapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener
{
	SurfaceClass Surface;
	float x, y;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
		setContentView(Surface);
		x = y = 0.0f;
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		Surface.pause();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		Surface.resume();
	}

	public boolean onTouch(View v, MotionEvent event)
	{
		x = event.getX();
		y = event.getY();
		return true;
	}
	
	public class SurfaceClass extends SurfaceView implements Runnable
	{
		SurfaceHolder surfHolder;
		Thread ThreadOne = null;
		Thread ThreadTwo = null;
		boolean IsRunning = false;
		int goX, goY;
		int timer;
		Bitmap test;
		boolean GoForward;
		int FPSCounter;
		
		public SurfaceClass(Context context)
		{
			super(context);
			surfHolder = getHolder();
			
			goX = goY = 50;
			GoForward = false;
			test = BitmapFactory.decodeResource(getResources(), R.drawable.spider);
			timer = 0;
			FPSCounter = 0;
		}

		public void pause()
		{
			IsRunning = false;
			while(true)
			{
				try
				{
					ThreadOne.join();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				break;
			}
			ThreadOne = null;
		}
		
		public void resume()
		{
			IsRunning = true;
			ThreadOne = new Thread(this);
			ThreadOne.start();
			ThreadTwo = new Thread(this);
			ThreadTwo.start();
		}
		
		public void run() 
		{
			Spider spider = new Spider();
			Thread currentthread = Thread.currentThread();
			if(currentthread == null)
				return;
			
			while(IsRunning)
			{				
				if(currentthread == ThreadOne)
				{	
					if(!surfHolder.getSurface().isValid())
						continue;
					
					Canvas canvas = surfHolder.lockCanvas();
					canvas.drawRGB(23, 56, 68);
					spider.OnDraw(canvas);
							
					//if(x != 0 && y != 0)
					//{
						
						canvas.drawBitmap(test, goX, goY, null);
					//}	
					surfHolder.unlockCanvasAndPost(canvas);
					
					{
						if(timer > 60)
						{
							if(GoForward)
							{				
								if(goY < 300)
									goY++;
								
								if(goX < 300)
									goX++;
								
								if(goX > 100 || goY > 400)
									GoForward = false;
							}
							else
							{				
								goY--;
								goX--;
								
								if(goX < 0 || goY < 0)
									GoForward = true;
							}
						}
						else
						{
							if(GoForward)
							{				
								if(goY < 300)
									goY--;
								
								if(goX < 300)
									goX++;
								
								if(goX > 100 || goY > 400)
									GoForward = false;
							}
							else
							{				
								goY++;
								goX--;
								
								if(goX < 0 || goY < 0)
									GoForward = true;
							}					
						}
						timer++;
						
						if(timer > 120)
							timer = 0;						
				}

				
					/*
				else if(currentthread == ThreadTwo)
				{
					if(timer > 60)
					{
						if(GoForward)
						{				
							if(goY < 300)
								goY++;
							
							if(goX < 300)
								goX++;
							
							if(goX > 100 || goY > 400)
								GoForward = false;
						}
						else
						{				
							goY--;
							goX--;
							
							if(goX < 0 || goY < 0)
								GoForward = true;
						}
					}
					else
					{
						if(GoForward)
						{				
							if(goY < 300)
								goY--;
							
							if(goX < 300)
								goX++;
							
							if(goX > 100 || goY > 400)
								GoForward = false;
						}
						else
						{				
							goY++;
							goX--;
							
							if(goX < 0 || goY < 0)
								GoForward = true;
						}					
					}
					timer++;
					
					if(timer > 120)
						timer = 0;
					
					/*
					if(FPSCounter > 60)
					{
						try
						{ 
							int temp = 1000 - FPSCounter;
							ThreadTwo.sleep(temp);
							FPSCounter= 0;
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						finally
						{
							
						}		
					}
					FPSCounter++;	
					*/
					
				}				
			}
		}
	}
}
