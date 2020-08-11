package project.famlymap;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import project.famlymap.Model.Model;
import project.famlymap.Model.ServerProxy;
import server.serviceClasses.RequestRegister;
import server.serviceClasses.ResponseEvents;

public class GetEventsTask extends AsyncTask<String,Void, ResponseEvents> {
    private Fragment myFrag;
    private RequestRegister taskRequest;
    private Context whereICameFrom;
    private String stringForToasIfSuccessful;
    private ResponseEvents myResponse = new ResponseEvents();

    public GetEventsTask(Fragment in, RequestRegister inTwo, Context inThree, String inFour){
        this.myFrag = in;
        taskRequest = inTwo;
        whereICameFrom = inThree;
        stringForToasIfSuccessful = inFour;
    }

    public ResponseEvents doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            Model cm = Model.getInstance();
            cm.setAuthToken(urlAuth[1]);

            myResponse = myServerProxy.getAllEventsUrl(url, urlAuth[1]);



            return myResponse;

        } catch (MalformedURLException e){
            myResponse.setMessage("Bad URl");
            myResponse.setSuccess(false);
            return myResponse;
        }
    }

    protected void onPostExecute(ResponseEvents response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful EventGetAll
            Model cm = Model.getInstance();
            cm.setEvents(myResponse.getData());



            Toast.makeText(myFrag.getContext(),
                    stringForToasIfSuccessful,
                    Toast.LENGTH_SHORT).show();

            //This is when we call onLoginRegisterSuccess for when we are both logging in and registering
            MainActivity source = (MainActivity) whereICameFrom;
            source.onLoginRegisterSuccess();


        } else { //was not a successful eventGetAll
            //display failed eventGetAll toast
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulEventGetALL,
                    Toast.LENGTH_SHORT).show();
        }

    }

}