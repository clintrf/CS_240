package familymap.client.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import familymap.client.R;


public class EventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        String personID = null;
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                personID = getIntent().getExtras().getString("personID");
                if(personID == null){
                    personID = getIntent().getExtras().getString("eventIDToZoom");
                }
            }
        } else {
            personID = (String) savedInstanceState.getSerializable("personID");
        }

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.map_fragment_layout);


        if(mapFrag==null) {
            mapFrag = new MapFragment();
            mapFrag.setEventId(personID);
            mapFrag.setComingFromPerson(true);
            fm.beginTransaction()
                    .replace(R.id.event_layout, mapFrag)
                    .commit();
        }
    }

}