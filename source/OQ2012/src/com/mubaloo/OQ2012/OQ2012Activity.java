package com.mubaloo.OQ2012;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.openfeint.api.OpenFeint;
import com.openfeint.api.OpenFeintDelegate;
import com.openfeint.api.OpenFeintSettings;
import com.openfeint.api.ui.Dashboard;

public class OQ2012Activity extends Activity 
{
	// OOP elements
	private static OQ2012Activity instance;
	
	// UI elements
	private Button b_play;
	private Button b_stats;
	private Button b_follow;
	private Button b_info;
	private Button b_countdown;
    private Typeface customFont;
    private ImageView iv_torch;
    private AnimationDrawable ad_torch;
    
    //Openfeint
    static final String gameName = "Olympic Quiz 2012";
	static final String gameID = "464542";
	static final String gameKey = "ADmARQITd7DzrPxJVKCfQ";
	static final String gameSecret = "fAaRrBhDRGqhPNcdMRfzhYWPzulq1cNhtglQLMPUhw"; 
	
	//Stats
	int r_goldmedals;
	int r_silvermedals;
	int r_bronzemedals;
	int r_points;
	int w_goldmedals = 0;
	int w_silvermedals = 0;
	int w_bronzemedals = 0;
	int w_points = 0;
	
    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oq2012activity);
        instance = this;
        
        //OPENFEINT
        Map<String, Object> options = new HashMap<String, Object>();
    	//line below used to set orientation
    	options.put(OpenFeintSettings.RequestedOrientation, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	OpenFeintSettings settings = new OpenFeintSettings(gameName, gameKey, gameSecret, gameID);
    	OpenFeint.initialize(this, settings, new OpenFeintDelegate() {});

        //UI
        customFont 	= Typeface.createFromAsset(getAssets(), "fonts/LONDON2012.TTF");
        
        b_play = (Button) findViewById(R.id.b_play);
        b_play.setText("PLAY!");
        b_play.setTextColor(Color.WHITE);
        b_play.setTypeface(customFont);
        
        b_stats = (Button) findViewById(R.id.b_stats);
        b_stats.setText("STATS");
        b_stats.setTextColor(Color.WHITE);
        b_stats.setTypeface(customFont);
        
        b_follow = (Button) findViewById(R.id.b_follow);
        b_follow.setText("FOLLOW");
        b_follow.setTextColor(Color.WHITE);
        b_follow.setTypeface(customFont);
        
        b_info = (Button) findViewById(R.id.b_info);
        b_info.setText("INFO");
        b_info.setTextColor(Color.WHITE);
        b_info.setTypeface(customFont);
        
        //Read File
        SharedPreferences myStats = this.getSharedPreferences("myStats", Context.MODE_PRIVATE);
        r_goldmedals = myStats.getInt("gold_medals", 0);
        r_silvermedals = myStats.getInt("silver_medals", 0);
        r_bronzemedals = myStats.getInt("bronze_medals", 0);
        r_points = myStats.getInt("points", 0);
        
        //Countdown
        countdown();
        
        // Button actions
        View.OnClickListener handler = new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				switch(v.getId())
				{
					case R.id.b_play:
						//GO TO SCREEN
						Intent play_Intent = new Intent(v.getContext(), Question.class);
						startActivityForResult(play_Intent, 0);
					break;
					
					case R.id.b_stats:
						//GO TO SCREEN
						Dashboard.open();
					break;
					
					case R.id.b_follow:
						//GO TO SCREEN
					break;
					
					case R.id.b_info:
						//GO TO SCREEN
					break;
				}	
			}
		};
		
		b_play.setOnClickListener(handler);
		b_stats.setOnClickListener(handler);
		b_follow.setOnClickListener(handler);
		b_info.setOnClickListener(handler);
    }
    
    @Override
	protected void onResume() 
	{	
    	super.onResume();
    	
    	//Animation
    	Runnable myAnimation = new Runnable(){
    	public void run(){
    	iv_torch = (ImageView)findViewById(R.id.iv_torch);
        iv_torch.setImageResource(R.anim.torch);
        ad_torch = (AnimationDrawable) iv_torch.getDrawable();
        ad_torch.start();
    	}
    	};
    	
    	new Handler().postDelayed(myAnimation, 1000);
	}
    
    @Override
   	protected void onStop() 
   	{	
       	super.onStop();
        
       	//Write
        w_goldmedals 	= r_goldmedals; 	//+ new medals
        w_silvermedals	= r_silvermedals;	//+ new medals
        w_bronzemedals 	= r_bronzemedals; 	//+ new medals
        w_points 		= r_points; 		//+ new points
        SharedPreferences myStats = this.getSharedPreferences("myStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor myStatsEditor = myStats.edit();
        myStatsEditor.putInt("gold_medals", w_goldmedals);
        myStatsEditor.putInt("silver_medals", w_silvermedals);
        myStatsEditor.putInt("bronze_medals", w_bronzemedals);
        myStatsEditor.putInt("points", w_points);
        myStatsEditor.commit();
   	}
    
    private void countdown()
    {
    	Calendar c = Calendar.getInstance();
    	int start_day = 208+1;	// 27 July + leap year
    	int current_day = c.get(Calendar.DAY_OF_YEAR);
    	int days = start_day-current_day;
        
        b_countdown = (Button) findViewById(R.id.b_countdown);
        b_countdown.setText("DAYS TO GO: "+ days);
        b_countdown.setTextColor(Color.WHITE);
        b_countdown.setTypeface(customFont);
    }
    
    public OQ2012Activity getInstance()
   	{
   		return instance;
   	}
    
    public void setGoldMedals(int newGold)
   	{
   		w_goldmedals = r_goldmedals + newGold;
   	}
    
    public void setSilverMedals(int newSilver)
   	{
   		w_silvermedals = r_silvermedals + newSilver;
   	}
    public void setBronzeMedals(int newBronze)
   	{
   		w_bronzemedals = r_bronzemedals + newBronze;
   	}
    public void setPoints(int newPoints)
   	{
   		w_points = r_points + newPoints;
   	}
		
}