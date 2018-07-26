package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class createCharacter extends AppCompatActivity
{
    static final String url = "http://cop4331-7.xyz/initializeCharacter.php";
    // Variable declarations
    private Button bRollDie, bSubmit; // Button for roll and submit
    private Random rng20 = new Random(); // Random number generator
    private int randomNum; // Actual random number generated
    private int count = 0; // Keep track of amount of rolls clicked

    // To get the TextViews and save onto the Strings for the next intent
    private TextView tvRoll1, tvRoll2, tvRoll3, tvRoll4, tvRoll5, tvRoll6;
    private EditText etCharName;
    private String roll1, roll2, roll3, roll4, roll5, roll6;
    private String charName;

    // Spinners (drop down) for race and class
    Spinner raceSpinner;
    Spinner classSpinner;
    private String raceSelected, classSelected;


    // Class and Race names
    String[] nameOfRace = {"Dark Elf", "Dragonborn", "Forest Gnome", "Half Orc", "Half-Elf", "High Elf"
            ,"Hill Dwarf", "Human", "Lightfoot Halfling", "Mountain Dwarf", "Rock Gnome"
            ,"Stout Halfling", "Tiefling", "Wood Elf"};
    String[] nameOfClass = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin"
            ,"Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard" };

    // Get user ID



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Get previous intent to get the userID and campaignID
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userid");
        final String campaignID =  intent.getStringExtra("campaignid");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        raceSpinner = (Spinner)findViewById(R.id.sRace);
        classSpinner = (Spinner) findViewById(R.id.sClass);




        // Find Buttons by id
        bRollDie = (Button) findViewById(R.id.bRollDie);
        bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setEnabled(false); // Disable Submit button until 6th roll


        etCharName = (EditText) findViewById(R.id.etCharName);

        // Get TextView id's
        tvRoll1 = (TextView) findViewById(R.id.tvRoll1);
        tvRoll2 = (TextView) findViewById(R.id.tvRoll2);
        tvRoll3 = (TextView) findViewById(R.id.tvRoll3);
        tvRoll4 = (TextView) findViewById(R.id.tvRoll4);
        tvRoll5 = (TextView) findViewById(R.id.tvRoll5);
        tvRoll6 = (TextView) findViewById(R.id.tvRoll6);

        // Set up the dice roll and submit buttons
        configureRollDie();
        configureSubmit(userID, campaignID);
        configureSubmitManual(userID, campaignID);



        // If the race spinner is selected, show the selected spinner
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                //String race = raceSpinner.getItemAtPosition(raceSpinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),nameOfRace[position],Toast.LENGTH_LONG).show();
                //android.R.layout.simple_spinner_dropdown_item, raceName
                // String charClass = classSpinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                // DO Nothing here
            }
        });
        ArrayAdapter arrayRace = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nameOfRace);
        arrayRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(arrayRace);


        // // If the class spinner is selected, show the selected spinner
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                //String race = raceSpinner.getItemAtPosition(raceSpinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),nameOfClass[position],Toast.LENGTH_LONG).show();



                //android.R.layout.simple_spinner_dropdown_item, raceName
                // String charClass = classSpinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                // DO Nothing here
            }
        });

        ArrayAdapter arrayClass = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nameOfClass);
        arrayRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(arrayClass);
    }



    // When die roll button is clicked, generate a random number
    public void configureRollDie()
    {
        bRollDie.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Random number
                randomNum = rng20.nextInt(20) + 1;

                // Increase count to keep track of how many times the button was clicked
                count++;

                // Maximum 6 rolls
                if(count <= 6)
                {
                    switch(count)
                    {
                        // Set text on tvRoll1 for first roll
                        case 1:
                            tvRoll1.setText(String.valueOf(randomNum));
                            break;
                        // Set text on tvRoll2 for 2nd roll
                        case 2:
                            tvRoll2.setText(String.valueOf(randomNum));
                            break;
                        // Set text on tvRoll3 for 4rd roll
                        case 3:
                            tvRoll3.setText(String.valueOf(randomNum));
                            break;
                        // Set text on tvRoll4 for 4th roll
                        case 4:
                            tvRoll4.setText(String.valueOf(randomNum));
                            break;
                        // Set text on tvRoll5 for 5th roll
                        case 5:
                            tvRoll5.setText(String.valueOf(randomNum));
                            break;
                        // Set text on tvRoll6 for final roll
                        case 6:
                            tvRoll6.setText(String.valueOf(randomNum));
                            bRollDie.setEnabled(false); // Disable roll button
                            bSubmit.setEnabled(true); // Enable submit button
                            break;
                    }
                }


            }
        });
    }

    // This button submits your current configuration to go onto the next intent (setCharacterRolls)
    public void configureSubmit(final String userID, final String campaignID)
    {
        bSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // If character name is less than 4 characters, try again
                if (etCharName.getText().toString().trim().length() < 4) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(createCharacter.this);
                    builder.setMessage("Character name must at least 4 characters.")
                            .setNegativeButton("Try Again", null)
                            .create().show();
                }
                else
                {
                    // Get the character name
                    charName = etCharName.getText().toString();


                    // Get Race and Class selected
                    raceSelected = raceSpinner.getSelectedItem().toString();
                    classSelected = classSpinner.getSelectedItem().toString();

                    // Save the rolls onto a String
                    roll1 = tvRoll1.getText().toString();
                    roll2 = tvRoll2.getText().toString();
                    roll3 = tvRoll3.getText().toString();
                    roll4 = tvRoll4.getText().toString();
                    roll5 = tvRoll5.getText().toString();
                    roll6 = tvRoll6.getText().toString();

                    // Get all the inputs and save to their respective Strings
                    final String inputSTR = roll1;
                    final String inputDEX = roll2;
                    final String inputINT = roll3;
                    final String inputWIS = roll4;
                    final String inputCHA = roll5;
                    final String inputCON = roll6;

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String charID = jsonResponse.getString("characterID");

                                Intent intent = new Intent(createCharacter.this, PlayerSessionActivity.class);
                                intent.putExtra("userid", userID);
                                intent.putExtra("campaignid", campaignID);
                                intent.putExtra("characterid", charID);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<>();

                            // POST parameters
                            params.put("Name", charName);
                            params.put("raceName", raceSelected);
                            params.put("className", classSelected);
                            params.put("rollDex", inputDEX);
                            params.put("rollCha", inputCHA);
                            params.put("rollStr", inputSTR);
                            params.put("rollCon", inputCON);
                            params.put("rollInt", inputINT);
                            params.put("rollWis", inputWIS);
                            params.put("userID", userID);
                            params.put("campaignID", campaignID);

                            return params;
                        }
                    };
                    Volley.newRequestQueue(getApplicationContext()).add(postRequest);

                }

            }
        });
    }

    private void updateScroll(String temp)
    {
        LinearLayout selectDMCamps = (LinearLayout) findViewById(R.id.scroll);
        TextView tv1 = new TextView(this);
        tv1.setText(temp);
        selectDMCamps.addView(tv1);
    }

    private void configureSubmitManual(final String userID, final String campaignID)
    {
        Button manual = findViewById(R.id.enterManual);
        manual.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // If character name is less than 4 characters, try again
                if (etCharName.getText().toString().trim().length() < 4) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(createCharacter.this);
                    builder.setMessage("Character name must at least 4 characters.")
                            .setNegativeButton("Try Again", null)
                            .create().show();
                }
                else
                {
                    // Get the character name
                    charName = etCharName.getText().toString();


                    // Get Race and Class selected
                    raceSelected = raceSpinner.getSelectedItem().toString();
                    classSelected = classSpinner.getSelectedItem().toString();

                    // Save the rolls onto a String
                    roll1 = tvRoll1.getText().toString();
                    roll2 = tvRoll2.getText().toString();
                    roll3 = tvRoll3.getText().toString();
                    roll4 = tvRoll4.getText().toString();
                    roll5 = tvRoll5.getText().toString();
                    roll6 = tvRoll6.getText().toString();

                    // Create new intent
                    Intent intent = new Intent(createCharacter.this, setCharacterRolls.class);

                    // Push the rolled numbers onto the next intent
                    intent.putExtra("roll1", roll1);
                    intent.putExtra("roll2", roll2);
                    intent.putExtra("roll3", roll3);
                    intent.putExtra("roll4", roll4);
                    intent.putExtra("roll5", roll5);
                    intent.putExtra("roll6", roll6);

                    intent.putExtra("userid", userID);
                    intent.putExtra("campaignid", campaignID);

                    // Push characer name, race selected and class selected
                    intent.putExtra("charName", charName);
                    intent.putExtra("raceSelected", raceSelected);
                    intent.putExtra("classSelected", classSelected);

                    // Start the new intent
                    createCharacter.this.startActivity(intent);
                }

            }
        });
    }


}