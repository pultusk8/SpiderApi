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
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

enum EnumGameState 
{
	LoadingScreen,
	Game,
	InGameMenu,
	InGameSpiderStat,
	InGameWormShop,
	MainMenu
}

public class GFXSurface extends Activity implements OnTouchListener
{
	//Game Options
	//actual gamestate
	private static EnumGameState CurrentGameState = EnumGameState.Game;
	//Bool for end of game
	private static boolean IsRunning = false;
	
	//Screen Size variables
    private static int screenHeight;
    private static int screenWidth;	
    //Screen Size methods
    static int getScreenHeight() { return screenHeight; }
    static int getScreenWidth() { return screenWidth; }
    //  
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
			
		//Initialize Game
		
		//full screen before all shit happens
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//Create a surface
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
		setContentView(Surface);	
		
		//Get Screen Size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenHeight = size.y;
		screenWidth = size.x;
		//

		//Timer
		CurrentTime = LastCurrentTime = 0;
		StartTime = System.currentTimeMillis();
		
		// !!! All objects loading bitmaps need to be below this line !!!
		
		//Load BackGround
		
		//Load Buttons
		ButtonMenager.CreateButtons(CurrentGameState);	
		
		//Initialize AnimalMenager
		WormMenager.OnCreate();
		
		//Initialize Objects
		WormBox.OnCreate();
		pTerrarium = new Terrarium();
		spider = new Spider();
		
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
		
		InterfaceButton bButton = ButtonMenager.GetButtonOnPosition(fOnTouchX, fOnTouchY);
		
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
				
				if(spider != null && spider.IsOnPosition(fOnTouchX, fOnTouchY) && TouchedWorm == null)
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
				if(bButton != null) 
				{ 
					bButton.OnClickUp(); 
					bButton = null; 
					break; 
				} 
				
				
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
		
		public void OnDraw(Canvas canvas,Bitmap bitmap,float fPosX, float fPosY)
		{
			if(bitmap == null || canvas == null)
				return;
			
			canvas.drawBitmap(bitmap, fPosX, fPosY, null);
		}		
		
		public Bitmap LoadBitmap(int ObjectID, int AnimationDirection, int AnimationFrame)
		{		
			Bitmap bmpTemp = null;
			
			//Spider ID
			switch(ObjectID)
			{
				//Pajak Dybkowy
				case 0:
				{
					//AnimationDirection
					switch(AnimationDirection)
					{
						case 0:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}
						case 1:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}
						case 2:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}
						case 3:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}						
						case 4:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}	
						case 5:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}	
						case 6:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}	
						case 7:
						{
							//AnimationFrame
							switch(AnimationFrame)
							{
								case 0: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1); break;
								case 1: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l1a); break;
								case 2: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l2); break;
								case 3: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l3); break;
								case 4: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4); break;
								case 5: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l4a); break;
								case 6: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l5); break;
								case 7: bmpTemp = BitmapFactory.decodeResource(getResources(), R.drawable.l6); break;	
								default: break;
							}	
						}						
						default: break;
					}
				}
				//Next Spider
				case 1: break;
				
				default: break;
			}
			
			return bmpTemp;
		}
		
		public Bitmap LoadBitmap(int SpiderID, int BitmapID)
		{
			Bitmap bmp = null;
			
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
						case 0: break;
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
				//InGame Menu Button
				case 300: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ingame_button_menu); break;

				case 301: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ingame_button_spider); break;
				
				case 302: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ingame_button_wormbox); break;
				
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
				
			//int fpscounter = 0;
			//int fpstimer = 100;
			
			while(IsRunning)
			{	
				CurrentTime = System.currentTimeMillis();	
				long TimeDiff = CurrentTime - LastCurrentTime;
				LastCurrentTime = CurrentTime;
						
				if(currentthread == ThreadOne)
				{	
					if(!surfHolder.getSurface().isValid())
						continue;	
					
					Canvas canvas = surfHolder.lockCanvas();
					canvas.drawRGB(0, 0, 0);
									
					switch(CurrentGameState)
					{
					    case LoadingScreen:
					    {
					    	break;
					    }
						case Game:
						{
							if(pTerrarium != null)
								pTerrarium.OnDraw(canvas);		
							
							WormMenager.OnDraw(canvas);
							
							if(spider != null)	
								spider.OnDraw(canvas);
									
							//WormBox.OnDraw(canvas);
							break;
						}
						case InGameSpiderStat:
						{
							break;
						}
						case InGameMenu:
						{
							break;
						}
						case MainMenu:
						{
							break;
						}
						default: break;
					}					

					ButtonMenager.OnDraw(canvas);
					MsgMenager.OnDraw(canvas);					
					
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

	public static EnumGameState GetCurrentGameState() { return CurrentGameState; }
	
	public static void SetCurrentGameStatte(EnumGameState GameState) 
	{ 
		CurrentGameState = GameState; 
		
		//Zainicjuj Guziki 
		
		//Zainicjuj tlo
		
		//zainicjuj narzedzia danego stanu gry	
		
		//przy kazdej inicjajci kasacja dotychczasowego stanu
	}
	public static void QuitFromGame() 
	{
		IsRunning = false;
		//save staff
		//delete all
	}
}
