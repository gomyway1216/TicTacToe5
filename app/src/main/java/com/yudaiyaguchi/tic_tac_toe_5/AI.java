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

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void move(BoardState state) {
        move = alphabetaSearch(state, maxDepth);
//        move = alphabetaSearch2(state, maxDepth);
    }

    public String getMove() {
        return move;
    }

    public String alphabetaSearch2(BoardState state, int maxDepth) {
        int v = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        String bestMove = null;
        // returns the move with the smallest index in the case of ties.
        // How can I do that? It can only return v value

        if(state.getIsFirstMove()) {
            int center = state.getBoardSize()/2;
//            if(state.isLegalMove(center,center)) {
//                Log.d(TAG, "inside if best move= " + bestMove) ;
//            }

//            Log.d(TAG, " best move= " + bestMove) ;

            return center +"," + center;
        }
        int max = -100000;
        bestMove = "0,0";

//        for(int k = 0; k < state.getBoard().length; k++)
//            for(int l = 0; l < state.getBoard().length; l++)
//                Log.d(TAG, "state b : k : " + k + " l : " + l + " " + state.getBoard()[k][l]) ;
        BoardState bs;



        int eval = 0;
        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j < cMax; j++) {
                if (state.isLegalMove(i, j)) {
//                    Log.d(TAG, " each======== i : " + i + " j : " + j + " " + eval) ;
                    eval = evaluate(state.applyMove(state.getAiTurn(), i, j));
//                    Log.d(TAG, " each======== eval : " + eval) ;
//                    if(i == 1 && j == 2)
//                        eval = 10000;

//                    Log.d(TAG, "evalllllllllll : " + i + " l : " + j + " " + eval) ;
//                    Log.d(TAG, "state.getAiTurn()" +  state.getAiTurn()) ;
//                    bs = state.applyMove(state.getAiTurn(), i, j);
////                    Log.d(TAG, "state 1 : " + 1 + " l : " + 1 + " " + state.getBoard()[0][0]) ;
//                    Log.d(TAG, "state  bs 1 : " + 0 + " l : " + 0 + " " + bs.getBoard()[0][0]) ;
//                    Log.d(TAG, "state  bs 1 : " + 0 + " l : " + 1 + " " + bs.getBoard()[0][1]) ;
//                    Log.d(TAG, "state  bs 1 : " + 0 + " l : " + 2 + " " + bs.getBoard()[0][2]) ;
//                    Log.d(TAG, "state  bs 1 : " + 1 + " l : " + 0 + " " + bs.getBoard()[1][0]) ;
//                    Log.d(TAG, "state  bs 1 : " + 1 + " l : " + 1 + " " + bs.getBoard()[1][1]) ;
//                    Log.d(TAG, "state  bs 1 : " + 1 + " l : " + 2 + " " + bs.getBoard()[1][2]) ;
//                    Log.d(TAG, "state  bs 1 : " + 2 + " l : " + 0 + " " + bs.getBoard()[2][0]) ;
//                    Log.d(TAG, "state  bs 1 : " + 2 + " l : " + 1 + " " + bs.getBoard()[2][1]) ;
//                    Log.d(TAG, "state  bs 1 : " + 2 + " l : " + 2 + " " + bs.getBoard()[2][2]) ;

//                    for(int k = 0; k < bs.getBoard().length; k++)
//                        for(int l = 0; l < bs.getBoard().length; l++)
//                            Log.d(TAG, "bs.board : k : " +  k + " l : " + l + " " + bs.getBoard()[i][j]) ;
//                    Log.d(TAG, "eval== " + eval + " i : " + " j : " + j) ;
                    if(eval > max) {
//                        Log.d(TAG, "best move!!!!!" + i + "  : " + j) ;
                        bestMove = i + "," + j;
                        max = eval;
                    }


                }
            }
        }


      return bestMove;
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
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        boolean isBreak = false;
        for (int i = rMin; i <= rMax; i++) {
            if(isBreak) break;
            for (int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    v = Math.max(v, minValue(state.applyMove('X', i, j), maxDepth,
                            maxDepth - 1, alpha, beta));
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


    public int maxValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {
        if (currentDepth == 0)
            return evaluate(state);


        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        int v = Integer.MIN_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    v = Math.max(v, minValue(state.applyMove('X', i, j), maxDepth,
                            currentDepth - 1, alpha, beta));

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

            return evaluate(state);
        }

        return v;
    }

    public int minValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {
        if (currentDepth == 0)
            return evaluate(state);

        int rMax = state.getMaxRow() < state.boardSize-1 ? state.getMaxRow() + 1 : state.getMaxRow();
        int rMin = state.getMinRow() > 1 ? state.getMinRow() - 1 : state.getMinRow();
        int cMax = state.getMaxCol() < state.boardSize-1 ? state.getMaxCol() + 1 : state.getMaxCol();
        int cMin = state.getMinCol() > 1 ? state.getMinCol() - 1 : state.getMinCol();

        int v = Integer.MAX_VALUE;
        for (int i = rMin; i <= rMax; i++) {
            for(int j = cMin; j <= cMax; j++) {
                if (state.isLegalMove(i, j)) {
                    v = Math.min(v, maxValue(state.applyMove('O', i, j), maxDepth,
                            currentDepth - 1, alpha, beta));
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

            return evaluate(state);
        }
        return v;
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
    private int evaluate(BoardState state) {

        int score = 0;
        HashMap<String, Integer> chains = state.findChain();

//        for (String key: chains.keySet()){
//            Log.d(TAG, " each======== " + key + "        " + chains.get(key)) ;
//        }

        for(int i = state.getMinRow(); i <= state.getMaxRow(); i++) {
            for(int j = state.getMinCol(); j <= state.getMaxCol(); j++) {

            }
        }

//        Iterator itF = chains.entrySet().iterator();
        for (String key: chains.keySet()){
//            Map.Entry pair = (Map.Entry)itF.next();
//            StringTokenizer tokens = new StringTokenizer((String)pair.getKey(), ",");
//            Log.d("Heeeellllo", " inside of  " + key + "        " + chains.get(key)) ;
            StringTokenizer tokens = new StringTokenizer(key, ",");
            String first = tokens.nextToken();// this will contain "X or O"
//            Log.d(TAG, " each======== pair.getValue() : " + chains.get(key)) ;
            int val = chains.get(key);
            if(first.equals(String.valueOf(state.getAiTurn()))) {
                if (val == 1)
                    score += 1;
                else if (val == 2)
                    score += 5;
                else if (val == 3)
                    score += 200;
                else if (val == 4)
                    score += 5000;
                else if (val == 5)
                    score += 1000000;
            } else {
                if (val == 1)
                    score -= 1;
                if (val == 2)
                    score -= 5;
                else if (val == 3)
                    score -= 500;
                else if (val == 4)
                    score -= 30000;
                else if (val == 5)
                    score -= 1000000;
            }
        }
//        Log.d(TAG, "SCOREEEEEEEEEEE " + score) ;

        return score;
//        return 5000;
    }


}
