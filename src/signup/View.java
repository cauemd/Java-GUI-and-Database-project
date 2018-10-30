package signup;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class View extends JFrame{
	
	Controller controller;
	
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
		
		//creating top panel with name and mobile labels and text fields
		JPanel topPanel = new JPanel();
//		JPanel labelPanel = new JPanel(new BoxLayout());
//		JPanel tfPanel = new JPanel(new BoxLayout());
//		topPanel.add(labelPanel);
//		topPanel.add(tfPanel);
		
		JLabel fullName = new JLabel("Full Name:                       ");
		JTextField nameTF = new JTextField(20);
		JLabel mobileNum = new JLabel("Mobile Number:             ");
		JTextField mobileTF = new JTextField(20);
		JLabel mail = new JLabel ("E-mail:                              ");
		JTextField mailTF = new JTextField(20);
		JLabel confMail = new JLabel ("Confirm E-mail:              ");
		JTextField confMailTF = new JTextField(20);
		JLabel password = new JLabel("Password:                       ");
		JPasswordField passTF = new JPasswordField(20);
		JLabel passConf = new JLabel("Confirm Password:      ");
		JPasswordField passConfTF = new JPasswordField(20);
		
		topPanel.add(fullName);
		topPanel.add(nameTF);
		topPanel.add(mobileNum);
		topPanel.add(mobileTF);
		this.add(topPanel);
		
		
		topPanel.add(mail);
		topPanel.add(mailTF);
		topPanel.add(confMail);
		topPanel.add(confMailTF);
		topPanel.add(password);
		topPanel.add(passTF);
		topPanel.add(passConf);
		topPanel.add(passConfTF);
		
		this.validate();
		this.repaint();
		
	}

	//sets size and visibility of the view
	private void settings() {
		this.setSize(400, 300);
		this.setResizable(false);
		this.setVisible(true);

	}

}
