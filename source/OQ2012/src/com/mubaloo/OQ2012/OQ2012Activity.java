package com.mubaloo.OQ2012;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class OQ2012Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
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