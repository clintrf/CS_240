package familymap.client.UI;

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

import familymap.client.R;
import familymap.client.Tasks.RegisterTask;
import familymap.client.Tasks.SignInTask;
import familymap.server.serviceClasses.RequestLogin;
import familymap.server.serviceClasses.RequestRegister;


public class LoginFragment extends Fragment {

    private RequestLogin requestLogin;
    private RequestRegister requestRegister;
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
    private static Context context;


    public LoginFragment() {
    }

    public static LoginFragment newInstance(Context in) {
        context = in;
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestLogin = new RequestLogin();
        requestRegister = new RequestRegister();
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
        requestRegister.setGender("m");
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
            }


            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                if(s.toString().equals("0")){
                    requestLogin.setHost("10.0.2.2");
                    requestRegister.setHost("10.0.2.2");
                }else {
                    requestLogin.setHost(s.toString());
                    requestRegister.setHost(s.toString());
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
                    requestLogin.setPort("8080");
                    requestRegister.setPort("8080");
                }else {
                    requestLogin.setPort(s.toString());
                    requestRegister.setPort(s.toString());
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
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                requestLogin.setUserName(s.toString());
                requestRegister.setUserName(s.toString());
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
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                requestLogin.setPassword(s.toString());
                requestRegister.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        checkLoginFieldsForEmptyValues();

        myFirstNameEditField = (EditText) v.findViewById(R.id.firstNameEditText);
        myFirstNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                requestRegister.setFirstName(s.toString());
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
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                requestRegister.setLastName(s.toString());
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
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                requestRegister.setEmail(s.toString());
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
                    requestRegister.setGender("f");
                }
                if (checkedId == R.id.maleRadioButton) {
                    requestRegister.setGender("m");
                }
            }
        });

        checkRegisterFieldsForEmptyValues();



        return v;
    }


    private void onSignInButtonClicked() {
        SignInTask signInTask = new SignInTask(this, context);
        signInTask.execute(requestLogin);
    }

    private void onRegisterButtonClicked(){
        RegisterTask registerTask = new RegisterTask(this, context);
        registerTask.execute(requestRegister);
    }

}
