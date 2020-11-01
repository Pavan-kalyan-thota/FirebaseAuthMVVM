package com.pavan.firebaseauthloginregistermvvm.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;
import com.pavan.firebaseauthloginregistermvvm.R;
import com.pavan.firebaseauthloginregistermvvm.viewmodel.LoggedInViewModel;

public class LoggedInFragment extends Fragment {
    private TextView loggedInUserTextView;
    private Button logOutButton;

    private LoggedInViewModel loggedInViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loggedInViewModel = ViewModelProviders.of(this).get(LoggedInViewModel.class);
        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                loggedInUserTextView.setText("Logged In User: " + firebaseUser.getEmail());
            }
        });

        loggedInViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if(loggedOut){
                    Navigation.findNavController(getView()).navigate(R.id.action_loggedInFragment_to_loginRegisterFragment);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loggedinfragment, container, false);

        loggedInUserTextView = view.findViewById(R.id.fragment_loggedin_loggedInUser);
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                loggedInViewModel.logout();
            }
        });

        return view;
    }
}
