package customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class CostView extends JFrame{

	private CostController controller;
	private String user;
	private boolean barbSearch;
	private BorderLayout manager;
	private JComboBox<Integer> dayCb;
	private JComboBox<String> hoursCb;
	private JComboBox<String> barbCb;
	private JComboBox<String> monthCb;
	private JLabel cbLabel;
	private JTable table;

	public CostView(CostController c, String user) {
		this.controller = c;
		this.user = user;
		settings();
		createElements();
	}

	public String getUser() {
		return user;
	}

	//sets basic elements (left panel) for the Customer View 
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

		//creating RadioButtons that will create either a ComboBox with locations or barbers
		JRadioButton barbButton = new JRadioButton("Organize by barbers");
		barbButton.addActionListener(controller);
		barbButton.setActionCommand("barbBox");
		JRadioButton locButton = new JRadioButton("Organize by location");
		locButton.addActionListener(controller);
		locButton.setActionCommand("locBox");
		ButtonGroup group = new ButtonGroup();
		group.add(locButton);
		group.add(barbButton);
		c.gridx = 0;
		c.gridy = 0;
		newApt.add(barbButton, c);
		c.gridx = 1;
		c.gridy = 0;
		newApt.add(locButton, c);

		//creating barber name combo box and label
		JLabel selectLb = new JLabel("");
		c.gridx = 0;
		c.gridy = 1;
		this.cbLabel = selectLb;
		newApt.add(selectLb, c);
		JComboBox<String> barbers = new JComboBox<>();
		this.barbCb = barbers;
		c.gridy = 2;
		c.gridwidth = 3;
		newApt.add(barbers, c);

		//creating month combo box and label
		JLabel monthLb = new JLabel("Month: ");
		c.gridwidth = 1;
		c.gridy = 3;
		c.gridx = 0;
		newApt.add(monthLb, c);
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
				"November", "December"};
		JComboBox<String> monthBox = new JComboBox<>(months);
		this.monthCb = monthBox;
		monthBox.addActionListener(controller);
		monthBox.setSelectedItem("Test");
		monthBox.setActionCommand("month");
		c.gridy = 4;
		c.gridx = 0;
		newApt.add(monthBox, c);


		//creates day combo box and label
		JLabel dayLb = new JLabel("Day: ");
		c.gridy = 3;
		c.gridx = 1;
		newApt.add(dayLb, c);
		JComboBox<Integer> dayBox = new JComboBox<>();
		this.dayCb = dayBox;
		c.gridy = 4;
		c.gridx = 1;
		newApt.add(dayBox, c);

		//creates hour box and label
		JLabel hourLb = new JLabel("Time: ");
		c.gridy = 3;
		c.gridx = 2;
		newApt.add(hourLb, c);

		//The string of hours could be done using a for loop or with different time slots if needed.
		String[] appTimes = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00",
				"14:30", "15:00", "15:30", "16:00", "!6:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
		JComboBox<String> hourBox = new JComboBox<>(appTimes);
		this.hoursCb = hourBox;
		c.gridy = 4;
		c.gridx = 2;
		newApt.add(hourBox, c);

		JButton sendApt = new JButton("Book Appointment");
		sendApt.addActionListener(controller);
		sendApt.setActionCommand("apt");
		sendApt.setHorizontalAlignment(SwingConstants.CENTER);
		c.insets = new Insets(50,150,0,0);
		c.ipadx = 40;
		c.ipady = 40;
		c.weightx = 3;
		c.gridy = 5;
		c.gridx = 0;
		newApt.add(sendApt, c);

		this.add(newApt, BorderLayout.CENTER);

		this.validate();
		this.repaint();

	}

	//Getters and Setters
	public void setBarbSearch(boolean flag) {
		barbSearch = flag;
	}
	public boolean isBarbSearch() {
		return barbSearch;
	}

	public String getDayCb() {
		return String.valueOf(dayCb.getSelectedItem());
	}

	public String getHoursCb() {
		return (String) hoursCb.getSelectedItem();
	}

	public String getBarbCb() {
		return (String) barbCb.getSelectedItem();
	}

	public int getMonthCb() {
		return monthCb.getSelectedIndex();
	}

	//populates the day combo box according to the month selected (using 2018 calendar)
	public void setDayBox(String month) {
		if (month.equals("January") || month.equals("March") || month.equals("May") || month.equals("July") ||
				month.equals("August") || month.equals("October") || month.equals("December")){
			Integer[] days = new Integer[31]; 
			for(int i=1;i<=31;i++){
				days[i - 1] = i;
				dayCb.addItem(days[i - 1]);
			}
		} else if (month.equals("April") || month.equals("June") || month.equals("September") || month.equals("November")) {
			Integer[] days = new Integer[30]; 
			for(int i=1;i<=30;i++){
				days[i - 1] = i;
				dayCb.addItem(days[i - 1]);
			}
		} else {
			Integer[] days = new Integer[29]; 
			for(int i=1;i<=29;i++){
				days[i - 1] = i;
				dayCb.addItem(days[i - 1]);
			}
		}
	}

	//populates the JComboBox with a list of the names of all verified barbers
	public void settingBarberCB() {

		this.barbCb.removeAllItems();
		for (String item: controller.getBarbersFromDB()) {
			this.barbCb.addItem(item);
		}
		this.cbLabel.setText("Please, select your barber:");
	}

	//populates the JComboBox with a list of the location of all verified barbers
	public void settingLocationCB() {
		this.barbCb.removeAllItems();
		for (String item: controller.getLocationsFromDB()) {
			this.barbCb.addItem(item);
		}
		this.cbLabel.setText("Please, select your location:");
	}

	//sets basic settings for the frame and creates the menu bar
	public void settings() {

		this.addWindowListener(controller);
		this.setVisible(true);
		this.setSize(1000, 650);
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

	public void checkApt() {
		this.remove(manager.getLayoutComponent(BorderLayout.CENTER));

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(tablePanel, BorderLayout.CENTER);

		//creating table header and data array
		String[] columnNames = {"App. N°", "Barber", "Phone Number", "App. Date", "Hour", "Leave Review"};
		Object[][] data = controller.getCustomersApt();
		JTable table = new JTable(data, columnNames) {

			@Override
			public boolean isCellEditable(int row, int col) {
				return (col == 5); 
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, c);
		this.table = table;
		
		JButton approve = new JButton("Submit Reviews");
		approve.addActionListener(controller);
		approve.setActionCommand("subReview");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		tablePanel.add(approve, c);
		
		JButton createReview = new JButton("Create Review");
		createReview.addActionListener(controller);
		createReview.setActionCommand("newReview");
		c.gridx = 1;
		tablePanel.add(createReview, c);
		
		this.validate();
		this.repaint();

	}

	
	public ArrayList<?> gettingReviews() {
		ArrayList<Object> reviewsList = new ArrayList<>();
		boolean isCellEmpty = false;
		int counter = 0;
		
		//Checking how many rows with data we have in the table (clunky way, wouldn't be needed if Lists were used to get the apt)
		while(!isCellEmpty) {
			if(table.getValueAt(counter, 0) == null) {
				isCellEmpty = true;
			} else {
				counter++;
			}
		}
		
		//populating the list with the appIDs and respective reviews
		for (int x = 0; x < counter; x++) {
				reviewsList.add(table.getValueAt(x, 5));
				reviewsList.add(table.getValueAt(x, 0));
			
		}
		
		return reviewsList;
	}	
	
	//pops dialog box where user can input his review for the selected appointment
	public void reviewPrompt() {
		String review = ""; 
		try {
			review = JOptionPane.showInputDialog("Please, enter your review for the appointment:");
			table.getModel().setValueAt(review, table.getSelectedRow(), 5);
		} catch (Exception e) {
			return;
		}
		
	}

}
