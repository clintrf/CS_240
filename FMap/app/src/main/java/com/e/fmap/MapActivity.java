package com.e.fmap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class MapActivity extends AppCompatActivity {

    private String IdOfEvent = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IdOfEvent = getIntent().getExtras().getString("personID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.myMapFragmentDifferent);


        if(mapFrag==null) {
            mapFrag=new MapFragment();
            mapFrag.setEventId(IdOfEvent);
            mapFrag.setComingFromPerson(true);
            mapFrag.setContext(this);
            fm.beginTransaction()
                    .replace(R.id.mapLayout, mapFrag)
                    .commit();
        }
    }

}