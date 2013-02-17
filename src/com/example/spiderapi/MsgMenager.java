package com.example.spiderapi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MsgMenager 
{
	static private String X = "";
	static private String Y = "";
	static private String Z = "";
	static private String fps = "";
	static private String fps1 = "";
	static private int fpsnumber[] = { 0,0 };

	static public void AddMssage(int msgtype, int Fps)
	{
		fpsnumber[msgtype] = Fps;
	}
	
	static public void OnUpdate(long timeDiff)
	{
		if(GFXSurface.spider != null)
		{
			X = "" + GFXSurface.spider.GetX();
			Y = "" + GFXSurface.spider.GetY();
			Z = "" + GFXSurface.spider.GetHealth();
			fps = "" + fpsnumber[0];
			fps1 = "" + fpsnumber[1];
		}
	}
	
	static public void OnDraw(Canvas canvas)
	{
		//Text Testing
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE);
		paint.setTextSize(20); 
		
		String temp1 = "Spider Health: " + Z;
		canvas.drawText(temp1, 20, 375, paint);		
		
		String temp = "Spider Position: " + X + " : " + Y;
		canvas.drawText(temp, 20, 300, paint);
		
		String temp2 = "FPS: " + fps;
		canvas.drawText(temp2, 20, 600, paint);
		
		String temp3 = "FPS: " + fps1;
		canvas.drawText(temp3, 20, 630, paint);
	}
}
