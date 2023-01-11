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
import craig_lohrman.studentscheduler.entities.Assessment;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentStartDate;
        private final TextView assessmentEndDate;
        private final TextView assessmentType;
        private final TextView assessmentCourseID;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessmentNameET);
            assessmentStartDate = itemView.findViewById(R.id.assessmentStartDateET);
            assessmentEndDate = itemView.findViewById(R.id.assessmentEndDateET);
            assessmentType = itemView.findViewById(R.id.assessmentTypeSpinner);
            assessmentCourseID = itemView.findViewById(R.id.assessmentCourseIDET);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment currentAssessment = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                    intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
                    intent.putExtra("assessmentStartDate", currentAssessment.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", currentAssessment.getAssessmentEndDate());
                    intent.putExtra("assessmentType", currentAssessment.getAssessmentType());
                    intent.putExtra("assessmentCourseID", currentAssessment.getAssessmentCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessment != null) {
            Assessment currentAssessment = mAssessment.get(position);
            String name = currentAssessment.getAssessmentName();
            String startDate = currentAssessment.getAssessmentStartDate();
            String endDate = currentAssessment.getAssessmentEndDate();
            String aType = currentAssessment.getAssessmentType();
            int courseID = currentAssessment.getAssessmentCourseID();

            holder.assessmentName.setText(name);
            holder.assessmentStartDate.setText(startDate);
            holder.assessmentEndDate.setText(endDate);
            holder.assessmentType.setText(aType);
            holder.assessmentCourseID.setText(courseID);
        }
        else {
            holder.assessmentName.setText(R.string.no_assessment_listed);
        }
    }

    public void setAssessment(List<Assessment> assessment) {
        mAssessment = assessment;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != mAssessment ? mAssessment.size() : 0);
    }
}