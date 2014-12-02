package w0269804.quizgame;

import java.util.ArrayList;

import android.content.Context;

public class QuizGame {
	
	int currentQuestion;// handles current question
	Questions questions; // handles questions
	
	/* constructor */
	public QuizGame(Context activityContext, int txtResourceID) {
		// TODO Find a way to implement a try-catch here.
		
		this.questions = new Questions(txtResourceID, activityContext);
		this.currentQuestion = 0;
	}
	
	/* return the number of questions */
	public int getTotalQuestions(){
		return this.questions.getNumberOfQuestions();
	}
	
	/* return the current question */
	public String getCurrentQuestion(){
		return this.questions.getQuestion(currentQuestion); // returns currentQuestion
	}
	
	/* return a list of the potential answers */
	public ArrayList<String> getCurrentAnswers(){
		return  this.questions.getAnswers(currentQuestion);
	}
	
	/* Check if answer is mapped to question in hash */
	public boolean isCorrectAnswer(String answer){
		return questions.checkAnswer(this.getCurrentQuestion(), answer);
	}
	
	/* advance the question number */
	public void nextQuestion(){
		int numberOfQuestions = questions.getNumberOfQuestions();
		if(this.currentQuestion + 1 < numberOfQuestions){
			this.currentQuestion++;
		}
	}
	
	/* returns true if last question is reached */
	public boolean isQuizOver(){
		return this.currentQuestion == questions.getNumberOfQuestions() - 1;
	}
	
	/* removes the last question */
	public void removeFirstQuestion(){
		this.questions.removeQuestion();
	}
	
	/* shuffles questions / answers */
	public void shuffleQuestions(){
		this.questions.shuffleQuestions();
	}
	
	// move the current question back
	public void prevQuestion(){
		if(this.currentQuestion > 0){
			this.currentQuestion--;
		}
	}
}
