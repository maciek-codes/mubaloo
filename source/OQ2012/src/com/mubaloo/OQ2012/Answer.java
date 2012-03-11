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
        
        
        if(param1.equalsIgnoreCase("true")) {
        	score++;
        	
        	feedback.setText("Congratulations, you answered correctly!\n You took "+duration+"ms to answer.");
        	
        	View mlayout = findViewById(R.id.medal);
            
        	
        	if(duration <= 3000) {
        		Gold++;
        		message.setText("A gold medal has been awarded to you!");
        		mlayout.setBackgroundResource(R.drawable.mgold);
        	}
        	if((duration > 3000)&&(duration <= 6000)){
        		Silver++;
        		message.setText("A silver medal has been awarded to you!");
        		mlayout.setBackgroundResource(R.drawable.msilver);
        	}
        	if((duration > 6000)&&(duration <= 12000)) {
        		Bronze++;
        		message.setText("A bronze medal has been awarded to you!");
        		mlayout.setBackgroundResource(R.drawable.mbronze);
        	}
       	
        }
        else feedback.setText("The answer is not correct. I am sorry."); 

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
