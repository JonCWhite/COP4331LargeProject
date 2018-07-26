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

public class SheetInventoryActivity extends AppCompatActivity {
    Button button;
    EditText etGP, etSP, etCP, etEquipment;
    Intent intent;
    RequestQueue queue;
    String characterID, userID;
    static final String urlGet = "http://cop4331-7.xyz/getEquiptmentBox.php";
    static final String urlSet = "http://cop4331-7.xyz/setEquiptmentBox.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_inventory);

        // Initialize class variables from UI elements
        etGP = (EditText) findViewById(R.id.etGP);
        etSP = (EditText) findViewById(R.id.etSP);
        etCP = (EditText) findViewById(R.id.etCP);
        etEquipment = (EditText) findViewById(R.id.etEquipment);
        button = (Button) findViewById(R.id.bSheetEquipment);
        queue = Volley.newRequestQueue(getApplicationContext());

        //get the character ID from the intent
        intent = getIntent();
        characterID = intent.getExtras().get("characterID").toString();
        userID = intent.getExtras().get("userID").toString();

        getInventory(characterID, queue);
    }

    // Loads data from the server into character inventory fields
    private void getInventory(final String characterID, final RequestQueue queue){

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
                            String gold = jsonResponse.getJSONObject(0).getString("gold"),
                                    silver = jsonResponse.getJSONObject(0).getString("silver"),
                                    copper = jsonResponse.getJSONObject(0).getString("copper"),
                                    inventory = jsonResponse.getJSONObject(0).getString("inventory"),
                                    responseUserID = jsonResponse.getJSONObject(0).getString("userID");


                            // If the error JSON response doesn't have an error field, then we know
                            // our data was successfully retrieved.
                            if(!jsonResponse.getJSONObject(0).has("error")){

                                //success
                                etGP.setText(gold);
                                etSP.setText(silver);
                                etCP.setText(copper);
                                etEquipment.setText(inventory);
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
                                        setInventory(characterID, queue);
                                    }
                                });
                            }
                            // Otherwise, this user should not be allowed to edit this character
                            // sheet so we remove the submite button.
                            else {
                                LinearLayout parent = (LinearLayout) findViewById(R.id.llInventory);
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

    }//end getEquipment

    // Applies any changes made to editable parts of the form to the server.
    private void setInventory(final String characterID, final RequestQueue queue){

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
                params.put("gold", etGP.getText().toString());
                params.put("silver", etSP.getText().toString());
                params.put("copper", etCP.getText().toString());
                params.put("inventory", etEquipment.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);
    }
}
