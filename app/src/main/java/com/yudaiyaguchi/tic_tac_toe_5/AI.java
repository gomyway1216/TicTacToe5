package com.yudaiyaguchi.tic_tac_toe_5;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

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
        move = alphabetaSearch(state, maxDepth);
//        move = alphabetaSearch2(state, maxDepth);
    }

    public String getMove() {
        return move;
    }

//    public String alphabetaSearch2(BoardState state, int maxDepth) {
//        int v = Integer.MIN_VALUE;
//        int alpha = Integer.MIN_VALUE;
//        int beta = Integer.MAX_VALUE;
//        String bestMove = null;
//        // returns the move with the smallest index in the case of ties.
//        // How can I do that? It can only return v value
//
//        if(state.getIsFirstMove()) {
//            int center = state.getBoardSize()/2;
////            if(state.isLegalMove(center,center)) {
////                Log.d(TAG, "inside if best move= " + bestMove) ;
////            }
//
////            Log.d(TAG, " best move= " + bestMove) ;
//
//            return center +"," + center;
//        }
//        int max = -100000;
//        bestMove = "0,0";
//
////        for(int k = 0; k < state.getBoard().length; k++)
////            for(int l = 0; l < state.getBoard().length; l++)
////                Log.d(TAG, "state b : k : " + k + " l : " + l + " " + state.getBoard()[k][l]) ;
//        BoardState bs;
//
//
//
//        int eval = 0;
//        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
//        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
//        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
//        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();
//
//        for (int i = rMin; i <= rMax; i++) {
//            for(int j = cMin; j < cMax; j++) {
//                if (state.isLegalMove(i, j)) {
////                    Log.d(TAG, " each======== i : " + i + " j : " + j + " " + eval) ;
//                    eval = evaluate(state.applyMove(state.getAiTurn(), i, j));
////                    Log.d(TAG, " each======== eval : " + eval) ;
////                    if(i == 1 && j == 2)
////                        eval = 10000;
//
////                    Log.d(TAG, "evalllllllllll : " + i + " l : " + j + " " + eval) ;
////                    Log.d(TAG, "state.getAiTurn()" +  state.getAiTurn()) ;
////                    bs = state.applyMove(state.getAiTurn(), i, j);
//////                    Log.d(TAG, "state 1 : " + 1 + " l : " + 1 + " " + state.getBoard()[0][0]) ;
////                    Log.d(TAG, "state  bs 1 : " + 0 + " l : " + 0 + " " + bs.getBoard()[0][0]) ;
////                    Log.d(TAG, "state  bs 1 : " + 0 + " l : " + 1 + " " + bs.getBoard()[0][1]) ;
////                    Log.d(TAG, "state  bs 1 : " + 0 + " l : " + 2 + " " + bs.getBoard()[0][2]) ;
////                    Log.d(TAG, "state  bs 1 : " + 1 + " l : " + 0 + " " + bs.getBoard()[1][0]) ;
////                    Log.d(TAG, "state  bs 1 : " + 1 + " l : " + 1 + " " + bs.getBoard()[1][1]) ;
////                    Log.d(TAG, "state  bs 1 : " + 1 + " l : " + 2 + " " + bs.getBoard()[1][2]) ;
////                    Log.d(TAG, "state  bs 1 : " + 2 + " l : " + 0 + " " + bs.getBoard()[2][0]) ;
////                    Log.d(TAG, "state  bs 1 : " + 2 + " l : " + 1 + " " + bs.getBoard()[2][1]) ;
////                    Log.d(TAG, "state  bs 1 : " + 2 + " l : " + 2 + " " + bs.getBoard()[2][2]) ;
//
////                    for(int k = 0; k < bs.getBoard().length; k++)
////                        for(int l = 0; l < bs.getBoard().length; l++)
////                            Log.d(TAG, "bs.board : k : " +  k + " l : " + l + " " + bs.getBoard()[i][j]) ;
////                    Log.d(TAG, "eval== " + eval + " i : " + " j : " + j) ;
//                    if(eval > max) {
////                        Log.d(TAG, "best move!!!!!" + i + "  : " + j) ;
//                        bestMove = i + "," + j;
//                        max = eval;
//                    }
//
//
//                }
//            }
//        }
//
//
//      return bestMove;
//    }

    public String alphabetaSearch(BoardState state, int maxDepth) {
        // function ALPHA-BETA-SEARCH(state) returns an action v ←MAX-VALUE(state,−∞,+∞)
        // return the action in ACTIONS(state) with value v
        double v = Double.MIN_VALUE;
        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;
        String bestMove = null;
        // returns the move with the smallest index in the case of ties.
        // How can I do that? It can only return v value

        if(state.getIsFirstMove()) {
            int center = state.getBoardSize()/2;
            if(state.isLegalMove(center,center)) {
//                Log.d(TAG, "inside if best move= " + bestMove) ;
                bestMove = center + "," + center;
            }
//            else if(state.isLegalMove(center+1,center)) {
//                bestMove = (center+1) + "," + center;
//            } else if(state.isLegalMove(center-1,center)) {
//                bestMove = (center-1) + "," + center;
//            } else if(state.isLegalMove(center,center+1)) {
//                bestMove = center + "," + (center+1);
//            }  else if(state.isLegalMove(center,center-1)) {
//                bestMove = center + "," + (center-1);
//            } else {
//                bestMove = 0 + "," + 0;
//            }

//            Log.d(TAG, " best move= " + bestMove) ;

            return bestMove;
        }

//        int count =0;

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 0 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 0 ? state.getMinCol() - 1 : state.getMinCol();

        boolean isBreak = false;
        for (int i = rMin; i <= rMax; i++) {
            if(isBreak) break;
//            i = 6;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    v = Math.max(v, minValue(state.applyMove('X', i, j), maxDepth,
                            maxDepth - 1, alpha, beta, i, j));
                    // v = getBestScore()
//                    Log.d(TAG, "before alpha =" + alpha) ;
                    if (v > alpha) {
//                        count++;
                        alpha = v;
                        bestMove = i + "," + j;
//                        Log.d(TAG, "v>alpha and v = " + v + "alpha =" + alpha) ;
                    }

                    if (alpha >= beta) {
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


    public double maxValue(BoardState state, int maxDepth, int currentDepth, double alpha, double beta, int row, int col) {
        if (currentDepth == 0)
            return evaluate3(state, row, col, state.getUserTurn());


        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        double v = Double.MIN_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    v = Math.max(v, minValue(state.applyMove('X', i, j), maxDepth,
                            currentDepth - 1, alpha, beta, i, j));

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

    public double minValue(BoardState state, int maxDepth, int currentDepth, double alpha, double beta, int row, int col) {
        if (currentDepth == 0)
            return evaluate3(state, row, col, state.getAiTurn());

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        double v = Double.MAX_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    v = Math.min(v, maxValue(state.applyMove('O', i, j), maxDepth,
                            currentDepth - 1, alpha, beta, i, j));
                    if (v <= alpha)
                        return v;

                    beta = Math.min(beta, v);

//                    if (beta <= alpha)
//                         break;
                }
            }
        }

        if (v == Integer.MAX_VALUE) {
//            Log.d(TAG, "min score = " + evaluate(state)) ;

            return evaluate3(state, row, col, state.getAiTurn());
        }
        return v;
    }

    private double evaluate3(BoardState state, int row, int col, char turn) {
        char current = turn;
//        char current = state.getCurrentPlayer();
        char opponent = state.getCurrentPlayer();
        String horizontal = getPattern(state, row, col, 0, 1, turn);
        Log.d("charArray1", horizontal);
        String vertical = getPattern(state, row, col, 1, 0, turn);
        String diagonalLTR = getPattern(state, row, col, 1, 1, turn);
        String diagonalRTL = getPattern(state, row, col, 1, -1, turn);
        String row5 = current+""+current+""+current+""+current+""+current;
        double eval = 0.0;

        Log.d("evaluate3", "evaluate3 is called");

        if(horizontal.contains(row5) || vertical.contains(row5) || diagonalLTR.contains(row5)
                || diagonalRTL.contains(row5))  eval+= 10000000000.0;

//        if(row == 0 && col == 1)    Log.d("positions", "This should be huge! " + eval);
        if(row == 6 && col == 3 && counter == 4) {
            Log.d("positions", "This should be huge! " + horizontal);
            Log.d("positions", "This should be huge! " + vertical);
            Log.d("positions", "This should be huge! " + diagonalLTR);
            Log.d("positions", "This should be huge! " + diagonalRTL);

        }


        Log.d("positions", "This should be huge! " + row + "," + col + " val " + eval);

        String row4Win = " " + current+""+current+""+current+""+current+ " ";
        if(horizontal.contains(row4Win))    eval+= 10000000;
        if(vertical.contains(row4Win))      eval+= 10000000;
        if(diagonalLTR.contains(row4Win))   eval+= 10000000;
        if(diagonalRTL.contains(row4Win))   eval+= 10000000;

        String normal4 = " " + current+""+current+""+current+""+current;

//        if(horizontal.contains(row4Win) && vertical.contains(row4Win) ||
//                horizontal.contains(row4Win) && diagonalLTR.contains(row4Win)
//                || horizontal.contains(row4Win) && diagonalRTL.contains(row4Win)
//                || vertical.contains(row4Win) && diagonalLTR.contains(row4Win)
//                || vertical.contains(row4Win) && diagonalRTL.contains(row4Win)
//                || vertical.contains(row4Win) && )
        if(horizontal.contains(normal4))    eval+= 100000;
        if(vertical.contains(normal4))  eval+= 100000;
        if(diagonalLTR.contains(normal4))   eval+= 100000;
        if(diagonalRTL.contains(normal4))   eval+= 100000;

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

        if(horizontal.charAt(horizontal.length()/2-1) == current)   eval+= 1000;

        return eval+5;
    }

    private String getPattern(BoardState state, int row, int col, int dr, int dc, char turn) {
        double point = 0;

        // check OOOOO
//        char tempCurrentPlayer = state.getCurrentPlayer() == 'X' ? 'O' : 'X';
//        char tempOpponent = state.getCurrentPlayer();
//        int count  = 1; // center stone should be also counted
//        int r = row + dr;
//        int c = col + dc;
//        while(r >= 0 && r < state.boardSize && c >= 0 && c < state.boardSize && state.board[r][c] == tempCurrentPlayer) {
//            count++;
//            r += dr;
//            c += dc;
//        }
//
//        if(count >= 5)  return 10000000.0;
//
//        r = row - dr;
//        c = col - dc;
//        while(r >= 0 && r < state.boardSize && c >= 0 && c < state.boardSize && state.board[r][c] == state.currentPlayer) {
//            count++;
//            r -= dr;
//            c -= dc;
//        }
//
//        if(count >= 5)  return 10000000.0;

        int r = row + dr;
        int c = col + dc;
        int k = 1;
        double eval = 1.0;
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
