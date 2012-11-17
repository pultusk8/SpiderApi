package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.graphics.Canvas;

public class Worm extends Animal
{		
	boolean IsDeath = false;

	public Worm(SurfaceClass Surface, Terrarium pTerrarium) 
	{
		ObjectID = 10;
		BitmapID = 0;
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(ObjectID, BitmapID);
		this.pTerrarium = pTerrarium;
	}

	@Override
	public void OnDraw(Canvas canvas) 
	{
		if(IsDeath)
			return;
		
		super.OnDraw(canvas);
	}

	public void Remove()
	{
		IsDeath = true;
	}
}
