package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class StandIn_CreateCharacter extends AppCompatActivity {
    // Variable declarations
    private Button bRollDie, bSubmit; // Button for roll and submit
    private Random rng20 = new Random(); // Random number generator
    private int randomNum; // Actual random number generated
    private int count = 0; // Keep track of amount of rolls clicked

    // To get the TextViews and save onto the Strings for the next intent
    private TextView tvRoll1, tvRoll2, tvRoll3, tvRoll4, tvRoll5, tvRoll6;
    private String roll1, roll2, roll3, roll4, roll5, roll6;

    // Spinners (drop down) for race and class
    Spinner raceSpinner;
    Spinner classSpinner;

    // URL where php is stored
    String CHARACTER_URL="http://cop4331-7.xyz/system/CharacterSheet.php";

    // ArrayList for race names and class names
    ArrayList<String> raceName;
    ArrayList<String> className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand_in__create_character);
        raceName=new ArrayList<>();
        raceSpinner=(Spinner)findViewById(R.id.sRace);
        loadSpinnerData(CHARACTER_URL);

        // Find Buttons by id
        bRollDie = (Button) findViewById(R.id.bRollDie);
        bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setEnabled(false); // Disable Submit button until 6th roll

        // Get TextView id's
        tvRoll1 = (TextView) findViewById(R.id.tvRoll1);
        tvRoll2 = (TextView) findViewById(R.id.tvRoll2);
        tvRoll3 = (TextView) findViewById(R.id.tvRoll3);
        tvRoll4 = (TextView) findViewById(R.id.tvRoll4);
        tvRoll5 = (TextView) findViewById(R.id.tvRoll5);
        tvRoll6 = (TextView) findViewById(R.id.tvRoll6);

        // Set up the dice roll and submit buttons
        configureRollDie();
        configureSubmit();

        // If the race spinner is selected, show the selected spinner
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String race = raceSpinner.getItemAtPosition(raceSpinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),race,Toast.LENGTH_LONG).show();

                // String charClass = classSpinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                // DO Nothing here
            }
        });

    }

    // This function loads the Race spinner with the information from the database
    private void loadSpinnerData(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("raceResult");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String race =jsonObject1.getString("raceName");
                        raceName.add(race);
                    }

                    raceSpinner.setAdapter(new ArrayAdapter<String>(StandIn_CreateCharacter.this, android.R.layout.simple_spinner_dropdown_item, raceName));
                }
                // Catch JSON error
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() // Get Volley errors
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });

        //Attempts to prepare the request for a retry. If there are no more attempts remaining in the
        // request's retry policy, a timeout exception is thrown.
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
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
    public void configureSubmit()
    {
        bSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Save the rolls onto a String
                roll1 = tvRoll1.getText().toString();
                roll2 = tvRoll2.getText().toString();
                roll3 = tvRoll3.getText().toString();
                roll4 = tvRoll4.getText().toString();
                roll5 = tvRoll5.getText().toString();
                roll6 = tvRoll6.getText().toString();

                // Create new intent
                Intent intent = new Intent(StandIn_CreateCharacter.this, StandIn_SetCharacterRolls.class);

                // Push the rolled numbers onto the next intent
                intent.putExtra("roll1", roll1);
                intent.putExtra("roll2", roll2);
                intent.putExtra("roll3", roll3);
                intent.putExtra("roll4", roll4);
                intent.putExtra("roll5", roll5);
                intent.putExtra("roll6", roll6);

                // Start the new intent
                StandIn_CreateCharacter.this.startActivity(intent);


            }
        });
    }
}
