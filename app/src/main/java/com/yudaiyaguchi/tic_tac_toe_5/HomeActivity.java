package com.yudaiyaguchi.tic_tac_toe_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yudaiyaguchi.tic_tac_toe_5.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent gameIntent;
        gameIntent = new Intent(HomeActivity.this, GameActivity.class);
        startActivity(gameIntent);
    }
}
