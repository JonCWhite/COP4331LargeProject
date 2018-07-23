package xyz.cop4331_7.taverntable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PlayerorDM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeror_dm);

        configureselectPlayer();
        configureselectDM();

    }

    private void configureselectPlayer() {
        Button selectPlayer = (Button) findViewById(R.id.selectPlayer);
        selectPlayer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(xyz.cop4331_7.taverntable.PlayerorDM.this, PlayerSelectionofCampaign.class));
            }
        }));
    }

    private void configureselectDM() {
        Button selectDM = (Button) findViewById(R.id.selectDM);
        selectDM.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(xyz.cop4331_7.taverntable.PlayerorDM.this, DMSelectionofCampaign.class));
            }
        }));
    }
}

