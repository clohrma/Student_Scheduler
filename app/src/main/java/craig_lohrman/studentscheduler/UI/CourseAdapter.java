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

import craig_lohrman.studentscheduler.R.*;
import craig_lohrman.studentscheduler.entities.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;
        private final TextView courseStartDate;
        private final TextView courseEndDate;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(id.courseNameListItemTV);
            courseStartDate = itemView.findViewById(id.courseStartDateTV);
            courseEndDate = itemView.findViewById(id.courseEndDateTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStartDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());
                    intent.putExtra("courseInstructorName", current.getCourseInstructorName());
                    intent.putExtra("courseShareNote", current.getCourseShareNote());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseTermID", current.getCourseTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String cName = current.getCourseName();
            String cStartDate = current.getCourseStartDate();
            String cEndDate = current.getCourseEndDate();

            holder.courseName.setText(cName);
            holder.courseStartDate.setText(cStartDate);
            holder.courseEndDate.setText(cEndDate);
        } else {
            holder.courseName.setText(string.no_course_list);
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != mCourses ? mCourses.size() : 0);
    }
}
