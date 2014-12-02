package w0269804.quizgame;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EvaluationActivity extends ActionBarActivity {

	TextView tvEvaluation;
	String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation);
		
		tvEvaluation = (TextView) findViewById(R.id.txtEvaluation);
		
		Intent intent = getIntent(); // get the incoming intent
		Bundle extras= intent.getExtras(); // get the bundle from the intent
		
		// Set the evaluation text box
		name = extras.getString("NAME");
		String correct = extras.getString("CORRECT");
		String total = extras.getString("TOTAL");
		String evalString = "Good job " + name + "! You scored " + correct + " out of " + total + "!";
		
		tvEvaluation.setText(evalString); // display the score
		
	}
	
	/* go back to main activity */
	public void restart(View view) {
    
    		Intent i = new Intent(this, QuizActivity.class);
    		Bundle extras = new Bundle();// create bundle object
    		extras.putString("NAME",  name);// fill bundle
    		i.putExtras(extras); // put the extras in the bundle
    		startActivity(i); // send the bundle to the next activity
    
    }


}
