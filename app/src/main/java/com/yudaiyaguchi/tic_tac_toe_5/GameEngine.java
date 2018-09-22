package com.yudaiyaguchi.tic_tac_toe_5;

import java.util.Random;

public class GameEngine {
    private static final Random RANDOM = new Random();
    private char[][] boardstates;
    private char currentPlayer;
    private boolean ended;

    public GameEngine() {
        boardstates = new char[3][3];
        newGame();
    }

    public boolean isEnded() {
        return ended;
    }

    public char play(int y, int x) {
        if (!ended  &&  boardstates[y][x] == ' ') {
            boardstates[y][x] = currentPlayer;
            changePlayer();
        }

        return checkEnd();
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == 'X' ? '0' : 'X');
    }


    public char getBoardstates(int y, int x) {
        return boardstates[y][x];
    }

    public void newGame() {
        for(int i = 0; i < boardstates.length; i++) {
            for(int j = 0; j < boardstates.length; j++) {
                boardstates[i][j] = ' ';
            }
        }

        currentPlayer = 'X';
        ended = false;
    }

    public char checkEnd() {
        for(int i = 0; i < 3; i++) {
            // check row
            if(boardstates[i][0] != ' ' &&
                    boardstates[i][0] == boardstates[i][1] &&
                    boardstates[i][1] == boardstates[i][2]){
                ended = true;
                return boardstates[i][0];
            }

            // check col
            if(boardstates[0][i] != ' ' &&
                    boardstates[0][i] == boardstates[1][i] &&
                    boardstates[1][i] == boardstates[2][i]){
                ended = true;
                return boardstates[0][i];
            }
        }

        // check diagonal
        // left to right
        if(boardstates[0][0] != ' ' &&
                boardstates[0][0] == boardstates[1][1] &&
                boardstates[1][1] == boardstates[2][2]) {
            ended = true;
            return boardstates[0][0];
        }

        // right to left
        if(boardstates[0][2] != ' ' &&
                boardstates[0][2] == boardstates[1][1] &&
                boardstates[1][1] == boardstates[2][0]) {
            ended = true;
            return boardstates[0][2];
        }

        // check if there is any empty place
        for(int i = 0; i < boardstates.length; i++) {
            for(int j = 0; j < boardstates.length; j++) {
                if(boardstates[i][j] == ' ')
                    return ' ';
            }
        }

        // if none of them are applied, it is draw
        return 'D';
    }

    public char randomBot() {
        if(!ended) {
            int positionX = -1;
            int positionY = -1;

            do {
                // get number from 0 to 8
                positionX = RANDOM.nextInt(3);
                positionY = RANDOM.nextInt(3);
            } while(boardstates[positionX][positionY] != ' ');

            boardstates[positionX][positionY] = currentPlayer;
            changePlayer();
        }
            return checkEnd();
    }
}
