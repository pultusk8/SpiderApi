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
	
	private Bitmap emptyBox = null;
	private Bitmap Box = null;
	private Bitmap bitmap = null;
	private SurfaceClass Surface = null;
	private Terrarium pTerrarium = null;	
	private boolean IsBoxEmpty = true;
	
	WormBox(SurfaceClass Surface, Terrarium pTerrarium) 
	{ 
		this.Surface = Surface;	
		this.Box = Surface.LoadBitmap(30, 0);
		this.emptyBox = Surface.LoadBitmap(30, 1);
		this.pTerrarium = pTerrarium;
		fPosX = GFXSurface.screenWidth;
		fPosY = GFXSurface.screenHeight - 100;
		
		wormnumber[0] = new Worm(Surface, pTerrarium);
		wormnumber[0].SetPosition(fPosX + 50, fPosY + 50);
		
		wormnumber[1] = new Worm(Surface, pTerrarium);
		wormnumber[1].SetPosition(fPosX + 70, fPosY + 30);
		
		wormnumber[2] = new Worm(Surface, pTerrarium);
		wormnumber[2].SetPosition(fPosX + 10, fPosY + 90);		
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
	
	public boolean IsEmpty()
	{
		return IsBoxEmpty;
	}
	
	public void OnUpdate(long diff)
	{		
		IsBoxEmpty = true;
		
		for(int i=0; i<MaxWormInBox; ++i)
		{
			if(wormnumber[i] != null)
			{
				if(IsBoxEmpty == true)
					IsBoxEmpty = false;
				
				wormnumber[i].OnUpdate(diff);
			}		
		}
		
		if(IsBoxEmpty == true)
			bitmap = emptyBox;
		else 
			bitmap = Box;
	}
	
	public void OnDraw(Canvas canvas) 
	{		
		if(bitmap != null)
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
