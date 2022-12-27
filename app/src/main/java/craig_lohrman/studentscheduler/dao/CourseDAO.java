package craig_lohrman.studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import craig_lohrman.studentscheduler.entities.Course;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM Course ORDER BY cousreName ASC")
    List<Course> getAllParts();

    @Query("SELECT * FROM Course WHERE courseName = :courseName ORDER BY courseName ASC")
    List<Course> getAllAssociatedParts(String courseName);
}
