package familymap.client.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import familymap.client.Model.DataCache;
import familymap.client.Model.DisplayObj;
import familymap.client.R;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    EditText search_bar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        context = this;

        setTitle("Family Map: Search");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_bar = (EditText)findViewById(R.id.search_bar);

        search_bar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    DataCache.getInstance().search(search_bar.getText().toString());


                    Adapter adapter = new Adapter(context, DataCache.getInstance().getSearchList());
                    recyclerView.setAdapter(adapter);

                    return true;
                }
                return false;
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<SearchActivity.Holder> {

        private ArrayList<DisplayObj> list;
        private LayoutInflater inflater;

        public Adapter(Context context, ArrayList<DisplayObj> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }


        @NonNull
        @Override
        public SearchActivity.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.expandable_list_item, parent, false);
            Holder thisHolder = new Holder(view);
            return thisHolder;
        }

        @Override
        public void onBindViewHolder(SearchActivity.Holder holder, int position) {
            DisplayObj item = list.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topView;
        private TextView bottomView;
        private ImageView image;
        private DisplayObj outer_item;

        public Holder(View view) {
            super(view);
            topView = (TextView) view.findViewById(R.id.textTop);
            bottomView = (TextView) view.findViewById(R.id.textBottom);
            image = (ImageView) view.findViewById(R.id.imageView);
        }

        void bind(DisplayObj item) {
            outer_item = item;
            if(item.getEvent() == null){
                if(item.getGender().equals("m")){
                    image.setImageResource(R.drawable.male);
                }else{
                    image.setImageResource(R.drawable.female);
                }
                topView.setText(item.getName());
                bottomView.setText("");

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getPersonID());
                        startActivity(intent);
                    }
                });

                topView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getPersonID());
                        startActivity(intent);
                    }
                });

                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getPersonID());
                        startActivity(intent);
                    }
                });


            }else{
                image.setImageResource(R.drawable.event);
                topView.setText(item.getEventDetails());
                bottomView.setText(item.getName());

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, EventActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().getEventID());
                        startActivity(intent);
                    }
                });

                topView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, EventActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().getEventID());
                        startActivity(intent);
                    }
                });

                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EventActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().getEventID());
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public void onClick(View view) { }

    }
}