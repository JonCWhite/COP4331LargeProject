package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class sheetPersonalityActivity extends AppCompatActivity {

    EditText personality, ideals, bonds, flaws;

    Intent intent;
    int characterID;
    static final String urlGet = "http://cop4331-7.xyz/getFlavorBox.php";
    static final String urlSet = "http://cop4331-7.xyz/setFlavorBox.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_personality);

        personality = (EditText) findViewById(R.id.etTraits);
        ideals = (EditText) findViewById(R.id.etIdeals);
        bonds = (EditText) findViewById(R.id.etBonds);
        flaws = (EditText) findViewById(R.id.etFlaws);

        Button button = (Button) findViewById(R.id.bSheetFeatures);

        //get the character ID
        intent = getIntent();
        characterID = intent.getIntExtra("characterID",-1);

        //if there was a character ID, go get its notes
        if(characterID > -1) {
            getTraits(characterID);



        }
        //when button gets pressed, go save the data
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String personData = personality.getText().toString();
                String idealsData = ideals.getText().toString();
                String bondsData = bonds.getText().toString();
                String flawsData = flaws.getText().toString();

                setTraits(characterID, personData, idealsData, bondsData, flawsData);
            }
        });

    }//End OnCreate()

    private void getTraits(final int characterID){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONArray jsonResponse = new JSONArray(response);

                            String personalityText = jsonResponse.getJSONObject(0).getString("personality"),
                                    idealsText = jsonResponse.getJSONObject(0).getString("ideals"),
                                    bondsText = jsonResponse.getJSONObject(0).getString("bonds"),
                                    flawsText = jsonResponse.getJSONObject(0).getString("flaws"),
                                    error = jsonResponse.getJSONObject(0).getString("error");

                            if(error.equals("")){

                                //success
                                personality.setText(personalityText);
                                ideals.setText(idealsText);
                                bonds.setText(bondsText);
                                flaws.setText(flawsText);
                            }

                            else{

                                //fail
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

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }//end getTraits()

    private void setTraits(final int characterID, final String personData, final String idealsData, final String bondsData, final String flawsData){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            String error = jsonResponse.getString("error");

                            if(error.equals("")){

                                //success
                            }

                            else{

                                //fail
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
                params.put("personality", personData);
                params.put("ideals", idealsData);
                params.put("bonds", bondsData);
                params.put("flaws", flawsData);

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);


    }//end setTraits()


}//end class
