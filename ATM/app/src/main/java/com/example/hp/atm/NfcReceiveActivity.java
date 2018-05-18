package com.example.hp.atm;


        import android.content.Intent;
        import android.nfc.NdefMessage;
        import android.nfc.NdefRecord;
        import android.nfc.NfcAdapter;
        import android.nfc.NfcEvent;
        import android.nfc.tech.NfcA;
        import android.os.Parcelable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.app.Activity;
        import android.content.Intent;
        import android.nfc.NdefMessage;
        import android.nfc.NdefRecord;
        import android.nfc.NfcAdapter;
        import android.nfc.NfcAdapter.CreateNdefMessageCallback;
        import android.nfc.NfcEvent;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.nio.charset.Charset;

        import org.w3c.dom.Text;

        import java.nio.charset.Charset;
        import java.util.Locale;

        import static android.nfc.NdefRecord.createMime;
//import static com.example.android.nfc.MainActivity.createNewTextRecord;

        import android.app.Activity;
        import android.content.Intent;
        import android.nfc.NdefMessage;
        import android.nfc.NdefRecord;
        import android.nfc.NfcAdapter;
        import android.nfc.NfcAdapter.CreateNdefMessageCallback;
        import android.nfc.NfcEvent;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.nio.charset.Charset;


public class NfcReceiveActivity extends AppCompatActivity implements CreateNdefMessageCallback {
    NfcAdapter mNfcAdapter;
    TextView textView;
    float amt;
    int pin,acc;
    String acc_type,method_used;
    Transaction transaction;
    DatabaseReference myRef;
    FirebaseDatabase database;
    public static String t="hello";

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String text = "Beam me up";
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMime(
                        "application/vnd.com.example.hp.atm", text.getBytes())
                        /**
                         * The Android Application Record (AAR) is commented out. When a device
                         * receives a push with an AAR in it, the application specified in the AAR
                         * is guaranteed to run. The AAR overrides the tag dispatch system.
                         * You can add it back in to guarantee that this
                         * activity starts when receiving a beamed message. For now, this code
                         * uses the tag dispatch system.
                        */
                        ,NdefRecord.createApplicationRecord("com.example.hp.atm")
                });
        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
       /* if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }*/
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        textView = (TextView) findViewById(R.id.textView);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
       // textView.setText(new String(msg.getRecords()[0].getPayload()));
       // t=textView.getText().toString();
        t=new String(msg.getRecords()[0].getPayload());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_receive);

       // amt =Float.parseFloat(getIntent().getExtras().getString("amount"));

        TextView textView = (TextView) findViewById(R.id.textView);
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
       myRef = database.getInstance().getReferenceFromUrl("https://cardlessatm-1a9ff.firebaseio.com/");
        myRef.child("TransactionDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            transaction = (Transaction) snapshot.getValue(Transaction.class);

                            //Getting the transaction id
                            String transid = String.valueOf(transaction.acc_type) + String.valueOf(transaction.acc_no) + transaction.date_of_Transaction;
                            //comparing the access codes
                            if (transaction.isComplete==false && (transid.equals(t))) {
                                //setting the isComplete value of transaction node to true
                                myRef.child("TransactionDetails").child(transid).child("isComplete").setValue(true);
                                System.out.println("isComplete set to true");
                                //setting the accesscode in the account node
                                //myRef.child("accounts").child(String.valueOf(transaction.acc_no)).child("accesscode").setValue(code);
                                //System.out.println("setting the accesscode in the account node");
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
                                //Intent intent=new Intent(NfcReceiveActivity.this,TransactionReceipts.class);
                               // intent.putExtra("acc",String.valueOf(transaction.acc_no));
                               // intent.putExtra("TransID",transid);
                                //startActivity(intent);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }


}


