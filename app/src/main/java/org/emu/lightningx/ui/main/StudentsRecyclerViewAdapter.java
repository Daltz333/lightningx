package org.emu.lightningx.ui.main;

import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.emu.lightningx.databinding.FragmentStudentBinding;
import org.emu.lightningx.models.StudentModel;

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
        holder.mImageView.setImageResource(mStudents.get(position).studentProfileUriPath);
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

        public ViewHolder(FragmentStudentBinding binding) {
            super(binding.getRoot());

            mNameView = binding.studentNameText;
            mIdView = binding.studentIdTextbox;
            mImageView = binding.studentImageView;
        }
    }
}