package craig_lohrman.studentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Instructor;
import craig_lohrman.studentscheduler.entities.Term;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enter = findViewById(R.id.enterBtn);
        enter.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TermList.class);
            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Repository repository = new Repository(getApplication());

                Term term = new Term(0, "Term 1", "01-01-2023", "02-01-2023");

                Term term2 = new Term(0, "Term 2", "02-01-2023", "03-01-2023");

                Term term3 = new Term(0, "Term 3", "03-01-2023", "04-01-2023");

                repository.insert(term);
                repository.insert(term2);
                repository.insert(term3);

                Course course = new Course(0, "Course 1", "01-01-2023", "02-01-2023",
                        "In Progress", "1", "John Smith",1);

                Course course2 = new Course(0, "Course 2", "02-01-2023", "03-01-2023",
                        "Completed", "2", "Jim Smith",1);

                Course course3 = new Course(0, "Course 3", "03-01-2023", "04-01-2023",
                        "Dropped", "3", "Jane Doe",3);

                repository.insert(course);
                repository.insert(course2);
                repository.insert(course3);

                Instructor instructor = new Instructor(0, "John Smith", "123-456-7890",
                        "johnsmith@company.com", 1);

                Instructor instructor2 = new Instructor(0, "Jim Smith", "456-789-0123",
                        "jimsmith@company.com", 2);

                Instructor instructor3 = new Instructor(0, "Jane Doe", "789-012-3456",
                        "janedoe@company.com", 3);

                repository.insert(instructor);
                repository.insert(instructor2);
                repository.insert(instructor3);

                Assessment assessment = new Assessment(0, "Assessment 1", "01-01-2023",
                        "02-01-2023","Performance", 1);

                Assessment assessment2 = new Assessment(0, "Assessment 2", "02-01-2023",
                        "03-01-2023","Objective", 1);

                Assessment assessment3 = new Assessment(0, "Assessment 3", "03-01-2023",
                        "04-01-2023","Performance", 3);

                repository.insert(assessment);
                repository.insert(assessment2);
                repository.insert(assessment3);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}