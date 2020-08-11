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
import server.serviceClasses.ResponsePeople;
import server.serviceClasses.ResponseRegister;

public class GetPeopleTask extends AsyncTask<String,Void, ResponsePeople> {
    private Fragment myFrag;
    private RequestRegister taskRequest;
    private ResponseRegister responseFromRegister;
    private Context whereICameFrom;
    private String stringForToastIfSuccessful;
    private ResponsePeople myResponse = new ResponsePeople();

    public GetPeopleTask(Fragment in, RequestRegister inTwo, ResponseRegister inThree, Context inFour, String inFive){
        this.myFrag = in;
        taskRequest = inTwo;
        responseFromRegister = inThree;
        whereICameFrom = inFour;
        stringForToastIfSuccessful = inFive;
    }

    public ResponsePeople doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            myResponse = myServerProxy.getAllPeopleUrl(url, urlAuth[1]);



            return myResponse;

        } catch (MalformedURLException e){
            myResponse.setMessage("Bad URl");
            myResponse.setSuccess(false);
            return myResponse;
        }
    }

    protected void onPostExecute(ResponsePeople response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful peoppleGetAll
            Model cm = Model.getInstance();
            cm.setPeople(myResponse.getData());

            //Now making EventGetAll task
            GetEventsTask eventTask = new GetEventsTask(myFrag, taskRequest, whereICameFrom, stringForToastIfSuccessful);
            String url = new String("http://" + taskRequest.getHost() + ":" + taskRequest.getPort() + "/event/");
            eventTask.execute(url, responseFromRegister.getAuthToken());


        } else { //was not a successful peopleGetAll
            //display failed register toast
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulPeopleGetAll,
                    Toast.LENGTH_SHORT).show();
        }

    }

}