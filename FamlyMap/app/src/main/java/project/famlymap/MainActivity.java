package project.famlymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import project.famlymap.Model.Model;

public class MainActivity extends AppCompatActivity {
    String maintsBirthID = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment loginFrag = fm.findFragmentById(R.id.layout);
//        Fragment mapFrag = fm.findFragmentById(R.id.layout);
//
//        if (!Model.getInstance().isLoggedIn()) {
//            if (loginFrag == null) {
//                loginFrag = LoginFragment.newInstance(this);
//                fm.beginTransaction()
//                        .add(R.id.layout, loginFrag)
//                        .commit();
//            }
//        } else {
//            if(mapFrag==null) {
//                mapFrag=new MyMapFragment();
//                fm.beginTransaction()
//                        .replace(R.id.firstLayout, mapFrag)
//                        .commit();
//            }
//        }
    }
    public void onLoginRegisterSuccess(){
        Model clientModel = Model.getInstance();
        clientModel.setLoggedIn(true);


        String birthIDOfUser = clientModel.getPeopleEventMap().get(clientModel.getUser().getPersonID()).get(0).getEventID();

        maintsBirthID = birthIDOfUser;

        FragmentManager fm = getSupportFragmentManager();
//        MyMapFragment mapFrag = (MyMapFragment) fm.findFragmentById(R.id.myMapFragmentDifferent);
//
//
//        if(mapFrag==null) {
//            mapFrag=new MyMapFragment();
//            mapFrag.setEventId(birthIDOfUser);
//            mapFrag.setContext(this);
//            fm.beginTransaction()
//                    .replace(R.id.firstLayout, mapFrag)
//                    .commit();
//
//
//        }
    }

}
