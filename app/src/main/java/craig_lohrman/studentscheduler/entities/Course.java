package craig_lohrman.studentscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Course")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String courseStartDate;
    private String courseEndDate;
    private String courseStatus;
    private String courseShareNote;
    private String courseInstructorName;
    private int courseTermID;

    public Course(int courseID, String courseName, String courseStartDate, String courseEndDate, String courseStatus, String courseShareNote, String courseInstructorName, int courseTermID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseShareNote = courseShareNote;
        this.courseInstructorName = courseInstructorName;
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

    public String getCourseStatus() {
        return courseStatus;
    }

    public String getCourseShareNote() {
        return courseShareNote;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
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

    public void setCourseStatus(String status) {
        this.courseStatus = status;
    }

    public void setCourseShareNote(String courseShareNote) {
        this.courseShareNote = courseShareNote;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public void setCourseTermID(int courseTermID) {
        this.courseTermID = courseTermID;
    }

    @NonNull
    @Override
    public String toString() {
        return courseName;
    }
}
