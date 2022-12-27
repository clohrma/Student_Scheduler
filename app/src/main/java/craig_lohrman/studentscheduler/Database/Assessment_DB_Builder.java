package craig_lohrman.studentscheduler.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import craig_lohrman.studentscheduler.dao.AssessmentDAO;
import craig_lohrman.studentscheduler.entities.Assessment;

@Database(entities = {Assessment.class}, version = 1, exportSchema = false)
public abstract class Assessment_DB_Builder extends RoomDatabase {

    public abstract AssessmentDAO assessmentDAO();

    private static volatile Assessment_DB_Builder INSTANCE;
    static Assessment_DB_Builder getDatabase(final Context context) {
        if(INSTANCE==null){
            synchronized (Assessment_DB_Builder.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Assessment_DB_Builder.class, "Assessment.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
