package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Course;

public class AddCourseToTerm extends AppCompatActivity {

    String courseNameSelected;
    int receivedTermID;
    Spinner courseSpinner;
    Repository repository;
    Course currentCourse,  course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_term);

        Intent intent = getIntent();
        receivedTermID = intent.getIntExtra("termID", -1);

        List<Course> allCourses = repository.getAllCourses();
        courseSpinner = findViewById(R.id.addCourseToTermSpinner);
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allCourses);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseArrayAdapter);
        if (receivedTermID != -1) {
            for (int i = 0; i < allCourses.size(); i++) {
                int courseTermID = (int) courseSpinner.getItemAtPosition(i);
                if (courseTermID == receivedTermID) {
                    courseSpinner.setSelection(i);
                }
            }
        }

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseNameSelected = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < allCourses.size(); i++) {
                    String courseName = currentCourse.getCourseName();
                    if(courseName.equalsIgnoreCase(courseNameSelected)){
                        course = new Course(currentCourse.getCourseID(), currentCourse.getCourseName(), currentCourse.getCourseStartDate(),
                                currentCourse.getCourseEndDate(), currentCourse.getCourseStatus(), currentCourse.getCourseShareNote(), currentCourse.getCourseInstructorName(),
                                receivedTermID);
                        repository.update(course);
                        Intent intent = new Intent(AddCourseToTerm.this, TermDetails.class);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button saveCourseToTerm = findViewById(R.id.saveCourseToTerm);
        saveCourseToTerm.setOnClickListener(v -> {
            Intent sendIntent = new Intent(AddCourseToTerm.this, TermDetails.class);
            sendIntent.putExtra("courseID", courseNameSelected);
            startActivity(sendIntent);
        });
    }
}