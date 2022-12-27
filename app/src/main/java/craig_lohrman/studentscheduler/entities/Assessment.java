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

    public Assessment(int assessmentID, String assessmentType, String assessmentName, String assessmentStartDate, String assessmentEndDate) {
        this.assessmentID = assessmentID;
        this.assessmentType = assessmentType;
        this.assessmentName = assessmentName;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
    }

    public Assessment() { }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentType() {
        return assessmentType;
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

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
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
}