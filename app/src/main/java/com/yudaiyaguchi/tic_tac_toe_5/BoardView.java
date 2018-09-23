package com.yudaiyaguchi.tic_tac_toe_5;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class BoardView extends View {
    private static final int LINE_THICK = 5;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 15;
    private int width, height, eltW, eltH;
    // oPaint is just the paint of '0' and xPaint is 'X'
    private Paint gridPaint, oPaint, xPaint;
    private GameEngine gameEngine;
    private GameActivity activity;



    private int squareLength, boxLength;
    private int boardSize;
    private BoardState boardState;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();

        boardSize = 7;

        // smooths out the edges of what is being drawn
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        // use the same setting as oPaint
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);


    }

//    public void setMainActivity(GameActivity activity) {
//        this.activity = activity;
//    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }



    //
    public void setGameActivity(GameActivity gameContainer) {
        this.activity = gameContainer;
    }
    //
    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }






//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        height = View.MeasureSpec.getSize(heightMeasureSpec);
//        width = View.MeasureSpec.getSize(widthMeasureSpec);
//        // I think this eltW and eltH is the with and height of each boxes
//        eltW = (width - LINE_THICK) / 3;
//        eltH = (height - LINE_THICK) / 3;
//
//        setMeasuredDimension(width, height);
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);

//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
        squareLength = Math.min(width, height);
        boxLength =  squareLength / boardSize;

        eltW = (squareLength - LINE_THICK) / 3;
        eltH = (squareLength - LINE_THICK) / 3;

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(squareLength, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(squareLength, MeasureSpec.EXACTLY));
    }




    //
    // #
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        height = View.MeasureSpec.getSize(heightMeasureSpec);
//        width = View.MeasureSpec.getSize(widthMeasureSpec);
//
//
//        // I think this eltW and eltH is the with and height of each boxes
//        eltW = (width - LINE_THICK) / 3;
//        eltH = (height - LINE_THICK) / 3;
//
//
//        if(width > height)
//            squareLength = height;
//        else
//            squareLength = width;
//
//        // set the size of each box
//        // each box should be square
//        boxLength =  squareLength / boardSize;
//        // the entire board should be also square
//        setMeasuredDimension(width, height);
//
//    }







    @Override
    protected  void onDraw(Canvas canvas) {
        drawGrid(canvas);
//        drawBoard(canvas);
        drawPieces(canvas);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(!gameEngine.isEnded() && event.getAction() == MotionEvent.ACTION_DOWN) {
//            int x = (int) (event.getX() / eltW);
//            int y = (int) (event.getY() / eltH);
//            char win = gameEngine.play(x, y);
//            invalidate();
//
//            if(win != ' ') {
//                activity.gameEnded(win);
//            } else {
//                // computer plays
//                win = gameEngine.randomBot();
//                invalidate();
//
//                if(win != ' ') {
//                    activity.gameEnded(win);
//                }
//            }
//        }
//
//        return super.onTouchEvent(event);
//    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // I think this is assuming opponet is CPU
        // I will modify this later so that Human vs Human can be operated.
        // this also have to check whether the movement is illigal or not.
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / boxLength);
            int y = (int) (event.getY() / boxLength);
//            Log.d("pair: ","pair : " + y + " " + x);
            // this part would be exchanged with change turn method
            // this "win", I am not sure
//            char win = gameEngine.play(x, y);
//            invalidate();

            // I am not sure whether it is going to work or not.
//            if(boardState.isLegalMove(x, y)) {
//                return false;
            // I think this is assuming that user always goes first
            if(boardState.getUserTurn() == boardState.getCurrentPlayer()) {
                if(boardState.isLegalMove(y, x)) {
                    // I am not very sure about these order
                    // call board.move method
//                    invalidate();
                    boolean userWin = boardState.move(y, x);
//                    boolean userWin = boardState.isEnded();
                    invalidate();
                    // if user win
                    if(userWin)
                        activity.gameEnded(boardState.getUserTurn());
                    else {
                        boolean aiWin = boardState.aiMove();
//                        boolean aiWin = boardState.isEnded();
                        invalidate();

                        if(aiWin)
                            activity.gameEnded((boardState.getAiTurn()));
                    }
                    // call change turn -> this is called inside of move method

                    // check ends
                }
            }
        }

        return super.onTouchEvent(event);
    }


//    private void drawBoard(Canvas canvas) {
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 3; j++) {
//                drawElt(canvas, gameEngine.getBoardstates(i, j), i, j);
//            }
//        }
//    }

//    private void drawGrid(Canvas canvas) {
//        // x coordinates start from left and
//        // y coordinates start from top
//        // so that's why left = eltW * (i +1)
//        // and top2 = eltH * (i + 1)
//        for (int i = 0; i < 2; i++) {
//            // vertical lines
//            float left = eltW * (i + 1);
//            float right = left + LINE_THICK;
//            float top = 0;
//            float bottom = height;
//
//            canvas.drawRect(left, top, right, bottom, gridPaint);
//
//            // horizontal lines
//            float left2 = 0;
//            float right2 = width;
//            float top2 = eltH * (i + 1);
//            float bottom2 = top2 + LINE_THICK;
//
//            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
//        }
//    }



    private void drawGrid(Canvas canvas) {
        // x coordinates start from left and
        // y coordinates start from top
        // so that's why left = eltW * (i +1)
        // and top2 = eltH * (i + 1)
        for(int i = 0; i < boardSize -1; i++) {
            // vertical lines
            // the line has thickness, that's why there is left and right
            // without +1, it is gonna draw line on the edge of the screen
            // that doesn't make sense
            float left = boxLength * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = squareLength;
            float top2 = boxLength * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    // after char win = gameEngine.play(x, y); is called in onTouchEvent,
    // boardState inside of gameEngine would be changed.
    // by accessing the board by gameEngine.getBoardstates(i, j),
    // we can know current board and draw it.
    private void drawPieces(Canvas canvas) {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                // I am guessing this is going to draw after computer moves
                // But I am still not sure
                // gameEngine.getBoardstates(i, j) is the char
                drawEachBox(canvas, boardState.getCharOfBox(i,j), i, j);
            }
        }
    }

    private void drawEachBox(Canvas canvas, char c, int y, int x) {
        if(c == 'O') {
            // move by the length of box and by boxLength / 2, it is drawing it to the center of box
            float cx = (boxLength * x) + boxLength / 2;
            float cy = (boxLength * y) + boxLength / 2;
            canvas.drawCircle(cx, cy, boxLength / 2 - ELT_MARGIN, oPaint);
        } else if(c == 'X') {
            // there are somthing, I am not sure,
            // but I am gonna look at it later.

            // drawing should not be starting from the edge of the line,
            // that's why it is adding ELT_MARGIN I think

            // this is from left to right of 'X'
            float startX = (boxLength * x) + ELT_MARGIN;
            float startY = (boxLength * y) + ELT_MARGIN;
            float endX = startX + boxLength - ELT_MARGIN * 2;
            float endY = startY + boxLength - ELT_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            // this is from right to left of 'X'
            float startX2 = (boxLength * (x + 1)) - ELT_MARGIN;
            float startY2 = (boxLength * y) + ELT_MARGIN;
            float endX2 = startX2 - boxLength + ELT_MARGIN * 2;
            float endY2 = startY2 + boxLength - ELT_MARGIN;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }

//    private void drawElt(Canvas canvas, char c, int x, int y) {
//        if(c == '0') {
//            // move by the length of box and by eltW / 2, it is drawing it to the center of box
//            float cx = (eltW * x) + eltW / 2;
//            float cy = (eltH * y) + eltH / 2;
//
//            canvas.drawCircle(cx, cy, Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2, oPaint);
//        } else if(c == 'X') {
//            // drawing should not be starting from the edge of the line,
//            // that's why it is adding ELT_MARGIN I think
//
//            // this is from left to right of 'X'
//            float startX = (eltW * x) + ELT_MARGIN;
//            float startY = (eltH * y) + ELT_MARGIN;
//            float endX = startX + eltW - ELT_MARGIN * 2;
//            float endY = startY + eltH - ELT_MARGIN;
//
//            canvas.drawLine(startX, startY, endX, endY, xPaint);
//
//            // this is from right to left of 'X'
//            float startX2 = (eltW * (x + 1)) - ELT_MARGIN;
//            float startY2 = (eltH * y) + ELT_MARGIN;
//            float endX2 = startX2 - eltW + ELT_MARGIN * 2;
//            float endY2 = startY2 + eltH - ELT_MARGIN;
//
//            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
//        }
//    }

}
