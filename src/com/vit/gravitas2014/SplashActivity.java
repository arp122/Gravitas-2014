package com.vit.gravitas2014;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class SplashActivity extends Activity {

	ViewFlipper flippy;
	ImageView img;
	String sharedPrefKey = "splashOccoured";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		
		SharedPreferences preferences = getSharedPreferences(sharedPrefKey, 0);
		
		TimerTask task=new TimerTask()
		{

			public void run()
			{

				finish();
				Intent mainIntent=new Intent().setClass(SplashActivity.this, HomeActivity.class);
				startActivity(mainIntent);
			}

		};

		
		String state = preferences.getString("status", "Couldn't load data!");
		if(state.equals("Done!"))
		{
			finish();
			Intent mainIntent=new Intent().setClass(SplashActivity.this, HomeActivity.class);
			startActivity(mainIntent);
		}		
		else
		{
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("status", "Done!");
			editor.commit();

			setContentView(R.layout.activity_splash);
			flippy = (ViewFlipper) findViewById (R.id.viewFlipperSplash);
			flippy.setFlipInterval(2000);
			Timer timer=new Timer();
			timer.schedule(task,3400);
		

			flippy.startFlipping();
		
		}

	}
	

}
