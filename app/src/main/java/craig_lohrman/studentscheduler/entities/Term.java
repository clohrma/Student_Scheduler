package craig_lohrman.studentscheduler.entities;

public class Term {

    private String termName;
    private String termStartDate;
    private String termEndDate;

    public Term(String termName, String termStartDate, String termEndDate) {
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public Term() { }

    public String getTermName() {
        return termName;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }
}