package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AdmView extends JFrame{

	private AdmController controller;
	private BorderLayout manager;
	private JTextField emailTF;
	private JTextField emailConfTF;
	private JPasswordField passTF;
	private JPasswordField passConfTF;
	private JLabel errorMsg;
	private JComboBox barbList;

	public AdmView(AdmController controller){
		// TODO Auto-generated constructor stub
		this.controller = controller;
		settings();
		createElements();

	}

	//sets basic elements (left panel) for the Customer View 
	private void createElements() {

		BorderLayout manager = new BorderLayout();
		this.setLayout(manager);
		this.manager = manager;
		JLabel title = new JLabel("Welcome, Master Skywalker.");
		title.setFont(new Font(title.getName(), Font.BOLD, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(title, BorderLayout.PAGE_START);

		//left Option panel
		JPanel optPanel = new JPanel();
		optPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		optPanel.setPreferredSize(new Dimension (200,500));
		optPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton newApt = new JButton("Approve Barbers");
		newApt.addActionListener(controller);
		newApt.setActionCommand("apBarb");
		JButton viewApt = new JButton("Create New Admin");
		viewApt.addActionListener(controller);
		viewApt.setActionCommand("newAdm");
		JButton close = new JButton("Exit Program");
		close.addActionListener(controller);
		close.setActionCommand("close");
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 10;
		c.insets = new Insets(20,0,0,0);
		c.gridy = 0;
		optPanel.add(newApt, c);
		c.gridy = 1;
		optPanel.add(viewApt, c);
		c.gridy = 2;
		optPanel.add(close, c);
		this.add(optPanel, BorderLayout.LINE_START);

		JPanel placeHolder = new JPanel();
		this.add(placeHolder, BorderLayout.CENTER);

		this.validate();
		this.repaint();

	}
	
	//creates panel with the options to register a new admin account
	public void newAdm() {
		
		this.remove(manager.getLayoutComponent(BorderLayout.CENTER));

		JPanel newAdm = new JPanel();
		newAdm.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.insets = new Insets(10, 10, 10, 10);
		
		JLabel mailLbl = new JLabel("Enter the e-mail:");
		c.gridx = 0;
		c.gridy = 0;
		newAdm.add(mailLbl, c);
		
		JTextField mailTF = new JTextField();
		c.gridy = 1;
		this.emailTF = mailTF;
		newAdm.add(mailTF, c);
		
		JLabel mailConfLbl = new JLabel("Confirm the e-mail");
		c.gridy = 2;
		newAdm.add(mailConfLbl, c);
		
		JTextField mailConfTF = new JTextField();
		c.gridy = 3;
		this.emailConfTF = mailConfTF;
		newAdm.add(mailConfTF, c);
		
		JLabel passLbl = new JLabel("Enter the password");
		c.gridy = 4;
		newAdm.add(passLbl, c);
		
		JPasswordField passTF = new JPasswordField();
		c.gridy = 5;
		this.passTF = passTF;
		newAdm.add(passTF, c);
				
		JLabel passConfLbl = new JLabel("Confirm the password");
		c.gridy = 6;
		newAdm.add(passConfLbl, c);
		
		JPasswordField passConfTF = new JPasswordField();
		c.gridy = 7;
		this.passConfTF = passConfTF;
		newAdm.add(passConfTF, c);
		
		JLabel error = new JLabel("");
		error.setForeground(Color.RED);
		error.setHorizontalAlignment(SwingConstants.CENTER);
		c.gridy = 8;
		this.errorMsg = error;
	    newAdm.add(error, c);
		
		JButton createAdm = new JButton("Create New Administrator");
		createAdm.addActionListener(controller);
		createAdm.setActionCommand("createAdm");
		createAdm.setHorizontalAlignment(SwingConstants.CENTER);
		c.ipadx = 10;
		c.ipady = 10;
		c.gridy = 9;
		newAdm.add(createAdm, c);
		
		this.add(newAdm, BorderLayout.CENTER);
		
		this.validate();
		this.repaint();

	}
	
	//creates the panel with the option to verify a new barber
	public void barbVerify() {
		
		this.remove(manager.getLayoutComponent(BorderLayout.CENTER));

		JPanel barberPanel = new JPanel();
		barberPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.insets = new Insets(10, 10, 10, 10);
		
		JLabel barbLbl = new JLabel("Please, select the barber registration you would like to approve:");
		barberPanel.add(barbLbl, c);
		
		JComboBox<String> barbBox = new JComboBox<>(controller.gettingBarbFromDB());
		c.gridy = 1;
		barberPanel.add(barbBox, c);
		this.barbList = barbBox;
		
		JButton verify = new JButton("Verify Barber");
		verify.setActionCommand("verify");
		verify.addActionListener(controller);
		c.gridy = 2;
		barberPanel.add(verify, c);
		
		
		this.add(barberPanel, BorderLayout.CENTER);

		this.validate();
		this.repaint();
	}

	public String getEmail() {
		return emailTF.getText();
	}

	public String getEmailConf() {
		return emailConfTF.getText();
	}

	public String getPassword() {
		return String.valueOf(passTF.getPassword());
	}

	public String getPassConf() {
		return String.valueOf(passConfTF.getPassword());
	}
	
	public JLabel getErrorMsg() {
		return this.errorMsg;
	}
	
	public String getSelectedBarb() {
		return (String) barbList.getSelectedItem();
	}

	public void settings() {

		this.setVisible(true);
		this.setSize(800, 600);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuClose= new JMenuItem("Close");
		menuClose.setActionCommand("close");
		menuClose.addActionListener(controller);
		menu.add(menuClose);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);

		this.validate();
		this.repaint();

	}

	

}
