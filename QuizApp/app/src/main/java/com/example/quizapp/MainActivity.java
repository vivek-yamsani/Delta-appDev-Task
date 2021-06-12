package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    int score = 0;
    int ansRd = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null)
            game();
        else{
            TextView textView = findViewById(R.id.RanDate);
            textView.setText(savedInstanceState.getString("tv"));
            ansRd = savedInstanceState.getInt("ansRd");
            String[] s = savedInstanceState.getStringArray("button");
            score = savedInstanceState.getInt("score");
            RadioButton r1 = findViewById(R.id.A);

            RadioButton r2 = findViewById(R.id.B),r3 = findViewById(R.id.C),r4 = findViewById(R.id.D);

            RadioButton[] radioButtons= new RadioButton[]{r1, r2, r3, r4};

            for(int i=0;i<4;i++){
                radioButtons[i].setText(s[i]);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView textView = findViewById(R.id.RanDate);
        outState.putString("tv",(String) textView.getText());
        outState.putInt("ansRd",ansRd);
        outState.putInt("score", score);
        String[] s = new String[4];
        RadioButton r1 = findViewById(R.id.A);

        RadioButton r2 = findViewById(R.id.B),r3 = findViewById(R.id.C),r4 = findViewById(R.id.D);

        RadioButton[] radioButtons= new RadioButton[]{r1, r2, r3, r4};
        
        for(int i=0;i<4;i++){
            s[i] = (String) radioButtons[i].getText();
        }

        outState.putStringArray("button",s);
    }



    public void game(){

        int ans=set();

        RadioButton r1 = findViewById(R.id.A);

        RadioButton r2 = findViewById(R.id.B),r3 = findViewById(R.id.C),r4 = findViewById(R.id.D);

        RadioButton[] radioButtons= new RadioButton[]{r1, r2, r3, r4};
        Collections.shuffle(Arrays.asList(radioButtons));

        ansRd = radioButtons[0].getId();

        radioButtons[0].setText(settext(ans));
        Set<String> s = new HashSet<String>();
        s.add(settext(ans));
        for(int i=1;i<4;i++) {
            radioButtons[i].setText(settext(0));
            while(s.contains((String) radioButtons[i].getText())){
                radioButtons[i].setText(settext(0));
            }
            s.add((String) radioButtons[i].getText());
        }

    }

    public String settext(int x){
        int a = randBetween(1,7);
        if(x>0) a = x;
        String ans="";
        switch (a){
            case 2:
                ans = "Monday";
                break;
            case 3:
                ans = "Tuesday";
                break;
            case 4:
                ans =  "Wednesday";
                break;
            case 5:
                ans = "Thursday";
                break;
            case 6:
                ans =  "Friday";
                break;
            case 7:
                ans = "Saturday";
                break;
            case 1:
                ans = "Sunday";
                break;
        }
        return ans;
    }

    public int set(){

        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1970, 2021);

        gc.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        TextView textView = findViewById(R.id.RanDate);
        textView.setText(gc.get(Calendar.DAY_OF_MONTH) +" - " + (gc.get(Calendar.MONTH) + 1) + " - "+gc.get(Calendar.YEAR) );

        return gc.get(Calendar.DAY_OF_WEEK);
    }



    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public void button(View view) {
        RadioGroup radioGroup = findViewById(R.id.options);
        int id = radioGroup.getCheckedRadioButtonId();
        if(id == -1){
            Toast.makeText(this,"Please select an option", Toast.LENGTH_LONG).show();
            return ;
        }
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);

            vibrator.vibrate(vibrationEffect);

        }
        ConstraintLayout layout = findViewById(R.id.Layout1);
        if(id == ansRd){
            score += 1;
            layout.setBackgroundColor(Color.GREEN);
            radioGroup.clearCheck();
            remoCol();
            game();
        }
        else{
            layout.setBackgroundColor(Color.RED);
            Intent intent = new Intent(this, Activity2.class);
            intent.putExtra("score",score);
            startActivity(intent);
            finish();
        }

    }

    public void remoCol(){
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ConstraintLayout layout = findViewById(R.id.Layout1);

                layout.setBackgroundColor(Color.BLACK);
            }
        };

        handler.postDelayed(runnable,700);
    }
}

