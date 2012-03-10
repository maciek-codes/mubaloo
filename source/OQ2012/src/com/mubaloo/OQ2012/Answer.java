package com.mubaloo.OQ2012;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Answer extends Activity implements OnClickListener {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        TextView feedback;
        Button next_btn;
    
        feedback = (TextView)findViewById(R.id.feedback);
        next_btn = (Button)findViewById(R.id.next);

    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
