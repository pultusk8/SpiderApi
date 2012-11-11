package com.example.spiderapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class GFXSurf extends SurfaceView
{
	public GFXSurf(Context context) 
	{
		super(context);
	}

	static public void OnDraw(Canvas canvas,Bitmap bitmap,int posX, int posY)
	{
		canvas.drawBitmap(bitmap, posX, posY, null);
	}
	
	static public Bitmap LoadBitmap(String bitmapname)
	{
		Bitmap temp = null;
		//temp = BitmapFactory.decodeResource(getResources(), R.drawable.bitmapname);
		return temp;
	}
	
}
