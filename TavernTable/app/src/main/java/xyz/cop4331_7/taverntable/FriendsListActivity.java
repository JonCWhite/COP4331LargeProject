package xyz.cop4331_7.taverntable;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FriendsListActivity extends AppCompatActivity {
    Button bFriendRequest;
    EditText etFriendRequest;
    FloatingActionButton backButton;
    // Best practices for android say padding should be done in multiples of 8. I declared this
    // as a constant to avoid magic numbers.
    private static final int paddingBase = 8;
    LinearLayout llFriends;
    // final RequestQueue queue;
    String userID, friendName;
    static final String getFriendsURL = "http://cop4331-7.xyz/test/getFriends.php";
    static final String getFriendInfoURL = "http://cop4331-7.xyz/test/getFriendInfo.php";
    static final String addFriendURL = "http://cop4331-7.xyz/test/addFriend.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        // Initialize variables.
        bFriendRequest = (Button) findViewById(R.id.bFriendRequest);
        backButton = (FloatingActionButton) findViewById(R.id.friendListBackButton);
        etFriendRequest = findViewById(R.id.etFriendRequest);
        llFriends = (LinearLayout) findViewById(R.id.llFriends);
        RequestQueue queue = Volley.newRequestQueue(this);
        userID = getIntent().getExtras().get("userID").toString();


        // Configure back and submit buttons
        configureBackButton();
        configureRequestButton(queue);

        // Populate list with friends
        getFriendInfo(queue);
    }

    // Configures back button such that it closes the friends list and returns to the select user
    // type activity.
    public void configureBackButton()
    {
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    public void configureRequestButton(RequestQueue queue) {
        bFriendRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                friendName = etFriendRequest.getText().toString();

                StringRequest postRequest = new StringRequest(Request.Method.POST, addFriendURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if (jsonResponse.getString("responseMessage") != null) {
                                        System.out.println(jsonResponse.getString("responseMessage"));
                                    }
                                    else if (jsonResponse.getString("error") != null) {
                                        System.out.println(jsonResponse.getString("error"));
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
                        params.put("friendName", friendName);
                        params.put("userID", userID);

                        return params;
                    }
                };
                Volley.newRequestQueue(getApplicationContext()).add(postRequest);
            }
        });
    }

    // Gathers a list of entries from the friends table to be used to search the users table.
    public void getFriendInfo(final RequestQueue queue) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, getFriendsURL,
                new Response.Listener<String>()
                {
                    // Define behavior on response to the requests execution. Here we want to send
                    // the collected rows from the friends table to another function that will
                    // search the Users table.
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONArray jsonResponse = new JSONArray(response);
                            System.out.println("Response: " + jsonResponse.toString());
                            appendFriends(jsonResponse, queue);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                // Checks to see if the request fails for some reason, and prints the stack trace
                // if an error occurs.
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                })
        {
            // Define parameters to perform search.
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                // POST params
                params.put("userID",userID);

                return params;
            }
        };

         queue.add(postRequest);
    }

    // Uses the user IDs from getFriendInfo() to append a text view to the UI for each friend.
    private void appendFriends(JSONArray jsonResponse, RequestQueue queue) {
        for (int i = 0; i < jsonResponse.length(); i++) {
            try {
                // If the userID is equal to the logged in user, then we want to search using
                // the friendID to get the friend's username if that friend is confirmed.
                if (userID.equals(jsonResponse.getJSONObject(i).getString("userID")) &&
                        jsonResponse.getJSONObject(i).getString("isConfirmed").equals("1")) {
                    textViewFactory(jsonResponse.getJSONObject(i).getString("friendID"), false, queue);
                }
                // Otherwise, we search by userID to get the friend's userID, but only if the
                // friend has been confirmed.
                else {
                    if (jsonResponse.getJSONObject(i).getString("isConfirmed").equals("1")) {
                        textViewFactory(jsonResponse.getJSONObject(i).getString("userID"), false, queue);
                    }
                    else {
                        textViewFactory(jsonResponse.getJSONObject(i).getString("userID"), true, queue);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    // Creates and appends a text view to the UI for each friend on this user's friends list.
    private void textViewFactory(final String searchID, final boolean mustConfirm, RequestQueue queue) {
        final int padding = getDPPaddingInPixels(paddingBase);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getFriendInfoURL,
                new Response.Listener<String>()
                {
                    // Adds a textView to the UI based on the response data.
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            System.out.println(searchID);
                            JSONObject jsonResponse = new JSONObject(response);

                            // Create new LinearLayout for friend entry
                            LinearLayout layout = new LinearLayout(FriendsListActivity.this);
                            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
                            layout.setOrientation(LinearLayout.HORIZONTAL);

                            // Create and format username view
                            TextView usernameView = new TextView(FriendsListActivity.this);
                            usernameView.setText(jsonResponse.getString("username"));
                            usernameView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                            usernameView.setGravity(android.view.Gravity.LEFT);
                            usernameView.setBackgroundColor(getResources().getColor(R.color.lightGray));
                            usernameView.setPadding(padding, padding, padding, padding);
                            layout.addView(usernameView);

                            // Create a confirm button if this friend is awaiting confirmation
                            if (mustConfirm) {
                                ImageButton bConfirm = new ImageButton(FriendsListActivity.this);
                                bConfirm.setImageResource(R.drawable.ic_confirm);
                                bConfirm.setBackgroundColor(getResources().getColor(R.color.forestGreen));
                                bConfirm.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                layout.addView(bConfirm);
                            }

                            // add user name and message to UI
                            llFriends.addView(layout);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                // Checks to see if the request fails for some reason, and prints the stack trace
                // if an error occurs.
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                })
        {
            // Define parameters to perform search.
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                // POST params
                params.put("userID",searchID);

                return params;
            }
        };

        queue.add(postRequest);
    }

    // Converts dp value to actual pixel value.
    private int getDPPaddingInPixels(int paddingDp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        return paddingPixel;
    }
}
