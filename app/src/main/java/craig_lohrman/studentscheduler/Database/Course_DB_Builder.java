package craig_lohrman.studentscheduler.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import craig_lohrman.studentscheduler.dao.CourseDAO;
import craig_lohrman.studentscheduler.entities.Course;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class Course_DB_Builder extends RoomDatabase {
    public abstract CourseDAO courseDAO();

    private static volatile Course_DB_Builder INSTANCE;
    static Course_DB_Builder getDatabase(final Context context) {
        if(INSTANCE==null){
            synchronized (Course_DB_Builder.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Course_DB_Builder.class, "Course.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
