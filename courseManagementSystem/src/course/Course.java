package course;

public class Course {
	private int courseId;
	private String courseName;
	
	public Course() {
	}
	
	public Course(String course) {
		setCourseName(course);
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
	
	
}
