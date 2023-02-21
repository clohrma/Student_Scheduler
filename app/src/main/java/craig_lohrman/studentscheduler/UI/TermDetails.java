package craig_lohrman.studentscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Term;

public class TermDetails extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener startDateDP, endDateDP;
    final Calendar myCalStart = Calendar.getInstance();
    final Calendar myCalEnd = Calendar.getInstance();
    EditText editTermName, editTermStartDate, editTermEndDate, send_termID;
    String termNameString, startDateString, endDateString;
    int termID, numCourses;
    Term term, currentTerm;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTermName = findViewById(R.id.termNameET);
        editTermStartDate = findViewById(R.id.termStartDateET);
        editTermEndDate = findViewById(R.id.termEndDateET);

        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        editTermStartDate.setText(dateSDF.format(new Date()));
        editTermEndDate.setText(dateSDF.format(new Date()));

        termID = getIntent().getIntExtra("termID", -1);
        termNameString = getIntent().getStringExtra("termName");
        startDateString = getIntent().getStringExtra("termStartDate");
        endDateString = getIntent().getStringExtra("termEndDate");

        editTermName.setText(termNameString);
        editTermStartDate.setText(startDateString);
        editTermEndDate.setText(endDateString);

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.termDetailsRecycler);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getCourseTermID() == termID) {
                filteredCourses.add(course);
            }
        }
        courseAdapter.setCourses(filteredCourses);

        Button save = findViewById(R.id.saveTerm);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termID == -1) {
                    term = new Term(0, editTermName.getText().toString(), editTermStartDate.getText().toString(), editTermEndDate.getText().toString());
                    repository.insert(term);
                    finish();
                    //Intent intent = new Intent(TermDetails.this, TermList.class);
                    //startActivity(intent);
                } else {
                    term = new Term(termID, editTermName.getText().toString(), editTermStartDate.getText().toString(), editTermEndDate.getText().toString());
                    repository.update(term);

                    finish();
                    //Intent intent = new Intent(TermDetails.this, TermList.class);
                    //startActivity(intent);
                }
            }
        });

        Button delete = findViewById(R.id.deleteTerm);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Term term : repository.getAllTerms()) {
                    if (term.getTermID() == termID && termID != -1) {
                        currentTerm = term;
                    }
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    int currentCourseTermID = course.getCourseTermID();
                    int currentTermID = currentTerm.getTermID();

                    if (currentCourseTermID == currentTermID) {
                        numCourses++;
                    }
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, "Deleted " + currentTerm.getTermName() + ".", Toast.LENGTH_LONG).show();

                    finish();
                } else {
                    Toast.makeText(TermDetails.this, "Cannot delete " + currentTerm.getTermName() + " with Course(s) assigned to it.", Toast.LENGTH_LONG).show();
                }
            }
        });

        editTermStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editTermStartDate.getText().toString();
                if (info.equals("")) {
                    info = "01/01/2023";
                }
                try {
                    myCalStart.setTime(dateSDF.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDateDP, myCalStart.get(Calendar.YEAR),
                        myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalStart.set(Calendar.YEAR, year);
                myCalStart.set(Calendar.MONTH, month);
                myCalStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                refreshStartDate();
            }
        };

        editTermEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editTermEndDate.getText().toString();
                if (info.equals("")) {
                    info = "01/01/2023";
                }
                try {
                    myCalEnd.setTime(dateSDF.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDateDP, myCalEnd.get(Calendar.YEAR),
                        myCalEnd.get(Calendar.MONTH), myCalEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalEnd.set(Calendar.YEAR, year);
                myCalEnd.set(Calendar.MONTH, month);
                myCalEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                refreshEndDate();
            }
        };
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.termAddCourse) {
            for (Term term : repository.getAllTerms()) {
                if (term.getTermID() == termID) {
                    Intent intent = new Intent(TermDetails.this, AddCourseToTerm.class);
                    intent.putExtra("termID", termID);
                    intent.putExtra("termName", termNameString);
                    startActivity(intent);
                    return true;
                }
            }
        }
        return false;
    }

    private void refreshStartDate() {
        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        editTermStartDate.setText(dateSDF.format(myCalStart.getTime()));
    }

    private void refreshEndDate() {
        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        editTermEndDate.setText(dateSDF.format(myCalEnd.getTime()));
    }

    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.termDetailsRecycler);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getCourseTermID() == termID) {
                filteredCourses.add(course);
            }
        }
        courseAdapter.setCourses(filteredCourses);
    }
}