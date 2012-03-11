package com.mubaloo.OQ2012;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Answer extends Activity implements OnClickListener {
	private Bundle bundleQ = new Bundle();
	int i;
	int score;
	long duration;
	int Gold;
	int Silver;
	int Bronze;
	OQ2012Activity oq2012activity = new OQ2012Activity();
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        
        TextView feedback;
        TextView message;
    
        feedback = (TextView)findViewById(R.id.feedback);
        message = (TextView)findViewById(R.id.message);
        
        Bundle bundle = this.getIntent().getExtras();
        String param1 = bundle.getString("Feedback");
        i = bundle.getInt("number");
        score = bundle.getInt("Score");
        duration = bundle.getLong("Duration");
        Gold = bundle.getInt("Gold");
        Silver = bundle.getInt("Silver");
        Bronze = bundle.getInt("Bronze");
        
        Button next = (Button) findViewById(R.id.next);
        next.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/LONDON2012.TTF"));
        next.setTextColor(Color.WHITE);
        
        if(param1.equalsIgnoreCase("true")) {
        	
        	String result = String.format("%.1f", duration/(float)1000);
        	View mlayout = findViewById(R.id.medal);
        	
        	score++;

        	feedback.setText("Congratulations, you answered correctly!\n You took "+result+" sec(s) to answer.\n");
        	
        	if(duration <= 2000) {
        		Gold++;
        		message.setText("A gold medal has been awarded to you!");
        		mlayout.setBackgroundResource(R.drawable.mgold);
        	}
        	else if((duration > 2000)&&(duration <= 5000)){
        		Silver++;
        		message.setText("A silver medal has been awarded to you!");
        		mlayout.setBackgroundResource(R.drawable.msilver);
        	}
        	else if((duration > 5000)&&(duration <= 8000)) {
        		Bronze++;
        		message.setText("A bronze medal has been awarded to you!");
        		mlayout.setBackgroundResource(R.drawable.mbronze);
        	}
        	else {
        		message.setText("you were too slow for a medal...");
        	}
       	
        }
        else feedback.setText("The answer was not correct or you didn't give an answer in time."); 

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
						if(Gold > 0) oq2012activity.getInstance().postLeaderboard("Gold", Gold);
						if(Silver > 0) oq2012activity.getInstance().postLeaderboard("Silver", Silver);
						if(Bronze > 0) oq2012activity.getInstance().postLeaderboard("Bronze", Bronze);
						oq2012activity.getInstance().postLeaderboard("Points", score);
						oq2012activity.getInstance().postLeaderboard("completed", oq2012activity.getInstance().getDaysLeft());
						i = 0;
						score = 0;
						Gold = 0;
						Silver = 0;
						Bronze = 0;
						Intent main_Intent = new Intent(v.getContext(), OQ2012Activity.class);
						startActivityForResult(main_Intent,0);
						Toast.makeText(v.getContext(), 
								"Thank you for playing. Come back for more questions tomorrow.",
								Toast.LENGTH_SHORT).show();
					}
					else {
						bundleQ.putInt("number", i);
						bundleQ.putInt("Score", score);
						bundleQ.putInt("Gold", Gold);
						bundleQ.putInt("Silver", Silver);
						bundleQ.putInt("Bronze", Bronze);
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
