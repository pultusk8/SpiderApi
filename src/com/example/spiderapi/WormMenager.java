package com.example.spiderapi;

import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class WormMenager 
{
	private SurfaceClass Surface = null;
	private Terrarium pTerrarium = null;
	private Worm WormNumber[] = { null,  null, null,  null, null };
	private int MaxWormsInTerr = 5;
	
	WormMenager(SurfaceClass Surface, Terrarium pTerrarium) 
	{
		this.Surface = Surface;	
		this.pTerrarium = pTerrarium;
	}

	public boolean AddWorm(Worm worm/*null doda 1 robaka randomowo*/)
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
	
	public boolean RemoveWorm(Worm worm)
	{
		if(worm != null)
		{
			for(int i=0; i<MaxWormsInTerr; ++i)
			{
				if(WormNumber[i] == worm)
				{
					WormNumber[i].OnRemove();
					WormNumber[i] = null;
					return true;	
				}		
			}	
		}
			
		return false;		
	}
	
	public Worm GetWorm(float fOnTouchX, float fOnTouchY)
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
	
	public Worm GetWorm()
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
	
	public void OnUpdate(long diff)
	{		
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				WormNumber[i].OnUpdate(diff);
			}		
		}
	}
	
	public void OnDraw(Canvas canvas) 
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



