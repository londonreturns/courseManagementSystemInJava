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
