package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Terrarium 
{
	private float fPosX = 0;
	private float fPosY = 0;
	private int fWidth = 400;
	private int fHeight = 600;
	
	private Bitmap bitmap = null;

	private int TerrBitmapID = R.drawable.terrarium01;
	
	public Terrarium()
	{
		this.bitmap = GFXSurface.GetSurface().LoadBitmap(TerrBitmapID);
		
		if(bitmap == null) return;
			
		this.bitmap = Bitmap.createScaledBitmap(bitmap, GFXSurface.getScreenWidth(), (int) (GFXSurface.getScreenHeight() - WormBox.GetHeight()), true);
		
		fWidth = bitmap.getWidth();
		fHeight = bitmap.getHeight();
	}	
	
	public int GetWidth() {return fWidth; }
	public int GetHeight() {return fHeight; }	
	
	public void OnDraw(Canvas canvas)
	{
		GFXSurface.GetSurface().OnDraw(canvas, bitmap, fPosX, fPosY);
	}
}
