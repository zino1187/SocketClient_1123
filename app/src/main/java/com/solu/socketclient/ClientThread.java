package com.solu.socketclient;

import android.os.Bundle;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ClientThread extends Thread{
    MainActivity mainActivity;
    Socket socket;
    BufferedReader buffr;
    BufferedWriter buffw;
    boolean flag=true;

    public ClientThread(MainActivity mainActivity, Socket socket){
        this.mainActivity=mainActivity;
        this.socket=socket;

        try {
            buffr = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            String msg=buffr.readLine();

            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg", msg);
            message.setData(bundle);

            mainActivity.handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg){
        try {
            buffw.write(msg);
            buffw.write("\n");
            buffw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(flag){
            listen();
        }
    }


}
