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

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM Term ORDER BY termName ASC")
    List<Term> getAllParts();

    @Query("SELECT * FROM Term WHERE termName = :termName ORDER BY termName ASC")
    List<Term> getAllAssociatedParts(String termName);
}