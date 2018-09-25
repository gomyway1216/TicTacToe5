package com.yudaiyaguchi.tic_tac_toe_5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yudaiyaguchi.tic_tac_toe_5.R;

public class GameActivity extends AppCompatActivity {

    private BoardView boardView;
    private GameEngine gameEngine;
    private BoardState boardState;


    private int boardSize;
    private int chain; // length to win ex 5 for gomoku
    private int mode; // 1 human vs human,
    // 2 h vs cpu, 3, cpu vs cpu
    private int cpuMode;  // 1 for random, 2 for min-max

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        boardView = (BoardView) findViewById(R.id.board);

        Intent intent = getIntent();


        // user chose to go first
        char userTurn = intent.getCharExtra("turn", 'X');
        int depth = intent.getIntExtra("depth", 1);
        Log.d("turn: "," passed turn: " + userTurn);
        Log.d("depth: "," passed depth: " + depth);

        // I will hard code the board size winning chanin and modify it later.
        boardState = new BoardState(9, 5, 1);


        gameEngine = new GameEngine();
        boardView.setGameEngine(gameEngine);

        //
        boardView.setBoardState(boardState);
        boardView.setGameActivity(this);

        if(userTurn == 'X') {
            boardState.setUserTurn('X');
            boardState.setAiTurn('O');
            boardState.setCurrentPlayer('X');
        }
        else {
            boardState.setUserTurn('O');
            boardState.setAiTurn('X');
            boardState.setCurrentPlayer('X');
            boardState.setIsFirstMoveTrue();
            boardState.aiMove();
            boardState.setIsFirstMoveFalse();
            boardView.invalidate();
        }



//        boardView.setMainActivity(this);
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
//        gameEngine.newGame();


//        boardState.newGame();
//        boardView.invalidate();

        this.recreate();

    }
}
