package craig_lohrman.studentscheduler.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Course")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String courseStartDate;
    private String courseEndDate;
    private int courseTermID;

    public Course(int courseID, String courseName, String courseStartDate, String courseEndDate, int courseTermID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseTermID = courseTermID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public int getCourseTermID() {
        return courseTermID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public void setCourseTermID(int courseTermID) {
        this.courseTermID = courseTermID;
    }
}
