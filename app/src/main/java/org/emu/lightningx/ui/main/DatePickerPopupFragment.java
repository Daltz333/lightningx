package org.emu.lightningx.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import org.emu.lightningx.R;
import org.emu.lightningx.services.GlobalStateService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerPopupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerPopupFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public DatePickerPopupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Title.
     * @return A new instance of fragment DatePickerPopupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePickerPopupFragment newInstance(String param1) {
        DatePickerPopupFragment fragment = new DatePickerPopupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker_popup, container, false);

        return view;
    }
}