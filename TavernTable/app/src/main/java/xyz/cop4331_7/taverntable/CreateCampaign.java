package xyz.cop4331_7.taverntable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class CreateCampaign extends AppCompatActivity {
    EditText etcampaignName;
    RequestQueue requestQueue;
    String url = "http://cop4331-7.xyz/system/createCampaign.php";
    Button selectPlayer;
    String campID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);
        etcampaignName = (EditText) findViewById(R.id.campaignName);
        selectPlayer = (Button) findViewById(R.id.sendCampaignName);
        // POST parameters
        Intent getintent = getIntent();
        final String user = getintent.getExtras().getString("userid");
        configurecreateCampaign(user);
}

    private void updateScroll(String temp)
    {
        LinearLayout selectDMCamps = (LinearLayout) findViewById(R.id.createdCampaigns);
        TextView tv1 = new TextView(this);
        tv1.setText(temp);
        selectDMCamps.addView(tv1);
    }

    private void configurecreateCampaign(final String user) {
        selectPlayer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFieldEmpty(etcampaignName))
                {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateCampaign.this);
                        builder.setMessage("Please enter Campaign Name")
                                .setNegativeButton("Try Again", null)
                                .create().show();
                }
                else if(etcampaignName.getText().toString().trim().length() <4) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(CreateCampaign.this);
                    builder2.setMessage("Campaign Name must contain atleast 4 characters")
                            .setNegativeButton("Try Again", null)
                            .create().show();
                }
                else
                {

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    //go get character names
                                    String partyKey = jsonResponse.getString("partyKey"),
                                            campaignID = jsonResponse.getString("campaignID"),
                                            error = jsonResponse.getString("error");
                                    //send userID and campaignID
                                    Intent intent = new Intent(CreateCampaign.this, DMSessionActivity.class);
                                    intent.putExtra("userid", user);
                                    intent.putExtra("campaignid", campaignID);
                                    startActivity(intent);
                                }

                                catch (JSONException e) {
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
                        String campName = etcampaignName.getText().toString();
                            params.put("userID", user);
                            params.put("name", campName);

                        return params;

                    }
                };
                Volley.newRequestQueue(getApplicationContext()).add(postRequest);
            }

            }
        }));

    }


    private boolean isFieldEmpty(EditText myEditText)
    {
        return myEditText.getText().toString().trim().length()==0;
    }

}
