package rooksoto.c4q.nyc.memefactory.View.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by rook on 1/21/17.
 */



/**
 * This class is broken... I switched to paths, using an ArrayList of PathHolders
 * which is a MUCH more elegant solution, which WORKS!
 */



public class DrawableCanvasViewBroken extends ImageView {

    private static int dcvStrokeColor = Color.WHITE;
    private static float dcvStrokeWidth = 5f;

    private static Paint paint;

    private static Canvas canvas;

    private static float downX = 0;
    private static float downY = 0;
    private static float upX = 0;
    private static float upY = 0;

    public DrawableCanvasViewBroken (Context context, AttributeSet attrs) {
        super(context, attrs);

        canvas = new Canvas();
        paint = new Paint();

        paint.setStrokeWidth(dcvStrokeWidth);
        paint.setColor(dcvStrokeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setDcvStrokeColor(int dcvStrokeColor) {
        this.dcvStrokeColor = dcvStrokeColor;
    }

    public void setDcvStrokeWidth(float dcvStrokeWidth) {
        this.dcvStrokeWidth = dcvStrokeWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                upX = motionEvent.getX();
                upY = motionEvent.getY();
                canvas.drawLine(downX, downY, upX, upY, paint);
                downX = upX;
                downY = upY;
                break;
            case MotionEvent.ACTION_CANCEL:
                // Don't need to do anything
                break;
            case MotionEvent.ACTION_MOVE:
                upX = motionEvent.getX();
                upY = motionEvent.getY();
                canvas.drawLine(downX, downY, upX, upY, paint);
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
}
