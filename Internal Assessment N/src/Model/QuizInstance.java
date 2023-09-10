package Model;

import java.util.ArrayList;

public class QuizInstance {

	private Quiz quiz;

	public QuizInstance(Quiz quiz) {
		this.quiz = quiz;
	}

	public Quiz getQuiz() {
		return quiz;
	}
//	public void setQuiz(Quiz quiz) {
//		this.quiz = quiz;
//	}

	ArrayList<Question> generatedQuestions = new ArrayList<Question>();

	public void addQuestion(Question q) {
		generatedQuestions.add(new Question(q));
	}

	public ArrayList<Question> getQuestionList() {
		return generatedQuestions;
	}
}
