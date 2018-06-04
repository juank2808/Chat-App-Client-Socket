package com.example.juank.clientchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Connection con;
    SenderMsg senderMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = new Connection();
        con.execute();
        senderMsg = new SenderMsg();

        final EditText etNick= findViewById(R.id.etNick);
        Button btnSendNick = findViewById(R.id.btnSendNick);

        btnSendNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send the message
                senderMsg.sendMessage("My Message "+etNick.getText().toString());
            }
        });
    }
}
