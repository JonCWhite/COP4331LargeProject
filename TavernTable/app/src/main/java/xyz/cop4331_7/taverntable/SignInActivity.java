package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class SignInActivity extends AppCompatActivity
{
    Button signInButton;
    EditText etUsername, etPassword;
    FloatingActionButton backButton;
    String username, password;
    static final String signInURL = "http://cop4331-7.xyz/system/login.php";


    // Begin sign in activity and configure UI elements.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        signInButton = (Button) findViewById(R.id.signInButton);
        etUsername = (EditText) findViewById(R.id.usernameField);
        etPassword = (EditText) findViewById(R.id.passwordField);
        backButton = (FloatingActionButton) findViewById(R.id.signInBackButton);



        // Configure sign in and back buttons
        configureBackButton();
        configureSignInButton();
    }

    // Configure back button to end this activity and return to the main activity.
    public void configureBackButton()
    {
        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.signInBackButton);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    public void configureSignInButton()
    {
        signInButton.setOnClickListener(new View.OnClickListener()
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

                                        Intent intent = new Intent(SignInActivity.this, StandIn_SelectUserTypeActivity.class);
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
        });
    }
}
