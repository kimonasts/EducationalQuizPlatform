package student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.SwingConstants;

import Model.Question;
import Model.Quiz;
import Model.QuizInstance;
import database.QuizDatabase;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;

public class StudentUI {

	private enum ScreensUI {
		IntroScreen, QuestionScreen, OutroScreen
	}; //

	private ScreensUI currentScreen;

	private JFrame frame;
	private JFrame previousWindow;

	private JLayeredPane screenContainer;
	private JPanel questionPanel;
	private JPanel outroPanel;

	private QuizInstance quizInstance;
	private int currentQIndex = -1;
	private int quizScore;
	private String email;
	private boolean isQuizFinished = false;
	private boolean timeIsUp = false;
	private int endMin;
	private int endSec;
	private int minsElapsed;
	private int secsElapsed;

	private JPanel introPanel;
	private JLabel lblWelcomeToThe;
	private JButton btnStart;

	private JLabel lblQuestionText;
	private JLabel lblQuizProgress;
	private JRadioButton rdbtnA1;
	private JRadioButton rdbtnA2;
	private JRadioButton rdbtnA3;
	private JRadioButton rdbtnA4;
	private ButtonGroup rdbtnGroup;
	private JButton btnNextQuestion;
	private JButton btnPreviousQuestion;
	private JTextField tfTimeRemaining;
	private JTextField tfOutroScore;
	private JTextField tfOutroTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentUI window = new StudentUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentUI() {
		initialize();
	}

	public StudentUI(JFrame previousWindow, String email, QuizInstance quizin) {
		this.quizInstance = quizin;
		this.email = email;
		this.previousWindow = previousWindow;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(100, 149, 237));
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 746, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		screenContainer = new JLayeredPane();
		screenContainer.setBounds(12, 100, 697, 433);
		frame.getContentPane().add(screenContainer);
		screenContainer.setLayout(new CardLayout(0, 0));

		introPanel = new JPanel();
		introPanel.setBackground(new Color(255, 165, 0));
		introPanel.setForeground(new Color(255, 255, 255));
		screenContainer.add(introPanel, "name_479964408690400");
		introPanel.setLayout(null);

		lblWelcomeToThe = new JLabel("Welcome to the Quiz!");
		lblWelcomeToThe.setBounds(218, 110, 240, 31);
		lblWelcomeToThe.setFont(new Font("Tahoma", Font.PLAIN, 25));
		introPanel.add(lblWelcomeToThe);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentUI.this.showScreenPanel(ScreensUI.QuestionScreen);
				(new Thread(new Runnable() {
					public void run() {
						Quiz curQuiz = quizInstance.getQuiz();
						for (endMin = curQuiz.getMin(); endMin >= 0; endMin--) {// TODO
							if (endMin == curQuiz.getMin()) {
								endSec = curQuiz.getSec();
							} else {
								endSec = 59;
							}
							for (; endSec >= 0; endSec--) {

								String timeRemaining;

								if (endSec < 10) {
									timeRemaining = endMin + ":0" + endSec;

								} else {
									timeRemaining = endMin + ":" + endSec;
								}

								tfTimeRemaining.setText(timeRemaining);

								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								if (isQuizFinished) {
									return;
								}
							}
						}
						JOptionPane.showMessageDialog(null, "Sorry, time is up!", "Quiz End",
								JOptionPane.WARNING_MESSAGE);
						timeIsUp = true;

						submitQuiz();
						// TODO
					}
				})).start();
			}
		});
		btnStart.setBounds(294, 234, 97, 25);
		introPanel.add(btnStart);

		questionPanel = new JPanel();
		questionPanel.setBackground(new Color(255, 165, 0));
		screenContainer.add(questionPanel, "name_477641282995000");
		questionPanel.setLayout(null);

		lblQuestionText = new JLabel("Question Text");
		lblQuestionText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQuestionText.setBounds(12, 65, 673, 38);
		questionPanel.add(lblQuestionText);

		lblQuizProgress = new JLabel("");
		lblQuizProgress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblQuizProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuizProgress.setBounds(320, 13, 56, 16);
		questionPanel.add(lblQuizProgress);

		rdbtnA1 = new JRadioButton("Answer1");
		rdbtnA1.setBackground(new Color(255, 165, 0));
		rdbtnA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Question q = getCurrentQuestion();
				q.setGivenAnswer(1);
			}
		});
		rdbtnA1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnA1.setBounds(129, 135, 500, 25);
		rdbtnA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Question q = getCurrentQuestion();
				q.setGivenAnswer(1);
			}
		});
		questionPanel.add(rdbtnA1);

		rdbtnA2 = new JRadioButton("Answer2");
		rdbtnA2.setBackground(new Color(255, 165, 0));
		rdbtnA2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnA2.setBounds(129, 176, 500, 25);
		rdbtnA2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Question q = getCurrentQuestion();
				q.setGivenAnswer(2);
			}
		});
		questionPanel.add(rdbtnA2);

		rdbtnA3 = new JRadioButton("Answer3");
		rdbtnA3.setBackground(new Color(255, 165, 0));
		rdbtnA3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnA3.setBounds(129, 221, 458, 25);
		rdbtnA3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Question q = getCurrentQuestion();
				q.setGivenAnswer(3);
			}
		});
		questionPanel.add(rdbtnA3);

		rdbtnA4 = new JRadioButton("Answer4");
		rdbtnA4.setBackground(new Color(255, 165, 0));
		rdbtnA4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnA4.setBounds(129, 265, 458, 25);
		rdbtnA4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Question q = getCurrentQuestion();
				q.setGivenAnswer(4);
			}
		});
		questionPanel.add(rdbtnA4);

		rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnA1);
		rdbtnGroup.add(rdbtnA2);
		rdbtnGroup.add(rdbtnA3);
		rdbtnGroup.add(rdbtnA4);

		btnNextQuestion = new JButton("Next Question");
		btnNextQuestion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNextQuestion.setBounds(468, 320, 161, 32);
		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Question> qlist = quizInstance.getQuestionList();
				if (currentQIndex < qlist.size() - 1) {
					currentQIndex++;
					if (currentQIndex == qlist.size() - 1) {// if we just went to the last question
						btnNextQuestion.setText("Submit Quiz");
					}
				} else { // user clicked on "Submit Quiz"
							// here quiz submit happens
					submitQuiz();
					return;
				}
				refreshQuestionUI();
			}
		});
		questionPanel.add(btnNextQuestion);

		btnPreviousQuestion = new JButton("Previous Question");
		btnPreviousQuestion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPreviousQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentQIndex > 0) {
					currentQIndex--;
					btnNextQuestion.setText("Next Question");
				} else
					return;
				refreshQuestionUI();
			}
		});
		btnPreviousQuestion.setBounds(40, 320, 161, 32);
		questionPanel.add(btnPreviousQuestion);

		tfTimeRemaining = new JTextField();
		tfTimeRemaining.setEditable(false);
		tfTimeRemaining.setBounds(599, 10, 73, 25);
		questionPanel.add(tfTimeRemaining);
		tfTimeRemaining.setColumns(10);

		JLabel lblTimeRemaining = new JLabel("Time Remaining:");
		lblTimeRemaining.setBounds(486, 14, 101, 16);
		questionPanel.add(lblTimeRemaining);

		outroPanel = new JPanel();
		outroPanel.setBackground(new Color(255, 165, 0));
		screenContainer.add(outroPanel, "name_477641287655900");
		outroPanel.setLayout(null);

		JLabel lblThankYouFor = new JLabel("Thank you for completing the quiz");
		lblThankYouFor.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblThankYouFor.setBounds(129, 53, 474, 50);
		outroPanel.add(lblThankYouFor);

		JLabel lblYourScoreIs = new JLabel("Your score is:");
		lblYourScoreIs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYourScoreIs.setBounds(34, 161, 120, 32);
		outroPanel.add(lblYourScoreIs);

		JLabel lblYouCompletedThe = new JLabel("You completed the quiz in:");
		lblYouCompletedThe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYouCompletedThe.setBounds(34, 217, 197, 32);
		outroPanel.add(lblYouCompletedThe);

		tfOutroScore = new JTextField();
		tfOutroScore.setEditable(false);
		tfOutroScore.setBounds(145, 167, 116, 22);
		outroPanel.add(tfOutroScore);
		tfOutroScore.setColumns(10);

		tfOutroTime = new JTextField();
		tfOutroTime.setEditable(false);
		tfOutroTime.setColumns(10);
		tfOutroTime.setBounds(232, 223, 116, 22);
		outroPanel.add(tfOutroTime);

		JButton btnExit = new JButton("Done");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentUI.this.hideUI();
				previousWindow.show();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExit.setBounds(289, 335, 116, 32);
		outroPanel.add(btnExit);

		JLabel lblStudent = new JLabel("STUDENT");
		lblStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudent.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblStudent.setBounds(256, 30, 176, 38);
		frame.getContentPane().add(lblStudent);

		showScreenPanel(ScreensUI.IntroScreen);
	}

	private void submitQuiz() {
		ArrayList<Question> qlist = quizInstance.getQuestionList();

		quizScore = 0;
		for (Question q : qlist) {
			if (q.checkAnswer()) {
				quizScore++;
			}
		}

		isQuizFinished = true;

		Quiz curQuiz = quizInstance.getQuiz();

		int quizSec = curQuiz.getMin() * 60 + curQuiz.getSec();
		int curTimeInSec = endMin * 60 + endSec;
		int timeDiff = quizSec - curTimeInSec;

		if (timeIsUp) {
			minsElapsed = curQuiz.getMin();
			secsElapsed = curQuiz.getSec();
		} else {
			minsElapsed = timeDiff / 60;
			secsElapsed = timeDiff % 60;
		}
		String timeElapsed;

		if (secsElapsed < 10) {
			timeElapsed = minsElapsed + ":0" + secsElapsed;
		} else {
			timeElapsed = minsElapsed + ":" + secsElapsed;
		}

		try {
			boolean fileIsNew = false;
			File f = new File("answers-" + quizInstance.getQuiz().getQuizID() + ".txt");
			if (!f.exists()) {
				fileIsNew = true;
			}

			FileWriter fileWriter = new FileWriter("answers-" + quizInstance.getQuiz().getQuizID() + ".txt", true); // Set
																													// true
																													// for
																													// append
																													// mode
			PrintWriter out = new PrintWriter(fileWriter);
			if (fileIsNew) {
				out.println(quizInstance.getQuiz().getQuizID() + ";" + quizInstance.getQuiz().getName() + ";");
			}

			out.println(email + ";" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + ";"
					+ quizScore + "/" + quizInstance.getQuiz().getFinalQuizLength() + ";" + timeElapsed + ";");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		tfOutroScore.setText("" + quizScore + "/" + curQuiz.getFinalQuizLength());
		if (minsElapsed == 0) {
			tfOutroTime.setText("" + secsElapsed + " seconds");
		} else {
			tfOutroTime.setText("" + timeElapsed + " minutes");
		}
		showScreenPanel(ScreensUI.OutroScreen);
		return;

	}

	public void showScreenPanel(ScreensUI screen) // You decide the screen that you are going to display
	{
		switch (screen) {
		case IntroScreen:
//				refreshQuizList(); 
			screenContainer.removeAll();
			screenContainer.add(introPanel);
			screenContainer.repaint();
			screenContainer.revalidate();
			currentScreen = ScreensUI.IntroScreen;
			break;
		case QuestionScreen:
			refreshQuestionUI();
			screenContainer.removeAll();
			screenContainer.add(questionPanel);
			screenContainer.repaint();
			screenContainer.revalidate();
			currentScreen = ScreensUI.QuestionScreen;
			break;
		case OutroScreen:
			screenContainer.removeAll();
			screenContainer.add(outroPanel);
			screenContainer.repaint();
			screenContainer.revalidate();
			currentScreen = ScreensUI.OutroScreen;
			break;
		}
	}

	public Question getCurrentQuestion() {
		ArrayList<Question> qlist = quizInstance.getQuestionList();
		Question q = qlist.get(currentQIndex);
		return q;
	}

	public void refreshQuestionUI() {
		ArrayList<Question> qlist = quizInstance.getQuestionList();
		if (currentQIndex == -1) { // first time loading UI
			currentQIndex = 0;
		}

		btnNextQuestion.setVisible(true);
		btnPreviousQuestion.setVisible(true);

		if (currentQIndex == 0) {
			btnPreviousQuestion.setVisible(false);
		}

		Question q = qlist.get(currentQIndex);
		lblQuestionText.setText(q.getText());
		rdbtnA1.setText(q.getA1());
		rdbtnA2.setText(q.getA2());
		rdbtnA3.setText(q.getA3());
		rdbtnA4.setText(q.getA4());

		rdbtnGroup.clearSelection();

		int ga = q.getGivenAnswer();
		switch (ga) {
		case 1:
			rdbtnA1.setSelected(true);
			break;
		case 2:
			rdbtnA2.setSelected(true);
			break;
		case 3:
			rdbtnA3.setSelected(true);
			break;
		case 4:
			rdbtnA4.setSelected(true);
			break;
		}

		lblQuizProgress.setText("" + (currentQIndex + 1) + "/" + qlist.size());
		screenContainer.repaint();
		screenContainer.revalidate();
	}

	public void showUI() {
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	public void hideUI() {
		this.frame.setVisible(false);
	}
}
