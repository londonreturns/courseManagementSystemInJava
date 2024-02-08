package user;

import java.util.ArrayList;

import course.Course;

public class Teacher extends User{
	private ArrayList<Course>courses;
	private String coursesString;
	
    public Teacher() {
        super();
        this.courses = new ArrayList<>();
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

	public String getCoursesString() {
		return coursesString;
	}

	public void setCoursesString(String coursesString) {
		this.coursesString = coursesString;
	}
	
}
