package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.spiderapi.GFXSurface.SurfaceClass;

public class WormBox 
{
	private float fPosX = 300;
	private float fPosY = 450;
	private float fHeight = 100;
	private float fWidth = 100;
	private int MaxWormInBox = 10;
	private Worm wormnumber[] = { null , null, null, null, null, null, null, null, null, null };
	
	private Bitmap bitmap = null;
	private SurfaceClass Surface = null;
	private Terrarium pTerrarium = null;	
	
	
	WormBox(SurfaceClass Surface, Terrarium pTerrarium) 
	{ 
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(30, 0);
		this.pTerrarium = pTerrarium;
		fPosX = pTerrarium.GetX();
		fPosY = pTerrarium.GetY();
		wormnumber[0] = new Worm(Surface, pTerrarium);
		wormnumber[0].SetPosition(fPosX + 50, fPosY + 50);
	}
	
	public Worm GetWorm()
	{
		Worm tempWorm = null;
		for(int i=0; i<MaxWormInBox; ++i)
		{
			if(wormnumber[i] != null)
			{
				tempWorm = wormnumber[i];
				wormnumber[i] = null;
				break;
			}	
		}
		return tempWorm;
	}
	
	public boolean AddWormToWormBox(Worm worm/*null doda 1 robaka randomowo*/)
	{
		if(worm != null)
		{
			for(int i=0; i<MaxWormInBox; ++i)
			{
				if(wormnumber[i] == null)
				{
					wormnumber[i] = worm;
					return true;	
				}		
			}	
		}
		
		Worm newworm = new Worm(Surface, pTerrarium);
		newworm.SetPosition(fPosX, fPosY);
		
		for(int i=0; i<MaxWormInBox; ++i)
		{
			if(wormnumber[i] == null)
			{
				wormnumber[i] = newworm;
				return true;	
			}		
		}		
		return false;
	}
	
	public boolean IsOnPosition(float fOnTouchX, float fOnTouchY)
	{
	    if( ( fOnTouchX > fPosX ) && ( fOnTouchX < fPosX + fWidth) && ( fOnTouchY > fPosY ) && ( fOnTouchY < fPosY + fHeight ) )
	        return true;

	    return false;
	}
	
	public void OnUpdate(long diff)
	{		
		for(int i=0; i<MaxWormInBox; ++i)
		{
			if(wormnumber[i] != null)
			{
				wormnumber[i].OnUpdate(diff);
			}		
		}
	}
	
	public void OnDraw(Canvas canvas) 
	{		
		Surface.OnDraw(canvas, bitmap, fPosX, fPosY);
		
		for(int i=0; i<MaxWormInBox; ++i)
		{
			if(wormnumber[i] != null)
			{
				wormnumber[i].OnDraw(canvas);
			}		
		}
	}
}
