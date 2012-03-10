package com.mubaloo.OQ2012;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.openfeint.api.OpenFeint;
import com.openfeint.api.OpenFeintDelegate;
import com.openfeint.api.OpenFeintSettings;
import com.openfeint.api.resource.Leaderboard;
import com.openfeint.api.resource.Score;
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
    	View layout = findViewById(R.id.relativeLayout);
        layout.setBackgroundResource(R.drawable.background);
    	
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
        
        b_countdown = (Button) findViewById(R.id.b_countdown);
        b_countdown.setText("DAYS TO GO...");
        b_countdown.setTextColor(Color.WHITE);
        b_countdown.setTypeface(customFont);
        
        //Read Preferences File
        SharedPreferences myStats = this.getSharedPreferences("myStats", Context.MODE_PRIVATE);
        r_goldmedals = myStats.getInt("gold_medals", 0);
        r_silvermedals = myStats.getInt("silver_medals", 0);
        r_bronzemedals = myStats.getInt("bronze_medals", 0);
        r_points = myStats.getInt("points", 0);

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
					String url = "http://www.london2012.com/";  
					Intent i = new Intent(Intent.ACTION_VIEW);  
					i.setData(Uri.parse(url));  
					startActivity(i);
					break;

				case R.id.b_info:
					//GO TO SCREEN
					Intent info_Intent = new Intent(v.getContext(), Info.class);
					startActivityForResult(info_Intent, 0);
					break;
					
				case R.id.b_countdown:
					//DO
					countdown();
					break;
				}	
			}
		};

		b_play.setOnClickListener(handler);
		b_stats.setOnClickListener(handler);
		b_follow.setOnClickListener(handler);
		b_info.setOnClickListener(handler);
		b_countdown.setOnClickListener(handler);
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
    
    private void countdown()
    {
    	Calendar c = Calendar.getInstance();
    	int start_day = 208+1;	// 27 July + leap year
    	int current_day = c.get(Calendar.DAY_OF_YEAR);
    	int days = start_day-current_day;
        b_countdown.setText(""+days);
    }
    
    public OQ2012Activity getInstance()
   	{
   		return instance;
   	}

	//post score to OpenFeint leaderboards
	public void postLeaderboard(String BoardName, int score) {
		String UniqueID="0"; //initialize value but it should get overwritten
		long scoreValue = 0; //THE SCORE TO BE ADDED
		
		//Declare prefernces file
		SharedPreferences myStats = this.getSharedPreferences("myStats", Context.MODE_PRIVATE);
		SharedPreferences.Editor myStatsEditor = myStats.edit();
		
		if (BoardName=="Points")
		{
			UniqueID="1105627";
			w_points = r_points + score;
			scoreValue = (long)w_points;
			myStatsEditor.putInt("points", w_points);
		}
			
		if (BoardName=="Gold")
		{
			UniqueID="1105787";
			w_goldmedals = r_goldmedals + score;
			scoreValue = (long)w_goldmedals;
			myStatsEditor.putInt("gold_medals", w_goldmedals);
		}
		if (BoardName=="Silver")
		{
			UniqueID="1105797";
			w_silvermedals = r_silvermedals + score;
			scoreValue = (long)w_silvermedals;
			myStatsEditor.putInt("silver_medals", w_silvermedals);
		}
		if (BoardName=="Bronze")
		{
			UniqueID="1105807";
			w_bronzemedals = r_bronzemedals + score;
			scoreValue = (long)w_bronzemedals;
			myStatsEditor.putInt("bronze_medals", w_bronzemedals);
		}
		
		myStatsEditor.commit();
		
		Score s = new Score(scoreValue, null); // Second parameter is null to indicate that custom display text is not used.
		Leaderboard l = new Leaderboard(UniqueID);
		s.submitTo(l, new Score.SubmitToCB() {
			@Override public void onSuccess(boolean newHighScore) { 		// sweet, score was posted
				OQ2012Activity.this.setResult(Activity.RESULT_OK);
			}
			@Override public void onFailure(String exceptionMessage) {
				Toast.makeText(OQ2012Activity.this, "Error (" + exceptionMessage + ") posting score.", Toast.LENGTH_SHORT).show();
				OQ2012Activity.this.setResult(Activity.RESULT_CANCELED);
			}
		}); 
	}
}