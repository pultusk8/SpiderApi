package com.example.spiderapi;

public class Spider 
{
	public Spider()
	{
		
	}
	
	int SluffLevel;//wylinka ze slownika :D
	int SluffTimer;
	
	int Health;
	int Speed;
	
	int HungryLevel;
	int HungryTimer; //when spider whant to eat
	
	
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
	
}
