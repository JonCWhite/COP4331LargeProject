package xyz.cop4331_7.taverntable;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.android.volley.RequestQueue;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure buttons to be pressed
        configureReturnUserButton();
        configureNewUserButton();
        configurePlayerSessionButton();
    }

    // Button functionality for the returning user button. Allows users to sign in.
    private void configureReturnUserButton() {
        Button returnUserButton = (Button) findViewById(R.id.returnUserButton);
        // Add an onClickListener to the button and configure it to start the sign in activity when
        // pressed.
        returnUserButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        }));
    }

    // Button functionality for new users button. Allows users to sign up.
    private void configureNewUserButton() {
        Button newUserButton = (Button) findViewById(R.id.newUserButton);
        // Add an onClickListener to the button and configure it to start the sign up activity when
        // pressed.
        newUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }
            }
        );
    }

    // Button functionality for new users button. Allows users to sign up.
    private void configurePlayerSessionButton() {
        Button newUserButton = (Button) findViewById(R.id.playerSessionButton);
        // Add an onClickListener to the button and configure it to start the sign up activity when
        // pressed.
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayerSessionActivity.class);
                intent.putExtra("campaign_name", "test_campaign");
                intent.putExtra("user_name", "test_user");
                startActivity(intent);
                }
        });
    }
}
