package com.mubaloo.OQ2012;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Question extends Activity implements OnClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        TextView countryName;
        TextView countryQuestion;
        ListView answer;
        countryName = (TextView)findViewById(R.id.countryName);
        countryQuestion = (TextView)findViewById(R.id.countryQuestion);
        answer = (ListView)findViewById(R.id.answer);

    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
