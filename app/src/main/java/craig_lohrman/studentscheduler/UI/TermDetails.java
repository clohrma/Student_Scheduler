package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Term;

public class TermDetails extends AppCompatActivity {

    EditText editTermName, editTermStart, editTermEnd;
    String name, startDate, endDate;
    int id;
    Term term;
    Course course;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTermName = findViewById(R.id.termNameEditText);
        editTermStart = findViewById(R.id.termStartDateEditText);
        editTermEnd = findViewById(R.id.termEndDateEditText);

        name = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("termStartDate");
        endDate = getIntent().getStringExtra("termEndDate");

        editTermName.setText(name);
        editTermStart.setText(startDate);
        editTermEnd.setText(endDate);

        RecyclerView recyclerView = findViewById(R.id.termDetailsCourseListRecycler);
        repository = new Repository(getApplication());
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button save = findViewById(R.id.saveTerm);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == -1) {
                    term = new Term(0, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repository.insert(term);
                } else {
                    term = new Term(0, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repository.update(term);
                }
            }
        });

        Button delete = findViewById(R.id.deleteTerm);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO  Look for assigned courses if none delete if not alert you
                    cannot delete until there are no assigned courses to the term*/
            }
        });

        FloatingActionButton fab = findViewById(R.id.termDetailsFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseList.class);
                startActivity(intent);
            }
        });
    }
}