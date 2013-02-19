package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class InterfaceButton 
{
	private int nPositionX = 0;
	private int nPositionY = 0;
	private int nHeight = 0;
	private int nWidth = 0;
	private int nButtonID= 0;
	private int nBitmapID= 0;
	private Bitmap bmpBitmap = null;
	
	public InterfaceButton(int ButtonID)
	{
		nButtonID = ButtonID;
		nBitmapID = ButtonBitmapIDTable[nButtonID-300];
		OnCreate();
	}
	
	public void OnCreate()
	{			
		bmpBitmap = GFXSurface.GetSurface().LoadBitmap(nBitmapID);
			
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
				nPositionY = GFXSurface.getScreenHeight() - nHeight;
				break;
			
			case 301:
				nPositionX = nWidth;
				nPositionY = GFXSurface.getScreenHeight() - nHeight;
				break;
				
			case 302:
				nPositionX = nWidth*2;
				nPositionY = GFXSurface.getScreenHeight() - nHeight;
				break;
				
			case 303:
				nPositionX = 200;
				nPositionY = 400;
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
					GFXSurface.SetCurrentGameStatte(EnumGameState.InGameMenu);
				break;
			case 301:
				if(GFXSurface.GetCurrentGameState() == EnumGameState.InGameSpiderStat)
					GFXSurface.SetCurrentGameStatte(EnumGameState.Game);
				else
					GFXSurface.SetCurrentGameStatte(EnumGameState.InGameSpiderStat);			
				break;
			case 303:
				if(GFXSurface.GetCurrentGameState() == EnumGameState.MainMenu)
					GFXSurface.SetCurrentGameStatte(EnumGameState.Game);		
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
	
	private int ButtonBitmapIDTable[] =
	{
		//bitmapid, posX, posY
		R.drawable.ingame_button_menu, 
		R.drawable.ingame_button_spider, 
		R.drawable.ingame_button_wormbox, 
		R.drawable.mainmenu_button_play, 

	};
}
