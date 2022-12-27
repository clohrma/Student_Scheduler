package craig_lohrman.studentscheduler.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import craig_lohrman.studentscheduler.entities.Assessment;

public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM Assessment ORDER BY termName ASC")
    List<Assessment> getAllParts();

    @Query("SELECT * FROM Assessment WHERE assessmentName = :assessmentName ORDER BY assessmentName ASC")
    List<Assessment> getAllAssociatedParts(String assessmentName);
}
