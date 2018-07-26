package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BackupDice extends AppCompatActivity {
    String campaignID, username;
    TextView tvRollResult;
    // RequestQueue requestQueue;
    //private static final String LOGIN_REQUEST_URL = "http://cop4331-7.xyz/tavernTable/Dice.php";
    public int value;
    Random rand = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_dice);

        // Initialize class variables
        username = getIntent().getExtras().get("user_name").toString();
        campaignID = getIntent().getExtras().get("campaign_name").toString();
        tvRollResult = (TextView) findViewById(R.id.tvRollResult);

        // Configure die buttons
        configureD4();
        configureD6();
        configureD8();
        configureD10();
        configureD20();
        configureD100();
    }

    public void configureD4() {
        ImageButton D4 = (ImageButton) findViewById(R.id.ibBackupD4);
        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(3)+1;
                postRoll(username + " rolled a " + value + " using a d4.");
                tvRollResult.setText("You rolled a " + value + " using a d4.");
            }
        });
    }

    public void configureD6()
    {
        ImageButton D6 =(ImageButton) findViewById(R.id.ibBackupD6);
        D6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(5)+1;
                postRoll(username + " rolled a " + value + " using a d6.");
                tvRollResult.setText("You rolled a " + value + " using a d6.");
            }
        });

    }
    public void configureD8()
    {
        ImageButton D8 =(ImageButton) findViewById(R.id.ibBackupD8);
        D8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(7)+1;
                postRoll(username + " rolled a " + value + " using a d8.");
                tvRollResult.setText("You rolled a " + value + " using a d8.");;
            }
        });

    }
    public void configureD10()
    {
        ImageButton D10 =(ImageButton) findViewById(R.id.ibBackupD10);
        D10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(9)+1;
                postRoll(username + " rolled a " + value + " using a d10.");
                tvRollResult.setText("You rolled a " + value + " using a d10.");
            }
        });

    }
    public void configureD20()
    {
        ImageButton D20 =(ImageButton) findViewById(R.id.ibBackupD20);
        D20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(19)+1;
                postRoll(username + " rolled a " + value + " using a d20.");
                tvRollResult.setText("You rolled a " + value + " using a d20.");
            }
        });

    }
    public void configureD100()
    {
        ImageButton D100 =(ImageButton) findViewById(R.id.ibBackupD100);
        D100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(100);
                postRoll(username + " rolled a " + value + " using a d100.");
                tvRollResult.setText("You rolled a " + value + " using a d100.");
            }
        });

    }

    // Posts player's roll to the chat---so they can't cheat and say they rolled higher!
    public void postRoll(String message) {
        DatabaseReference roomRoot = FirebaseDatabase.getInstance().getReference().child(campaignID);
        String tempKey;
        Map<String, Object> messageEntry = new HashMap<String, Object>();
        // Create a new entry for the message in the database with a random key.
        tempKey = roomRoot.push().getKey();
        roomRoot.updateChildren(messageEntry);

        // Create a reference to the entry we just created.
        DatabaseReference messageRoot = roomRoot.child(tempKey);
        Map<String, Object> messageContent = new HashMap<String, Object>();
        messageContent.put("name", "System");
        messageContent.put("message", message);

        // Update the database.
        messageRoot.updateChildren(messageContent);
    }
}
