package com.yudaiyaguchi.tic_tac_toe_5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.yudaiyaguchi.tic_tac_toe_5.R;

public class GameActivity extends AppCompatActivity {
    private BoardView boardView;
    private BoardState boardState;
    private Button unmoveButton;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        boardView = (BoardView) findViewById(R.id.board);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode

        MobileAds.initialize(this, "ca-app-pub-1376392773501409/1123341491");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // get the setting values from homeActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int boardSize = extras.getInt("boardSize", 13);
//        int boardSize = 3;   // for debugging // small board is easier to debug
        char userTurn = extras.getChar("userTurn");
        int aiLevel = extras.getInt("AILevel", 0);

        boardState = new BoardState(boardSize, 5, aiLevel,3);

        boardView.setBoardState(boardState);
        boardView.setGameActivity(this);

        // 1 : 2 players, 2 : 1 player and the user goes first, 3 : 1 player and the AI goes first
        if(userTurn == 'U') {
            boardState.setIsMultiPlayer();
            boardState.setUserTurn('X');
            boardState.setCurrentPlayer('X');
        } else if(userTurn == 'X') {
            boardState.setUserTurn('X');
            boardState.setAiTurn('O');
            boardState.setCurrentPlayer('X');
        } else {
            boardState.setUserTurn('O');
            boardState.setAiTurn('X');
            boardState.setCurrentPlayer('X');
            boardState.setIsFirstMoveTrue();
            boardState.aiMove();
            boardState.setIsFirstMoveFalse();
            boardView.invalidate();
        }

        unmoveButton = (Button) findViewById(R.id.undo);
        unmoveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!boardState.getIsMultiPlayer() && boardState.getMoveCounter() <= 2) {
                    newGame();
                    return;
                }

                boardState.unMove();
                boardView.invalidate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        if(item.getItemId() == R.id.action_home) {
            Intent homeIntent =  new Intent(GameActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameEnded(char c) {
        String msg = (c == 'D') ? "Good Game!    Tie" : "Good Game! " + c + " won";

        new AlertDialog.Builder(this).setTitle(msg).setMessage("Please click New Game").show();
        unmoveButton.setEnabled(false);
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    private void newGame() {
        this.recreate();
    }
}