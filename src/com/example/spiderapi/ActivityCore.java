package com.example.spiderapi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

enum BUTTON_TYPE
{
	BUTTON_CONTINUE,
	BUTTON_START,
	BUTTON_OPTIONS,
	BUTTON_QUIT,
	BUTTON_MAX,
}

public class ActivityCore extends Activity 
{
	int nCounter;
	//for(int i=0; i<BUTTON_MAX)
	Button bContinue;
	Button bStart;
	Button bOptions;
	Button bQuit;
	TextView text;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_core);
        
        //connecting our objects with xmls objects
        nCounter = 0;
        bContinue = (Button) findViewById(R.id.MenuButt_Continue);
        bStart = (Button) findViewById(R.id.MenuButt_Start);
        bOptions = (Button) findViewById(R.id.MenuButt_Options);
        bQuit = (Button) findViewById(R.id.MenuButt_Quit);
        text = (TextView) findViewById(R.id.textView2); 
        
        bContinue.setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				nCounter++;
				text.setText("Kliknales " + nCounter + " razy");
			}
		});
        
        bStart.setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				nCounter++;
				text.setText("Kliknales " + nCounter + " razy");
			}
		});      
    }

    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_activity_core, menu);
        return true;
    }
}
