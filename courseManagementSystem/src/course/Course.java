package course;

public class Course {
	private int courseId;
	private String courseName;
	
	public Course() {
	}
	
	public Course(String course) {
		setCourseName(course);
	}
	
	public Course(String course, int courseId) {
		setCourse(course, courseId);
	}

	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void setCourse(String course, int courseId) {
		this.courseName = course;
		this.courseId = courseId;
	}
	
}
