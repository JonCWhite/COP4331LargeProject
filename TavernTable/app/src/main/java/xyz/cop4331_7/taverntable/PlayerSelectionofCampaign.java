package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class PlayerSelectionofCampaign extends AppCompatActivity {
    RequestQueue requestQueue;
    static final String url = "http://cop4331-7.xyz/system/selectCampaign.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selectionof_campaign);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Intent getintent = getIntent();
        final String user = getintent.getExtras().getString("userid");

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponseObject = new JSONObject(response);
                            //go get character names
                            JSONArray characterNames = jsonResponseObject.getJSONArray("characterNames");
                            JSONArray campaignNames = jsonResponseObject.getJSONArray("campaignNames");
                            JSONArray campaignIDs = jsonResponseObject.getJSONArray("campaignIDs");
                            JSONArray characterIDs = jsonResponseObject.getJSONArray("characterID");
                            JSONArray dm = jsonResponseObject.getJSONArray("DMNames");
                            JSONArray partySize = jsonResponseObject.getJSONArray("partySizes");

                            for(int i=0; i<characterNames.length(); i++)
                            {
                                String charName = characterNames.getString(i);
                                updateScroll("Character Name: " + charName);
                                String dmS = dm.getString(i);
                                updateScroll("DM: " + dmS);
                                String campName = campaignNames.getString(i);
                                updateScroll("Campaign Name: " + campName);
                                String campID = campaignIDs.getString(i);
                                updateScroll("Campaign ID: " + campID);
                                String partyS = partySize.getString(i);
                                String characterID = characterIDs.getString(i);
                                updateScroll("Party Size: " + partyS);
                                addButton(user,campID, characterID);
                            }
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
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // POST parameters
                Intent intent = getIntent();
                String user = intent.getExtras().getString("userid");
                params.put("userID", user);

                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
        configurejoinCampaign(user);
    }


    private void updateScroll(String temp)
    {
        LinearLayout selectDMCamps = (LinearLayout) findViewById(R.id.selectCamps);
        TextView tv1 = new TextView(this);
        tv1.setText(temp);
        selectDMCamps.addView(tv1);
    }

    private void addButton(final String id, final String campID, final String characterID)
    {
        LinearLayout selectCamps = (LinearLayout) findViewById(R.id.selectCamps);
        Button b1 = new Button(this);
        b1.setText("Return to Campaign Above");
        b1.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent createintent = new Intent(PlayerSelectionofCampaign.this, PlayerSessionActivity.class);
            createintent.putExtra("userid", id );
            createintent.putExtra("campaignid", campID );
            createintent.putExtra("characterid", characterID );
            startActivity(createintent);
        }
    }));
        selectCamps.addView(b1);
    }
    private void configurejoinCampaign(final String id) {
        Button joinPlayer = findViewById(R.id.createPlayerCampaign);
        joinPlayer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createintent = new Intent(PlayerSelectionofCampaign.this, JoinCampaign.class);
                createintent.putExtra("userid", id );
                startActivity(createintent);
            }
        }));
    }

}

