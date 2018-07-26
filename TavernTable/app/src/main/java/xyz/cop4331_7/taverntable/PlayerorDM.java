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

        // receive userID from SignIn
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("userid");

        configureselectPlayer(userID);
        configureselectDM(userID);

    }

    private void configureselectPlayer(final String id) {
        Button selectPlayer = (Button) findViewById(R.id.selectPlayer);
        selectPlayer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerorDM.this, PlayerSelectionofCampaign.class);
                intent.putExtra("userid", id );
                startActivity(intent);
            }
        }));
    }

    private void configureselectDM(final String id) {
        Button selectDM = (Button) findViewById(R.id.selectDM);
        selectDM.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerorDM.this, DMSelectionofCampaign.class);
                intent.putExtra("userid", id );
                startActivity(intent);            }
        }));
    }
}

