package xyz.cop4331_7.taverntable;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SignInActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String loginUrl = "http://cop4331-7.xyz/login.php";

    // Begin sign in activity and configure UI elements.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize requestQueue
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Configure submit and back buttons
        configureBackButton();
    }

    // Configure back button to end this activity and return to the main activity.
    public void configureBackButton() {
        FloatingActionButton backButton = (FloatingActionButton) findViewById(R.id.signInBackButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
