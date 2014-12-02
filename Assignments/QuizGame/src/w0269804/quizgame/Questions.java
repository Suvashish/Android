package w0269804.quizgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;


import android.content.Context;
import android.util.Log;

public class Questions {
	
	final int NUMBER_OF_ANSWERS = 4; // max number of answers per question
	private Map <String, String> answerMap; // hashmap questions and answers
	private ArrayList<Question> questions; // question objects to handle quiz questions
	
	/* constructor */
	public Questions(int resId, Context ctx){
		createQuestions(ctx, resId); // create questions from text file
		hashQuestionAnswers(); // create answer hash 
		populateQuizQuestions(); // populate the answers
		shuffleQuestions(); // shuffle the questions
	}

	/* create a list of question objects */
	public void createQuestions(Context ctx, int resId)
	{
		this.questions = new ArrayList<Question>(); // new list on each call to create
	    String line; // holds current line

	    try {
		    InputStream inputStream = ctx.getResources().openRawResource(resId); // holds ioStream
		    InputStreamReader inputreader = new InputStreamReader(inputStream); // stream reader
		    BufferedReader buffreader = new BufferedReader(inputreader); // need to have a buffered reader to read lines
		    
	        while (( line = buffreader.readLine()) != null) { // while questions remain
	        	questions.add(new Question()); // add a new question for each line
				StringTokenizer tk = new StringTokenizer(line, ","); // tokenize line
	        	while (tk.hasMoreTokens()) { // while tokens exist
					   String lineToAdd = tk.nextToken();
					   if(lineToAdd != null){
						   if(tk.hasMoreTokens()){ // determine is line is question or answer
							   questions.get(questions.size()-1).setQuestion(lineToAdd); // add question to currect question
						   } else {
							   questions.get(questions.size()-1).setCorrectAnswer(lineToAdd); // add answer to current question
						   }
					   }
	        	}  	
	        }
	    } catch (IOException e) {
	    	Log.e("MISSING_FILE", "No valid file from which to read questions.", e);
	    }
	    
	 } // end method

	/*  create an answer hash map */
	private void hashQuestionAnswers(){
		this. answerMap = new HashMap<String, String>(this.questions.size());

		for (Question q : questions) {
			answerMap.put(q.getQuestion(), q.getAnswer());
		}
	}

	/* re-arrange question / answer order */
	public void shuffleQuestions(){

		Collections.shuffle(questions); // shuffle questions

		for (Question q : questions) {
			q.shuffleAnswers(); // shuffle answers
		}

	}

	/* add random definition to each question */
	public void populateQuizQuestions(){
		
		Random randomNumberGenerator = new Random();
		int randomDefinition = 0;
		
		for(int i = 0; i < questions.size();i++){
			
			// loop until we have NUMBER_OF_QUESTIONS answers added
			while(questions.get(i).getAnswers().size() < NUMBER_OF_ANSWERS){
			
				randomDefinition = randomNumberGenerator.nextInt(questions.size());
				String answerToAdd = questions.get(randomDefinition).getAnswer();// get the answer from a random question

				// if the current question does not already contain the answer, add it
				if(!questions.get(i).getAnswers().contains(answerToAdd)){
					questions.get(i).addAnswer(answerToAdd);
				}

			}
	}
	}
	
	/* return a set of answers for a question */
	public ArrayList<String> getAnswers(int questionNumber){
		return this.questions.get(questionNumber).getAnswers();
	}

	/* return a question */
	public String getQuestion(int questionNumber){
		return this.questions.get(questionNumber).getQuestion();
	}

	/* return the number of questions */
	public int getNumberOfQuestions(){
		return this.questions.size();
	}

	/* remove the first question */
	public void removeQuestion(){
		if(this.questions.size() > 0){
			questions.remove(0);
		}
	}
	
	/* check if an answer is correct */
	public boolean checkAnswer(String questionText, String answer){
		return this.answerMap.get(questionText).equals(answer);
}

	

	private class Question{
		
		private String question; // the text of the question
		private String correctAnswer; // the correct answer
		private ArrayList<String> answers; // the list of possible answers
	
		// constructor
		public Question(){
			this.answers = new ArrayList<String>();
		}
		
		// set the question text
		public void setQuestion(String question){
			this.question = question;
		}

		// set the correct answere
		public void setCorrectAnswer(String correctAnswer){
			this.correctAnswer = correctAnswer;
			addAnswer(correctAnswer);
		}

		// add an answer
		public void addAnswer(String answerText){
			this.answers.add(answerText);
		}

		// remove the last answer
		public void removeAnswer(){
			if(this.answers.size() > 0){
				this.answers.remove(this.answers.size() - 1);
			}
		}
		
		// return all possible answers
		public ArrayList<String> getAnswers(){
			return this.answers;
		}
		
		// return the correct answer
		public String getAnswer(){
			return this.correctAnswer;
		}
		
		// get the question text
		public String getQuestion(){
			return this.question;
		}
		
		// shuffle the answers
		public void shuffleAnswers(){
			Collections.shuffle(this.answers);
		}
		
	
	}


	
}
	