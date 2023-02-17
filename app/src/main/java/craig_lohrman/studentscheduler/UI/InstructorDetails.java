package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Instructor;

public class InstructorDetails extends AppCompatActivity {

    EditText editInstructorName, editInstructorPhone, editInstructorEmail;
    String iNameString, iPhoneString, iEmailString;
    int instructorID, instructorCourseID;
    Instructor instructor, currentInstructor;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_details);

        editInstructorName = findViewById(R.id.instructorNameET);
        editInstructorPhone = findViewById(R.id.instructorPhoneET);
        editInstructorEmail = findViewById(R.id.instructorEmailET);

        instructorID = getIntent().getIntExtra("instructorID", -1);
        iNameString = getIntent().getStringExtra("instructorName");
        iPhoneString = getIntent().getStringExtra("instructorPhone");
        iEmailString = getIntent().getStringExtra("instructorEmail");
        instructorCourseID = getIntent().getIntExtra("instructorCourseID", -1);

        editInstructorName.setText(iNameString);
        editInstructorPhone.setText(iPhoneString);
        editInstructorEmail.setText(iEmailString);

        repository = new Repository(getApplication());

        Button saveInstructor = findViewById(R.id.saveInstructor);
        saveInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instructorID == -1) {
                    instructor = new Instructor(0, editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString(), instructorCourseID);
                    repository.insert(instructor);
                    Intent intent = new Intent(InstructorDetails.this, InstructorList.class);
                    startActivity(intent);
                } else {
                    instructor = new Instructor(instructorID, editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString(), instructorCourseID);
                    repository.update(instructor);
                    Intent intent = new Intent(InstructorDetails.this, InstructorList.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteInstructor = findViewById(R.id.deleteInstructor);
        deleteInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Instructor instructor : repository.getAllInstructors()) {
                    if (instructor.getInstructorID() == instructorID) {
                        currentInstructor = instructor;
                    }
                }
                if (currentInstructor.getInstructorID() == instructorID) {
                    repository.delete(currentInstructor);
                    Toast.makeText(InstructorDetails.this, currentInstructor.getInstructorName() + " was deleted", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(InstructorDetails.this, InstructorList.class);
                    startActivity(intent);
                }
            }
        });
    }
}