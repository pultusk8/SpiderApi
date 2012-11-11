package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Spider
{
	private int SluffLevel;//wylinka ze slownika :D
	private int SluffTimer;
	
	private int Health;
	private int Speed;
	
	private int HungryLevel;
	private int HungryTimer; //when spider whant to eat	
	
	private int PosX = 0;
	private int PosY = 0;
	private int goX = 20;
	private int goY = 20;	
	private int timer = 0;
	private Boolean GoForward = false;
	Bitmap bitmap = null;
	
	public Spider()
	{
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
		int x = 0, y = 0, z = 0;
		//get random point 
		
		if(timer > 60)
		{
			if(GoForward)
			{				
				if(goY < 300)
					goY++;
				
				if(goX < 300)
					goX++;
				
				if(goX > 100 || goY > 400)
					GoForward = false;
			}
			else
			{				
				goY--;
				goX--;
				
				if(goX < 0 || goY < 0)
					GoForward = true;
			}
		}
		else
		{
			if(GoForward)
			{				
				if(goY < 300)
					goY--;
				
				if(goX < 300)
					goX++;
				
				if(goX > 100 || goY > 400)
					GoForward = false;
			}
			else
			{				
				goY++;
				goX--;
				
				if(goX < 0 || goY < 0)
					GoForward = true;
			}					
		}
		timer++;
		
		if(timer > 120)
			timer = 0;			
		
		MoveToPoint(x, y, z);
	}
		
	private void MoveToPoint(int x, int y, int z) {}
		
	public int GetPosX() { return goX; }
	public int GetPosY() { return goY; }
		
	public void OnUpdate()
	{
		this.OnEatTime();
		this.OnMove();
	}
	
	public void OnDraw(Canvas canvas)
	{
		GFXSurf.OnDraw(canvas, bitmap, goX, goY);
		//Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.spider);
		//canvas.drawBitmap(test, goX, goY, null);	
	}
	
}
