package com.example.handlertest;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.buttonProgressBar);


        button.setOnClickListener(new buttonListener());

    }


    class buttonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);

            System.out.println("Start UpdateThread!");
            handler.post(UpdateThread);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressBar.setProgress(msg.arg1);

            handler.post(UpdateThread);
        }
    };

    Runnable UpdateThread = new Runnable() {

        int bar = 0;
        @Override
        public void run() {
            System.out.println("Run UpdateThread!");
            bar += 10;

            Message msg = handler.obtainMessage();
            msg.arg1 = bar;
            try{

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(bar == 100) {

                System.out.println("End UpdateThread.......");
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(UpdateThread);
            }
            else
                handler.sendMessage(msg);
        }
    };
}

