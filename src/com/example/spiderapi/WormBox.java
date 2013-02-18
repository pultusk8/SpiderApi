package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class WormBox 
{
	private static float fPosX = 0;
	private static float fPosY = 0;
	private static float fHeight = 100;
	private static float fWidth = 100;

	private static Bitmap bmpEmptyBox = null;
	private static Bitmap bmpBox = null;
	private static Bitmap bmpOnDrawReady = null;
	
	private static boolean IsBoxEmpty = true;
		
	public static void OnCreate()
	{
		SurfaceClass Surface = GFXSurface.GetSurface();
		bmpBox = Surface.LoadBitmap(30, 0);	
		bmpEmptyBox = Surface.LoadBitmap(30, 1);
		
		bmpBox = Bitmap.createScaledBitmap(bmpBox, GFXSurface.getScreenWidth()/3, GFXSurface.getScreenHeight()/15, true);
		bmpEmptyBox = Bitmap.createScaledBitmap(bmpEmptyBox, GFXSurface.getScreenWidth()/3, GFXSurface.getScreenHeight()/15, true);
		
		fWidth = bmpBox.getWidth();
		fHeight = bmpBox.getHeight();
		fPosX = GFXSurface.getScreenWidth() - fWidth;
		fPosY = GFXSurface.getScreenHeight() - fHeight - 100;
		
		MsgMenager.AddMssage(0, (int) fPosX);
		MsgMenager.AddMssage(1, (int) fPosY);
		MsgMenager.AddMassage(2,"Wormbox Wigth: " + fWidth + "");
		MsgMenager.AddMassage(3,"Wormbox Height: " + fHeight + "");
	}
	
	public static boolean IsOnPosition(float fOnTouchX, float fOnTouchY)
	{
	    if( ( fOnTouchX > fPosX ) && ( fOnTouchX < fPosX + fWidth) && ( fOnTouchY > fPosY ) && ( fOnTouchY < fPosY + fHeight ) )
	        return true;

	    return false;
	}
	
	public static boolean IsEmpty()
	{
		return IsBoxEmpty;
	}
	
	public static void OnUpdate(long diff)
	{		
		IsBoxEmpty = true;
		
		for(int i=0; i<WormMenager.GetWormsNumber(); ++i)
		{
			if(WormMenager.GetWormOnListPosition(i) != null)
			{
				if(WormMenager.GetWormOnListPosition(i).IsInWormBox() == true)
					IsBoxEmpty = false;
			}		
		}
		
		if(IsBoxEmpty == true)
			bmpOnDrawReady = bmpEmptyBox;
		else 
			bmpOnDrawReady = bmpBox;
	}
	
	public static void OnDraw(Canvas canvas) 
	{		
		if(bmpOnDrawReady != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpOnDrawReady, fPosX, fPosY);
	}	
	
	public static float GetPositionX() { return fPosX; }
	public static float GetPositionY() { return fPosY; }
	public static float GetHeight() { return fHeight; }
	public static float GetWidth() { return fWidth; }
	
	public static void SetPosition(float fOnTouchX, float fOnTouchY) { fPosX = fOnTouchX; fPosY = fOnTouchY; }
}
