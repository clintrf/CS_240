package familymap.client.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import familymap.client.UI.MainActivity;
import familymap.client.Model.DataCache;
import familymap.client.Net.ServerProxy;
import familymap.client.R;
import familymap.server.serviceClasses.RequestRegister;
import familymap.server.serviceClasses.ResponseEvents;

public class GetEventsTask extends AsyncTask<String,Void, ResponseEvents> {
    private Fragment myFrag;
    private Context whereICameFrom;
    private String toastSuccess;
    private ResponseEvents myResponse = new ResponseEvents();

    public GetEventsTask(Fragment in, RequestRegister inTwo, Context inThree, String inFour){
        this.myFrag = in;
        whereICameFrom = inThree;
        toastSuccess = inFour;
    }

    public ResponseEvents doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);
            myResponse = myServerProxy.getEvents(url, urlAuth[1]);
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

        if (response == null){
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulEventGetALL,
                    Toast.LENGTH_SHORT).show();
        }

        if (response.getSuccess()){
            DataCache cm = DataCache.getInstance();
            cm.setEvents(myResponse.getData());



            Toast.makeText(myFrag.getContext(),
                    toastSuccess,
                    Toast.LENGTH_SHORT).show();
            MainActivity source = (MainActivity) whereICameFrom;
            source.onLoginRegisterSuccess();


        } else {
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulEventGetALL,
                    Toast.LENGTH_SHORT).show();
        }

    }

}