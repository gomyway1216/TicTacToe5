package com.yudaiyaguchi.tic_tac_toe_5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {
    private static final int LINE_THICK = 5;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 15;
    private int width, height, eltW, eltH;
    private Paint gridPaint, oPaint, xPaint;
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

        // 'O' shape and 'X' shape
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);
    }

    public void setGameActivity(GameActivity gameContainer) {
        this.activity = gameContainer;
    }

    // This is called to set the view to Game activity
    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
        boardSize = boardState.getBoardSize();
    }

    // measure the screen and the value is used my
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        squareLength = Math.min(width, height);
        boxLength =  squareLength / boardSize;

        eltW = (squareLength - LINE_THICK) / 3;
        eltH = (squareLength - LINE_THICK) / 3;

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(squareLength, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(squareLength, MeasureSpec.EXACTLY));
    }

    // This method is called each time invalidate() is called
    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawPieces(canvas);
    }

    // Detect user interaction with screen (detect user putting stones)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / boxLength);
            int y = (int) (event.getY() / boxLength);

            // if it is user's turn
            if(boardState.getUserTurn() == boardState.getCurrentPlayer()) {
                if(boardState.isLegalMove(y, x)) {
                    boolean userWin = boardState.move(y, x);
                    invalidate();

                    // if user win
                    if(userWin)
                        activity.gameEnded(boardState.getUserTurn());
                    else {
                        if(boardState.isBoardFilled()) activity.gameEnded('D');

                        // if there are 2 players, no need to implement AI
                        if(boardState.getIsMultiPlayer()) {
                            boardState.setUserTurn(boardState.getCurrentPlayer());
                            return super.onTouchEvent(event);
                        }

                        // move on to ai move after user made a move
                        boolean aiWin = boardState.aiMove();
                        invalidate();

                        if(aiWin)
                            activity.gameEnded((boardState.getAiTurn()));
                    }

                    if(boardState.isBoardFilled()) activity.gameEnded('D');
                }
            }
        }
        return super.onTouchEvent(event);
    }

    // draw the grid runs from top to down and left to right
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

    // draw each pieces by accessing char list in boardState
    private void drawPieces(Canvas canvas) {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                drawEachBox(canvas, boardState.getCharOfBox(i,j), i, j);
            }
        }
    }

    // draw 'O' and 'X' at the given position on canvas
    private void drawEachBox(Canvas canvas, char c, int y, int x) {
        if(c == 'O') {
            // move by the length of box and by boxLength / 2, it is drawing it to the center of box
            float cx = (boxLength * x) + boxLength / 2;
            float cy = (boxLength * y) + boxLength / 2;
            canvas.drawCircle(cx, cy, boxLength / 2 - ELT_MARGIN, oPaint);
        } else if(c == 'X') {
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
}