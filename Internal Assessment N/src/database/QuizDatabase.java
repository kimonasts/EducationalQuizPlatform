package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Question;
import Model.Quiz;

public class QuizDatabase {

	private static int nextQuizID = 9;
	private static int nextQuestionID = 20;
	private static ArrayList<Quiz> quizList = new ArrayList<Quiz>();

	static {
		initializeDatabase();
	}

	private static void initializeDatabase() {
		/* Load the quiz database from a csv file */
		try {
			// the file to be opened for reading
			FileInputStream dbFile = new FileInputStream("quizDatabase.txt");
			Scanner sc = new Scanner(dbFile); // file to be scanned

			Quiz curQuiz = null; // the current quiz we are working on
			int maxQuizID = 1; // the maximum quiz id we have found so far
			int maxQuestionID = 1;// the maximum question id we have found so far
			// while there is another line to read
			while (sc.hasNextLine()) {
				String line = sc.nextLine(); // read next line
				// break it into an array using the semicolon as the separator
				String parts[] = line.split(";");
				/*
				 * if the first word of the broken line is "quiz"
				 * 
				 */
				if (parts[0].equals("quiz")) {

					/*
					 * if curQuiz is not null that means that during the previous iterations of the
					 * loop, we have loaded and built quiz object along with its questions, and now
					 * it is time to move on to the next quiz. So we add it to the databaze's
					 * quizList and assign a new empty quiz object to curQuiz, that is going to be
					 * filled in the next iterations.
					 */
					if (curQuiz != null) {
						quizList.add(curQuiz);
					}
					curQuiz = new Quiz(parts[2]);

					curQuiz.setQuizID(Integer.parseInt(parts[1]));
					int curQuizID = Integer.parseInt(parts[1]);
					if (curQuizID > maxQuizID) {
						maxQuizID = curQuizID;
					}
					curQuiz.setFinalQuizLength(Integer.parseInt(parts[3]));
					curQuiz.setMin(Integer.parseInt(parts[4]));
					curQuiz.setSec(Integer.parseInt(parts[5]));
				} else if (parts[0].equals("ques")) {
					Question q = new Question(curQuiz, Integer.parseInt(parts[1]), // question id
							parts[2], // name
							parts[3], // A1
							parts[4], // A2
							parts[5], // A3
							parts[6], // A4
							Integer.parseInt(parts[7]) // correct answer
					);
					int curQuestionID = Integer.parseInt(parts[1]);
					if (curQuestionID > maxQuestionID) {
						maxQuestionID = curQuestionID;
					}
					curQuiz.addQuestion(q);
				}
			}
			quizList.add(curQuiz);
			nextQuizID = maxQuizID + 1;
			nextQuestionID = maxQuestionID + 1;

			sc.close(); // closes the scanner
			dbFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		Quiz qz1=new Quiz("First Quiz");
//		Question q1=new Question(
//				qz1,
//				"What is the name of Messi?",
//				"Lionel",
//				"Edurardo",
//				"Claudio",
//				"Antonio"
//				,1);
//		Question q2=new Question(
//				qz1,
//				"What time does the sun sets?",
//				"6",
//				"7",
//				"8",
//				"9"
//				,1);
//		Question q3=new Question(
//				qz1,
//				"What fruit is yellow?",
//				"banana",
//				"orange",
//				"cherry",
//				"apple"
//				,1);
//		qz1.addQuestion(q1);
//		qz1.addQuestion(q2);
//		qz1.addQuestion(q3);
//		quizList.add(qz1);

	}

	public static void saveAllToCSV() {
		try {

			/*
			 * Open the quizDatabase.txt file where all the quizzes are stored The contents
			 * are erased automatically and that is ok because the updated data will be
			 * saved again from scratch.
			 */
			PrintWriter out = new PrintWriter("quizDatabase.txt");
			ArrayList<Quiz> quizList = getQuizList(); // get the list of all the quizzes
			for (Quiz quiz : quizList) { // for each quiz
				/*
				 * prints in a line all the important properties of the quiz, separated using
				 * semicolons
				 */
				out.println("quiz;" + quiz.getQuizID() + ";" + quiz.getName() + ";" + quiz.getFinalQuizLength() + ";"
						+ quiz.getMin() + ";" + quiz.getSec() + ";");
				for (Question question : quiz.getQuestions()) {
					/*
					 * prints in a line all the important properties of the question, separated
					 * using semicolons
					 */
					out.println("ques;" + question.getQuestionID() + ";" + question.getText() + ";" + question.getA1()
							+ ";" + question.getA2() + ";" + question.getA3() + ";" + question.getA4() + ";"
							+ question.getCorrAns() + ";");
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getNextQuizID() {
		return nextQuizID++;
	}

	public static int getNextQuestionID() {
		return nextQuestionID++;
	}

	public static void addQuiz(Quiz qz) {
		quizList.add(qz);
	}

	public static Quiz getQuiz(int quizID) {
		for (int j = 0; j < quizList.size(); j++) {
			Quiz qz = quizList.get(j);
			if (qz.getQuizID() == quizID) {
				return qz;
			}
		}
		return null;
	}

	public static ArrayList<Quiz> getQuizList() {
		return quizList;
	}

	public static void deleteQuiz(Quiz qz) {
		quizList.remove(qz);
	}
}
