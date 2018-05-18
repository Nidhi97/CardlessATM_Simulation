package com.example.hp.atm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by LENOVO on 02-05-2018.
 */

public class DisplayQR extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    String currentTransaction;
    String TransactionIDList;
    long acc_no;
    long amt;
    ImageView imageView;
    private int progress = 0;
    private final int pBarMax = 60;


    public void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayqr);
        FirebaseDatabase.getInstance().getReference().child("ATM").child("HYD12").addValueEventListener(new ValueEventListener()
        {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child("transaction_initiated").getValue(Boolean.class) == true)
                        {
                            for (int i = 0; i < 30; i++) ;
                            System.out.println("TRANSACTION DONE");

                            //retrieving the value of current Transaction from atm node
                            currentTransaction = (String) dataSnapshot.child("current_transaction").getValue();
                            System.out.println("CT-" + currentTransaction);

                            //setting the isComplete attribute of TransactionDetails Node to true
                            FirebaseDatabase.getInstance().getReference().child("TransactionDetails").child(currentTransaction).child("isComplete").setValue(true);

                            // updating transactionidlist in atm node
                            TransactionIDList = (String) dataSnapshot.child("transaction_list").getValue();
                            FirebaseDatabase.getInstance().getReference().child("ATM").child("HYD12").child("transaction_list").setValue(TransactionIDList + "*" + currentTransaction);

                            FirebaseDatabase.getInstance().getReference().child("ATM").child("HYD12").removeEventListener(this);

                            //retrieving the account no n amt;
                           FirebaseDatabase.getInstance().getReference().child("TransactionDetails").child(currentTransaction).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    amt= (long) dataSnapshot.child("amount").getValue();
                                    acc_no=(long)dataSnapshot.child("acc_no").getValue();
                                    FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc_no)).child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            amt=(long)dataSnapshot.getValue()-amt;
                                            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc_no)).child("balance").setValue(amt);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            Intent intent=new Intent(DisplayQR.this,TransactionStatus.class);

                            startActivity(intent);
                        }

                    }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });





                            //retrieving the account no n amt;
                           /*FirebaseDatabase.getInstance().getReference().child("TransactionDetails").child(currentTransaction).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    amt= (float) dataSnapshot.child("amount").getValue();
                                    acc_no=(int)dataSnapshot.child("acc_no").getValue();
                                    FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc_no)).child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            amt=(float)dataSnapshot.getValue()-amt;
                                            FirebaseDatabase.getInstance().getReference().child("accounts").child(String.valueOf(acc_no)).child("balance").setValue(amt);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/


                        }

                    }


