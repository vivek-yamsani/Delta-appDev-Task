package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        TextView textView = findViewById(R.id.Scoreboard);

            int x = getIntent().getIntExtra("score", 0);
            textView.setText("Your score is:\n" + x);

    }

    public void quit(View view) {
        Activity2.this.finish();
        System.exit(0);
    }

    public void regame(View view) {
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
    }
}