package xyz.cop4331_7.taverntable;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class SelectSheetActivity extends AppCompatActivity {
    RequestQueue queue;
    private String campaignID, characterID;
    static final String getCharacterNamesURL = "http://cop4331-7.xyz/test/getCharacterNames.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sheet);

        // Initialize class variables
        campaignID = getIntent().getExtras().get("campaignID").toString();
        characterID = getIntent().getExtras().get("characterID").toString();
        queue = Volley.newRequestQueue(this);

        getCharacterNames(SelectSheetActivity.this, queue);
    }

    public void getCharacterNames(final Context context, RequestQueue queue)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, getCharacterNamesURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            // To access a string in the JSONArray, use the following syntax:
                            // mData.getJSONObject(position).getString("name")
                            JSONArray jsonResponse = new JSONArray(response);

                            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(context, jsonResponse);
                            myrv.setLayoutManager(new GridLayoutManager(context, 3));
                            myrv.setAdapter(myAdapter);
                            /*
                            if(error.equals("This user does not exist"))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setMessage("Login failed.")
                                        .setNegativeButton("Try Again", null)
                                        .create().show();
                            }

                            else
                            {

                                System.out.println("SUCCESS");
                                Intent intent = new Intent(SignInActivity.this, UserAreaActivity.class);
                                startActivity(intent);

                            }
                            */
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
                params.put("campaignID", campaignID);
                params.put("characterID", characterID);

                return params;
            }
        };
        queue.add(postRequest);
    }
}
