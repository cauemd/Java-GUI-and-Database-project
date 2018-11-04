package customer;

import main.Model;

public class CostController {
	
	private CostView view;
	private Model model;
	
	public CostController(){
		this.view = new CostView(this);
		System.out.println("Cheguei");
	}

}
