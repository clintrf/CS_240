package familymap.client.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import familymap.client.Model.DataCache;
import familymap.client.R;

public class SettingsActivity extends AppCompatActivity {

    Context context = this;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch lifeLineSwitchView = (Switch) findViewById(R.id.life_line_switch);
        if (DataCache.getInstance().getUserSettings().isLifeLines()){ lifeLineSwitchView.setChecked(true); }
        else { lifeLineSwitchView.setChecked(false); }

        lifeLineSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isLifeLines()){
                    DataCache.getInstance().getUserSettings().setLifeLines(false);

                } else {
                    DataCache.getInstance().getUserSettings().setLifeLines(true);
                }

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch treeLineSwitchView = (Switch) findViewById(R.id.tree_line_switch);
        if (DataCache.getInstance().getUserSettings().isTreeLines()){ treeLineSwitchView.setChecked(true); }
        else { treeLineSwitchView.setChecked(false); }

        treeLineSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isTreeLines()){
                    DataCache.getInstance().getUserSettings().setTreeLines(false);

                } else {
                    DataCache.getInstance().getUserSettings().setTreeLines(true);
                }

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch spouseLineSwitchView = (Switch) findViewById(R.id.spouse_line_switch);
        if (DataCache.getInstance().getUserSettings().isSpouseLines()){ spouseLineSwitchView.setChecked(true); }
        else { spouseLineSwitchView.setChecked(false); }

        spouseLineSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isSpouseLines()){
                    DataCache.getInstance().getUserSettings().setSpouseLines(false);
                } else {
                    DataCache.getInstance().getUserSettings().setSpouseLines(true);
                }

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch fatherSideSwichView = (Switch) findViewById(R.id.father_side_switch);
        if (DataCache.getInstance().getUserSettings().isFatherSide()){ fatherSideSwichView.setChecked(true); }
        else { fatherSideSwichView.setChecked(false); }

        fatherSideSwichView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isFatherSide()){
                    DataCache.getInstance().getUserSettings().setFatherSide(false);
                } else {
                    DataCache.getInstance().getUserSettings().setFatherSide(true);
                }

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch motherSideSwitchView = (Switch) findViewById(R.id.mother_side_switch);
        if (DataCache.getInstance().getUserSettings().isMotherSide()){ motherSideSwitchView.setChecked(true); }
        else { motherSideSwitchView.setChecked(false); }

        motherSideSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isMotherSide()){
                    DataCache.getInstance().getUserSettings().setMotherSide(false);
                } else {
                    DataCache.getInstance().getUserSettings().setMotherSide(true);
                }

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch maleEventsSwitchView = (Switch) findViewById(R.id.male_events_switch);
        if (DataCache.getInstance().getUserSettings().isMaleEvents()){ maleEventsSwitchView.setChecked(true); }
        else { maleEventsSwitchView.setChecked(false); }

        maleEventsSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isMaleEvents()){
                    DataCache.getInstance().getUserSettings().setMaleEvents(false);
                } else {
                    DataCache.getInstance().getUserSettings().setMaleEvents(true);
                }

            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch femaleEventsSwitchView = (Switch) findViewById(R.id.female_events_switch);
        if (DataCache.getInstance().getUserSettings().isFemaleEvents()){ femaleEventsSwitchView.setChecked(true); }
        else { femaleEventsSwitchView.setChecked(false); }

        femaleEventsSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataCache.getInstance().getUserSettings().isFemaleEvents()){
                    DataCache.getInstance().getUserSettings().setFemaleEvents(false);
                } else {
                    DataCache.getInstance().getUserSettings().setFemaleEvents(true);
                }

            }
        });


        RelativeLayout logoutView = (RelativeLayout) findViewById(R.id.logoutView);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.getInstance().clearDataCache();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
