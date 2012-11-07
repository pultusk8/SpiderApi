package com.example.spiderapi;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Text extends Activity implements View.OnClickListener
{
	Button chkCmd;
	ToggleButton passTog;
	EditText input;
	TextView display;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		
		MatchButtons();
		
		passTog.setOnClickListener(this);
		chkCmd.setOnClickListener(this);
	}
	
	void MatchButtons()
	{
		chkCmd = (Button) findViewById(R.id.bResults);
		passTog = (ToggleButton) findViewById(R.id.tbPassword);
		input = (EditText) findViewById(R.id.etCommands);
		display = (TextView) findViewById(R.id.tvResults);			
	}

	//Button Support in Class Methods Implemented from listener
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.bResults:
			String check = input.getText().toString();
			display.setText(check);
			if(check.contentEquals("left"))
			{
				display.setGravity(Gravity.LEFT);
			}
			else if(check.contentEquals("center"))
			{
				display.setGravity(Gravity.CENTER);
			}
			else if(check.contentEquals("right"))
			{
				display.setGravity(Gravity.RIGHT);
			}
			else if(check.contentEquals("blue"))
			{
				display.setTextColor(Color.BLUE);
			}
			else if(check.contains("Podaj Liczbe"))
			{
				Random randInt = new Random();
				int i = randInt.nextInt(5);
				display.setText("" + i);
				switch(randInt.nextInt(3))
				{
					case 0:
						break;
					case 1:
						break;
					case 2:
						break;
				}
			}
			else
			{
				display.setText("invalid");
				display.setGravity(Gravity.CENTER);
			}
			break;
				
			case R.id.tbPassword:
			{
				if(passTog.isChecked())
					input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

				else
					input.setInputType(InputType.TYPE_CLASS_TEXT);

				break;
			}
		
		}
		
	}	
}
