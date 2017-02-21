package com.vit.gravitas2014;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Contact extends Activity implements OnClickListener
{

	Typeface font;
	ImageView ivWWW, ivFacebook;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");
		TextView yourTextView;
		
		ivWWW = (ImageView)findViewById(R.id.ivwww);
		ivWWW.setOnClickListener(this);
		ivFacebook = (ImageView)findViewById(R.id.ivfacebook);
		ivFacebook.setOnClickListener(this);
		
		
		try
		{
			int titleId = getResources().getIdentifier("action_bar_title", "id",
	            "android");
	    yourTextView = (TextView) findViewById(titleId);
	    yourTextView.setTypeface(font);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	    
	    yourTextView = (TextView) findViewById(R.id.tvVIT1);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tvVIT2);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tvVIT3);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tVName1);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tvPhone1);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tVName2);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tvPhone2);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tVNameHosp1);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tVNameHosp2);
	    yourTextView.setTypeface(font);

	    yourTextView = (TextView) findViewById(R.id.tvPhoneHosp1);
	    yourTextView.setTypeface(font);

	    yourTextView = (TextView) findViewById(R.id.tvPhoneHosp2);
	    yourTextView.setTypeface(font);

	    yourTextView = (TextView) findViewById(R.id.tVNameSales1);
	    yourTextView.setTypeface(font);

	    yourTextView = (TextView) findViewById(R.id.tVNameSales2);
	    yourTextView.setTypeface(font);
	    
	    yourTextView = (TextView) findViewById(R.id.tvPhoneSales1);
	    yourTextView.setTypeface(font);

	    yourTextView = (TextView) findViewById(R.id.tvPhoneSales2);
	    yourTextView.setTypeface(font);


	}

	@Override
	public void onClick(View view)
	{
		
		switch(view.getId())
		{
			case R.id.ivwww:
				String URL1= "http://vitgravitas.com/";
				Intent website1 = new Intent(Intent.ACTION_VIEW,Uri.parse(URL1));
			     startActivity(website1);
				
				break;
			
			case R.id.ivfacebook:
				String URL2 = "https://www.facebook.com/vitgravitas14";
				Intent website2 = new Intent(Intent.ACTION_VIEW,Uri.parse(URL2));
			     startActivity(website2);
				break;
		}
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