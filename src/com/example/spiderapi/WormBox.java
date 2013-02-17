package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class WormBox 
{
	private static float fPosX = 500;
	private static float fPosY = 850;
	private static float fHeight = 100;
	private static float fWidth = 100;

	private static Bitmap emptyBox = null;
	private static Bitmap Box = null;
	private static Bitmap bitmap = null;
	
	private static boolean IsBoxEmpty = true;
		
	public static void OnCreate()
	{
		SurfaceClass Surface = GFXSurface.GetSurface();
		Box = Surface.LoadBitmap(30, 0);	
		emptyBox = Surface.LoadBitmap(30, 1);
		
		fPosX = 100;
		fPosY = 100;
		fHeight = Box.getHeight();
		fWidth = Box.getWidth();		
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
			bitmap = emptyBox;
		else 
			bitmap = Box;
	}
	
	public static void OnDraw(Canvas canvas) 
	{		
		if(bitmap != null)
			GFXSurface.GetSurface().OnDraw(canvas, bitmap, fPosX, fPosY);
	}	
	
	public static float GetX() { return fPosX; }
	public static float GetY() { return fPosY; }
	public static float GetH() { return fHeight; }
	public static float GetW() { return fWidth; }
	
	public static void SetPosition(float fOnTouchX, float fOnTouchY) { fPosX = fOnTouchX; fPosY = fOnTouchY; }
}
