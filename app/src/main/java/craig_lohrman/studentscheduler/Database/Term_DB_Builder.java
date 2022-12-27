package craig_lohrman.studentscheduler.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import craig_lohrman.studentscheduler.dao.TermDAO;
import craig_lohrman.studentscheduler.entities.Term;

@Database(entities = {Term.class}, version = 1, exportSchema = false)
public abstract class Term_DB_Builder extends RoomDatabase {
    public abstract TermDAO termDAO();

    private static volatile Term_DB_Builder INSTANCE;
    static Term_DB_Builder getDatabase(final Context context) {
        if(INSTANCE==null){
            synchronized (Term_DB_Builder.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Term_DB_Builder.class, "Term.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
