package xyz.cop4331_7.taverntable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinCampaign extends Activity {
    EditText etPartyKey;
    //RequestQueue requestQueue;
    String url = "http://cop4331-7.xyz/system/validatePartyKey.php";
    Button joinCampaignButton;
    String campID;
    String pKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_campaign);

        etPartyKey = (EditText) findViewById(R.id.etPartyKey);
        joinCampaignButton = (Button) findViewById(R.id.joinCampaign);
        Intent getintent = getIntent();

        final String user = getintent.getExtras().getString("userid");
        configureJoinCampaign(user);
    }

    public void configureJoinCampaign(final String user)
    {
        joinCampaignButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pKey = etPartyKey.getText().toString();

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String campaignID = jsonResponse.getString("campaignID");
                                    String error = jsonResponse.getString("error");

                                    if(error.equals("Invalid party key"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinCampaign.this);
                                        builder.setMessage("Invalid party key.")
                                                .setNegativeButton("Try Again", null)
                                                .create().show();

                                    }
                                    else if(error.equals("You are a DM in this campaign"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinCampaign.this);
                                        builder.setMessage("You are a DM in this campaign.")
                                                .setNegativeButton("Try Again", null)
                                                .create().show();
                                    }
                                    else if(error.equals("You already joined this campaign"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinCampaign.this);
                                        builder.setMessage("You already joined this campaign.")
                                                .setNegativeButton("Try Again", null)
                                                .create().show();
                                    }
                                    else
                                    {

                                        Intent intent = new Intent(JoinCampaign.this, createCharacter.class);
                                        intent.putExtra("userid", user);
                                        intent.putExtra("campaignid", campaignID);
                                        startActivity(intent);
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
                        params.put("userID", user);
                        params.put("partyKey", pKey);

                        return params;
                    }
                };

                Volley.newRequestQueue(getApplicationContext()).add(postRequest);
            }
        });
    }






    private void updateScroll(String temp)
    {
        LinearLayout selectDMCamps = (LinearLayout) findViewById(R.id.scroll);
        TextView tv1 = new TextView(this);
        tv1.setText(temp);
        selectDMCamps.addView(tv1);
    }
}
