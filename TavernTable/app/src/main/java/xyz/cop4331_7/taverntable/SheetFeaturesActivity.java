package xyz.cop4331_7.taverntable;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class SheetFeaturesActivity extends AppCompatActivity {
    Button button;
    EditText content;
    Intent intent;
    RequestQueue queue;
    String characterID, userID;
    static final String urlGet = "http://cop4331-7.xyz/getFeaturesBox.php";
    static final String urlSet = "http://cop4331-7.xyz/setFeaturesBox.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_features);

        content = (EditText) findViewById(R.id.etFeatures);
        button = (Button) findViewById(R.id.bSheetFeatures);
        queue = Volley.newRequestQueue(getApplicationContext());

        //get the character ID
        intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        userID = intent.getExtras().get("userID").toString();

        getFeatures(characterID, queue);
    }

    private void getFeatures(final String characterID, final RequestQueue queue){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONArray jsonResponse = new JSONArray(response);
                            String feat = jsonResponse.getJSONObject(0).getString("featuresAndTraits"),
                                    responseUserID = jsonResponse.getJSONObject(0).getString("userID");


                            if(!jsonResponse.getJSONObject(0).has("error")){

                                //success
                                content.setText(feat);
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
                                        setFeatures(characterID, queue);
                                    }
                                });
                            }
                            // Otherwise, this user should not be allowed to edit this character
                            // sheet so we remove the submite button.
                            else {
                                LinearLayout parent = (LinearLayout) findViewById(R.id.llFeatures);
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

    private void setFeatures(final String characterID, final RequestQueue queue){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSet,
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
                params.put("characterID", characterID);
                params.put("featuresAndTraits", content.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);
    }


}//end class

