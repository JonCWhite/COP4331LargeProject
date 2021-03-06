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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    Button signUpButton;
    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    FloatingActionButton backButton;
    String username, email, password, confirmPassword;
    static final String signUpURL = "http://cop4331-7.xyz/system/signUp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize variables from UI elements
        signUpButton = (Button) findViewById(R.id.signUpButton);
        etUsername = (EditText) findViewById(R.id.registerUsernameField);
        etEmail = (EditText) findViewById(R.id.registerEmailField);
        etPassword = (EditText) findViewById(R.id.registerPasswordField);
        etConfirmPassword = (EditText) findViewById(R.id.registerConfirmField);
        backButton = (FloatingActionButton) findViewById(R.id.registerBackButton);

        // Configure submit and back buttons
        configureBackButton();
        configureSignUpButton();
    }

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

    public void configureSignUpButton() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
        {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                email = etEmail.getText().toString();

                if (!password.equals(confirmPassword))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Passwords do not match. Please try again.")
                            .setNegativeButton("Try Again", null)
                            .create().show();
                }
                else
                {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, signUpURL,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String userID = jsonResponse.getString("userID"),
                                                error = jsonResponse.getString("error");

                                        if (error.equals("This user already exists"))
                                        {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                            builder.setMessage("Registration failed.")
                                                    .setNegativeButton("Try Again", null)
                                                    .create().show();
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                                            RegisterActivity.this.startActivity(intent);

                                        }

                                    } catch (JSONException e)
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
                            }) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<>();

                            // POST parameters
                            params.put("username", username);
                            params.put("password", password);
                            params.put("email", email);

                            return params;
                        }
                    };

                    Volley.newRequestQueue(getApplicationContext()).add(postRequest);
                }
        }
        });
    }
}