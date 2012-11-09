package com.example.spiderapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener
{
	SurfaceClass Surface;
	float x, y;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
		setContentView(Surface);
		x = y = 0.0f;
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		Surface.pause();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		Surface.resume();
	}

	public boolean onTouch(View v, MotionEvent event)
	{
		x = event.getX();
		y = event.getY();
		return true;
	}
	
	public class SurfaceClass extends SurfaceView implements Runnable
	{
		SurfaceHolder surfHolder;
		Thread ThreadOne = null;
		boolean IsRunning = false;
		
		public SurfaceClass(Context context)
		{
			super(context);
			surfHolder = getHolder();

		}

		public void pause()
		{
			IsRunning = false;
			while(true)
			{
				try
				{
					ThreadOne.join();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				break;
			}
			ThreadOne = null;
		}
		
		public void resume()
		{
			IsRunning = true;
			ThreadOne = new Thread(this);
			ThreadOne.start();	
		}
		
		public void run() 
		{
			while(IsRunning)
			{
				if(!surfHolder.getSurface().isValid())
					continue;
				
				Canvas canvas = surfHolder.lockCanvas();
				canvas.drawRGB(23, 56, 68);
				if(x != 0 && y != 0)
				{
					Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.button1);
					canvas.drawBitmap(test, x-(test.getWidth()/2), y-(test.getHeight()/2), null);
				}	
				surfHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
}
