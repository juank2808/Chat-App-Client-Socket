package com.example.juank.clientchat;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Connection  con = new Connection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con.execute();
        final EditText etNick= findViewById(R.id.etNick);
        Button btnSendNick = findViewById(R.id.btnSendNick);

        btnSendNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send the message
                con.sendMessage("NICK "+etNick.getText().toString());
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        Log.w("Login with nick","/ "+msg);
                        String [] mMensaje = msg.obj.toString().split(" ");
                        Log.w("Login with nick","/ "+ Arrays.toString(mMensaje));

                    }
                };
                con.recibirMensaje(handler);
            }
        });



    }

}
