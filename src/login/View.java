package login;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//-------------------------------
//View contents of the login page
//-------------------------------

public class View extends JFrame{

	private Controller controller;

	private JLabel errorMsg;
	private JTextField emailTF;
	private JPasswordField passTF;
	private JCheckBox captcha;

	//View class constructor
	public View(Controller c) {
		this.controller = c;
		settings();
		creatingComponents();
	}

	//create all components and panels and add to the frame
	private void creatingComponents() {

		//creating menu bar and itens
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuClose= new JMenuItem("Close");
		menuClose.setActionCommand("close");
		menuClose.addActionListener(controller);
		menu.add(menuClose);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);

		//creating a grid layout manager for the frame
		this.setLayout(new GridLayout(0,1));

		//creating top panel with welcome message
		JPanel topPanel = new JPanel(new GridBagLayout());
		//topPanel.setSize(200, 300);
		JLabel welcomeMsg = new JLabel("Welcome to CMD's booking system!");
		welcomeMsg.setFont(new Font(welcomeMsg.getName(), Font.BOLD, 16));
		topPanel.add(welcomeMsg);
		this.add(topPanel);

		//creating mid panel with email and password text fields
		JPanel midPanel = new JPanel();
		midPanel.setSize(200, 300);
		JLabel email = new JLabel("E-mail:        ");
		JTextField emailTF = new JTextField(20);
		this.emailTF = emailTF;
		JLabel pass = new JLabel("Password: ");
		JPasswordField passTF = new JPasswordField(20);
		this.passTF = passTF;

		midPanel.add(email);
		midPanel.add(emailTF);
		midPanel.add(pass);
		midPanel.add(passTF);
		this.add(midPanel);

		//creating panel with error label and captcha
		JLabel error = new JLabel ("");
		error.setForeground(Color.RED);
		this.errorMsg = error;
		JPanel captchaPanel = new JPanel(new GridLayout(2,1));
		JCheckBox captcha = new JCheckBox("I'm not a robot.");
		this.captcha = captcha;
		captchaPanel.add(captcha);
		captchaPanel.add(error);
		captcha.setHorizontalAlignment(captcha.CENTER);
		error.setHorizontalAlignment(error.CENTER);
		this.add(captchaPanel);


		//creating bottom panel with login and sign up button
		JPanel bottomPanel = new JPanel();
		JButton login = new JButton("Login");
		login.addActionListener(controller);
		login.setActionCommand("login");
		JButton signup = new JButton("Sign up");
		signup.addActionListener(controller);
		signup.setActionCommand("signup");
		bottomPanel.add(login);
		bottomPanel.add(signup);
		this.add(bottomPanel);

		this.add(bottomPanel);

		this.validate();
		this.repaint();

	}

	//sets size and visibility of the view
	private void settings() {
		this.setSize(350, 300);
		this.setResizable(false);
		this.setVisible(true);

	}

	//getters for View components
	public String getEmail() {
		return emailTF.getText();
	}
	public char[] getPassTF() {
		return passTF.getPassword();
	}
	public JLabel getErrorMsg() {
		return errorMsg;
	}
	public boolean getCaptcha( ) {
		return captcha.isSelected();
	}
}
