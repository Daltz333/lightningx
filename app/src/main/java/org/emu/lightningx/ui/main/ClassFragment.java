package org.emu.lightningx.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.emu.lightningx.R;
import org.emu.lightningx.models.ClassModel;
import org.emu.lightningx.placeholder.PlaceholderContent;
import org.emu.lightningx.services.GlobalStateService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * A fragment representing a list of Items.
 */
public class ClassFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ClassFragment newInstance(int columnCount) {
        ClassFragment fragment = new ClassFragment();
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);
        View listView = view.findViewById(R.id.studentRecyclerAdapter);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            recyclerView = (RecyclerView) listView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // TODO, we should replace this adapter list of items with a list of CLASSES
            // and bind the appropriate UI content
            recyclerView.setAdapter(new ClassRecyclerViewAdapter(GlobalStateService.getInstance()
                    .getSelectedProfessor().getClasses(), context));
        }

        FloatingActionButton fab = view.findViewById(R.id.createClassFab);
        fab.setOnClickListener(view1 -> onClassCreationButtonPressed());

        return view;
    }

    /**
     * TODO This function should load our own custom AlertDialog
     *
     * The AlertDialog should contain some inputs that allow the user to create a new class
     * Class name should suffice.
     */
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    public void onClassCreationButtonPressed() {
        // generate the alert UI
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Enter Class Name:");

        EditText editTextClassName = new EditText(getContext());
        LinearLayout alertLayout = new LinearLayout(getContext());

        editTextClassName.setHint("Biology 101");

        alertLayout.setPadding(30, 30, 30, 30);
        alertLayout.setOrientation(LinearLayout.VERTICAL);
        alertLayout.addView(editTextClassName);

        alert.setView(alertLayout);
        alert.setCancelable(true);

        alert.setPositiveButton("Create", (dialog, whichButton) -> {
            // Create a class and update the navigation
            ClassModel newClass = new ClassModel(editTextClassName.getText().toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            newClass.setClassCreationDate(LocalDateTime.now().format(formatter));

            GlobalStateService.getInstance()
                    .getSelectedProfessor()
                    .addClass(newClass);

            // forcibly update navigation due to a weird bug
            // that causes the first item added to have a long delay
            if (recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            dialog.dismiss();
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            dialog.cancel();
        });

        alert.create();
        alert.show();
    }
}