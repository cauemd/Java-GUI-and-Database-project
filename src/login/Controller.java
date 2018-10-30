package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	private View view;
	private Model model;

	public Controller() {
		this.view = new View(this);
		this.model = new Model();

	}

	//setting actions for our frame
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//gets email and password from text and password fields and call the login method, showing error messages if needed
		if(e.getActionCommand().equals("login")) {
			if (!this.view.getCaptcha()) {
				this.view.getErrorMsg().setText("Please, select the check box.");
			} else {
				String pass = String.valueOf(this.view.getPassTF());
				String email = this.view.getEmail();
				if (this.model.login(pass, email)) {
					this.view.dispose();
				} else {
					this.view.getErrorMsg().setText("Invalid email or Password.");
				}
			}
		
		//creates a new instance of the signup interface
		} else if (e.getActionCommand().equals("signup")) {
			new signup.Controller();
			this.view.dispose();
			
		//terminates the program
		} else if (e.getActionCommand().equals("close")) {
			System.exit(0);
		}
	}

	
}