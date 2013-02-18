package com.example.spiderapi;

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
	protected Bitmap bmpBitmapTable[][] = { { null } };
	protected int AnimationCurrentState = 0;
	protected int AnimationTimer = 1000; //first animation
	protected int AnimationNextTimer = 1000; // time between animation change
	protected int MaxAnimationsDirection = 8;
	protected int MaxAnimationFrames = 8;	
	
	//Flags
	protected int MovementFlag = 0;
	protected int UnitFlag = 0;
	
	//Positions
	protected float fPosX = 50.0f;
	protected float fPosY = 50.0f;
	protected float fGoX = 50.0f;
	protected float fGoY = 50.0f;
	protected float fHeight = 0.0f;
	protected float fWidth = 0.0f;
	protected float fSpeed = 0.0f;
	protected int Orientation = 0;
	protected float fRadius = 0.0f;
	protected MoveDirection moveDirection = MoveDirection.Down;
	
	//Social
	protected int AnimalSize = 1;
	protected int Health = 5;
	protected int HungryTimer = 5000; //when spider whant to eat
	
	//Position Methods
	public float GetX() { return fPosX; }
	public float GetY() { return fPosY; }
	public float GetW() { return fWidth; }
	public float GetH() { return fHeight; }
	public void SetPosition(float posX, float posY) { fPosX = posX; fPosY = posY; }
	public void SetMovementFlag(int Flag) { MovementFlag = Flag; }
	
	//Social Methods
	public int GetHealth() { return Health; }
	
	protected void OnCreate()
	{

	}
	
	public void OnDraw(Canvas canvas)
	{	
		if(UnitFlag == 1)
			return;
		
		if(bmpBitmapTable[0][0] != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpBitmapTable[0][0], fPosX-(bmpBitmapTable[0][0].getWidth()/2), fPosY-(bmpBitmapTable[0][0].getHeight()/2));
	}
	
	public void OnUpdate(long diff)
	{	
		OnAnimate(diff);
	}	
	
	public void OnAnimate(long diff)
	{
		//BitmapID
		//this.bitmap = Surface.LoadBitmap(ObjectID, AnimationCurrentState);	
		if(AnimationTimer < diff)			
		{
			++AnimationCurrentState;
			
			if(AnimationCurrentState == MaxAnimationFrames)
				AnimationCurrentState = 0;
			
			AnimationTimer = AnimationNextTimer;
		}AnimationTimer -= diff;
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


