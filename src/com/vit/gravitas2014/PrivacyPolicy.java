package com.vit.gravitas2014;
 



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class PrivacyPolicy extends Activity
{

	Typeface font;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.privacy);
		font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");
		try
		{
			int titleId = getResources().getIdentifier("action_bar_title", "id",
					"android");
			TextView yourTextView = (TextView) findViewById(titleId);
			yourTextView.setTypeface(font);

			getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
		}
		catch(NullPointerException e )
		{
			e.printStackTrace();
		}
		
		String privacyPolicy = "This app does not collect, use or process any of your private information. This application uses internet for the sole purpose of updating event details that are displayed in the app.\n\nNo private data what so ever is collected in the app and sent to the internet.\n\n";
		
		TextView tv = (TextView)findViewById(R.id.policy);
		tv.setTypeface(font);
		tv.setText(privacyPolicy);
		
		TextView tvSupport = (TextView)findViewById(R.id.support);
		tvSupport.setTypeface(font);
		//tvSupport.setPaintFlags(tvSupport.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView emailIshan = (TextView)findViewById(R.id.emailIshan);
		emailIshan.setTypeface(font);
		
		TextView emailUtsav = (TextView)findViewById(R.id.emailUtsav);
		emailUtsav.setTypeface(font);
		
		TextView emailArpit = (TextView)findViewById(R.id.emailArpit);
		emailArpit.setTypeface(font);
		
		TextView emailHarjatin = (TextView)findViewById(R.id.emailJats);
		emailHarjatin.setTypeface(font);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent myinIntent=new Intent(this,HomeActivity.class);
		startActivity(myinIntent);
		this.finish();
	}
}
