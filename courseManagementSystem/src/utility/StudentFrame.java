package utility;

import java.awt.Color;

import user.Admin;
import user.Student;
import user.Teacher;

public class StudentFrame extends MenuFrame {

	public StudentFrame(){
		super();
	}
	
	public StudentFrame(int x_coord, int y_coord, int width, int height, Student student){
		super(x_coord, y_coord, width, height, student);
	}
	
	public StudentFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}

}
