package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public Button btn_login;
    public Button btn_register;
    public Onclick onclick = new Onclick();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        setListener();


    }
    public void setListener(){
        btn_login.setOnClickListener(onclick);
        btn_register.setOnClickListener(onclick);
    }
    private class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btn_login:
                    Toast.makeText(LoginActivity.this,"press login",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_register:
                    intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
