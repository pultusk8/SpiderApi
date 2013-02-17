package com.example.spiderapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener
{
	//Game Options
    static int screenHeight;
    static int screenWidth;	

	static SurfaceClass Surface 	= null;
	static Terrarium pTerrarium 	= null;
	
	static Spider spider    = null;
	WakeLock wL 			= null;
	
	boolean CanGetMoveOrders = true;
	
	long StartTime, CurrentTime;
	long LastCurrentTime;
	
	//Save Load Variables
	public static String filename = "SaveData";
	SharedPreferences Data = null;

	//OnTouch Actions
	boolean IsWormBoxTaken = false;
	Worm TouchedWorm = null;
	Spider TouchedSpider = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//Initialize AnimalMenager
		WormMenager.OnCreate();
		
		
		//Initialize Game
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		MsgMenager.AddMssage(0, width);
		MsgMenager.AddMssage(1, height);
		/*
		DisplayMetrics displaymetrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    screenHeight = displaymetrics.heightPixels;
	    screenWidth = displaymetrics.widthPixels;	*/	
		
		//Timer
		StartTime = CurrentTime = LastCurrentTime = 0;
		StartTime = System.currentTimeMillis();
		
		//Create a surface
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
		setContentView(Surface);
		
		//Initialize Objects
		pTerrarium = new Terrarium();
		spider = new Spider();
		WormBox.OnCreate();

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
		
		if(IsWormBoxTaken)
			WormBox.SetPosition(fOnTouchX, fOnTouchY);
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				TouchedWorm = WormMenager.GetWorm(fOnTouchX, fOnTouchY);
				
				if(TouchedWorm != null)
				{
					TouchedWorm.SetIsInWormBox(false);
				}
				
				if(spider.IsOnPosition(fOnTouchX, fOnTouchY) && TouchedWorm == null)
				{
					TouchedSpider = spider;
					TouchedSpider.SetMovementFlag(3);
				}
				
				//if(TouchedSpider == null &&  TouchedWorm == null)
					//if(WormBox.IsOnPosition(fOnTouchX, fOnTouchY))
						//IsWormBoxTaken = true;
				
				break;
			}
			
			case MotionEvent.ACTION_UP:
			{
				if(WormBox.IsOnPosition(fOnTouchX, fOnTouchY))
				{
					if(WormBox.IsEmpty() && TouchedWorm == null)
					{
						//create new content activity with shop 
						Worm worm = new Worm();
						worm.SetIsInWormBox(true);
					}
					
					if(TouchedWorm != null)
					{
						TouchedWorm.SetIsInWormBox(true);				
					}
				}				
				
				IsWormBoxTaken = false;
				TouchedWorm = null;
				TouchedSpider = null;
				spider.SetMovementFlag(1);
				
				if(CanGetMoveOrders)
					spider.SetUpWaypoint(fOnTouchX, fOnTouchY, 0);
				break;
			}
			default:
				break;
		}

		return true;
	}
	
	static public SurfaceClass GetSurface() { return Surface; }
	public static Terrarium GetTerrarium() { return pTerrarium; }
	
	public class SurfaceClass extends SurfaceView implements Runnable
	{
		private SurfaceHolder surfHolder = null;
		private Thread ThreadOne = null;
		private Thread ThreadTwo = null;
		private boolean IsRunning = false;
	
		public void OnDraw(Canvas canvas,Bitmap bitmap,float fPosX, float fPosY)
		{
			if(bitmap == null || canvas == null)
				return;
			
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
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
						case 1: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
						case 2: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
						case 3: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
						case 4: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
						case 5: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
						case 6: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
						case 7: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
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
						case 1: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.wormbox01addworm); break;
						
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
				
			int fpscounter = 0;
			int fpstimer = 100;
			
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
							
					WormBox.OnDraw(canvas);
					
					WormMenager.OnDraw(canvas);
					
					MsgMenager.OnDraw(canvas);
					
					/*
			    	if(fpstimer < TimeDiff)
			    	{
			    		//MsgMenager.AddMssage(1,fpscounter);
			    		fpstimer = 1000;
			    		fpscounter = 0;
			    	}fpstimer -= TimeDiff;
					
			    	++fpscounter;	*/				
					
					surfHolder.unlockCanvasAndPost(canvas);	
				}	
				
				if(currentthread == ThreadTwo)
				{		
					MsgMenager.OnUpdate(TimeDiff);
					
					try
					{
						float FrameRate = 60;
						float PauseTime = 1000 / FrameRate;
						//MsgMenager.AddMssage(0,(int) PauseTime);
						Thread.sleep((long) PauseTime);	
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}						
						
					if(spider != null)		
						spider.OnUpdate(TimeDiff);
					
					WormBox.OnUpdate(TimeDiff);
					
					WormMenager.OnUpdate(TimeDiff);
				}						
			}
		}
	}
}
