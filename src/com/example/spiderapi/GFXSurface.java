package com.example.spiderapi;

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
	//Media Player for all items in apliaction
	/*
	 * DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    int screenHeight = displaymetrics.heightPixels;
    int screenWidth = displaymetrics.widthPixels;
	 */
	
	
	SurfaceClass Surface = null;
	
	Terrarium pTerrarium = null;
	Spider spider = null;
	WormBox wormbox = null;
	WormMenager WormMgr = null;
	
	boolean CanGetMoveOrders = true;
	WakeLock wL =  null;
	
	long StartTime, CurrentTime;
	long LastCurrentTime;
	
	//Save Load Variables
	public static String filename = "SaveData";
	SharedPreferences Data = null;

	//OnTouch Actions
	WormBox TouchedWormBox = null;
	Worm TouchedWorm = null;
	Spider TouchedSpider = null;
	
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
		
		WormMgr = new WormMenager(Surface, pTerrarium);
		pTerrarium = new Terrarium(Surface);
		spider = new Spider(Surface, pTerrarium, WormMgr);
		wormbox = new WormBox(Surface, pTerrarium);

		//wakelock
		PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "whatever");
		wL.acquire();
		
		//save load 
		Data = getSharedPreferences(filename, 0);
	}

	private void OnSave()
	{
		if(spider == null)
			return;
				
		float X = spider.GetX();
		float Y = spider.GetY();

		SharedPreferences.Editor Editor = Data.edit();
		Editor.putFloat("X", X);
		Editor.putFloat("Y", Y);
		
		Editor.commit();	
	}
	
	private void OnLoad()
	{
		if(spider == null)
			return;		
		
		Data = getSharedPreferences(filename, 0);
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
		wL.release(); //wakelock	 
	}

	@Override
	protected void onResume() 
	{
		OnLoad();
		super.onResume();
		Surface.resume();
		wL.acquire(); //wakelock
	}

	//called when u touch the screen with this activity opened
	public boolean onTouch(View v, MotionEvent event)
	{		
		float fOnTouchX = event.getX();
		float fOnTouchY = event.getY();
		
		if(TouchedWorm != null)
			TouchedWorm.SetPosition(fOnTouchX, fOnTouchY);
		
		if(TouchedSpider != null)
			TouchedSpider.SetPosition(fOnTouchX, fOnTouchY);		
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				TouchedWorm = WormMgr.GetWorm(fOnTouchX, fOnTouchY);
					
				if(spider.IsOnPosition(fOnTouchX, fOnTouchY))
				{
					TouchedSpider = spider;
					TouchedSpider.SetMovementFlag(1);
				}
				
				if(wormbox.IsOnPosition(fOnTouchX, fOnTouchY))
				{
					TouchedWorm = wormbox.GetWorm();
					WormMgr.AddWorm(TouchedWorm);
				}
				break;
			}
			
			case MotionEvent.ACTION_UP:
			{
				TouchedWorm = null;
				TouchedSpider = null;
				spider.SetMovementFlag(0);
				
				if(CanGetMoveOrders)
					spider.SetUpWaypoint(fOnTouchX, fOnTouchY, 0);
				break;
			}
			default:
				break;
		}

		return true;
	}
	
	public class SurfaceClass extends SurfaceView implements Runnable
	{
		private SurfaceHolder surfHolder = null;
		private Thread ThreadOne = null;
		private Thread ThreadTwo = null;
		private boolean IsRunning = false;
	
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
				//worm box
				case 30:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.wormbox01); break;
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
					canvas.drawRGB(0, 0, 0);
					
					if(pTerrarium != null)
						pTerrarium.OnDraw(canvas);		
					
					if(spider != null)	
						spider.OnDraw(canvas);
							
					if(wormbox != null)
						wormbox.OnDraw(canvas);
					
					if(WormMgr != null)
						WormMgr.OnDraw(canvas);
					
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
						spider.OnUpdate(TimeDiff);
					
					if(wormbox != null)
						wormbox.OnUpdate(TimeDiff);
				}						
			}
		}
	}
}
