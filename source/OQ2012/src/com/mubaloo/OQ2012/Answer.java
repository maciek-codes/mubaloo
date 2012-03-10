package com.mubaloo.OQ2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Answer extends Activity implements OnClickListener {
	private Bundle bundleQ = new Bundle();
	int i;
	int score;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        
        TextView feedback;
    
        feedback = (TextView)findViewById(R.id.feedback);
        
        Bundle bundle = this.getIntent().getExtras();
        String param1 = bundle.getString("Feedback");
        i = bundle.getInt("number");
        score = bundle.getInt("Score");
        
        Button next = (Button) findViewById(R.id.next);
        
        if(param1.equalsIgnoreCase("true")){
        	score++;
        	feedback.setText(score + " "+ i + "Congrats");
        	
        }
        else feedback.setText("!Congrats"); 

		View.OnClickListener handler = new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				switch(v.getId())
				{
				case R.id.next:
					//GO TO SCREEN
					i++;
					if(i == 3) {
						i = 0;
						score = 0;
						Intent main_Intent = new Intent(v.getContext(), OQ2012Activity.class);
						startActivityForResult(main_Intent,0);
					}
					else {
						bundleQ.putInt("number", i);
						bundleQ.putInt("Score", score);
						Intent newQ_Intent = new Intent(v.getContext(), Question.class);
						newQ_Intent.putExtras(bundleQ);
						startActivityForResult(newQ_Intent,0);
					}
					
					break;
				}
			}
		};
		next.setOnClickListener(handler);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.next:
			break;
			// TODO Auto-generated method stub
		}
	}
}
