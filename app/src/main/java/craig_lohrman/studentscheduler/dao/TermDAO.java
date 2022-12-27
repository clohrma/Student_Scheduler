package craig_lohrman.studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import craig_lohrman.studentscheduler.entities.Term;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Delete
    void delete(Term term);

    @Update
    void update(Term term);

    @Query("SELECT * FROM Term ORDER BY termID")
    List<Term> getAllTerms();

    @Query("SELECT * FROM Term WHERE termID = :termID ORDER BY termID ASC")
    List<Term> getAllAssociatedTerms(int termID);
}
