package com.example.hp.atm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonqr, buttonsms, buttonnfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonsms = (Button) findViewById(R.id.buttonsms);
        buttonqr = (Button) findViewById(R.id.buttonqr);
        buttonnfc = (Button) findViewById(R.id.buttonnfc);

        buttonqr.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayQR.class);

                startActivity(intent);
            }
        });
        buttonsms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this, EnterAccessCode.class);

                startActivity(intent);
            }
        });
        buttonnfc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this,NfcReceiveActivity.class);

                startActivity(intent);
            }
        });

    }
}