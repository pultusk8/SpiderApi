package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Spider
{
	private enum MoveDirection
	{
		Right,
	    UpRight,
	    Left,
	    UpLeft,
	    Down,
	    DownRight,
	    DownLeft,
	    Up,
	}	
	
	private int SpiderType = 0;
	
	private int SluffLevel = 0;//wylinka ze slownika :D
	private int SluffTimer;
	
	private int Health = 40;
	
	
	private int HungryLevel = 0;
	private int HungryTimer = 5000; //when spider whant to eat	
	
	//Moving Variables
	private float fPosX = 50.0f;
	private float fPosY = 50.0f;
	private float fGoX = 0.0f;
	private float fGoY = 0.0f;
	private float fGoZ = 0.0f;
	private float fSpeed = 1.0f;
		
	private int timer = 0;
	
	Bitmap bitmap = null;
	SurfaceClass Surface = null;
	
	public Spider()
	{

	}
	
	public Spider(Context context, SurfaceClass SurfaceC)
	{
		Surface = SurfaceC;	
		bitmap = Surface.LoadBitmap(SpiderType, SluffLevel);
	}	
	
	/*Worm*/int TargetedWorm = 0;
	
	private void OnEatTime()
	{
		if(HungryLevel < 1)
		{
			Health--;
			SelectWormToEat();
		}
		
		if(TargetedWorm == 1)
		{
			//if is on range to eat
			Eat();
		}		
	}
	
	private void SelectWormToEat()
	{
		TargetedWorm = 1;
	}
	
	private void Eat(/* WormClass target*/) 
	{
		TargetedWorm = 0;
		Health++;
	}
	
	private void OnMove() 
	{	
	    if(fPosX == fGoX && fPosY == fGoY)
	    {
	        //StopMove(); 
	        return;
	    }

	    float vectorX, vectorY, fNewX, fNewY;
	    vectorX = vectorY = fNewX = fNewY = 0.0f;

	    MoveDirection Move = MoveDirection.UpRight;

	    if(fPosX < fGoX && fPosY < fGoY)
	    	Move = MoveDirection.DownRight;
	    if(fPosX > fGoX && fPosY < fGoY)
	    	Move = MoveDirection.DownLeft;
	    if(fPosX < fGoX && fPosY > fGoY)
	    	Move = MoveDirection.UpRight;
	    if(fPosX > fGoX && fPosY > fGoY)
	    	Move = MoveDirection.UpLeft;

	    int CurrentFrameCol;
	    
		switch(Move)
	    {
	        case UpRight:   vectorX = 1.0f;     vectorY = -1.0f;    CurrentFrameCol = 1; break;
	        case DownRight: vectorX = 1.0f;     vectorY = 1.0f;     CurrentFrameCol = 1; break;
	        case DownLeft:  vectorX = -1.0f;    vectorY = 1.0f;     CurrentFrameCol = 0; break;
	        case UpLeft:    vectorX = -1.0f;    vectorY = -1.0f;    CurrentFrameCol = 0; break;
	    }

	    fNewX = fPosX;
	    fNewY = fPosY;
	    
	    if(fPosX != fGoX)
	        fNewX = fPosX + (fSpeed*vectorX);

	    if(fPosY != fGoY)
	        fNewY = fPosY + (fSpeed*vectorY); 

        fPosX = fNewX; 
        fPosY = fNewY;	        
	}
		
	public void SetUpWaypoint(float GoX, float GoY, float GoZ)
	{		
		fGoX = GoX;
		fGoY = GoY;
		fGoZ = GoZ;
	}
	
	public float GetPosX() { return fPosX; }
	public float GetPosY() { return fPosY; }
		
	public void OnUpdate()
	{
		this.OnEatTime();
		this.OnMove();
	}
	
	public void OnDraw(Canvas canvas)
	{
		Surface.OnDraw(canvas, bitmap, fPosX, fPosY);
	}	
}
