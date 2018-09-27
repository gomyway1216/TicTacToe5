package com.yudaiyaguchi.tic_tac_toe_5;

import android.util.Log;

// minMax with alph-beta pruning
public class AI {
    private int maxDepth;
    // this should be initialized when AI is created.
    // or whenver it is calling this AI
//    private char AITurn;
    private String move;
    String TAG = "test";
    private int maxRow;  // I define them to narrow down search space
    private int maxCol;  // usually starting from center and it exclude
    // more than 2 outside of most outside stone
    int counter = 0; // for debugging

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void move(BoardState state) {
        counter++;
        move = search(state, maxDepth);
//        move = search2(state, maxDepth);
    }

    public String getMove() {
        return move;
    }


    public String search(BoardState state, int maxDepth) {
        String bestMove = null;

        if(state.getIsFirstMove()) {
            int center = state.getBoardSize()/2;
            if(state.isLegalMove(center,center)) {
                bestMove = center + "," + center;
            }
            return bestMove;
        }

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 0 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 0 ? state.getMinCol() - 1 : state.getMinCol();

        int sumMoveValue = 0;
        String finalMove = null;

        boolean isBreak = false;
        for (int i = rMin; i <= rMax; i++) {
            if (isBreak) break;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    // ai move
                    int attackValueTemp = evaluate3(state.applyMove(i, j), i, j, state.getAiTurn());
                    // opponent move
                    int protectValueTemp = -evaluate3(state.applyMove(i, j), i, j, state.getUserTurn());
                    int sumMoveValueTemp = 2 * attackValueTemp + protectValueTemp;

                    if (sumMoveValue < sumMoveValueTemp) {
                        sumMoveValue = sumMoveValueTemp;
                        finalMove = i + "," + j;
                    }
                }
            }
        }
        return finalMove;
    }

    // potential min-max for future reference to make it even stronger
    public String search2(BoardState state, int maxDepth) {
        int v = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        String bestMove = null;

        if(state.getIsFirstMove()) {
            int center = state.getBoardSize()/2;
            if(state.isLegalMove(center,center)) {
                bestMove = center + "," + center;
            }
            return bestMove;
        }

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 0 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 0 ? state.getMinCol() - 1 : state.getMinCol();

        boolean isBreak = false;

        for (int i = rMin; i <= rMax; i++) {
            if (isBreak) break;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    int temp = minValue2(state.applyMove(i, j), maxDepth,maxDepth - 1, alpha, beta, i, j);
                    v = Math.max(v, temp);

                    if (v > alpha) {
                        alpha = v;
                        bestMove = i + "," + j;
                    }

                    if (alpha >= beta) {
                        isBreak = true;
                        break;
                    }
                }
            }
        }
        return bestMove;
    }

    public int minValue2(BoardState state, int maxDepth, int currentDepth, int alpha, int beta, int row, int col) {
        if (currentDepth == 0) {
            int attackValue = evaluate3(state.applyMove(row, col), row, col, state.getAiTurn());
            int protectValue = -evaluate3(state.applyMove(row, col), row, col, state.getUserTurn());
            int sumMoveValue = 2 * attackValue + protectValue;
            return sumMoveValue;
        }

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        int v = Integer.MAX_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    int opponentMax = maxValue2(state.applyMove(i, j), maxDepth, currentDepth - 1, alpha, beta, i, j);
                    v = Math.min(v, opponentMax);
                    if (v <= alpha) {
                        return v;
                    }

                    beta = Math.min(beta, v);
                }
            }
        }

        if (v == Integer.MAX_VALUE)
            return evaluate3(state, row, col, state.getAiTurn());

        return v;
    }


    public int maxValue2(BoardState state, int maxDepth, int currentDepth, int alpha, int beta, int row, int col) {
        if (currentDepth == 0) {
            int attackValue = -evaluate3(state.applyMove(row, col), row, col, state.getUserTurn());
            int protectValue = evaluate3(state.applyMove(row, col), row, col, state.getAiTurn());
            int sumMoveValue = 2 * attackValue + protectValue;
            return -sumMoveValue;
        }

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        int v = Integer.MIN_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    int temp = minValue2(state.applyMove(i, j), maxDepth,currentDepth - 1, alpha, beta, i, j);
                    v = Math.max(v, temp);

                    if (v >= beta)
                        return v;

                    alpha = Math.max(alpha, v);
                }
            }
        }

        if (v == Integer.MIN_VALUE)
            return -evaluate3(state, row, col, state.getUserTurn());

        return v;
    }


    private int evaluate3(BoardState state, int row, int col, char turn) {
        char current = turn;
//        char current = state.getCurrentPlayer();
        char opponent = state.getCurrentPlayer();
        String horizontal = getPattern(state, row, col, 0, 1, turn);
        Log.d("charArray1", horizontal);
        String vertical = getPattern(state, row, col, 1, 0, turn);
        String diagonalLTR = getPattern(state, row, col, 1, 1, turn);
        String diagonalRTL = getPattern(state, row, col, 1, -1, turn);
        String row5 = current+""+current+""+current+""+current+""+current;
        int eval = 0;

        if(horizontal.contains(row5) || vertical.contains(row5) || diagonalLTR.contains(row5)
                || diagonalRTL.contains(row5))  eval+= 100000000;

        String row4Win = " " + current+""+current+""+current+""+current+ " ";
        if(horizontal.contains(row4Win))    eval+= 1000000;
        if(vertical.contains(row4Win))      eval+= 1000000;
        if(diagonalLTR.contains(row4Win))   eval+= 1000000;
        if(diagonalRTL.contains(row4Win))   eval+= 1000000;

        String normal4A = " " + current+""+current+""+current+""+current;
        String normal4B = current+""+current+""+current+""+current + " ";
        if(horizontal.contains(normal4A))    eval+= 10000;
        if(vertical.contains(normal4A))      eval+= 10000;
        if(diagonalLTR.contains(normal4A))   eval+= 10000;
        if(diagonalRTL.contains(normal4A))   eval+= 10000;
        if(horizontal.contains(normal4B))    eval+= 10000;
        if(vertical.contains(normal4B))      eval+= 10000;
        if(diagonalLTR.contains(normal4B))   eval+= 10000;
        if(diagonalRTL.contains(normal4B))   eval+= 10000;

        String row3 = " " + current+""+current+""+current+ " ";
        if(horizontal.contains(row3))   eval+= 5000;
        if(vertical.contains(row3))     eval+= 5000;
        if(diagonalLTR.contains(row3))  eval+= 5000;
        if(diagonalRTL.contains(row3))  eval+= 5000;

        String broken3T1 = " " + current+""+current+ " " + current + " ";
        String broken3T2 = " " + current+ " "+current+""+current + " ";
        if(horizontal.contains(broken3T1))   eval+= 3000;
        if(vertical.contains(broken3T2))     eval+= 3000;
        if(diagonalLTR.contains(broken3T1))  eval+= 3000;
        if(diagonalRTL.contains(broken3T2))  eval+= 3000;
        if(horizontal.contains(broken3T1))   eval+= 3000;
        if(vertical.contains(broken3T2))     eval+= 3000;
        if(diagonalLTR.contains(broken3T1))  eval+= 3000;
        if(diagonalRTL.contains(broken3T2))  eval+= 3000;

        String row2 = " " + turn + turn + " ";
        if(horizontal.contains(row2))   eval+= 300;
        if(vertical.contains(row2))     eval+= 300;
        if(diagonalLTR.contains(row2))  eval+= 300;
        if(diagonalRTL.contains(row2))  eval+= 300;

        String normal3A = " " + turn + turn + turn;
        String normal3B = turn + turn + turn + " ";
        if(horizontal.contains(normal3A))   eval+= 100;
        if(vertical.contains(normal3A))     eval+= 100;
        if(diagonalLTR.contains(normal3A))  eval+= 100;
        if(diagonalRTL.contains(normal3A))  eval+= 100;
        if(horizontal.contains(normal3B))   eval+= 100;
        if(vertical.contains(normal3B))     eval+= 100;
        if(diagonalLTR.contains(normal3B))  eval+= 100;
        if(diagonalRTL.contains(normal3B))  eval+= 100;

        return turn == state.getAiTurn() ? eval+5 : -(eval+5);
    }

    private String getPattern(BoardState state, int row, int col, int dr, int dc, char turn) {
        int r = row + dr;
        int c = col + dc;
        int k = 1;
        char tempOpponent = turn == 'X' ? 'O' : 'X';
        char[] chain = new char[2*state.wChain-1]; // the passed stone position is the center
        for(int i = 0; i < chain.length; i++)chain[i]='A';
        chain[chain.length/2]= turn;
        while(r >= 0 && r < state.boardSize && c >= 0 && c < state.boardSize && k < state.wChain && state.board[r][c] != tempOpponent) {
            chain[state.wChain-1+k] = state.board[r][c];
            r += dr;
            c += dc;
            k++;
        }

        r = row - dr;
        c = col - dc;
        k = 1;
        while(r >= 0 && r < state.boardSize && c >= 0 && c < state.boardSize && k < state.wChain && state.board[r][c] != tempOpponent) {
            chain[state.wChain-1-k] = state.board[r][c];
            r -= dr;
            c -= dc;
            k++;
        }

        return new String(chain);
    }
}
