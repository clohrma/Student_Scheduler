package craig_lohrman.studentscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Instructor;

public class AssessmentDetails extends AppCompatActivity {

    EditText editAssessmentCourseID, editAssessmentName, editAssessmentStartDate, editAssessmentEndDate;
    String aName, aStartDate, aEndDate, aType;
    DatePickerDialog.OnDateSetListener startDateDP, endDateDP;
    final Calendar myCalStart = Calendar.getInstance();
    final Calendar myCalEnd = Calendar.getInstance();
    int assessmentID, assessmentCourseID, aTypeINT;
    String[] type = {"Performance", "Objective"};
    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        editAssessmentCourseID = findViewById(R.id.assessmentCourseIdET);
        editAssessmentName = findViewById(R.id.assessmentNameET);
        editAssessmentStartDate = findViewById(R.id.assessmentStartDateET);
        editAssessmentEndDate = findViewById(R.id.assessmentEndDateET);

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateFormat, Locale.US);

        editAssessmentStartDate.setText(dateSDF.format(new Date()));
        editAssessmentEndDate.setText(dateSDF.format(new Date()));

        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        aName = getIntent().getStringExtra("assessmentName");
        aStartDate = getIntent().getStringExtra("assessmentStartDate");
        aEndDate = getIntent().getStringExtra("assessmentEndDate");
        assessmentCourseID = getIntent().getIntExtra("assessmentCourseID", -1);
        aTypeINT = getIntent().getIntExtra("assessmentType", -1);
        assessmentCourseID = getIntent().getIntExtra("editAssessmentCourseID", -1);

        editAssessmentCourseID.setText(assessmentCourseID);
        editAssessmentName.setText(aName);
        editAssessmentStartDate.setText(aStartDate);
        editAssessmentEndDate.setText(aStartDate);
        editAssessmentEndDate.setText(aEndDate);

        Spinner aSpinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter assessmentArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        assessmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        aSpinner.setAdapter(assessmentArrayAdapter);
        aSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                aType = aSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saveButton = findViewById(R.id.saveAssessment);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assessmentID == -1) {
                    assessment = new Assessment(0, editAssessmentName.getText().toString(), editAssessmentStartDate.getText().toString(),
                            editAssessmentEndDate.getText().toString(), aType, assessmentCourseID);
                    repository.insert(assessment);
                    Toast.makeText(AssessmentDetails.this, aName + " was added to Assessments", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AssessmentDetails.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), editAssessmentStartDate.getText().toString(),
                            editAssessmentEndDate.getText().toString(), aType, assessmentCourseID);
                    repository.update(assessment);
                    Toast.makeText(AssessmentDetails.this, aName + " was updated in Assessments", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AssessmentDetails.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteAssessment);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.delete(assessment);
                Toast.makeText(AssessmentDetails.this, assessment.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();
            }
        });

        editAssessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateSTR = editAssessmentStartDate.getText().toString();
                try {
                    myCalStart.setTime(Objects.requireNonNull(dateSDF.parse(dateSTR)));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDateDP, myCalStart.get(Calendar.YEAR), myCalStart.get(Calendar.MONTH),
                        myCalStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalStart.set(Calendar.YEAR, year);
                myCalStart.set(Calendar.MONTH, month);
                myCalStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateET();
            }
        };


        editAssessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateSTR = editAssessmentEndDate.getText().toString();
                try {
                    myCalEnd.setTime(Objects.requireNonNull(dateSDF.parse(dateSTR)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDateDP, myCalEnd.get(Calendar.YEAR), myCalEnd.get(Calendar.MONTH),
                        myCalEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalEnd.set(Calendar.YEAR, year);
                myCalEnd.set(Calendar.MONTH, month);
                myCalEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateET();
            }
        };
    }

    private void updateDateET() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateFormat, Locale.US);

        editAssessmentStartDate.setText(dateSDF.format(myCalStart.getTime()));
        editAssessmentEndDate.setText(dateSDF.format(myCalEnd.getTime()));
    }
}