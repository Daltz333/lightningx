package org.emu.lightningx.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import org.emu.lightningx.R;
import org.emu.lightningx.services.GlobalStateService;
import org.emu.lightningx.services.StudentRetrieveService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A fragment representing a list of Items.
 */
public class StudentsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    private TextView selectedDate;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentsFragment() {
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StudentsFragment newInstance(int columnCount) {
        StudentsFragment fragment = new StudentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.studentRecyclerAdapter);

        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.currentPageTitle);

            toolbar.setTitle(GlobalStateService.getInstance().getSelectedClass().getName());
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        selectedDate  = view.findViewById(R.id.datePickerCurrentDate);
        ConstraintLayout datePicker = view.findViewById(R.id.datePickerConstraintLayout);

        datePicker.setOnClickListener(this::onDatePickerClick);

        selectedDate.setText(LocalDateTime.now().format(format));

        Context context = view.getContext();

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new StudentsRecyclerViewAdapter(StudentRetrieveService.instance.getStudents()));

        return view;
    }

    @Override
    public void onDestroyView() {
        if (getActivity() != null) {
            // restore toolbar as we are going back to root
            Toolbar toolbar = getActivity().findViewById(R.id.currentPageTitle);
            toolbar.setTitle(GlobalStateService.getInstance().getSelectedProfessor().getName());
        }
        super.onDestroyView();
    }

    public void onDatePickerClick(View view) {
        // build calendar popup view
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View dialogView = new DatePickerPopupFragment().onCreateView(getLayoutInflater(), null, null);

        alert.setCancelable(true);
        alert.setView(dialogView);

        AlertDialog alertDialog = alert.show();
        CalendarView calendar = dialogView.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener((view1, year, month, day) -> {
            GlobalStateService.getInstance().setSelectedDate(month + "/" + day + "/" + year);
            alertDialog.dismiss();
        });

        alertDialog.setOnDismissListener(args -> {
            selectedDate.setText(GlobalStateService.getInstance().getSelectedDate());
        });
    }
}