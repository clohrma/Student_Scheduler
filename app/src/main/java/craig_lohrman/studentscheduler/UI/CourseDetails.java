package craig_lohrman.studentscheduler.UI;

import android.app.Activity;
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

import androidx.annotation.NonNull;
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
import java.util.Objects;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Instructor;
import craig_lohrman.studentscheduler.entities.Term;

public class CourseDetails extends AppCompatActivity {

    String dateFormatted = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
    EditText editCourseName, editCourseStartDate, editCourseEndDate, editShareNote;
    String cNameString, cStartDateString, cEndDateString, cShareNoteString, cStatusString, cInstructorName;
    String cStatusStringSelected, instructorNameSelected;
    DatePickerDialog.OnDateSetListener startDateDP, endDateDP;
    final Calendar myCalStart = Calendar.getInstance();
    final Calendar myCalEnd = Calendar.getInstance();
    int courseID, courseTermID, numAssessments;
    Course course, currentCourse;
    Repository repository;
    Spinner cInstructorSpinner, spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editCourseName = findViewById(R.id.courseNameET);
        editCourseStartDate = findViewById(R.id.courseStartDateET);
        editCourseEndDate = findViewById(R.id.courseEndDateET);
        editShareNote = findViewById(R.id.courseShareNoteET);

        editCourseStartDate.setText(dateFormatted);
        editCourseEndDate.setText(dateFormatted);

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

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courseDetailsRecycler);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getAssessmentCourseID() == courseID) {
                filteredAssessments.add(assessment);
            }
        }
        assessmentAdapter.setAssessment(filteredAssessments);

        List<Instructor> allInstructors = repository.getAllInstructors();
        cInstructorSpinner = findViewById(R.id.courseInstructorNameSpinner);
        ArrayAdapter<Instructor> cInstructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allInstructors);
        cInstructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cInstructorSpinner.setAdapter(cInstructorAdapter);
        if (courseID != -1) {
            for (int i = 0; i < allInstructors.size(); i++) {
                String instructorName = cInstructorSpinner.getItemAtPosition(i).toString();
                if (instructorName.equalsIgnoreCase(cInstructorName)) {
                    cInstructorSpinner.setSelection(i);
                }
            }
        }

        cInstructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instructorNameSelected = cInstructorSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence> courseArrayAdapter = ArrayAdapter.createFromResource(this, R.array.course_status_list, android.R.layout.simple_spinner_item);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(courseArrayAdapter);
        int cStatusItem = convertSpinnerToInt(spinner, cStatusString);
        spinner.setSelection(cStatusItem);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cStatusStringSelected = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saveCourse = findViewById(R.id.saveCourse);
        saveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseID == -1) {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStartDate.getText().toString(),
                            editCourseEndDate.getText().toString(), cStatusStringSelected, editShareNote.getText().toString(),
                            instructorNameSelected, courseTermID);

                    repository.insert(course);
                    finish();
                    //Intent intent = new Intent(CourseDetails.this, CourseList.class);
                    //startActivity(intent);
                } else {
                    course = new Course(courseID, editCourseName.getText().toString(), editCourseStartDate.getText().toString(),
                            editCourseEndDate.getText().toString(), cStatusStringSelected, editShareNote.getText().toString(),
                            instructorNameSelected, courseTermID);

                    repository.update(course);
                    finish();
                    //Intent intent = new Intent(CourseDetails.this, CourseList.class);
                    //startActivity(intent);
                }
            }
        });

        Button deleteCourse = findViewById(R.id.deleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == courseID) {
                        currentCourse = course;
                    }
                }

                numAssessments = 0;
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentCourseID() == courseID) {
                        ++numAssessments;
                    }
                }

                if (numAssessments == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(CourseDetails.this, "Deleted " + currentCourse.getCourseName() + ".", Toast.LENGTH_LONG).show();

                    finish();
                    //Intent intent = new Intent(CourseDetails.this, CourseList.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(CourseDetails.this, "Cannot delete " + currentCourse.getCourseName() + " with Assessment(s) assigned to it.", Toast.LENGTH_LONG).show();
                }
            }
        });

        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateSTR = "MM/dd/yyyy";
                SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

                String info = editCourseStartDate.getText().toString();
                if (info.equals("")) {
                    info = dateFormatted;
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

                refreshStartDate();
            }
        };

        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateSTR = "MM/dd/yyyy";
                SimpleDateFormat dateSDF = new SimpleDateFormat(dateSTR, Locale.US);

                String info = editCourseEndDate.getText().toString();
                if (info.equals("")) {
                    info = dateFormatted;
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

    private int convertSpinnerToInt(Spinner spinner, String name) {
        for (int i = 0; i < spinner.getCount(); i++) {
            String instructorName = spinner.getItemAtPosition(i).toString();
            if (instructorName.equalsIgnoreCase(name)) {
                return (i);
            }
        }
        return 0;
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.courseAddAssessment) {
            for (Course course : repository.getAllCourses()) {
                if (course.getCourseID() == courseID) {
                    Intent intent = new Intent(CourseDetails.this, AddAssessmentToCourse.class);
                    intent.putExtra("courseID", courseID);
                    intent.putExtra("courseName", cNameString);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        }

        if (item.getItemId() == R.id.courseShareNote) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editShareNote.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Note Sharing");
            sendIntent.setType("text/plain");
            Intent shareNote = Intent.createChooser(sendIntent, null);
            startActivity(shareNote);
            return true;
        }

        if (item.getItemId() == R.id.courseNotifyStart) {
            String courseStartDate = editCourseStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date date = null;

            try {
                date = sdf.parse(courseStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = date.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", "Start Date " + courseStartDate + " is set.");
            PendingIntent senderStart = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderStart);
            return true;
        }

        if (item.getItemId() == R.id.courseNotifyEnd) {

            String courseEndDate = editCourseEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date date = null;

            try {
                date = sdf.parse(courseEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = date.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", "End Date " + courseEndDate + " is set.");
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

        RecyclerView recyclerView = findViewById(R.id.courseDetailsRecycler);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getAssessmentCourseID() == courseID) {
                filteredAssessments.add(assessment);
            }
        }
        assessmentAdapter.setAssessment(filteredAssessments);
    }
}