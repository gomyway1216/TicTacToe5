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
//        move = alphabetaSearch(state, maxDepth);
        move = search(state, maxDepth);
// ;
    }

    public String getMove() {
        return move;
    }

    public String alphabetaSearch(BoardState state, int maxDepth) {
        // function ALPHA-BETA-SEARCH(state) returns an action v ←MAX-VALUE(state,−∞,+∞)
        // return the action in ACTIONS(state) with value v
        int v = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        String bestMove = null;
        // returns the move with the smallest index in the case of ties.
        // How can I do that? It can only return v value

        if(state.getIsFirstMove()) {
            int center = state.getBoardSize()/2;
            if(state.isLegalMove(center,center)) {
//                Log.d(TAG, "inside if best move= " + bestMove) ;
                bestMove = center + "," + center;
            }

            return bestMove;
        }

//        int count =0;

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 0 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 0 ? state.getMinCol() - 1 : state.getMinCol();

        if(counter == 4)
            Log.d("MinMax", "MinMaxBreak");

        boolean isBreak = false;
        for (int i = rMin; i <= rMax; i++) {
            if(isBreak) break;
//            i = 6;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    // it returns always smallest number of double
                    int temp = minValue(state.applyMove(i, j), maxDepth,maxDepth - 1, alpha, beta, i, j);
                    Log.d("first Loop temp : ", "first Loop temp : " + i + "," + j + "v value " + v);
                    v = Math.max(v, temp);


                    // v = getBestScore()
//                    Log.d(TAG, "before alpha =" + alpha) ;
                    Log.d("first Loop v Value : ", "first Loop v Value : " + i + "," + j + "v value " + v);
                    if (v > alpha) {
//                        count++;
                        alpha = v;
                        Log.d("bestMove : ", "bestMove : " + i + "," + j + "v value " + v);
                        bestMove = i + "," + j;
//                        Log.d(TAG, "v>alpha and v = " + v + "alpha =" + alpha) ;
                    }

                    if (alpha >= beta) {
                        Log.d("Breaking first loop", "Breaking first loop" + i + "," + j + "v value " + v);
                        isBreak = true;
                        break;
                    }


                    // alpha = Math.max(alpha, v);
                }
            }
        }

//        Log.d(TAG, " best move= " + bestMove) ;
//        Log.d(TAG, " best count= " + count) ;

        return bestMove;
    }


    public int maxValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta, int row, int col) {
        if (currentDepth == 0) {
            if(counter == 3 && row == 4 && col == 1)
                Log.d("check!", "check! i, j : " + row + ", " + col );
            return evaluate3(state, row, col, state.getUserTurn());

        }

//        if(counter == 4) {
//            Log.d("check!", "check! i, j : " + row + ", " + col );
//        }


        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        int v = Integer.MIN_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    int temp = minValue(state.applyMove(i, j), maxDepth,currentDepth - 1, alpha, beta, i, j);
                    v = Math.max(v, temp);

                    if (v >= beta)
                        return v;

                    alpha = Math.max(alpha, v);

//                    if(beta<=alpha)
//                        break;
                }
            }
        }

        if (v == Integer.MIN_VALUE) {
//            Log.d(TAG, "max score = " + evaluate(state)) ;

            return evaluate3(state, row, col, state.getUserTurn());
        }

        return v;
    }

    public int minValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta, int row, int col) {
        if (currentDepth == 0)
            return evaluate3(state, row, col, state.getAiTurn());

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        int v = Integer.MAX_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    int opponentMax = maxValue(state.applyMove(i, j), maxDepth, currentDepth - 1, alpha, beta, i, j);
                    int opponentValue = evaluate3(state, i, j, state.getUserTurn());
                    // if opponentMax it is  negative
                    v = Math.min(v, opponentMax); // this is trying to find the smallest value
                    if (v <= alpha) {
                        Log.d("second loop : v Value", "second loop : v Value : " + v);
                        return v;
                    }

                    beta = Math.min(beta, v);

//                    if (beta <= alpha)
//                         break;
                }
            }
        }

            // it doesn't reach here and I am sure it is returning value at if (v <= alpha) above
//        Log.d("second loop : v Value", "second loop : v Value : " + v);
        if (v == Integer.MAX_VALUE) {
//            Log.d(TAG, "min score = " + evaluate(state)) ;

            return evaluate3(state, row, col, state.getAiTurn());
        }

        return v;
    }

    public String search(BoardState state, int maxDepth) {
        // function ALPHA-BETA-SEARCH(state) returns an action v ←MAX-VALUE(state,−∞,+∞)
        // return the action in ACTIONS(state) with value v
        int v = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        String bestMove = null;
        // returns the move with the smallest index in the case of ties.
        // How can I do that? It can only return v value

        if(state.getIsFirstMove()) {
            int center = state.getBoardSize()/2;
            if(state.isLegalMove(center,center)) {
//                Log.d(TAG, "inside if best move= " + bestMove) ;
                bestMove = center + "," + center;
            }

            return bestMove;
        }

//        int count =0;

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 0 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 0 ? state.getMinCol() - 1 : state.getMinCol();

        if(counter == 4)
            Log.d("MinMax", "MinMaxBreak");

        int attackValue = Integer.MIN_VALUE;
        int protectValue = Integer.MIN_VALUE;
        String attackTemp = null;
        String protectTemp = null;

        boolean isBreak = false;
        for (int i = rMin; i <= rMax; i++) {
            if (isBreak) break;
//            i = 6;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    // every single time, AI will choose to attack or protect

                    // ai move
                    int attackValueTemp = evaluate3(state.applyMove(i, j), i, j, state.getAiTurn());
                    if (attackValue < attackValueTemp) {
                        attackValue = attackValueTemp;
                        attackTemp = i + "," + j;
                    }
                    // opponent move
                    int protectValueTemp = -evaluate3(state.applyMove(i, j), i, j, state.getUserTurn());
                    if (protectValue < protectValueTemp) {
                        protectValue = protectValueTemp;
                        protectTemp = i + "," + j;
                    }
                }
            }
        }
            if(attackValue > protectValue)
                bestMove = attackTemp;
            else
                bestMove = protectTemp;

        return bestMove;
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

        Log.d("evaluate3", "evaluate3 is called");

        if(horizontal.contains(row5) || vertical.contains(row5) || diagonalLTR.contains(row5)
                || diagonalRTL.contains(row5))  eval+= 1000000000;

//        if(row == 0 && col == 1)    Log.d("positions", "This should be huge! " + eval);
//        if(row == 6 && col == 3 && counter == 4) {
//            Log.d("positions", "This should be huge! " + horizontal);
//            Log.d("positions", "This should be huge! " + vertical);
//            Log.d("positions", "This should be huge! " + diagonalLTR);
//            Log.d("positions", "This should be huge! " + diagonalRTL);
//
//        }


        Log.d("positions", "This should be huge! " + row + "," + col + " val " + eval);

        String row4Win = " " + current+""+current+""+current+""+current+ " ";
        if(horizontal.contains(row4Win))    eval+= 10000000;
        if(vertical.contains(row4Win))      eval+= 10000000;
        if(diagonalLTR.contains(row4Win))   eval+= 10000000;
        if(diagonalRTL.contains(row4Win))   eval+= 10000000;

        String normal4A = " " + current+""+current+""+current+""+current;
        String normal4B = current+""+current+""+current+""+current + " ";
//        if(horizontal.contains(row4Win) && vertical.contains(row4Win) ||
//                horizontal.contains(row4Win) && diagonalLTR.contains(row4Win)
//                || horizontal.contains(row4Win) && diagonalRTL.contains(row4Win)
//                || vertical.contains(row4Win) && diagonalLTR.contains(row4Win)
//                || vertical.contains(row4Win) && diagonalRTL.contains(row4Win)
//                || vertical.contains(row4Win) && )
        if(horizontal.contains(normal4A))    eval+= 100000;
        if(vertical.contains(normal4A))      eval+= 100000;
        if(diagonalLTR.contains(normal4A))   eval+= 100000;
        if(diagonalRTL.contains(normal4A))   eval+= 100000;
        if(horizontal.contains(normal4B))    eval+= 100000;
        if(vertical.contains(normal4B))      eval+= 100000;
        if(diagonalLTR.contains(normal4B))   eval+= 100000;
        if(diagonalRTL.contains(normal4B))   eval+= 100000;

        String row3 = " " + current+""+current+""+current+ " ";
        if(horizontal.contains(row3))   eval+= 50000;
        if(vertical.contains(row3))     eval+= 50000;
        if(diagonalLTR.contains(row3))  eval+= 50000;
        if(diagonalRTL.contains(row3))  eval+= 50000;

        String broken3T1 = " " + current+""+current+ " " + current + " ";
        String broken3T2 = " " + current+ " "+current+""+current + " ";
        if(horizontal.contains(broken3T1))   eval+= 30000;
        if(vertical.contains(broken3T2))     eval+= 30000;
        if(diagonalLTR.contains(broken3T1))  eval+= 30000;
        if(diagonalRTL.contains(broken3T2))  eval+= 30000;
        if(horizontal.contains(broken3T1))   eval+= 30000;
        if(vertical.contains(broken3T2))     eval+= 30000;
        if(diagonalLTR.contains(broken3T1))  eval+= 30000;
        if(diagonalRTL.contains(broken3T2))  eval+= 30000;

        // open 2 EXXE #E indicates empty
        String row2 = " " + turn + turn + " ";

        if(horizontal.contains(row2))   eval+= 3000;
        if(vertical.contains(row2))     eval+= 3000;
        if(diagonalLTR.contains(row2))  eval+= 3000;
        if(diagonalRTL.contains(row2))  eval+= 3000;

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
//        return eval+5;
    }

    private String getPattern(BoardState state, int row, int col, int dr, int dc, char turn) {
        int r = row + dr;
        int c = col + dc;
        int k = 1;
//        double eval = 1.0;
//        char tempCurrentPlayer = turn;
        char tempOpponent = turn == 'X' ? 'O' : 'X';
        // search the length of 5 until it hits to the opponent stone or edge. Edge is considered to be th opponent stone?
        char[] chain = new char[2*state.wChain-1]; // the passed stone position is the center
        for(int i = 0; i < chain.length; i++)chain[i]='A';
        chain[chain.length/2]= turn;
        while(r >= 0 && r < state.boardSize && c >= 0 && c < state.boardSize && k < state.wChain && state.board[r][c] != tempOpponent) {


            // after the move, currentPlayer is already altered because currentPlayer is
            // altered in the move method. so I have to temporary alter to get the player
            // that placed the stone on row, col that are passed.
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

        // Maybe I will print out the chain here
        return new String(chain);

        // first check horizontal



//        // check 6 block which inside 4 is occupied with the same color
//        if(r-dr >= 0 && r-dr < state.boardSize && col-dc >= 0 && col-dc < state.boardSize && state.board[r-dr][col-dc] == ' ') {
//
//        }


    }

    private double evaluate2(BoardState state, int row, int col) {

            double score = (evaluate2(state, row, col, 1, 0) * evaluate2(state, row, col, -1, 0)
                    + evaluate2(state, row, col, 0, 1) * evaluate2(state, row, col, 0, -1)
                    + evaluate2(state, row, col, 1, 1) * evaluate2(state, row, col, -1, -1)
                    + evaluate2(state, row, col, 1, -1) * evaluate2(state, row, col, -1, 1));
            return score;
    }

    private double evaluate2(BoardState state, int row, int col, int dr, int dc) {
        double epsilon = 2.0;
        int r = row + dr;
        int c = col + dc;
        int k = 1;
        double eval = 1.0;
        double weight = Math.pow(2, 12);

        while(r >= 0 && r < state.boardSize && c >= 0 && c < state.boardSize && k < state.wChain) {


        // after the move, currentPlayer is already altered because currentPlayer is
        // altered in the move method. so I have to temporary alter to get the player
        // that placed the stone on row, col that are passed.
        char tempCurrentPlayer = state.getCurrentPlayer() == 'X' ? 'O' : 'X';
        char tempOpponent = state.getCurrentPlayer();
        if(state.board[row+dr][col+dc] == tempOpponent) break;
        else if(state.board[row+dr][col+dc] == ' ') eval *= epsilon;
        else if(state.board[row+dr][col+dc] == tempCurrentPlayer) eval *= weight;

        weight = Math.pow(2, 12-k);
        r += dr;
        c += dc;
        k++;
        }
        return eval;
    }

    /**
     * Evaluation function
     * @return score of board
     */
    // turn should be fixed through entire game.
    // When the game start, it have to initialize turn
    // that's why it is using maxValue method and minValue method
    // let's say the computer's turn is 'O', when it is maxValue(), we want to find value closer to infinity
    // when it is minValue, we want tob find value closer to negative infinity
//    private int evaluate(BoardState state) {
//
//        int score = 0;
//        HashMap<String, Integer> chains = state.findChain();
//
////        for (String key: chains.keySet()){
////            Log.d(TAG, " each======== " + key + "        " + chains.get(key)) ;
////        }
//
//        for(int i = state.getMinRow(); i <= state.getMaxRow(); i++) {
//            for(int j = state.getMinCol(); j <= state.getMaxCol(); j++) {
//
//            }
//        }
//
////        Iterator itF = chains.entrySet().iterator();
//        for (String key: chains.keySet()){
////            Map.Entry pair = (Map.Entry)itF.next();
////            StringTokenizer tokens = new StringTokenizer((String)pair.getKey(), ",");
////            Log.d("Heeeellllo", " inside of  " + key + "        " + chains.get(key)) ;
//            StringTokenizer tokens = new StringTokenizer(key, ",");
//            String first = tokens.nextToken();// this will contain "X or O"
////            Log.d(TAG, " each======== pair.getValue() : " + chains.get(key)) ;
//            int val = chains.get(key);
//            if(first.equals(String.valueOf(state.getAiTurn()))) {
//                if (val == 1)
//                    score += 1;
//                else if (val == 2)
//                    score += 5;
//                else if (val == 3)
//                    score += 200;
//                else if (val == 4)
//                    score += 5000;
//                else if (val == 5)
//                    score += 1000000;
//            } else {
//                if (val == 1)
//                    score -= 1;
//                if (val == 2)
//                    score -= 5;
//                else if (val == 3)
//                    score -= 500;
//                else if (val == 4)
//                    score -= 30000;
//                else if (val == 5)
//                    score -= 1000000;
//            }
//        }
////        Log.d(TAG, "SCOREEEEEEEEEEE " + score) ;
//
//        return score;
////        return 5000;
//    }


}
