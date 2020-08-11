package com.e.fmap;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.e.fmap.Model.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import server.serviceClasses.RequestLogin;
import server.serviceClasses.RequestRegister;
import server.serviceClasses.ResponseLogin;
import server.serviceClasses.ResponsePerson;
import server.serviceClasses.ResponseRegister;

public class GetPersonTask extends AsyncTask<String, Void, ResponsePerson> {
    ResponsePerson person;
    Fragment myFrag;
    Context whereICameFrom;
    RequestLogin loginRequest;
    ResponseLogin loginResponse;
    RequestRegister requestForGetPeopleTask;
    ResponseRegister responseForGetPeopleTask;

    public GetPersonTask(Fragment in, Context inTwo, RequestLogin inThree, ResponseLogin inFour){
        myFrag = in;
        whereICameFrom = inTwo;
        loginRequest = inThree;
        requestForGetPeopleTask = new RequestRegister();
        requestForGetPeopleTask.setHost(loginRequest.getHost());
        requestForGetPeopleTask.setPort(loginRequest.getPort());
        responseForGetPeopleTask = new ResponseRegister();
        loginResponse = inFour;
    }

    public ResponsePerson doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            person = myServerProxy.getPersonURL(url, urlAuth[1]);

            return person;

        } catch (MalformedURLException e){
            person.setMessage("Bad URl");
            person.setSuccess(false);
            return person;
        }
    }

    protected void onPostExecute(ResponsePerson response){

        if (response.getSuccess()){

            String stringForToastIfSuccessful = new String("Login Success!" + "\n" + response.getFirstName() + "\n" + response.getLastName());
            responseForGetPeopleTask.setAuthToken(loginResponse.getAuthToken());
            String url = new String ("http://" + requestForGetPeopleTask.getHost() + ":" + requestForGetPeopleTask.getPort() + "/person/");

            GetPeopleTask myPeopleTask = new GetPeopleTask(myFrag,requestForGetPeopleTask, responseForGetPeopleTask, whereICameFrom, stringForToastIfSuccessful);
            myPeopleTask.execute(url, responseForGetPeopleTask.getAuthToken());


        }
        else {
            Toast.makeText(myFrag.getContext(),
                    R.string.loginNotSuccessfulPerson,
                    Toast.LENGTH_SHORT).show();
        }

    }


}