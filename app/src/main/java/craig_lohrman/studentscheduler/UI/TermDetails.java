package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.dao.CourseDAO;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Term;

public class TermDetails extends AppCompatActivity {

    EditText editTermName, editTermStart, editTermEnd;
    String name, startDate, endDate;
    int termID, numCourses;
    Term term, currentTerm;
    Course course;
    Repository repository;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTermName = findViewById(R.id.termNameET);
        editTermStart = findViewById(R.id.termStartDateET);
        editTermEnd = findViewById(R.id.termEndDateET);

        termID = getIntent().getIntExtra("termID", -1);
        name = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("termStartDate");
        endDate = getIntent().getStringExtra("termEndDate");

        editTermName.setText(name);
        editTermStart.setText(startDate);
        editTermEnd.setText(endDate);

        RecyclerView recyclerView = findViewById(R.id.courseDetailsRecycler);
        repository = new Repository(getApplication());
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button save = findViewById(R.id.saveTerm);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termID == -1) {
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
                for (Term term : repository.getAllTerms()) {
                    if (term.getTermID() == termID) {
                        currentTerm = term;
                    }
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseTermID() == termID) {
                        Toast.makeText(TermDetails.this, "Cannot delete " + currentTerm.getTermName() + " with Courses assigned to it.", Toast.LENGTH_LONG).show();
                    } else {
                        repository.delete(currentTerm);
                        Toast.makeText(TermDetails.this, "Deleted " + currentTerm.getTermName() + ".", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.termDetailsFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent);
            }
        });
    }
}