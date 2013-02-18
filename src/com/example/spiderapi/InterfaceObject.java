package com.example.spiderapi;

import android.graphics.Bitmap;

public class InterfaceObject 
{
	protected static int nPositionX;
	protected static int nPositionY;
	protected static int nHeight;
	protected static int nWidth;
	protected static int nObjectID;
	protected static int nBitmapID;
	protected static Bitmap bmpBitmapTable[] = { null, null, null };
	
	public InterfaceObject()
	{
		
	}
	
	public static void OnCreate()
	{
		/*
		*for(int i=0; i<3; ++i)
		{
			bmpBitmapTable[i] = GFXSurface.GetSurface().LoadBitmap(nObjectID, 0);	
		}
		*/
	}
	
	public InterfaceObject GetInterface(int PositionX, int PositionY)
	{
		if( ( PositionX > nPositionX-(nWidth/2) ) && ( PositionX < nPositionX + (nWidth/2)) && ( PositionY > nPositionY - (nHeight/2) ) && ( PositionY < nPositionY + (nHeight/2) ) )	
			return this;

		return null;
	}	

	public void OnClick() 
	{
		
	}
}
