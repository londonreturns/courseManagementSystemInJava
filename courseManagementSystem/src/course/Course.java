package course;

public class Course {
	private String courseId;
	private String courseName;
	private String faculty;
	private String level;
	
	public Course() {
	}
	
	public Course(String courseName) {
		setCourseName(courseName);
	}
	
	public Course(String courseName, String courseId) {
		setCourse(courseName, courseId);
	}

	public Course(String courseName, String courseId, String faculty, String level) {
		setCourse(courseName, courseId, faculty, level);
	}

	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String id) {
		this.courseId = id;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void setCourse(String course, String courseId) {
		this.courseName = course;
		this.courseId = courseId;
	}
	
	public void setCourse(String courseName, String courseId, String faculty, String level) {
		this.courseName = courseName;
		this.courseId = courseId;
		this.faculty = faculty;
		this.level = level;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
