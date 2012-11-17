package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Worm extends Animal
{
	private float fPosX = 80.0f;
	private float fPosY = 80.0f;
	
	Bitmap bitmap = null;
	SurfaceClass Surface = null;
	Terrarium pTerrarium = null;
	
	int WormType = 10;
	
	boolean IsDeath = false;
	
	public Worm(Context context, SurfaceClass Surface, Terrarium pTerrarium)
	{
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(WormType, 0);
		this.pTerrarium = pTerrarium;
	}	
	
	public Worm(SurfaceClass Surface, Terrarium pTerrarium) 
	{
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(WormType, 0);
		this.pTerrarium = pTerrarium;
	}

	public void SetPosition(float posX, float posY)
	{
		fPosX = posX;
		fPosY = posY;
	}
	
	public float GetX() { return fPosX; }
	public float GetY() { return fPosY; }
	
	public void OnDraw(Canvas canvas)
	{
		if(IsDeath)
			return;
		
		Surface.OnDraw(canvas, bitmap, fPosX, fPosY);
	}
	
	public void Remove()
	{
		IsDeath = true;
	}
}
