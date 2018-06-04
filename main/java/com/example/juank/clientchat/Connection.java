package com.example.juank.clientchat;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class Connection extends AsyncTask<Void ,Void, Void> {
    public static Socket socket;

    public static Socket connect(){
        try {
            socket = new Socket("10.0.2.2",1995);
            Log.w("This is My socket", ""+socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        connect();
        return null;
    }
}
