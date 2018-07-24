package xyz.cop4331_7.taverntable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Dice extends AppCompatActivity {
   // RequestQueue requestQueue;
    //private static final String LOGIN_REQUEST_URL = "http://cop4331-7.xyz/tavernTable/Dice.php";
    public int value;
    Random rand = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        configureD4();
        configureD6();
        configureD8();
        configureD10();
        configureD20();
        configureD100();
    }

    public void configureD4() {
        Button D4 = (Button) findViewById(R.id.D4);
        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(3)+1;
                Intent intent = new Intent(getApplicationContext(), null);
                intent.putExtra("value","value");
                startActivity(intent);
            }
        });
    }

    public void configureD6()
    {
        Button D6 =(Button) findViewById(R.id.D6);
        D6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(5)+1;
                Intent intent = new Intent(getApplicationContext(), null);
                intent.putExtra("value","value");
                startActivity(intent);
            }
        });

    }
    public void configureD8()
    {
        Button D8 =(Button) findViewById(R.id.D8);
        D8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(7)+1;
                Intent intent = new Intent(getApplicationContext(), null);
                intent.putExtra("value","value");
                startActivity(intent);
            }
        });

    }
    public void configureD10()
    {
        Button D10 =(Button) findViewById(R.id.D10);
        D10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(9)+1;
                Intent intent = new Intent(getApplicationContext(), null);
                intent.putExtra("value","value");
                startActivity(intent);
            }
        });

    }
    public void configureD20()
    {
        Button D20 =(Button) findViewById(R.id.D20);
        D20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(19)+1;
                Intent intent = new Intent(getApplicationContext(), null);
                intent.putExtra("value","value");
                startActivity(intent);
            }
        });

    }
    public void configureD100()
    {
        Button D100 =(Button) findViewById(R.id.D100);
        D100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=rand.nextInt(100);
                Intent intent = new Intent(getApplicationContext(), null);
                intent.putExtra("value","value");
                startActivity(intent);
            }
        });

    }

}
