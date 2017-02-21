package com.vit.gravitas2014;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SportsSearch extends Activity implements OnItemClickListener
{

	int[] indexes;
	int count = 0;
	Typeface font;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sports);
		
		Bundle bundle = getIntent().getExtras();
		String query = bundle.getString("query");
		
		font = Typeface.createFromAsset(getAssets(), "BrandonText-Medium.otf");
		

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4f81bc")));
		
		indexes = new int[200];

		for(int j = 0 ; j < SportsStrings.titles.length ; j++)
		{
			if((SportsStrings.titles[j]).toUpperCase().indexOf(query.toUpperCase()) != -1)
			{

					indexes[count] = j;
					count++;
			

			}
		}
		
		if(count == 0)
		{
			TextView tvNoResults = (TextView)findViewById(R.id.tvNoResults);
			tvNoResults.setVisibility(View.VISIBLE);
		}
		
		ListView listView=(ListView)findViewById(R.id.listViewSports);
		
		List<RowItem> rowItems;
		rowItems = new ArrayList<RowItem>();
		for ( int i = 0; i<count ; i++)
		{
			RowItem item = new RowItem( SportsStrings.titles[indexes[i]],SportsStrings.time[indexes[i]],SportsStrings.venue[indexes[i]],"","#CCCCCC");
			rowItems.add(item);
		}
		CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems,font);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		

	}
	
	ImageView imageView;
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
	{
		
		position = indexes[position];
		String heading = SportsStrings.titles[position];
        String time = SportsStrings.time[position];
        String date = SportsStrings.dates[position];
        String venue = SportsStrings.venue[position];
        final String googleMapsLink = SportsStrings.googleMapsLinks[position];
        
        Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.sportsdialog);
        ((TextView)d.findViewById(R.id.tvSportName)).setText(heading);
        ((TextView)d.findViewById(R.id.tvSportName)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsDates)).setText("Dates: "+date);
        ((TextView)d.findViewById(R.id.tvSportsDates)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsTime)).setText("Time: "+time);
        ((TextView)d.findViewById(R.id.tvSportsTime)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsVenue)).setText("Venue: "+venue+"\n");
        ((TextView)d.findViewById(R.id.tvSportsVenue)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsRules)).setText(SportsStrings.RULES);
        ((TextView)d.findViewById(R.id.tvSportsRules)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsCo1)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsCom1)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsCo2)).setTypeface(font);
        ((TextView)d.findViewById(R.id.tvSportsCom2)).setTypeface(font);
        
        imageView = (ImageView)d.findViewById(R.id.ivSportsLocation);
        imageView.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View view) 
			{
				 Intent openMaps = new Intent(Intent.ACTION_VIEW,Uri.parse(googleMapsLink));
			     startActivity(openMaps);
			}
		});
        
        d.show();
        
	}
}
