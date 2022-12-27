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

    public Instructor(int instructorID, String instructorName, String instructorPhone, String instructorEmail) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }

    public Instructor(String instructorName) { }

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
}