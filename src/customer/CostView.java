package customer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CostView extends JFrame{
	
	private CostController controller;
	
	public CostView(CostController c) {
		this.controller = c;
		this.add(new JLabel("E ae"));
		this.setVisible(true);
		this.setSize(300, 300);
	}

}
