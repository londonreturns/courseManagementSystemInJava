package user;

import java.util.ArrayList;

import course.Course;
import course.Module_;

public class Student extends User{
	private String level;
	private Course course;
	private ArrayList<Module_> modules = new ArrayList<>();
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}	
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	@Override
	public void displayDetails() {
		System.out.println(this.getName());
		System.out.println(this.getId());
		System.out.println(this.getEmail());
		System.out.println(this.getPassword());
		System.out.println(this.getContact());
		System.out.println(this.getTypeOfUser());
		System.out.println(this.getDateOfBirth());
		System.out.println(this.getCourse().getCourseName());
		System.out.println(this.getLevel());
	}

	public ArrayList<Module_> getModules() {
		return modules;
	}

	public void setModules(ArrayList<Module_> module) {
		this.modules = module;
	}
	
	public void addModule(Module_ module) {
		modules.add(module);
	}
}
