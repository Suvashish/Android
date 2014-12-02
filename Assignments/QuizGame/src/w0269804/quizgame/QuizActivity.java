package w0269804.quizgame;

import java.util.ArrayList;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends ActionBarActivity {
	
	QuizGame quizGame; // controller
	String name;
	RadioGroup radGroup;
	Button btnNext; 
	TextView txtView; 
	ArrayList<RadioButton> radBtns;
	
	int correctAnswerCount = 0;
	
	RadioButton answOne; // the answer radio buttons
	RadioButton answTwo;
	RadioButton answThree;
	RadioButton answFour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
			
		// =============== LINK FORM ELEMENTS =================//
		txtView = (TextView) findViewById(R.id.txtQuestion); 
		btnNext = (Button) findViewById(R.id.btnNext);
		answOne = (RadioButton) findViewById(R.id.radAnswerOne);
		answTwo = (RadioButton) findViewById(R.id.radAnswerTwo);
		answThree = (RadioButton) findViewById(R.id.radAnswerThree);
		answFour = (RadioButton) findViewById(R.id.radAnswerFour);
		radGroup = (RadioGroup) findViewById(R.id.radGroupAnswers);
		//======================================================//
		
		// Finesse, make an iterable list of radio buttons
		radBtns = new ArrayList<RadioButton>(); // arrayList of radBtns
		radBtns.add(answOne);
		radBtns.add(answTwo);
		radBtns.add(answThree);
		radBtns.add(answFour); 
	
		answOne.setChecked(true);
		
		// =============== IO OPERATIONS =================//
		try{
		Context ctx = this.getApplicationContext(); // get this context (for IO operations)
    	int i = this.getResources().getIdentifier // get handle on the resource id (for questions)
    			("questions","raw", this.getPackageName());
		quizGame = new QuizGame(ctx, i); // the quiz game (controller) will handle the questions
		} catch(Resources.NotFoundException e){ // exception requirement
			Log.e("MISSING_FILE", "No valid file from which to read questions.", e);
		}
		// =============== INTENT OPERATIONS =================//
		Intent intent = getIntent(); 
		Bundle extras= intent.getExtras(); 
		name = extras.getString("NAME"); // bundle will be used on end of game evaluation
		setCurrentQuestionText(); 
		
		
		// =============== LISTENERS =================//
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showQuestionEvaluation(); // show toast to indicate correct or
											// false
				if (!quizGame.isQuizOver()) {
					quizGame.nextQuestion(); // increment question
					setCurrentQuestionText(); // set up the next question.
				} else {
					gameOver();
				}
			}
		});
		
	}
	
	/* handles end of game state, sends bundle to evaluation and resets score*/
	public void gameOver(){
		
    	Intent i = new Intent(this, EvaluationActivity.class);
		
    	// send bundle to evaluation activity
		Bundle extras = new Bundle();//create bundle object
		extras.putString("CORRECT",  String.valueOf(correctAnswerCount));//fill bundle
		extras.putString("TOTAL",  String.valueOf(quizGame.getTotalQuestions()));//fill bundle
		extras.putString("NAME", name);//fill bundle
		i.putExtras(extras);
		
		// RESET THE STATE OF THE QUIZ
		quizGame.shuffleQuestions();
		correctAnswerCount = 0;
		
		startActivity(i);
	}
	
	/* gets an array list from controller and displays the answers */
	private void setCurrentQuestionText(){
		
		ArrayList<String> answerList = quizGame.getCurrentAnswers(); // create an array of answers
		txtView.setText(quizGame.getCurrentQuestion()); // set text to currentQuestion

		// loop through each button
		for(int i = 0; i < radBtns.size(); i++){ // loop 
			radBtns.get(i).setText(answerList.get(i)); // set text of radio button
		}
		
		
	}
	
	// show question evaluation in toast
	private void showQuestionEvaluation() {

		int selectedId = radGroup.getCheckedRadioButtonId(); // get the ID of the selected radio button
		RadioButton selectedRadButton = (RadioButton) findViewById(selectedId); // create a new radio button
		String answer = (String) selectedRadButton.getText(); // pull the text off the radio button and store in answer
	
		/* Uncomment the lines below for tonnes o' toast */
		
		//String result; // the message to be printed

		if (quizGame.isCorrectAnswer(answer)) { // if the question text matches answer in hash.
			//result = "That is correct!"; // show a correct response 
			correctAnswerCount++;
		} else {
			//result = "That is not correct!";
		}

		//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG) // show the toast
				//.show();

	}
	
}
