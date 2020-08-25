package com.e.fmap;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import server.serviceClasses.RequestLogin;
import server.serviceClasses.RequestRegister;


public class LoginFragment extends Fragment {

    private RequestLogin myLoginRequest;
    private RequestRegister myRequestRegister;
    private EditText myServerHostEditField;
    private EditText myServerPortEditField;
    private EditText myUserNameEditField;
    private EditText myPasswordEditField;
    private EditText myFirstNameEditField;
    private EditText myLastNameEditField;
    private EditText myEmailEditField;
    private RadioGroup genderGroup;
    private Button mySignInButton;
    private Button myRegisterButton;
    private static Context whereICameFrom;


    public LoginFragment() {

    }

    public static LoginFragment newInstance(Context in) {
        whereICameFrom = in;
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLoginRequest = new RequestLogin();
        myRequestRegister = new RequestRegister();
    }

    private void checkLoginFieldsForEmptyValues(){
        String s1 = myServerHostEditField.getText().toString();
        String s2 = myServerPortEditField.getText().toString();
        String s3 = myUserNameEditField.getText().toString();
        String s4 = myPasswordEditField.getText().toString();


        if(s1.equals("")|| s2.equals("") || s3.equals("") || s4.equals("")){
            mySignInButton.setEnabled(false);
        } else {
            mySignInButton.setEnabled(true);
        }
    }

    private void checkRegisterFieldsForEmptyValues(){
        String s1 = myServerHostEditField.getText().toString();
        String s2 = myServerPortEditField.getText().toString();
        String s3 = myUserNameEditField.getText().toString();
        String s4 = myPasswordEditField.getText().toString();
        String s5 = myFirstNameEditField.getText().toString();
        String s6 = myLastNameEditField.getText().toString();
        String s7 = myEmailEditField.getText().toString();

        if(s1.equals("")|| s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("") || s7.equals("")){
            myRegisterButton.setEnabled(false);
        } else {
            myRegisterButton.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myRequestRegister.setGender("m");
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mySignInButton = (Button) v.findViewById(R.id.signInButton);
        mySignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClicked();
            }
        });

        myRegisterButton = (Button) v.findViewById(R.id.registerButton);
        myRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });


        myServerHostEditField = (EditText) v.findViewById(R.id.serverHostEditText);
        myServerHostEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }


            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                if(s.toString().equals("0")){
                    myLoginRequest.setHost("10.0.2.2");
                    myRequestRegister.setHost("10.0.2.2");
                }else {
                    myLoginRequest.setHost(s.toString());
                    myRequestRegister.setHost(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        myServerPortEditField = (EditText) v.findViewById(R.id.serverPortEditText);
        myServerPortEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }



            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                if(s.toString().equals("0")){
                    myLoginRequest.setPort("8080");
                    myRequestRegister.setPort("8080");
                }else {
                    myLoginRequest.setPort(s.toString());
                    myRequestRegister.setPort(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });


        myUserNameEditField = (EditText) v.findViewById(R.id.userNameEditText);
        myUserNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myLoginRequest.setUserName(s.toString());
                myRequestRegister.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        myPasswordEditField = (EditText) v.findViewById(R.id.passwordEditText);
        myPasswordEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myLoginRequest.setPassword(s.toString());
                myRequestRegister.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        checkLoginFieldsForEmptyValues();


        //From here on out it's only for register requests

        myFirstNameEditField = (EditText) v.findViewById(R.id.firstNameEditText);
        myFirstNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myRequestRegister.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterFieldsForEmptyValues();
            }
        });

        myLastNameEditField = (EditText) v.findViewById(R.id.lastNameEditText);
        myLastNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myRequestRegister.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterFieldsForEmptyValues();
            }
        });

        myEmailEditField = (EditText) v.findViewById(R.id.emailEditText);
        myEmailEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myRequestRegister.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterFieldsForEmptyValues();
            }
        });

        genderGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.femaleRadioButton) {
                    myRequestRegister.setGender("f");
                }
                if (checkedId == R.id.maleRadioButton) {
                    myRequestRegister.setGender("m");
                }
            }
        });

        checkRegisterFieldsForEmptyValues();



        return v;
    }


    private void onSignInButtonClicked() {
        SignInTask signInTask = new SignInTask(this,whereICameFrom);
        signInTask.execute(myLoginRequest);
    }

    private void onRegisterButtonClicked(){
        RegisterTask registerTask = new RegisterTask(this,whereICameFrom);
        registerTask.execute(myRequestRegister);
    }

}
