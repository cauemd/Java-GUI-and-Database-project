package customer;

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
import javax.swing.SwingConstants;

public class CostView extends JFrame{
	
	private CostController controller;
	private String user;
	private BorderLayout manager;
	private JComboBox dayCb;
	private JComboBox hoursCb;
	private JComboBox barbCb;
	
	public CostView(CostController c, String user) {
		this.controller = c;
		this.user = user;
		settings();
		createElements();
	}

	private void createElements() {
		
		BorderLayout manager = new BorderLayout();
		this.setLayout(manager);
		this.manager = manager;
		JLabel title = new JLabel("Welcome, " + user + "!");
		title.setFont(new Font(title.getName(), Font.BOLD, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(title, BorderLayout.PAGE_START);
		
		//left Option panel
		JPanel optPanel = new JPanel();
		optPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		optPanel.setPreferredSize(new Dimension (200,500));
		optPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton newApt = new JButton("New Appointment");
		newApt.addActionListener(controller);
		newApt.setActionCommand("newApt");
		JButton viewApt = new JButton("Check My Appointments");
		viewApt.addActionListener(controller);
		viewApt.setActionCommand("seeApt");
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
	
	//creates panel with the options to make a new appointment
	public void newApt() {
		
		//clearing CENTER position in the frame
		this.remove(manager.getLayoutComponent(BorderLayout.CENTER));
		
		JPanel newApt = new JPanel();
		newApt.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.insets = new Insets(10, 10, 10, 10);
		
		//creating barber name combo box and label
		JLabel barbLb = new JLabel("Please, select service provider:");
		c.gridy = 0;
		newApt.add(barbLb, c);
		JComboBox barbers = new JComboBox();
		this.barbCb = barbers;
		c.gridy = 1;
		c.gridwidth = 3;
		newApt.add(barbers, c);
		
		//creating month combo box and label
		JLabel monthLb = new JLabel("Month: ");
		c.gridwidth = 1;
		c.gridy = 2;
		c.gridx = 0;
		newApt.add(monthLb, c);
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
						   "November", "December"};
		JComboBox monthBox = new JComboBox(months);
		monthBox.addActionListener(controller);
		monthBox.setSelectedItem("Test");
		monthBox.setActionCommand("month");
		c.gridy = 3;
		c.gridx = 0;
		newApt.add(monthBox, c);
		
		
		//creates day combo box and label
		JLabel dayLb = new JLabel("Day: ");
		c.gridy = 2;
		c.gridx = 1;
		newApt.add(dayLb, c);
		JComboBox dayBox = new JComboBox();
		this.dayCb = dayBox;
		c.gridy = 3;
		c.gridx = 1;
		newApt.add(dayBox, c);
		
		//creates hour day box and label
		JLabel hourLb = new JLabel("Time: ");
		c.gridy = 2;
		c.gridx = 2;
		newApt.add(hourLb, c);
		JComboBox hourBox = new JComboBox();
		this.hoursCb = hourBox;
		c.gridy = 3;
		c.gridx = 2;
		newApt.add(hourBox, c);
		
		JButton sendApt = new JButton("Book Appointment");
		sendApt.setHorizontalAlignment(SwingConstants.CENTER);
		c.insets = new Insets(50,150,0,0);
		c.ipadx = 40;
		c.ipady = 40;
		c.weightx = 3;
		c.gridy = 4;
		c.gridx = 0;
//		c.anchor = GridBagConstraints.CENTER;
//		c.fill = GridBagConstraints.CENTER;
		newApt.add(sendApt, c);
		
		this.add(newApt, BorderLayout.CENTER);
		
		this.validate();
		this.repaint();

	}
	
	public JComboBox getDayCb() {
		return dayCb;
	}

	public JComboBox getBarbCb() {
		return barbCb;
	}
	
	//sets basic settings for the frame and creates the menu bar
	private void settings() {

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
