package familymap.client.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import familymap.client.Model.DataCache;
import familymap.client.Model.LinkEvents;
import familymap.client.R;
import familymap.server.modelClasses.ModelEvents;
import familymap.server.modelClasses.ModelPersons;


public class MapFragment extends Fragment implements  OnMapReadyCallback{
    private String centeredID;
    private GoogleMap mMap;
    private ModelEvents mapsEventSelected;
    private Boolean markerClicked = false;
    private Boolean comingFromPerson = false;

    private View v;


    public MapFragment() {
    }

    public void setEventId(String in) {
        centeredID = in;
    }

    public void setComingFromPerson(Boolean bool) {
        comingFromPerson = bool;
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (comingFromPerson) {
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(Menu myMenu, MenuInflater inflater) {
        inflater.inflate(R.layout.activity_main_menu, myMenu);
        super.onCreateOptionsMenu(myMenu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intentThree = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentThree);
                return true;

            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);


        SupportMapFragment myfrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        myfrag.getMapAsync(this);

        LinearLayout bottomOfScreen = (LinearLayout) v.findViewById(R.id.bottomOfScreen);
        bottomOfScreen.setOnClickListener(new View.OnClickListener() {



            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (markerClicked) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personID", mapsEventSelected.getPersonID());

                    startActivity(intent);
                }

            }
        });

        return v;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void clickMarker(View v) {
        DataCache cm = DataCache.getInstance();

        ModelPersons personSelected = cm.getPersonById(mapsEventSelected.getPersonID());

        ImageView img = (ImageView) v.findViewById(R.id.iconImageView);

        Iconify.with(new FontAwesomeModule());


        if (personSelected.getGender().equals("f")) {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_color).sizeDp(40);
            img.setImageDrawable(genderIcon);
        } else {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_color).sizeDp(40);
            img.setImageDrawable(genderIcon);
        }

        TextView text = (TextView) v.findViewById(R.id.text);
        text.setText(personSelected.getDescription() + "\n" + mapsEventSelected.getDescription());
        markerClicked = true;

        setLines(DataCache.getInstance().getEventById(mapsEventSelected.getEventID()),mMap);
    }


    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMarkers(mMap);

        ModelEvents eventToCenter = DataCache.getInstance().getEventMap().get(centeredID);
        LatLng placeToCenter = new LatLng(eventToCenter.getLatitude(), eventToCenter.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(placeToCenter));



       if (comingFromPerson) {
           mapsEventSelected = DataCache.getInstance().getEventById(centeredID);
           clickMarker(v);
       }

       mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

           @Override
           public void onMapClick(LatLng latLng) {
               mMap.clear();
               setMarkers(mMap);
           }

       });

       mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
           @Override
           public boolean onMarkerClick(Marker marker) {
               mapsEventSelected = (ModelEvents) marker.getTag();
               clickMarker(v);
               return false;
           }
       });
       }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setMarkers(GoogleMap mMap) {

        List<String> colorList = Arrays.asList(
                "#F44336", //R
                "#2196F3", //B
                "#4CAF50", //G
                "#CDDC39", //Y
                "#673AB7", //P
                "#BA7300",
                "#289261",
                "#000000",
                "#000000");;

        Map<String, ModelEvents> events;

        if (DataCache.getInstance().getUserSettings().isFatherSide() && DataCache.getInstance().getUserSettings().isMotherSide()){
            events = DataCache.getInstance().getEventMap();
        } else if (!DataCache.getInstance().getUserSettings().isFatherSide() && DataCache.getInstance().getUserSettings().isMotherSide()){
            events = DataCache.getInstance().getEventMapMaternal();
        } else if(DataCache.getInstance().getUserSettings().isFatherSide() && !DataCache.getInstance().getUserSettings().isMotherSide()){
            events = DataCache.getInstance().getEventMapPaternal();
        } else {
            events= new HashMap<>();
        }
        Set<String> masterEventList = new ArraySet<>();

        masterEventList.addAll(DataCache.getInstance().getEventTypes());
        masterEventList.addAll(DataCache.getInstance().getEventTypesFemale());
        masterEventList.addAll(DataCache.getInstance().getEventTypesMale());


        List<String> finalListOfEventTypes = new ArrayList<>(masterEventList);

        DataCache.getInstance().setAllEventTypes(finalListOfEventTypes);
        for (Map.Entry<String, ModelEvents> entry : events.entrySet()) {
            ModelEvents event = entry.getValue();

            if (DataCache.getInstance().getUserSettings().isFemaleEvents() &&
                    DataCache.getInstance().getPersonById(event.getPersonID()).getGender().equals("f")) {
                int indexOfColorToUse = finalListOfEventTypes.indexOf(event.getEventType().toLowerCase());
                if(indexOfColorToUse<0){
                    indexOfColorToUse = 4 + (indexOfColorToUse *-1);
                }
                String colorHexValue = colorList.get(indexOfColorToUse);

                LatLng eventLatLong2 = new LatLng(event.getLatitude(), event.getLongitude());
                mMap.addMarker(new MarkerOptions().position(eventLatLong2)
                        .icon(getMarkerIcon(colorHexValue))).setTag(event);
            }

            if (DataCache.getInstance().getUserSettings().isMaleEvents() && DataCache.getInstance().getPersonById(event.getPersonID()).getGender().equals("m")) {
                int indexOfColorToUse = finalListOfEventTypes.indexOf(event.getEventType().toLowerCase());
                if(indexOfColorToUse<0){
                    indexOfColorToUse = 4 + (indexOfColorToUse *-1);
                }
                String colorHexValue = colorList.get(indexOfColorToUse);

                LatLng eventLatLong2 = new LatLng(event.getLatitude(), event.getLongitude());
                mMap.addMarker(new MarkerOptions().position(eventLatLong2)
                        .icon(getMarkerIcon(colorHexValue))).setTag(event);
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setLines(ModelEvents event, GoogleMap mMap) {
        mMap.clear();
        setMarkers(mMap);

        if(DataCache.getInstance().getUserSettings().isLifeLines()){
            setLifeLines(event, mMap);
        }
        if(DataCache.getInstance().getUserSettings().isTreeLines()){
            setTreeLines(event, mMap);
        }
        if(DataCache.getInstance().getUserSettings().isSpouseLines()){
            setSpouseLines(event, mMap);
        }

    }

    public void setSingleLine(LinkEvents events, Integer color, GoogleMap googleMap, int width){
        PolylineOptions lines = new PolylineOptions().add(
                new LatLng(events.getFirst().getLatitude(), events.getFirst().getLongitude()),
                new LatLng(events.getSecond().getLatitude(), events.getSecond().getLongitude())
        ).color(color).width(width);
        googleMap.addPolyline(lines);
    }

    public void setLifeLines(ModelEvents selectedEvent, GoogleMap googleMap){
        ArrayList<ModelEvents> eventList = DataCache.getInstance().getOrderLifeEvents(
                DataCache.getInstance().getPersonByEvent(selectedEvent));

        for(int i = 0; i < eventList.size()-1; i++){
            setSingleLine(
                    new LinkEvents(eventList.get(i), eventList.get(i+1)),
                    DataCache.getInstance().getUserSettings().getLifeLinesColor(),
                    googleMap,
                    10);
        }
    }

    public void setTreeLines(ModelEvents selectedEvent, GoogleMap googleMap){
        if (DataCache.getInstance().getUserSettings().isMaleEvents() && DataCache.getInstance().getPersonById(selectedEvent.getPersonID()).getGender().equals("m")) {
            setTreeLinesPerson(
                    DataCache.getInstance().getPersonByEvent(selectedEvent),
                    selectedEvent,
                    googleMap,
                    1);
        }
        if (DataCache.getInstance().getUserSettings().isFemaleEvents() && DataCache.getInstance().getPersonById(selectedEvent.getPersonID()).getGender().equals("f")) {
            setTreeLinesPerson(
                    DataCache.getInstance().getPersonByEvent(selectedEvent),
                    selectedEvent,
                    googleMap,
                    1);
        }

    }

    public void setTreeLinesPerson(ModelPersons person, ModelEvents selectedEvent, GoogleMap googleMap, int generation){
        int genMother;
        int genFather;
        if(person.getFatherID() != null) {
            ModelEvents fatherFirst = DataCache.getInstance().findFirstEvent(
                    DataCache.getInstance().findPersonByID(person.getFatherID()));
            if (fatherFirst != null) {
                if (DataCache.getInstance().getUserSettings().isMaleEvents()) {
                    setSingleLine(
                            new LinkEvents(selectedEvent, fatherFirst),
                            DataCache.getInstance().getUserSettings().getTreeLinesColor(),
                            googleMap,
                            20 / generation);
                    genFather = generation+1;
                    setTreeLinesPerson(
                            DataCache.getInstance().findPersonByID(person.getFatherID()),
                            fatherFirst,
                            googleMap,
                            genFather);
                }

            }
        }
        if(person.getMotherID() != null){
            ModelEvents motherFirst = DataCache.getInstance().findFirstEvent(
                    DataCache.getInstance().findPersonByID(person.getMotherID()));
            if(motherFirst!=null){
                if (DataCache.getInstance().getUserSettings().isFemaleEvents()) {
                    setSingleLine(
                            new LinkEvents(selectedEvent, motherFirst),
                            DataCache.getInstance().getUserSettings().getTreeLinesColor(),
                            googleMap,
                            20/generation);
                    genMother = generation+1;
                    setTreeLinesPerson(
                            DataCache.getInstance().findPersonByID(person.getMotherID()),
                            motherFirst,
                            googleMap,
                            genMother);
                }

            }
        }
    }
    public void setSpouseLines(ModelEvents selectedEvent, GoogleMap googleMap){
        ModelPersons spouse = DataCache.getInstance().findSpouseByPersonID(
                DataCache.getInstance().getPersonByEvent(selectedEvent));
        if(spouse != null){
            ModelEvents eventSecond = DataCache.getInstance().findFirstEvent(spouse);
            if(eventSecond != null){
                if (DataCache.getInstance().getUserSettings().isMaleEvents() && DataCache.getInstance().getPersonById(spouse.getPersonID()).getGender().equals("m")) {
                    setSingleLine(
                            new LinkEvents(selectedEvent, eventSecond),
                            DataCache.getInstance().getUserSettings().getSpouseLinesColor(),
                            googleMap,
                            12);
                }
                if (DataCache.getInstance().getUserSettings().isFemaleEvents() && DataCache.getInstance().getPersonById(spouse.getPersonID()).getGender().equals("f")) {
                    setSingleLine(
                            new LinkEvents(selectedEvent, eventSecond),
                            DataCache.getInstance().getUserSettings().getSpouseLinesColor(),
                            googleMap,
                            12);
                }
            }
        }
    }

}