package com.pavan.firebaseauthloginregistermvvm.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;
import com.pavan.firebaseauthloginregistermvvm.R;
import com.pavan.firebaseauthloginregistermvvm.viewmodel.LoginRegisterViewModel;

import java.time.DayOfWeek;

public class LoginRegisterFragment extends Fragment {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserMutableLiveData().observe(this, (Observer) (firebaseUser) -> {
            if(firebaseUser != null){
                    Navigation.findNavController(getView()).navigate(R.id.action_loginRegisterFragment_to_loggedInFragment);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        boolean attachToRoot;
        View view = inflater.inflate(R.layout.fragment_loginregister, container, false);

        emailEditText = view.findViewById(R.id.fragment_logineregister_email);
        passwordEditText = view.findViewById(R.id.fragment_logineregister_password);
        loginButton = view.findViewById(R.id.fragment_logineregister_login);
        registerButton = view.findViewById(R.id.fragment_logineregister_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.register(email, password);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(email.length() > 0 && password.length() > 0){
                    loginRegisterViewModel.login(email, password);
                }
            }
        });

        return view;
    }
}

