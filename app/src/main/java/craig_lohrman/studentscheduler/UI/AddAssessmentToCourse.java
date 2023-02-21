package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;

public class AddAssessmentToCourse extends AppCompatActivity {

    TextView courseNameTV;
    int receiveCourseID, currentAssessmentID;
    String assessmentNameSelected, currentAssessmentName, currentAssessmentStartDate, currentAssessmentEndDate, currentAssessmentType;
    String courseName;
    Spinner assessmentSpinner;
    Repository repository;
    Assessment currentAssessment, assessment;
    Intent intent;
    List<Assessment> allAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_to_course);

        intent = getIntent();
        receiveCourseID = intent.getIntExtra("courseID", -1);
        courseName = intent.getStringExtra("courseName");

        courseNameTV = findViewById(R.id.addAssessment_CourseName_TV);
        courseNameTV.setText(courseName);

        repository = new Repository(getApplication());
        allAssessments = repository.getAllAssessments();
        assessmentSpinner = findViewById(R.id.addAssessmentToCourse);
        ArrayAdapter<Assessment> assessmentArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allAssessments);
        assessmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentSpinner.setAdapter(assessmentArrayAdapter);
        String itemAssessmentName = getCurrentAssessmentName(receiveCourseID);
        int aAssessmentItem = convertSpinnerToInt(assessmentSpinner, itemAssessmentName);
        assessmentSpinner.setSelection(aAssessmentItem);

        assessmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assessmentNameSelected = assessmentSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button saveAssessmentToTerm = findViewById(R.id.saveAssessmentToCourse);
        saveAssessmentToTerm.setOnClickListener(v -> {
            for (int i = 0; i < allAssessments.size(); i++) {
                currentAssessment = allAssessments.get(i);
                String assessmentName = currentAssessment.getAssessmentName();
                if (assessmentName.equalsIgnoreCase(assessmentNameSelected)) {
                    currentAssessmentID = currentAssessment.getAssessmentID();
                    currentAssessmentName = currentAssessment.getAssessmentName();
                    currentAssessmentStartDate = currentAssessment.getAssessmentStartDate();
                    currentAssessmentEndDate = currentAssessment.getAssessmentEndDate();
                    currentAssessmentType = currentAssessment.getAssessmentType();

                    assessment = new Assessment(currentAssessmentID, currentAssessmentName, currentAssessmentStartDate,
                            currentAssessmentEndDate, currentAssessmentType, receiveCourseID);
                    repository.update(assessment);

                    finish();
                }
            }
        });

        Button deleteAssessmentToTerm = findViewById(R.id.deleteAssessmentToCourse);
        deleteAssessmentToTerm.setOnClickListener(v -> {
            for (int i = 0; i < allAssessments.size(); i++) {
                currentAssessment = allAssessments.get(i);
                String assessmentName = currentAssessment.getAssessmentName();
                if (assessmentName.equalsIgnoreCase(assessmentNameSelected)) {
                    currentAssessmentID = currentAssessment.getAssessmentID();
                    currentAssessmentName = currentAssessment.getAssessmentName();
                    currentAssessmentStartDate = currentAssessment.getAssessmentStartDate();
                    currentAssessmentEndDate = currentAssessment.getAssessmentEndDate();
                    currentAssessmentType = currentAssessment.getAssessmentType();
                    receiveCourseID = -1;

                    assessment = new Assessment(currentAssessmentID, currentAssessmentName, currentAssessmentStartDate,
                            currentAssessmentEndDate, currentAssessmentType, receiveCourseID);
                    repository.update(assessment);

                    finish();
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

    private String getCurrentAssessmentName(int courseID) {
        allAssessments = repository.getAllAssessments();
        for (int i = 0; i < allAssessments.size(); i++) {
            currentAssessment = allAssessments.get(i);
            if (currentAssessment.getAssessmentCourseID() == courseID && courseID != -1) {
                return currentAssessment.getAssessmentName();
            }
        }
        return null;
    }
}