package com.mubaloo.OQ2012;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    //private ImageView iv_torch;
    //private AnimationDrawable ad_torch;
	
    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oq2012activity);
        instance = this;
        
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
        
        /*
        iv_torch = (ImageView) findViewById(R.id.iv_torch);
        iv_torch.setBackgroundResource(R.drawable.torch);
        ad_torch = (AnimationDrawable) iv_torch.getBackground();
        ad_torch.start();
        */
        
        /*ad_torch = new AnimationDrawable();
        ad_torch.addFrame(getResources().getDrawable(R.drawable.torch1),200);
        ad_torch.addFrame(getResources().getDrawable(R.drawable.torch2),200);
        ad_torch.addFrame(getResources().getDrawable(R.drawable.torch3),200);
        ad_torch.addFrame(getResources().getDrawable(R.drawable.torch4),200);
        ad_torch.addFrame(getResources().getDrawable(R.drawable.torch5),200);
        ad_torch.setOneShot(false);
        
        iv_torch = (ImageView) findViewById(R.id.iv_torch);
        iv_torch.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        iv_torch.setImageDrawable(ad_torch);
        ad_torch.start();*/
        
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
						//Intent info_Intent = new Intent(v.getContext(), Info.class);
						//startActivityForResult(info_Intent, 0);
					break;
					
					case R.id.b_stats:
						//GO TO SCREEN
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
    }
}