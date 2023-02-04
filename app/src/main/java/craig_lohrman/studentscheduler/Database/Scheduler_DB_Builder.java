package craig_lohrman.studentscheduler.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import craig_lohrman.studentscheduler.dao.AssessmentDAO;
import craig_lohrman.studentscheduler.dao.CourseDAO;
import craig_lohrman.studentscheduler.dao.InstructorDAO;
import craig_lohrman.studentscheduler.dao.TermDAO;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Instructor;
import craig_lohrman.studentscheduler.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 12, exportSchema = false)
public abstract class Scheduler_DB_Builder extends RoomDatabase {
    public abstract TermDAO termDAO();

    public abstract CourseDAO courseDAO();

    public abstract AssessmentDAO assessmentDAO();

    public abstract InstructorDAO instructorDAO();

    private static volatile Scheduler_DB_Builder INSTANCE;

    static Scheduler_DB_Builder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Scheduler_DB_Builder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Scheduler_DB_Builder.class, "SchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
