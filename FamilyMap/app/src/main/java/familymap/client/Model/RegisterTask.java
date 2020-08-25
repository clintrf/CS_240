package familymap.client.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import familymap.client.Net.ServerProxy;
import familymap.client.R;
import familymap.server.serviceClasses.RequestRegister;
import familymap.server.serviceClasses.ResponseEvents;
import familymap.server.serviceClasses.ResponsePeople;
import familymap.server.serviceClasses.ResponseRegister;

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
            //URL urla = new URL("http://" + "10.0.2.2" + ":" + "8080" + "/user/register");
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

        if(response == null){
            Toast.makeText(fragActivity.getContext(),
                    R.string.registerNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

        if (response.getSuccess()){ //was a successful register

            String url = new String ("http://" + taskRequest.getHost() + ":" + taskRequest.getPort() + "/person/");
            //String url = new String ("http://" + "10.0.2.2" + ":" + "8080" + "/person/");
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