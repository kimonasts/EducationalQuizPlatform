package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import admin.*;
import student.StudentLogin;
import java.awt.Color;

/**
 * StartWindow Class description This class implements a ui window and
 * represents the first screen that is displayed to any user that opens the
 * application. Two options are presented to the user so that the user can
 * continue as a Student or as an Admin.
 */

public class StartWindow {

	private JFrame frame;
	private AdminLogin adminLoginFrame;
	private StudentLogin userLoginFrame;

	/**
	 * This is the main method that is called when the program starts. All the other
	 * mains are not used, they just help with debugging.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/**
					 * At this point, a new object of the StartWindow is created and centered, and
					 * then is displayed on the user.
					 */
					StartWindow window = new StartWindow();
					window.frame.setLocationRelativeTo(null);
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
	public StartWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the start window. That means that every button,
	 * label or any other control element that is required is created here.
	 * Moreover, the position and appearance of each element is determined here
	 * along with the actions that need to be taken when the user interacts with
	 * these controls. The code inside the initialize method was in part generated
	 * automatically from the WindowBuilder eclipse tool, and then modified to fit
	 * the needed purpose.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(100, 149, 237));
		frame.setBounds(100, 100, 780, 313);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 165, 0));
		panel.setBounds(59, 30, 651, 208);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome to the system!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(194, 13, 256, 57);
		panel.add(lblNewLabel);

		JLabel lblPleaseSelectYourRole = new JLabel("Please select your role:");
		lblPleaseSelectYourRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectYourRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPleaseSelectYourRole.setBounds(194, 68, 256, 57);
		panel.add(lblPleaseSelectYourRole);

		JButton btnUser = new JButton("Student");
		/**
		 * If the user presses the Student button, then an object of the Student Login
		 * window is created (if it doesn't exist), the current Start Window becomes
		 * invisible and the Student Login window becomes visible.
		 */
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (userLoginFrame == null) // if we haven't created the UserLoginFrame
				{
					/**
					 * when creating the StudentLogin window, it receives a reference to the current
					 * window (StartWindow), so that the user can navigate back here. This technique
					 * is used throughout the project.
					 */
					userLoginFrame = new StudentLogin(frame); // a new object is created in order to show UserLogin with
																// the below commands
				}
				frame.setVisible(false);
				userLoginFrame.show();
			}
		});
		btnUser.setBounds(115, 138, 137, 36);
		panel.add(btnUser);

		JButton btnAdmin = new JButton("Admin");
		/**
		 * If the user presses the Admin button, then an object of the Admin Login
		 * window is created (if it doesn't exist), the current Start Window becomes
		 * invisible and the Admin Login window becomes visible.
		 */
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (adminLoginFrame == null) {
					/**
					 * when creating the StudentLogin window, it receives a reference to the current
					 * window (StartWindow), so that the user can navigate back here. This technique
					 * is used throughout the project.
					 */
					adminLoginFrame = new AdminLogin(frame); // Start window frame
				}
				frame.setVisible(false); // we make the StartWindow frame disappear
				adminLoginFrame.show();

				// todo: user login frame
			}
		});
		btnAdmin.setBounds(398, 138, 137, 36);
		panel.add(btnAdmin);
	}
}
