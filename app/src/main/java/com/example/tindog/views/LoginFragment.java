package com.example.tindog.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tindog.R;
import com.example.tindog.models.ModelFirebase;

public class LoginFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInput = view.findViewById(R.id.login_activity_email_edit_text);
        passwordInput = view.findViewById(R.id.login_activity_password_edit_text);
        loginBtn = view.findViewById(R.id.login_fragment_btn);
        registerBtn = view.findViewById(R.id.login_fragment_register_btn);

        loginBtn.setOnClickListener(v -> login());
        registerBtn.setOnClickListener(v -> toRegisterPage());
    }

    public void login() {
        ModelFirebase.signIn(emailInput, passwordInput, listener -> {
            if (listener) {
                Toast.makeText(getContext(), "Welcome :)", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToFeedsFragment());
                getActivity().recreate();
            }
        });
    }

    public void toRegisterPage() {
        Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment());
    }
}