package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Assessment;
import craig_lohrman.studentscheduler.entities.Term;

public class AssessmentList extends AppCompatActivity {

    Repository repository;
    Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);


        FloatingActionButton fab = findViewById(R.id.assessmentListFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);

                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> allAssessments = repository.getAllAssessments();
        assessmentAdapter.setAssessment(allAssessments);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assessment> allAssessment = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allAssessment);
    }
}