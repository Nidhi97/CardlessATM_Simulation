package com.example.hp.atm;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.atm.R;
import com.example.hp.atm.Transdetails;
import com.example.hp.atm.Userdetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionReceipts extends AppCompatActivity {
    Userdetails ud=new Userdetails();
    //int acc;
    String acc;
    public String d="hello";
    String TID;
    Transdetails td=new Transdetails();
    private TextView tRs,tPh,tAc,tAmt,tDt,tS;
    Transdetails transdetails=new Transdetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_receipts);
        tRs=(TextView)findViewById(R.id.textViewRs);
        tPh=(TextView)findViewById(R.id.textViewDPhone);
        tAc=(TextView)findViewById(R.id.textViewDAcc);
        tAmt=(TextView)findViewById(R.id.textViewDAmt);
        tDt=(TextView)findViewById(R.id.textViewDT);
        tS=(TextView)findViewById(R.id.textView7);
        acc=getIntent().getExtras().getString("acc");
        TID=getIntent().getExtras().getString("TransID");
        System.out.println(acc);
        System.out.println(TID);
        final DatabaseReference mref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/");
        mref.child("accounts").child(String.valueOf(acc)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ud=dataSnapshot.getValue(Userdetails.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mref.child("TransactionDetails").child("savings44417:05:2018 13:17:08").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                td=dataSnapshot.getValue(Transdetails.class);
                //System.out.println(td.getAmount());
                String R= "Rs"+String.valueOf(td.getAmount());

                if(td.isComplete()){
                    //tRs.setText(dataSnapshot.getValue());
                    String s="Transaction Successful";
                    tS.setText(s);
                    tRs.setText(R);
                    tPh.setText(String.valueOf(ud.getMobile()));
                    tAc.setText(String.valueOf(td.getAcc_no()));
                    tAmt.setText(String.valueOf(td.getAmount()));
                    tDt.setText(String.valueOf(td.getDate_of_Transaction()));
                }
                else{
                    String n="Transaction Unsuccessful";
                    tS.setText(n);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
