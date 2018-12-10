package customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import main.Model;

//Controller class for the User view and model

public class CostController implements ActionListener {

	private CostView view;
	private Model model;
	private int userId;

	public CostController(String name, int id){
		this.userId = id;
		this.view = new CostView(this, name);
		this.model = new Model();
	}

	//manages user interactivity
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("close")) {
			System.exit(0);

		//generates the view where the user can make new appointments	
		} else if (e.getActionCommand().equals("newApt")) {
			view.newApt();

		//changes number of entries in daybox according to selected month	
		} else if (e.getActionCommand().equals("month")) {
			JComboBox<String> cb = (JComboBox) e.getSource();
			String month = (String) cb.getSelectedItem();
			view.setDayBox(month);

		//fills the combo box with the barber names from the DB
		} else if (e.getActionCommand().equals("barbBox")) {
			view.setBarbSearch(true);
			view.settingBarberCB();

		//fills the combo box with the location of the barbers from the DB	
		} else if (e.getActionCommand().equals("locBox")) {
			view.setBarbSearch(false);
			view.settingLocationCB();

		//generates the panel where the user can check his/her appointment
		}else if (e.getActionCommand().equals("checkApt")) {
			view.checkApt();
			
			//creates a new entry in the appointment relationship with the data the user selected in the CostView	
		} else if (e.getActionCommand().equals("apt")) {

			int monthTemp = view.getMonthCb() + 1;
			String month = "";
			if (monthTemp >= 10) {
				month = String.valueOf(monthTemp);
			} else {
				month = "0" + String.valueOf(monthTemp);
			}

			String dayTemp = view.getDayCb();
			String day = "";
			if (dayTemp.length() == 1) {
				day = "0" + dayTemp;
			} else {
				day = dayTemp;
			}

			String date = "2018-" + month + "-" + day;
			String hour = view.getHoursCb();

			//getting barber id using the fullName or the location
			int barbID = 0;
			if(view.isBarbSearch()) {
				barbID = model.gettingBarberId(view.getBarbCb(), true);
			}else {
				barbID =  model.gettingBarberId(view.getBarbCb(), false);
			}
			model.newApt(userId, barbID, date, hour);
		}
	}

	public String[] getBarbersFromDB() {
		return model.getBarbersList();
	}

	public String[] getLocationsFromDB() {
		return model.getLocationList();
	}

}
