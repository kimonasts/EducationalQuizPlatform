package admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Question;
import Model.Quiz;
import database.QuizDatabase;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.CardLayout;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import java.awt.SystemColor;
import javax.swing.JRadioButton;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;


import javax.swing.JTable;
import javax.swing.JComboBox;

/**
 * 
 * This class represents the administration ui window. It serves as a container
 * for all the ui screens that the admin will be using.
 *
 */

public class AdminUI extends JFrame {

	private JPanel contentPane;
	private JFrame previousWindow;

	/**
	 * 
	 * based on #internet resource It is the only way to provide a JTable element
	 * with the data you want in a flexible way.
	 */
	class MyTableModel extends AbstractTableModel {

		private String tableData[][];
		private String tableColumns[];

		public MyTableModel(String tableData[][], String tableColumns[]) {
			this.tableData = tableData;
			this.tableColumns = tableColumns;
		}

		/**
		 * This method prevents the user from editing the table's cells. In our case
		 * editing is prohibited for all the cells.
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public int getColumnCount() {
			return tableColumns.length;
		}

		@Override
		public int getRowCount() {
			return tableData.length;
		}

		@Override
		public Object getValueAt(int rowNum, int colNum) {
			return tableData[rowNum][colNum];
		}
		
		@Override
		public String getColumnName(int c) {
	        return tableColumns[c];
	    }    

	}

	/*
	 * The different AdminUI panels
	 */
	/**
	 * These panels serve as containers for each UI Screen that the admin can use.
	 */

	/**
	 * The quizListWrapperPanel displays a list of all the quizzes. Directly inside
	 * it, it contains some buttons and text that inform the user about the role if
	 * the UI and allow them to create a new quiz. It also contains the
	 * quizListPanel which actually holds the quiz list.
	 */
	JPanel quizListWrapperPanel; // QuizList screen container
	JPanel quizListPanel;
	/**
	 * This panel contains a table with all the statistics of the chosen quiz
	 */
	JPanel quizResultsPanel;
	/**
	 * This panel allows the admin to edit each quiz question, add new questions and
	 * edit some quiz parameters
	 */
	JPanel editQuestionPanel;

	private JTextField newQuizName;

	/**
	 * The selectedQuiz variable holds a reference to the quiz currently being
	 * displayed and edited by the admin
	 */
	private Quiz selectedQuiz = null;
	/**
	 * The selectedQnIndex variable holds the position on the quiz's question list,
	 * of the question currently being displayed and edited by the admin
	 */
	private int selectedQnIndex = -1;
	/**
	 * The selectedAnswer variable holds the correct answer number (1, 2, 3, 4) of
	 * the question currently being displayed and edited by the admin
	 */
	private int selectedAnswer = 1;

	/*
	 * UI Screens
	 */
	/**
	 * This enum's values represent the three different screens that are available
	 * to the admin, and the currentScreen variable holds the value representing the
	 * currently displayed screen
	 */
	private enum ScreensUI {
		QuizList, EditQuiz, QuizResults
	}; //

	private ScreensUI currentScreen;

	JLayeredPane screenContainer;
	private JTextField tfQuizID;
	private JTextField tfQuizName;
	private JTextField tfQuizMin;
	private JTextField tfQuizSec;
	private JTextField tfQuizLen;
	private JLabel lblQuestionNum;
	private JTextField tfQuestionID;
	private JTextField tfQuestionText;
	private JTextField tfQAnswer1;
	private JTextField tfQAnswer2;
	private JTextField tfQAnswer3;
	private JTextField tfQAnswer4;
	private JRadioButton rdbtnAnswer1;
	private JRadioButton rdbtnAnswer2;
	private JRadioButton rdbtnAnswer3;
	private JRadioButton rdbtnAnswer4;

	private JButton btnPreviousQuestion;
	private JButton btnNextQuestion;
	JScrollPane scrollPane2;
	private JTable resultsTable;
	JLabel resultsQuizName;
	private JLabel showSecValidation;

	/**
	 * This main is not used, only for testing purposes
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUI frame = new AdminUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the AdminUI window. That means that every button,
	 * label or any other control element that is required is created here.
	 * Moreover, the position and appearance of each element is determined here
	 * along with the actions that need to be taken when the user interacts with
	 * these controls. The code inside the initialize method was in part generated
	 * automatically from the WindowBuilder eclipse tool, and then modified to fit
	 * the needed purpose.
	 */
	public AdminUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 771, 548);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAdministratorInterface = new JLabel("Administrator UI");
		lblAdministratorInterface.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblAdministratorInterface.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministratorInterface.setBounds(264, 28, 224, 27);
		contentPane.add(lblAdministratorInterface);

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 228, 181));
		separator.setBounds(12, 65, 729, 15);
		contentPane.add(separator);

		screenContainer = new JLayeredPane();
		screenContainer.setBounds(12, 79, 729, 409);
		contentPane.add(screenContainer);
		screenContainer.setLayout(new CardLayout(0, 0));

		editQuestionPanel = new JPanel();
		editQuestionPanel.setBackground(new Color(100, 149, 237));
		screenContainer.add(editQuestionPanel, "name_829092025869600");
		editQuestionPanel.setLayout(null);

		JLabel lblSetQuizId = new JLabel("set Quiz ID:");
		lblSetQuizId.setBounds(84, 15, 79, 16);
		lblSetQuizId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editQuestionPanel.add(lblSetQuizId);

		tfQuizID = new JTextField();
		tfQuizID.setEditable(false);
		tfQuizID.setBounds(159, 13, 73, 22);
		editQuestionPanel.add(tfQuizID);
		tfQuizID.setColumns(10);

		JLabel lblQuizName = new JLabel("Quiz name:");
		lblQuizName.setBounds(244, 15, 79, 16);
		lblQuizName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editQuestionPanel.add(lblQuizName);

		tfQuizName = new JTextField();
		tfQuizName.setColumns(10);
		tfQuizName.setBounds(319, 13, 122, 22);
		editQuestionPanel.add(tfQuizName);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(122, 44, 41, 16);
		editQuestionPanel.add(lblTime);

		tfQuizMin = new JTextField();
		tfQuizMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')|| ke.getKeyCode() == ke.VK_BACK_SPACE) {
		               tfQuizMin.setEditable(true);
		            } else {
		               tfQuizMin.setEditable(false);
		            }
				
			}
		});
		tfQuizMin.setColumns(10);
		tfQuizMin.setBounds(159, 42, 27, 22);
		editQuestionPanel.add(tfQuizMin);

		JLabel label = new JLabel("  :");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(185, 42, 21, 20);
		editQuestionPanel.add(label);

		tfQuizSec = new JTextField();
		tfQuizSec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')|| ke.getKeyCode() == ke.VK_BACK_SPACE) {
		               tfQuizSec.setEditable(true);
		            } else {
		               tfQuizSec.setEditable(false);
		            }
				
			}
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
		        
		        String text = tfQuizSec.getText();
		        try {
		        	int a=Integer.parseInt(text);
		        	if(a>59)
		        	{
		        		tfQuizSec.setText("");
		        	}
		        }catch(Exception e1){
		        	
		        }
		        
			}
		});
		tfQuizSec.setColumns(10);
		tfQuizSec.setBounds(205, 42, 27, 22);
		editQuestionPanel.add(tfQuizSec);

		JLabel lblEnterFinalQuiz = new JLabel("Enter Final Quiz Length:");
		lblEnterFinalQuiz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterFinalQuiz.setBounds(12, 70, 154, 22);
		editQuestionPanel.add(lblEnterFinalQuiz);

		tfQuizLen = new JTextField();	
		tfQuizLen.setColumns(10);
		tfQuizLen.setBounds(159, 71, 73, 22);
		editQuestionPanel.add(tfQuizLen);

		JLabel lblEnterQuestionNumber = new JLabel("Number of questions:");
		lblEnterQuestionNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterQuestionNumber.setBounds(419, 70, 170, 22);
		editQuestionPanel.add(lblEnterQuestionNumber);

		lblQuestionNum = new JLabel("");
		lblQuestionNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuestionNum.setBounds(559, 70, 73, 22);
		editQuestionPanel.add(lblQuestionNum);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(12, 102, 705, 256);
		editQuestionPanel.add(panel);
		panel.setLayout(null);

		/**
		 * This button is displayed on the EditQuiz Screen. It allows the admin to
		 * navigate to the previous question of the quiz's question list
		 */
		btnPreviousQuestion = new JButton("Previous Question");
		btnPreviousQuestion.setBackground(SystemColor.menu);
		btnPreviousQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if we are not at the first question
				if (selectedQnIndex > 0) {

					// get the question currently being edited
					ArrayList<Question> questions = selectedQuiz.getQuestions();
					Question currentQuestion = questions.get(selectedQnIndex);

					// save the changes from the UI on the current question's object
					currentQuestion.setText(tfQuestionText.getText());
					currentQuestion.setA1(tfQAnswer1.getText());
					currentQuestion.setA2(tfQAnswer2.getText());
					currentQuestion.setA3(tfQAnswer3.getText());
					currentQuestion.setA4(tfQAnswer4.getText());
					currentQuestion.setCorrAns(selectedAnswer);

					// decrement the position of the selected question
					selectedQnIndex--;
					// if the position is 0 (we go to the first question in the list)
					// then the previous button is hidden
					if (selectedQnIndex == 0) {
						btnPreviousQuestion.setVisible(false);
					}
					// the question on the new position (selectedQnIndex) is loaded
					// on the UI form
					loadQuizUI();

					// the next button is displayed because the next question certainly exists
					btnNextQuestion.setVisible(true);
					btnNextQuestion.setText("Next Question");

				}
			}
		});

		btnPreviousQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPreviousQuestion.setBounds(12, 13, 163, 25);
		panel.add(btnPreviousQuestion);

		/**
		 * This button is displayed on the EditQuiz Screen. It allows the admin to
		 * navigate to the next question of the quiz's question list. If the current
		 * question is the last one on the list, the buttons text becomes "Add Question"
		 * and when pressed, it creates a new question and adds it to the end of the
		 * list
		 */
		btnNextQuestion = new JButton("Next Question");
		btnNextQuestion.setBackground(SystemColor.menu);
		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// get the question currently being edited
				ArrayList<Question> questions = selectedQuiz.getQuestions();
				Question currentQuestion = questions.get(selectedQnIndex);

				// save the changes from the UI on the current question's object
				currentQuestion.setText(tfQuestionText.getText());
				currentQuestion.setA1(tfQAnswer1.getText());
				currentQuestion.setA2(tfQAnswer2.getText());
				currentQuestion.setA3(tfQAnswer3.getText());
				currentQuestion.setA4(tfQAnswer4.getText());
				currentQuestion.setCorrAns(selectedAnswer);

				// the admin has to fill in all the fields of the question edit form in order
				// to move on to the next or previous question
				if (!currentQuestion.checkIsCompleted()) {
					JOptionPane.showMessageDialog(null, "Please complete the current question.",
							"Question Creation Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				/*
				 * If we are at the second to the last question, the next question will be the
				 * last, so we need the button to display "Add Question"
				 */
				if (selectedQnIndex == questions.size() - 2) {
					btnNextQuestion.setText("Add Question");
				}
				/*
				 * if we are at the last question, that means that the user pressed
				 * "Add Question" A new question will be created and that will be the last on
				 * the list, so we need the button to display "Add Question"
				 */
				else if (selectedQnIndex == questions.size() - 1) {
					btnNextQuestion.setText("Add Question");
					
				}
				
				btnPreviousQuestion.setVisible(true);

				// decrement the position of the selected question
				selectedQnIndex++;
				// if the button "Add Question" was pressed then we create a new empty
				// question object and add it to the end of the list
				if (questions.size() == selectedQnIndex) {
					Question q = new Question(selectedQuiz, "", "", "", "", "", 1);
					selectedQuiz.addQuestion(q);

				}
				// the question on the new position (selectedQnIndex) is loaded
				// on the UI form
				loadQuizUI();

			}
		});
		btnNextQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNextQuestion.setBounds(530, 14, 163, 25);
		panel.add(btnNextQuestion);

		JLabel lblQuestionId = new JLabel("Question ID");
		lblQuestionId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuestionId.setBounds(54, 51, 78, 16);
		panel.add(lblQuestionId);

		tfQuestionID = new JTextField();
		tfQuestionID.setColumns(10);
		tfQuestionID.setBounds(54, 69, 73, 22);
		panel.add(tfQuestionID);

		JLabel lblQuestionText = new JLabel("Question Text");
		lblQuestionText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuestionText.setBounds(160, 52, 91, 16);
		panel.add(lblQuestionText);

		tfQuestionText = new JTextField();
		tfQuestionText.setColumns(10);
		tfQuestionText.setBounds(160, 69, 190, 22);
		panel.add(tfQuestionText);

		JLabel lblAnswer = new JLabel("Answer 1");
		lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnswer.setBounds(160, 106, 65, 16);
		panel.add(lblAnswer);

		JLabel lblAnswer_1 = new JLabel("Answer 2");
		lblAnswer_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnswer_1.setBounds(160, 137, 65, 16);
		panel.add(lblAnswer_1);

		JLabel lblAnswer_2 = new JLabel("Answer 3");
		lblAnswer_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnswer_2.setBounds(160, 168, 65, 16);
		panel.add(lblAnswer_2);

		JLabel lblAnswer_3 = new JLabel("Answer 4");
		lblAnswer_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnswer_3.setBounds(160, 199, 65, 16);
		panel.add(lblAnswer_3);

		tfQAnswer1 = new JTextField();
		tfQAnswer1.setColumns(10);
		tfQAnswer1.setBounds(220, 104, 130, 22);
		panel.add(tfQAnswer1);

		tfQAnswer2 = new JTextField();
		tfQAnswer2.setColumns(10);
		tfQAnswer2.setBounds(220, 135, 130, 22);
		panel.add(tfQAnswer2);

		tfQAnswer3 = new JTextField();
		tfQAnswer3.setColumns(10);
		tfQAnswer3.setBounds(220, 166, 130, 22);
		panel.add(tfQAnswer3);

		tfQAnswer4 = new JTextField();
		tfQAnswer4.setColumns(10);
		tfQAnswer4.setBounds(220, 197, 130, 22);
		panel.add(tfQAnswer4);

		JLabel lblCorrectAnswer = new JLabel("Correct Answer");
		lblCorrectAnswer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCorrectAnswer.setBounds(378, 71, 107, 16);
		panel.add(lblCorrectAnswer);

		rdbtnAnswer1 = new JRadioButton("");
		rdbtnAnswer1.setBackground(new Color(240, 248, 255));
		rdbtnAnswer1.setBounds(407, 101, 25, 25);
		panel.add(rdbtnAnswer1);
		/*
		 * When the admin clicks on the radio button of an answer in order to select it
		 * as the correct answer, the other radio buttons get deselected
		 */
		rdbtnAnswer1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAnswer = 1;
				rdbtnAnswer1.setSelected(true);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(false);
			}
		});
		/*
		 * When the admin clicks on the radio button of an answer in order to select it
		 * as the correct answer, the other radio buttons get deselected
		 */
		rdbtnAnswer2 = new JRadioButton("");
		rdbtnAnswer2.setBackground(new Color(240, 248, 255));
		rdbtnAnswer2.setBounds(407, 132, 25, 25);
		panel.add(rdbtnAnswer2);
		rdbtnAnswer2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAnswer = 2;
				rdbtnAnswer1.setSelected(false);
				rdbtnAnswer2.setSelected(true);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(false);
			}
		});
		/*
		 * When the admin clicks on the radio button of an answer in order to select it
		 * as the correct answer, the other radio buttons get deselected
		 */
		rdbtnAnswer3 = new JRadioButton("");
		rdbtnAnswer3.setBackground(new Color(240, 248, 255));
		rdbtnAnswer3.setBounds(407, 163, 25, 25);
		panel.add(rdbtnAnswer3);
		rdbtnAnswer3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAnswer = 3;
				rdbtnAnswer1.setSelected(false);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(true);
				rdbtnAnswer4.setSelected(false);
			}
		});
		/*
		 * When the admin clicks on the radio button of an answer in order to select it
		 * as the correct answer, the other radio buttons get deselected
		 */
		rdbtnAnswer4 = new JRadioButton("");
		rdbtnAnswer4.setBackground(new Color(240, 248, 255));
		rdbtnAnswer4.setBounds(407, 194, 25, 25);
		panel.add(rdbtnAnswer4);
		rdbtnAnswer4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAnswer = 4;
				rdbtnAnswer1.setSelected(false);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(true);
			}
		});

		/*
		 * By pressing this button, the admin resets the form fields of the question
		 * being edited.
		 */
		JButton btnResetQuestion = new JButton("Reset Question");
		btnResetQuestion.setBackground(SystemColor.menu);
		btnResetQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedQuiz.getQuestions().get(selectedQnIndex).reset();

				tfQuestionText.setText("");
				tfQAnswer1.setText("");
				tfQAnswer2.setText("");
				tfQAnswer3.setText("");
				tfQAnswer4.setText("");

				rdbtnAnswer1.setSelected(true);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(false);
			}
		});
		btnResetQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnResetQuestion.setBounds(530, 218, 163, 25);
		panel.add(btnResetQuestion);

		/*
		 * The admin is done editing the quiz
		 */
		JButton btnSubmitQuiz = new JButton("Submit Quiz");
		btnSubmitQuiz.setBackground(SystemColor.menu);
		btnSubmitQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				submitQuiz();

			}

		});
		btnSubmitQuiz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmitQuiz.setBounds(284, 371, 163, 25);
		editQuestionPanel.add(btnSubmitQuiz);

		/*
		 * By pressing this button the quiz length is updated for the quiz being edited.
		 * Quiz length is how many questions the student has to answer in order to
		 * complete the quiz. The quiz can have many more questions, but the student
		 * will be presented with a number of questions equal to the quiz's length
		 */
		JButton btnSetQuizLength = new JButton("Set quiz length");
		btnSetQuizLength.setBackground(SystemColor.menu);
		btnSetQuizLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int newQuizLength = 0;
				try {
					// get the new quiz length entered on the form
					newQuizLength = Integer.parseInt(tfQuizLen.getText());
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null,
							"Please enter an integer value.",
							"Data Type Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// get the existing quiz length
				int oldQuizLength = selectedQuiz.getFinalQuizLength();
				// if they are equal, no need to change something
				if (newQuizLength == oldQuizLength) {
					return;
				}
				
			
				// if the new length is less than the number of created questions, then make the
				// change
				if (selectedQuiz.getQuestions().size() >= newQuizLength) {
//					if(selectedQnIndex == oldQuizLength - 1) {
//						btnNextQuestion.setVisible(true);
//					}
					selectedQuiz.setFinalQuizLength(newQuizLength);
				}
				// Entering a length number greater than the number of questions
				// is not allowed, because quiz cannot provide more questions
				// than it already has
				else {
					JOptionPane.showMessageDialog(null,
							"Please enter a final quiz length less or equal to the current number of questions.",
							"Quiz Edit Error", JOptionPane.ERROR_MESSAGE);
					tfQuizLen.setText("" + selectedQuiz.getFinalQuizLength());
//					
				}
			}
		});
		btnSetQuizLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSetQuizLength.setBounds(244, 70, 163, 25);
		editQuestionPanel.add(btnSetQuizLength);
		
		showSecValidation = new JLabel("");
		showSecValidation.setForeground(Color.RED);
		showSecValidation.setBounds(244, 45, 208, 16);
		editQuestionPanel.add(showSecValidation);

		quizListWrapperPanel = new JPanel();
		quizListWrapperPanel.setBackground(new Color(100, 149, 237));
		screenContainer.add(quizListWrapperPanel, "name_38927630787728");
		quizListWrapperPanel.setLayout(null);

		JLabel lblQuizList = new JLabel("Quiz List");
		lblQuizList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQuizList.setBounds(330, 13, 68, 22);
		quizListWrapperPanel.add(lblQuizList);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 58, 675, 284);
		quizListWrapperPanel.add(scrollPane);

		quizListPanel = new JPanel();
		quizListPanel.setBackground(new Color(240, 248, 255));
		quizListPanel.setBounds(12, 102, 705, 256);
		scrollPane.setViewportView(quizListPanel);
		quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));

		newQuizName = new JTextField();
		newQuizName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		newQuizName.setBounds(223, 355, 278, 26);
		quizListWrapperPanel.add(newQuizName);
		newQuizName.setColumns(10);

		/*
		 * This button creates a new quiz
		 */
		JButton btnAddQuiz = new JButton("Add Quiz");
		btnAddQuiz.setBackground(SystemColor.menu);
		btnAddQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if a quiz name is not provided, show error
				if (newQuizName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter a quiz name", "Quiz Creation Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				/*
				 * Searh the quiz list for an existing quiz with the same name as the given one
				 * and if that's the case, show error
				 */
				ArrayList<Quiz> quizList = QuizDatabase.getQuizList();
				for (Quiz quiz : quizList) {

					if (quiz.getName().equals(newQuizName.getText())) {
						JOptionPane.showMessageDialog(null,
								"A quiz with the given name already exists! Please insert a different name.",
								"Quiz Creation Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				/*
				 * Everything went well, so create a new quiz with the given name ,add it to the
				 * database and refresh the displayed quiz list
				 */
				Quiz createdQuiz = new Quiz(newQuizName.getText());
				QuizDatabase.addQuiz(createdQuiz);
				newQuizName.setText(null);
				refreshQuizList();
			}
		});
		btnAddQuiz.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAddQuiz.setBounds(513, 355, 128, 26);
		quizListWrapperPanel.add(btnAddQuiz);

		JLabel lblNewLabel = new JLabel("Enter Quiz name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(87, 355, 133, 26);
		quizListWrapperPanel.add(lblNewLabel);

		quizResultsPanel = new JPanel();
		quizResultsPanel.setBackground(new Color(135, 206, 250));
		screenContainer.add(quizResultsPanel, "name_137924756655567");
		quizResultsPanel.setLayout(null);

		JLabel lblEditingQuiz = new JLabel("Results for Quiz:");
		lblEditingQuiz.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEditingQuiz.setBounds(87, 23, 136, 22);
		quizResultsPanel.add(lblEditingQuiz);

		resultsQuizName = new JLabel("Quiz Being Viewed");
		resultsQuizName.setForeground(SystemColor.textHighlight);
		resultsQuizName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		resultsQuizName.setBounds(230, 23, 411, 22);
		quizResultsPanel.add(resultsQuizName);

		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(87, 58, 554, 284);
		quizResultsPanel.add(scrollPane2);

		resultsTable = new JTable();
		resultsTable.setEnabled(false);
		scrollPane2.setViewportView(resultsTable);

		/*
		 * The back button is visible in all three screens that the admin can be in. So
		 * depending on the current screen, the button's action is adjusted.
		 */
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(SystemColor.menu);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				switch (currentScreen) {
				// if the current screen displays the quiz list, then the back button
				// navigates to the Start Window (its reference stored on the previousWindow)
				case QuizList:
					QuizDatabase.saveAllToCSV();
					AdminUI.this.hideUI();
					AdminUI.this.previousWindow.show();
					break;
				// if the current screen is the quiz editing screen, then the back buttons
				// submits (and saves) the quiz and navigates to the quiz list screen
				case EditQuiz:
					submitQuiz();
					break;
				// if the current screen is the quiz statistics(results) then
				// navigate to the quiz list
				case QuizResults:
					showScreenPanel(ScreensUI.QuizList);
				}

			}
		});
		btnBack.setBounds(12, 30, 113, 25);
		contentPane.add(btnBack);
		/*
		 * When entering the admin ui for the first time, the screen that is displayed
		 * is the quiz list
		 */
		showScreenPanel(ScreensUI.QuizList);
		refreshQuizList();
	}

	/*
	 * The admin ui constructor that takes a reference to the previous window so
	 * that the back button can go back there
	 */
	public AdminUI(JFrame previousWindow) {
		this();
		this.previousWindow = previousWindow;
	}

	/*
	 * This is called when the admin has finished editing a quiz and presses the
	 * "Submit Quiz" or the "Back Button".
	 */
	private void submitQuiz() {

		/*
		 * Get the object of the question currently being edited
		 */
		ArrayList<Question> questions = selectedQuiz.getQuestions();
		Question currentQuestion = questions.get(selectedQnIndex);

		/*
		 * update the question object with the latest form data
		 */
		currentQuestion.setText(tfQuestionText.getText());
		currentQuestion.setA1(tfQAnswer1.getText());
		currentQuestion.setA2(tfQAnswer2.getText());
		currentQuestion.setA3(tfQAnswer3.getText());
		currentQuestion.setA4(tfQAnswer4.getText());
		currentQuestion.setCorrAns(selectedAnswer);

		/*
		 * update the current quiz object with the latest form data
		 */
		selectedQuiz.setName(tfQuizName.getText());
		selectedQuiz.setMin(Integer.parseInt(tfQuizMin.getText()));
		selectedQuiz.setSec(Integer.parseInt(tfQuizSec.getText()));

		/*
		 * Get the object of the last question that the admin might not have finished
		 * editing, because they are allowed to go to the previous question without
		 * having completed the last one
		 */
		int lastQIndex = questions.size() - 1;
		Question lastQuestion = questions.get(lastQIndex);

		/*
		 * if the last question is complete then save all the quizzes with their
		 * questions from the database object to a csv file
		 */
		if (lastQuestion.checkIsCompleted()) {
			QuizDatabase.saveAllToCSV();

			btnNextQuestion.setText("Next Question");
			showScreenPanel(ScreensUI.QuizList);
		} else {
			JOptionPane.showMessageDialog(null, "Please complete the last question before submiting the quiz.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * There are 3 available screens inside the screenContainer. This method takes
	 * as a parameter the enum value of the desired screen and adds it inside the
	 * screenContainer, removes all the others, and refreshes it so that it is
	 * displayed properly. It also sets the value of the currentScreen
	 */
	public void showScreenPanel(ScreensUI screen) // You decide the screen that you are going to display
	{
		switch (screen) {
		case QuizList:
			refreshQuizList(); // reload the quiz list from the database
			screenContainer.removeAll(); // empty the screenContainer
			screenContainer.add(quizListWrapperPanel);// add the quiz list panel
			// repaint and revalidate are required from Swing in order to clear the
			// previous image and paint properly the new one
			screenContainer.repaint();
			screenContainer.revalidate();
			currentScreen = ScreensUI.QuizList;// update currentScreen with the respective enum value
			break;
		case EditQuiz:
			refreshQuizList(); // reload the quiz list from the database
			screenContainer.removeAll();// empty the screenContainer
			screenContainer.add(editQuestionPanel);// add the edit question panel
			// repaint and revalidate are required from Swing in order to clear the
			// previous image and paint properly the new one
			screenContainer.repaint();
			screenContainer.revalidate();
			currentScreen = ScreensUI.EditQuiz;// update currentScreen with the respective enum value
			break;
		case QuizResults:
			screenContainer.removeAll();
			screenContainer.add(quizResultsPanel);
			// repaint and revalidate are required from Swing in order to clear the
			// previous image and paint properly the new one
			screenContainer.repaint();
			screenContainer.revalidate();
			currentScreen = ScreensUI.QuizResults;// update currentScreen with the respective enum value
			break;
		}
	}

	public void showUI() {
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void hideUI() {
		this.setVisible(false);
	}

	// gets from the database a QuizList and for every quiz it creates a
	// QuizListEntryComp
	public void refreshQuizList() {
		quizListPanel.removeAll();// empties the quiz list container
		// gets the most recent list of quizzes
		ArrayList<Quiz> quizList = QuizDatabase.getQuizList();
		/*
		 * For each quiz, a QuizListEntryComp (extends JPanel) object is created and
		 * passed two references, "this": the Admin UI object, and the quiz it will
		 * represent
		 */
		for (Quiz quiz : quizList) {
			quizListPanel.add(new QuizListEntryComp(this, quiz));
		}
		// refresh and draw the screen properly
		quizListPanel.repaint();
		quizListPanel.revalidate();
	}

	public void loadQuizUI() {

		Question selectedQuestion = null;
		if (selectedQuiz != null) {
			tfQuizID.setText("" + selectedQuiz.getQuizID());
			tfQuizName.setText("" + selectedQuiz.getName());
			tfQuizMin.setText("" + selectedQuiz.getMin());
			tfQuizSec.setText("" + selectedQuiz.getSec());
			tfQuizLen.setText("" + selectedQuiz.getFinalQuizLength());
			selectedQuestion = selectedQuiz.getQuestions().get(selectedQnIndex);
			lblQuestionNum.setText("" + selectedQuiz.getQuestions().size());
			if (selectedQnIndex==0 && !selectedQuestion.checkIsCompleted())
			{
				btnNextQuestion.setText("Add Question");
			}

		}

		if (selectedQuestion != null) {
			tfQuestionID.setText("" + selectedQuestion.getQuestionID());
			tfQuestionText.setText("" + selectedQuestion.getText());
			tfQAnswer1.setText("" + selectedQuestion.getA1());
			tfQAnswer2.setText("" + selectedQuestion.getA2());
			tfQAnswer3.setText("" + selectedQuestion.getA3());
			tfQAnswer4.setText("" + selectedQuestion.getA4());

			int corrAns = selectedQuestion.getCorrAns();
			selectedAnswer = corrAns;
			switch (corrAns) {
			case 1:
				rdbtnAnswer1.setSelected(true);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(false);
				break;
			case 2:
				rdbtnAnswer1.setSelected(false);
				rdbtnAnswer2.setSelected(true);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(false);
				break;
			case 3:
				rdbtnAnswer1.setSelected(false);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(true);
				rdbtnAnswer4.setSelected(false);
				break;
			case 4:
				rdbtnAnswer1.setSelected(false);
				rdbtnAnswer2.setSelected(false);
				rdbtnAnswer3.setSelected(false);
				rdbtnAnswer4.setSelected(true);
				break;
			}
		}
	}

	public void showQuizResults(Quiz quiz) {

		File f = new File("answers-" + quiz.getQuizID() + ".txt");
		if (!f.exists()) {
			resultsTable = new JTable();
			scrollPane2.setViewportView(resultsTable);
			resultsQuizName.setText(quiz.getQuizID() + " - " + quiz.getName());
			showScreenPanel(ScreensUI.QuizResults);
			return;
		}

		try {

			FileInputStream dbFile = new FileInputStream("answers-" + quiz.getQuizID() + ".txt");
			Scanner sc = new Scanner(dbFile); // file to be scanned

			ArrayList<String[]> tableDataLines = new ArrayList<String[]>();
			// if there is another line to read
			if (sc.hasNextLine()) {
				String line = sc.nextLine(); // read it and store it, but do nothing with it
				// put the id of the quiz and its name on the ui label
				resultsQuizName.setText(quiz.getQuizID() + " - " + quiz.getName());
			}

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String parts[] = line.split(";");

				tableDataLines.add(parts);

			}

			String tableData[][] = new String[tableDataLines.size()][];

			for (int i = 0; i < tableDataLines.size(); i++) {
				tableData[i] = tableDataLines.get(i);
			}

			String tableColumns[] = { "Email", "Date", "Score", "Time Taken" };

//			resultsTable = new JTable(tableData, tableColumns);
			resultsTable = new JTable(new MyTableModel(tableData, tableColumns));

			scrollPane2.setViewportView(resultsTable);
		} catch (IOException e) {
			e.printStackTrace();
		}

		showScreenPanel(ScreensUI.QuizResults);
	}

	public void editQuiz(Quiz quiz) {
		selectedQuiz = quiz;
		ArrayList<Question> questions = selectedQuiz.getQuestions();
		if (questions.size() > 0) {
			selectedQnIndex = 0; // we want to take the first question - position 0
			loadQuizUI();
		} else {
			Question q = new Question(selectedQuiz, "", "", "", "", "", 1);
			quiz.addQuestion(q);
			selectedQnIndex = 0;
			loadQuizUI();
		}
		btnPreviousQuestion.setVisible(false);
		btnNextQuestion.setVisible(true);
		showScreenPanel(ScreensUI.EditQuiz);
	}

	public void deleteQuiz(Quiz quiz) {
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this quiz with all its questions?",
				"Delete Quiz", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
			QuizDatabase.deleteQuiz(quiz);
			refreshQuizList();
		}

	}

	public void errorExit() {
		JOptionPane.showMessageDialog(null, "Fatal Application Error! The application has to close.", "Error",
				JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
}
