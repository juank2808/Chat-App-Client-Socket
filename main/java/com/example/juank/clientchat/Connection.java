package com.example.juank.clientchat;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection extends AsyncTask<Void ,Void, Void> {
    public static Socket socket;

    /*to send*/
    private HandlerThread handlerThreadSender;
    private Handler handlerSend;
    private BufferedWriter out;

    /*to receive*/
    private HandlerThread handlerThreadReceiver;
    private Handler handlerRecibir;
    private Handler handlerHiloP;
    private BufferedReader in;


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
    public void sendMessage(String mMessage){
        handlerThreadSender=new HandlerThread("sendMSG");
        handlerThreadSender.start();

        Looper looper = handlerThreadSender.getLooper();

        handlerSend = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //socket =Connection.socket;
                try {
                    out  =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    Log.w ("ESTO ES MI MENSAJE A ENVIAR",msg.obj.toString() );

                    out.write(msg.obj.toString()+"\n");


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
    /* handler thread to receive messages*/
    public void recibirMensaje(final Handler handlerHiloPrin){
        handlerHiloP = handlerHiloPrin;
        handlerThreadReceiver = new HandlerThread("recibir");
        handlerThreadReceiver.start();
        /* ademas de hacer referancia el handler del hilo principal, tambien hay que hacerlo con el hiloprincipal*/
        Looper looper = handlerThreadReceiver.getLooper();
        handlerHiloP = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String mensajeRec;

                try {

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while((mensajeRec= in.readLine())!=null){
                        Message mensaje = handlerHiloPrin.obtainMessage();
                        mensaje.obj=mensajeRec;
                        handlerHiloPrin.sendMessage(mensaje);
                        Log.w("MI MENSAJE A ENVIAR A UI :"," " +mensajeRec);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.w("ESTO RECIBE EN EL HANDLER FUERA DEL HILO:", handlerHiloP.obtainMessage().toString());
        /*esto acciona el hilo*/
        Message mensaje=handlerHiloP.obtainMessage();
        handlerHiloP.sendMessage(mensaje);


    }
}
