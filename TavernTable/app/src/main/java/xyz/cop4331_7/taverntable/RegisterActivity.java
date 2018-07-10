package xyz.cop4331_7.taverntable;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String signUpUrl = "http://cop4331-7.xyz/signUp.php";
    Button submitButton;
    EditText regUsername, regPassword, regEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize view variables and requestqueue
        submitButton = (Button) findViewById(R.id.signUpButton);
        regUsername = (EditText) findViewById(R.id.registerUsernameField);
        regPassword = (EditText) findViewById(R.id.registerPasswordField);
        regEmail = (EditText) findViewById(R.id.registerEmailField);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Configure submit and back buttons
        configureBackButton();
        configureSubmitButton();
    }

    // Configures submit button so that it will send a JSON with user information to be added to
    // the database.
    public void configureSubmitButton() {
        final TextView test = (TextView) findViewById(R.id.testTextView);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom response listener
                StringRequest request = new StringRequest(Request.Method.POST, signUpUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                // Custom error listener
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    // Create map to store form information.
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("username", regUsername.getText().toString());
                        parameters.put("password", regPassword.getText().toString());
                        parameters.put("email", regEmail.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    // Configure back button to end this activity and return to the main activity.
    public void configureBackButton() {
        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.registerBackButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
