package customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import main.Model;

//Controller class for the User view and model

public class CostController implements ActionListener {
	
	private CostView view;
	private Model model;
	
	public CostController(String fullName){
		this.view = new CostView(this, fullName);
	}

	//manages user interactivity
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("close")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("newApt")) {
			this.view.newApt();
			//this.model.fillBarbJbox(this.view.getBarbCb());
			
		//changes number of entries in daybox according to selected month	
		} else if (e.getActionCommand().equals("month")) {
				JComboBox cb = (JComboBox)e.getSource();
				String month = (String) cb.getSelectedItem();
				
				if (month.equals("January") || month.equals("March") || month.equals("May") || month.equals("July") ||
						month.equals("August") || month.equals("October") || month.equals("December")){
					Integer[] days = new Integer[31]; 
					for(int i=1;i<=31;i++){
					    days[i - 1] = i;
					    this.view.getDayCb().addItem(days[i - 1]);
					}
				} else if (month.equals("April") || month.equals("June") || month.equals("September") || month.equals("November")) {
					Integer[] days = new Integer[30]; 
					for(int i=1;i<=30;i++){
					    days[i - 1] = i;
					    this.view.getDayCb().addItem(days[i - 1]);
					}
				} else {
					Integer[] days = new Integer[29]; 
					for(int i=1;i<=29;i++){
					    days[i - 1] = i;
					    this.view.getDayCb().addItem(days[i - 1]);
					}
				}
		}		
	}

}
