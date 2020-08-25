package com.e.fmap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.e.fmap.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterFilter adapter;
    private Context here = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        recyclerView = (RecyclerView) findViewById(R.id.activity_filter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

    }

    private void updateUI() {
        Model cm = Model.getInstance();
        List<String> allEventTypes = cm.getAllEventTypes();
        List<FilterRow> filterRows = new ArrayList<>();

        for (int i = 0; i < allEventTypes.size(); i++) {
            FilterRow temp = new FilterRow(allEventTypes.get(i),"event");
            filterRows.add(temp);
        }
        FilterRow temp = new FilterRow("Father's","side");
        filterRows.add(temp);
        temp = new FilterRow("Mother's", "side");
        filterRows.add(temp);
        temp = new FilterRow("Male" , "gender male");
        filterRows.add(temp);
        temp = new FilterRow("Female", "gender female");
        filterRows.add(temp);

        GroupFilter filterGroup = new GroupFilter(filterRows);
        List<GroupFilter> groups = new ArrayList<>();
        groups.add(filterGroup);

        adapter = new AdapterFilter(this, groups);
        recyclerView.setAdapter(adapter);


    }

    class GroupFilter implements Parent<FilterRow> {
        List<FilterRow> rows;

        GroupFilter(List<FilterRow> rows) {
            this.rows = rows;
        }

        @Override
        public List<FilterRow> getChildList() {

            return rows;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return true;
        }
    }


    class AdapterFilter extends ExpandableRecyclerAdapter<GroupFilter, FilterRow, GroupHolderFilter, HolderFilter> {

        private LayoutInflater inflater;

        public AdapterFilter(Context context, List<GroupFilter> groups) {
            super(groups);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GroupHolderFilter onCreateParentViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.group_filter, viewGroup, false);
            return new GroupHolderFilter(view);
        }

        @Override
        public HolderFilter onCreateChildViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.filter_row, viewGroup, false);
            return new HolderFilter(view);
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolderFilter holder, int i, GroupFilter group) {
            holder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(@NonNull HolderFilter holder, int i, int j, FilterRow item) {
            holder.bind(item);
        }
    }

    class GroupHolderFilter extends ParentViewHolder {

        private LinearLayout linearLayout;

        public GroupHolderFilter(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.groupHolderIdFilter);
        }

        void bind(GroupFilter group) {
            //textView.setText(group.name);
        }

    }

    class HolderFilter extends ChildViewHolder implements View.OnClickListener {

        private TextView textView;
        private TextView textViewTwo;
        private Switch mySwitch;
        private String rowType;
        private String Id;

        public HolderFilter(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.topTextOfRowFilter);
            textViewTwo = (TextView) view.findViewById(R.id.bottomTextOfRowFilter);
            RelativeLayout row = (RelativeLayout) view.findViewById(R.id.rowLayoutFilter);
            mySwitch = (Switch) view.findViewById(R.id.filterRowSwitch);
            Id = new String();
        }

        void bind(FilterRow row) {
            rowType = row.getType();
            textView.setText(row.getTopRow());
            textViewTwo.setText(row.getBottomRow());
            /*
            mySwitch.setEnabled(true);
            mySwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rowType.equals("gender female")){
                        if (mySwitch.isChecked()){
                            ClientModel.getInstance().setShowFemaleEvents(false);
                        } else {
                            ClientModel.getInstance().setShowFemaleEvents(true);
                        }
                    } else if (rowType.equals("gender male")){
                        if (mySwitch.isChecked()){
                            ClientModel.getInstance().setShowFemaleEvents(false);
                        } else {
                            ClientModel.getInstance().setShowFemaleEvents(true);
                        }
                    } else {
                        Toast.makeText(here, "Hi", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            */
        }

        @Override
        public void onClick(View view) {

        }

    }
}