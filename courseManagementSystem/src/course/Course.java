package course;

import java.util.ArrayList;

public class Course {
	private String courseId;
	private String courseName;
	private ArrayList<Module_> modules;
	private boolean isActive;
	
	public Course() {
        this.modules = new ArrayList<>();
    }
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public ArrayList<Module_> getModules() {
		return modules;
	}
	
	public void setModules(ArrayList<Module_> modules) {
		this.modules = modules;
	}
	
	public void setModule(Module_ module) {
		modules.add(module);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
		
	
}
