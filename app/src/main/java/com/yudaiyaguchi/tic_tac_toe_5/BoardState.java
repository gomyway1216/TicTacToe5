package com.yudaiyaguchi.tic_tac_toe_5;

import java.util.StringTokenizer;

public class BoardState {
    int boardSize;
    int wChain;
    char[][] board;
    private char userTurn;
    private char aiTurn;
    private char currentPlayer;
    private AI ai;
    private boolean isFirstMove = false;

    private int maxRow;  // I define them to narrow down search space
    private int minRow;
    private int maxCol;  // usually starting from center and it exclude
    private int minCol;
    private int counter = 0;
    private boolean isMultiPlayer = false;
    private String[] moves;
    private int moveCounter = 0;

    public BoardState(int boardSize, int wChain, int aiLevel, int maxDepth) {
//        turn = 'X';
        this.boardSize = boardSize;
        this.wChain = wChain;
        board = new char[boardSize][boardSize];
//        chainsForFirst = new HashMap<String, Integer>();
        // AI should be initialized here?
        ai = new AI(aiLevel, maxDepth);
        moves = new String[boardSize * boardSize];
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
        this.moves = other.moves;
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
        moves[moveCounter] = row + "," + col;
        moveCounter++;
        changeTurn();
        counter++;
        return false;
    }

    // this method creates the new board using copy constructor and apply the move
    // this doesn't affect the actual board state that user can see.
    public BoardState applyMove(int row, int col) {
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

    public boolean isBoardFilled() {
        return counter == boardSize * boardSize;
    }

    public void setIsMultiPlayer() {
        isMultiPlayer = true;
    }

    public boolean getIsMultiPlayer() {
        return isMultiPlayer;
    }

    public void unMove() {
        moveCounter--;
        String deletedMove1 = moves[moveCounter];
        moves[moveCounter] = "";

        moveCounter--;
        String deletedMove2 = moves[moveCounter];
        moves[moveCounter] = "";

        String[] temp1 = deletedMove1.split(",");
        int row1 = Integer.parseInt(temp1[0]);
        int col1 = Integer.parseInt(temp1[1]);
        String[] temp2 = deletedMove2.split(",");
        int row2 = Integer.parseInt(temp2[0]);
        int col2 = Integer.parseInt(temp2[1]);

        board[row1][col1] = ' ';
        board[row2][col2] = ' ';
    }

    public int getMoveCounter() {
        return moveCounter;
    }
}

