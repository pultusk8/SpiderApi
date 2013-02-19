package com.example.spiderapi;

import android.graphics.Canvas;

public class ButtonMenager 
{
	private static int n=0;
	private static int nButtonTableSize = 10;
	private static InterfaceButton ButtonTable[] = { null, null, null, null, null, null, null, null, null ,null };
	
	public static InterfaceButton GetButtonOnPosition(float PositionX, float PositionY)
	{
		for(int i=0; i<nButtonTableSize; ++i)
		{
			if(ButtonTable[i] != null && ButtonTable[i].IsOnPosition(PositionX, PositionY))
				return ButtonTable[i];
		}
		
		return null;
	}

	public static void AddButton(InterfaceButton NewButton) 
	{
		if(NewButton != null)
		{
			for(int i=0; i<nButtonTableSize; ++i)
			{
				if(ButtonTable[i] == null)
				{
					ButtonTable[i] = NewButton;
					++n;
					return;
				}		
			}	
		}	
	}
	
	public static void OnDraw(Canvas canvas)
	{
		for(int i=0; i<nButtonTableSize; ++i)
		{
			if(ButtonTable[i] != null)
				ButtonTable[i].OnDraw(canvas);
		}	
		MsgMenager.AddMassage(0,"ile guzikow: " + n + "");	
	}

	public static void CreateButtons(EnumGameState currentGameState)
	{
		RemoveButtons();
		
		switch(currentGameState)
		{
			case Game:
			{
				new InterfaceButton(300);
				new InterfaceButton(301);
				new InterfaceButton(302);
				break;
			}
				
			default:
				break;
		}
		
	}

	private static void RemoveButtons() 
	{
		for(int i=0; i<nButtonTableSize; ++i)
		{
			ButtonTable[i] = null;
		}		
	}
}
