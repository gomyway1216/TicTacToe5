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
    int aiLevel = 2;

    public AI(int aiLevel, int depth) {
        this.aiLevel = aiLevel;
        this.aiLevel = 3;
        this.maxDepth = depth;
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
        int sumMoveValue2 = 0;
        String finalMove = null;
        String finalMove2 = null;

        Log.d("AI Level", "AI Level : " + aiLevel);
        boolean isBreak = false;
        for (int i = rMin; i <= rMax; i++) {
            if (isBreak) break;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    // ai move
                    if(counter == 5 && i == 6 && j == 9) {
                        Log.d("breakPoint", "breakPoint : ");
                    }

                    int attackValueTemp = 0;
                    if(aiLevel == 1)
                        attackValueTemp = evaluate1(state.applyMove(i, j), i, j, state.getAiTurn());
                    else if(aiLevel == 2)
                        attackValueTemp = evaluate2(state.applyMove(i, j), i, j, state.getAiTurn());
                    else if(aiLevel == 3)
                        attackValueTemp = evaluate3(state.applyMove(i, j), i, j, state.getAiTurn());

//                    int attackValueTemp = aiLevel == 2 ? evaluate2(state.applyMove(i, j), i, j,
//                            state.getAiTurn()) : evaluate1(state.applyMove(i, j), i, j, state.getAiTurn());
                    // opponent move
                    int protectValueTemp = 0;
                    if(aiLevel == 1)
                        protectValueTemp = -evaluate1(state.applyMove(i, j), i, j, state.getUserTurn());
                    else if(aiLevel == 2)
                        protectValueTemp = -evaluate2(state.applyMove(i, j), i, j,state.getUserTurn());
                    else if(aiLevel == 3)
                        protectValueTemp = -evaluate3(state.applyMove(i, j), i, j,state.getUserTurn());

                    int sumMoveValueTemp = 2 * attackValueTemp + protectValueTemp;

//                    if(aiLevel == 3)
//                        sumMoveValueTemp = 3 * attackValueTemp + 3 * protectValueTemp;

                    if (sumMoveValue < sumMoveValueTemp) {
                        sumMoveValue = sumMoveValueTemp;
                        finalMove = i + "," + j;
                    }
//                    if(sumMoveValue2 < sumMoveValueTemp && sumMoveValue > sumMoveValueTemp) {
//                        sumMoveValue2 = sumMoveValueTemp;
//                        finalMove2 = i + "," + j;
//                    }
                }
            }
        }
//        if(finalMove2 == null)
//            finalMove2 = finalMove;
//        return aiLevel == 3 ? finalMove2 : finalMove;
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
            int attackValue = evaluate2(state.applyMove(row, col), row, col, state.getAiTurn());
            int protectValue = -evaluate2(state.applyMove(row, col), row, col, state.getUserTurn());
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
            return evaluate2(state, row, col, state.getAiTurn());

        return v;
    }


    public int maxValue2(BoardState state, int maxDepth, int currentDepth, int alpha, int beta, int row, int col) {
        if (currentDepth == 0) {
            int attackValue = -evaluate2(state.applyMove(row, col), row, col, state.getUserTurn());
            int protectValue = evaluate2(state.applyMove(row, col), row, col, state.getAiTurn());
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
            return -evaluate2(state, row, col, state.getUserTurn());

        return v;
    }

    private int evaluate1(BoardState state, int row, int col, char turn) {
        char current = turn;
        String horizontal = getPattern(state, row, col, 0, 1, turn);
        String vertical = getPattern(state, row, col, 1, 0, turn);
        String diagonalLTR = getPattern(state, row, col, 1, 1, turn);
        String diagonalRTL = getPattern(state, row, col, 1, -1, turn);
        int eval = 0;

        String row5 = current+""+current+""+current+""+current+""+current;
        if(horizontal.contains(row5) || vertical.contains(row5) || diagonalLTR.contains(row5)
                || diagonalRTL.contains(row5))  eval+= 100000000;

        String row4Win = " " + current+""+current+""+current+""+current+ " ";
        if(horizontal.contains(row4Win))    eval+= 3000;
        if(vertical.contains(row4Win))      eval+= 3000;
        if(diagonalLTR.contains(row4Win))   eval+= 3000;
        if(diagonalRTL.contains(row4Win))   eval+= 3000;

        String normal4A = " " + current+""+current+""+current+""+current;
        String normal4B = current+""+current+""+current+""+current + " ";
        if(horizontal.contains(normal4A))    eval+= 1000;
        if(vertical.contains(normal4A))      eval+= 1000;
        if(diagonalLTR.contains(normal4A))   eval+= 1000;
        if(diagonalRTL.contains(normal4A))   eval+= 1000;
        if(horizontal.contains(normal4B))    eval+= 1000;
        if(vertical.contains(normal4B))      eval+= 1000;
        if(diagonalLTR.contains(normal4B))   eval+= 1000;
        if(diagonalRTL.contains(normal4B))   eval+= 1000;

        String row3 = " " + current+""+current+""+current+ " ";
        if(horizontal.contains(row3))   eval+= 1000;
        if(vertical.contains(row3))     eval+= 1000;
        if(diagonalLTR.contains(row3))  eval+= 1000;
        if(diagonalRTL.contains(row3))  eval+= 1000;

        String broken3T1 = " " + current+""+current+ " " + current + " ";
        String broken3T2 = " " + current+ " "+current+""+current + " ";
        if(horizontal.contains(broken3T1))   eval+= 1000;
        if(vertical.contains(broken3T2))     eval+= 1000;
        if(diagonalLTR.contains(broken3T1))  eval+= 1000;
        if(diagonalRTL.contains(broken3T2))  eval+= 1000;
        if(horizontal.contains(broken3T1))   eval+= 1000;
        if(vertical.contains(broken3T2))     eval+= 1000;
        if(diagonalLTR.contains(broken3T1))  eval+= 1000;
        if(diagonalRTL.contains(broken3T2))  eval+= 1000;

        String row2 = " " + turn + turn + " ";
        if(horizontal.contains(row2))   eval+= 2000;
        if(vertical.contains(row2))     eval+= 2000;
        if(diagonalLTR.contains(row2))  eval+= 2000;
        if(diagonalRTL.contains(row2))  eval+= 2000;

        String normal3A = " " + turn + turn + turn;
        String normal3B = turn + turn + turn + " ";
        if(horizontal.contains(normal3A))   eval+= 1000;
        if(vertical.contains(normal3A))     eval+= 1000;
        if(diagonalLTR.contains(normal3A))  eval+= 1000;
        if(diagonalRTL.contains(normal3A))  eval+= 1000;
        if(horizontal.contains(normal3B))   eval+= 1000;
        if(vertical.contains(normal3B))     eval+= 1000;
        if(diagonalLTR.contains(normal3B))  eval+= 1000;
        if(diagonalRTL.contains(normal3B))  eval+= 1000;

        return turn == state.getAiTurn() ? eval+5 : -(eval+5);
    }

    private int evaluate2(BoardState state, int row, int col, char turn) {
        char current = turn;
        String horizontal = getPattern(state, row, col, 0, 1, turn);
        String vertical = getPattern(state, row, col, 1, 0, turn);
        String diagonalLTR = getPattern(state, row, col, 1, 1, turn);
        String diagonalRTL = getPattern(state, row, col, 1, -1, turn);
        int eval = 0;

        String row5 = current+""+current+""+current+""+current+""+current;
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

    private int evaluate3(BoardState state, int row, int col, char turn) {
        char current = turn;
        String horizontal = "A" + getPattern(state, row, col, 0, 1, turn) + "A";
        String vertical = "A" + getPattern(state, row, col, 1, 0, turn) + "A";
        String diagonalLTR = "A" + getPattern(state, row, col, 1, 1, turn) + "A";
        String diagonalRTL = "A" + getPattern(state, row, col, 1, -1, turn) + "A";
        int eval = 0;

        String row5 = current+""+current+""+current+""+current+""+current;
        if(horizontal.contains(row5) || vertical.contains(row5) || diagonalLTR.contains(row5)
                || diagonalRTL.contains(row5))  eval+= 100000000;


        String row4Win = " " + current+""+current+""+current+""+current+ " ";
        if(horizontal.contains(row4Win))    eval+= 1000000;
        if(vertical.contains(row4Win))      eval+= 1000000;
        if(diagonalLTR.contains(row4Win))   eval+= 1000000;
        if(diagonalRTL.contains(row4Win))   eval+= 1000000;

        boolean thread4 = false;
        String normal4A = " " + current+""+current+""+current+""+current + "A";
        String normal4B = "A" + current+""+current+""+current+""+current + " ";
        if(horizontal.contains(normal4A))    {
            eval+= 10000;
            thread4 = true;
        }
        if(vertical.contains(normal4A))      {
            eval+= 10000;
            thread4 = true;
        }
        if(diagonalLTR.contains(normal4A))   {
            eval+= 10000;
            thread4 = true;
        }
        if(diagonalRTL.contains(normal4A))   {
            eval+= 10000;
            thread4 = true;
        }
        if(horizontal.contains(normal4B))    {
            eval+= 10000;
            thread4 = true;
        }
        if(vertical.contains(normal4B))      {
            eval+= 10000;
            thread4 = true;
        }
        if(diagonalLTR.contains(normal4B))   {
            eval+= 10000;
            thread4 = true;
        }
        if(diagonalRTL.contains(normal4B))   {
            eval+= 500000;
            thread4 = true;
        }

        String row3 = " " + current+""+current+""+current+ " ";
        int thread3 = 0;
        if(horizontal.contains(row3))   {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 5000;
            thread3++;
        }
        if(vertical.contains(row3))     {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 5000;
            thread3++;
        }
        if(diagonalLTR.contains(row3))  {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 5000;
            thread3++;
        }
        if(diagonalRTL.contains(row3))  {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 5000;
            thread3++;
        }

        String broken3T1 = " " + current+""+current+ " " + current + " ";
        String broken3T2 = " " + current+ " " +current+""+current + " ";
        int threadBroken3 = 0;
        if(horizontal.contains(broken3T1))   {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(vertical.contains(broken3T1))     {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(diagonalLTR.contains(broken3T1))  {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(diagonalRTL.contains(broken3T1))  {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(horizontal.contains(broken3T2))   {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(vertical.contains(broken3T2))     {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(diagonalLTR.contains(broken3T2))  {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }
        if(diagonalRTL.contains(broken3T2))  {
            if(thread4) eval += 1000000;
//            else if(thread3) eval += 100000;
            eval+= 1500;
            threadBroken3++;
        }

        if(thread3 > 1)
            eval+= 100000;
        else if(thread3 > 0 && threadBroken3 > 0)
            eval+= 80000;
        else if(threadBroken3 > 1)
            eval+= 50000;



        String normal3A = " " + turn + turn + turn + "A";
        String normal3B = "A" + turn + turn + turn + " ";
        if(horizontal.contains(normal3A))   {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(vertical.contains(normal3A))     {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(diagonalLTR.contains(normal3A))  {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(diagonalRTL.contains(normal3A))  {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(horizontal.contains(normal3B))   {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(vertical.contains(normal3B))     {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(diagonalLTR.contains(normal3B))  {
//            if(thread4) eval += 10000;
            eval+= 500;
        }
        if(diagonalRTL.contains(normal3B))  {
//            if(thread4) eval += 10000;
            eval+= 500;
        }

        String broken3A = " " + turn + turn + " " + turn + 'A';
        String broken3B = "A" + turn + turn + " " + turn + " ";
        String broken3C = " " + turn + " "  + turn+ turn + 'A';
        String broken3D = "A"+ turn + " " + turn + turn + " ";

        if(horizontal.contains(broken3A))   {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(vertical.contains(broken3A))     {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalLTR.contains(broken3A))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalRTL.contains(broken3A))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(horizontal.contains(broken3B))   {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(vertical.contains(broken3B))     {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalLTR.contains(broken3B))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalRTL.contains(broken3B))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }

        if(horizontal.contains(broken3C))   {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(vertical.contains(broken3C))     {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalLTR.contains(broken3C))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalRTL.contains(broken3C))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(horizontal.contains(broken3D))   {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(vertical.contains(broken3D))     {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalLTR.contains(broken3D))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }
        if(diagonalRTL.contains(broken3D))  {
//            if(thread4) eval += 10000;
            eval+= 400;
        }

        int i;
        for(i = horizontal.length()/2; i < horizontal.length(); i++) {
            if(horizontal.charAt(i) != ' ') break;
        }
        if(horizontal.charAt(i) != 'A' && i+1 < horizontal.length() && horizontal.charAt(i+1) != 'A') {
            eval+=500;
        }
        for(i = horizontal.length()/2; i >= 0; i--) {
            if(horizontal.charAt(i) != ' ') break;
        }
        if(horizontal.charAt(i) != 'A' && i-1 >= 0 && horizontal.charAt(i-1) != 'A') {
            eval+=500;
        }

        for(i = vertical.length()/2; i < vertical.length(); i++) {
            if(vertical.charAt(i) != ' ') break;
        }
        if(vertical.charAt(i) != 'A' && i+1 < vertical.length() && vertical.charAt(i+1) != 'A') {
            eval+=500;
        }
        for(i = vertical.length()/2; i >= 0; i--) {
            if(vertical.charAt(i) != ' ') break;
        }
        if(vertical.charAt(i) != 'A' && i-1 >= 0 && vertical.charAt(i-1) != 'A') {
            eval+=500;
        }

        for(i = diagonalLTR.length()/2; i < diagonalLTR.length(); i++) {
            if(diagonalLTR.charAt(i) != ' ') break;
        }
        if(diagonalLTR.charAt(i) != 'A' && i+1 < diagonalLTR.length() && diagonalLTR.charAt(i+1) != 'A') {
            eval+=500;
        }
        for(i = diagonalLTR.length()/2; i >= 0; i--) {
            if(diagonalLTR.charAt(i) != ' ') break;
        }
        if(diagonalLTR.charAt(i) != 'A' && i-1 >= 0 && diagonalLTR.charAt(i-1) != 'A') {
            eval+=500;
        }

        for(i = diagonalRTL.length()/2; i < diagonalRTL.length(); i++) {
            if(diagonalRTL.charAt(i) != ' ') break;
        }
        if(diagonalRTL.charAt(i) != 'A' && i+1 < diagonalRTL.length() && diagonalRTL.charAt(i+1) != 'A') {
            eval+=500;
        }
        for(i = diagonalRTL.length()/2; i >= 0; i--) {
            if(diagonalRTL.charAt(i) != ' ') break;
        }
        if(diagonalRTL.charAt(i) != 'A' && i-1 >= 0 && diagonalRTL.charAt(i-1) != 'A') {
            eval+=500;
        }



        String row2 = " " + turn + turn + " ";
        if(horizontal.contains(row2))   {
//            if(thread4) eval += 100000;
//            if(thread3) eval += 10000;
            eval+= 300;
        }
        if(vertical.contains(row2))     {
//            if(thread4) eval += 100000;
//            if(thread3) eval += 10000;
            eval+= 300;
        }
        if(diagonalLTR.contains(row2))  {
//            if(thread4) eval += 100000;
//            if(thread3) eval += 10000;
            eval+= 300;
        }
        if(diagonalRTL.contains(row2))  {
//            if(thread4) eval += 100000;
//            if(thread3) eval += 10000;
            eval+= 300;
        }

        return turn == state.getAiTurn() ? eval+5 : -(eval+5);
    }
}
