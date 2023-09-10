package Model;

import java.util.ArrayList;

import database.QuizDatabase;

public class Question {

	private Quiz parentQuiz; /* The quiz that this question belongs to */
	private int questionID;
	private boolean isCompleted = false;
	private String text; /* */
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private int corrAns;
	private int givenAnswer;

	ArrayList<Question> questionList = new ArrayList<Question>();

	public Question(Quiz parentQuiz, String text, String a1, String a2, String a3, String a4, int corrAns) {
		this.questionID = QuizDatabase.getNextQuestionID();
		this.parentQuiz = parentQuiz;
		this.text = text;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.corrAns = corrAns;
		this.givenAnswer = -1;
		checkIsCompleted();
	}

	public Question(Quiz parentQuiz, int questionID, String text, String a1, String a2, String a3, String a4,
			int corrAns) {
		this.parentQuiz = parentQuiz;
		this.questionID = questionID;
		this.text = text;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.corrAns = corrAns;
		this.givenAnswer = -1;
		checkIsCompleted();
	}

	public Question(Question otherQ) {
		this.parentQuiz = otherQ.parentQuiz;
		this.questionID = otherQ.questionID;
		this.text = otherQ.text;
		this.a1 = otherQ.a1;
		this.a2 = otherQ.a2;
		this.a3 = otherQ.a3;
		this.a4 = otherQ.a4;
		this.corrAns = otherQ.corrAns;
		this.isCompleted = true;
		this.givenAnswer = -1;
	}


	public boolean checkIsCompleted() {
		if (text.equals("") || a1.equals("") || a2.equals("") || a3.equals("") || a3.equals("") || corrAns == 0) {
			this.isCompleted = false;
		} else {
			this.isCompleted = true;
		}
		return this.isCompleted;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA1() {
		return a1;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA2() {
		return a2;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA3() {
		return a3;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA4() {
		return a4;
	}

	public void setCorrAns(int corrAns) {
		this.corrAns = corrAns;
	}

	public int getCorrAns() {
		return this.corrAns;
	}

	public int getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(int givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public boolean checkAnswer() {
		return this.givenAnswer == this.corrAns;
	}

	public String getCorrAnsText() {
		switch (corrAns) {
		case 1:
			return a1;
		case 2:
			return a2;
		case 3:
			return a3;
		case 4:
			return a4;
		default:
			return "";
		}
	}

	public boolean isAnsweredCorrectly() {
		if (corrAns == givenAnswer) {
			return true;
		}

		return false;
	}

	public void reset() {
		this.isCompleted = false;
		this.text = "";
		this.a1 = "";
		this.a2 = "";
		this.a3 = "";
		this.a4 = "";
		this.corrAns = 1;
	}

}
