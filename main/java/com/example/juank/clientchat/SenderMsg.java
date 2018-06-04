package com.example.juank.clientchat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SenderMsg {
    private Socket socket;
    private HandlerThread handlerThreadSender;
    private Handler handlerSend;
    private BufferedWriter out;

    public void sendMessage(String mMessage){
        handlerThreadSender=new HandlerThread("sendMSG");
        handlerThreadSender.start();

        Looper looper = handlerThreadSender.getLooper();

        handlerSend = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                socket =Connection.socket;
                try {
                    out  =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    Log.w ("ESTO ES MI MENSAJE A ENVIAR",String.valueOf(msg.obj.toString()) );

                    out.write(msg.obj.toString());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        /* i need this  to send menssage to server*/

        Message msg = handlerSend.obtainMessage();
        /* I tell it that the object of the message is my Message that i pass it like parameter*/
        msg.obj=mMessage;
        /* Now i send message to HandlerSend so that the message is sent to my server*/
        handlerSend.sendMessage(msg);

    }

}
