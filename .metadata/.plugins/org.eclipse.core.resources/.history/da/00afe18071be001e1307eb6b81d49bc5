package user;

import java.util.ArrayList;

import course.Course;

public class Student extends User{
	private String faculty;
	private int level;
	private ArrayList<Course> courses;
	
	public Student(){
		super();
	}
	
	public String getFaculty() {
		return faculty;
	}
	
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
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
		System.out.println(this.getFaculty());
		System.out.println(this.getLevel());
	}

    public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public void setCourse(Course course) {
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}
}
