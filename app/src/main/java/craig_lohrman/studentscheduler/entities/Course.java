package craig_lohrman.studentscheduler.entities;

public class Course {

    private String courseName;
    private String courseStartDate;
    private String cousreEndDate;

    public Course(String courseName, String courseStartDate, String cousreEndDate) {
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.cousreEndDate = cousreEndDate;
    }

    public Course(String courseName) { }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public String getCousreEndDate() {
        return cousreEndDate;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public void setCousreEndDate(String cousreEndDate) {
        this.cousreEndDate = cousreEndDate;
    }
}
