package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetCharacterBackgroundActivity extends AppCompatActivity {
    Button button;
    EditText etCharacterName, etClass, etLevel, etRace, etBackground, etAlignment, etPlayerName, etEXP;
    Intent intent;
    RequestQueue queue;
    String characterID, userID;
    TextView tvPlayerName;
    static final String urlGet = "http://cop4331-7.xyz/getTopRow.php";
    static final String urlSet = "http://cop4331-7.xyz/setTopRow.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_character_background);

        // Initialize class variables from UI elements
        etCharacterName = (EditText) findViewById(R.id.etCharacterName);
        etClass = (EditText) findViewById(R.id.etClass);
        etLevel = (EditText) findViewById(R.id.etLevel);
        etRace = (EditText) findViewById(R.id.etRace);
        etBackground = (EditText) findViewById(R.id.etBackground);
        etAlignment = (EditText) findViewById(R.id.etAlignment);
        tvPlayerName = (TextView) findViewById(R.id.tvPlayerName);
        etEXP = (EditText) findViewById(R.id.etEXP);
        button = (Button) findViewById(R.id.bSheetBackground);
        queue = Volley.newRequestQueue(getApplicationContext());

        //get the character ID from the intent
        intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        userID = intent.getExtras().get("userID").toString();

        getCharacterBackground(characterID, queue);
    }

    // Loads data from the server into character background fields
    private void getCharacterBackground(final String characterID, final RequestQueue queue){

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
                            String characterName = jsonResponse.getJSONObject(0).getString("characterName"),
                                    characterClass = jsonResponse.getJSONObject(0).getString("class"),
                                    level = jsonResponse.getJSONObject(0).getString("level"),
                                    race = jsonResponse.getJSONObject(0).getString("race"),
                                    background = jsonResponse.getJSONObject(0).getString("background"),
                                    playerName = jsonResponse.getJSONObject(0).getString("playerName"),
                                    alignment = jsonResponse.getJSONObject(0).getString("alignment"),
                                    experiencePoints = jsonResponse.getJSONObject(0).getString("experiencePoints"),
                                    responseUserID = jsonResponse.getJSONObject(0).getString("userID");


                            // If the error JSON response doesn't have an error field, then we know
                            // our data was successfully retrieved.
                            if(!jsonResponse.getJSONObject(0).has("error")){

                                //success
                                etCharacterName.setText(characterName);
                                etClass.setText(characterClass);
                                etLevel.setText(level);
                                etRace.setText(race);
                                etBackground.setText(background);
                                etAlignment.setText(alignment);
                                tvPlayerName.setText(playerName);
                                etEXP.setText(experiencePoints);
                            }
                            else{
                                //fail
                            }


                            if (responseUserID.equals(userID)) {
                                // When the button gets pressed, go save the data. We configure the
                                // button here instead of in OnCreate() to ensure the user's data isn't
                                // accidentally overwritten with placeholder text in the event of a
                                // delayed load.
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        setCharacterBackground(characterID, queue);
                                    }
                                });
                            }
                            // Otherwise, this user should not be allowed to edit this character
                            // sheet so we remove the submite button.
                            else {
                                LinearLayout parent = (LinearLayout) findViewById(R.id.llBackground);
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

    }//end getFeatures

    // Applies any changes made to editable parts of the form to the server.
    private void setCharacterBackground(final String characterID, final RequestQueue queue){

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
                params.put("characterName", etCharacterName.getText().toString());
                params.put("className", etClass.getText().toString());
                params.put("level", etLevel.getText().toString());
                params.put("raceName", etRace.getText().toString());
                params.put("background", etBackground.getText().toString());
                params.put("playerName", tvPlayerName.getText().toString());
                params.put("alignment", etAlignment.getText().toString());
                params.put("expPoints", etEXP.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);
    }
}
