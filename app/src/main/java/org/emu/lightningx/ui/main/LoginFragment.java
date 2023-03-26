package org.emu.lightningx.ui.main;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.emu.lightningx.R;
import org.emu.lightningx.models.ProfessorModel;
import org.emu.lightningx.services.DatabaseService;
import org.emu.lightningx.services.GlobalStateService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Represents the popup window used for retrieving user credentials

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = root.findViewById(R.id.goButton);
        EditText uuid = root.findViewById(R.id.uuidEntry);

        loginButton.setOnClickListener(args -> {
            ProfessorModel professor = null;

            // grab uuid, set id to -1 if no id was entered
            // TODO, update textfield above login box stating to enter a valid UUID
            int realUuid = -1;

            // do some input checking
            try {
                realUuid = Integer.parseInt(uuid.getText().toString());
            } catch (NumberFormatException ex) {
                Log.println(Log.INFO, this.getClass().getSimpleName(), "User entered an invalid UUID!");
            }

            if (DatabaseService.getInstance().doesUserExist(realUuid)) {
                professor = new ProfessorModel();
                userLoggedIn(professor, inflater, root);

            } else {
                // Open the account creation page, by a fragment transaction
                AccountCreateFragment fragment = new AccountCreateFragment();
                FragmentManager manager = getParentFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.fragmentContainerView, fragment);
                transaction.addToBackStack(fragment.getClass().getSimpleName());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();

                return;
            }
        });

        return root;
    }

    @SuppressLint("ResourceType")
    public void userLoggedIn(ProfessorModel professor, LayoutInflater inflater, View root) {
        GlobalStateService.getInstance().setSelectedProfessor(professor);

        View root_view = inflater.inflate(R.layout.fragment_root_view, null, false);

        Activity activity = getActivity();

        if (activity != null) {
            Toolbar pageTitle = root_view.findViewById(R.id.currentPageTitle);
            pageTitle.setTitle(professor.getName() + " - " + professor.getUuid());

            activity.setContentView(root_view);
        } else {
            Log.println(Log.WARN, root.getResources().getString(R.id.applicationName), "Failed to set device root, as activity is null!");
        }
    }

}