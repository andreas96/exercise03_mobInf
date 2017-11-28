package com.example.andreas.nfcapplikation;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private TextView nfcInfoView;
    private String infoOutput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcInfoView = (TextView) findViewById(R.id.nfcInfoView);

        /*if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }*/

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC is available", Toast.LENGTH_SHORT).show();
            nfcInfoView.setText("Tap the button to get started!");
        }
        else{
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            nfcInfoView.setText("Please enable NFC!");
        }
    }

    public void readNFCtag(View view){

        TextView nfcInfoView = (TextView) findViewById(R.id.nfcInfoView);

        if(nfcAdapter ==  null){
            nfcInfoView.setText("Your device doesn't support NFC or you have to switch it on!");
            return;
        }
        else{
            nfcInfoView.setText("Please scan the NFC tag you want to read!");
        }
    }


// copied from developer.android.com TODO: überprüfe auf Richtigkeit und schreibe NFC-Tag-Daten auf die Textview
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                    infoOutput = infoOutput + messages[i];
                }
            }
            nfcInfoView.setText(infoOutput);
        }
    }
}
