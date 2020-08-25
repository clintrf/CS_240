package familymap.client.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import familymap.client.Model.DataCache;
import familymap.client.R;

public class SettingsActivity extends AppCompatActivity {

    Context here = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RelativeLayout logoutView = (RelativeLayout) findViewById(R.id.logoutView);

        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.getInstance().clearClientForTesting();
                Intent intent = new Intent(here, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
