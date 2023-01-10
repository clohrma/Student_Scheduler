package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Course;

public class CourseDetails extends AppCompatActivity {

    EditText editCourseName, editCourseStart, editCourseEnd;
    String name, startDate, endDate;
    int courseID, courseTermID;
    Course course, currentCourse;
    RadioGroup radioGroup;
    Repository repository;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editCourseName = findViewById(R.id.courseNameET);
        editCourseStart = findViewById(R.id.courseStartDateET);
        editCourseEnd = findViewById(R.id.courseEndDateET);
        radioGroup = findViewById(R.id.courseStatusRBGroup);

        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
            }
        });

        courseID = getIntent().getIntExtra("courseID", -1);
        name = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("courseStartDate");
        endDate = getIntent().getStringExtra("courseEndDate");
        courseTermID = getIntent().getIntExtra("courseID", -1);

        editCourseName.setText(name);
        editCourseStart.setText(startDate);
        editCourseEnd.setText(endDate);

        RecyclerView recyclerView = findViewById(R.id.assessmentListRecycler);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button save = findViewById(R.id.saveCourse);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int statusID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = radioGroup.findViewById(statusID);

                if (courseID != -1) {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), radioButton.getText().toString(), courseTermID);
                    repository.insert(course);
                } else {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), radioButton.getText().toString(), courseTermID);
                    repository.update(course);
                }
            }
        });

        Button delete = findViewById(R.id.deleteCourse);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, "Deleting " + currentCourse.getCourseName() + ".", Toast.LENGTH_LONG).show();

            }
        });

        FloatingActionButton fab = findViewById(R.id.courseDetailsFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });
    }
}