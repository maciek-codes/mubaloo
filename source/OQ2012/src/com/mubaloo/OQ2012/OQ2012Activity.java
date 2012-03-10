package com.mubaloo.OQ2012;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.openfeint.api.OpenFeint;
import com.openfeint.api.OpenFeintDelegate;
import com.openfeint.api.OpenFeintSettings;
import com.openfeint.api.ui.Dashboard;


public class OQ2012Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	static final String gameName = "Olympic Quiz 2012";
	static final String gameID = "464542";
	static final String gameKey = "ADmARQITd7DzrPxJVKCfQ";
	static final String gameSecret = "fAaRrBhDRGqhPNcdMRfzhYWPzulq1cNhtglQLMPUhw"; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Map<String, Object> options = new HashMap<String, Object>();
    	//line below used to set orientation
    	options.put(OpenFeintSettings.RequestedOrientation, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	OpenFeintSettings settings = new OpenFeintSettings(gameName, gameKey, gameSecret, gameID);
    	OpenFeint.initialize(this, settings, new OpenFeintDelegate() {});
    	
    	//opens the main openfeint dashboard
    	Dashboard.open();

        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello, Olympic Quiz 20122");
        setContentView(tv);
        setContentView(R.layout.main);
        Button button1;
        button1 = (Button)findViewById(R.id.button1); 
    }
    
    public void onClick(View v) {
		if (v.getId() == R.id.button1) {
		   Intent intent = new Intent(this, Question.class);
		   startActivity(intent);
		}

    }
		
}