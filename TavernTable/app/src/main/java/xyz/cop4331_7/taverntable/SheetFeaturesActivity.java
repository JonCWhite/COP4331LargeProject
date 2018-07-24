package xyz.cop4331_7.taverntable;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SheetFeaturesActivity extends AppCompatActivity {

    EditText content;
    Intent intent;
    int characterID;
    static final String urlGet = "http://cop4331-7.xyz/GET/getFeaturesAndTraits.php";
    static final String urlSet = "http://cop4331-7.xyz/SET/setFeaturesAndTraits.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_features);

        content = (EditText) findViewById(R.id.etFeatures);
        Button button = (Button) findViewById(R.id.bSheetFeatures);

        //get the character ID
        intent = getIntent();
        characterID = intent.getIntExtra("characterID",-1);

        //if there was a character ID, go get its notes
        if(characterID > -1)
            getFeatures(characterID);

        //when button gets pressed, go save the data
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String data = content.getText().toString();

                setFeatures(characterID, data);
            }
        });

    }

    private void getFeatures(final int characterID){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            String feat = jsonResponse.getString("featuresAndTraits"),
                                    error = jsonResponse.getString("error");

                            if(error.equals("")){

                                //success
                                content.setText(feat);
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

    }//end getFeatures

    private void setFeatures(final int characterID, final String data){

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
                params.put("featuresAndTraits", data);

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
    }


}//end class

