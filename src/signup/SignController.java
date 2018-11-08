package signup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Model;

public class SignController implements ActionListener {

	private SignView view;
	private Model model;

	public SignController() {
		this.view = new SignView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("close")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("cost")) {
			view.costumerLogin();
		} else if (e.getActionCommand().equals("barb")) {
			view.barberLogin();
		} else if (e.getActionCommand().equals("cancel")) {
			this.view.dispose();
			new login.LogController();
		//runs validation and inserts new entry in the database if valid
		} else if (e.getActionCommand().equals("register")) {
			if (validate()) {
				String name = view.getFullName().trim();
				String mobile = view.getMobile().trim();
				String email = view.getEmail();
				String pass = view.getPassword();

				if (view.isCustomer()) {
					this.model = new Model();
					model.newCust(name, mobile, email, pass);
					this.view.dispose();
					new login.LogController();
				} else {
					String loc = view.getLoc().trim();
					this.model = new Model();
					model.newBarb(name, mobile, loc, email, pass);
					this.view.dispose();
					new login.LogController();
				}

			}
		}
	}


	//validates all fields before inserting into the database
	private boolean validate() {
		//checking for empty fields
		if (view.getFullName().equals("") || view.getMobile().equals("") || view.getPassword().equals("")) {
			view.getErrorMsg().setText("Please, complete all fields.");
			return false;	
		}else if(!view.isCustomer() && view.getLoc().equals("")) {
			view.getErrorMsg().setText("Please, complete all fields.");
			return false;
		}
		//checking if the email is valid with the RFC 5322 regex
		if (!view.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
			view.getErrorMsg().setText("Invalid e-mail.");
			return false;
		} else if (!view.getEmail().equals(view.getConfEmail())) {
			view.getErrorMsg().setText("<html>Please make sure that both the email<br/> and the confirmation are the same.</html>");
			return false;
		}
		//checking if the password has at least one upper and lower case, one number, one symbol and is between 8 and 12 characters
		if (!view.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,12}$")) {
			view.getErrorMsg().setText("<html><p style=\"width:200px\">" + "Your password has to have one upper and lower key letter, a number, a symbol and between 8 and 12 characters." + "</p></html>");
			return false;
		} else if (!view.getPassword().equals(view.getConfPass())) {
			view.getErrorMsg().setText("<html>Please make sure that both the password<br/> and the confirmation are the same.</html>");
			return false;
		} else {
			return true;
		}
	}

}

