package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Terrarium 
{
	private float fPosX = 0;
	private float fPosY = 0;
	private int X = 325;
	private int Y = 521;
	
	private Bitmap bitmap = null;
	private SurfaceClass Surface = null;
	
	private int TerrariumID = 20;
	private int TerrBitmapID = 0;
	
	public Terrarium(SurfaceClass Surface)
	{
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(TerrariumID, TerrBitmapID);
		Y = bitmap.getHeight();
		X = bitmap.getWidth();
	}	
	
	public int GetX() {return X; }
	public int GetY() {return Y; }	
	
	public void OnDraw(Canvas canvas)
	{
		Surface.OnDraw(canvas, bitmap, fPosX, fPosY);
	}
}
