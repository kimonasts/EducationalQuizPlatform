package student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import Model.Quiz;
import Model.QuizInstance;
import admin.AdminLogin;
import admin.AdminUI;
import database.QuizDatabase;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class StudentLogin {

	private JFrame frame_login_users;
	private JFrame previousFrame;
	private JTextField emailInput;
	private JTextField quiz_ID;
	private String email;
	private String quizID;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuizID() {
		return quizID;
	}

	public void setQuizID(String quizID) {
		this.quizID = quizID;
	}

	public StudentLogin(JFrame previousFrame) {
		this.previousFrame = previousFrame;
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentLogin window = new StudentLogin();
					window.frame_login_users.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame_login_users = new JFrame();
		frame_login_users.getContentPane().setBackground(UIManager.getColor("menu"));
		frame_login_users.setBackground(Color.WHITE);
		frame_login_users.getContentPane().setForeground(new Color(255, 255, 255));
		frame_login_users.setBounds(100, 100, 553, 286);
		frame_login_users.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_login_users.getContentPane().setLayout(null);

		JLabel lblUserLogin = new JLabel("STUDENT LOGIN");
		lblUserLogin.setBounds(199, 26, 124, 16);
		lblUserLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame_login_users.getContentPane().add(lblUserLogin);

		JLabel email = new JLabel("Email");
		email.setBounds(32, 84, 77, 16);
		email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame_login_users.getContentPane().add(email);

		JLabel lblQuizId = new JLabel("Quiz ID");
		lblQuizId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblQuizId.setBounds(32, 138, 71, 16);
		frame_login_users.getContentPane().add(lblQuizId);

		emailInput = new JTextField();
		emailInput.setBounds(142, 82, 318, 22);
		frame_login_users.getContentPane().add(emailInput);
		emailInput.setColumns(10);

		quiz_ID = new JTextField();
		quiz_ID.setBounds(142, 136, 318, 22);
		frame_login_users.getContentPane().add(quiz_ID);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String quizID = quiz_ID.getText();
				String email = emailInput.getText();
				int quizIDnum = 0;

				if (email == null || email.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter an email", "Login error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					quizIDnum = Integer.parseInt(quizID);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid quiz ID number", "Login error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Quiz quiz = QuizDatabase.getQuiz(quizIDnum);

				if (quiz == null) {
					JOptionPane.showMessageDialog(null, "The quiz id entered does not correspond to a valid quiz!",
							"Login error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				QuizInstance qinstance = quiz.generateQuizInstance();

				StudentLogin.this.quizID = quizID;
				StudentLogin.this.email = email;
				quiz_ID.setText(null);
				emailInput.setText(null);

				StudentUI stdui = new StudentUI(StudentLogin.this.previousFrame, email, qinstance);
				StudentLogin.this.hide();
				stdui.showUI();

			}
		});
		btnLogin.setBounds(32, 201, 97, 25);
		frame_login_users.getContentPane().add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emailInput.setText(null);
				quiz_ID.setText(null);
			}
		});
		btnReset.setBounds(214, 201, 97, 25);
		frame_login_users.getContentPane().add(btnReset);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(frame_login_users, "Confirm if you want to exit", "Login Systems",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(403, 201, 97, 25);
		frame_login_users.getContentPane().add(btnExit);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentLogin.this.hide();
				previousFrame.show();
			}
		});
		btnBack.setBounds(12, 23, 97, 25);
		frame_login_users.getContentPane().add(btnBack);
	}

	public void show() {
		this.frame_login_users.setLocationRelativeTo(null);
		this.frame_login_users.setVisible(true);
	}

	public void hide() {
		this.frame_login_users.setVisible(false);
	}
}
