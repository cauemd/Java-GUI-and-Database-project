package signup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
	
	private View view;
	private Model model;
	
	public Controller() {
		this.view = new View(this);
		this.model = new Model();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("close")) {
			System.exit(0);
		}
	}
	
	
}
