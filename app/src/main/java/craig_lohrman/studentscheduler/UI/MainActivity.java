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
import craig_lohrman.studentscheduler.entities.Term;

public class MainActivity extends AppCompatActivity {

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

                Course course = new Course(0, "Course 1", "01-01-2023", "02-01-2023", 1);
                Course course2 = new Course(0, "Course 2", "02-01-2023", "03-01-2023", 2);
                Course course3 = new Course(0, "Course 3", "03-01-2023", "04-01-2023", 3);
                repository.insert(course);
                repository.insert(course2);
                repository.insert(course3);

                Assessment assessment = new Assessment(0, "Assessment 1", "01-01-2023", "02-01-2023","Performance");
                Assessment assessment2 = new Assessment(0, "Assessment 2", "02-01-2023", "03-01-2023","Objective");
                Assessment assessment3 = new Assessment(0, "Assessment 3", "03-01-2023", "04-01-2023","Performance");
                repository.insert(assessment);
                repository.insert(assessment2);
                repository.insert(assessment3);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}