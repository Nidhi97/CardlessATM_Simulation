package com.example.hp.atm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by LENOVO on 08-05-2018.
 */

public class TransactionStatus extends AppCompatActivity {
    private int progress = 0;
    private final int pBarMax = 10;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionstatus);

        final ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar2);
        pBar.setMax(pBarMax);

        final Thread pBarThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(progress<=pBarMax) {
                        pBar.setProgress(progress);
                        sleep(1000);
                        ++progress;
                    }
                  //pBar.setVisibility(View.GONE);
                    intent=new Intent(TransactionStatus.this, TransactionCompleted.class);
                    startActivity(intent);
                }
                catch(InterruptedException e) {
                }

            }
        };
System.out.println("progress bar");
        pBarThread.start();


    }
}