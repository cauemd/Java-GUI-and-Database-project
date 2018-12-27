package admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import main.Model;

public class AdmController implements ActionListener, WindowListener {

	private AdmView view;
	private Model model;

	public AdmController() {

		view = new AdmView(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("close")) {
			System.exit(0);
		
		//select admin account creator view
		} else if (e.getActionCommand().equals("newAdm")) {
			view.newAdm();
			
		//select barber verification view	
		}else if (e.getActionCommand().equals("apBarb")) {
			view.barbVerify();
			
		//sends the data to the model to changed the status of a new barber to approved
		}else if (e.getActionCommand().equals("verify")) {
			model.verify(view.getSelectedBarb());
			view.barbVerify();
			
		//gets data from AdmView textbox, validates it and creates new entry in the database	
		} else if (e.getActionCommand().equals("createAdm")) {
			if(validate()) {
				String email = view.getEmail();
				String pass = view.getPassword();
				this.model = new Model();
				this.model.newAdmin(email, pass);
				this.view.dispose();
				this.view = new AdmView(this);
			}

		}

	}
	//validates all fields before inserting into the database
	private boolean validate() {
		//checking for empty fields
		if (view.getEmail().equals("") || view.getEmailConf().equals("") 
				|| view.getPassword().equals("") || view.getPassword().equals("")) {
			view.getErrorMsg().setText("Please, complete all fields.");
			return false;	
		}	
		//checking if the email is valid with the RFC 5322 regex
		if (!view.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
			view.getErrorMsg().setText("Invalid e-mail.");
			return false;
		} else if (!view.getEmail().equals(view.getEmailConf())) {
			view.getErrorMsg().setText("<html>Please make sure that both the email<br/> and the confirmation are the same.</html>");
			return false;
		}
		//checking if the password has at least one upper and lower case, one number, one symbol and is between 8 and 12 characters
		if (!view.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,12}$")) {
			view.getErrorMsg().setText("<html><p style=\"width:200px\">" + "Your password has to have one upper and lower key letter, a number, a symbol and between 8 and 12 characters." + "</p></html>");
			return false;
		} else if (!view.getPassword().equals(view.getPassConf())) {
			view.getErrorMsg().setText("<html>Please make sure that both the password<br/> and the confirmation are the same.</html>");
			return false;
		} else {
			return true;
		}
	}

	public String[] gettingBarbFromDB() {
		this.model = new Model();
		return model.getUnvBarbersList();
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		new login.LogController();
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
