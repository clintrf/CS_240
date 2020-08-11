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
import server.serviceClasses.ResponseEvents;
import server.serviceClasses.ResponseLogin;
import server.serviceClasses.ResponsePeople;
import server.serviceClasses.ResponseRegister;

public class RegisterTask extends AsyncTask<RequestRegister, Void, ResponseRegister> {

    private Fragment fragActivity;
    private Context myMainActivity;
    private RequestRegister taskRequest;
    private ResponseRegister answer = new ResponseRegister();
    private ResponsePeople peopleAnswer = new ResponsePeople();
    private ResponseEvents eventAnswer = new ResponseEvents();

    public RegisterTask(Fragment fragAct, Context in){
        this.fragActivity = fragAct;
        this.myMainActivity = in;
    }

    public ResponseRegister doInBackground(RequestRegister... myRegisterRequests){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        taskRequest = myRegisterRequests[0];
        try {
            ServerProxy myServerProxy = new ServerProxy();

            URL urla = new URL("http://" + myRegisterRequests[0].getHost() + ":" + myRegisterRequests[0].getPort() + "/user/register");
            answer = myServerProxy.getRegisterUrl(urla, myRegisterRequests[0]);

            return answer;

        } catch (MalformedURLException e){
            answer.setMessage("Bad URl");
            answer.setSuccess(false);
            return answer;
        }
    }

    protected void onPostExecute(ResponseRegister response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful register

            String url = new String ("http://" + taskRequest.getHost() + ":" + taskRequest.getPort() + "/person/");
            String stringForToastIfSuccessful = new String("Register Success!" + "\n" + taskRequest.getFirstName() + "\n" + taskRequest.getLastName());

            GetPeopleTask peopleTask = new GetPeopleTask(fragActivity, taskRequest, response, myMainActivity,stringForToastIfSuccessful);
            peopleTask.execute(url, response.getAuthToken());


        } else { //was not a successful register
            //display failed register toast
            Toast.makeText(fragActivity.getContext(),
                    R.string.registerNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

    }
}