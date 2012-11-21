package com.example.spiderapi;

import com.example.spiderapi.GFXSurface.SurfaceClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Animal 
{
	protected int ObjectID = 0;
	protected int BitmapID = 0;
	
	protected float fPosX = 50.0f;
	protected float fPosY = 50.0f;
	protected float fHeight = 0.0f;
	protected float fWidth = 0.0f;
	protected float fSpeed = 0.0f;
	protected float fOrientation = 0.0f;
	
	protected Bitmap bitmap = null;
	protected SurfaceClass Surface = null;
	protected Terrarium pTerrarium = null;
	
	public float GetX() { return fPosX; }
	public float GetY() { return fPosY; }
	public float GetW() { return fWidth; }
	public float GetH() { return fHeight; }
	
	public void SetPosition(float posX, float posY) { fPosX = posX; fPosY = posY; }
	
	public void OnDraw(Canvas canvas)
	{	
		Surface.OnDraw(canvas, bitmap, fPosX, fPosY);
	}
	
	public void OnUpdate(long diff)
	{	
		
	}	
	
	public void OnRemove()	
	{
		
	}
}
