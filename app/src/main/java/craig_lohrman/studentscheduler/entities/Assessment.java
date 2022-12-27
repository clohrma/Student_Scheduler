package craig_lohrman.studentscheduler.entities;

public class Assessment {

    private String assessmentType;
    private String assessmentName;
    private String assessmentStartDate;
    private String assessmentEndDate;

    public Assessment(String assessmentType, String assessmentName, String assessmentStartDate, String assessmentEndDate) {
        this.assessmentType = assessmentType;
        this.assessmentName = assessmentName;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
    }

    public Assessment() { }

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