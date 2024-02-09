package user;

import course.Course;

public class Student extends User{
	private String level;
	private Course course;
	
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

}
