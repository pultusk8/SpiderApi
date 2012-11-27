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
	UNIT_MOVE_MOVEDBYUSER// 0x0200
}

public class Animal 
{	
	//Graphics
	protected int ObjectID = 0;
	protected int BitmapID = 0;
	
	//Flags
	protected int MovementFlag = 0;
	protected int UnitFlag = 0;
	
	//Positions
	protected float fPosX = 50.0f;
	protected float fPosY = 50.0f;
	protected float fHeight = 0.0f;
	protected float fWidth = 0.0f;
	protected float fSpeed = 0.0f;
	protected float fOrientation = 0.0f;
	protected float fRadius = 0.0f;
	
	//Social
	protected int Health = 5;
	
	protected Bitmap bitmap = null;
	protected SurfaceClass Surface = null;
	protected Terrarium pTerrarium = null;
	
	public float GetX() { return fPosX; }
	public float GetY() { return fPosY; }
	public float GetW() { return fWidth; }
	public float GetH() { return fHeight; }
	
	public int GetHealth() { return Health; }
	
	public void SetPosition(float posX, float posY) { fPosX = posX; fPosY = posY; }
	
	public void SetMovementFlag(int Flag) { MovementFlag = Flag; }
	
	protected void OnCreate()
	{
		this.Surface = GFXSurface.GetSurface();
		this.bitmap = Surface.LoadBitmap(ObjectID, BitmapID);	
		this.pTerrarium =  GFXSurface.GetTerrarium();
	}
	
	public void OnDraw(Canvas canvas)
	{	
		if(UnitFlag == 1)
			return;
		
		if(bitmap != null)
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
		
	private double GetDistance(float x1, float y1, float x2, float y2 )
	{ 
	    return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); 	
	}	
	
	public boolean IsInRange(Spider spider) 
	{
		if(GetDistance(fPosX, fPosY, spider.GetX(),  spider.GetY()) < fRadius + spider.fRadius)
			return true;
	
		return false;
	}	
}


