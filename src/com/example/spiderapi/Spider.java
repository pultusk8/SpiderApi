package com.example.spiderapi;

import java.util.Random;

import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class Spider extends Animal
{	
	private int SluffLevel = 0;//wylinka ze slownika :D
	private int SluffTimer = 10000;
	
	private int HungryLevel = 0;
	private int HungryTimer = 5000; //when spider whant to eat	
	
	//Moving Variables
	private float fGoX = 50.0f;
	private float fGoY = 50.0f;
	private float fGoZ = 0.0f;
	
	private float vectorX, vectorY, fNewX, fNewY;	
			
	private int RandomWaypointTimer = 5000;
	
	//Pointers
	Worm worm = null;	
	
	public Spider(SurfaceClass Surface, Terrarium pTerrarium)
	{
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(ObjectID, BitmapID);
		this.pTerrarium = pTerrarium;

		this.OnCreate();
	}	
	
	
	@Override
	protected void OnCreate() 
	{
		super.OnCreate();
		//Initialize Variable
		MovementFlag = 1; //super
		fSpeed = 0.5f;
		fWidth = this.bitmap.getWidth();
		fHeight = this.bitmap.getHeight();
		this.SetPosition(19,45);
	}
	
	private void OnEatTime(long diff)
	{
		if(worm == null)
		{
	    	if(HungryTimer < diff)
	    	{
	    		worm = WormMenager.GetWorm();
	    		if(worm != null)
	    		{
		    		fGoX = worm.GetX();
		    		fGoY = worm.GetY();
	    		}
	    		else
	    		{
	    			Health--;
	    		}
	    		HungryTimer = 20000;
	    	}HungryTimer -= diff;
		}
		else
		{
			if(fPosX == worm.GetX()  && fPosY == worm.GetY())
			{
				//WormMgr.RemoveWorm(worm); //added support in worm struct
				worm.OnRemove();
				worm = null;
				Health++;
				HungryTimer = 8000;
			}
		}			
	}
	
	private void RandomWaypoint()
	{
		int TerrX, TerrY;
		TerrX = pTerrarium.GetX();
		TerrY = pTerrarium.GetY();
			
		long random = System.currentTimeMillis();
		
		Random r = new Random();
		int randomX = r.nextInt((int) random);
		int randomY = r.nextInt((int) random);
		
		vectorX = vectorY = 0.0f;
		
		switch(r.nextInt(8))
	    {
	        case 0:   vectorX = 1.0f;     vectorY = -1.0f;    break;
	        case 1: vectorX = 1.0f;     vectorY = 1.0f;     break;
	        case 2:  vectorX = -1.0f;    vectorY = 1.0f;     break;
	        case 3:    vectorX = -1.0f;    vectorY = -1.0f;    break;
	        case 4: 		vectorY = -1.0f; break;
	        case 5: 		vectorY = 1.0f; break;
	        case 6: 		vectorX = -1.0f; break;
	        case 7: 	vectorX = 1.0f; break;
	        default: break;
	    }		
		
		fGoX = (randomX - (randomX - TerrX)) * vectorX;
		if(fGoX < 0) 
			RandomWaypoint();
			//fGoX = (randomX - (randomX - TerrX)) * 1;
		if(fGoX > TerrX) 
			RandomWaypoint();
			//fGoX = TerrX;
		fGoY = (randomY - (randomY - TerrY)) * vectorY;
		if(fGoY < 0) 
			//fGoY = (randomY - (randomY - TerrY)) * 1;
			RandomWaypoint();
		if(fGoY > TerrY)
			RandomWaypoint();
	}
	
	private void OnMove(long diff) 
	{	
		if(MovementFlag == 0)
			return;
		
		if(MovementFlag == 3)
			return;		
		
		if(worm == null)
		{
	    	if(RandomWaypointTimer < diff)
	    	{
	    		RandomWaypoint();
	    		RandomWaypointTimer = 2000;
	    	}RandomWaypointTimer -= diff;	
		}
		
	    if(fPosX == fGoX && fPosY == fGoY)
	    {	
	        //StopMove(); 
	        return;
	    }

	    vectorX = vectorY = fNewX = fNewY = 0.0f;

	    MoveDirection Move = null;

	    if(fPosX < fGoX && fPosY < fGoY)
	    	Move = MoveDirection.DownRight;
	    if(fPosX > fGoX && fPosY < fGoY)
	    	Move = MoveDirection.DownLeft;
	    if(fPosX < fGoX && fPosY > fGoY)
	    	Move = MoveDirection.UpRight;
	    if(fPosX > fGoX && fPosY > fGoY)
	    	Move = MoveDirection.UpLeft;
	    if(fPosX == fGoX && fPosY > fGoY)
	    	Move = MoveDirection.Up;
	    if(fPosX == fGoX && fPosY < fGoY)
	    	Move = MoveDirection.Down;
	    if(fPosX < fGoX && fPosY == fGoY)
	    	Move = MoveDirection.Right;
	    if(fPosX > fGoX && fPosY == fGoY)
	    	Move = MoveDirection.Left;	    
	        
	    if(Move != null)
	    {
			switch(Move)
		    {
		        case UpRight:   vectorX = 1.0f;     vectorY = -1.0f; break;
		        case DownRight: vectorX = 1.0f;     vectorY = 1.0f;  break;
		        case DownLeft:  vectorX = -1.0f;    vectorY = 1.0f;  break;
		        case UpLeft:    vectorX = -1.0f;    vectorY = -1.0f; break;
		        case Up: 		vectorY = -1.0f; 	break;
		        case Down: 		vectorY = 1.0f; 	break;
		        case Left: 		vectorX = -1.0f; 	break;
		        case Right: 	vectorX = 1.0f; 	break;
		        default: break;
		    }
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
		worm = null;
	}
		
	public void GetNewSluff()
	{
		++SluffLevel;
		this.bitmap = Surface.LoadBitmap(ObjectID, SluffLevel);
		SluffTimer = 30000;
	}
	
	@Override	
	public void OnUpdate(long diff)
	{	
		super.OnUpdate(diff);
		
		if(Health < -5)
			return;
			//Smierc :D
		
		if(Health <= 0)
			HungryTimer = 2000;
		
    	if(SluffTimer < diff)
    	{
    		GetNewSluff();
    	}SluffTimer -= diff;	
	
		this.OnEatTime(diff);

		this.OnMove(diff);
	}

	@Override
	public void OnDraw(Canvas canvas) 
	{
		super.OnDraw(canvas);
	}
}
