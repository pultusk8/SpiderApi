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

	private int TerrariumID = 20;
	private int TerrBitmapID = 0;
	
	public Terrarium()
	{
		this.bitmap = GFXSurface.GetSurface().LoadBitmap(TerrariumID, TerrBitmapID);
		this.bitmap = Bitmap.createScaledBitmap(bitmap, GFXSurface.getScreenWidth(), GFXSurface.getScreenHeight() - (int)WormBox.GetHeight(), false);
		
		fWidth = bitmap.getWidth();
		fHeight = bitmap.getHeight();
	
		MsgMenager.AddMssage(2, (int) fWidth);
		MsgMenager.AddMssage(3, (int) fHeight);
	}	
	
	public int GetWidth() {return fWidth; }
	public int GetHeight() {return fHeight; }	
	
	public void OnDraw(Canvas canvas)
	{
		GFXSurface.GetSurface().OnDraw(canvas, bitmap, fPosX, fPosY);
	}
}
