package barber;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class BarbView extends JFrame {

	private BorderLayout manager;
	private BarbController controller;
	private String user;
	private JTable table;

	public BarbView(BarbController controller, String name) {

		this.controller = controller;
		this.user = name;
		settings();
		createElements();

	}

	//sets basic elements (left panel) for the Barber View 
	private void createElements() {

		BorderLayout manager = new BorderLayout();
		this.setLayout(manager);
		this.manager = manager;
		JLabel title = new JLabel("Welcome, " + user);
		title.setFont(new Font(title.getName(), Font.BOLD, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(title, BorderLayout.PAGE_START);

		//left Option panel
		JPanel optPanel = new JPanel();
		optPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		optPanel.setPreferredSize(new Dimension (200,500));
		optPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton newApt = new JButton("Approve New Bookings");
		newApt.addActionListener(controller);
		newApt.setActionCommand("approve");
		JButton viewApt = new JButton("Check My Bookings");
		viewApt.addActionListener(controller);
		viewApt.setActionCommand("showApp");
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

	//creates table with the appointments that haven't been approved yet for the barber logged in
	public void approveScreen() {

		this.remove(manager.getLayoutComponent(BorderLayout.CENTER));

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(tablePanel, BorderLayout.CENTER);

		//creating table header and data array
		String[] columnNames = {"App. N°", "Costumer Name", "Mobile", "App. Date", "Hour", "Approved"};
		Object[][] data = controller.getAppData(true);
		JTable table = new JTable(data, columnNames) {

			@Override
			public Class<?> getColumnClass(int column) {
				if(column == 5){
					return Boolean.class;
				}
				// rest of them as a text
				return String.class;
			}
			
			@Override
			public boolean isCellEditable(int row, int col) {
			    return (col == 5); 
			}
		};
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, c);
		this.table = table;
		
		JButton approve = new JButton("Approve Appointments");
		approve.addActionListener(controller);
		approve.setActionCommand("updateDB");
		c.gridy = 1;
		tablePanel.add(approve, c);

		this.validate();
		this.repaint();

	}

	//returns an array list with the appointments set as "true" to change their status in the DB
	public ArrayList<Object> approvingAppointments(){
		ArrayList<Object> appointments = new ArrayList<>();
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
		
		//populating the list with the appIDs that will be approved in the DB
		for (int x = 0; x < counter; x++) {
			if((boolean) table.getValueAt(x, 5)) {
				appointments.add(table.getValueAt(x, 0));
			}
		}
		
		return appointments;
	}


	//Sets basic format of the frame
	public void settings() {

		this.addWindowListener(controller);
		this.setVisible(true);
		this.setSize(1000, 600);
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

	//Show Complete List of Appointments
	public void showAppointments() {
		
		this.remove(manager.getLayoutComponent(BorderLayout.CENTER));

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(tablePanel, BorderLayout.CENTER);

		//creating table header and data array
		String[] columnNames = {"App. N°", "Costumer Name", "Mobile", "App. Date", "Hour", "Review"};
		Object[][] data = controller.getAppData(false);
		JTable table = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int col) {
			    return (col == 5); 
			}
		};
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, c);
		this.table = table;
		
		this.validate();
		this.repaint();

		
	}


}
