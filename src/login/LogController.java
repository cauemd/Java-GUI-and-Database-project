package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Model;

public class LogController implements ActionListener {

	private LogView view;
	private Model model;

	public LogController() {
		this.view = new LogView(this);
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
				String pass = view.getPassTF();
				String email =view.getEmail();
				if (pass.equals("") || email.equals("")) {
					this.view.getErrorMsg().setText("Invalid email or Password.");
				} else if (this.model.login(pass, email)) {
					this.view.dispose();
				} else {
					this.view.getErrorMsg().setText("Invalid email or Password.");
				}
			}

			//creates a new instance of the signup interface
		} else if (e.getActionCommand().equals("signup")) {
			new signup.SignController();
			this.view.dispose();

			//terminates the program
		} else if (e.getActionCommand().equals("close")) {
			System.exit(0);
		}
	}


}