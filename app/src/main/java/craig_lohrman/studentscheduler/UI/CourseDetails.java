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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Course;

public class CourseDetails extends AppCompatActivity {

    EditText editCourseName, editCourseStart, editCourseEnd, editCourseTermID;
    String name, startDate, endDate;
    int courseID, courseTermID;
    Course course, currentCourse;
    Repository repository;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editCourseName = findViewById(R.id.courseNameET);
        editCourseStart = findViewById(R.id.courseStartDateET);
        editCourseEnd = findViewById(R.id.courseEndDateET);
        editCourseTermID = findViewById(R.id.courseTermIDET);

        courseID = getIntent().getIntExtra("courseID", -1);
        name = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("courseStartDate");
        endDate = getIntent().getStringExtra("courseEndDate");
        courseTermID = getIntent().getIntExtra("courseID", -1);

        editCourseName.setText(name);
        editCourseStart.setText(startDate);
        editCourseEnd.setText(endDate);
        editCourseTermID.setText(courseTermID);

        RecyclerView recyclerView = findViewById(R.id.assessmentListRecycler);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button save = findViewById(R.id.saveCourse);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(courseID != -1){
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseTermID.getId());
                    repository.insert(course);
                } else{
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseTermID.getId());
                    repository.update(course);
                }
            }
        });

        Button delete = findViewById(R.id.deleteCourse);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder delAlert = new AlertDialog.Builder(mContext);
                delAlert.setTitle("Deleting " + course.getCourseName() + "?");
                delAlert.setMessage("Are you sure you want to delete " + course.getCourseName() + "?");
                delAlert.setIcon(android.R.drawable.ic_dialog_alert);
                delAlert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        repository.delete(currentCourse);
                    }
                });
                delAlert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = delAlert.create();
                dialog.show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.courseDetailsFAB);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });
    }
}