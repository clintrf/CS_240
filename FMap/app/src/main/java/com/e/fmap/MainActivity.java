package com.e.fmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.layout);
        Fragment mapFrag = fm.findFragmentById(R.id.layout);

        loginFrag = LoginFragment.newInstance(this);
                fm.beginTransaction()
                        .add(R.id.layout, loginFrag)
                        .commit();
    }

    public void onLoginRegisterSuccess() {
    }
}
