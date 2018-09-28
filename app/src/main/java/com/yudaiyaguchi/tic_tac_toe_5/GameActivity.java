package com.yudaiyaguchi.tic_tac_toe_5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yudaiyaguchi.tic_tac_toe_5.R;

public class GameActivity extends AppCompatActivity {
    private BoardView boardView;
    private BoardState boardState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        boardView = (BoardView) findViewById(R.id.board);

        // get the setting values from homeActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int boardSize = extras.getInt("boardSize", 13);
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

        final Button button = (Button) findViewById(R.id.undo);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(boardState.getMoveCounter() <= 2) {
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
        String msg = (c == 'D') ? "Game Ended. Tie" : "GameEnded. " + c + " win";

        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        this.recreate();
    }
}