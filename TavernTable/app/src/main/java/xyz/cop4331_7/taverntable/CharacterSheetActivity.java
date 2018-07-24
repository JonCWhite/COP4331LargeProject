package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class CharacterSheetActivity extends AppCompatActivity {
    String characterID, name;
    private Button bCharacterBackground;
    private ImageButton ibStats, ibOther, ibHealth, ibMagic, ibInventory, ibFeatsTraits, ibPersonality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet);

        // Initialize variables
        Intent intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        name = intent.getExtras().get("name").toString();
        bCharacterBackground = (Button) findViewById(R.id.bCharacterBackground);

        // Set button text to character name
        bCharacterBackground.setText(name);
    }
}
