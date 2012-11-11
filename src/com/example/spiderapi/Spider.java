package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Spider
{
	private int SluffLevel;//wylinka ze slownika :D
	int SluffTimer;
	
	int Health;
	int Speed;
	
	int HungryLevel;
	int HungryTimer; //when spider whant to eat	
	
	public Spider()
	{
		
	}
	
	/*Worm*/int TargetedWorm;
	
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
		MoveToPoint(x, y, z);
	}
		
		private void MoveToPoint(int x, int y, int z)
		{
			
		}
		
	public void OnUpdate()
	{
		this.OnEatTime();
		this.OnMove();
	}
	
	public void OnDraw(Canvas canvas)
	{
		//Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.spider);
		//canvas.drawBitmap(test, goX, goY, null);	
	}
	
}
