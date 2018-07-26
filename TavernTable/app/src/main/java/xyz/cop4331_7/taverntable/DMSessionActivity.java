package xyz.cop4331_7.taverntable;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DMSessionActivity extends AppCompatActivity {
    String user_name, campaign_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmsession);

        // Initialize user and campaign names.
        Intent intent = getIntent();
        user_name = intent.getExtras().getString("userid");
        campaign_name =  intent.getExtras().getString("campaignid");

        // Set FrameLayout to use chat fragment.
        Bundle bundle = new Bundle();
        bundle.putString("userid", user_name);
        bundle.putString("campaignid", campaign_name);
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
        configureRollButton();
    }

    private void configureBattleButton() {
        ImageButton bBattle = (ImageButton) findViewById(R.id.bBattle);
        // Add an onClickListener to the button and configure it to start the sign in activity when
        // pressed.
        bBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DMSessionActivity.this);
                builder.setMessage("Please enter Campaign Name"+ user_name + "campaignID: "+ campaign_name)
                        .setNegativeButton("Try Again", null)
                        .create().show();
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
                //reusing the CharacterNotes activity, but looking at campaign notes

                final Intent intent = new Intent(DMSessionActivity.this, CampaignNotes.class);

                //notes activity accepts integers, need to convert
                int cID = Integer.parseInt(campaign_name);

                intent.putExtra("campaignID", cID);
                startActivity(intent);
            }
        });
    }

    private void configureRollButton() {
        ImageButton bRoll = (ImageButton) findViewById(R.id.bRoll);
        // Add an onClickListener to the button and configure it to start the sign in activity when
        // pressed.
        bRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start next activity (currently a placeholder, will have to make this a popup)
                // startActivity(new Intent(PlayerSessionActivity.this, null));
            }
        });
    }

}
