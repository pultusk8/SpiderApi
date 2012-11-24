package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;

enum UnitFlag
{
	UNIT_FLAG_VISIBLE, // 0x0002	
}

enum MovementFlag
{
	UNIT_MOVE_NOTMOVING, // 0x0002
	UNIT_MOVE_MOVING,    // 0x0020	
}

public class Animal 
{	
	protected int ObjectID = 0;
	protected int BitmapID = 0;
	
	protected int MovementFlag = 0;
	protected int UnitFlag = 0;
	
	protected float fPosX = 50.0f;
	protected float fPosY = 50.0f;
	protected float fHeight = 0.0f;
	protected float fWidth = 0.0f;
	protected float fSpeed = 0.0f;
	protected float fOrientation = 0.0f;
	protected float fRadius = 0.0f;
	
	protected Bitmap bitmap = null;
	protected SurfaceClass Surface = null;
	protected Terrarium pTerrarium = null;
	
	public float GetX() { return fPosX; }
	public float GetY() { return fPosY; }
	public float GetW() { return fWidth; }
	public float GetH() { return fHeight; }
	
	public void SetPosition(float posX, float posY) { fPosX = posX; fPosY = posY; }
	
	public void SetMovementFlag(int Flag) { MovementFlag = Flag; }
	
	public void OnDraw(Canvas canvas)
	{	
		if(UnitFlag == 1)
			return;
		
		Surface.OnDraw(canvas, bitmap, fPosX-(bitmap.getWidth()/2), fPosY-(bitmap.getHeight()/2));
	}
	
	public void OnUpdate(long diff)
	{	
		
	}	
	
	public void OnRemove()	
	{
		UnitFlag = 1;
	}
	
	public boolean IsOnPosition(float fOnTouchX, float fOnTouchY)
	{
		if( ( fOnTouchX > fPosX-(fWidth/2) ) && ( fOnTouchX < fPosX + (fWidth/2)) && ( fOnTouchY > fPosY - (fHeight/2) ) && ( fOnTouchY < fPosY + (fHeight/2) ) )	
				return true;

	    return false;
	}	
	
	public boolean IsNearObject(Animal animal, Worm worm, Spider spider)
	{
		return false;
	}
	
	public boolean IsInDistance(Animal animal, float fPosX, float fPosY, float fDistance)
	{
		return false;
	}
}


