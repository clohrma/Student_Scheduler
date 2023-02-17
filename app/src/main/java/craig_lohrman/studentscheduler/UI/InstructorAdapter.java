package craig_lohrman.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import craig_lohrman.studentscheduler.R;
import craig_lohrman.studentscheduler.entities.Instructor;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {

    class InstructorViewHolder extends RecyclerView.ViewHolder {
        private final TextView instructorName;
        private final TextView instructorPhone;
        private final TextView instructorEmail;

        private InstructorViewHolder(View itemView) {
            super(itemView);

            instructorName = itemView.findViewById(R.id.instructorNameTV);
            instructorPhone = itemView.findViewById(R.id.instructorPhoneTV);
            instructorEmail = itemView.findViewById(R.id.instructorEmailTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Instructor current = mInstructor.get(position);
                    Intent intent = new Intent(context, InstructorDetails.class);
                    intent.putExtra("instructorID", current.getInstructorID());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("instructorCourseID", current.getInstructorCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Instructor> mInstructor;
    private final Context context;
    private final LayoutInflater mInflater;

    public InstructorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.instructor_list_item, parent, false);
        return new InstructorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {
        if (mInstructor != null) {
            Instructor current = mInstructor.get(position);
            String iName = current.getInstructorName();
            String iPhone = current.getInstructorPhone();
            String iEmail = current.getInstructorEmail();

            holder.instructorName.setText(iName);
            holder.instructorPhone.setText(iPhone);
            holder.instructorEmail.setText(iEmail);
        } else {
            holder.instructorName.setText(R.string.no_instructor_listed);
        }
    }

    public void setInstructors(List<Instructor> instructors) {
        mInstructor = instructors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != mInstructor ? mInstructor.size() : 0);
    }
}
