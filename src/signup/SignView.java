package signup;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SignView extends JFrame{

	private SignController controller;
	private boolean isCustomer;
	private JLabel errorMsg;
	private JTextField fullName;
	private JTextField mobile;
	private JTextField location;
	private JTextField email;
	private JTextField confEmail;
	private JPasswordField password;
	private JPasswordField confPass;
	

	public SignView(SignController c) {
		this.controller = c;
		this.setTitle("Sign up");
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


		//setting buttons that will generate the view for different kinds of users
		JButton costBtn = new JButton("New Customer");
		costBtn.addActionListener(controller);
		costBtn.setActionCommand("cost");
		JButton barbBtn = new JButton("New Barber");
		barbBtn.addActionListener(controller);
		barbBtn.setActionCommand("barb");
		JButton loginBtn = new JButton("Return to Front Page");
		loginBtn.addActionListener(controller);
		loginBtn.setActionCommand("cancel");
		this.add(costBtn);
		this.add(barbBtn);
		this.add(loginBtn);

		this.validate();
		this.repaint();		
	}

	//creates the view for a new costumer sign-up
	public void costumerLogin() {
		
		isCustomer = true;
		this.getContentPane().removeAll();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//creating top panel with name and mobile labels and text fields
		JLabel fullName = new JLabel("Full Name:");
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		this.add(fullName, c);
		JLabel mobileNum = new JLabel("Mobile Number:");
		c.gridx = 0;
		c.gridy = 1;
		this.add(mobileNum, c);
		JLabel mail = new JLabel ("E-mail:");
		c.gridx = 0;
		c.gridy = 2;
		this.add(mail, c);
		JLabel confMail = new JLabel ("Confirm E-mail:");
		c.gridx = 0;
		c.gridy = 3;
		this.add(confMail, c);
		JLabel password = new JLabel("Password:");
		c.gridx = 0;
		c.gridy = 4;
		this.add(password, c);
		JLabel passConf = new JLabel("Confirm Password: ");
		c.gridx = 0;
		c.gridy = 5;
		this.add(passConf, c);
		JTextField nameTF = new JTextField(20);
		c.insets = new Insets(10,10,10,10);
		c.gridx = 1;
		c.gridy = 0;
		this.add(nameTF, c);
		this.fullName = nameTF;
		JTextField mobileTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		this.add(mobileTF, c);
		this.mobile = mobileTF;
		JTextField mailTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		this.add(mailTF, c);
		this.email = mailTF;
		JTextField confMailTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 3;
		this.add(confMailTF, c);
		this.confEmail = confMailTF;
		JPasswordField passTF = new JPasswordField(20);
		c.gridx = 1;
		c.gridy = 4;
		this.add(passTF, c);
		this.password = passTF;
		JPasswordField passConfTF = new JPasswordField(20);
		c.gridx = 1;
		c.gridy = 5;
		this.add(passConfTF, c);
		this.confPass = passConfTF;
		JLabel error = new JLabel("");
		error.setForeground(Color.RED);
		error.setHorizontalAlignment(SwingConstants.CENTER);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(error, c);
		this.errorMsg = error;

		JButton register = new JButton ("Sign-up!");
		register.addActionListener(controller);
		register.setActionCommand("register");
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 7;
		this.add(register, c);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(controller);
		cancel.setActionCommand("cancel");
		c.gridx = 0;
		c.gridy = 8;
		this.add(cancel, c);

		this.validate();
		this.repaint();
	}


	//creates the view for a new costumer sign-up
	public void barberLogin() {
		
		isCustomer = false;
		this.getContentPane().removeAll();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//creating top panel with name and mobile labels and text fields
		JLabel fullName = new JLabel("Full Name:");
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		this.add(fullName, c);
		JLabel mobileNum = new JLabel("Mobile Number:");
		c.gridx = 0;
		c.gridy = 1;
		this.add(mobileNum, c);
		JLabel location = new JLabel("Location:");
		c.gridx = 0;
		c.gridy = 2;
		this.add(location, c);
		JLabel mail = new JLabel ("E-mail:");
		c.gridx = 0;
		c.gridy = 3;
		this.add(mail, c);
		JLabel confMail = new JLabel ("Confirm E-mail:");
		c.gridx = 0;
		c.gridy = 4;
		this.add(confMail, c);
		JLabel password = new JLabel("Password:");
		c.gridx = 0;
		c.gridy = 5;
		this.add(password, c);
		JLabel passConf = new JLabel("Confirm Password: ");
		c.gridx = 0;
		c.gridy = 6;
		this.add(passConf, c);
		JTextField nameTF = new JTextField(20);
		c.insets = new Insets(8,8,8,8);
		c.gridx = 1;
		c.gridy = 0;
		this.add(nameTF, c);
		this.fullName = nameTF;
		JTextField mobileTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		this.add(mobileTF, c);
		this.mobile = mobileTF;
		JTextField locTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		this.add(locTF, c);
		this.location = locTF;
		JTextField mailTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 3;
		this.add(mailTF, c);
		this.email = mailTF;
		JTextField confMailTF = new JTextField(20);
		c.gridx = 1;
		c.gridy = 4;
		this.add(confMailTF, c);
		this.confEmail = confMailTF;
		JPasswordField passTF = new JPasswordField(20);
		c.gridx = 1;
		c.gridy = 5;
		this.add(passTF, c);
		this.password = passTF;
		JPasswordField passConfTF = new JPasswordField(20);
		c.gridx = 1;
		c.gridy = 6;
		this.add(passConfTF, c);
		this.confPass = passConfTF;

		JLabel error = new JLabel("");
		error.setForeground(Color.RED);
		error.setHorizontalAlignment(SwingConstants.CENTER);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(error, c);
		this.errorMsg = error;

		JButton register = new JButton ("Sign-up!");
		register.addActionListener(controller);
		register.setActionCommand("register");
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 8;
		this.add(register, c);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(controller);
		cancel.setActionCommand("cancel");
		c.gridx = 0;
		c.gridy = 9;
		this.add(cancel, c);

		this.validate();
		this.repaint();

	}

	//sets size and visibility of the view
	private void settings() {
		this.setSize(400, 475);
		this.setResizable(false);
		this.setVisible(true);

	}
	
	public boolean isCustomer() {
		return isCustomer;
	}

	public JLabel getErrorMsg() {
		return errorMsg;
	}
	
	public String getFullName() {
		return fullName.getText();
	}

	public String getMobile() {
		return mobile.getText();
	}

	public String getLoc() {
		return location.getText();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getConfEmail() {
		return confEmail.getText();
	}

	public String getPassword() {
		return String.valueOf(password.getPassword());
	}

	public String getConfPass() {
		return String.valueOf(confPass.getPassword());
	}


}
