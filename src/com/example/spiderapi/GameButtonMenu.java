package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameButtonMenu
{
	private static int nPositionX = 0;
	private static int nPositionY = 0;
	private static int nHeight = 0;
	private static int nWidth = 0;
	private static int nObjectID= 40;
	private static int nBitmapID= 0;
	private static Bitmap bmpBitmapTable[] = { null, null, null };
	
	public static void OnCreate()
	{
		SurfaceClass teampSurface = GFXSurface.GetSurface();
		bmpBitmapTable[0] = teampSurface.LoadBitmap(nObjectID, nBitmapID);
		
		if(bmpBitmapTable[0] != null)
		bmpBitmapTable[0] = Bitmap.createScaledBitmap(bmpBitmapTable[0], GFXSurface.getScreenWidth()/3, GFXSurface.getScreenHeight()/15, true);
		
		if(bmpBitmapTable[0] != null)
		{
			nWidth = bmpBitmapTable[0].getWidth();
			nHeight = bmpBitmapTable[0].getHeight();
		}
		
		nPositionX = 0;
		nPositionY = GFXSurface.getScreenHeight() - nHeight - 100;
	}
	
	public void OnClick()
	{		
		GFXSurface.CurrentGameState = EnumGameState.InGameMenu;
	}

	public InterfaceObject GetInterface(int PositionX, int PositionY)
	{		
		if(GFXSurface.CurrentGameState == EnumGameState.Game)
			//return super.GetInterface(PositionX, PositionY);
		
			return null;
		return null;
	}
	
	public static void OnDraw(Canvas canvas) 
	{
		if(bmpBitmapTable[0] != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpBitmapTable[0], nPositionX, nPositionY);	
	}
}
