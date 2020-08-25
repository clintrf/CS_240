package familymap.client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import familymap.client.Net.ServerProxy;
import familymap.client.R;
import familymap.server.serviceClasses.RequestLogin;
import familymap.server.serviceClasses.ResponseLogin;

public class SignInTask extends AsyncTask<RequestLogin, Void, ResponseLogin> {

    private ResponseLogin answer = new ResponseLogin();
    private RequestLogin myRequest;
    private Fragment myFrag;
    private Context myMainActivity;

    public SignInTask(Fragment in, Context inActivity){
        this.myFrag = in;
        myMainActivity = inActivity;
    }



    public ResponseLogin doInBackground(RequestLogin... myLoginRequest) {
        myRequest = myLoginRequest[0];
        try {
            ServerProxy myProxy = new ServerProxy();

            URL url = new URL("http://" + myLoginRequest[0].getHost() + ":" + myLoginRequest[0].getPort() + "/user/login");

            answer = myProxy.getLogin(url, myLoginRequest[0]);

            return answer;

        } catch (MalformedURLException e){
            answer.setMessage("Bad URl");
            answer.setSuccess(false);
            return answer;
        }
    }

    protected void onPostExecute(ResponseLogin response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (answer.getSuccess()){

            String url = ("http://" + myRequest.getHost() + ":" + myRequest.getPort() + "/person/" + response.getPersonID());

            GetPersonTask personTask = new GetPersonTask(myFrag, myMainActivity,myRequest, response);
            personTask.execute(url, response.getAuthToken());

        } else {
            Toast.makeText(myFrag.getContext(),
                    R.string.loginNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

    }

}