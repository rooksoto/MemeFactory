package rooksoto.c4q.nyc.memefactory.View.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * Created by rook on 1/20/17.
 */

public class DrawableCanvasView extends ImageView {

    private int color = Color.WHITE;
    private float width = 15f;
    private ArrayList<PathHolder> pathHolderList = new ArrayList<>();

    private class PathHolder {
        Path path;
        Paint paint;

        PathHolder(int color, float width) {
            path = new Path();
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(width);
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    public DrawableCanvasView(Context context) {
        super(context);
        init();
    }

    public DrawableCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawableCanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        pathHolderList.add(new PathHolder(color, width));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathHolder pathHolder : pathHolderList) {
            canvas.drawPath(pathHolder.path, pathHolder.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pathHolderList.add(new PathHolder(color,width));
                pathHolderList.get(pathHolderList.size() - 1).path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                pathHolderList.get(pathHolderList.size() - 1).path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void resetPaths() {
        for (PathHolder pathHolder : pathHolderList) {
            pathHolder.path.reset();
        }
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getStrokeWidth() {
        return width;
    }

    public void setStrokeWidth(float width) {
        this.width = width;
    }
}
