package utility;

import java.awt.Color;

import user.Admin;
public class AdminFrame  extends MenuFrame{

	public AdminFrame(){
		super();
	}
	
	public AdminFrame(int x_coord, int y_coord, int width, int height, Admin admin){
		super(x_coord, y_coord, width, height, admin);
	}
	
	public AdminFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}

	public void setRightPanel(Admin admin) {
		System.out.println(admin.getName());
	}
	
//	@Override
//	void setRightPanel() {	}
	
	

}
