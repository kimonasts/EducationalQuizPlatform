package Model;

import java.util.ArrayList;
import java.util.Random;

import database.QuizDatabase;

public class Quiz {

	private int quizID;
	private String name;
	private int min;
	private int sec;
	// question list can have lets say 20 items, and the quiz length (final quiz
	// length) can have 10
	// todo refactor name
	private int finalQuizLength;
	ArrayList<Question> questionList = new ArrayList<Question>();

	public Quiz(String name) {
		this.quizID = QuizDatabase.getNextQuizID();
		this.name = name;
		this.min = 10;
		this.sec = 0;
		this.finalQuizLength = 1;

	}

	public void addQuestion(Question q) {
		questionList.add(q);
	}

	public ArrayList<Question> getQuestions() {
		return questionList;
	}

	public Question getQuestion(int questionID) {
		for (int i = 0; i < questionList.size(); i++) {
			Question q = questionList.get(i);
			if (q.getQuestionID() == questionID) {
				return q;

			}
		}
		return null;
	}

	public void removeQuestion(Question q) {
		questionList.remove(q);
	}

	public int getQuizID() {
		return quizID;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	public String getName() {
		return name;
	}

	public void setName(String quizName) {
		this.name = quizName;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public int getFinalQuizLength() {
		return finalQuizLength;
	}

	public void setFinalQuizLength(int length) {
		this.finalQuizLength = length;
	}

	public QuizInstance generateQuizInstance() {
		// how many questions a student has to answer to complete the quiz
		int finalQuizLength = this.getFinalQuizLength();

		// QuizIntance object that includes only some of the available questions of the
		// quiz
		QuizInstance qz = new QuizInstance(this);

		ArrayList<Question> clonedList = new ArrayList<Question>();

		// fill in the cloned list with all the elements
		// of this quiz's question - make a perfect copy
		for (Question q : questionList) {
			clonedList.add(new Question(q));
		}

		Random r = new Random();
		// repeat as many times as the number of questions we want
		for (int i = 0; i < finalQuizLength; i++) {
			// choose a random number from 0 to the size of the cloned list minus one
			// this number represents the position on the cloned list from which
			// we will remove an item
			int randomPosition = r.nextInt(clonedList.size());
			Question chosenQuestion;
			// a questioned is randomly removed from the cloned list and is moved
			// to the question list of the QuizInstance
			chosenQuestion = clonedList.remove(randomPosition);
			qz.addQuestion(chosenQuestion);
		}
		return qz;

	}
}
