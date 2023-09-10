package admin;

import javax.swing.JPanel;

import Model.Quiz;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class QuizListEntryComp extends JPanel {

	AdminUI adminUI;
	Quiz quiz;

	/**
	 * Create the panel.
	 */
	public QuizListEntryComp(AdminUI adminUI, Quiz quiz) {
		setBackground(SystemColor.control);
		this.setPreferredSize(new Dimension(647, 54));
		setLayout(null);

		JLabel lblQuizName = new JLabel(quiz.getName());
		lblQuizName.setBounds(12, 17, 217, 20);
		lblQuizName.setBackground(Color.LIGHT_GRAY);
		lblQuizName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblQuizName);

		JButton btnNewButton = new JButton("Edit Quiz");
		btnNewButton.setBounds(241, 13, 130, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adminUI.editQuiz(quiz); // the parameter is the quiz that belongs to the method editQuiz and is going to
										// be edited
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(btnNewButton);

		JButton btnDeleteQuiz = new JButton("Delete Quiz");
		btnDeleteQuiz.setBounds(382, 13, 130, 29);
		btnDeleteQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminUI.deleteQuiz(quiz);
			}
		});
		btnDeleteQuiz.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(btnDeleteQuiz);

		JButton btnResults = new JButton("Results");
		btnResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adminUI.showQuizResults(quiz);
			}
		});
		btnResults.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnResults.setBounds(524, 13, 100, 29);
		add(btnResults);

	}
}
