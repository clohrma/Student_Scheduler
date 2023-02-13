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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import craig_lohrman.studentscheduler.entities.Course;
import craig_lohrman.studentscheduler.entities.Instructor;
import craig_lohrman.studentscheduler.entities.Term;

public class AssessmentDetails extends AppCompatActivity {

    EditText editAssessmentCourseID, editAssessmentName, editAssessmentStartDate, editAssessmentEndDate;
    String aName, aStartDate, aEndDate, aType;
    DatePickerDialog.OnDateSetListener startDateDP, endDateDP;
    final Calendar myCalStart = Calendar.getInstance();
    final Calendar myCalEnd = Calendar.getInstance();
    int assessmentID, assessmentCourseID;
    Assessment assessment, currentAssessment;
    Repository repository;
    List<Assessment> allAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

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
        aType = getIntent().getStringExtra("assessmentType");

        editAssessmentCourseID.setText(String.valueOf(assessmentCourseID));
        editAssessmentName.setText(aName);
        editAssessmentStartDate.setText(aStartDate);
        editAssessmentEndDate.setText(aStartDate);
        editAssessmentEndDate.setText(aEndDate);

        Spinner aSpinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<CharSequence> assessmentArrayAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_list, android.R.layout.simple_spinner_item);
        assessmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        aSpinner.setAdapter(assessmentArrayAdapter);
        int cStatusItem = convertSpinnerToInt(aSpinner, aType);
        aSpinner.setSelection(cStatusItem);

        aSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aType = aSpinner.getSelectedItem().toString();
                Toast.makeText(AssessmentDetails.this, aType +" is selected", Toast.LENGTH_LONG).show();
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
                    assessment = new Assessment(0, editAssessmentName.getText().toString(), editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(), aType, Integer.parseInt(editAssessmentCourseID.getText().toString()));
                    repository.insert(assessment);
                    Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                    startActivity(intent);
                } else {
                    assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(), aType, Integer.parseInt(editAssessmentCourseID.getText().toString()));
                    repository.update(assessment);
                    Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteAssessment);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == assessmentID && assessmentID != -1) {
                        currentAssessment = assessment;
                    }
                }
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, assessment.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();

                refreshAssessmentRecycler();
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

    private int convertSpinnerToInt(Spinner spinner, String name) {
        for (int i = 0; i < spinner.getCount(); i++) {
            String instructorName = spinner.getItemAtPosition(i).toString();
            if (instructorName.equalsIgnoreCase(name)) {
                return (i);
            }
        }
        return 0;
    }

    private void updateDateET() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat dateSDF = new SimpleDateFormat(dateFormat, Locale.US);

        editAssessmentStartDate.setText(dateSDF.format(myCalStart.getTime()));
        editAssessmentEndDate.setText(dateSDF.format(myCalEnd.getTime()));
    }

    private void refreshAssessmentRecycler() {
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allAssessments = repository.getAllAssessments();
    }
}