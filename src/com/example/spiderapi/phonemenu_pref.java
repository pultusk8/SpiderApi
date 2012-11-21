package com.example.spiderapi;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class phonemenu_pref extends PreferenceActivity 
{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
	
	
}
