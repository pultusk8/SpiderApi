package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class InterfaceButton 
{
	private static int nPositionX = 0;
	private static int nPositionY = 0;
	private static int nHeight = 0;
	private static int nWidth = 0;
	private static int nButtonID= 0;
	private static int nBitmapID= 0;
	private static Bitmap bmpBitmap = null;
	
	public InterfaceButton(int ButtonID)
	{
		nButtonID = ButtonID;
		nBitmapID = nButtonID;
		OnCreate();
	}
	
	public void OnCreate()
	{			
		SurfaceClass teampSurface = GFXSurface.GetSurface();
		bmpBitmap = teampSurface.LoadBitmap(nButtonID, nBitmapID);
		
		if(bmpBitmap == null)
			return;
		
		ScaleBitmap();

		nWidth = bmpBitmap.getWidth();
		nHeight = bmpBitmap.getHeight();

		SetUpPosition();
			
		ButtonMenager.AddButton(this);
	}

	private void SetUpPosition() 
	{
		switch(nButtonID)
		{
			case 300:
				nPositionX = 0;
				nPositionY = GFXSurface.getScreenHeight() - nHeight - 100;
				break;
			
			case 301:
				nPositionX = nWidth*2;
				nPositionY = GFXSurface.getScreenHeight() - nHeight - 100;
				break;
				
			case 302:
				nPositionX = nWidth*3;
				nPositionY = GFXSurface.getScreenHeight() - nHeight - 100;
				break;
			
			default: break;
		}
	}

	private void ScaleBitmap() 
	{		
		switch(nButtonID)
		{
			case 300:
			case 301:
			case 302:
				bmpBitmap = Bitmap.createScaledBitmap(bmpBitmap, GFXSurface.getScreenWidth()/3, GFXSurface.getScreenHeight()/15, true);
				break;
				
			default: break;
		}
	}

	public void OnDraw(Canvas canvas) 
	{
		if(bmpBitmap != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpBitmap, nPositionX, nPositionY);	
	}

	public void OnClickMove()
	{
		switch(nButtonID)
		{		
			default: break;
		}		
	}
	
	public void OnClickUp() 
	{
		switch(nButtonID)
		{
			case 300: 
				if(GFXSurface.GetCurrentGameState() == EnumGameState.InGameMenu)
					GFXSurface.SetCurrentGameStatte(EnumGameState.Game);
				else
					GFXSurface.CurrentGameState = EnumGameState.InGameMenu;
				break;
			
			default: break;
		}
	}
	
	public void OnClickDown()
	{		
		switch(nButtonID)
		{	
			default: break;
		}		
	}

	public boolean IsOnPosition(float positionX, float positionY) 
	{
	    if( ( positionX > nPositionX ) && ( positionX < nPositionX + nWidth) && ( positionY > nPositionY ) && ( positionY < nPositionY + nHeight ) )
	        return true;

	    return false;
	}
}
