package craig_lohrman.studentscheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Instructor;
import craig_lohrman.studentscheduler.entities.Term;

public class CourseDetails extends AppCompatActivity {

    EditText editCourseName, editCourseStartDate, editCourseEndDate, editInstructorName, editShareNote;
    String cNameString, cStartDateString, cEndDateString, cShareNoteString, cStatusString, cInstructorName;
    DatePickerDialog.OnDateSetListener startDateDP, endDateDP;
    final Calendar myCalStart = Calendar.getInstance();
    final Calendar myCalEnd = Calendar.getInstance();
    int courseID, courseTermID;
    Course course, currentCourse;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editCourseName = findViewById(R.id.courseNameET);
        editCourseStartDate = findViewById(R.id.courseStartDateET);
        editCourseEndDate = findViewById(R.id.courseEndDateET);
        editShareNote = findViewById(R.id.courseShareNoteET);

        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        editCourseStartDate.setText(dateSDF.format(new Date()));
        editCourseEndDate.setText(dateSDF.format(new Date()));

        courseID = getIntent().getIntExtra("courseID", -1);
        cNameString = getIntent().getStringExtra("courseName");
        cStartDateString = getIntent().getStringExtra("courseStartDate");
        cEndDateString = getIntent().getStringExtra("courseEndDate");
        cStatusString = getIntent().getStringExtra("courseStatus");
        cShareNoteString = getIntent().getStringExtra("courseShareNote");
        cInstructorName = getIntent().getStringExtra("courseInstructorName");
        courseTermID = getIntent().getIntExtra("courseTermID", -1);

        editCourseName.setText(cNameString);
        editCourseStartDate.setText(cStartDateString);
        editCourseEndDate.setText(cEndDateString);
        editShareNote.setText(cShareNoteString);


        RecyclerView recyclerView = findViewById(R.id.courseDetailsRecycler);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getAssessmentCourseID() == courseTermID) {
                filteredAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessment(filteredAssessments);

        Spinner cInstructorSpinner = findViewById(R.id.courseInstructorNameSpinner);
        ArrayAdapter<Instructor> cInstructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, repository.getAllInstructors());
        cInstructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cInstructorSpinner.setAdapter(cInstructorAdapter);

        cInstructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < cInstructorSpinner.getCount(); i++){
                    if(cInstructorSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(cInstructorName)){
                        cInstructorSpinner.getItemAtPosition(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence> courseArrayAdapter = ArrayAdapter.createFromResource(this, R.array.course_status_list, android.R.layout.simple_spinner_item);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(courseArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saveCourse = findViewById(R.id.saveCourse);
        saveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseID != -1) {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(),
                            cStatusString, cShareNoteString, cInstructorName, courseTermID);
                    repository.insert(course);
                    Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                    startActivity(intent);
                } else {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(),
                            cStatusString, cShareNoteString, cInstructorName, courseTermID);
                    repository.update(course);
                    Intent intent = new Intent(CourseDetails.this, TermDetails.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteCourse = findViewById(R.id.deleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, "Deleted " + currentCourse.getCourseName() + ".", Toast.LENGTH_LONG).show();
            }
        });

        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseStartDate.getText().toString();
                if (info.equals("")) {
                    info = "01/01/2023";
                }
                try {
                    myCalStart.setTime(dateSDF.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDateDP, myCalStart.get(Calendar.YEAR),
                        myCalStart.get(Calendar.MONTH), myCalStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalStart.set(Calendar.YEAR, year);
                myCalStart.set(Calendar.MONTH, month);
                myCalStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateSTR = "MM/dd/yyyy";
                SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

                refreshStartDate();
            }
        };

        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseEndDate.getText().toString();
                if (info.equals("")) {
                    info = "01/01/2023";
                }
                try {
                    myCalEnd.setTime(dateSDF.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDateDP, myCalEnd.get(Calendar.YEAR),
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

    private void refreshStartDate() {
        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        editCourseStartDate.setText(dateSDF.format(myCalStart.getTime()));
    }

    private void refreshEndDate() {
        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        editCourseEndDate.setText(dateSDF.format(myCalEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String dateSTR = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if(item.getItemId() == R.id.courseShareNote) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editShareNote.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Note Sharing");
            sendIntent.setType("text/plain");
            Intent shareNote = Intent.createChooser(sendIntent, null);
            startActivity(shareNote);
            return true;
        }

        if(item.getItemId() == R.id.courseNotifyStart) {
            String courseStartDate = editCourseStartDate.getText().toString();
            Date date = null;

            try {
                date = dateSDF.parse(courseStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = date.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", courseStartDate + " is set.");
            PendingIntent senderStart = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderStart);
            return true;
        }

        if(item.getItemId() == R.id.courseNotifyEnd) {
            String courseEndDate = editCourseEndDate.getText().toString();
            Date date = null;

            try {
                date = dateSDF.parse(courseEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = date.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", courseEndDate + " is removed.");
            PendingIntent senderEnd = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderEnd);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assessment> allAssessment = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.courseDetailsRecycler);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allAssessment);

        List<Assessment> filteredAssessment = new ArrayList<>();
        for (Assessment assessment : allAssessment) {
            if (assessment.getAssessmentCourseID() == courseID) {
                filteredAssessment.add(assessment);
            }
        }
        assessmentAdapter.setAssessment(filteredAssessment);
    }
}