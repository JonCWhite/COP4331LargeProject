package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;


public class SheetPersonalityActivity extends AppCompatActivity {
    Button button;
    EditText etTraits, etIdeals, etBonds, etFlaws;
    Intent intent;
    RequestQueue queue;
    String characterID, userID;
    static final String urlGet = "http://cop4331-7.xyz/getFlavorBox.php";
    static final String urlSet = "http://cop4331-7.xyz/setFlavorBox.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_personality);

        // Initialize class variables from UI elements
        etTraits = (EditText) findViewById(R.id.etTraits);
        etIdeals = (EditText) findViewById(R.id.etIdeals);
        etBonds = (EditText) findViewById(R.id.etBonds);
        etFlaws = (EditText) findViewById(R.id.etFlaws);
        button = (Button) findViewById(R.id.bSheetPersonality);
        queue = Volley.newRequestQueue(getApplicationContext());

        //get the character ID from the intent
        intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        userID = intent.getExtras().get("userID").toString();

        getPersonality(characterID, queue);
    }

    // Loads data from the server into character background fields
    private void getPersonality(final String characterID, final RequestQueue queue){

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
                            String personality = jsonResponse.getJSONObject(0).getString("personality"),
                                    ideals = jsonResponse.getJSONObject(0).getString("ideals"),
                                    bonds = jsonResponse.getJSONObject(0).getString("bonds"),
                                    flaws = jsonResponse.getJSONObject(0).getString("flaws"),
                                    responseUserID = jsonResponse.getJSONObject(0).getString("userID");

                            // If the error JSON response doesn't have an error field, then we know
                            // our data was successfully retrieved.
                            if(!jsonResponse.getJSONObject(0).has("error")){

                                //success
                                etTraits.setText(personality);
                                etIdeals.setText(ideals);
                                etBonds.setText(bonds);
                                etFlaws.setText(flaws);
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
                                        setPersonality(characterID, queue);
                                    }
                                });
                            }
                            // Otherwise, this user should not be allowed to edit this character
                            // sheet so we remove the submite button.
                            else {
                                LinearLayout parent = (LinearLayout) findViewById(R.id.llPersonality);
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
    private void setPersonality(final String characterID, final RequestQueue queue){

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
                params.put("personality", etTraits.getText().toString());
                params.put("ideals", etIdeals.getText().toString());
                params.put("bonds", etBonds.getText().toString());
                params.put("flaws", etFlaws.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);
    } // end setPersonality
}
