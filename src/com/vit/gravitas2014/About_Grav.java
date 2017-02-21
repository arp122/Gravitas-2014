package com.vit.gravitas2014;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class About_Grav extends Activity {
	
	TextView tvAboutGrav;
	static Typeface font;
	
	
	String_Gravitas obj;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_gravitas);
		font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
		tvAboutGrav = (TextView)findViewById(R.id.tvAboutGrav);
		
		tvAboutGrav.setText(obj.desc_grav+"\n\n\n");
		tvAboutGrav.setTypeface(font);
	
		
		TextView tvSupport = (TextView)findViewById(R.id.support);
		tvSupport.setTypeface(font);
		//tvSupport.setPaintFlags(tvSupport.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView emailIshan = (TextView)findViewById(R.id.emailIshan);
		emailIshan.setTypeface(font);
		
		TextView emailUtsav = (TextView)findViewById(R.id.emailUtsav);
		emailUtsav.setTypeface(font);
		
		TextView emailArpit = (TextView)findViewById(R.id.emailArpit);
		emailArpit.setTypeface(font);
		
		TextView emailJats = (TextView)findViewById(R.id.emailJats);
		emailJats.setTypeface(font);
		
		TextView email = (TextView)findViewById(R.id.emailEmial);
		email.setTypeface(font);
		
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
