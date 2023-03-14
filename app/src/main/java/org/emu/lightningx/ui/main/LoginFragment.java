package org.emu.lightningx.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

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

            try {
                realUuid = Integer.parseInt(uuid.getText().toString());
            } catch (NumberFormatException ex) {
                Log.println(Log.INFO, this.getClass().getSimpleName(), "User entered an invalid UUID!");
            }

            if (DatabaseService.getInstance().doesUserExist(realUuid)) {
                professor = new ProfessorModel();
                userLoggedIn(professor, inflater, root);
            } else {
                View popupView = inflater.inflate(R.layout.fragment_account_create, container, false);
                PopupWindow window = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                window.showAtLocation(root, Gravity.CENTER, 0, 0);

                window.setOnDismissListener(() -> {
                    EditText newUuidTextEntry = window.getContentView().findViewById(R.id.accountCreationUuid);
                    EditText newNameTextEntry = window.getContentView().findViewById(R.id.accountCreationName);

                    int newUuid = -1;
                    String newName = "";
                    try {
                        newUuid = Integer.parseInt(newUuidTextEntry.getText().toString());
                        newName = newNameTextEntry.getText().toString();

                    } catch (NumberFormatException ex) {
                        Log.println(Log.INFO, getClass().getSimpleName(), "Failed to parse new account creation UUID");
                        return;
                    }

                    ProfessorModel newProfessor = new ProfessorModel();
                    newProfessor.setUuid(newUuid);
                    newProfessor.setName(newName);

                    userLoggedIn(newProfessor, inflater, root);
                });

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