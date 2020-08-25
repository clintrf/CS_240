package familymap.client.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import familymap.client.Model.DataCache;
import familymap.client.Model.DisplayRow;
import familymap.client.R;
import familymap.server.modelClasses.ModelEvents;
import familymap.server.modelClasses.ModelPersons;

public class PersonActivity extends AppCompatActivity {
    String selectedPersonID = new String();
    private RecyclerView recyclerView;
    private Adapter adapter;
    private Context here = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedPersonID = getIntent().getExtras().getString("personID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        TextView firstNameTextView = (TextView) findViewById(R.id.firstNameTitle);
        TextView lastNameTextView = (TextView) findViewById(R.id.lastNameTitle);
        TextView genderTextView = (TextView) findViewById(R.id.genderTitle);

        ModelPersons personSelected = new ModelPersons();
        personSelected = DataCache.getInstance().getPersonById(selectedPersonID);

        firstNameTextView.setText(personSelected.getFirstName());
        lastNameTextView.setText(personSelected.getLastName());
        if (personSelected.getGender().equals("m")){
            genderTextView.setText("Male");
        } else {
            genderTextView.setText("Female");
        }

        //Recycler view time
        recyclerView = (RecyclerView) findViewById(R.id.expandableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ModelEvents> eventsOfPerson = DataCache.getInstance().getEventsOfPersonByPersonId(selectedPersonID);
        ModelPersons person = DataCache.getInstance().getPersonById(selectedPersonID);
        List<DisplayRow> eventRows = new ArrayList<>();
        List<DisplayRow> peopleRows = new ArrayList<>();


        List<ModelPersons> personsChildren = new ArrayList<>(); //could have none
        List<ModelPersons> personsParents = new ArrayList<>(); //could have none
        ModelPersons spouse = new ModelPersons();


//Beginning people

        Map<String, List<ModelPersons>> childrenMap = DataCache.getInstance().getChildrenMap();
        if (childrenMap.containsKey(person.getPersonID())){ //person has children
            personsChildren = childrenMap.get(person.getPersonID());
        }

        personsParents = DataCache.getInstance().findParentsByPersonID(person);

        spouse = DataCache.getInstance().findSpouseByPersonID(person);

        //Now we should check and see if personParents, personsChildren, and spouse are empty

        if (personsParents.size() != 0){ //has parents, make display rows
            for (int i = 0; i < personsParents.size(); i++){
                if (personsParents.get(i).getGender().equals("f")){
                    DisplayRow temp = new DisplayRow(personsParents.get(i).getDescription(),"Mother", "female", personsParents.get(i).getPersonID());
                    peopleRows.add(temp);
                } else {
                    DisplayRow temp = new DisplayRow(personsParents.get(i).getDescription(),"Father", "male", personsParents.get(i).getPersonID());
                    peopleRows.add(temp);
                }

            }
        }

        if (personsChildren.size() != 0){
            for (int i = 0; i < personsChildren.size(); i++){
                if (personsChildren.get(i).getGender().equals("f")){
                    DisplayRow temp = new DisplayRow(personsChildren.get(i).getDescription(), "Daughter", "female", personsChildren.get(i).getPersonID());
                    peopleRows.add(temp);
                } else {
                    DisplayRow temp = new DisplayRow(personsChildren.get(i).getDescription(), "Son", "male", personsChildren.get(i).getPersonID());
                    peopleRows.add(temp);
                }
            }
        }

        if (spouse.getPersonID() != null && !spouse.getPersonID().equals("")){
            if (spouse.getGender().equals("f")){
                DisplayRow temp = new DisplayRow(spouse.getDescription(), "Wife", "female", spouse.getPersonID());
                peopleRows.add(temp);
            } else {
                DisplayRow temp = new DisplayRow(spouse.getDescription(), "Husband", "Male", spouse.getPersonID());
                peopleRows.add(temp);
            }
        }


        //Beginning events

        for (int i = 0; i < eventsOfPerson.size(); i++){
            DisplayRow temp = new DisplayRow(eventsOfPerson.get(i).getDescription(), person.getDescription(), "event", eventsOfPerson.get(i).getEventID());
            eventRows.add(temp);
        }

        Group eventGroup = new Group("LIFE EVENTS", eventRows);
        Group peopleGroup = new Group("FAMILY", peopleRows);

        List<Group> groups = new ArrayList<>();
        groups.add(eventGroup);
        groups.add(peopleGroup);




        adapter = new Adapter(this, groups);
        recyclerView.setAdapter(adapter);

        adapter.setExpandCollapseListener(
                new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @Override
                    public void onParentExpanded(int parentPosition) {
                        adapter.expandParent(parentPosition);
                    }
                    @Override
                    public void onParentCollapsed(int parentPosition) {
                        adapter.collapseParent(parentPosition);
                    }
                });

    }

    class Group implements Parent<DisplayRow> {
        String name;
        List<DisplayRow> rows;

        Group(String name, List<DisplayRow> rows){
            this.name = name;
            this.rows = rows;
        }

        @Override
        public List<DisplayRow> getChildList() {

            return rows;
        }
        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }

    }



    class Adapter extends ExpandableRecyclerAdapter<Group, DisplayRow, GroupHolder, Holder> {

        private LayoutInflater inflater;

        public Adapter(Context context, List<Group> groups) {
            super(groups);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GroupHolder onCreateParentViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.group, viewGroup, false);
            return new GroupHolder(view);
        }

        @Override
        public Holder onCreateChildViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.display_row, viewGroup, false);
            return new Holder(view);
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolder holder, int i, Group group) {
            holder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(@NonNull Holder holder, int i, int j, DisplayRow item) {
            holder.bind(item);
        }
    }

    class GroupHolder extends ParentViewHolder {

        private TextView textView;

        public GroupHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.groupHolderId);
        }

        void bind(Group group) {
            textView.setText(group.name);
        }

    }

    class Holder extends ChildViewHolder implements View.OnClickListener {

        private TextView textView;
        private TextView textViewTwo;
        private ImageView iconForRow;
        private String rowType;
        private String Id;

        public Holder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.topTextOfRow);
            textViewTwo = (TextView) view.findViewById(R.id.bottomTextOfRow);
            iconForRow = (ImageView) view.findViewById(R.id.rowIcon);
            LinearLayout row = (LinearLayout) view.findViewById(R.id.rowLayout);
            row.setOnClickListener(this);
            rowType = new String();
            Id = new String();
        }

        void bind(DisplayRow row) {
            rowType = row.getType();
            Id = row.getIdOfRow();

            textView.setText(row.getTopRow());
            textViewTwo.setText(row.getBottomRow());
            if (row.getType().equals("event")){
                Drawable eventIcon = new IconDrawable(here, FontAwesomeIcons.fa_map_marker).colorRes(R.color.green_color).sizeDp(40);
                iconForRow.setImageDrawable(eventIcon);
            } else if (row.getType().equals("female")){
                Drawable genderIcon = new IconDrawable(here, FontAwesomeIcons.fa_female).colorRes(R.color.female_color).sizeDp(40);
                iconForRow.setImageDrawable(genderIcon);
            } else {
                Drawable genderIcon = new IconDrawable(here, FontAwesomeIcons.fa_male).colorRes(R.color.male_color).sizeDp(40);
                iconForRow.setImageDrawable(genderIcon);
            }
        }

        @Override
        public void onClick(View view) {
            if (rowType.equals("event")){

                Intent intent = new Intent(here, MapActivity.class);
                Bundle mBundle = new Bundle();

                mBundle.putString("personID", Id);
                intent.putExtras(mBundle);
                startActivity(intent);

            } else {
                Intent intent = new Intent(here, PersonActivity.class);
                Bundle mBundle  = new Bundle();

                mBundle.putString("personID", Id);
                intent.putExtras(mBundle);
                startActivity(intent);
            }

        }

    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

}