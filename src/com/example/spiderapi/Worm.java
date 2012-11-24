package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.graphics.Canvas;

public class Worm extends Animal
{		
	public Worm(SurfaceClass Surface, Terrarium pTerrarium) 
	{
		fWidth = 20.0f;
		fHeight = 20.0f;
		ObjectID = 10;
		BitmapID = 0;
		this.Surface = Surface;	
		this.bitmap = Surface.LoadBitmap(ObjectID, BitmapID);
		this.pTerrarium = pTerrarium;
	}

	@Override
	public void OnDraw(Canvas canvas) 
	{	
		super.OnDraw(canvas);
	}

	@Override
	public void OnUpdate(long diff) 
	{
		super.OnUpdate(diff);
	}

	@Override
	public void OnRemove()
	{
		super.OnRemove();
	}
}
