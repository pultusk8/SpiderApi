package com.example.spiderapi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MsgMenager 
{
	static private String X = "";
	static private String Y = "";
	static private String Z = "";

	static public void OnUpdate(long timeDiff)
	{
		if(GFXSurface.spider != null)
		{
			X = "" + GFXSurface.spider.GetX();
			Y = "" + GFXSurface.spider.GetY();
			Z = "" + GFXSurface.spider.GetHealth();
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
		
		String temp = "Spider Position: " + X + ":" + Y;
		canvas.drawText(temp, 20, 300, paint);
	}
}
