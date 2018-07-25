package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SheetStatsActivity extends AppCompatActivity {
    Button button;
    EditText etStr, etDex, etCon, etInt, etWis, etCha, etPassiveWisdom, etProBono, etInspiration, etSavingThrows, etProficiencies;
    Intent intent;
    RequestQueue queue;
    String characterID, userID;
    static final String urlGet = "http://cop4331-7.xyz/getMainRectangle.php";
    static final String urlSet = "http://cop4331-7.xyz/setMainRectangle.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_stats);

        // Initialize class variables from UI elements
        etStr = (EditText) findViewById(R.id.etStr);
        etDex = (EditText) findViewById(R.id.etDex);
        etCon = (EditText) findViewById(R.id.etCon);
        etInt = (EditText) findViewById(R.id.etInt);
        etWis = (EditText) findViewById(R.id.etWis);
        etCha = (EditText) findViewById(R.id.etCha);
        etProBono = (EditText) findViewById(R.id.etProBono);
        etPassiveWisdom = (EditText) findViewById(R.id.etPassiveWisdom);
        etInspiration = (EditText) findViewById(R.id.etInspiration);
        etSavingThrows = (EditText) findViewById(R.id.etSavingThrows);
        etProficiencies = (EditText) findViewById(R.id.etProficiencies);
        button = (Button) findViewById(R.id.bSheetStats);
        queue = Volley.newRequestQueue(getApplicationContext());

        //get the character ID from the intent
        intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        userID = intent.getExtras().get("userID").toString();

        getStats(characterID, queue);
    }

    private void getStats(final String characterID, final RequestQueue queue){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {


                            final JSONArray jsonResponse = new JSONArray(response);
                            String strength = jsonResponse.getJSONObject(0).getString("strength"),
                                    dexterity = jsonResponse.getJSONObject(0).getString("dexterity"),
                                    constitution = jsonResponse.getJSONObject(0).getString("constitution"),
                                    intelligence = jsonResponse.getJSONObject(0).getString("intelligence"),
                                    wisdom = jsonResponse.getJSONObject(0).getString("wisdom"),
                                    charisma = jsonResponse.getJSONObject(0).getString("charisma"),
                                    proficiencyBonus = jsonResponse.getJSONObject(0).getString("proficiencyBonus"),
                                    savingThrows = jsonResponse.getJSONObject(0).getString("savingThrows"),
                                    skillProf = jsonResponse.getJSONObject(0).getString("skillProf"),
                                    passiveWisdom = jsonResponse.getJSONObject(0).getString("passiveWisdom"),
                                    inspiration = jsonResponse.getJSONObject(0).getString("inspiration"),
                                    responseUserID = jsonResponse.getJSONObject(0).getString("userID");


                            // If the error JSON response doesn't have an error field, then we know
                            // our data was successfully retrieved.
                            if(!jsonResponse.getJSONObject(0).has("error")){

                                //success
                                etStr.setText(strength);
                                etDex.setText(dexterity);
                                etCon.setText(constitution);
                                etInt.setText(intelligence);
                                etWis.setText(wisdom);
                                etCha.setText(charisma);
                                etProBono.setText(proficiencyBonus);
                                etSavingThrows.setText(savingThrows);
                                etProficiencies.setText(skillProf);
                                etPassiveWisdom.setText(passiveWisdom);
                                etInspiration.setText(inspiration);
                            }
                            else{
                                //fail
                            }

                            // When the button gets pressed, go save the data. We configure the
                            // button here instead of in OnCreate() to ensure the user's data isn't
                            // accidentally overwritten with placeholder text in the event of a
                            // delayed load.
                            if (responseUserID.equals(userID)) {
                                // When the button gets pressed, go save the data. We configure the
                                // button here instead of in OnCreate() to ensure the user's data isn't
                                // accidentally overwritten with placeholder text in the event of a
                                // delayed load.
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        setStats(characterID, queue);
                                    }
                                });
                            }
                            // Otherwise, this user should not be allowed to edit this character
                            // sheet so we remove the submite button.
                            else {
                                RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlStats);
                                parent.removeView(button);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                // POST params
                params.put("characterID", String.valueOf(characterID));

                return params;
            }
        };

        queue.add(postRequest);

    }//end getStats

    private void setStats(final String characterID, final RequestQueue queue){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            if (!response.equals("")) {
                                JSONObject jsonResponse = new JSONObject(response);
                                String error = jsonResponse.getString("error");
                            }

                            /*if(error.equals("")){

                                //success
                            }

                            else{

                                //fail
                            }*/

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // POST params
                params.put("characterID", characterID);
                params.put("strength", etStr.getText().toString());
                params.put("dexterity", etDex.getText().toString());
                params.put("constitution", etCon.getText().toString());
                params.put("intelligence", etInt.getText().toString());
                params.put("wisdom", etWis.getText().toString());
                params.put("charisma", etCha.getText().toString());
                params.put("proficiencyBonus", etProBono.getText().toString());
                params.put("savingThrows", etSavingThrows.getText().toString());
                params.put("skillProf", etProficiencies.getText().toString());
                params.put("passiveWisdom", etPassiveWisdom.getText().toString());
                params.put("inspiration", etInspiration.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);
    } // end setStats
}
