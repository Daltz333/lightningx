package org.emu.lightningx.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.emu.lightningx.R;
import org.emu.lightningx.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class ClassFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);
        View listView = view.findViewById(R.id.studentRecyclerAdapter);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // TODO, we should replace this adapter list of items with a list of CLASSES
            // and bind the appropriate UI content
            recyclerView.setAdapter(new ClassRecyclerViewAdapter(PlaceholderContent.ITEMS, context));
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
    public void onClassCreationButtonPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("New Class");
        alert.setMessage("Welcome to the class creation screen. This screen is still a WIP");
        alert.setCancelable(true);

        alert.create();
        alert.show();
    }
}