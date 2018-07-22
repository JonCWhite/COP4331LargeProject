package xyz.cop4331_7.taverntable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

public class SelectSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sheet);

        populateWithSheets();
    }

    public void populateWithSheets()
    {
        // StringRequest postRequest = new StringRequest(Request.Method.POST, )

        /* signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                StringRequest postRequest = new StringRequest(Request.Method.POST, signInURL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String userID = jsonResponse.getString("userID"),
                                            error = jsonResponse.getString("error");

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
                        params.put("username", username);
                        params.put("password", password);

                        return params;
                    }
                };

                Volley.newRequestQueue(getApplicationContext()).add(postRequest);
            }
        }); */
    }
}
