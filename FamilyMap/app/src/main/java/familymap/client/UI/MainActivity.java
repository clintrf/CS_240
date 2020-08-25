package familymap.client.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import familymap.client.Model.DataCache;
import familymap.client.R;

public class MainActivity extends AppCompatActivity {
    String maintsBirthID = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.fragment_layout);
        Fragment mapFrag = fm.findFragmentById(R.id.fragment_layout);

        if (!DataCache.getInstance().isLoggedIn()){
            if (loginFrag == null) {
                loginFrag = LoginFragment.newInstance(this);
                fm.beginTransaction()
                        .add(R.id.fragment_layout, loginFrag)
                        .commit();
            }
        } else {
            if(mapFrag==null) {
                mapFrag=new MapFragment();
                fm.beginTransaction()
                        .replace(R.id.fragment_layout, mapFrag)
                        .commit();
            }
        }
    }

    public void onLoginRegisterSuccess() {
        DataCache clientModel = DataCache.getInstance();
        clientModel.setLoggedIn(true);


        String birthIDOfUser = clientModel.getPeopleEventMap().get(clientModel.getUser().getPersonID()).get(0).getEventID();

        maintsBirthID = birthIDOfUser;

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.mapFragment);


        if(mapFrag==null) {
            mapFrag=new MapFragment();
            mapFrag.setEventId(birthIDOfUser);
            mapFrag.setContext(this);
            fm.beginTransaction()
                    .replace(R.id.fragment_layout, mapFrag)
                    .commit();


        }
    }
}