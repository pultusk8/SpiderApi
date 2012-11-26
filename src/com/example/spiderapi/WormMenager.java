package com.example.spiderapi;

import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class WormMenager 
{
	private SurfaceClass Surface = null;
	private Terrarium pTerrarium = null;
	
	private static Worm WormNumber[] = { null,  null, null,  null, null };
	private static int MaxWormsInTerr = 5;
	
	WormMenager() {}

	static public boolean AddWorm(Worm worm/*null doda 1 robaka randomowo*/)
	{
		if(worm != null)
		{
			for(int i=0; i<MaxWormsInTerr; ++i)
			{
				if(WormNumber[i] == null)
				{
					WormNumber[i] = worm;
					return true;	
				}		
			}	
		}
			
		return false;
	}	
	
	static public boolean RemoveWorm(Worm worm)
	{
		if(worm != null)
		{
			for(int i=0; i<MaxWormsInTerr; ++i)
			{
				if(WormNumber[i] == worm)
				{
					//WormNumber[i].OnRemove();
					WormNumber[i] = null;
					return true;	
				}		
			}	
		}
			
		return false;		
	}
	
	static public Worm GetWorm(float fOnTouchX, float fOnTouchY)
	{
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				if(WormNumber[i].IsOnPosition(fOnTouchX, fOnTouchY))	
					return WormNumber[i];	
			}		
		}	

	    return null;
	}
	
	static public Worm GetWorm()
	{		
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				return WormNumber[i];	
			}		
		}	
	    return null;
	}		
	
	static public void OnUpdate(long diff)
	{		
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				WormNumber[i].OnUpdate(diff);
			}		
		}
	}
	
	static public void OnDraw(Canvas canvas) 
	{			
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				WormNumber[i].OnDraw(canvas);
			}		
		}
	}	
}



