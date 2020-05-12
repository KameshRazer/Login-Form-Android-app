package com.example.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class welcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Intent i = getIntent();
        TextView welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        welcomeMsg.setText("Wellcome "+i.getStringExtra("name_key"));
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(welcomePage.this,MainActivity.class);
                i.putExtra("logout_key",Boolean.TRUE);
                startActivity(i);
            }
        });
    }
}
