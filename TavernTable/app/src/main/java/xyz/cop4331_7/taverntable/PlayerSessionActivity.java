package xyz.cop4331_7.taverntable;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PlayerSessionActivity extends AppCompatActivity {
    private String user_name, campaign_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_session);

        // Initialize user and campaign names.
        user_name = getIntent().getExtras().get("user_name").toString();
        campaign_name = getIntent().getExtras().get("campaign_name").toString();

        // Set FrameLayout to use chat fragment.
        Bundle bundle = new Bundle();
        bundle.putString("user_name", user_name);
        bundle.putString("campaign_name", campaign_name);
        ChatFragment fragObj = new ChatFragment();
        fragObj.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flChat, fragObj);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

        // Assign button listeners to toolbar buttons
        configureBattleButton();
        configureSheetsButton();
        configureNotesButton();
    }

    private void configureBattleButton() {
        ImageButton bBattle = (ImageButton) findViewById(R.id.bBattle);
        // Add an onClickListener to the button and configure it to start the sign in activity when
        // pressed.
        bBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start next activity (currently a placeholder, will have to make this a popup)
                // startActivity(new Intent(PlayerSessionActivity.this, null));
            }
        });
    }

    private void configureSheetsButton() {
        ImageButton bSheets = (ImageButton) findViewById(R.id.bSheets);
        // Add an onClickListener to the button and configure it to start the sign in activity when
        // pressed.
        bSheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start next activity (currently a placeholder, will have to make this a popup)
                // startActivity(new Intent(PlayerSessionActivity.this, null));
            }
        });
    }

    private void configureNotesButton() {
        ImageButton bNotes = (ImageButton) findViewById(R.id.bNotes);
        // Add an onClickListener to the button and configure it to start the sign in activity when
        // pressed.
        bNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start next activity (currently a placeholder, will have to make this a popup)
                // startActivity(new Intent(PlayerSessionActivity.this, null));
            }
        });
    }
}