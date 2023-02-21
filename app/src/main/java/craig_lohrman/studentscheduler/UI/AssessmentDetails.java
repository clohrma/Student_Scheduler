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

import androidx.annotation.NonNull;
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

    EditText editAssessmentName, editAssessmentStartDate, editAssessmentEndDate;
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

        editAssessmentName.setText(aName);
        editAssessmentStartDate.setText(aStartDate);
        editAssessmentEndDate.setText(aStartDate);
        editAssessmentEndDate.setText(aEndDate);

        repository = new Repository(getApplication());
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

                    finish();
                    //Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                    //startActivity(intent);
                } else {
                    assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), editAssessmentStartDate.getText().toString(),
                            editAssessmentEndDate.getText().toString(), aType, assessmentCourseID);
                    repository.update(assessment);

                    finish();
                    //Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                    //startActivity(intent);
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteAssessment);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == assessmentID) {
                        currentAssessment = assessment;
                    }
                }
                if (currentAssessment.getAssessmentID() == assessmentID) {
                    repository.delete(currentAssessment);
                    Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();

                    finish();
                    //Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                    //startActivity(intent);
                }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.assessmentNotifyStart) {
            String assessmentStartDate = editAssessmentStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date date = null;

            try {
                date = sdf.parse(assessmentStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = date.getTime();
            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", "Start Date " + assessmentStartDate + " is set.");
            PendingIntent senderStart = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderStart);
            return true;
        }

        if (item.getItemId() == R.id.assessmentNotifyEnd) {

            String assessmentEndDate = editAssessmentEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date date = null;

            try {
                date = sdf.parse(assessmentEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = date.getTime();
            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", "End Date " + assessmentEndDate + " is set.");
            PendingIntent senderEnd = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderEnd);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}