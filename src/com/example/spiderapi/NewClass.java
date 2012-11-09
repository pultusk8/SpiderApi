package com.example.spiderapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class NewClass extends View 
{

	Bitmap gBitmap;
	float changingY;
	
	public NewClass(Context context) 
	{
		super(context);
		gBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
		changingY = 0.0f;
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(gBitmap, 50, changingY, null);
		
		if(changingY < canvas.getHeight())
		{
			changingY += 1;
		}else
			changingY = 0;
		
		Rect middleRect = new Rect();
		middleRect.set(0, 400, canvas.getWidth(), 500);
		
		Paint ourBlue = new Paint();
		ourBlue.setColor(Color.BLUE);
		canvas.drawRect(middleRect, ourBlue);
	
		invalidate();	
	}

	
	
}
