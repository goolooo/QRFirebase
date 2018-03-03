package com.bird.qrfirebas.qrfirebase.frag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bird.qrfirebas.qrfirebase.MainActivity;
import com.bird.qrfirebas.qrfirebase.R;
import com.bird.qrfirebas.qrfirebase.utility.MainAlert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

//    Explicit
    private String nameString, emailString, passwordString;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Create Toolbar
        createToolbar();
        setHasOptionsMenu(true);

    }   //Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if (item.getItemId() == R.id.iteSave){
           saveController();
           return true;
       }

        return super.onOptionsItemSelected(item);
    }

    private void saveController() {
//        Get Value From EditText
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText emailEditText = getView().findViewById(R.id.edtEmail);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        nameString = nameEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

//        Check space
        if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {
//            Have Space
            MainAlert mainAlert = new MainAlert(getActivity());
            mainAlert.normalDialog("Have Space",
                    "Plase Fill All Every Blank");
        } else {
//            No Space
            uploadToFirebase();
        }



    }

    private void uploadToFirebase() {
        FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Success Register
                            Toast.makeText(getActivity(),"Register Successful",
                                    Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();

                        } else {
//                            Non Success
                            String resultString = task.getException().getMessage();
                            MainAlert mainAlert = new MainAlert(getActivity());
                            mainAlert.normalDialog("Register False",resultString);
                        }
                    }
                });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.th_register));
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.sub_register));
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

}