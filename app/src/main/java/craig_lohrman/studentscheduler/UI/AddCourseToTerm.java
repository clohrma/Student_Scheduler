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

    int receivedTermID, currentCourseID, removeCourseTermID;
    String courseNameSelected, currentCourseName, currentCourseStartDate, currentCourseEndDate, currentCourseStatus, currentCourseShareNote, currentCourseInstructorName;
    Spinner courseSpinner;
    Repository repository;
    Course currentCourse, course;
    Intent intent;
    List<Course> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_term);

        intent = getIntent();
        receivedTermID = intent.getIntExtra("termID", -1);
        repository = new Repository(getApplication());

        allCourses = repository.getAllCourses();
        courseSpinner = findViewById(R.id.addCourseToTermSpinner);
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allCourses);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseArrayAdapter);
        String itemCourseName = getCurrentCourseName(receivedTermID);
        int cCourseItem = convertSpinnerToInt(courseSpinner, itemCourseName);
        courseSpinner.setSelection(cCourseItem);

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseNameSelected = courseSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button saveCourseToTerm = findViewById(R.id.saveCourseToTerm);
        saveCourseToTerm.setOnClickListener(v -> {
            for (int i = 0; i < allCourses.size(); i++) {
                currentCourse = allCourses.get(i);
                String courseName = currentCourse.getCourseName();
                if (courseName.equalsIgnoreCase(courseNameSelected)) {
                    currentCourseID = currentCourse.getCourseID();
                    currentCourseName = currentCourse.getCourseName();
                    currentCourseStartDate = currentCourse.getCourseStartDate();
                    currentCourseEndDate = currentCourse.getCourseEndDate();
                    currentCourseStatus = currentCourse.getCourseStatus();
                    currentCourseShareNote = currentCourse.getCourseShareNote();
                    currentCourseInstructorName = currentCourse.getCourseInstructorName();

                    course = new Course(currentCourseID, currentCourseName, currentCourseStartDate, currentCourseEndDate, currentCourseStatus,
                            currentCourseShareNote, currentCourseInstructorName, receivedTermID);
                    repository.update(course);
                    Intent intent = new Intent(AddCourseToTerm.this, TermList.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteCourseFromTerm = findViewById(R.id.deleteCourseFromTerm);
        deleteCourseFromTerm.setOnClickListener(v -> {
            for (int i = 0; i < allCourses.size(); i++) {
                currentCourse = allCourses.get(i);
                String courseName = currentCourse.getCourseName();
                if (courseName.equalsIgnoreCase(courseNameSelected)) {
                    currentCourseID = currentCourse.getCourseID();
                    currentCourseName = currentCourse.getCourseName();
                    currentCourseStartDate = currentCourse.getCourseStartDate();
                    currentCourseEndDate = currentCourse.getCourseEndDate();
                    currentCourseStatus = currentCourse.getCourseStatus();
                    currentCourseShareNote = currentCourse.getCourseShareNote();
                    currentCourseInstructorName = currentCourse.getCourseInstructorName();
                    removeCourseTermID = -1;

                    course = new Course(currentCourseID, currentCourseName, currentCourseStartDate, currentCourseEndDate, currentCourseStatus,
                            currentCourseShareNote, currentCourseInstructorName, removeCourseTermID);
                    repository.update(course);
                    Intent intent = new Intent(AddCourseToTerm.this, TermList.class);
                    startActivity(intent);
                }
            }
        });
    }

    private int convertSpinnerToInt(Spinner spinner, String name) {
        for (int i = 0; i < spinner.getCount(); i++) {
            String itemName = spinner.getItemAtPosition(i).toString();
            if (itemName.equalsIgnoreCase(name)) {
                return (i);
            }
        }
        return -1;
    }

    private String getCurrentCourseName(int termID) {
        allCourses = repository.getAllCourses();
        for (int i = 0; i < allCourses.size(); i++) {
            currentCourse = allCourses.get(i);
            if (currentCourse.getCourseTermID() == termID && termID != -1) {
                return currentCourse.getCourseName();
            }
        }
        return null;
    }
}