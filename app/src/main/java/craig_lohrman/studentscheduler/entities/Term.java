package craig_lohrman.studentscheduler.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Term")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;
    private String termStartDate;
    private String termEndDate;

    public Term(int termID, String termName, String termStartDate, String termEndDate) {
        this.termID = termID;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public Term() { }

    public int getTermID() {
        return termID;
    }

    public String getTermName() {
        return termName;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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