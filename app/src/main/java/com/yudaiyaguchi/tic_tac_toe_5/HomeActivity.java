package com.yudaiyaguchi.tic_tac_toe_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yudaiyaguchi.tic_tac_toe_5.R;

public class HomeActivity extends AppCompatActivity {
    private int boardSize = 13;
    private RadioGroup radioBoardSizeGroup;
    private RadioGroup radioUserTurnGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // radio button
        radioBoardSizeGroup = (RadioGroup) findViewById(R.id.boardSize);
        radioUserTurnGroup = (RadioGroup) findViewById(R.id.userTurn);

        // For each button click
        // 1) create intent
        // 2) add detailed information to the intent
        // 3) start the intent

        final Button player2 = findViewById(R.id.player2);
        player2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent =  new Intent(HomeActivity.this, GameActivity.class);
                Bundle extras = new Bundle();

                int selectedId = radioBoardSizeGroup.getCheckedRadioButtonId();
                RadioButton radioBoardSizeButton = (RadioButton) findViewById(selectedId);
                int size = Integer.parseInt(radioBoardSizeButton.getText().toString().trim().split(" ")[0]);
                extras.putInt("boardSize", size);

                extras.putChar("userTurn", 'U');

                gameIntent.putExtras(extras);
                startActivity(gameIntent);
            }
        });


        final Button level1 = findViewById(R.id.level1);
        level1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent =  new Intent(HomeActivity.this, GameActivity.class);
                Bundle extras = new Bundle();

                int selectedSizeId = radioBoardSizeGroup.getCheckedRadioButtonId();
                RadioButton radioBoardSizeButton = ((RadioButton) findViewById(selectedSizeId));
                int size = Integer.parseInt(radioBoardSizeButton.getText().toString().trim().split(" ")[0]);
                extras.putInt("boardSize", size);

                int selectedTurnId = radioUserTurnGroup.getCheckedRadioButtonId();
                RadioButton radioUserTurnButton = ((RadioButton) findViewById(selectedTurnId));
                String temp = radioUserTurnButton.getText().toString();
                extras.putChar("userTurn", temp.charAt(temp.length()-1));

                extras.putInt("AILevel", 1);

                gameIntent.putExtras(extras);
                startActivity(gameIntent);
            }
        });

        final Button level2 = findViewById(R.id.level2);
        level2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent =  new Intent(HomeActivity.this, GameActivity.class);
                Bundle extras = new Bundle();

                int selectedSizeId = radioBoardSizeGroup.getCheckedRadioButtonId();
                RadioButton radioBoardSizeButton = ((RadioButton) findViewById(selectedSizeId));
                int size = Integer.parseInt(radioBoardSizeButton.getText().toString().trim().split(" ")[0]);
                extras.putInt("boardSize", size);

                int selectedTurnId = radioUserTurnGroup.getCheckedRadioButtonId();
                RadioButton radioUserTurnButton = ((RadioButton) findViewById(selectedTurnId));
                String temp = radioUserTurnButton.getText().toString();
                extras.putChar("userTurn", temp.charAt(temp.length()-1));

                extras.putInt("AILevel", 2);

                gameIntent.putExtras(extras);
                startActivity(gameIntent);
            }
        });

        final Button level3 = findViewById(R.id.level3);
        level3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent =  new Intent(HomeActivity.this, GameActivity.class);
                Bundle extras = new Bundle();

                int selectedSizeId = radioBoardSizeGroup.getCheckedRadioButtonId();
                RadioButton radioBoardSizeButton = ((RadioButton) findViewById(selectedSizeId));
                int size = Integer.parseInt(radioBoardSizeButton.getText().toString().trim().split(" ")[0]);
                extras.putInt("boardSize", size);

                int selectedTurnId = radioUserTurnGroup.getCheckedRadioButtonId();
                RadioButton radioUserTurnButton = ((RadioButton) findViewById(selectedTurnId));
                String temp = radioUserTurnButton.getText().toString();
                extras.putChar("userTurn", temp.charAt(temp.length()-1));

                extras.putInt("AILevel", 3);

                gameIntent.putExtras(extras);
                startActivity(gameIntent);
            }
        });
    }
}
