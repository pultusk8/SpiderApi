package com.example.spiderapi;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener
{
	SurfaceClass Surface = null;
	
	Terrarium pTerrarium = null;
	Spider spider = null;
	Worm worm = null;
	
	boolean CanGetMoveOrders = true;
	WakeLock wL =  null;
	
	long StartTime, CurrentTime;
	long LastCurrentTime;
	
	//Save Load Variables
	public static String filename = "SaveData";
	SharedPreferences Data = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//Timer
		StartTime = CurrentTime = LastCurrentTime = 0;
		StartTime = System.currentTimeMillis();
		
		super.onCreate(savedInstanceState);
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
		setContentView(Surface);
		
		
		pTerrarium = new Terrarium(Surface);
		spider = new Spider(Surface, pTerrarium);
		worm = new Worm(Surface, pTerrarium);
		worm.SetPosition(33,66);
		
		//wakelock
		PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "whatever");
		wL.acquire();
		
		//save load 
		Data = getSharedPreferences(filename, 0);
		
		OnLoad();
	}

	private void OnSave()
	{
		if(spider == null)
			return;
		
		long TimeSaved = System.currentTimeMillis();
		String SpiderName = "Spider";
				
		float X = spider.GetX();
		float Y = spider.GetY();

		SharedPreferences.Editor Editor = Data.edit();
		Editor.putString("Name", SpiderName);
		Editor.putLong("Time", TimeSaved);
		Editor.putFloat("X", X);
		Editor.putFloat("Y", Y);
		
		Editor.commit();	
	}
	
	private void OnLoad()
	{
		if(spider == null)
			return;		
		
		Data = getSharedPreferences(filename, 0);
		String LoadedString = Data.getString("Name", "no string at this key");
		long LoadedTime = Data.getLong("Time", 0);
		float x = Data.getFloat("X", 0);
		float y = Data.getFloat("Y", 0);
		spider.SetPosition(x, y);	
	}
	
	@Override
	protected void onPause() 
	{
		OnSave();
		super.onPause();
		Surface.pause();
		wL.release();	
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		Surface.resume();
		wL.acquire();
	}

	//called when u touch the screen with this activity opened
	public boolean onTouch(View v, MotionEvent event)
	{		
		float fOnTouchX = event.getX();
		float fOnTouchY = event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:		
				break;
			case MotionEvent.ACTION_UP:
				if(CanGetMoveOrders)
					spider.SetUpWaypoint(fOnTouchX, fOnTouchY, 0);
				break;
		}

		return true;
	}
	
	public class SurfaceClass extends SurfaceView implements Runnable
	{
		SurfaceHolder surfHolder;
		Thread ThreadOne = null;
		Thread ThreadTwo = null;
		boolean IsRunning = false;

		Bitmap test = null;
	
		public void OnDraw(Canvas canvas,Bitmap bitmap,float fPosX, float fPosY)
		{
			canvas.drawBitmap(bitmap, fPosX, fPosY, null);
		}		
		
		public Bitmap LoadBitmap(int SpiderID, int BitmapID)
		{
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spider);
			switch(SpiderID)
			{
				//Spider
				case 0:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spider); break;
						case 1: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spider02); break;
						default: break;
					}
					break;
				}
				case 1:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spider); break;
						case 1: break;
						default: break;
					}
					break;					
				}
				//Test Worm
				case 10:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.worm); break;
						default: break;
					}
					break;	
				}
				//Terrarium
				case 20:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.terrarium01); break;
						default: break;
					}
					break;		
				}
				default: break;
			}
			
			return bmp; 
		}		
		
		public SurfaceClass(Context context)
		{
			super(context);
			surfHolder = getHolder();	
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
			Thread currentthread = Thread.currentThread();
			if(currentthread == null)
				return;
						
			while(IsRunning)
			{		
				CurrentTime = System.currentTimeMillis();	
				long TimeDiff = CurrentTime - LastCurrentTime;
				LastCurrentTime = CurrentTime;
								
				if(currentthread == ThreadOne)
				{	
					if(!surfHolder.getSurface().isValid())
						continue;
					
					//draw background
					Canvas canvas = surfHolder.lockCanvas();
					canvas.drawRGB(0, 254, 0);
									
					if(pTerrarium != null)
						pTerrarium.OnDraw(canvas);		
					
					if(spider != null)	
					{	
						spider.OnDraw(canvas);
					}							
			
					if(worm != null)
						worm.OnDraw(canvas);
					
					surfHolder.unlockCanvasAndPost(canvas);					
				}	
				
				if(currentthread == ThreadTwo)
				{				
					try
					{
						float FrameRate = 60;
						float PauseTime = 1000 / FrameRate;
						Thread.sleep((long) PauseTime);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}						
						
					if(spider != null)	
					{	
						spider.OnUpdate(TimeDiff);
					}											
				}						
			}
		}
		
		public Worm GetWormFromTerrarium()
		{
			if(worm.GetX() > 0 && worm.GetX() < pTerrarium.GetX())
			{
				if(worm.GetY() > 0 && worm.GetY() < pTerrarium.GetY())
				{
					if(!worm.IsDeath)
						return worm;
				}
			}
			return null;
		}	
		
		public void CreateNewWorm()
		{
			worm = new Worm(Surface, pTerrarium);
			Random r = new Random();
			
			worm.SetPosition(r.nextInt(200), r.nextInt(300));
		}
	}
}
