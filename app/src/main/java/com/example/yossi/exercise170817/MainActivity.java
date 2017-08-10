package com.example.yossi.exercise170817;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    CheckBox cbRememberMe;
    Button btLogin;
    String dbusername, dbpass;
    DBHandler db;
    public  final String MyPREFERENCES = "MyPrefsPlayer";
    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.etUname);
        etPassword = (EditText) findViewById(R.id.etPass);
        cbRememberMe =(CheckBox) findViewById(R.id.checkBox);
        btLogin = (Button)findViewById(R.id.btLogin);
        //
        //dbusername = "GMAIL.COM";
        //dbpass = "31665";
        dbusername = "a";
        dbpass = "a";

        try {
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            if (sharedpreferences.getBoolean("cbstatus", false)) {
                cbRememberMe.setChecked(true);
            }
            etUserName.setText(sharedpreferences.getString("musername", ""));
            etPassword.setText(sharedpreferences.getString("mpassword", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        db = new DBHandler(this);
        cbRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (isChecked && etUserName.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0) {

                    editor.putString("musername", etUserName.getText().toString());
                    editor.putString("mpassword", etPassword.getText().toString());
                    editor.putBoolean("cbstatus", cbRememberMe.isChecked());
                    editor.commit();
                }
                if (!isChecked) {
                    editor.putString("musername", "");
                    editor.putString("mpassword", "");
                    editor.putBoolean("cbstatus", false);
                    editor.commit();
                }
            }
        });



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (etUserName.getText().length() > 0 && etPassword.getText().length() > 0) {


                        if (dbusername.equals(etUserName.getText().toString()) && dbpass.equals(etPassword.getText().toString())) {
                            Intent myIntent = new Intent(MainActivity.this, Processes.class);
                            MainActivity.this.startActivity(myIntent);
                            //MainActivity.this.finish();

                        } else {
                            Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }





}


