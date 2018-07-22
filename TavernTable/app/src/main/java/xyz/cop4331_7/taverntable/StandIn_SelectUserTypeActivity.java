package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StandIn_SelectUserTypeActivity extends AppCompatActivity {
    Button bDM, bPlayer;
    ImageButton bFriends;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand_in__select_user_type);

        // Initialize userID from intent and button variables from UI
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        bPlayer = (Button) findViewById(R.id.bPlayer);
        bDM = (Button) findViewById(R.id.bDM);
        bFriends = (ImageButton) findViewById(R.id.bFriends);

        // Configure UI buttons.
        configureDMButton();
        configureFriendsButton();
        configurePlayerButton();
    }

    private void configurePlayerButton() {
        // Add an onClickListener to the button and configure it to start the player's version of
        // the campaign select activity when pressed.
        bPlayer.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(StandIn_SelectUserTypeActivity.this, StandIn_CampaignSelectActivity.class);
                     intent.putExtra("userID", userID);
                     startActivity(intent);
                 }
             }
        );
    }

    private void configureDMButton() {
        // Add an onClickListener to the button and configure it to start the DM's version of
        // the campaign select activity when pressed.
        bDM.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(StandIn_SelectUserTypeActivity.this, StandIn_CampaignSelectActivity.class);
                   intent.putExtra("userID", userID);
                   startActivity(intent);
               }
           }
        );
    }

    private void configureFriendsButton() {
        // Add an onClickListener to the button and configure it to start the friends list activity
        // when pressed.
        bFriends.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(StandIn_SelectUserTypeActivity.this, FriendsListActivity.class);
                                       intent.putExtra("userID", userID);
                                       startActivity(intent);
                                   }
                               }
        );
    }
}
