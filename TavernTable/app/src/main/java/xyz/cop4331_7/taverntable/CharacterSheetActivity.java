package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CharacterSheetActivity extends AppCompatActivity {
    String characterID, name, userID;
    private Button bCharacterBackground;
    private ImageButton ibStats, ibOther, ibHealth, ibAbilities, ibInventory, ibFeatsTraits, ibPersonality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet);

        // Initialize class variables
        Intent intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        name = intent.getExtras().get("name").toString();
        userID = intent.getExtras().get("userID").toString();
        System.out.println("userID in CharacterSheetActivity: " + userID);
        bCharacterBackground = (Button) findViewById(R.id.bCharacterBackground);
        ibStats = (ImageButton) findViewById(R.id.ibStats);
        ibOther = (ImageButton) findViewById(R.id.ibOther);
        ibHealth = (ImageButton) findViewById(R.id.ibHealth);
        ibAbilities = (ImageButton) findViewById(R.id.ibAbilities);
        ibInventory = (ImageButton) findViewById(R.id.ibInventory);
        ibFeatsTraits = (ImageButton) findViewById(R.id.ibFeatsTraits);
        ibPersonality = (ImageButton) findViewById(R.id.ibPersonality);

        // Set button text to character name
        bCharacterBackground.setText(name);

        // Configure buttons to start their respective activities
        configureButtons();
    }

    public void configureButtons() {
        // Add an onClickListener to the button and configure it to start the character background
        // activity when pressed.
        bCharacterBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetCharacterBackgroundActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the stats activity when
        // pressed.
        ibStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetStatsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the activity for the
        // other section of the character sheet when pressed.
        ibOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetOtherActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the health activity when
        // pressed.
        ibHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetHealthActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the abilities activity
        // when pressed.
        ibAbilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetAbilitiesActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the inventory activity
        // when pressed.
        ibInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetInventoryActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the personality activity
        // when pressed.
        ibPersonality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetPersonalityActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Add an onClickListener to the button and configure it to start the Feats and Traits
        // activity when pressed.
        ibFeatsTraits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharacterSheetActivity.this, SheetFeaturesActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("characterID", characterID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
