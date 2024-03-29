package org.emu.lightningx.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.emu.lightningx.R;
import org.emu.lightningx.models.StudentModel;
import org.emu.lightningx.services.GlobalStateService;
import org.emu.lightningx.util.Constants;
import org.emu.lightningx.util.GlobalUtil;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * A fragment representing a list of Items.
 */
public class StudentsFragment extends Fragment {
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {

                    selectedImage = result.getData().getData();
                }
            }
    );

    private Uri selectedImage;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    private TextView selectedDate;

    private RecyclerView recyclerView;

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
        recyclerView = view.findViewById(R.id.studentRecyclerAdapter);

        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.currentPageTitle);

            toolbar.setTitle(GlobalStateService.getInstance().getSelectedClass().getName());
        }

        // Set date picker logic
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        selectedDate  = view.findViewById(R.id.datePickerCurrentDate);
        ConstraintLayout datePicker = view.findViewById(R.id.datePickerConstraintLayout);

        datePicker.setOnClickListener(this::onDatePickerClick);

        // Verify if we've previously selected a date
        // If we haven't, then default to current local datetime
        if (GlobalStateService.getInstance().getSelectedDate().isEmpty()) {
            selectedDate.setText(GlobalUtil.getCurrentDateFormatted());
            GlobalStateService.getInstance().setSelectedDate(GlobalUtil.getCurrentDateFormatted());
        } else {
            selectedDate.setText(GlobalStateService.getInstance().getSelectedDate());
        }

        Context context = view.getContext();

        // Set student create FAB logic
        FloatingActionButton fab = view.findViewById(R.id.createStudentFab);
        fab.setOnClickListener(this::onStudentFabClicked);

        // Set layout and fill adapter
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new StudentsRecyclerViewAdapter(GlobalStateService.getInstance().getSelectedClass().getStudents()));

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

    public void onStudentFabClicked(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View dialogView = new StudentCreatePopupFragment().onCreateView(getLayoutInflater(), null, null);

        if (dialogView != null) {
            Button imagePicker = dialogView.findViewById(R.id.studentCreateImagePicker);
            imagePicker.setOnClickListener(this::onImagePickerClick);

            Log.println(Log.WARN, Constants.kAppName, "Dialog View for Student Create Popup is null!!!");
        }

        alert.setTitle("Add Student");
        alert.setView(dialogView);

        alert.setCancelable(true);

        // Add buttons to bottom of popup
        alert.setPositiveButton("Add", ((dialogInterface, i) -> createStudent(dialogView)));
        alert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        alert.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createStudent(View alertView) {
        String studentEntry = ((TextView)alertView.findViewById(R.id.studentNameEntry)).getText().toString();
        String studentUuid = ((TextView)alertView.findViewById(R.id.studentUuidEntry)).getText().toString();

        StudentModel student = new StudentModel(studentEntry, studentUuid);

        if (selectedImage != null) {
            student.setStudentProfileUriPath(selectedImage.toString());
        }

        GlobalStateService.getInstance()
                .getSelectedClass()
                .addStudent(student);

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    public void onImagePickerClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onDatePickerClick(View view) {
        // build calendar popup view
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View dialogView = new DatePickerPopupFragment().onCreateView(getLayoutInflater(), null, null);

        alert.setCancelable(true);
        alert.setView(dialogView);

        AlertDialog alertDialog = alert.show();
        CalendarView calendar = dialogView.findViewById(R.id.calendarView);

        try {
            // Attempt to parse the string as a literal
            String[] date = GlobalStateService.getInstance().getSelectedDate().split("/");

            // Bring into a calendar object to parse into millis since that's
            // what the android CalendarView object expects
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
            cal.set(Calendar.MONTH, Integer.parseInt(date[0]) - 1); // subtract once since calendar expected index at 0
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[1]));

            // Update the calendar selection for intuitive UX
            calendar.setDate(cal.getTimeInMillis(), false, true);
        } catch (Exception ex) {
            Log.println(Log.ERROR, Constants.kAppName, "Failed to parse selected date with stack:\n" + ex);
        }

        calendar.setOnDateChangeListener((view1, year, month, day) -> {
            GlobalStateService.getInstance().setSelectedDate((month + 1) + "/" + day + "/" + year); // add one for UI
            recyclerView.getAdapter().notifyDataSetChanged();
            alertDialog.dismiss();
        });

        alertDialog.setOnDismissListener(args -> {
            selectedDate.setText(GlobalStateService.getInstance().getSelectedDate());
        });
    }
}