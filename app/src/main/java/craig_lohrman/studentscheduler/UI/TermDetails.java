package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.dao.CourseDAO;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Term;

public class TermDetails extends AppCompatActivity {

    final Calendar mCalDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener termStartDateDP, termEndDateDP;
    EditText termNameET, startDateET, endDateET;
    String termNameString, startDateString, endDateString;
    int termID, numCourses;
    Term term, currentTerm;
    Course course;
    Repository repository;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        termNameET = findViewById(R.id.termNameET);
        startDateET = findViewById(R.id.termStartDateET);
        endDateET = findViewById(R.id.termEndDateET);

        termID = getIntent().getIntExtra("termID", -1);
        termNameString = getIntent().getStringExtra("termName");
        startDateString = getIntent().getStringExtra("termStartDate");
        endDateString = getIntent().getStringExtra("termEndDate");

        String dateForm = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateForm, Locale.US);
        termNameET.setText(termNameString);
        startDateET.setText(dateSDF.format(new Date()));
        endDateET.setText(dateSDF.format(new Date()));

        System.out.println("The start date is " +startDateET + " and the end date is " + endDateET);
        startDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = startDateET.getText().toString();

                try {
                    mCalDate.setTime(dateSDF.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, termStartDateDP, mCalDate.get(Calendar.YEAR),
                        mCalDate.get(Calendar.MONTH), mCalDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termStartDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalDate.set(Calendar.YEAR, year);
                mCalDate.set(Calendar.MONTH, month);
                mCalDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateForm = "MM/dd/yyyy";
                SimpleDateFormat dateSDF = new SimpleDateFormat(dateForm, Locale.US);

                startDateET.setText(dateSDF.format(mCalDate.getTime()));
            }
        };

        endDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = endDateET.getText().toString();

                try {
                    mCalDate.setTime(dateSDF.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, termEndDateDP, mCalDate.get(Calendar.YEAR),
                        mCalDate.get(Calendar.MONTH), mCalDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termEndDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalDate.set(Calendar.YEAR, year);
                mCalDate.set(Calendar.MONTH, month);
                mCalDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateForm = "MM/dd/yyyy";
                SimpleDateFormat dateSDF = new SimpleDateFormat(dateForm, Locale.US);

                endDateET.setText(dateSDF.format(mCalDate.getTime()));
            }
        };

        RecyclerView recyclerView = findViewById(R.id.courseDetailsRecycler);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getCourseTermID() == termID) {
                filteredCourses.add(course);
            }
        }

        Button save = findViewById(R.id.saveTerm);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termID == -1) {
                    term = new Term(0, termNameET.getText().toString(), startDateET.getText().toString(), endDateET.getText().toString());
                    repository.insert(term);
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);
                } else {
                    term = new Term(0, termNameET.getText().toString(), startDateET.getText().toString(), endDateET.getText().toString());
                    repository.update(term);
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);
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