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
			if (view.isCostumer == true){
				if (costumerValidate()) {
					
				}
			}
		}

	}

	private boolean costumerValidate() {
		// TODO Auto-generated method stub
		return false;
	}
}

