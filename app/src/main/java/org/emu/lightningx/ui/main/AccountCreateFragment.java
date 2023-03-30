package org.emu.lightningx.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.emu.lightningx.R;
import org.emu.lightningx.models.ProfessorModel;
import org.emu.lightningx.services.GlobalStateService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountCreateFragment newInstance(String param1, String param2) {
        AccountCreateFragment fragment = new AccountCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account_create, container, false);

        // Grab view items
        EditText uuidEntry = root.findViewById(R.id.uuidEntry);
        EditText nameEntry = root.findViewById(R.id.nameEntry);

        Button confirmButton = root.findViewById(R.id.accountConfirmButton);

        confirmButton.setOnClickListener(args -> {
            // Input checking
            try {
                int id = Integer.parseInt(uuidEntry.getText().toString());
                String name = nameEntry.getText().toString();

                ProfessorModel professor = new ProfessorModel();
                professor.setName(name);
                professor.setUuid(id);

                // Set the selected professor
                GlobalStateService.getInstance().setSelectedProfessor(professor);

                RootViewFragment root_view_fragment = new RootViewFragment();

                FragmentManager manager = getParentFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.fragmentContainerView, root_view_fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();

            } catch (NumberFormatException ex){
                // User entered a bad uuid somehow

                //Hello! it's Luke. I deleted this so that I could better format the UI.
                //Add it back when you need it again.
                //TextView errorText = root.findViewById(R.id.accountCreateErrorText);

                //errorText.setText("Invalid UUID, UUID must be a non-negative integer!");
                //errorText.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
}