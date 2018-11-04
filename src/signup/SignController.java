package signup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Model;

public class SignController implements ActionListener {

	private SignView view;
	private Model model;

	public SignController() {
		this.view = new SignView(this);
		this.model = new Model();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("close")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("cost")) {
			view.costumerLogin();
		} else if (e.getActionCommand().equals("barb")) {
			view.barberLogin();
		} else if (e.getActionCommand().equals("cancel")) {
			this.view.dispose();
			new login.LogController();
		} else if (e.getActionCommand().equals("register")) {
			if (validate()) {
					
				}
			}
		}


	//validates all fields before inserting into the database
	private boolean validate() {
		//checking for empty fields
		if (view.getFullName().equals("") || view.getMobile().equals("") || view.getPassword().equals("")) {
			view.getErrorMsg().setText("Please, complete all fields.");
			return false;	
		}else if(!view.isCostumer() && view.getLoc().equals("")) {
			view.getErrorMsg().setText("Please, complete all fields.");
			return false;
		}
		//checking if the email is valid with the RFC 5322 regex
		if (!view.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
			view.getErrorMsg().setText("Invalid e-mail.");
			return false;
		}
		return true;
	}
}

