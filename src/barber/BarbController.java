package barber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import main.Model;

public class BarbController implements ActionListener, WindowListener{

	private int id;
	private BarbView view;
	private Model model;

	public BarbController(String fullName, int id) {
		this.id = id;
		this.view = new BarbView(this, fullName);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("close")) {
			System.exit(0);
		}else if (e.getActionCommand().equals("approve")) {
			view.approveScreen();
		}else if (e.getActionCommand().equals("showApp")) {
			view.showAppointments();			
		}else if (e.getActionCommand().equals("updateDB")) {
			this.model = new Model();
			model.approveAppointments(view.approvingAppointments());
			view.approveScreen();
		}
	}

	//returns an object array with all appointments information, flag used to retrieve either unapproved apts or ones with a review
	public Object[][] getAppData(boolean check) {
		this.model = new Model();
		return model.gettingAppData(id, check);
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
