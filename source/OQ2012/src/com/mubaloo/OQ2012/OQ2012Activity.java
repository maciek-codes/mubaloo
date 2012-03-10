package com.mubaloo.OQ2012;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

	//Openfeint
	static final String gameName = "Olympic Quiz 2012";
	static final String gameID = "464542";
	static final String gameKey = "ADmARQITd7DzrPxJVKCfQ";
	static final String gameSecret = "fAaRrBhDRGqhPNcdMRfzhYWPzulq1cNhtglQLMPUhw"; 

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

		//GRAPHICS
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
		b_countdown.setText("DAYS TO GO: XXX");
		b_countdown.setTextColor(Color.WHITE);
		b_countdown.setTypeface(customFont);

		/*ImageView iv_torch = (ImageView)findViewById(R.id.iv_torch);
        iv_torch.setBackgroundResource(R.drawable.torch);
        AnimationDrawable frameAnimation = (AnimationDrawable) iv_torch.getBackground();
        frameAnimation.start();*/

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

				case R.id.b_follow: {
					//GO TO SCREEN
					postLeaderboard("Gold", 25);
				}
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

	//post score to OpenFeint leaderboards
	public void postLeaderboard(String BoardName, int score) {
		String UniqueID="0"; //initialize value but it should get overwritten
		if (BoardName=="Points") UniqueID="1105627";
		if (BoardName=="Gold") UniqueID="1105787";
		if (BoardName=="Silver") UniqueID="1105797";
		if (BoardName=="Bronze") UniqueID="1105807";
		long scoreValue = (long) score; //THE SCORE TO BE ADDED
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