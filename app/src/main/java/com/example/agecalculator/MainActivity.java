package com.example.agecalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    EditText et;
    Button btn;
    TextView tv;
    boolean isValid;
    public static boolean validateJavaDate(String strDate)
    {

        if (strDate.trim().equals(""))
        {
            return true;
        }

        else
        {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/mm/yyyy");
            sdfrmt.setLenient(false);

            try
            {
                Date javaDate = sdfrmt.parse(strDate);
            }
            catch (ParseException e)
            {
                return false;
            }
            return true;
        }
    }

    protected boolean isLeap(int y){
        return (((y % 4 == 0) &&
                (y % 100 != 0)) ||
                (y % 400 == 0));
    }
    protected boolean isValidDate(int d,
                               int m,
                               int y)
    {
        final int MAX_VALID_YR = 9999;
        final int MIN_VALID_YR = 1800;

        if (y > MAX_VALID_YR ||
                y < MIN_VALID_YR)
            return false;
        if (m < 1 || m > 12)
            return false;
        if (d < 1 || d > 31)
            return false;


        if (m == 2)
        {
            if (isLeap(y))
                return (d <= 29);
            else
                return (d <= 28);
        }


        if (m == 4 || m == 6 ||
                m == 9 || m == 11)
            return (d <= 30);

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.etDate);
        btn = findViewById((R.id.btnSubmit));
        tv = findViewById((R.id.tvResult));
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                isValid  = validateJavaDate(et.getText().toString());

                if(TextUtils.isEmpty(et.getText())){
                        tv.setText("Please enter date of birth!");
                }
                else if(isValid != true || et.getText().length() < 10 || et.getText().length() != 10){

                        tv.setText("Please check your date format. Include 0's if necessary. For " +
                                "Example: 08/08/2000");
                        Toast.makeText(MainActivity.this,"Invalid Date!",
                                Toast.LENGTH_SHORT).show();

                }else {

                    String date = et.getText().toString().trim();
                    LocalDate today = LocalDate.now();
                    int day = Integer.parseInt(date.substring(0, 2));
                    int mon = Integer.parseInt(date.substring(3, 5));
                    int year = Integer.parseInt(date.substring(6, 10));

                    if(isValidDate(day,mon,year)) {
                        LocalDate dob = LocalDate.of(year, mon, day);
                        Period p = Period.between(dob, today);
                        int bmon = p.getMonths();
                        int bday = p.getDays();
                        int byea = p.getYears();
                        tv.setText("You are " + byea + " years " + bmon + " months " + bday +
                                " days old.");
                    }
                    else{
                        tv.setText("Please enter a valid date.");
                        Toast.makeText(MainActivity.this,"Invalid Date!",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}