package com.yudaiyaguchi.tic_tac_toe_5;


import android.util.Log;

import java.util.HashMap;
import java.util.StringTokenizer;

public class BoardState {
    int boardSize;
    int wChain;
    char[][] board;
//    public char aiTurn;
    private char userTurn;
    private char aiTurn;
    private char currentPlayer;
    private AI ai;
    // If I make the chain to global variable, it gets much complicated
//    // movement for 1st player
//    Map<String, Integer> chainsForFirst;
//    // movement for 2nd player
//    Map<String, Integer> chainsForSecond;
//    public char turn;
    private boolean isFirstMove = false;

    private int maxRow;  // I define them to narrow down search space
    private int minRow;
    private int maxCol;  // usually starting from center and it exclude
    private int minCol;
    // more than 2 outside of most outside stone

    public BoardState(int boardSize, int wChain, int maxDepth) {
//        turn = 'X';
        this.boardSize = boardSize;
        this.wChain = wChain;
        board = new char[boardSize][boardSize];
//        chainsForFirst = new HashMap<String, Integer>();
        // AI should be initialized here?
        ai = new AI();
        ai.setMaxDepth(maxDepth);
        inizializeBoard();
        maxRow = boardSize/2;
        minRow = boardSize/2;
        maxCol = boardSize/2;
        minCol = boardSize/2;
    }

    /**
     * Copy constructor
     */
    public BoardState(BoardState other) {
        this.boardSize = other.boardSize;
        this.wChain = other.wChain;
        this.board = new char[boardSize][boardSize];
        this.isFirstMove = other.isFirstMove;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.board[i][j] = other.board[i][j];
            }
        }
//        this.chainsForFirst = other.chainsForFirst;
//        this.chainsForSecond = other.chainsForSecond;
    }

    private void inizializeBoard() {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean getIsFirstMove(){
        return isFirstMove;
    }

    public void setIsFirstMoveTrue() { isFirstMove = true;}
    public void setIsFirstMoveFalse() {
        isFirstMove = false;
    }


    public void changeTurn() {
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    public void newGame() {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCharOfBox(int row, int col) {
        return board[row][col];
    }

    public void setUserTurn(char userTurn) {
        this.userTurn = userTurn;
    }

    public char getUserTurn() {
        return userTurn;
    }

    public void setAiTurn(char aiTurn) {
        this.aiTurn = aiTurn;
    }

    public char getAiTurn() {
        return aiTurn;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    // first let's treat this as goal check
    // keep track of the chain of first and the length
    public HashMap<String, Integer> findChain() {
        //    // movement for 1
        // st player
    HashMap<String, Integer> chains = new HashMap<String, Integer>();
        String TAG = "findChain";

        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                // to make cpu strong, we have to care not only the bot's move but also opponent move
                if(board[i][j] != ' ') {
                    char check = board[i][j];
                    int startCol = j+1;
                    int counterRow = 1;
                    // check row from left to right
                    while(startCol < boardSize && check == board[i][startCol]) {
                        counterRow++;
                        startCol++;
                    }

                    int startRow = i+1;
                    int counterCol = 1;
                    // check col from top to down
                    while(startRow < boardSize && check == board[startRow][j]) {
                        counterCol++;
                        startRow++;
                    }

                    int startDiagCol = j+1;
                    int startDiagRow = i+1;
                    int counterDiag = 1;
                    // check diagonal from top left to bottom right
                    while(startDiagCol < boardSize && startDiagRow < boardSize && check == board[startDiagRow][startDiagCol]) {
                        counterDiag++;
                        startDiagCol++;
                        startDiagRow++;
                    }

                    int startDiag2Row = i+1;
                    int startDiag2Col = j-1;
                    int counterDiag2 = 1;
                    // check diagonal from top right to bottom left
                    while(startDiag2Row < boardSize && startDiag2Col >= 0 && check == board[startDiag2Row][startDiag2Col]) {
                        counterDiag2++;
                        startDiag2Row++;
                        startDiag2Col--;
                    }



                    if(check == 'X') {
                        chains.put("X," + i + "," + j + "," + "R", counterRow);
                        chains.put("X," + i + "," + j + "," + "C", counterCol);
                        chains.put("X," + i + "," + j + "," + "D", counterDiag);
                        chains.put("X," + i + "," + j + "," + "E", counterDiag2);
                        Log.d(TAG, " findChain= " + "X," + i + "," + j + "," + "R"+ " counter: " + counterRow) ;
                        Log.d(TAG, " findChain= " + "X," + i + "," + j + "," + "C"+ " counter: " + counterCol) ;
                        Log.d(TAG, " findChain= " + "X," + i + "," + j + "," + "D"+ " counter: " + counterDiag) ;
                        Log.d(TAG, " findChain= " + "X," + i + "," + j + "," + "E"+ " counter: " + counterDiag2) ;
                    } else if(check == 'O') {
                        chains.put("O," + i + "," + j + "," + "R", counterRow);
                        chains.put("O," + i + "," + j + "," + "C", counterCol);
                        chains.put("O," + i + "," + j + "," + "D", counterDiag);
                        chains.put("O," + i + "," + j + "," + "E", counterDiag2);
                        Log.d(TAG, " findChain= " + "O," + i + "," + j + "," + "R"+ " counter: " + counterRow);
                        Log.d(TAG, " findChain= " + "O," + i + "," + j + "," + "C"+ " counter: " + counterCol);
                        Log.d(TAG, " findChain= " + "O," + i + "," + j + "," + "D"+ " counter: " + counterDiag);
                        Log.d(TAG, " findChain= " + "O," + i + "," + j + "," + "E"+ " counter: " + counterDiag2);
                    }
                }

            }
        }
        return chains;
    }

    public boolean move(int row, int col) {
        board[row][col] = currentPlayer;
        maxRow = Math.max(maxRow, row);
        minRow = Math.min(minRow, row);
        maxCol = Math.max(maxCol, col);
        minCol = Math.min(minCol, col);
        if(isEnded(row, col, 1, 0)) return true;
        if(isEnded(row, col, 0, 1)) return true;
        if(isEnded(row, col, 1, 1)) return true;
        if(isEnded(row, col, 1, -1)) return true;
        changeTurn();
        return false;
//        if (count( board[row][col], row, col, 1, 0 ) >= 5)
//            return true;
//        if (count( board[row][col], row, col, 0, 1 ) >= 5)
//            return true;
//        if (count( board[row][col], row, col, 1, -1 ) >= 5)
//            return true;
//        if (count( board[row][col], row, col, 1, 1 ) >= 5)
//            return true;
    }

//    public void move(int row, int col) {
//        board[row][col] = turn;
//        turn= turn == 'x' ? 'o' : 'x';
//    }
//
//
//    public void unmove(int idx) {
//        board[row][col] = ' ';
//        turn = turn == 'x' ? 'o' : 'x';
//    }

    // this method creates the new board using copy constructor and apply the move
    // this doesn't affect the actual board state that user can see.
    public BoardState applyMove(char player, int row, int col) {
        BoardState afterMove = new BoardState(this);
//        afterMove.board[row][col] = player;
        afterMove.setCurrentPlayer(currentPlayer);
        afterMove.setUserTurn(this.userTurn);
        afterMove.setAiTurn(this.aiTurn);
        afterMove.maxRow = maxRow;
        afterMove.minRow = minRow;
        afterMove.maxCol = maxCol;
        afterMove.minCol = minCol;
        afterMove.move(row, col);
        return afterMove;
    }

    public boolean isEnded(int row, int col, int dr, int dc) {
        int count  = 1; // center stone should be also counted
        int r = row + dr;
        int c = col + dc;
        while(r >= 0 && r < boardSize && c >= 0 && c < boardSize && board[r][c] == currentPlayer) {
            count++;
            r += dr;
            c += dc;
        }

        if(count >= 5)  return true;

        r = row - dr;
        c = col - dc;
        while(r >= 0 && r < boardSize && c >= 0 && c < boardSize && board[r][c] == currentPlayer) {
            count++;
            r -= dr;
            c -= dc;
        }

        if(count >= 5)  return true;
        return false;
    }

    public boolean isEnded() {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if(board[i][j] != ' ') {
                    char check = board[i][j];
                    int startCol = j + 1;
                    int counterRow = 1;
                    // check row from left to right
                    while (startCol < boardSize && check == board[i][startCol]) {
                        counterRow++;
                        startCol++;
                    }

                    if(counterRow >= wChain)
                        return true;

                    int startRow = i + 1;
                    int counterCol = 1;
                    // check col from top to down
                    while (startRow < boardSize && check == board[startRow][j]) {
                        counterCol++;
                        startRow++;
                    }

                    if(counterCol >= wChain)
                        return true;

                    int startDiagCol = j + 1;
                    int startDiagRow = i + 1;
                    int counterDiag = 1;
                    // check diagonal from top left to bottom right
                    while (startDiagCol < boardSize && startDiagRow < boardSize && check == board[startDiagRow][startDiagCol]) {
                        counterDiag++;
                        startDiagCol++;
                        startDiagRow++;
                    }

                    if(counterDiag >= wChain)
                        return true;

                    int startDiag2Row = i + 1;
                    int startDiag2Col = j - 1;
                    int counterDiag2 = 1;
                    // check diagonal from top right to bottom left
                    while (startDiag2Row < boardSize && startDiag2Col >= 0 && check == board[startDiag2Row][startDiag2Col]) {
                        counterDiag2++;
                        startDiag2Row++;
                        startDiag2Col--;
                    }

                    if(counterDiag2 >= wChain)
                        return true;
                }
            }
        }

        return false;
    }

    public boolean isLegalMove(int row, int col) {
        if (board[row][col] == ' ')
            return true;
        return false;
    }

    public boolean aiMove() {
        ai.move(this);
        String stringMove = ai.getMove();
        StringTokenizer tokens = new StringTokenizer(stringMove, ",");
        String row = tokens.nextToken();
        String col = tokens.nextToken();
        return move(Integer.parseInt(row), Integer.parseInt(col));
    }

//    public BoardState applyMove(int player, int move) {
//
//    }


    public int getMaxRow() {
        return maxRow;
    }

    public int getMinRow() {
        return minRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public int getMinCol() {
        return minCol;
    }
}
