package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class DMSelectionofCampaign extends AppCompatActivity {
    RequestQueue requestQueue;
    String url = "http://cop4331-7.xyz/system/selectDMCampaigns.php";
    Button selectPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmselectionof_campaign);

        selectPlayer = (Button) findViewById(R.id.createCampaignButton);
        Intent getintent = getIntent();
        final String usr = getintent.getExtras().getString("userid");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                            JSONArray dm = jsonResponseObject.getJSONArray("DMNames");
                            JSONArray partySize = jsonResponseObject.getJSONArray("partySizes");
                            JSONArray partyKey = jsonResponseObject.getJSONArray("partyKeys");

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
                                updateScroll("Party Size: " + partyS);
                                String partyK = partyKey.getString(i);
                                updateScroll("Party Key: " + partyK);
                                addButton();
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
                params.put("userID", usr);

                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
        configurecreateCampaign(usr);
    }


    private void updateScroll(String temp)
    {
        LinearLayout selectDMCamps = (LinearLayout) findViewById(R.id.selectDMCamps);
        TextView tv1 = new TextView(this);
        tv1.setText(temp);
        selectDMCamps.addView(tv1);
    }

    private void addButton()
    {
        LinearLayout selectDMCamps = (LinearLayout) findViewById(R.id.selectDMCamps);
        Button b1 = new Button(this);
        b1.setText("Return to Campaign Above");

        selectDMCamps.addView(b1);
    }
    private void configurecreateCampaign(final String id) {
        selectPlayer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createintent = new Intent(DMSelectionofCampaign.this, CreateCampaign.class);
                createintent.putExtra("userid", id );
                startActivity(createintent);
            }
        }));
    }
}
