package com.patryk1007.fillme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.patryk1007.fillme.calculations.ConvexLinesCalculation;
import com.patryk1007.fillme.calculations.FloodFillLinesCalculationAsync;
import com.patryk1007.fillme.calculations.FloodFillLinesCalculationSync;
import com.patryk1007.fillme.calculations.LinesCalculation;
import com.patryk1007.fillme.enums.FillMode;
import com.patryk1007.fillme.listeners.OnFillChangeListener;
import com.patryk1007.fillme.listeners.OnFillLineCalculationListener;
import com.patryk1007.fillme.models.FillLine;

import java.util.ArrayList;
import java.util.Calendar;

public class FillMe extends View {

    public static final int DEFAULT_ALPHA_LEVEL = 122;//0-255

    private Bitmap image;
    private int alphaLevel = DEFAULT_ALPHA_LEVEL;
    private float fillPercentVertical;
    private float fillPercentHorizontal;
    private FillMode fillMode;
    private boolean convexFigure;
    private boolean asyncCalculation;
    private int fillColour = Color.GREEN;

    private int width;
    private int height;
    private Paint paint = new Paint();
    private ArrayList<FillLine> fillLines = new ArrayList<>();

    public FillMe(Context context) {
        super(context);
        paint.setColor(fillColour);
    }

    public FillMe(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttr(attrs);
        paint.setColor(fillColour);
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FillMe);
        asyncCalculation = typedArray.getBoolean(R.styleable.FillMe_fmAsyncCalculation, true);
        setImageDrawable(typedArray.getDrawable(R.styleable.FillMe_fmImage));
        setConvexFigure(typedArray.getBoolean(R.styleable.FillMe_fmConvexFigure, true));
        alphaLevel = (typedArray.getInt(R.styleable.FillMe_fmAlphaLevel, DEFAULT_ALPHA_LEVEL));
        this.fillColour = typedArray.getInt(R.styleable.FillMe_fmFillColour, fillColour);
        paint.setColor(fillColour);
        float percentFillHorizontal = typedArray.getFloat(R.styleable.FillMe_fmFillHorizontalPercent, -1);
        float percentFillVertical = typedArray.getFloat(R.styleable.FillMe_fmFillVerticalPercent, -1);
        setFillPercentHorizontalAndVertical(percentFillHorizontal, percentFillVertical);
        fillMode = (FillMode.fromId(typedArray.getInt(R.styleable.FillMe_fmFillByTouch, FillMode.NONE.getValue())));
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (image != null) {
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            } else if (widthMode == MeasureSpec.AT_MOST) {
                width = image.getWidth();
            } else {
                width = image.getWidth();
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            } else if (heightMode == MeasureSpec.AT_MOST) {
                height = image.getHeight();
            } else {
                height = image.getHeight();
            }

            if (image.getWidth() != width || image.getHeight() != height) {
                updateDrawable();
            }
        } else {
            width = widthSize;
            height = heightSize;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            int fillX = -1;
            int fillVertical = -1;
            if (fillMode == FillMode.BOTH || fillMode == FillMode.HORIZONTAL) {
                fillX = (int) event.getX();
            }
            if (fillMode == FillMode.BOTH || fillMode == FillMode.VERTICAL) {
                fillVertical = (int) event.getY();
            }
            fillPixelXY(fillX, fillVertical);
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (image != null) {
            long timeStart = Calendar.getInstance().getTimeInMillis();
            fillShape(canvas);
            Log.d("SPEED_TEST2", "Line: " + (Calendar.getInstance().getTimeInMillis() - timeStart));
            canvas.drawBitmap(image, 0, 0, null);
        }
    }


    private LinesCalculation initCalculationManager() {
        if (isConvexFigure()) {
            return new ConvexLinesCalculation();
        }
        if (asyncCalculation) {
            return new FloodFillLinesCalculationAsync();
        }
        return new FloodFillLinesCalculationSync();
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        requestLayout();
    }

    public void setImageDrawableId(int drawableId) {
        setImage(BitmapFactory.decodeResource(getResources(), drawableId));
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            setImage(((BitmapDrawable) drawable).getBitmap());
        }
    }

    public int getAlphaLevel() {
        return alphaLevel;
    }

    public void setAlphaLevel(int alphaLevel) {
        this.alphaLevel = alphaLevel;
        updateDrawable();
        postInvalidate();
    }

    public boolean isConvexFigure() {
        return convexFigure;
    }

    public void setConvexFigure(boolean convexFigure) {
        if (this.convexFigure != convexFigure && image != null) {
            this.convexFigure = convexFigure;
            updateDrawable();
            this.postInvalidate();
        }
    }

    public boolean isAsyncCalculation() {
        return asyncCalculation;
    }

    public void setSyncCalculation(boolean asyncCalculation) {
        this.asyncCalculation = asyncCalculation;
    }

    public int getFillPixelVertical() {
        return (int) (fillPercentVertical * height);
    }

    public void setFillPixelVertical(int fillPixelVertical) {
        fillVertical(fillPixelVertical);
    }

    public int getFillPixelHorizontal() {
        return (int) (fillPercentHorizontal * width);
    }

    public void setFillPixelHorizontal(int fillPixelHorizontal) {
        fillHorizontal(fillPixelHorizontal);
    }

    public void setFillPercentHorizontal(float percentX) {
        percentX = percentX > 1.0f ? 1.0f : percentX;
        percentX = percentX < 0 ? 0 : percentX;
        fillHorizontal((int) (width * percentX));
    }

    public float getFillPercentHorizontal() {
        return fillPercentHorizontal;
    }

    public FillMode getFillMode() {
        return fillMode;
    }

    public void setFillMode(FillMode fillMode) {
        this.fillMode = fillMode;
    }

    public void setFillPercentVertical(float percentY) {
        percentY = percentY > 1.0f ? 1.0f : percentY;
        percentY = percentY < 0 ? 0 : percentY;
        fillVertical((int) (height * percentY));
    }

    public void setFillPercentHorizontalAndVertical(float percentHorizontal, float percentVertical) {
        percentHorizontal = percentHorizontal > 1.0f ? 1.0f : percentHorizontal;
        percentVertical = percentVertical > 1.0f ? 1.0f : percentVertical;
        if (percentVertical >= 0) {
            percentVertical = 1.0f - percentVertical;
        }

        if (percentHorizontal >= 0) {
            fillPercentHorizontal = percentHorizontal;
            onFillChangeListener.onHorizontalValueChange(percentHorizontal, (int) (width * percentHorizontal));
        }
        if (percentVertical > 0) {
            fillPercentVertical = percentVertical;
            onFillChangeListener.onVerticalValueChange(percentVertical, (int) (height * percentVertical));
        }

        if (fillPercentHorizontal >= 0 || fillPercentVertical >= 0) {
            this.postInvalidate();
        }
    }

    public float getFillPercentVertical() {
        return fillPercentVertical;
    }

    public int getFillColour() {
        return fillColour;
    }

    public void setFillColour(int fillColour) {
        this.fillColour = fillColour;
        paint.setColor(fillColour);
        this.postInvalidate();
    }

    public void setOnFillChangeListener(OnFillChangeListener onFillChangeListener) {
        this.onFillChangeListener = onFillChangeListener;
    }

    private void fillHorizontal(int x) {
        setFillPercentHorizontalAndVertical(x / (float) width, -1);
    }

    private void fillVertical(int y) {
        setFillPercentHorizontalAndVertical(-1, y / (float) height);
    }

    private void fillPixelXY(int horizontal, int vertical) {
        setFillPercentHorizontalAndVertical(horizontal / (float) width, 1 - (vertical / (float) height));
    }

    private void updateDrawable() {
        if (image != null && width > 0 && height > 0) {
            image = Bitmap.createScaledBitmap(image, width, height, false);
            fillLines = new ArrayList<>();
            initCalculationManager().startCalculation(image, alphaLevel, onFillLineCalculationListener);
        }
    }

    private void fillShape(Canvas canvas) {
        int lineSize = fillLines.size();
        int fillPixelVertical = getFillPixelVertical();
        int fillPixelHorizontal = getFillPixelHorizontal();
        for (int i = lineSize - 1; i >= 0; i--) {
            FillLine fillLine = fillLines.get(i);
            if (fillLine.getStartY() >= fillPixelVertical) {
                if (fillLine.getStartX() < fillPixelHorizontal) {
                    canvas.drawLine(fillLine.getStartX(), fillLine.getStartY(), fillLine.getEndX() > fillPixelHorizontal ? fillPixelHorizontal : fillLine.getEndX(), fillLine.getEndY(), paint);
                }
            } else {
                return;
            }
        }
    }

    private OnFillLineCalculationListener onFillLineCalculationListener = new OnFillLineCalculationListener() {
        @Override
        public void onCompleted(ArrayList<FillLine> points) {
            fillLines = points;
            invalidate();
        }
    };

    private OnFillChangeListener onFillChangeListener = new OnFillChangeListener() {
        @Override
        public void onHorizontalValueChange(float fillPercent, int fillPx) {

        }

        @Override
        public void onVerticalValueChange(float fillPercent, int fillPx) {

        }
    };

}
