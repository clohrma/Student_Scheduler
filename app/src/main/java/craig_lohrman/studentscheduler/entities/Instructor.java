package craig_lohrman.studentscheduler.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Instructor")
public class Instructor {

    @PrimaryKey(autoGenerate = true)
    private int instructorID;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int instructorCourseID;

    public Instructor(int instructorID, String instructorName, String instructorPhone, String instructorEmail, int instructorCourseID) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.instructorCourseID = instructorCourseID;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public int getInstructorCourseID() {
        return instructorCourseID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public void setInstructorCourseID(int instructorCourseID) {
        this.instructorCourseID = instructorCourseID;
    }
}