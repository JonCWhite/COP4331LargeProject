package xyz.cop4331_7.taverntable;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure buttons to be pressed
        configureReturnUserButton();
        configureNewUserButton();
    }

    private void configureReturnUserButton() {
        Button returnUserButton = (Button) findViewById(R.id.bReturnUser);
        returnUserButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        }));
    }

    private void configureNewUserButton() {
        Button newUserButton = (Button) findViewById(R.id.bNewUser);
        newUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }
            }
        );
    }
}
