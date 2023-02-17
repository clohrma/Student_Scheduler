package craig_lohrman.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import craig_lohrman.studentscheduler.Database.Repository;
import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Instructor;

public class InstructorList extends AppCompatActivity {

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);

        FloatingActionButton fab = findViewById(R.id.instructorListFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorList.this, InstructorDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.instructorListRecyclerView);
        repository = new Repository(getApplication());
        final InstructorAdapter instructorAdapter = new InstructorAdapter(this);
        recyclerView.setAdapter(instructorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Instructor> allInstructors = repository.getAllInstructors();
        instructorAdapter.setInstructors(allInstructors);
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Instructor> allInstructors = repository.getAllInstructors();
        RecyclerView recyclerView = findViewById(R.id.instructorListRecyclerView);
        final InstructorAdapter instructorAdapter = new InstructorAdapter(this);
        recyclerView.setAdapter(instructorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructorAdapter.setInstructors(allInstructors);
    }
}