package com.example.hp.atm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TransactionCompleted extends AppCompatActivity {

    TextView trans_comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_completed);
        trans_comp=(TextView)findViewById(R.id.textView2);
    }
}
