package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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

public class SheetHealthActivity extends AppCompatActivity {
    Button button;
    EditText etArmorClass, etInitiave, etSpeed, etMaxHP, etCurrentHP, etTempHP, etHitDie;
    Intent intent;
    RequestQueue queue;
    String characterID, userID;
    static final String urlGet = "http://cop4331-7.xyz/getHpBox.php";
    static final String urlSet = "http://cop4331-7.xyz/setHpBox.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_health);

        // Initialize class variables from UI elements
        etArmorClass = (EditText) findViewById(R.id.etArmorClass);
        etInitiave = (EditText) findViewById(R.id.etInitiative);
        etSpeed = (EditText) findViewById(R.id.etSpeed);
        etMaxHP = (EditText) findViewById(R.id.etMaxHP);
        etCurrentHP = (EditText) findViewById(R.id.etCurrentHP);
        etTempHP = (EditText) findViewById(R.id.etTempHP);
        etHitDie = (EditText) findViewById(R.id.etHitDice);
        button = (Button) findViewById(R.id.bSheetHealth);
        queue = Volley.newRequestQueue(getApplicationContext());

        //get the character ID from the intent
        intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        userID = intent.getExtras().get("userID").toString();

        getHealth(characterID, queue);
    }

    // Loads data from the server into character health fields
    private void getHealth(final String characterID, final RequestQueue queue){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            // Read server response into strings
                            final JSONArray jsonResponse = new JSONArray(response);
                            String armorClass = jsonResponse.getJSONObject(0).getString("armorClass"),
                                    initiative = jsonResponse.getJSONObject(0).getString("initiative"),
                                    speed = jsonResponse.getJSONObject(0).getString("speed"),
                                    currentHP = jsonResponse.getJSONObject(0).getString("currentHP"),
                                    maxHP = jsonResponse.getJSONObject(0).getString("maxHP"),
                                    tempHP = jsonResponse.getJSONObject(0).getString("tempHP"),
                                    hitDie = jsonResponse.getJSONObject(0).getString("hitDie"),
                                    responseUserID = jsonResponse.getJSONObject(0).getString("userID");


                            // If the error JSON response doesn't have an error field, then we know
                            // our data was successfully retrieved.
                            if(!jsonResponse.getJSONObject(0).has("error")){

                                //success
                                etArmorClass.setText(armorClass);
                                etInitiave.setText(initiative);
                                etSpeed.setText(speed);
                                etMaxHP.setText(maxHP);
                                etCurrentHP.setText(currentHP);
                                etTempHP.setText(tempHP);
                                etHitDie.setText(hitDie);
                            }
                            else{
                                //fail
                            }

                            // When the button gets pressed, go save the data. We configure the
                            // button here instead of in OnCreate() to ensure the user's data isn't
                            // accidentally overwritten with placeholder text in the event of a
                            // delayed load.
                            System.out.println("responseUserID: " + responseUserID + ", userID: " + userID);
                            if (responseUserID.equals(userID)) {
                                // When the button gets pressed, go save the data. We configure the
                                // button here instead of in OnCreate() to ensure the user's data isn't
                                // accidentally overwritten with placeholder text in the event of a
                                // delayed load.
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        setHealth(characterID, queue);
                                    }
                                });
                            }
                            // Otherwise, this user should not be allowed to edit this character
                            // sheet so we remove the submite button.
                            else {
                                LinearLayout parent = (LinearLayout) findViewById(R.id.llHealthBottom);
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

    }//end getHealth

    // Applies any changes made to editable parts of the form to the server.
    private void setHealth(final String characterID, final RequestQueue queue){

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
                params.put("armorClass", etArmorClass.getText().toString());
                params.put("initiative", etInitiave.getText().toString());
                params.put("speed", etSpeed.getText().toString());
                params.put("maxHP", etMaxHP.getText().toString());
                params.put("currentHP", etCurrentHP.getText().toString());
                params.put("tempHP", etTempHP.getText().toString());
                params.put("hitDie", etHitDie.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);
    } // end setHealth
}
