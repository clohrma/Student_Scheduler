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
import craig_lohrman.studentscheduler.R.*;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Course;

public class CourseDetails extends AppCompatActivity {

    EditText editCourseName, editCourseStart, editCourseEnd, editShareNote;
    String cName, cStartDate, cEndDate, cShareNote;
    DatePickerDialog.OnDateSetListener startDateDP, endDateDP;
    final Calendar myCalStart = Calendar.getInstance();
    final Calendar myCalEnd = Calendar.getInstance();
    Spinner cStatus;
    int courseID, courseTermID;
    Course course, currentCourse;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_course_details);

        editCourseName = findViewById(id.courseNameET);
        editCourseStart = findViewById(id.courseStartDateET);
        editCourseEnd = findViewById(id.courseEndDateET);
        editShareNote = findViewById(id.courseShareNoteET);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat dateSDF = new SimpleDateFormat(myFormat, Locale.US);
        editCourseStart.setText(dateSDF.format(new Date()));
        editCourseEnd.setText(dateSDF.format(new Date()));

        courseID = getIntent().getIntExtra("courseID", -1);
        cName = getIntent().getStringExtra("courseName");
        cStartDate = getIntent().getStringExtra("courseStartDate");
        cEndDate = getIntent().getStringExtra("courseEndDate");
        cStatus = getIntent().getParcelableExtra("courseStatus");
        cShareNote = getIntent().getStringExtra("courseShareNote");
        courseTermID = getIntent().getIntExtra("courseTermID", -1);

        editCourseName.setText(cName);
        editCourseStart.setText(cStartDate);
        editCourseEnd.setText(cEndDate);
        editShareNote.setText(cShareNote);

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(id.assessmentListRecycler);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment a : repository.getAllAssessments()){
            if(a.getAssessmentCourseID() == courseTermID){
                filteredAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessment(filteredAssessments);

        Spinner spinner = findViewById(id.courseStatusSpinner);
        ArrayAdapter<CharSequence> courseArrayAdapter = ArrayAdapter.createFromResource(this, array.course_status_list, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(courseArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                cStatus.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button save = findViewById(id.saveCourse);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (courseID != -1) {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(),
                            cStatus.toString(), cShareNote, courseTermID);
                    repository.insert(course);
                } else {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(),
                            cStatus.toString(), cShareNote, courseTermID);
                    repository.update(course);
                }
            }
        });

        Button delete = findViewById(id.deleteCourse);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, "Deleted " + currentCourse.getCourseName() + ".", Toast.LENGTH_LONG).show();

            }
        });

        FloatingActionButton fab = findViewById(id.courseDetailsFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return true;
    }

    public boolean onOptionsItemsSelected(MenuItem item){

        String startDateFormat = "MM/dd/yy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(startDateFormat, Locale.US);
        Date date = null;
        Long trigger;
        Intent intent;
        AlarmManager alarmManager;

       if(item.getItemId() == id.courseShareNote) {
           Intent sendIntent = new Intent();
           sendIntent.setAction(Intent.ACTION_SEND);
           sendIntent.putExtra(Intent.EXTRA_TEXT, editShareNote.getText().toString());
           sendIntent.putExtra(Intent.EXTRA_TITLE, "Note Sharing");
           sendIntent.setType("text/plain");
           Intent shareNote = Intent.createChooser(sendIntent, null);
           startActivity(shareNote);
           return true;
       }

        if(item.getItemId() == id.courseNotifyStart) {
            String courseStartDate = editCourseStart.getText().toString();

            try {
                date = dateSDF.parse(courseStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            assert date != null;
            trigger = date.getTime();
            intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", courseStartDate + " is set.");
            PendingIntent senderStart = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderStart);
            return true;
        }

        if(item.getItemId() == id.courseNotifyEnd){
                String courseEndDate = editCourseEnd.getText().toString();

                try {
                    date = dateSDF.parse(courseEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            assert date != null;
            trigger = date.getTime();
                intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", courseEndDate + " is removed.");
                PendingIntent senderEnd = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assessment> allProducts = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(id.assessmentListRecycler);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allProducts);
    }
}