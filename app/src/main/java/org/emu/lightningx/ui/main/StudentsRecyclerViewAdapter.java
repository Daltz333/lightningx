package org.emu.lightningx.ui.main;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.emu.lightningx.R;
import org.emu.lightningx.databinding.FragmentStudentBinding;
import org.emu.lightningx.models.ClassModel;
import org.emu.lightningx.models.StudentModel;
import org.emu.lightningx.services.GlobalStateService;
import org.emu.lightningx.util.GlobalUtil;

import java.util.List;

public class StudentsRecyclerViewAdapter extends RecyclerView.Adapter<StudentsRecyclerViewAdapter.ViewHolder> {

    private final List<StudentModel> mStudents;


    public StudentsRecyclerViewAdapter(List<StudentModel> items) {
        mStudents = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentStudentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mStudent = mStudents.get(position);
        holder.mIdView.setText(mStudents.get(position).studentId);
        holder.mNameView.setText(mStudents.get(position).studentName);

        holder.updateCardColor(holder.mStudent);

        try {
            holder.mImageView.setImageURI(Uri.parse(mStudents.get(position).getStudentProfileUriPath()));
        } catch (Exception ignored) {}
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mIdView;
        public final ImageView mImageView;

        public StudentModel mStudent;
        private CardView card;

        public ViewHolder(FragmentStudentBinding binding) {
            super(binding.getRoot());

            card = binding.studentCard;
            mNameView = binding.studentNameText;
            mIdView = binding.studentIdTextbox;
            mImageView = binding.studentPicture;

            // User clicked on button
            binding.getRoot().setOnClickListener(view1 -> {
                ClassModel currentClass = GlobalStateService.getInstance().getSelectedClass();

                // update card color based on student present
                // transparent can be assumed present
                // behavior may change in the future
                if (currentClass.isStudentPresent(mStudent, GlobalStateService.getInstance().getSelectedDate())) {
                    card.setCardBackgroundColor(view1.getContext().getResources().getColor(R.color.light_gray, view1.getContext().getTheme()));
                    currentClass.markStudentPresent(mStudent, GlobalStateService.getInstance().getSelectedDate(), false);
                } else {
                    card.setCardBackgroundColor(Color.GREEN);
                    currentClass.markStudentPresent(mStudent, GlobalStateService.getInstance().getSelectedDate(), true);
                }
            });
        }

        public void updateCardColor(StudentModel student) {
            // Update the card color of the student that just got pressed
            int color = GlobalStateService.getInstance().
                    getSelectedClass().
                    isStudentPresent(student, GlobalStateService.getInstance()
                            .getSelectedDate()) ? Color.GREEN : card.getContext().getResources().getColor(R.color.light_gray, card.getContext().getTheme());

            card.setCardBackgroundColor(color);
        }
    }
}