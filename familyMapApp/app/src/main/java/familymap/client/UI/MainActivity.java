package familymap.client.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import familymap.client.Model.DataCache;
import familymap.client.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.fragment_layout);
        Fragment mapFrag = fm.findFragmentById(R.id.fragment_layout);

        if (!DataCache.getInstance().getUserSettings().isLoggedIn()){
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
        DataCache.getInstance().getUserSettings().setLoggedIn(true);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.map_fragment_layout);


        if(mapFrag==null) {
            mapFrag=new MapFragment();
            mapFrag.setEventId(
                    DataCache.getInstance().getPeopleEventMap().get(
                            DataCache.getInstance().getUser().getPersonID()).get(0).getEventID());
            fm.beginTransaction()
                    .replace(R.id.fragment_layout, mapFrag)
                    .commit();


        }
    }
}
