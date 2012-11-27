package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Terrarium 
{
	private float fPosX = 0;
	private float fPosY = 0;
	private int X = 400;
	private int Y = 600;
	
	private Bitmap bitmap = null;

	private int TerrariumID = 20;
	private int TerrBitmapID = 0;
	
	public Terrarium()
	{
		this.bitmap = GFXSurface.GetSurface().LoadBitmap(TerrariumID, TerrBitmapID);
		Y = bitmap.getHeight();
		X = bitmap.getWidth();
	}	
	
	public int GetX() {return X; }
	public int GetY() {return Y; }	
	
	public void OnDraw(Canvas canvas)
	{
		GFXSurface.GetSurface().OnDraw(canvas, bitmap, fPosX, fPosY);
	}
}
