package familymap.client.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

import familymap.client.Model.DataCache;
import familymap.client.Model.DisplayObj;
import familymap.client.R;
import familymap.server.modelClasses.ModelPersons;

public class PersonActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        String personID = null;
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                personID = getIntent().getExtras().getString("personID");
            }
        } else {
            personID = (String) savedInstanceState.getSerializable("personID");
        }

        ModelPersons person = DataCache.getInstance().getPersonById(personID);

        ((TextView)findViewById(R.id.first_name)).setText(person.getFirstName());
        ((TextView)findViewById(R.id.last_name)).setText(person.getLastName());
        if (person.getGender().equals("m")){
            ((TextView)findViewById(R.id.gender)).setText(R.string.male);
        } else {
            ((TextView)findViewById(R.id.gender)).setText(R.string.female);
        }


        ArrayList<DisplayObj> eventList = DataCache.getInstance().getOrderEvents(person);
        ArrayList<DisplayObj> peopleList = DataCache.getInstance().getOrderPeople(person);

        ArrayList<Group> groupList = new ArrayList<>();
        groupList.add(new Group("EVENTS", eventList));
        groupList.add(new Group("RELATIONS", peopleList));


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.expandableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Adapter adapter = new Adapter(this, groupList);
        recyclerView.setAdapter(adapter);

    }
    class Adapter extends ExpandableRecyclerAdapter<Group, DisplayObj, GroupHolder, Holder> {

        private LayoutInflater inflater;

        public Adapter(Context context, List<Group> groups) {
            super(groups);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public GroupHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
            return new GroupHolder(inflater.inflate(R.layout.activity_person_title_layout, parentViewGroup, false));
        }

        @NonNull
        @Override
        public Holder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
            return new Holder(inflater.inflate(R.layout.activity_person_line_layout, childViewGroup, false));
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolder parentViewHolder, int parentPosition, @NonNull Group parent) {
            parentViewHolder.bind(parent);
        }

        @Override
        public void onBindChildViewHolder(@NonNull Holder childViewHolder, int parentPosition, int childPosition, @NonNull DisplayObj child) {
            childViewHolder.bind(child);
        }
    }

    static class Group implements Parent<DisplayObj> {
        String title;
        List<DisplayObj> list;

        Group(String name, List<DisplayObj> list){
            this.title = name;
            this.list = list;
        }

        @Override
        public List<DisplayObj> getChildList() {
            return list;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return true;
        }
    }

    static class GroupHolder extends ParentViewHolder {
        private TextView textView;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.groupHolderId);
        }
        void bind(Group group) {
            textView.setText(group.title);
        }
        @Override
        public void onClick(View view) {
        }

    }

    class Holder extends ChildViewHolder implements View.OnClickListener {
        private ImageView lineIcon;
        private TextView lineText;
        private String lineType;
        private String linePersonID;
        public Holder(View view) {
            super(view);
            ((LinearLayout) view.findViewById(R.id.line_layout)).setOnClickListener(this);
            lineText = (TextView) view.findViewById(R.id.lineText);
            lineIcon = (ImageView) view.findViewById(R.id.line_icon);
        }

        void bind(DisplayObj line) {
            if (line.getLineType().equals("event")){
                lineIcon.setImageResource(R.drawable.event);
            } else if (line.getLineType().equals("female")){
                lineIcon.setImageResource(R.drawable.female);
            } else if (line.getLineType().equals("male")){
                lineIcon.setImageResource(R.drawable.male);
            } else {
                lineIcon.setImageResource(R.drawable.android);
            }

            lineText.setText(line.getLineText());
            lineType = line.getLineType();
            linePersonID = line.getLinePersonID();
        }

        @Override
        public void onClick(View view) {
            Bundle mBundle = new Bundle();
            mBundle.putString("personID", linePersonID);
            Intent intent = null;
            if (lineType.equals("event")){
                intent = new Intent(view.getContext(), EventActivity.class);
            } else if (lineType.equals("male") || lineType.equals("female")){
                intent = new Intent(view.getContext(), PersonActivity.class);
            } else {
                intent = new Intent(view.getContext(), MainActivity.class);
            }
            intent.putExtras(mBundle);
            startActivity(intent);
        }

    }

}