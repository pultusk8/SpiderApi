package com.example.spiderapi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MsgMenager
{
	//Text Settings
	private static int TextSizeLoadingScreen = 20;
	
	
	private static String stringtable[] = { null, null, null, null };
	//private static String X = "";
	//private static String Y = "";
	//private static int Z = "";
	private static int fpsnumber[] = { 0,0,0,0 };
	
	private static String LoadingInformation = "Loading ... ";
	private static String TerrariumInformation[] = { "", "", "" };
	private static String WormInBoxNumber = "Worm = ";
	
	static public void OnUpdate(long timeDiff)
	{
		
	}
	
	public static void OnDraw(Canvas canvas)
	{
		//Text Testing
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE);
		paint.setTextSize(TextSizeLoadingScreen); 
		
		switch(GameCore.GetCurrentGameState())
		{
		    case LoadingScreen:
		    {
				canvas.drawText(LoadingInformation, 10, GameCore.GetGraphicEngine().getHeight() - TextSizeLoadingScreen , paint);		
		    	break;
		    }
			case Game:
			{		
				canvas.drawText(WormInBoxNumber, GameCore.GetGraphicEngine().getWidth() - GameCore.GetGraphicEngine().getWidth()/3, GameCore.GetGraphicEngine().getHeight() - TextSizeLoadingScreen , paint);	
				break;
			}
			case InGameMenu:
			{
				break;
			}
			
			case InGameSpiderStat:
			{
				Animal animal = new Animal();
				String temp1 = "Spider Health: " + animal.Health + "%";
				canvas.drawText(temp1, 20, 525, paint);	
				
				String Hungry = "Spider Hungry: " + animal.HungryTimer;
				canvas.drawText(Hungry, 20, 550, paint);	
				
				String temp = "Spider Position: " + animal.GetX() + " : " + animal.GetY();
				canvas.drawText(temp, 20, 575, paint);
				
				for(int i=0; i<3; ++i)
				{
					canvas.drawText(TerrariumInformation[i], 20, 600 + i*25, paint);
				}
				
				break;
			}
			case MainMenu:
			{
				break;
			}
			
			case MainMenuDevelopers:
			{
				canvas.drawText("Genik", 150, 450, paint);		
				canvas.drawText("Brzuzek", 150, 575, paint);
				
				break;
			}
			
			case MainMenuOptions:
			{
				canvas.drawText("W Chuj Opcji", 150, 575, paint);
				break;
			}
			
			default: break;
		}	
		
		canvas.drawText(DataMenager.GetGameVersion(), 10, 20, paint);		
	}
	
	static public void AddMssage(int msgtype, int Fps)
	{
		fpsnumber[msgtype] = Fps;
	}	

	public static void AddLoadingInfo(int msgtype, String string) 
	{
		switch(msgtype)
		{
			case 0: LoadingInformation = string;
			default: break;
		}
		
		stringtable[msgtype] = string;
	}

	public static void AddTerrariumInfo(int msgtype, String string) 
	{
		TerrariumInformation[msgtype] = string;		
	}
	
	public static void AddWormBoxInfo(String string) 
	{
		WormInBoxNumber = "Worms = " + string;
	}
}
