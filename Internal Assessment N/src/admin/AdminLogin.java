package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin {

	public JFrame frameLogin;
	public JFrame previousFrame;
	private JTextField adminUsername;
	private JPasswordField adminPassword;

	public AdminLogin() {
		initialize();
	}

	public AdminLogin(JFrame previousFrame) {
		this.previousFrame = previousFrame; // in the parameter the previous frame from this UI (StartWindow in this
											// case) is included
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameLogin = new JFrame();
		frameLogin.setBounds(200, 200, 500, 300);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogin.setBounds(218, 30, 73, 16);
		frameLogin.getContentPane().add(lblLogin);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(70, 72, 73, 26);
		frameLogin.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(70, 121, 73, 26);
		frameLogin.getContentPane().add(lblPassword);

		adminUsername = new JTextField();
		adminUsername.setBounds(144, 76, 232, 22);
		frameLogin.getContentPane().add(adminUsername);
		adminUsername.setColumns(10);

		adminPassword = new JPasswordField();
		adminPassword.setBounds(144, 124, 232, 22);
		frameLogin.getContentPane().add(adminPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // this belongs to the anonymous class that implements
															// ActionListener
				String password = adminPassword.getText();
				String username = adminUsername.getText();

				if (password.equals("asd") && username.equals("asd")) {
					adminPassword.setText(null);
					adminUsername.setText(null);

					/*
					 * if the user presses back to start from the Administrator Interface, they will
					 * go to the start window, not the AdminLogin window
					 */
					AdminUI adui = new AdminUI(AdminLogin.this.previousFrame); // The previous frame of AdminUI is
																				// StartWindow, AdminLogin is needed to
																				// reference to the AdminLogin instance
					AdminLogin.this.hide(); // Not the ActionListener instance
					adui.showUI();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid entry", "Login error", JOptionPane.ERROR_MESSAGE);
					adminPassword.setText(null);
					adminUsername.setText(null);
				}
			}
		});
		btnLogin.setBounds(58, 185, 97, 25);
		frameLogin.getContentPane().add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminPassword.setText(null);
				adminUsername.setText(null);
			}
		});
		btnReset.setBounds(194, 185, 97, 25);
		frameLogin.getContentPane().add(btnReset);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frameLogin, "Confirm if you want to exit", "Login Systems",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(331, 185, 97, 25);
		frameLogin.getContentPane().add(btnExit);
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminLogin.this.hide();
				previousFrame.show();
			}
		});
		btnBack.setBounds(12, 23, 97, 25);
		frameLogin.getContentPane().add(btnBack);
	}
	
	

	public void show() {
		this.frameLogin.setLocationRelativeTo(null);
		this.frameLogin.setVisible(true);
	}

	public void hide() {
		this.frameLogin.setVisible(false);
	}

}
