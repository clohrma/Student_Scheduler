package craig_lohrman.studentscheduler.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assessment")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentType;
    private String assessmentName;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private int assessmentCourseID;

    public Assessment(int assessmentID, String assessmentName, String assessmentStartDate, String assessmentEndDate, String assessmentType, int assessmentCourseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
        this.assessmentCourseID = assessmentCourseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public int getAssessmentCourseID() {
        return assessmentCourseID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public void setAssessmentCourseID(int assessmentCourseID) {
        this.assessmentCourseID = assessmentCourseID;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }
}