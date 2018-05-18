package com.example.hp.atm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnterAccessCode extends AppCompatActivity {
EditText ac;
String code;
    Transaction transaction;
    DatabaseReference myRef;
    FirebaseDatabase database;
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_access_code);
        proceed=(Button)findViewById(R.id.buttonProceed);
        ac = (EditText) findViewById(R.id.editText);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        proceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        /*try {
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("Task performed on ");
                    ac.setEnabled(true);
                    cancel();
                }
            };
            Timer timer = new Timer("Timer");

            timer.scheduleAtFixedRate(task, 1000L, 1000L);

            Thread.sleep(10000L * 2);
        } catch (Exception e) {
            System.out.println("error"+e);
        }*/
                //retrieving all transaction nodes


                myRef.child("TransactionDetails")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    transaction = (Transaction) snapshot.getValue(Transaction.class);

                                    //Getting the transaction id
                                    String transid = String.valueOf(transaction.acc_type) + String.valueOf(transaction.acc_no) + transaction.date_of_Transaction;
                                    code = transaction.code;

                                    System.out.println("transid" + transid);
                                    System.out.println("transaction .is complete-"+transaction.isComplete);
                                    System.out.println("code from user" + ac.getText());
                                    System.out.println("code from transaction" + code);

                                    System.out.println("transaction.isComplete"+(String.valueOf(transaction.isComplete==false)));
                                    System.out.println("(ac.getText()).equals(code)"+(ac.getText().toString()).equals(code));
                                    System.out.println();
                                    //comparing the access codes
                                    if (transaction.isComplete==false && (ac.getText().toString()).equals(code)) {
                                        //setting the isComplete value of transaction node to true
                                        System.out.println("account num"+transaction.acc_no);
                                        myRef.child("TransactionDetails").child(transid).child("isComplete").setValue(true);
                                        System.out.println("isComplete set to true");
                                        //setting the accesscode in the account node
                                        myRef.child("accounts").child(String.valueOf(transaction.acc_no)).child("accesscode").setValue(code);
                                        System.out.println("setting the accesscode in the account node");
                                        //setting the balance in the account node
                                        final long[] amt = {(long) transaction.amount};
                                        myRef.child("accounts").child(String.valueOf(transaction.acc_no)).child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                amt[0] = (long) dataSnapshot.getValue() - amt[0];
                                                myRef.child("accounts").child(String.valueOf(transaction.acc_no)).child("balance").setValue(amt[0]);
                                                Toast.makeText(getApplicationContext(), "Transaction Complete!",
                                                        Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                       // Intent intent=new Intent(EnterAccessCode.this,TransactionReceipts.class);
                                       // intent.putExtra("acc",String.valueOf(transaction.acc_no));
                                       // intent.putExtra("TransID",transid);
                                       // startActivity(intent);


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
    });
    }
}
