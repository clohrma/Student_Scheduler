package craig_lohrman.studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Instructor;

@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM Instructor ORDER BY instructorID ASC")
    List<Instructor> getAllInstructors();
}
