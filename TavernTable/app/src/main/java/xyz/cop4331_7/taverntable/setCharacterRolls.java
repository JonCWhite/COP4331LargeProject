package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class setCharacterRolls extends AppCompatActivity
{
    // Variable declarations
    private TextView tvNum1, tvNum2, tvNum3, tvNum4, tvNum5, tvNum6;
    private EditText etSTR, etDEX, etINT, etWIS, etCHA, etCON;
    private String charName;
    private String raceSelected, classSelected;
    private String roll1, roll2, roll3, roll4, roll5, roll6;
    private String inputSTR, inputDEX, inputINT, inputWIS, inputCHA, inputCON;
    private Button bSubmit;
    private int posSTR, posDEX, posINT, posWIS, posCHA, posCON;

    // Holds all the rolls in 1 array
    private ArrayList<String> rollsArray = new ArrayList<String>();

    // URL to send request
    static final String SEND_CHARACTER_URL = "http://cop4331-7.xyz/initializeCharacter.php";

    private String userID, campID; // Holds userID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_character_rolls);

        // Get the previous intent
        Intent intent = getIntent();

        // Save the rolls from the previous screen onto a String to use later.
        roll1 = intent.getStringExtra("roll1");
        roll2 = intent.getStringExtra("roll2");
        roll3 = intent.getStringExtra("roll3");
        roll4 = intent.getStringExtra("roll4");
        roll5 = intent.getStringExtra("roll5");
        roll6 = intent.getStringExtra("roll6");

        userID = intent.getStringExtra("userid");
        campID = intent.getStringExtra("campaignid");

        // Get the character name, class and race of the character.
        charName = intent.getStringExtra("charName");
        raceSelected = intent.getStringExtra("raceSelected");
        classSelected = intent.getStringExtra("classSelected");


        // Add the rolls to an array to check with later
        rollsArray.add(roll1);
        rollsArray.add(roll2);
        rollsArray.add(roll3);
        rollsArray.add(roll4);
        rollsArray.add(roll5);
        rollsArray.add(roll6);


        // Get TextView Id's
        tvNum1 = (TextView) findViewById(R.id.tDrag1);
        tvNum2 = (TextView) findViewById(R.id.tDrag2);
        tvNum3 = (TextView) findViewById(R.id.tDrag3);
        tvNum4 = (TextView) findViewById(R.id.tDrag4);
        tvNum5 = (TextView) findViewById(R.id.tDrag5);
        tvNum6 = (TextView) findViewById(R.id.tDrag6);

        // Input Id's
        etSTR = (EditText) findViewById(R.id.etInputSTR);
        etDEX = (EditText) findViewById(R.id.etInputDEX);
        etINT = (EditText) findViewById(R.id.etInputINT);
        etWIS = (EditText) findViewById(R.id.etInputWIS);
        etCHA = (EditText) findViewById(R.id.etInputCHA);
        etCON = (EditText) findViewById(R.id.etInputCON);

        // Set min and maximum number allowed as an input
        etSTR.setFilters(new InputFilter[] {new InputFilterMinMax("1", "20")});
        etDEX.setFilters(new InputFilter[] {new InputFilterMinMax("1", "20")});
        etINT.setFilters(new InputFilter[] {new InputFilterMinMax("1", "20")});
        etWIS.setFilters(new InputFilter[] {new InputFilterMinMax("1", "20")});
        etCHA.setFilters(new InputFilter[] {new InputFilterMinMax("1", "20")});
        etCON.setFilters(new InputFilter[] {new InputFilterMinMax("1", "20")});


        // Set TextView to the roll numbers
        tvNum1.setText(roll1);
        tvNum2.setText(roll2);
        tvNum3.setText(roll3);
        tvNum4.setText(roll4);
        tvNum5.setText(roll5);
        tvNum6.setText(roll6);


        // Set up Submit button
        bSubmit = (Button) findViewById(R.id.bSubmit);


        // TextWatcher shows pop up for valid/invalid inputs
        etSTR.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s)
            {
                if (rollsArray.contains(etSTR.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });

        etDEX.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s)
            {
                if (rollsArray.contains(etDEX.getText().toString()))

                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });

        etINT.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                if (rollsArray.contains(etINT.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });

        etWIS.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                if (rollsArray.contains(etWIS.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });

        etCHA.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                if (rollsArray.contains(etCHA.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });

        etCON.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                if (rollsArray.contains(etCON.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Valid!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });



        // Configures submit button to be used
        configureSubmit();
    }


    // Configures submit button.
    // Sends the request to the server
    public void configureSubmit()
    {
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Check for empty input
                if ((isFieldEmpty(etSTR) || isFieldEmpty(etDEX) || isFieldEmpty(etINT) ||
                        isFieldEmpty(etWIS) || isFieldEmpty(etCHA) || isFieldEmpty(etCON))== true)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(setCharacterRolls.this);
                    builder.setMessage("Input(s) cannot be empty.")
                            .setNegativeButton("Try Again", null)
                            .create().show();
                }
                // Inputs not allowed. They are not from the rolled numbers.
                else
                {
                    // Get all the inputs and save to their respective Strings
                    inputSTR = etSTR.getText().toString();
                    inputDEX = etDEX.getText().toString();
                    inputINT = etINT.getText().toString();
                    inputWIS = etWIS.getText().toString();
                    inputCHA = etCHA.getText().toString();
                    inputCON = etCON.getText().toString();

                    StringRequest postRequest = new StringRequest(Request.Method.POST, SEND_CHARACTER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String charID = jsonResponse.getString("characterID");

                                Intent intent = new Intent(setCharacterRolls.this, PlayerSessionActivity.class);
                                intent.putExtra("userid", userID);
                                intent.putExtra("campaignid", campID);
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
                            params.put("campaignID", campID);

                            return params;
                        }
                    };

                    Volley.newRequestQueue(getApplicationContext()).add(postRequest);
                }



            }
        });
    }


   // This function checks if the EditText field is empty.
   private boolean isFieldEmpty(EditText myeditText) {
       return myeditText.getText().toString().trim().length() == 0;
   }
}
