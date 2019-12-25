package com.example.y.socket_demo;

import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.send_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            sendServer();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void sendServer() throws IOException {
        Socket socket = new Socket("192.168.1.3",9019);
        OutputStream os = socket.getOutputStream();
        Person person = initData();
        String data = gson.toJson(person);
        os.write(data.getBytes());
        os.flush();
        socket.shutdownOutput();
        socket.close();
    }

    private Person initData() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(23);
        person.setSex("男");
        return person;
    }
}
